package com.resamx.web.rest;

import com.resamx.ResaMxWebApp;
import com.resamx.domain.Cliente;
import com.resamx.domain.User;
import com.resamx.domain.Empresa;
import com.resamx.domain.CatTipoEmpresa;
import com.resamx.domain.CatIdentificacion;
import com.resamx.domain.CatSector;
import com.resamx.domain.CatMoneda;
import com.resamx.domain.ExpedienteCliente;
import com.resamx.repository.ClienteRepository;
import com.resamx.service.ClienteService;
import com.resamx.service.dto.ClienteCriteria;
import com.resamx.service.ClienteQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ClienteResource} REST controller.
 */
@SpringBootTest(classes = ResaMxWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ClienteResourceIT {

    private static final String DEFAULT_NO_IDENTIFICACION = "AAAAAAAAAA";
    private static final String UPDATED_NO_IDENTIFICACION = "BBBBBBBBBB";

    private static final Double DEFAULT_INGRESOS = 1D;
    private static final Double UPDATED_INGRESOS = 2D;
    private static final Double SMALLER_INGRESOS = 1D - 1D;

    private static final Double DEFAULT_ESTIMACION_OPERACION = 1D;
    private static final Double UPDATED_ESTIMACION_OPERACION = 2D;
    private static final Double SMALLER_ESTIMACION_OPERACION = 1D - 1D;

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_ALTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ALTA = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_ALTA = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_FECHA_ACT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ACT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_ACT = LocalDate.ofEpochDay(-1L);

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteQueryService clienteQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClienteMockMvc;

    private Cliente cliente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cliente createEntity(EntityManager em) {
        Cliente cliente = new Cliente()
            .noIdentificacion(DEFAULT_NO_IDENTIFICACION)
            .ingresos(DEFAULT_INGRESOS)
            .estimacionOperacion(DEFAULT_ESTIMACION_OPERACION)
            .telefono(DEFAULT_TELEFONO)
            .fechaAlta(DEFAULT_FECHA_ALTA)
            .fechaAct(DEFAULT_FECHA_ACT);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        cliente.setUsuario(user);
        // Add required entity
        CatTipoEmpresa catTipoEmpresa;
        if (TestUtil.findAll(em, CatTipoEmpresa.class).isEmpty()) {
            catTipoEmpresa = CatTipoEmpresaResourceIT.createEntity(em);
            em.persist(catTipoEmpresa);
            em.flush();
        } else {
            catTipoEmpresa = TestUtil.findAll(em, CatTipoEmpresa.class).get(0);
        }
        cliente.setTipoCliente(catTipoEmpresa);
        // Add required entity
        CatIdentificacion catIdentificacion;
        if (TestUtil.findAll(em, CatIdentificacion.class).isEmpty()) {
            catIdentificacion = CatIdentificacionResourceIT.createEntity(em);
            em.persist(catIdentificacion);
            em.flush();
        } else {
            catIdentificacion = TestUtil.findAll(em, CatIdentificacion.class).get(0);
        }
        cliente.setTipoIdentificacion(catIdentificacion);
        // Add required entity
        CatSector catSector;
        if (TestUtil.findAll(em, CatSector.class).isEmpty()) {
            catSector = CatSectorResourceIT.createEntity(em);
            em.persist(catSector);
            em.flush();
        } else {
            catSector = TestUtil.findAll(em, CatSector.class).get(0);
        }
        cliente.setSector(catSector);
        // Add required entity
        CatMoneda catMoneda;
        if (TestUtil.findAll(em, CatMoneda.class).isEmpty()) {
            catMoneda = CatMonedaResourceIT.createEntity(em);
            em.persist(catMoneda);
            em.flush();
        } else {
            catMoneda = TestUtil.findAll(em, CatMoneda.class).get(0);
        }
        cliente.setMoneda(catMoneda);
        // Add required entity
        cliente.setUsuarioAlta(user);
        // Add required entity
        cliente.setUsuarioAct(user);
        // Add required entity
        ExpedienteCliente expedienteCliente;
        if (TestUtil.findAll(em, ExpedienteCliente.class).isEmpty()) {
            expedienteCliente = ExpedienteClienteResourceIT.createEntity(em);
            em.persist(expedienteCliente);
            em.flush();
        } else {
            expedienteCliente = TestUtil.findAll(em, ExpedienteCliente.class).get(0);
        }
        cliente.getExpedientes().add(expedienteCliente);
        return cliente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cliente createUpdatedEntity(EntityManager em) {
        Cliente cliente = new Cliente()
            .noIdentificacion(UPDATED_NO_IDENTIFICACION)
            .ingresos(UPDATED_INGRESOS)
            .estimacionOperacion(UPDATED_ESTIMACION_OPERACION)
            .telefono(UPDATED_TELEFONO)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaAct(UPDATED_FECHA_ACT);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        cliente.setUsuario(user);
        // Add required entity
        CatTipoEmpresa catTipoEmpresa;
        if (TestUtil.findAll(em, CatTipoEmpresa.class).isEmpty()) {
            catTipoEmpresa = CatTipoEmpresaResourceIT.createUpdatedEntity(em);
            em.persist(catTipoEmpresa);
            em.flush();
        } else {
            catTipoEmpresa = TestUtil.findAll(em, CatTipoEmpresa.class).get(0);
        }
        cliente.setTipoCliente(catTipoEmpresa);
        // Add required entity
        CatIdentificacion catIdentificacion;
        if (TestUtil.findAll(em, CatIdentificacion.class).isEmpty()) {
            catIdentificacion = CatIdentificacionResourceIT.createUpdatedEntity(em);
            em.persist(catIdentificacion);
            em.flush();
        } else {
            catIdentificacion = TestUtil.findAll(em, CatIdentificacion.class).get(0);
        }
        cliente.setTipoIdentificacion(catIdentificacion);
        // Add required entity
        CatSector catSector;
        if (TestUtil.findAll(em, CatSector.class).isEmpty()) {
            catSector = CatSectorResourceIT.createUpdatedEntity(em);
            em.persist(catSector);
            em.flush();
        } else {
            catSector = TestUtil.findAll(em, CatSector.class).get(0);
        }
        cliente.setSector(catSector);
        // Add required entity
        CatMoneda catMoneda;
        if (TestUtil.findAll(em, CatMoneda.class).isEmpty()) {
            catMoneda = CatMonedaResourceIT.createUpdatedEntity(em);
            em.persist(catMoneda);
            em.flush();
        } else {
            catMoneda = TestUtil.findAll(em, CatMoneda.class).get(0);
        }
        cliente.setMoneda(catMoneda);
        // Add required entity
        cliente.setUsuarioAlta(user);
        // Add required entity
        cliente.setUsuarioAct(user);
        // Add required entity
        ExpedienteCliente expedienteCliente;
        if (TestUtil.findAll(em, ExpedienteCliente.class).isEmpty()) {
            expedienteCliente = ExpedienteClienteResourceIT.createUpdatedEntity(em);
            em.persist(expedienteCliente);
            em.flush();
        } else {
            expedienteCliente = TestUtil.findAll(em, ExpedienteCliente.class).get(0);
        }
        cliente.getExpedientes().add(expedienteCliente);
        return cliente;
    }

    @BeforeEach
    public void initTest() {
        cliente = createEntity(em);
    }

    @Test
    @Transactional
    public void createCliente() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();
        // Create the Cliente
        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isCreated());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate + 1);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getNoIdentificacion()).isEqualTo(DEFAULT_NO_IDENTIFICACION);
        assertThat(testCliente.getIngresos()).isEqualTo(DEFAULT_INGRESOS);
        assertThat(testCliente.getEstimacionOperacion()).isEqualTo(DEFAULT_ESTIMACION_OPERACION);
        assertThat(testCliente.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testCliente.getFechaAlta()).isEqualTo(DEFAULT_FECHA_ALTA);
        assertThat(testCliente.getFechaAct()).isEqualTo(DEFAULT_FECHA_ACT);
    }

    @Test
    @Transactional
    public void createClienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();

        // Create the Cliente with an existing ID
        cliente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNoIdentificacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setNoIdentificacion(null);

        // Create the Cliente, which fails.


        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIngresosIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setIngresos(null);

        // Create the Cliente, which fails.


        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstimacionOperacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setEstimacionOperacion(null);

        // Create the Cliente, which fails.


        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaAltaIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setFechaAlta(null);

        // Create the Cliente, which fails.


        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaActIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setFechaAct(null);

        // Create the Cliente, which fails.


        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClientes() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList
        restClienteMockMvc.perform(get("/api/clientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].noIdentificacion").value(hasItem(DEFAULT_NO_IDENTIFICACION)))
            .andExpect(jsonPath("$.[*].ingresos").value(hasItem(DEFAULT_INGRESOS.doubleValue())))
            .andExpect(jsonPath("$.[*].estimacionOperacion").value(hasItem(DEFAULT_ESTIMACION_OPERACION.doubleValue())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())))
            .andExpect(jsonPath("$.[*].fechaAct").value(hasItem(DEFAULT_FECHA_ACT.toString())));
    }
    
    @Test
    @Transactional
    public void getCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get the cliente
        restClienteMockMvc.perform(get("/api/clientes/{id}", cliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cliente.getId().intValue()))
            .andExpect(jsonPath("$.noIdentificacion").value(DEFAULT_NO_IDENTIFICACION))
            .andExpect(jsonPath("$.ingresos").value(DEFAULT_INGRESOS.doubleValue()))
            .andExpect(jsonPath("$.estimacionOperacion").value(DEFAULT_ESTIMACION_OPERACION.doubleValue()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.fechaAlta").value(DEFAULT_FECHA_ALTA.toString()))
            .andExpect(jsonPath("$.fechaAct").value(DEFAULT_FECHA_ACT.toString()));
    }


    @Test
    @Transactional
    public void getClientesByIdFiltering() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        Long id = cliente.getId();

        defaultClienteShouldBeFound("id.equals=" + id);
        defaultClienteShouldNotBeFound("id.notEquals=" + id);

        defaultClienteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClienteShouldNotBeFound("id.greaterThan=" + id);

        defaultClienteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClienteShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllClientesByNoIdentificacionIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where noIdentificacion equals to DEFAULT_NO_IDENTIFICACION
        defaultClienteShouldBeFound("noIdentificacion.equals=" + DEFAULT_NO_IDENTIFICACION);

        // Get all the clienteList where noIdentificacion equals to UPDATED_NO_IDENTIFICACION
        defaultClienteShouldNotBeFound("noIdentificacion.equals=" + UPDATED_NO_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void getAllClientesByNoIdentificacionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where noIdentificacion not equals to DEFAULT_NO_IDENTIFICACION
        defaultClienteShouldNotBeFound("noIdentificacion.notEquals=" + DEFAULT_NO_IDENTIFICACION);

        // Get all the clienteList where noIdentificacion not equals to UPDATED_NO_IDENTIFICACION
        defaultClienteShouldBeFound("noIdentificacion.notEquals=" + UPDATED_NO_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void getAllClientesByNoIdentificacionIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where noIdentificacion in DEFAULT_NO_IDENTIFICACION or UPDATED_NO_IDENTIFICACION
        defaultClienteShouldBeFound("noIdentificacion.in=" + DEFAULT_NO_IDENTIFICACION + "," + UPDATED_NO_IDENTIFICACION);

        // Get all the clienteList where noIdentificacion equals to UPDATED_NO_IDENTIFICACION
        defaultClienteShouldNotBeFound("noIdentificacion.in=" + UPDATED_NO_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void getAllClientesByNoIdentificacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where noIdentificacion is not null
        defaultClienteShouldBeFound("noIdentificacion.specified=true");

        // Get all the clienteList where noIdentificacion is null
        defaultClienteShouldNotBeFound("noIdentificacion.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientesByNoIdentificacionContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where noIdentificacion contains DEFAULT_NO_IDENTIFICACION
        defaultClienteShouldBeFound("noIdentificacion.contains=" + DEFAULT_NO_IDENTIFICACION);

        // Get all the clienteList where noIdentificacion contains UPDATED_NO_IDENTIFICACION
        defaultClienteShouldNotBeFound("noIdentificacion.contains=" + UPDATED_NO_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void getAllClientesByNoIdentificacionNotContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where noIdentificacion does not contain DEFAULT_NO_IDENTIFICACION
        defaultClienteShouldNotBeFound("noIdentificacion.doesNotContain=" + DEFAULT_NO_IDENTIFICACION);

        // Get all the clienteList where noIdentificacion does not contain UPDATED_NO_IDENTIFICACION
        defaultClienteShouldBeFound("noIdentificacion.doesNotContain=" + UPDATED_NO_IDENTIFICACION);
    }


    @Test
    @Transactional
    public void getAllClientesByIngresosIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where ingresos equals to DEFAULT_INGRESOS
        defaultClienteShouldBeFound("ingresos.equals=" + DEFAULT_INGRESOS);

        // Get all the clienteList where ingresos equals to UPDATED_INGRESOS
        defaultClienteShouldNotBeFound("ingresos.equals=" + UPDATED_INGRESOS);
    }

    @Test
    @Transactional
    public void getAllClientesByIngresosIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where ingresos not equals to DEFAULT_INGRESOS
        defaultClienteShouldNotBeFound("ingresos.notEquals=" + DEFAULT_INGRESOS);

        // Get all the clienteList where ingresos not equals to UPDATED_INGRESOS
        defaultClienteShouldBeFound("ingresos.notEquals=" + UPDATED_INGRESOS);
    }

    @Test
    @Transactional
    public void getAllClientesByIngresosIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where ingresos in DEFAULT_INGRESOS or UPDATED_INGRESOS
        defaultClienteShouldBeFound("ingresos.in=" + DEFAULT_INGRESOS + "," + UPDATED_INGRESOS);

        // Get all the clienteList where ingresos equals to UPDATED_INGRESOS
        defaultClienteShouldNotBeFound("ingresos.in=" + UPDATED_INGRESOS);
    }

    @Test
    @Transactional
    public void getAllClientesByIngresosIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where ingresos is not null
        defaultClienteShouldBeFound("ingresos.specified=true");

        // Get all the clienteList where ingresos is null
        defaultClienteShouldNotBeFound("ingresos.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientesByIngresosIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where ingresos is greater than or equal to DEFAULT_INGRESOS
        defaultClienteShouldBeFound("ingresos.greaterThanOrEqual=" + DEFAULT_INGRESOS);

        // Get all the clienteList where ingresos is greater than or equal to UPDATED_INGRESOS
        defaultClienteShouldNotBeFound("ingresos.greaterThanOrEqual=" + UPDATED_INGRESOS);
    }

    @Test
    @Transactional
    public void getAllClientesByIngresosIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where ingresos is less than or equal to DEFAULT_INGRESOS
        defaultClienteShouldBeFound("ingresos.lessThanOrEqual=" + DEFAULT_INGRESOS);

        // Get all the clienteList where ingresos is less than or equal to SMALLER_INGRESOS
        defaultClienteShouldNotBeFound("ingresos.lessThanOrEqual=" + SMALLER_INGRESOS);
    }

    @Test
    @Transactional
    public void getAllClientesByIngresosIsLessThanSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where ingresos is less than DEFAULT_INGRESOS
        defaultClienteShouldNotBeFound("ingresos.lessThan=" + DEFAULT_INGRESOS);

        // Get all the clienteList where ingresos is less than UPDATED_INGRESOS
        defaultClienteShouldBeFound("ingresos.lessThan=" + UPDATED_INGRESOS);
    }

    @Test
    @Transactional
    public void getAllClientesByIngresosIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where ingresos is greater than DEFAULT_INGRESOS
        defaultClienteShouldNotBeFound("ingresos.greaterThan=" + DEFAULT_INGRESOS);

        // Get all the clienteList where ingresos is greater than SMALLER_INGRESOS
        defaultClienteShouldBeFound("ingresos.greaterThan=" + SMALLER_INGRESOS);
    }


    @Test
    @Transactional
    public void getAllClientesByEstimacionOperacionIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where estimacionOperacion equals to DEFAULT_ESTIMACION_OPERACION
        defaultClienteShouldBeFound("estimacionOperacion.equals=" + DEFAULT_ESTIMACION_OPERACION);

        // Get all the clienteList where estimacionOperacion equals to UPDATED_ESTIMACION_OPERACION
        defaultClienteShouldNotBeFound("estimacionOperacion.equals=" + UPDATED_ESTIMACION_OPERACION);
    }

    @Test
    @Transactional
    public void getAllClientesByEstimacionOperacionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where estimacionOperacion not equals to DEFAULT_ESTIMACION_OPERACION
        defaultClienteShouldNotBeFound("estimacionOperacion.notEquals=" + DEFAULT_ESTIMACION_OPERACION);

        // Get all the clienteList where estimacionOperacion not equals to UPDATED_ESTIMACION_OPERACION
        defaultClienteShouldBeFound("estimacionOperacion.notEquals=" + UPDATED_ESTIMACION_OPERACION);
    }

    @Test
    @Transactional
    public void getAllClientesByEstimacionOperacionIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where estimacionOperacion in DEFAULT_ESTIMACION_OPERACION or UPDATED_ESTIMACION_OPERACION
        defaultClienteShouldBeFound("estimacionOperacion.in=" + DEFAULT_ESTIMACION_OPERACION + "," + UPDATED_ESTIMACION_OPERACION);

        // Get all the clienteList where estimacionOperacion equals to UPDATED_ESTIMACION_OPERACION
        defaultClienteShouldNotBeFound("estimacionOperacion.in=" + UPDATED_ESTIMACION_OPERACION);
    }

    @Test
    @Transactional
    public void getAllClientesByEstimacionOperacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where estimacionOperacion is not null
        defaultClienteShouldBeFound("estimacionOperacion.specified=true");

        // Get all the clienteList where estimacionOperacion is null
        defaultClienteShouldNotBeFound("estimacionOperacion.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientesByEstimacionOperacionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where estimacionOperacion is greater than or equal to DEFAULT_ESTIMACION_OPERACION
        defaultClienteShouldBeFound("estimacionOperacion.greaterThanOrEqual=" + DEFAULT_ESTIMACION_OPERACION);

        // Get all the clienteList where estimacionOperacion is greater than or equal to UPDATED_ESTIMACION_OPERACION
        defaultClienteShouldNotBeFound("estimacionOperacion.greaterThanOrEqual=" + UPDATED_ESTIMACION_OPERACION);
    }

    @Test
    @Transactional
    public void getAllClientesByEstimacionOperacionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where estimacionOperacion is less than or equal to DEFAULT_ESTIMACION_OPERACION
        defaultClienteShouldBeFound("estimacionOperacion.lessThanOrEqual=" + DEFAULT_ESTIMACION_OPERACION);

        // Get all the clienteList where estimacionOperacion is less than or equal to SMALLER_ESTIMACION_OPERACION
        defaultClienteShouldNotBeFound("estimacionOperacion.lessThanOrEqual=" + SMALLER_ESTIMACION_OPERACION);
    }

    @Test
    @Transactional
    public void getAllClientesByEstimacionOperacionIsLessThanSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where estimacionOperacion is less than DEFAULT_ESTIMACION_OPERACION
        defaultClienteShouldNotBeFound("estimacionOperacion.lessThan=" + DEFAULT_ESTIMACION_OPERACION);

        // Get all the clienteList where estimacionOperacion is less than UPDATED_ESTIMACION_OPERACION
        defaultClienteShouldBeFound("estimacionOperacion.lessThan=" + UPDATED_ESTIMACION_OPERACION);
    }

    @Test
    @Transactional
    public void getAllClientesByEstimacionOperacionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where estimacionOperacion is greater than DEFAULT_ESTIMACION_OPERACION
        defaultClienteShouldNotBeFound("estimacionOperacion.greaterThan=" + DEFAULT_ESTIMACION_OPERACION);

        // Get all the clienteList where estimacionOperacion is greater than SMALLER_ESTIMACION_OPERACION
        defaultClienteShouldBeFound("estimacionOperacion.greaterThan=" + SMALLER_ESTIMACION_OPERACION);
    }


    @Test
    @Transactional
    public void getAllClientesByTelefonoIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where telefono equals to DEFAULT_TELEFONO
        defaultClienteShouldBeFound("telefono.equals=" + DEFAULT_TELEFONO);

        // Get all the clienteList where telefono equals to UPDATED_TELEFONO
        defaultClienteShouldNotBeFound("telefono.equals=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllClientesByTelefonoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where telefono not equals to DEFAULT_TELEFONO
        defaultClienteShouldNotBeFound("telefono.notEquals=" + DEFAULT_TELEFONO);

        // Get all the clienteList where telefono not equals to UPDATED_TELEFONO
        defaultClienteShouldBeFound("telefono.notEquals=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllClientesByTelefonoIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where telefono in DEFAULT_TELEFONO or UPDATED_TELEFONO
        defaultClienteShouldBeFound("telefono.in=" + DEFAULT_TELEFONO + "," + UPDATED_TELEFONO);

        // Get all the clienteList where telefono equals to UPDATED_TELEFONO
        defaultClienteShouldNotBeFound("telefono.in=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllClientesByTelefonoIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where telefono is not null
        defaultClienteShouldBeFound("telefono.specified=true");

        // Get all the clienteList where telefono is null
        defaultClienteShouldNotBeFound("telefono.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientesByTelefonoContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where telefono contains DEFAULT_TELEFONO
        defaultClienteShouldBeFound("telefono.contains=" + DEFAULT_TELEFONO);

        // Get all the clienteList where telefono contains UPDATED_TELEFONO
        defaultClienteShouldNotBeFound("telefono.contains=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllClientesByTelefonoNotContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where telefono does not contain DEFAULT_TELEFONO
        defaultClienteShouldNotBeFound("telefono.doesNotContain=" + DEFAULT_TELEFONO);

        // Get all the clienteList where telefono does not contain UPDATED_TELEFONO
        defaultClienteShouldBeFound("telefono.doesNotContain=" + UPDATED_TELEFONO);
    }


    @Test
    @Transactional
    public void getAllClientesByFechaAltaIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where fechaAlta equals to DEFAULT_FECHA_ALTA
        defaultClienteShouldBeFound("fechaAlta.equals=" + DEFAULT_FECHA_ALTA);

        // Get all the clienteList where fechaAlta equals to UPDATED_FECHA_ALTA
        defaultClienteShouldNotBeFound("fechaAlta.equals=" + UPDATED_FECHA_ALTA);
    }

    @Test
    @Transactional
    public void getAllClientesByFechaAltaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where fechaAlta not equals to DEFAULT_FECHA_ALTA
        defaultClienteShouldNotBeFound("fechaAlta.notEquals=" + DEFAULT_FECHA_ALTA);

        // Get all the clienteList where fechaAlta not equals to UPDATED_FECHA_ALTA
        defaultClienteShouldBeFound("fechaAlta.notEquals=" + UPDATED_FECHA_ALTA);
    }

    @Test
    @Transactional
    public void getAllClientesByFechaAltaIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where fechaAlta in DEFAULT_FECHA_ALTA or UPDATED_FECHA_ALTA
        defaultClienteShouldBeFound("fechaAlta.in=" + DEFAULT_FECHA_ALTA + "," + UPDATED_FECHA_ALTA);

        // Get all the clienteList where fechaAlta equals to UPDATED_FECHA_ALTA
        defaultClienteShouldNotBeFound("fechaAlta.in=" + UPDATED_FECHA_ALTA);
    }

    @Test
    @Transactional
    public void getAllClientesByFechaAltaIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where fechaAlta is not null
        defaultClienteShouldBeFound("fechaAlta.specified=true");

        // Get all the clienteList where fechaAlta is null
        defaultClienteShouldNotBeFound("fechaAlta.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientesByFechaAltaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where fechaAlta is greater than or equal to DEFAULT_FECHA_ALTA
        defaultClienteShouldBeFound("fechaAlta.greaterThanOrEqual=" + DEFAULT_FECHA_ALTA);

        // Get all the clienteList where fechaAlta is greater than or equal to UPDATED_FECHA_ALTA
        defaultClienteShouldNotBeFound("fechaAlta.greaterThanOrEqual=" + UPDATED_FECHA_ALTA);
    }

    @Test
    @Transactional
    public void getAllClientesByFechaAltaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where fechaAlta is less than or equal to DEFAULT_FECHA_ALTA
        defaultClienteShouldBeFound("fechaAlta.lessThanOrEqual=" + DEFAULT_FECHA_ALTA);

        // Get all the clienteList where fechaAlta is less than or equal to SMALLER_FECHA_ALTA
        defaultClienteShouldNotBeFound("fechaAlta.lessThanOrEqual=" + SMALLER_FECHA_ALTA);
    }

    @Test
    @Transactional
    public void getAllClientesByFechaAltaIsLessThanSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where fechaAlta is less than DEFAULT_FECHA_ALTA
        defaultClienteShouldNotBeFound("fechaAlta.lessThan=" + DEFAULT_FECHA_ALTA);

        // Get all the clienteList where fechaAlta is less than UPDATED_FECHA_ALTA
        defaultClienteShouldBeFound("fechaAlta.lessThan=" + UPDATED_FECHA_ALTA);
    }

    @Test
    @Transactional
    public void getAllClientesByFechaAltaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where fechaAlta is greater than DEFAULT_FECHA_ALTA
        defaultClienteShouldNotBeFound("fechaAlta.greaterThan=" + DEFAULT_FECHA_ALTA);

        // Get all the clienteList where fechaAlta is greater than SMALLER_FECHA_ALTA
        defaultClienteShouldBeFound("fechaAlta.greaterThan=" + SMALLER_FECHA_ALTA);
    }


    @Test
    @Transactional
    public void getAllClientesByFechaActIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where fechaAct equals to DEFAULT_FECHA_ACT
        defaultClienteShouldBeFound("fechaAct.equals=" + DEFAULT_FECHA_ACT);

        // Get all the clienteList where fechaAct equals to UPDATED_FECHA_ACT
        defaultClienteShouldNotBeFound("fechaAct.equals=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllClientesByFechaActIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where fechaAct not equals to DEFAULT_FECHA_ACT
        defaultClienteShouldNotBeFound("fechaAct.notEquals=" + DEFAULT_FECHA_ACT);

        // Get all the clienteList where fechaAct not equals to UPDATED_FECHA_ACT
        defaultClienteShouldBeFound("fechaAct.notEquals=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllClientesByFechaActIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where fechaAct in DEFAULT_FECHA_ACT or UPDATED_FECHA_ACT
        defaultClienteShouldBeFound("fechaAct.in=" + DEFAULT_FECHA_ACT + "," + UPDATED_FECHA_ACT);

        // Get all the clienteList where fechaAct equals to UPDATED_FECHA_ACT
        defaultClienteShouldNotBeFound("fechaAct.in=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllClientesByFechaActIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where fechaAct is not null
        defaultClienteShouldBeFound("fechaAct.specified=true");

        // Get all the clienteList where fechaAct is null
        defaultClienteShouldNotBeFound("fechaAct.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientesByFechaActIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where fechaAct is greater than or equal to DEFAULT_FECHA_ACT
        defaultClienteShouldBeFound("fechaAct.greaterThanOrEqual=" + DEFAULT_FECHA_ACT);

        // Get all the clienteList where fechaAct is greater than or equal to UPDATED_FECHA_ACT
        defaultClienteShouldNotBeFound("fechaAct.greaterThanOrEqual=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllClientesByFechaActIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where fechaAct is less than or equal to DEFAULT_FECHA_ACT
        defaultClienteShouldBeFound("fechaAct.lessThanOrEqual=" + DEFAULT_FECHA_ACT);

        // Get all the clienteList where fechaAct is less than or equal to SMALLER_FECHA_ACT
        defaultClienteShouldNotBeFound("fechaAct.lessThanOrEqual=" + SMALLER_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllClientesByFechaActIsLessThanSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where fechaAct is less than DEFAULT_FECHA_ACT
        defaultClienteShouldNotBeFound("fechaAct.lessThan=" + DEFAULT_FECHA_ACT);

        // Get all the clienteList where fechaAct is less than UPDATED_FECHA_ACT
        defaultClienteShouldBeFound("fechaAct.lessThan=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllClientesByFechaActIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where fechaAct is greater than DEFAULT_FECHA_ACT
        defaultClienteShouldNotBeFound("fechaAct.greaterThan=" + DEFAULT_FECHA_ACT);

        // Get all the clienteList where fechaAct is greater than SMALLER_FECHA_ACT
        defaultClienteShouldBeFound("fechaAct.greaterThan=" + SMALLER_FECHA_ACT);
    }


    @Test
    @Transactional
    public void getAllClientesByUsuarioIsEqualToSomething() throws Exception {
        // Get already existing entity
        User usuario = cliente.getUsuario();
        clienteRepository.saveAndFlush(cliente);
        Long usuarioId = usuario.getId();

        // Get all the clienteList where usuario equals to usuarioId
        defaultClienteShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the clienteList where usuario equals to usuarioId + 1
        defaultClienteShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }


    @Test
    @Transactional
    public void getAllClientesByEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);
        Empresa empresa = EmpresaResourceIT.createEntity(em);
        em.persist(empresa);
        em.flush();
        cliente.setEmpresa(empresa);
        clienteRepository.saveAndFlush(cliente);
        Long empresaId = empresa.getId();

        // Get all the clienteList where empresa equals to empresaId
        defaultClienteShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the clienteList where empresa equals to empresaId + 1
        defaultClienteShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }


    @Test
    @Transactional
    public void getAllClientesByTipoClienteIsEqualToSomething() throws Exception {
        // Get already existing entity
        CatTipoEmpresa tipoCliente = cliente.getTipoCliente();
        clienteRepository.saveAndFlush(cliente);
        Long tipoClienteId = tipoCliente.getId();

        // Get all the clienteList where tipoCliente equals to tipoClienteId
        defaultClienteShouldBeFound("tipoClienteId.equals=" + tipoClienteId);

        // Get all the clienteList where tipoCliente equals to tipoClienteId + 1
        defaultClienteShouldNotBeFound("tipoClienteId.equals=" + (tipoClienteId + 1));
    }


    @Test
    @Transactional
    public void getAllClientesByTipoIdentificacionIsEqualToSomething() throws Exception {
        // Get already existing entity
        CatIdentificacion tipoIdentificacion = cliente.getTipoIdentificacion();
        clienteRepository.saveAndFlush(cliente);
        Long tipoIdentificacionId = tipoIdentificacion.getId();

        // Get all the clienteList where tipoIdentificacion equals to tipoIdentificacionId
        defaultClienteShouldBeFound("tipoIdentificacionId.equals=" + tipoIdentificacionId);

        // Get all the clienteList where tipoIdentificacion equals to tipoIdentificacionId + 1
        defaultClienteShouldNotBeFound("tipoIdentificacionId.equals=" + (tipoIdentificacionId + 1));
    }


    @Test
    @Transactional
    public void getAllClientesBySectorIsEqualToSomething() throws Exception {
        // Get already existing entity
        CatSector sector = cliente.getSector();
        clienteRepository.saveAndFlush(cliente);
        String sectorId = sector.getId();

        // Get all the clienteList where sector equals to sectorId
        defaultClienteShouldBeFound("sectorId.equals=" + sectorId);

        // Get all the clienteList where sector equals to sectorId + 1
        defaultClienteShouldNotBeFound("sectorId.equals=" + (sectorId + 1));
    }


    @Test
    @Transactional
    public void getAllClientesByMonedaIsEqualToSomething() throws Exception {
        // Get already existing entity
        CatMoneda moneda = cliente.getMoneda();
        clienteRepository.saveAndFlush(cliente);
        String monedaId = moneda.getId();

        // Get all the clienteList where moneda equals to monedaId
        defaultClienteShouldBeFound("monedaId.equals=" + monedaId);

        // Get all the clienteList where moneda equals to monedaId + 1
        defaultClienteShouldNotBeFound("monedaId.equals=" + (monedaId + 1));
    }


    @Test
    @Transactional
    public void getAllClientesByUsuarioAltaIsEqualToSomething() throws Exception {
        // Get already existing entity
        User usuarioAlta = cliente.getUsuarioAlta();
        clienteRepository.saveAndFlush(cliente);
        Long usuarioAltaId = usuarioAlta.getId();

        // Get all the clienteList where usuarioAlta equals to usuarioAltaId
        defaultClienteShouldBeFound("usuarioAltaId.equals=" + usuarioAltaId);

        // Get all the clienteList where usuarioAlta equals to usuarioAltaId + 1
        defaultClienteShouldNotBeFound("usuarioAltaId.equals=" + (usuarioAltaId + 1));
    }


    @Test
    @Transactional
    public void getAllClientesByUsuarioActIsEqualToSomething() throws Exception {
        // Get already existing entity
        User usuarioAct = cliente.getUsuarioAct();
        clienteRepository.saveAndFlush(cliente);
        Long usuarioActId = usuarioAct.getId();

        // Get all the clienteList where usuarioAct equals to usuarioActId
        defaultClienteShouldBeFound("usuarioActId.equals=" + usuarioActId);

        // Get all the clienteList where usuarioAct equals to usuarioActId + 1
        defaultClienteShouldNotBeFound("usuarioActId.equals=" + (usuarioActId + 1));
    }


    @Test
    @Transactional
    public void getAllClientesByExpedienteIsEqualToSomething() throws Exception {
        // Get already existing entity
        ExpedienteCliente expediente = cliente.getExpedientes().iterator().next();
        clienteRepository.saveAndFlush(cliente);
        Long expedienteId = expediente.getId();

        // Get all the clienteList where expediente equals to expedienteId
        defaultClienteShouldBeFound("expedienteId.equals=" + expedienteId);

        // Get all the clienteList where expediente equals to expedienteId + 1
        defaultClienteShouldNotBeFound("expedienteId.equals=" + (expedienteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClienteShouldBeFound(String filter) throws Exception {
        restClienteMockMvc.perform(get("/api/clientes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].noIdentificacion").value(hasItem(DEFAULT_NO_IDENTIFICACION)))
            .andExpect(jsonPath("$.[*].ingresos").value(hasItem(DEFAULT_INGRESOS.doubleValue())))
            .andExpect(jsonPath("$.[*].estimacionOperacion").value(hasItem(DEFAULT_ESTIMACION_OPERACION.doubleValue())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())))
            .andExpect(jsonPath("$.[*].fechaAct").value(hasItem(DEFAULT_FECHA_ACT.toString())));

        // Check, that the count call also returns 1
        restClienteMockMvc.perform(get("/api/clientes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClienteShouldNotBeFound(String filter) throws Exception {
        restClienteMockMvc.perform(get("/api/clientes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClienteMockMvc.perform(get("/api/clientes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCliente() throws Exception {
        // Get the cliente
        restClienteMockMvc.perform(get("/api/clientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCliente() throws Exception {
        // Initialize the database
        clienteService.save(cliente);

        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Update the cliente
        Cliente updatedCliente = clienteRepository.findById(cliente.getId()).get();
        // Disconnect from session so that the updates on updatedCliente are not directly saved in db
        em.detach(updatedCliente);
        updatedCliente
            .noIdentificacion(UPDATED_NO_IDENTIFICACION)
            .ingresos(UPDATED_INGRESOS)
            .estimacionOperacion(UPDATED_ESTIMACION_OPERACION)
            .telefono(UPDATED_TELEFONO)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaAct(UPDATED_FECHA_ACT);

        restClienteMockMvc.perform(put("/api/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCliente)))
            .andExpect(status().isOk());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getNoIdentificacion()).isEqualTo(UPDATED_NO_IDENTIFICACION);
        assertThat(testCliente.getIngresos()).isEqualTo(UPDATED_INGRESOS);
        assertThat(testCliente.getEstimacionOperacion()).isEqualTo(UPDATED_ESTIMACION_OPERACION);
        assertThat(testCliente.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testCliente.getFechaAlta()).isEqualTo(UPDATED_FECHA_ALTA);
        assertThat(testCliente.getFechaAct()).isEqualTo(UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void updateNonExistingCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteMockMvc.perform(put("/api/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCliente() throws Exception {
        // Initialize the database
        clienteService.save(cliente);

        int databaseSizeBeforeDelete = clienteRepository.findAll().size();

        // Delete the cliente
        restClienteMockMvc.perform(delete("/api/clientes/{id}", cliente.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
