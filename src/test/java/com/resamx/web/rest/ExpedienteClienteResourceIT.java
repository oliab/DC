package com.resamx.web.rest;

import com.resamx.ResaMxWebApp;
import com.resamx.domain.ExpedienteCliente;
import com.resamx.domain.Cliente;
import com.resamx.domain.CatTipoDocumento;
import com.resamx.domain.User;
import com.resamx.repository.ExpedienteClienteRepository;
import com.resamx.service.ExpedienteClienteService;
import com.resamx.service.dto.ExpedienteClienteCriteria;
import com.resamx.service.ExpedienteClienteQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ExpedienteClienteResource} REST controller.
 */
@SpringBootTest(classes = ResaMxWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ExpedienteClienteResourceIT {

    private static final Boolean DEFAULT_EMPRESARIAL = false;
    private static final Boolean UPDATED_EMPRESARIAL = true;

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DOCUMENTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOCUMENTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOCUMENTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOCUMENTO_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_FECHA_ALTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ALTA = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_ALTA = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_FECHA_ACT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ACT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_ACT = LocalDate.ofEpochDay(-1L);

    @Autowired
    private ExpedienteClienteRepository expedienteClienteRepository;

    @Autowired
    private ExpedienteClienteService expedienteClienteService;

    @Autowired
    private ExpedienteClienteQueryService expedienteClienteQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExpedienteClienteMockMvc;

    private ExpedienteCliente expedienteCliente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExpedienteCliente createEntity(EntityManager em) {
        ExpedienteCliente expedienteCliente = new ExpedienteCliente()
            .empresarial(DEFAULT_EMPRESARIAL)
            .descripcion(DEFAULT_DESCRIPCION)
            .documento(DEFAULT_DOCUMENTO)
            .documentoContentType(DEFAULT_DOCUMENTO_CONTENT_TYPE)
            .fechaAlta(DEFAULT_FECHA_ALTA)
            .fechaAct(DEFAULT_FECHA_ACT);
        // Add required entity
        Cliente cliente;
        if (TestUtil.findAll(em, Cliente.class).isEmpty()) {
            cliente = ClienteResourceIT.createEntity(em);
            em.persist(cliente);
            em.flush();
        } else {
            cliente = TestUtil.findAll(em, Cliente.class).get(0);
        }
        expedienteCliente.setCliente(cliente);
        // Add required entity
        CatTipoDocumento catTipoDocumento;
        if (TestUtil.findAll(em, CatTipoDocumento.class).isEmpty()) {
            catTipoDocumento = CatTipoDocumentoResourceIT.createEntity(em);
            em.persist(catTipoDocumento);
            em.flush();
        } else {
            catTipoDocumento = TestUtil.findAll(em, CatTipoDocumento.class).get(0);
        }
        expedienteCliente.setTipoDocumento(catTipoDocumento);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        expedienteCliente.setUsuarioAlta(user);
        // Add required entity
        expedienteCliente.setUsuarioAct(user);
        return expedienteCliente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExpedienteCliente createUpdatedEntity(EntityManager em) {
        ExpedienteCliente expedienteCliente = new ExpedienteCliente()
            .empresarial(UPDATED_EMPRESARIAL)
            .descripcion(UPDATED_DESCRIPCION)
            .documento(UPDATED_DOCUMENTO)
            .documentoContentType(UPDATED_DOCUMENTO_CONTENT_TYPE)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaAct(UPDATED_FECHA_ACT);
        // Add required entity
        Cliente cliente;
        if (TestUtil.findAll(em, Cliente.class).isEmpty()) {
            cliente = ClienteResourceIT.createUpdatedEntity(em);
            em.persist(cliente);
            em.flush();
        } else {
            cliente = TestUtil.findAll(em, Cliente.class).get(0);
        }
        expedienteCliente.setCliente(cliente);
        // Add required entity
        CatTipoDocumento catTipoDocumento;
        if (TestUtil.findAll(em, CatTipoDocumento.class).isEmpty()) {
            catTipoDocumento = CatTipoDocumentoResourceIT.createUpdatedEntity(em);
            em.persist(catTipoDocumento);
            em.flush();
        } else {
            catTipoDocumento = TestUtil.findAll(em, CatTipoDocumento.class).get(0);
        }
        expedienteCliente.setTipoDocumento(catTipoDocumento);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        expedienteCliente.setUsuarioAlta(user);
        // Add required entity
        expedienteCliente.setUsuarioAct(user);
        return expedienteCliente;
    }

    @BeforeEach
    public void initTest() {
        expedienteCliente = createEntity(em);
    }

    @Test
    @Transactional
    public void createExpedienteCliente() throws Exception {
        int databaseSizeBeforeCreate = expedienteClienteRepository.findAll().size();
        // Create the ExpedienteCliente
        restExpedienteClienteMockMvc.perform(post("/api/expediente-clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(expedienteCliente)))
            .andExpect(status().isCreated());

        // Validate the ExpedienteCliente in the database
        List<ExpedienteCliente> expedienteClienteList = expedienteClienteRepository.findAll();
        assertThat(expedienteClienteList).hasSize(databaseSizeBeforeCreate + 1);
        ExpedienteCliente testExpedienteCliente = expedienteClienteList.get(expedienteClienteList.size() - 1);
        assertThat(testExpedienteCliente.isEmpresarial()).isEqualTo(DEFAULT_EMPRESARIAL);
        assertThat(testExpedienteCliente.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testExpedienteCliente.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
        assertThat(testExpedienteCliente.getDocumentoContentType()).isEqualTo(DEFAULT_DOCUMENTO_CONTENT_TYPE);
        assertThat(testExpedienteCliente.getFechaAlta()).isEqualTo(DEFAULT_FECHA_ALTA);
        assertThat(testExpedienteCliente.getFechaAct()).isEqualTo(DEFAULT_FECHA_ACT);
    }

    @Test
    @Transactional
    public void createExpedienteClienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = expedienteClienteRepository.findAll().size();

        // Create the ExpedienteCliente with an existing ID
        expedienteCliente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExpedienteClienteMockMvc.perform(post("/api/expediente-clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(expedienteCliente)))
            .andExpect(status().isBadRequest());

        // Validate the ExpedienteCliente in the database
        List<ExpedienteCliente> expedienteClienteList = expedienteClienteRepository.findAll();
        assertThat(expedienteClienteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEmpresarialIsRequired() throws Exception {
        int databaseSizeBeforeTest = expedienteClienteRepository.findAll().size();
        // set the field null
        expedienteCliente.setEmpresarial(null);

        // Create the ExpedienteCliente, which fails.


        restExpedienteClienteMockMvc.perform(post("/api/expediente-clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(expedienteCliente)))
            .andExpect(status().isBadRequest());

        List<ExpedienteCliente> expedienteClienteList = expedienteClienteRepository.findAll();
        assertThat(expedienteClienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExpedienteClientes() throws Exception {
        // Initialize the database
        expedienteClienteRepository.saveAndFlush(expedienteCliente);

        // Get all the expedienteClienteList
        restExpedienteClienteMockMvc.perform(get("/api/expediente-clientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expedienteCliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].empresarial").value(hasItem(DEFAULT_EMPRESARIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].documentoContentType").value(hasItem(DEFAULT_DOCUMENTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENTO))))
            .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())))
            .andExpect(jsonPath("$.[*].fechaAct").value(hasItem(DEFAULT_FECHA_ACT.toString())));
    }
    
    @Test
    @Transactional
    public void getExpedienteCliente() throws Exception {
        // Initialize the database
        expedienteClienteRepository.saveAndFlush(expedienteCliente);

        // Get the expedienteCliente
        restExpedienteClienteMockMvc.perform(get("/api/expediente-clientes/{id}", expedienteCliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(expedienteCliente.getId().intValue()))
            .andExpect(jsonPath("$.empresarial").value(DEFAULT_EMPRESARIAL.booleanValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.documentoContentType").value(DEFAULT_DOCUMENTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.documento").value(Base64Utils.encodeToString(DEFAULT_DOCUMENTO)))
            .andExpect(jsonPath("$.fechaAlta").value(DEFAULT_FECHA_ALTA.toString()))
            .andExpect(jsonPath("$.fechaAct").value(DEFAULT_FECHA_ACT.toString()));
    }


    @Test
    @Transactional
    public void getExpedienteClientesByIdFiltering() throws Exception {
        // Initialize the database
        expedienteClienteRepository.saveAndFlush(expedienteCliente);

        Long id = expedienteCliente.getId();

        defaultExpedienteClienteShouldBeFound("id.equals=" + id);
        defaultExpedienteClienteShouldNotBeFound("id.notEquals=" + id);

        defaultExpedienteClienteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultExpedienteClienteShouldNotBeFound("id.greaterThan=" + id);

        defaultExpedienteClienteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultExpedienteClienteShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllExpedienteClientesByEmpresarialIsEqualToSomething() throws Exception {
        // Initialize the database
        expedienteClienteRepository.saveAndFlush(expedienteCliente);

        // Get all the expedienteClienteList where empresarial equals to DEFAULT_EMPRESARIAL
        defaultExpedienteClienteShouldBeFound("empresarial.equals=" + DEFAULT_EMPRESARIAL);

        // Get all the expedienteClienteList where empresarial equals to UPDATED_EMPRESARIAL
        defaultExpedienteClienteShouldNotBeFound("empresarial.equals=" + UPDATED_EMPRESARIAL);
    }

    @Test
    @Transactional
    public void getAllExpedienteClientesByEmpresarialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expedienteClienteRepository.saveAndFlush(expedienteCliente);

        // Get all the expedienteClienteList where empresarial not equals to DEFAULT_EMPRESARIAL
        defaultExpedienteClienteShouldNotBeFound("empresarial.notEquals=" + DEFAULT_EMPRESARIAL);

        // Get all the expedienteClienteList where empresarial not equals to UPDATED_EMPRESARIAL
        defaultExpedienteClienteShouldBeFound("empresarial.notEquals=" + UPDATED_EMPRESARIAL);
    }

    @Test
    @Transactional
    public void getAllExpedienteClientesByEmpresarialIsInShouldWork() throws Exception {
        // Initialize the database
        expedienteClienteRepository.saveAndFlush(expedienteCliente);

        // Get all the expedienteClienteList where empresarial in DEFAULT_EMPRESARIAL or UPDATED_EMPRESARIAL
        defaultExpedienteClienteShouldBeFound("empresarial.in=" + DEFAULT_EMPRESARIAL + "," + UPDATED_EMPRESARIAL);

        // Get all the expedienteClienteList where empresarial equals to UPDATED_EMPRESARIAL
        defaultExpedienteClienteShouldNotBeFound("empresarial.in=" + UPDATED_EMPRESARIAL);
    }

    @Test
    @Transactional
    public void getAllExpedienteClientesByEmpresarialIsNullOrNotNull() throws Exception {
        // Initialize the database
        expedienteClienteRepository.saveAndFlush(expedienteCliente);

        // Get all the expedienteClienteList where empresarial is not null
        defaultExpedienteClienteShouldBeFound("empresarial.specified=true");

        // Get all the expedienteClienteList where empresarial is null
        defaultExpedienteClienteShouldNotBeFound("empresarial.specified=false");
    }

    @Test
    @Transactional
    public void getAllExpedienteClientesByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        expedienteClienteRepository.saveAndFlush(expedienteCliente);

        // Get all the expedienteClienteList where descripcion equals to DEFAULT_DESCRIPCION
        defaultExpedienteClienteShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the expedienteClienteList where descripcion equals to UPDATED_DESCRIPCION
        defaultExpedienteClienteShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllExpedienteClientesByDescripcionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expedienteClienteRepository.saveAndFlush(expedienteCliente);

        // Get all the expedienteClienteList where descripcion not equals to DEFAULT_DESCRIPCION
        defaultExpedienteClienteShouldNotBeFound("descripcion.notEquals=" + DEFAULT_DESCRIPCION);

        // Get all the expedienteClienteList where descripcion not equals to UPDATED_DESCRIPCION
        defaultExpedienteClienteShouldBeFound("descripcion.notEquals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllExpedienteClientesByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        expedienteClienteRepository.saveAndFlush(expedienteCliente);

        // Get all the expedienteClienteList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultExpedienteClienteShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the expedienteClienteList where descripcion equals to UPDATED_DESCRIPCION
        defaultExpedienteClienteShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllExpedienteClientesByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        expedienteClienteRepository.saveAndFlush(expedienteCliente);

        // Get all the expedienteClienteList where descripcion is not null
        defaultExpedienteClienteShouldBeFound("descripcion.specified=true");

        // Get all the expedienteClienteList where descripcion is null
        defaultExpedienteClienteShouldNotBeFound("descripcion.specified=false");
    }
                @Test
    @Transactional
    public void getAllExpedienteClientesByDescripcionContainsSomething() throws Exception {
        // Initialize the database
        expedienteClienteRepository.saveAndFlush(expedienteCliente);

        // Get all the expedienteClienteList where descripcion contains DEFAULT_DESCRIPCION
        defaultExpedienteClienteShouldBeFound("descripcion.contains=" + DEFAULT_DESCRIPCION);

        // Get all the expedienteClienteList where descripcion contains UPDATED_DESCRIPCION
        defaultExpedienteClienteShouldNotBeFound("descripcion.contains=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllExpedienteClientesByDescripcionNotContainsSomething() throws Exception {
        // Initialize the database
        expedienteClienteRepository.saveAndFlush(expedienteCliente);

        // Get all the expedienteClienteList where descripcion does not contain DEFAULT_DESCRIPCION
        defaultExpedienteClienteShouldNotBeFound("descripcion.doesNotContain=" + DEFAULT_DESCRIPCION);

        // Get all the expedienteClienteList where descripcion does not contain UPDATED_DESCRIPCION
        defaultExpedienteClienteShouldBeFound("descripcion.doesNotContain=" + UPDATED_DESCRIPCION);
    }


    @Test
    @Transactional
    public void getAllExpedienteClientesByFechaAltaIsEqualToSomething() throws Exception {
        // Initialize the database
        expedienteClienteRepository.saveAndFlush(expedienteCliente);

        // Get all the expedienteClienteList where fechaAlta equals to DEFAULT_FECHA_ALTA
        defaultExpedienteClienteShouldBeFound("fechaAlta.equals=" + DEFAULT_FECHA_ALTA);

        // Get all the expedienteClienteList where fechaAlta equals to UPDATED_FECHA_ALTA
        defaultExpedienteClienteShouldNotBeFound("fechaAlta.equals=" + UPDATED_FECHA_ALTA);
    }

    @Test
    @Transactional
    public void getAllExpedienteClientesByFechaAltaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expedienteClienteRepository.saveAndFlush(expedienteCliente);

        // Get all the expedienteClienteList where fechaAlta not equals to DEFAULT_FECHA_ALTA
        defaultExpedienteClienteShouldNotBeFound("fechaAlta.notEquals=" + DEFAULT_FECHA_ALTA);

        // Get all the expedienteClienteList where fechaAlta not equals to UPDATED_FECHA_ALTA
        defaultExpedienteClienteShouldBeFound("fechaAlta.notEquals=" + UPDATED_FECHA_ALTA);
    }

    @Test
    @Transactional
    public void getAllExpedienteClientesByFechaAltaIsInShouldWork() throws Exception {
        // Initialize the database
        expedienteClienteRepository.saveAndFlush(expedienteCliente);

        // Get all the expedienteClienteList where fechaAlta in DEFAULT_FECHA_ALTA or UPDATED_FECHA_ALTA
        defaultExpedienteClienteShouldBeFound("fechaAlta.in=" + DEFAULT_FECHA_ALTA + "," + UPDATED_FECHA_ALTA);

        // Get all the expedienteClienteList where fechaAlta equals to UPDATED_FECHA_ALTA
        defaultExpedienteClienteShouldNotBeFound("fechaAlta.in=" + UPDATED_FECHA_ALTA);
    }

    @Test
    @Transactional
    public void getAllExpedienteClientesByFechaAltaIsNullOrNotNull() throws Exception {
        // Initialize the database
        expedienteClienteRepository.saveAndFlush(expedienteCliente);

        // Get all the expedienteClienteList where fechaAlta is not null
        defaultExpedienteClienteShouldBeFound("fechaAlta.specified=true");

        // Get all the expedienteClienteList where fechaAlta is null
        defaultExpedienteClienteShouldNotBeFound("fechaAlta.specified=false");
    }

    @Test
    @Transactional
    public void getAllExpedienteClientesByFechaAltaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        expedienteClienteRepository.saveAndFlush(expedienteCliente);

        // Get all the expedienteClienteList where fechaAlta is greater than or equal to DEFAULT_FECHA_ALTA
        defaultExpedienteClienteShouldBeFound("fechaAlta.greaterThanOrEqual=" + DEFAULT_FECHA_ALTA);

        // Get all the expedienteClienteList where fechaAlta is greater than or equal to UPDATED_FECHA_ALTA
        defaultExpedienteClienteShouldNotBeFound("fechaAlta.greaterThanOrEqual=" + UPDATED_FECHA_ALTA);
    }

    @Test
    @Transactional
    public void getAllExpedienteClientesByFechaAltaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        expedienteClienteRepository.saveAndFlush(expedienteCliente);

        // Get all the expedienteClienteList where fechaAlta is less than or equal to DEFAULT_FECHA_ALTA
        defaultExpedienteClienteShouldBeFound("fechaAlta.lessThanOrEqual=" + DEFAULT_FECHA_ALTA);

        // Get all the expedienteClienteList where fechaAlta is less than or equal to SMALLER_FECHA_ALTA
        defaultExpedienteClienteShouldNotBeFound("fechaAlta.lessThanOrEqual=" + SMALLER_FECHA_ALTA);
    }

    @Test
    @Transactional
    public void getAllExpedienteClientesByFechaAltaIsLessThanSomething() throws Exception {
        // Initialize the database
        expedienteClienteRepository.saveAndFlush(expedienteCliente);

        // Get all the expedienteClienteList where fechaAlta is less than DEFAULT_FECHA_ALTA
        defaultExpedienteClienteShouldNotBeFound("fechaAlta.lessThan=" + DEFAULT_FECHA_ALTA);

        // Get all the expedienteClienteList where fechaAlta is less than UPDATED_FECHA_ALTA
        defaultExpedienteClienteShouldBeFound("fechaAlta.lessThan=" + UPDATED_FECHA_ALTA);
    }

    @Test
    @Transactional
    public void getAllExpedienteClientesByFechaAltaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        expedienteClienteRepository.saveAndFlush(expedienteCliente);

        // Get all the expedienteClienteList where fechaAlta is greater than DEFAULT_FECHA_ALTA
        defaultExpedienteClienteShouldNotBeFound("fechaAlta.greaterThan=" + DEFAULT_FECHA_ALTA);

        // Get all the expedienteClienteList where fechaAlta is greater than SMALLER_FECHA_ALTA
        defaultExpedienteClienteShouldBeFound("fechaAlta.greaterThan=" + SMALLER_FECHA_ALTA);
    }


    @Test
    @Transactional
    public void getAllExpedienteClientesByFechaActIsEqualToSomething() throws Exception {
        // Initialize the database
        expedienteClienteRepository.saveAndFlush(expedienteCliente);

        // Get all the expedienteClienteList where fechaAct equals to DEFAULT_FECHA_ACT
        defaultExpedienteClienteShouldBeFound("fechaAct.equals=" + DEFAULT_FECHA_ACT);

        // Get all the expedienteClienteList where fechaAct equals to UPDATED_FECHA_ACT
        defaultExpedienteClienteShouldNotBeFound("fechaAct.equals=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllExpedienteClientesByFechaActIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expedienteClienteRepository.saveAndFlush(expedienteCliente);

        // Get all the expedienteClienteList where fechaAct not equals to DEFAULT_FECHA_ACT
        defaultExpedienteClienteShouldNotBeFound("fechaAct.notEquals=" + DEFAULT_FECHA_ACT);

        // Get all the expedienteClienteList where fechaAct not equals to UPDATED_FECHA_ACT
        defaultExpedienteClienteShouldBeFound("fechaAct.notEquals=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllExpedienteClientesByFechaActIsInShouldWork() throws Exception {
        // Initialize the database
        expedienteClienteRepository.saveAndFlush(expedienteCliente);

        // Get all the expedienteClienteList where fechaAct in DEFAULT_FECHA_ACT or UPDATED_FECHA_ACT
        defaultExpedienteClienteShouldBeFound("fechaAct.in=" + DEFAULT_FECHA_ACT + "," + UPDATED_FECHA_ACT);

        // Get all the expedienteClienteList where fechaAct equals to UPDATED_FECHA_ACT
        defaultExpedienteClienteShouldNotBeFound("fechaAct.in=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllExpedienteClientesByFechaActIsNullOrNotNull() throws Exception {
        // Initialize the database
        expedienteClienteRepository.saveAndFlush(expedienteCliente);

        // Get all the expedienteClienteList where fechaAct is not null
        defaultExpedienteClienteShouldBeFound("fechaAct.specified=true");

        // Get all the expedienteClienteList where fechaAct is null
        defaultExpedienteClienteShouldNotBeFound("fechaAct.specified=false");
    }

    @Test
    @Transactional
    public void getAllExpedienteClientesByFechaActIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        expedienteClienteRepository.saveAndFlush(expedienteCliente);

        // Get all the expedienteClienteList where fechaAct is greater than or equal to DEFAULT_FECHA_ACT
        defaultExpedienteClienteShouldBeFound("fechaAct.greaterThanOrEqual=" + DEFAULT_FECHA_ACT);

        // Get all the expedienteClienteList where fechaAct is greater than or equal to UPDATED_FECHA_ACT
        defaultExpedienteClienteShouldNotBeFound("fechaAct.greaterThanOrEqual=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllExpedienteClientesByFechaActIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        expedienteClienteRepository.saveAndFlush(expedienteCliente);

        // Get all the expedienteClienteList where fechaAct is less than or equal to DEFAULT_FECHA_ACT
        defaultExpedienteClienteShouldBeFound("fechaAct.lessThanOrEqual=" + DEFAULT_FECHA_ACT);

        // Get all the expedienteClienteList where fechaAct is less than or equal to SMALLER_FECHA_ACT
        defaultExpedienteClienteShouldNotBeFound("fechaAct.lessThanOrEqual=" + SMALLER_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllExpedienteClientesByFechaActIsLessThanSomething() throws Exception {
        // Initialize the database
        expedienteClienteRepository.saveAndFlush(expedienteCliente);

        // Get all the expedienteClienteList where fechaAct is less than DEFAULT_FECHA_ACT
        defaultExpedienteClienteShouldNotBeFound("fechaAct.lessThan=" + DEFAULT_FECHA_ACT);

        // Get all the expedienteClienteList where fechaAct is less than UPDATED_FECHA_ACT
        defaultExpedienteClienteShouldBeFound("fechaAct.lessThan=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllExpedienteClientesByFechaActIsGreaterThanSomething() throws Exception {
        // Initialize the database
        expedienteClienteRepository.saveAndFlush(expedienteCliente);

        // Get all the expedienteClienteList where fechaAct is greater than DEFAULT_FECHA_ACT
        defaultExpedienteClienteShouldNotBeFound("fechaAct.greaterThan=" + DEFAULT_FECHA_ACT);

        // Get all the expedienteClienteList where fechaAct is greater than SMALLER_FECHA_ACT
        defaultExpedienteClienteShouldBeFound("fechaAct.greaterThan=" + SMALLER_FECHA_ACT);
    }


    @Test
    @Transactional
    public void getAllExpedienteClientesByClienteIsEqualToSomething() throws Exception {
        // Get already existing entity
        Cliente cliente = expedienteCliente.getCliente();
        expedienteClienteRepository.saveAndFlush(expedienteCliente);
        Long clienteId = cliente.getId();

        // Get all the expedienteClienteList where cliente equals to clienteId
        defaultExpedienteClienteShouldBeFound("clienteId.equals=" + clienteId);

        // Get all the expedienteClienteList where cliente equals to clienteId + 1
        defaultExpedienteClienteShouldNotBeFound("clienteId.equals=" + (clienteId + 1));
    }


    @Test
    @Transactional
    public void getAllExpedienteClientesByTipoDocumentoIsEqualToSomething() throws Exception {
        // Get already existing entity
        CatTipoDocumento tipoDocumento = expedienteCliente.getTipoDocumento();
        expedienteClienteRepository.saveAndFlush(expedienteCliente);
        Long tipoDocumentoId = tipoDocumento.getId();

        // Get all the expedienteClienteList where tipoDocumento equals to tipoDocumentoId
        defaultExpedienteClienteShouldBeFound("tipoDocumentoId.equals=" + tipoDocumentoId);

        // Get all the expedienteClienteList where tipoDocumento equals to tipoDocumentoId + 1
        defaultExpedienteClienteShouldNotBeFound("tipoDocumentoId.equals=" + (tipoDocumentoId + 1));
    }


    @Test
    @Transactional
    public void getAllExpedienteClientesByUsuarioAltaIsEqualToSomething() throws Exception {
        // Get already existing entity
        User usuarioAlta = expedienteCliente.getUsuarioAlta();
        expedienteClienteRepository.saveAndFlush(expedienteCliente);
        Long usuarioAltaId = usuarioAlta.getId();

        // Get all the expedienteClienteList where usuarioAlta equals to usuarioAltaId
        defaultExpedienteClienteShouldBeFound("usuarioAltaId.equals=" + usuarioAltaId);

        // Get all the expedienteClienteList where usuarioAlta equals to usuarioAltaId + 1
        defaultExpedienteClienteShouldNotBeFound("usuarioAltaId.equals=" + (usuarioAltaId + 1));
    }


    @Test
    @Transactional
    public void getAllExpedienteClientesByUsuarioActIsEqualToSomething() throws Exception {
        // Get already existing entity
        User usuarioAct = expedienteCliente.getUsuarioAct();
        expedienteClienteRepository.saveAndFlush(expedienteCliente);
        Long usuarioActId = usuarioAct.getId();

        // Get all the expedienteClienteList where usuarioAct equals to usuarioActId
        defaultExpedienteClienteShouldBeFound("usuarioActId.equals=" + usuarioActId);

        // Get all the expedienteClienteList where usuarioAct equals to usuarioActId + 1
        defaultExpedienteClienteShouldNotBeFound("usuarioActId.equals=" + (usuarioActId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultExpedienteClienteShouldBeFound(String filter) throws Exception {
        restExpedienteClienteMockMvc.perform(get("/api/expediente-clientes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expedienteCliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].empresarial").value(hasItem(DEFAULT_EMPRESARIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].documentoContentType").value(hasItem(DEFAULT_DOCUMENTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENTO))))
            .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())))
            .andExpect(jsonPath("$.[*].fechaAct").value(hasItem(DEFAULT_FECHA_ACT.toString())));

        // Check, that the count call also returns 1
        restExpedienteClienteMockMvc.perform(get("/api/expediente-clientes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultExpedienteClienteShouldNotBeFound(String filter) throws Exception {
        restExpedienteClienteMockMvc.perform(get("/api/expediente-clientes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restExpedienteClienteMockMvc.perform(get("/api/expediente-clientes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingExpedienteCliente() throws Exception {
        // Get the expedienteCliente
        restExpedienteClienteMockMvc.perform(get("/api/expediente-clientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExpedienteCliente() throws Exception {
        // Initialize the database
        expedienteClienteService.save(expedienteCliente);

        int databaseSizeBeforeUpdate = expedienteClienteRepository.findAll().size();

        // Update the expedienteCliente
        ExpedienteCliente updatedExpedienteCliente = expedienteClienteRepository.findById(expedienteCliente.getId()).get();
        // Disconnect from session so that the updates on updatedExpedienteCliente are not directly saved in db
        em.detach(updatedExpedienteCliente);
        updatedExpedienteCliente
            .empresarial(UPDATED_EMPRESARIAL)
            .descripcion(UPDATED_DESCRIPCION)
            .documento(UPDATED_DOCUMENTO)
            .documentoContentType(UPDATED_DOCUMENTO_CONTENT_TYPE)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .fechaAct(UPDATED_FECHA_ACT);

        restExpedienteClienteMockMvc.perform(put("/api/expediente-clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedExpedienteCliente)))
            .andExpect(status().isOk());

        // Validate the ExpedienteCliente in the database
        List<ExpedienteCliente> expedienteClienteList = expedienteClienteRepository.findAll();
        assertThat(expedienteClienteList).hasSize(databaseSizeBeforeUpdate);
        ExpedienteCliente testExpedienteCliente = expedienteClienteList.get(expedienteClienteList.size() - 1);
        assertThat(testExpedienteCliente.isEmpresarial()).isEqualTo(UPDATED_EMPRESARIAL);
        assertThat(testExpedienteCliente.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testExpedienteCliente.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
        assertThat(testExpedienteCliente.getDocumentoContentType()).isEqualTo(UPDATED_DOCUMENTO_CONTENT_TYPE);
        assertThat(testExpedienteCliente.getFechaAlta()).isEqualTo(UPDATED_FECHA_ALTA);
        assertThat(testExpedienteCliente.getFechaAct()).isEqualTo(UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void updateNonExistingExpedienteCliente() throws Exception {
        int databaseSizeBeforeUpdate = expedienteClienteRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpedienteClienteMockMvc.perform(put("/api/expediente-clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(expedienteCliente)))
            .andExpect(status().isBadRequest());

        // Validate the ExpedienteCliente in the database
        List<ExpedienteCliente> expedienteClienteList = expedienteClienteRepository.findAll();
        assertThat(expedienteClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExpedienteCliente() throws Exception {
        // Initialize the database
        expedienteClienteService.save(expedienteCliente);

        int databaseSizeBeforeDelete = expedienteClienteRepository.findAll().size();

        // Delete the expedienteCliente
        restExpedienteClienteMockMvc.perform(delete("/api/expediente-clientes/{id}", expedienteCliente.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExpedienteCliente> expedienteClienteList = expedienteClienteRepository.findAll();
        assertThat(expedienteClienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
