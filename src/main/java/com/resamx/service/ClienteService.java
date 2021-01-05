package com.resamx.service;

import com.resamx.config.Constants;
import com.resamx.config.TipoDocumentoEnum;
import com.resamx.config.TipoEmpresaEnum;
import com.resamx.domain.Authority;
import com.resamx.domain.CatTipoDocumento;
import com.resamx.domain.Cliente;
import com.resamx.domain.DatosUsuario;
import com.resamx.domain.DomicilioEmpresa;
import com.resamx.domain.DomicilioUsuario;
import com.resamx.domain.Empresa;
import com.resamx.domain.ExpedienteCliente;
import com.resamx.domain.User;
import com.resamx.repository.AuthorityRepository;
import com.resamx.repository.CatTipoDocumentoRepository;
import com.resamx.repository.ClienteRepository;
import com.resamx.repository.DomicilioEmpresaRepository;
import com.resamx.repository.DomicilioUsuarioRepository;
import com.resamx.repository.EmpresaRepository;
import com.resamx.repository.ExpedienteClienteRepository;
import com.resamx.repository.UserRepository;
import com.resamx.security.AuthoritiesConstants;
import com.resamx.service.dto.ClienteDTO;
import com.resamx.service.dto.DatosDireccionCliente;
import com.resamx.service.dto.DatosEmpresaCliente;
import com.resamx.service.dto.DatosGeneralesCliente;
import com.resamx.service.dto.DomicilioEmpresaCriteria;
import com.resamx.service.dto.DomicilioUsuarioCriteria;
import com.resamx.service.dto.ExpedienteClienteCriteria;
import com.resamx.service.dto.UserDTO;
import com.resamx.service.mapper.DomicilioUsuarioMapper;
import com.resamx.service.mapper.UserMapper;


import io.github.jhipster.security.RandomUtil;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.LongFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing {@link Cliente}.
 */
@Service
@Transactional
public class ClienteService {

    private final Logger log = LoggerFactory.getLogger(ClienteService.class);

    private final ClienteRepository clienteRepository;
    private final UserRepository userRepository;
    private final DomicilioUsuarioRepository domicilioRepository;
    private final DomicilioEmpresaRepository domicilioEmpresaRepository;
    private final EmpresaRepository empresaRepository;
    private final AuthorityRepository authorityRepository;
    private final CatTipoDocumentoRepository tipoDocumentoRepository;
    private final ExpedienteClienteRepository expedienteClienteRepository;
    private final PasswordEncoder passwordEncoder;        
    private final DomicilioUsuarioMapper domicilioUsuarioMapper = new DomicilioUsuarioMapper();
    private final UserMapper userMapper = new UserMapper();
    private final DomicilioUsuarioQueryService domicilioService;
    private final DomicilioEmpresaQueryService domicilioEmpresaService;
    private final ExpedienteClienteQueryService expedienteService;

    public ClienteService(ClienteRepository clienteRepository,
    					  UserRepository userRepository,
    					  EmpresaRepository empresaRepository,
    					  DomicilioUsuarioRepository domicilioRepository,
    					  DomicilioEmpresaRepository domicilioEmpresaRepository,
    					  CatTipoDocumentoRepository tipoDocumentoRepository,
    					  ExpedienteClienteRepository expedienteClienteRepository,
    					  AuthorityRepository authorityRepository,
    					  PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;     
        this.userRepository = userRepository;
        this.domicilioRepository = domicilioRepository;
        this.domicilioEmpresaRepository = domicilioEmpresaRepository;
        this.tipoDocumentoRepository = tipoDocumentoRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder; 
        this.empresaRepository = empresaRepository;
        this.expedienteClienteRepository = expedienteClienteRepository;
        this.expedienteService = new ExpedienteClienteQueryService(this.expedienteClienteRepository);
        this.domicilioService = new DomicilioUsuarioQueryService(this.domicilioRepository);
        this.domicilioEmpresaService = new DomicilioEmpresaQueryService(this.domicilioEmpresaRepository);
    }

