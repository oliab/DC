package com.resamx.web.rest;

import com.resamx.ResaMxWebApp;
import com.resamx.domain.Empresa;
import com.resamx.domain.CatIdentificacion;
import com.resamx.domain.DomicilioEmpresa;
import com.resamx.domain.User;
import com.resamx.repository.EmpresaRepository;
import com.resamx.service.EmpresaService;
import com.resamx.service.EmpresaQueryService;

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
 * Integration tests for the {@link EmpresaResource} REST controller.
 */
@SpringBootTest(classes = ResaMxWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmpresaResourceIT {

    private static final Boolean DEFAULT_FIDEICOMISO = false;
    private static final Boolean UPDATED_FIDEICOMISO = true;

    private static final String DEFAULT_RFC = "AAAAAAAAAA";
    private static final String UPDATED_RFC = "BBBBBBBBBB";

    private static final String DEFAULT_RAZON_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_RAZON_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_NO_IDENTIFICACION = "AAAAAAAAAA";
    private static final String UPDATED_NO_IDENTIFICACION = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_ALTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ALTA = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_ALTA = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_FECHA_ACT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ACT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_ACT = LocalDate.ofEpochDay(-1L);

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private EmpresaQueryService empresaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmpresaMockMvc;

    private Empresa empresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Empresa createEntity(EntityManager em) {
        Empresa empresa = new Empresa()
            .fideicomiso(DEFAULT_FIDEICOMISO)
            .rfc(DEFAULT_RFC)
            .razonSocial(DEFAULT_RAZON_SOCIAL)
            .noIdentificacion(DEFAULT_NO_IDENTIFICACION)
            .telefono(DEFAULT_TELEFONO)
            .fechaAlta(DEFAULT_FECHA_ALTA)
            .fechaAct(DEFAULT_FECHA_ACT);
        // Add required entity
        CatIdentificacion catIdentificacion;
        if (TestUtil.findAll(em, CatIdentificacion.class).isEmpty()) {
            catIdentificacion = CatIdentificacionResourceIT.createEntity(em);
            em.persist(catIdentificacion);
            em.flush();
        } else {
            catIdentificacion = TestUtil.findAll(em, CatIdentificacion.class).get(0);
        }
        empresa.setTipoIdentificacion(catIdentificacion);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        empresa.setUsuarioAlta(user);
        // Add required entity
        empresa.setUsuarioAct(user);
        // Add required entity
        DomicilioEmpresa domicilioUsuario;
        if (TestUtil.findAll(em, DomicilioEmpresa.class).isEmpty()) {
            domicilioUsuario = DomicilioEmpresaResourceIT.createEntity(em);
            em.persist(domicilioUsuario);
            em.flush();
        } else {
            domicilioUsuario = TestUtil.findAll(em, DomicilioEmpresa.class).get(0);
        }
        empresa.setDomicilio(domicilioUsuario);
        return empresa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Empresa createUpdatedEntity(EntityManager em) {
        Empresa empresa = new Empresa()
            .fideicomiso(UPDATED_FIDEICOMISO)
            .rfc(UPDATED_RFC)
            .razonSocial(UPDATED_RAZON_SOCIAL)
            .noIdentificacion(UPDATED_NO_IDENTIFICACION)
            .telefono(UPDATED_TELEFONO)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaAct(UPDATED_FECHA_ACT);
        // Add required entity
        CatIdentificacion catIdentificacion;
        if (TestUtil.findAll(em, CatIdentificacion.class).isEmpty()) {
            catIdentificacion = CatIdentificacionResourceIT.createUpdatedEntity(em);
            em.persist(catIdentificacion);
            em.flush();
        } else {
            catIdentificacion = TestUtil.findAll(em, CatIdentificacion.class).get(0);
        }
        empresa.setTipoIdentificacion(catIdentificacion);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        empresa.setUsuarioAlta(user);
        // Add required entity
        empresa.setUsuarioAct(user);
        // Add required entity
        DomicilioEmpresa domicilioUsuario;
        if (TestUtil.findAll(em, DomicilioEmpresa.class).isEmpty()) {
            domicilioUsuario = DomicilioEmpresaResourceIT.createUpdatedEntity(em);
            em.persist(domicilioUsuario);
            em.flush();
        } else {
            domicilioUsuario = TestUtil.findAll(em, DomicilioEmpresa.class).get(0);
        }
        empresa.setDomicilio(domicilioUsuario);
        return empresa;
    }

    @BeforeEach
    public void initTest() {
        empresa = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmpresa() throws Exception {
        int databaseSizeBeforeCreate = empresaRepository.findAll().size();
        // Create the Empresa
        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(empresa)))
            .andExpect(status().isCreated());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeCreate + 1);
        Empresa testEmpresa = empresaList.get(empresaList.size() - 1);
        assertThat(testEmpresa.isFideicomiso()).isEqualTo(DEFAULT_FIDEICOMISO);
        assertThat(testEmpresa.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testEmpresa.getRazonSocial()).isEqualTo(DEFAULT_RAZON_SOCIAL);
        assertThat(testEmpresa.getNoIdentificacion()).isEqualTo(DEFAULT_NO_IDENTIFICACION);
        assertThat(testEmpresa.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testEmpresa.getFechaAlta()).isEqualTo(DEFAULT_FECHA_ALTA);
        assertThat(testEmpresa.getFechaAct()).isEqualTo(DEFAULT_FECHA_ACT);
    }

    @Test
    @Transactional
    public void createEmpresaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = empresaRepository.findAll().size();

        // Create the Empresa with an existing ID
        empresa.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(empresa)))
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFideicomisoIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setFideicomiso(null);

        // Create the Empresa, which fails.


        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(empresa)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRfcIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setRfc(null);

        // Create the Empresa, which fails.


        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(empresa)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRazonSocialIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setRazonSocial(null);

        // Create the Empresa, which fails.


        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(empresa)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNoIdentificacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setNoIdentificacion(null);

        // Create the Empresa, which fails.


        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(empresa)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaAltaIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setFechaAlta(null);

        // Create the Empresa, which fails.


        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(empresa)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaActIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setFechaAct(null);

        // Create the Empresa, which fails.


        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(empresa)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmpresas() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList
        restEmpresaMockMvc.perform(get("/api/empresas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].fideicomiso").value(hasItem(DEFAULT_FIDEICOMISO.booleanValue())))
            .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC)))
            .andExpect(jsonPath("$.[*].razonSocial").value(hasItem(DEFAULT_RAZON_SOCIAL)))
            .andExpect(jsonPath("$.[*].noIdentificacion").value(hasItem(DEFAULT_NO_IDENTIFICACION)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())))
            .andExpect(jsonPath("$.[*].fechaAct").value(hasItem(DEFAULT_FECHA_ACT.toString())));
    }
    
    @Test
    @Transactional
    public void getEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get the empresa
        restEmpresaMockMvc.perform(get("/api/empresas/{id}", empresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(empresa.getId().intValue()))
            .andExpect(jsonPath("$.fideicomiso").value(DEFAULT_FIDEICOMISO.booleanValue()))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC))
            .andExpect(jsonPath("$.razonSocial").value(DEFAULT_RAZON_SOCIAL))
            .andExpect(jsonPath("$.noIdentificacion").value(DEFAULT_NO_IDENTIFICACION))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.fechaAlta").value(DEFAULT_FECHA_ALTA.toString()))
            .andExpect(jsonPath("$.fechaAct").value(DEFAULT_FECHA_ACT.toString()));
    }


    @Test
    @Transactional
    public void getEmpresasByIdFiltering() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        Long id = empresa.getId();

        defaultEmpresaShouldBeFound("id.equals=" + id);
        defaultEmpresaShouldNotBeFound("id.notEquals=" + id);

        defaultEmpresaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmpresaShouldNotBeFound("id.greaterThan=" + id);

        defaultEmpresaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmpresaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEmpresasByFideicomisoIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where fideicomiso equals to DEFAULT_FIDEICOMISO
        defaultEmpresaShouldBeFound("fideicomiso.equals=" + DEFAULT_FIDEICOMISO);

        // Get all the empresaList where fideicomiso equals to UPDATED_FIDEICOMISO
        defaultEmpresaShouldNotBeFound("fideicomiso.equals=" + UPDATED_FIDEICOMISO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByFideicomisoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where fideicomiso not equals to DEFAULT_FIDEICOMISO
        defaultEmpresaShouldNotBeFound("fideicomiso.notEquals=" + DEFAULT_FIDEICOMISO);

        // Get all the empresaList where fideicomiso not equals to UPDATED_FIDEICOMISO
        defaultEmpresaShouldBeFound("fideicomiso.notEquals=" + UPDATED_FIDEICOMISO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByFideicomisoIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where fideicomiso in DEFAULT_FIDEICOMISO or UPDATED_FIDEICOMISO
        defaultEmpresaShouldBeFound("fideicomiso.in=" + DEFAULT_FIDEICOMISO + "," + UPDATED_FIDEICOMISO);

        // Get all the empresaList where fideicomiso equals to UPDATED_FIDEICOMISO
        defaultEmpresaShouldNotBeFound("fideicomiso.in=" + UPDATED_FIDEICOMISO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByFideicomisoIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where fideicomiso is not null
        defaultEmpresaShouldBeFound("fideicomiso.specified=true");

        // Get all the empresaList where fideicomiso is null
        defaultEmpresaShouldNotBeFound("fideicomiso.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmpresasByRfcIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where rfc equals to DEFAULT_RFC
        defaultEmpresaShouldBeFound("rfc.equals=" + DEFAULT_RFC);

        // Get all the empresaList where rfc equals to UPDATED_RFC
        defaultEmpresaShouldNotBeFound("rfc.equals=" + UPDATED_RFC);
    }

    @Test
    @Transactional
    public void getAllEmpresasByRfcIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where rfc not equals to DEFAULT_RFC
        defaultEmpresaShouldNotBeFound("rfc.notEquals=" + DEFAULT_RFC);

        // Get all the empresaList where rfc not equals to UPDATED_RFC
        defaultEmpresaShouldBeFound("rfc.notEquals=" + UPDATED_RFC);
    }

    @Test
    @Transactional
    public void getAllEmpresasByRfcIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where rfc in DEFAULT_RFC or UPDATED_RFC
        defaultEmpresaShouldBeFound("rfc.in=" + DEFAULT_RFC + "," + UPDATED_RFC);

        // Get all the empresaList where rfc equals to UPDATED_RFC
        defaultEmpresaShouldNotBeFound("rfc.in=" + UPDATED_RFC);
    }

    @Test
    @Transactional
    public void getAllEmpresasByRfcIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where rfc is not null
        defaultEmpresaShouldBeFound("rfc.specified=true");

        // Get all the empresaList where rfc is null
        defaultEmpresaShouldNotBeFound("rfc.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmpresasByRfcContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where rfc contains DEFAULT_RFC
        defaultEmpresaShouldBeFound("rfc.contains=" + DEFAULT_RFC);

        // Get all the empresaList where rfc contains UPDATED_RFC
        defaultEmpresaShouldNotBeFound("rfc.contains=" + UPDATED_RFC);
    }

    @Test
    @Transactional
    public void getAllEmpresasByRfcNotContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where rfc does not contain DEFAULT_RFC
        defaultEmpresaShouldNotBeFound("rfc.doesNotContain=" + DEFAULT_RFC);

        // Get all the empresaList where rfc does not contain UPDATED_RFC
        defaultEmpresaShouldBeFound("rfc.doesNotContain=" + UPDATED_RFC);
    }


    @Test
    @Transactional
    public void getAllEmpresasByRazonSocialIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where razonSocial equals to DEFAULT_RAZON_SOCIAL
        defaultEmpresaShouldBeFound("razonSocial.equals=" + DEFAULT_RAZON_SOCIAL);

        // Get all the empresaList where razonSocial equals to UPDATED_RAZON_SOCIAL
        defaultEmpresaShouldNotBeFound("razonSocial.equals=" + UPDATED_RAZON_SOCIAL);
    }

    @Test
    @Transactional
    public void getAllEmpresasByRazonSocialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where razonSocial not equals to DEFAULT_RAZON_SOCIAL
        defaultEmpresaShouldNotBeFound("razonSocial.notEquals=" + DEFAULT_RAZON_SOCIAL);

        // Get all the empresaList where razonSocial not equals to UPDATED_RAZON_SOCIAL
        defaultEmpresaShouldBeFound("razonSocial.notEquals=" + UPDATED_RAZON_SOCIAL);
    }

    @Test
    @Transactional
    public void getAllEmpresasByRazonSocialIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where razonSocial in DEFAULT_RAZON_SOCIAL or UPDATED_RAZON_SOCIAL
        defaultEmpresaShouldBeFound("razonSocial.in=" + DEFAULT_RAZON_SOCIAL + "," + UPDATED_RAZON_SOCIAL);

        // Get all the empresaList where razonSocial equals to UPDATED_RAZON_SOCIAL
        defaultEmpresaShouldNotBeFound("razonSocial.in=" + UPDATED_RAZON_SOCIAL);
    }

    @Test
    @Transactional
    public void getAllEmpresasByRazonSocialIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where razonSocial is not null
        defaultEmpresaShouldBeFound("razonSocial.specified=true");

        // Get all the empresaList where razonSocial is null
        defaultEmpresaShouldNotBeFound("razonSocial.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmpresasByRazonSocialContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where razonSocial contains DEFAULT_RAZON_SOCIAL
        defaultEmpresaShouldBeFound("razonSocial.contains=" + DEFAULT_RAZON_SOCIAL);

        // Get all the empresaList where razonSocial contains UPDATED_RAZON_SOCIAL
        defaultEmpresaShouldNotBeFound("razonSocial.contains=" + UPDATED_RAZON_SOCIAL);
    }

    @Test
    @Transactional
    public void getAllEmpresasByRazonSocialNotContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where razonSocial does not contain DEFAULT_RAZON_SOCIAL
        defaultEmpresaShouldNotBeFound("razonSocial.doesNotContain=" + DEFAULT_RAZON_SOCIAL);

        // Get all the empresaList where razonSocial does not contain UPDATED_RAZON_SOCIAL
        defaultEmpresaShouldBeFound("razonSocial.doesNotContain=" + UPDATED_RAZON_SOCIAL);
    }


    @Test
    @Transactional
    public void getAllEmpresasByNoIdentificacionIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where noIdentificacion equals to DEFAULT_NO_IDENTIFICACION
        defaultEmpresaShouldBeFound("noIdentificacion.equals=" + DEFAULT_NO_IDENTIFICACION);

        // Get all the empresaList where noIdentificacion equals to UPDATED_NO_IDENTIFICACION
        defaultEmpresaShouldNotBeFound("noIdentificacion.equals=" + UPDATED_NO_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void getAllEmpresasByNoIdentificacionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where noIdentificacion not equals to DEFAULT_NO_IDENTIFICACION
        defaultEmpresaShouldNotBeFound("noIdentificacion.notEquals=" + DEFAULT_NO_IDENTIFICACION);

        // Get all the empresaList where noIdentificacion not equals to UPDATED_NO_IDENTIFICACION
        defaultEmpresaShouldBeFound("noIdentificacion.notEquals=" + UPDATED_NO_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void getAllEmpresasByNoIdentificacionIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where noIdentificacion in DEFAULT_NO_IDENTIFICACION or UPDATED_NO_IDENTIFICACION
        defaultEmpresaShouldBeFound("noIdentificacion.in=" + DEFAULT_NO_IDENTIFICACION + "," + UPDATED_NO_IDENTIFICACION);

        // Get all the empresaList where noIdentificacion equals to UPDATED_NO_IDENTIFICACION
        defaultEmpresaShouldNotBeFound("noIdentificacion.in=" + UPDATED_NO_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void getAllEmpresasByNoIdentificacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where noIdentificacion is not null
        defaultEmpresaShouldBeFound("noIdentificacion.specified=true");

        // Get all the empresaList where noIdentificacion is null
        defaultEmpresaShouldNotBeFound("noIdentificacion.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmpresasByNoIdentificacionContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where noIdentificacion contains DEFAULT_NO_IDENTIFICACION
        defaultEmpresaShouldBeFound("noIdentificacion.contains=" + DEFAULT_NO_IDENTIFICACION);

        // Get all the empresaList where noIdentificacion contains UPDATED_NO_IDENTIFICACION
        defaultEmpresaShouldNotBeFound("noIdentificacion.contains=" + UPDATED_NO_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void getAllEmpresasByNoIdentificacionNotContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where noIdentificacion does not contain DEFAULT_NO_IDENTIFICACION
        defaultEmpresaShouldNotBeFound("noIdentificacion.doesNotContain=" + DEFAULT_NO_IDENTIFICACION);

        // Get all the empresaList where noIdentificacion does not contain UPDATED_NO_IDENTIFICACION
        defaultEmpresaShouldBeFound("noIdentificacion.doesNotContain=" + UPDATED_NO_IDENTIFICACION);
    }


    @Test
    @Transactional
    public void getAllEmpresasByTelefonoIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where telefono equals to DEFAULT_TELEFONO
        defaultEmpresaShouldBeFound("telefono.equals=" + DEFAULT_TELEFONO);

        // Get all the empresaList where telefono equals to UPDATED_TELEFONO
        defaultEmpresaShouldNotBeFound("telefono.equals=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByTelefonoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where telefono not equals to DEFAULT_TELEFONO
        defaultEmpresaShouldNotBeFound("telefono.notEquals=" + DEFAULT_TELEFONO);

        // Get all the empresaList where telefono not equals to UPDATED_TELEFONO
        defaultEmpresaShouldBeFound("telefono.notEquals=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByTelefonoIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where telefono in DEFAULT_TELEFONO or UPDATED_TELEFONO
        defaultEmpresaShouldBeFound("telefono.in=" + DEFAULT_TELEFONO + "," + UPDATED_TELEFONO);

        // Get all the empresaList where telefono equals to UPDATED_TELEFONO
        defaultEmpresaShouldNotBeFound("telefono.in=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByTelefonoIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where telefono is not null
        defaultEmpresaShouldBeFound("telefono.specified=true");

        // Get all the empresaList where telefono is null
        defaultEmpresaShouldNotBeFound("telefono.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmpresasByTelefonoContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where telefono contains DEFAULT_TELEFONO
        defaultEmpresaShouldBeFound("telefono.contains=" + DEFAULT_TELEFONO);

        // Get all the empresaList where telefono contains UPDATED_TELEFONO
        defaultEmpresaShouldNotBeFound("telefono.contains=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByTelefonoNotContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where telefono does not contain DEFAULT_TELEFONO
        defaultEmpresaShouldNotBeFound("telefono.doesNotContain=" + DEFAULT_TELEFONO);

        // Get all the empresaList where telefono does not contain UPDATED_TELEFONO
        defaultEmpresaShouldBeFound("telefono.doesNotContain=" + UPDATED_TELEFONO);
    }


    @Test
    @Transactional
    public void getAllEmpresasByFechaAltaIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where fechaAlta equals to DEFAULT_FECHA_ALTA
        defaultEmpresaShouldBeFound("fechaAlta.equals=" + DEFAULT_FECHA_ALTA);

        // Get all the empresaList where fechaAlta equals to UPDATED_FECHA_ALTA
        defaultEmpresaShouldNotBeFound("fechaAlta.equals=" + UPDATED_FECHA_ALTA);
    }

    @Test
    @Transactional
    public void getAllEmpresasByFechaAltaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where fechaAlta not equals to DEFAULT_FECHA_ALTA
        defaultEmpresaShouldNotBeFound("fechaAlta.notEquals=" + DEFAULT_FECHA_ALTA);

        // Get all the empresaList where fechaAlta not equals to UPDATED_FECHA_ALTA
        defaultEmpresaShouldBeFound("fechaAlta.notEquals=" + UPDATED_FECHA_ALTA);
    }

    @Test
    @Transactional
    public void getAllEmpresasByFechaAltaIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where fechaAlta in DEFAULT_FECHA_ALTA or UPDATED_FECHA_ALTA
        defaultEmpresaShouldBeFound("fechaAlta.in=" + DEFAULT_FECHA_ALTA + "," + UPDATED_FECHA_ALTA);

        // Get all the empresaList where fechaAlta equals to UPDATED_FECHA_ALTA
        defaultEmpresaShouldNotBeFound("fechaAlta.in=" + UPDATED_FECHA_ALTA);
    }

    @Test
    @Transactional
    public void getAllEmpresasByFechaAltaIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where fechaAlta is not null
        defaultEmpresaShouldBeFound("fechaAlta.specified=true");

        // Get all the empresaList where fechaAlta is null
        defaultEmpresaShouldNotBeFound("fechaAlta.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmpresasByFechaAltaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where fechaAlta is greater than or equal to DEFAULT_FECHA_ALTA
        defaultEmpresaShouldBeFound("fechaAlta.greaterThanOrEqual=" + DEFAULT_FECHA_ALTA);

        // Get all the empresaList where fechaAlta is greater than or equal to UPDATED_FECHA_ALTA
        defaultEmpresaShouldNotBeFound("fechaAlta.greaterThanOrEqual=" + UPDATED_FECHA_ALTA);
    }

    @Test
    @Transactional
    public void getAllEmpresasByFechaAltaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where fechaAlta is less than or equal to DEFAULT_FECHA_ALTA
        defaultEmpresaShouldBeFound("fechaAlta.lessThanOrEqual=" + DEFAULT_FECHA_ALTA);

        // Get all the empresaList where fechaAlta is less than or equal to SMALLER_FECHA_ALTA
        defaultEmpresaShouldNotBeFound("fechaAlta.lessThanOrEqual=" + SMALLER_FECHA_ALTA);
    }

    @Test
    @Transactional
    public void getAllEmpresasByFechaAltaIsLessThanSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where fechaAlta is less than DEFAULT_FECHA_ALTA
        defaultEmpresaShouldNotBeFound("fechaAlta.lessThan=" + DEFAULT_FECHA_ALTA);

        // Get all the empresaList where fechaAlta is less than UPDATED_FECHA_ALTA
        defaultEmpresaShouldBeFound("fechaAlta.lessThan=" + UPDATED_FECHA_ALTA);
    }

    @Test
    @Transactional
    public void getAllEmpresasByFechaAltaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where fechaAlta is greater than DEFAULT_FECHA_ALTA
        defaultEmpresaShouldNotBeFound("fechaAlta.greaterThan=" + DEFAULT_FECHA_ALTA);

        // Get all the empresaList where fechaAlta is greater than SMALLER_FECHA_ALTA
        defaultEmpresaShouldBeFound("fechaAlta.greaterThan=" + SMALLER_FECHA_ALTA);
    }


    @Test
    @Transactional
    public void getAllEmpresasByFechaActIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where fechaAct equals to DEFAULT_FECHA_ACT
        defaultEmpresaShouldBeFound("fechaAct.equals=" + DEFAULT_FECHA_ACT);

        // Get all the empresaList where fechaAct equals to UPDATED_FECHA_ACT
        defaultEmpresaShouldNotBeFound("fechaAct.equals=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllEmpresasByFechaActIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where fechaAct not equals to DEFAULT_FECHA_ACT
        defaultEmpresaShouldNotBeFound("fechaAct.notEquals=" + DEFAULT_FECHA_ACT);

        // Get all the empresaList where fechaAct not equals to UPDATED_FECHA_ACT
        defaultEmpresaShouldBeFound("fechaAct.notEquals=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllEmpresasByFechaActIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where fechaAct in DEFAULT_FECHA_ACT or UPDATED_FECHA_ACT
        defaultEmpresaShouldBeFound("fechaAct.in=" + DEFAULT_FECHA_ACT + "," + UPDATED_FECHA_ACT);

        // Get all the empresaList where fechaAct equals to UPDATED_FECHA_ACT
        defaultEmpresaShouldNotBeFound("fechaAct.in=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllEmpresasByFechaActIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where fechaAct is not null
        defaultEmpresaShouldBeFound("fechaAct.specified=true");

        // Get all the empresaList where fechaAct is null
        defaultEmpresaShouldNotBeFound("fechaAct.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmpresasByFechaActIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where fechaAct is greater than or equal to DEFAULT_FECHA_ACT
        defaultEmpresaShouldBeFound("fechaAct.greaterThanOrEqual=" + DEFAULT_FECHA_ACT);

        // Get all the empresaList where fechaAct is greater than or equal to UPDATED_FECHA_ACT
        defaultEmpresaShouldNotBeFound("fechaAct.greaterThanOrEqual=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllEmpresasByFechaActIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where fechaAct is less than or equal to DEFAULT_FECHA_ACT
        defaultEmpresaShouldBeFound("fechaAct.lessThanOrEqual=" + DEFAULT_FECHA_ACT);

        // Get all the empresaList where fechaAct is less than or equal to SMALLER_FECHA_ACT
        defaultEmpresaShouldNotBeFound("fechaAct.lessThanOrEqual=" + SMALLER_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllEmpresasByFechaActIsLessThanSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where fechaAct is less than DEFAULT_FECHA_ACT
        defaultEmpresaShouldNotBeFound("fechaAct.lessThan=" + DEFAULT_FECHA_ACT);

        // Get all the empresaList where fechaAct is less than UPDATED_FECHA_ACT
        defaultEmpresaShouldBeFound("fechaAct.lessThan=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllEmpresasByFechaActIsGreaterThanSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where fechaAct is greater than DEFAULT_FECHA_ACT
        defaultEmpresaShouldNotBeFound("fechaAct.greaterThan=" + DEFAULT_FECHA_ACT);

        // Get all the empresaList where fechaAct is greater than SMALLER_FECHA_ACT
        defaultEmpresaShouldBeFound("fechaAct.greaterThan=" + SMALLER_FECHA_ACT);
    }


    @Test
    @Transactional
    public void getAllEmpresasByTipoIdentificacionIsEqualToSomething() throws Exception {
        // Get already existing entity
        CatIdentificacion tipoIdentificacion = empresa.getTipoIdentificacion();
        empresaRepository.saveAndFlush(empresa);
        Long tipoIdentificacionId = tipoIdentificacion.getId();

        // Get all the empresaList where tipoIdentificacion equals to tipoIdentificacionId
        defaultEmpresaShouldBeFound("tipoIdentificacionId.equals=" + tipoIdentificacionId);

        // Get all the empresaList where tipoIdentificacion equals to tipoIdentificacionId + 1
        defaultEmpresaShouldNotBeFound("tipoIdentificacionId.equals=" + (tipoIdentificacionId + 1));
    }


    @Test
    @Transactional
    public void getAllEmpresasByUsuarioAltaIsEqualToSomething() throws Exception {
        // Get already existing entity
        User usuarioAlta = empresa.getUsuarioAlta();
        empresaRepository.saveAndFlush(empresa);
        Long usuarioAltaId = usuarioAlta.getId();

        // Get all the empresaList where usuarioAlta equals to usuarioAltaId
        defaultEmpresaShouldBeFound("usuarioAltaId.equals=" + usuarioAltaId);

        // Get all the empresaList where usuarioAlta equals to usuarioAltaId + 1
        defaultEmpresaShouldNotBeFound("usuarioAltaId.equals=" + (usuarioAltaId + 1));
    }


    @Test
    @Transactional
    public void getAllEmpresasByUsuarioActIsEqualToSomething() throws Exception {
        // Get already existing entity
        User usuarioAct = empresa.getUsuarioAct();
        empresaRepository.saveAndFlush(empresa);
        Long usuarioActId = usuarioAct.getId();

        // Get all the empresaList where usuarioAct equals to usuarioActId
        defaultEmpresaShouldBeFound("usuarioActId.equals=" + usuarioActId);

        // Get all the empresaList where usuarioAct equals to usuarioActId + 1
        defaultEmpresaShouldNotBeFound("usuarioActId.equals=" + (usuarioActId + 1));
    }


    @Test
    @Transactional
    public void getAllEmpresasByDomicilioIsEqualToSomething() throws Exception {
        // Get already existing entity
        DomicilioEmpresa domicilio = empresa.getDomicilio();
        empresaRepository.saveAndFlush(empresa);
        Long domicilioId = domicilio.getId();

        // Get all the empresaList where domicilio equals to domicilioId
        defaultEmpresaShouldBeFound("domicilioId.equals=" + domicilioId);

        // Get all the empresaList where domicilio equals to domicilioId + 1
        defaultEmpresaShouldNotBeFound("domicilioId.equals=" + (domicilioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmpresaShouldBeFound(String filter) throws Exception {
        restEmpresaMockMvc.perform(get("/api/empresas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].fideicomiso").value(hasItem(DEFAULT_FIDEICOMISO.booleanValue())))
            .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC)))
            .andExpect(jsonPath("$.[*].razonSocial").value(hasItem(DEFAULT_RAZON_SOCIAL)))
            .andExpect(jsonPath("$.[*].noIdentificacion").value(hasItem(DEFAULT_NO_IDENTIFICACION)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())))
            .andExpect(jsonPath("$.[*].fechaAct").value(hasItem(DEFAULT_FECHA_ACT.toString())));

        // Check, that the count call also returns 1
        restEmpresaMockMvc.perform(get("/api/empresas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmpresaShouldNotBeFound(String filter) throws Exception {
        restEmpresaMockMvc.perform(get("/api/empresas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmpresaMockMvc.perform(get("/api/empresas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEmpresa() throws Exception {
        // Get the empresa
        restEmpresaMockMvc.perform(get("/api/empresas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmpresa() throws Exception {
        // Initialize the database
        empresaService.save(empresa);

        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();

        // Update the empresa
        Empresa updatedEmpresa = empresaRepository.findById(empresa.getId()).get();
        // Disconnect from session so that the updates on updatedEmpresa are not directly saved in db
        em.detach(updatedEmpresa);
        updatedEmpresa
            .fideicomiso(UPDATED_FIDEICOMISO)
            .rfc(UPDATED_RFC)
            .razonSocial(UPDATED_RAZON_SOCIAL)
            .noIdentificacion(UPDATED_NO_IDENTIFICACION)
            .telefono(UPDATED_TELEFONO)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaAct(UPDATED_FECHA_ACT);

        restEmpresaMockMvc.perform(put("/api/empresas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmpresa)))
            .andExpect(status().isOk());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
        Empresa testEmpresa = empresaList.get(empresaList.size() - 1);
        assertThat(testEmpresa.isFideicomiso()).isEqualTo(UPDATED_FIDEICOMISO);
        assertThat(testEmpresa.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testEmpresa.getRazonSocial()).isEqualTo(UPDATED_RAZON_SOCIAL);
        assertThat(testEmpresa.getNoIdentificacion()).isEqualTo(UPDATED_NO_IDENTIFICACION);
        assertThat(testEmpresa.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testEmpresa.getFechaAlta()).isEqualTo(UPDATED_FECHA_ALTA);
        assertThat(testEmpresa.getFechaAct()).isEqualTo(UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void updateNonExistingEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpresaMockMvc.perform(put("/api/empresas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(empresa)))
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmpresa() throws Exception {
        // Initialize the database
        empresaService.save(empresa);

        int databaseSizeBeforeDelete = empresaRepository.findAll().size();

        // Delete the empresa
        restEmpresaMockMvc.perform(delete("/api/empresas/{id}", empresa.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