    /**
     * Save a cliente.
     *
     * @param cliente the entity to save.
     * @return the persisted entity.
     */
    public Cliente save(Cliente cliente) {
        log.debug("Request to save Cliente : {}", cliente);        
        return clienteRepository.save(cliente);
    }
    
    
    /**
     * Save a clienteDTO.
     *
     * @param cliente the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public Cliente save(ClienteDTO cliente) {
        log.debug("Request to save ClienteDTO : {}", cliente);      
        
        Cliente nuevoCliente = this.clienteFromDTO(cliente, true);                
        User usuario = this.usuarioCliente(cliente);
        DomicilioUsuario domicilioUsuario = this.domicilioUsuario(cliente);   
        Empresa empresa = this.empresaCliente(cliente, true);
        DomicilioEmpresa domicilioEmpresa = this.domicilioEmpresa(cliente);
        
        // Guardar usuario
        this.userRepository.saveAndFlush(usuario);
        
        // Guardar dirección del usuario
        domicilioUsuario.setUser(usuario);
        this.domicilioRepository.saveAndFlush(domicilioUsuario);
        
        nuevoCliente.setUsuario(usuario);
 
        if(empresa != null) {
        	// Guardar dirección Empresa
        	domicilioEmpresa.setUser(usuario);
            this.domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);
            
            // Guardar empresa
            empresa.setDomicilio(domicilioEmpresa);
            this.empresaRepository.saveAndFlush(empresa);
            
            nuevoCliente.setEmpresa(empresa);
        }
        
        // Expediente cliente
        nuevoCliente.setExpedientes(this.expedienteCliente(cliente, true, nuevoCliente));
                
        return clienteRepository.save(nuevoCliente);
    }

    /**
     * Get all the clientes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Cliente> findAll(Pageable pageable) {
        log.debug("Request to get all Clientes");
        return clienteRepository.findAll(pageable);
    }


    /**
     * Get one cliente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Cliente> findOne(Long id) {
        log.debug("Request to get Cliente : {}", id);
        return clienteRepository.findById(id);
    }

    /**
     * Delete the cliente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Cliente : {}", id);
        clienteRepository.deleteById(id);
    }
    
    
    /**
     * Get one clienteDTO by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public ClienteDTO findClienteDTO(Long id) {
        log.debug("Request to get Cliente : {}", id);
        ClienteDTO clienteDTO = new ClienteDTO();        
        Optional<Cliente> cliente = this.clienteRepository.findById(id);        
        if(cliente.isPresent()) {
        	clienteDTO.setId(id);
        	clienteDTO.setDatosGeneralesCliente(this.datosGeneralesCliente(cliente.get()));
        	clienteDTO.setDatosDireccionCliente(this.datosDireccionCliente(cliente.get(), false));        	
        	if(cliente.get().getTipoCliente().getId() != TipoEmpresaEnum.FISICA.getTipo()) {
        		clienteDTO.setDatosEmpresaCliente(this.datosEmpresaCliente(cliente.get()));
        		clienteDTO.setDatosDireccionEmpresa(this.datosDireccionCliente(cliente.get(), true));	
        	}
        }
                
        return clienteDTO; 
    }
    
    
    private Cliente clienteFromDTO(ClienteDTO cliente, boolean alta) {
    	Cliente nuevoCliente = new Cliente();
    	nuevoCliente.setId(cliente.getDatosGeneralesCliente().getId());
    	nuevoCliente.setNoIdentificacion(cliente.getDatosGeneralesCliente().getNoIdentificacion());
    	nuevoCliente.setIngresos(cliente.getDatosGeneralesCliente().getIngresos());
    	nuevoCliente.setEstimacionOperacion(cliente.getDatosGeneralesCliente().getEstimacionOperacion());
    	nuevoCliente.setTelefono(cliente.getDatosGeneralesCliente().getTelefono());
    	if (alta) {
    		nuevoCliente.setFechaAlta(LocalDate.now());
    		nuevoCliente.setUsuarioAlta(cliente.getDatosGeneralesCliente().getUsuarioAct());
    	}
    	
    	nuevoCliente.setUsuarioAct(cliente.getDatosGeneralesCliente().getUsuarioAct());
    	nuevoCliente.setTipoCliente(cliente.getDatosGeneralesCliente().getTipoCliente());
    	nuevoCliente.setTipoIdentificacion(cliente.getDatosGeneralesCliente().getTipoIdentificacion());
    	nuevoCliente.setSector(cliente.getDatosGeneralesCliente().getSector());
    	nuevoCliente.setMoneda(cliente.getDatosGeneralesCliente().getMoneda());
    	return nuevoCliente;
    }
    
    private User usuarioCliente(ClienteDTO cliente) {
    	UserDTO userDTO = cliente.getDatosGeneralesCliente().getUsuario();    	
    	User user = new User();    	
        user.setLogin(userDTO.getLogin().toLowerCase());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setmLastName(userDTO.getmLastName());
        user.setFechaNacimiento(userDTO.getFechaNacimiento());
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail().toLowerCase());
        }
        user.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE);
        } else {
            user.setLangKey(userDTO.getLangKey());
        }
        String encryptedPassword = this.passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(true);
        Set<Authority> authorities = new HashSet<>();
        this.authorityRepository.findById(AuthoritiesConstants.CLIENTE).ifPresent(authorities::add);
        user.setAuthorities(authorities);                             
    	return user;
    }
    
    
    private DomicilioUsuario domicilioUsuario(ClienteDTO cliente) {
    	return domicilioUsuarioMapper.domicilioUsuarioDTOToDomicilioUsuario(cliente.getDatosDireccionCliente().getDomicilioUsuario());
    }
    
    
    private DomicilioEmpresa domicilioEmpresa(ClienteDTO cliente) {
    	return domicilioUsuarioMapper.domicilioUsuarioDTOToDomicilioEmpresa(cliente.getDatosDireccionEmpresa().getDomicilioUsuario());
    }
    
    private Empresa empresaCliente(ClienteDTO cliente, boolean alta) {
    	
    	if ( cliente.getDatosGeneralesCliente().getTipoCliente().getId() == TipoEmpresaEnum.FISICA.getTipo()) {
    		return null;
    	}
    	
    	Empresa empresa = new Empresa();
    	DomicilioEmpresa domicilioUsuario = domicilioUsuarioMapper.domicilioUsuarioDTOToDomicilioEmpresa(cliente.getDatosDireccionEmpresa().getDomicilioUsuario());
        empresa.setFideicomiso(cliente.getDatosGeneralesCliente().getTipoCliente().getId() == TipoEmpresaEnum.FIDEICOMISO.getTipo());
        empresa.setTipoIdentificacion(cliente.getDatosEmpresaCliente().getTipoIdentificacion());
        empresa.setNoIdentificacion(cliente.getDatosEmpresaCliente().getNoIdentificacion());        
        empresa.setRazonSocial(cliente.getDatosEmpresaCliente().getRazonSocial());
        empresa.setRfc(cliente.getDatosEmpresaCliente().getRfc());
        empresa.setTelefono(cliente.getDatosEmpresaCliente().getTelefono());
        empresa.setDomicilio(domicilioUsuario);        
        if (alta) {
        	empresa.setFechaAlta(LocalDate.now());
        	empresa.setUsuarioAlta(cliente.getDatosGeneralesCliente().getUsuarioAct());	
        }        
        empresa.setUsuarioAct(cliente.getDatosGeneralesCliente().getUsuarioAct());
    	return empresa;
    }
    
    
    private ExpedienteCliente comprobanteIdentificacionCliente(ClienteDTO cliente, boolean alta, Cliente nuevoCliente) {
        ExpedienteCliente comprobante = new ExpedienteCliente();        
        comprobante.setDescripcion(cliente.getDatosGeneralesCliente().getDescripcionIdentificacion());
        comprobante.setDocumento(cliente.getDatosGeneralesCliente().getComprobanteIdentificacion());
        comprobante.setDocumentoContentType(cliente.getDatosGeneralesCliente().getIdentificacionContentType());
        comprobante.setEmpresarial(cliente.getDatosGeneralesCliente().getTipoCliente().getId() != TipoEmpresaEnum.FISICA.getTipo());
        if (alta) {
        	comprobante.setFechaAlta(LocalDate.now());
        	comprobante.setUsuarioAlta(cliente.getDatosGeneralesCliente().getUsuarioAct());
        }
        comprobante.setUsuarioAct(cliente.getDatosGeneralesCliente().getUsuarioAct());        
        comprobante.setTipoDocumento(this.getTipoDocumentoById(TipoDocumentoEnum.IdentificacionOficial.getTipo()));
        comprobante.setCliente(nuevoCliente);
        return comprobante;
    }
    
    
    private ExpedienteCliente comprobanteIngresosCliente(ClienteDTO cliente, boolean alta, Cliente nuevoCliente) {
        ExpedienteCliente comprobante = new ExpedienteCliente();        
        comprobante.setDescripcion(cliente.getDatosGeneralesCliente().getDescripcionIngresos());
        comprobante.setDocumento(cliente.getDatosGeneralesCliente().getComprobanteIngresos());
        comprobante.setDocumentoContentType(cliente.getDatosGeneralesCliente().getIngresosContentType());
        comprobante.setEmpresarial(cliente.getDatosGeneralesCliente().getTipoCliente().getId() != TipoEmpresaEnum.FISICA.getTipo());
        if (alta) {
        	comprobante.setFechaAlta(LocalDate.now());
        	comprobante.setUsuarioAlta(cliente.getDatosGeneralesCliente().getUsuarioAct());
        }
        comprobante.setUsuarioAct(cliente.getDatosGeneralesCliente().getUsuarioAct());        
        comprobante.setTipoDocumento(this.getTipoDocumentoById(TipoDocumentoEnum.ComprobanteIngresos.getTipo()));
        comprobante.setCliente(nuevoCliente);
        return comprobante;
    }
    
    private ExpedienteCliente comprobanteDomicilioCliente(ClienteDTO cliente, boolean alta, Cliente nuevoCliente) {
        ExpedienteCliente comprobante = new ExpedienteCliente();        
        comprobante.setDescripcion(cliente.getDatosDireccionCliente().getDescripcionDomicilio());
        comprobante.setDocumento(cliente.getDatosDireccionCliente().getComprobanteDomicilio());
        comprobante.setDocumentoContentType(cliente.getDatosDireccionCliente().getDomicilioContentType());
        comprobante.setEmpresarial(cliente.getDatosGeneralesCliente().getTipoCliente().getId() != TipoEmpresaEnum.FISICA.getTipo());
        if (alta) {
        	comprobante.setFechaAlta(LocalDate.now());
        	comprobante.setUsuarioAlta(cliente.getDatosGeneralesCliente().getUsuarioAct());
        }
        comprobante.setUsuarioAct(cliente.getDatosGeneralesCliente().getUsuarioAct());        
        comprobante.setTipoDocumento(this.getTipoDocumentoById(TipoDocumentoEnum.ComprobanteDomicilio.getTipo()));
        comprobante.setCliente(nuevoCliente);
        return comprobante;
    }
    
    
    private ExpedienteCliente comprobanteDomicilioEmpresa(ClienteDTO cliente, boolean alta, Cliente nuevoCliente) {
        ExpedienteCliente comprobante = new ExpedienteCliente();        
        comprobante.setDescripcion(cliente.getDatosDireccionEmpresa().getDescripcionDomicilio());
        comprobante.setDocumento(cliente.getDatosDireccionEmpresa().getComprobanteDomicilio());
        comprobante.setDocumentoContentType(cliente.getDatosDireccionEmpresa().getDomicilioContentType());
        comprobante.setEmpresarial(cliente.getDatosGeneralesCliente().getTipoCliente().getId() != TipoEmpresaEnum.FISICA.getTipo());
        if (alta) {
        	comprobante.setFechaAlta(LocalDate.now());
        	comprobante.setUsuarioAlta(cliente.getDatosGeneralesCliente().getUsuarioAct());
        }
        comprobante.setUsuarioAct(cliente.getDatosGeneralesCliente().getUsuarioAct());        
        comprobante.setTipoDocumento(this.getTipoDocumentoById(TipoDocumentoEnum.ComprobanteDomicilio.getTipo()));
        comprobante.setCliente(nuevoCliente);
        return comprobante;
    }
    
    private ExpedienteCliente comprobanteIdentificacionEmpresa(ClienteDTO cliente, boolean alta, Cliente nuevoCliente) {
        ExpedienteCliente comprobante = new ExpedienteCliente();        
        comprobante.setDescripcion(cliente.getDatosEmpresaCliente().getDescripcionIdentificacion());
        comprobante.setDocumento(cliente.getDatosEmpresaCliente().getComprobanteIdentificacion());
        comprobante.setDocumentoContentType(cliente.getDatosEmpresaCliente().getIdentificacionContentType());
        comprobante.setEmpresarial(cliente.getDatosGeneralesCliente().getTipoCliente().getId() != TipoEmpresaEnum.FISICA.getTipo());
        if (alta) {
        	comprobante.setFechaAlta(LocalDate.now());
        	comprobante.setUsuarioAlta(cliente.getDatosGeneralesCliente().getUsuarioAct());
        }
        comprobante.setUsuarioAct(cliente.getDatosGeneralesCliente().getUsuarioAct());        
        comprobante.setTipoDocumento(this.getTipoDocumentoById(TipoDocumentoEnum.IdentificacionOficial.getTipo()));
        comprobante.setCliente(nuevoCliente);
        return comprobante;
    }
    
    
    private ExpedienteCliente firmaElectronicaEmpresa(ClienteDTO cliente, boolean alta, Cliente nuevoCliente) {
        ExpedienteCliente comprobante = new ExpedienteCliente();        
        comprobante.setDescripcion(cliente.getDatosEmpresaCliente().getDescripcionFirma());
        comprobante.setDocumento(cliente.getDatosEmpresaCliente().getComprobanteFirma());
        comprobante.setDocumentoContentType(cliente.getDatosEmpresaCliente().getFirmaContentType());
        comprobante.setEmpresarial(cliente.getDatosGeneralesCliente().getTipoCliente().getId() != TipoEmpresaEnum.FISICA.getTipo());
        if (alta) {
        	comprobante.setFechaAlta(LocalDate.now());
        	comprobante.setUsuarioAlta(cliente.getDatosGeneralesCliente().getUsuarioAct());
        }
        comprobante.setUsuarioAct(cliente.getDatosGeneralesCliente().getUsuarioAct());        
        comprobante.setTipoDocumento(this.getTipoDocumentoById(TipoDocumentoEnum.FirmaElectronica.getTipo()));
        comprobante.setCliente(nuevoCliente);
        return comprobante;
    }
    
    
    private Set<ExpedienteCliente> expedienteCliente(ClienteDTO cliente, boolean alta, Cliente nuevoCliente) {    	
    	Set<ExpedienteCliente> expediente = new HashSet<ExpedienteCliente>();
    	
    	// Comprobante identificación oficial
    	expediente.add(this.comprobanteIdentificacionCliente(cliente, alta, nuevoCliente));
    	
    	// Comprobante ingresos cliente
    	expediente.add(this.comprobanteIngresosCliente(cliente, alta, nuevoCliente));
    	
    	// Comprobante domicilio cliente
    	expediente.add(this.comprobanteDomicilioCliente(cliente, alta, nuevoCliente));
    	
    	
    	if( cliente.getDatosGeneralesCliente().getTipoCliente().getId() != TipoEmpresaEnum.FISICA.getTipo()) {
    		
    		// Comprobante identificación oficial empresa
        	expediente.add(this.comprobanteIdentificacionEmpresa(cliente, alta, nuevoCliente));
        	
    		// Comprobante domicilio empresa
        	expediente.add(this.comprobanteDomicilioEmpresa(cliente, alta, nuevoCliente));
        	
        	// Firma electrónica empresa
        	expediente.add(this.firmaElectronicaEmpresa(cliente, alta, nuevoCliente));
    	}
    	
    	return expediente;
    }
    
    
    private CatTipoDocumento getTipoDocumentoById(Long id) {
    	return this.tipoDocumentoRepository.findById(id).get();
    }
    
    
    private DatosGeneralesCliente datosGeneralesCliente(Cliente cliente) {  	
    	DatosGeneralesCliente datosGenerales = new DatosGeneralesCliente();    	
    	datosGenerales.setId(cliente.getId());
    	datosGenerales.setIngresos(cliente.getIngresos());
    	datosGenerales.setEstimacionOperacion(cliente.getEstimacionOperacion());
    	datosGenerales.setFechaAlta(cliente.getFechaAlta());
    	datosGenerales.setFechaAct(cliente.getFechaAct());
    	datosGenerales.setMoneda(cliente.getMoneda());
    	datosGenerales.setNoIdentificacion(cliente.getNoIdentificacion());
    	datosGenerales.setSector(cliente.getSector());
    	datosGenerales.setTelefono(cliente.getTelefono());
    	datosGenerales.setTipoCliente(cliente.getTipoCliente());
    	datosGenerales.setTipoIdentificacion(cliente.getTipoIdentificacion());
    	datosGenerales.setUsuarioAct(cliente.getUsuarioAct());
    	
    	Optional<User> usuario = this.userRepository.findById(cliente.getUsuario().getId());    	
    	if(usuario.isPresent()) {
    		datosGenerales.setUsuario(userMapper.userToUserDTO(usuario.get()));	
    	}
    	
    	ExpedienteClienteCriteria criteria = this.criteriaExpediente(cliente.getId(), TipoDocumentoEnum.IdentificacionOficial.getTipo(), false);    	
    	List<ExpedienteCliente> expediente = this.expedienteService.findByCriteria(criteria);
    	if(!expediente.isEmpty()) {
    		datosGenerales.setComprobanteIdentificacion(expediente.get(0).getDocumento());
    		datosGenerales.setIdentificacionContentType(expediente.get(0).getDocumentoContentType());
    		datosGenerales.setDescripcionIdentificacion(expediente.get(0).getDescripcion());
    	}
    	
    	criteria = this.criteriaExpediente(cliente.getId(), TipoDocumentoEnum.ComprobanteIngresos.getTipo(), false);
    	expediente = this.expedienteService.findByCriteria(criteria);
    	if(!expediente.isEmpty()) {
    		datosGenerales.setComprobanteIngresos(expediente.get(0).getDocumento());
    		datosGenerales.setIngresosContentType(expediente.get(0).getDocumentoContentType());
    		datosGenerales.setDescripcionIngresos(expediente.get(0).getDescripcion());
    	}
    	
    	return datosGenerales;
    }
    
    
    private DatosEmpresaCliente datosEmpresaCliente(Cliente cliente) {  	
    	DatosEmpresaCliente datosGenerales = new DatosEmpresaCliente();
    	datosGenerales.setId(cliente.getEmpresa().getId());
    	datosGenerales.setFechaAct(cliente.getEmpresa().getFechaAct());
    	datosGenerales.setFechaAlta(cliente.getEmpresa().getFechaAlta());
    	datosGenerales.setFideicomiso(cliente.getEmpresa().isFideicomiso());
    	datosGenerales.setNoIdentificacion(cliente.getEmpresa().getNoIdentificacion());
    	datosGenerales.setRazonSocial(cliente.getEmpresa().getRazonSocial());
    	datosGenerales.setRfc(cliente.getEmpresa().getRfc());
    	datosGenerales.setTelefono(cliente.getEmpresa().getTelefono());
    	datosGenerales.setTipoIdentificacion(cliente.getEmpresa().getTipoIdentificacion());    	
    	
    	ExpedienteClienteCriteria criteria = this.criteriaExpediente(cliente.getId(), TipoDocumentoEnum.IdentificacionOficial.getTipo(), true);    	
    	List<ExpedienteCliente> expediente = this.expedienteService.findByCriteria(criteria);
    	if(!expediente.isEmpty()) {
    		datosGenerales.setComprobanteIdentificacion(expediente.get(0).getDocumento());
    		datosGenerales.setIdentificacionContentType(expediente.get(0).getDocumentoContentType());
    		datosGenerales.setDescripcionIdentificacion(expediente.get(0).getDescripcion());
    	}
    	
    	criteria = this.criteriaExpediente(cliente.getId(), TipoDocumentoEnum.FirmaElectronica.getTipo(), true);
    	expediente = this.expedienteService.findByCriteria(criteria);
    	if(!expediente.isEmpty()) {
    		datosGenerales.setComprobanteFirma(expediente.get(0).getDocumento());
    		datosGenerales.setFirmaContentType(expediente.get(0).getDocumentoContentType());
    		datosGenerales.setDescripcionFirma(expediente.get(0).getDescripcion());
    	}
    	
    	return datosGenerales;
    }
    
    
    private DatosDireccionCliente datosDireccionCliente(Cliente cliente, boolean empresa) {    	
    	DatosDireccionCliente datosDireccion = new DatosDireccionCliente();    	
    	if(empresa) {
    		DomicilioEmpresaCriteria criteriaDomicilio = this.domicilioEmpresaCriteria(cliente.getUsuario().getId());
        	List<DomicilioEmpresa> domicilioEmpresa = this.domicilioEmpresaService.findByCriteria(criteriaDomicilio);
        	if(!domicilioEmpresa.isEmpty()) {
        		datosDireccion.setId(domicilioEmpresa.get(0).getId());        	
    			datosDireccion.setDomicilioUsuario(this.domicilioUsuarioMapper.DomicilioEmpresaToDomicilioUsuarioDTO(domicilioEmpresa.get(0)));
        	}
    	} else {
    		DomicilioUsuarioCriteria criteriaDomicilio = this.domicilioCriteria(cliente.getUsuario().getId());    	    	
        	List<DomicilioUsuario> domicilioUsuario = this.domicilioService.findByCriteria(criteriaDomicilio);
        	if(!domicilioUsuario.isEmpty()) {
        		datosDireccion.setId(domicilioUsuario.get(0).getId());
    			datosDireccion.setDomicilioUsuario(this.domicilioUsuarioMapper.DomicilioUsuarioToDomicilioUsuarioDTO(domicilioUsuario.get(0)));	
        	}
    	}    	
    	
    	ExpedienteClienteCriteria criteriaExp = this.criteriaExpediente(cliente.getId(), TipoDocumentoEnum.ComprobanteDomicilio.getTipo(), empresa);    	    	
    	List<ExpedienteCliente> expediente = this.expedienteService.findByCriteria(criteriaExp);
    	if(!expediente.isEmpty()) {
    		datosDireccion.setComprobanteDomicilio(expediente.get(0).getDocumento());
    		datosDireccion.setDomicilioContentType(expediente.get(0).getDocumentoContentType());
    		datosDireccion.setDescripcionDomicilio(expediente.get(0).getDescripcion());
    	}
    	
    	return datosDireccion;
    }
    
    
    private ExpedienteClienteCriteria criteriaExpediente(Long idCliente, Long idDocumento, boolean empresa) {
    	ExpedienteClienteCriteria criteria = new ExpedienteClienteCriteria();
    	
    	LongFilter clienteId = new LongFilter();
    	clienteId.setEquals(idCliente);
    	criteria.setClienteId(clienteId);    	
    	
    	LongFilter comprobanteFilterId = new LongFilter();
    	comprobanteFilterId.setEquals(idDocumento);    
    	
    	BooleanFilter empresaFlag = new BooleanFilter();
    	empresaFlag.setEquals(empresa);
    	
    	criteria.setTipoDocumentoId(comprobanteFilterId);    	
    	return criteria;
    }
    
    
    private DomicilioUsuarioCriteria domicilioCriteria(Long idUsuario) {
    	LongFilter usuarioId = new LongFilter();
    	usuarioId.setEquals(idUsuario);    	    
    	DomicilioUsuarioCriteria domicilioCriteria = new DomicilioUsuarioCriteria();
    	domicilioCriteria.setUserId(usuarioId);
    	return domicilioCriteria;
    }
    
    
    private DomicilioEmpresaCriteria domicilioEmpresaCriteria(Long idUsuario) {
    	LongFilter usuarioId = new LongFilter();
    	usuarioId.setEquals(idUsuario);    	    
    	DomicilioEmpresaCriteria domicilioCriteria = new DomicilioEmpresaCriteria();
    	domicilioCriteria.setUserId(usuarioId);
    	return domicilioCriteria;
    }
    
}
