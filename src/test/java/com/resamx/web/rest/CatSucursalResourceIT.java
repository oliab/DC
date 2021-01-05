package com.resamx.web.rest;

import com.resamx.ResaMxWebApp;
import com.resamx.domain.CatSucursal;
import com.resamx.domain.User;
import com.resamx.repository.CatSucursalRepository;
import com.resamx.service.CatSucursalService;
import com.resamx.service.dto.CatSucursalCriteria;
import com.resamx.service.CatSucursalQueryService;

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
 * Integration tests for the {@link CatSucursalResource} REST controller.
 */
@SpringBootTest(classes = ResaMxWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CatSucursalResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_ACT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ACT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_ACT = LocalDate.ofEpochDay(-1L);

    @Autowired
    private CatSucursalRepository catSucursalRepository;

    @Autowired
    private CatSucursalService catSucursalService;

    @Autowired
    private CatSucursalQueryService catSucursalQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCatSucursalMockMvc;

    private CatSucursal catSucursal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatSucursal createEntity(EntityManager em) {
        CatSucursal catSucursal = new CatSucursal()
            .nombre(DEFAULT_NOMBRE)
            .direccion(DEFAULT_DIRECCION)
            .telefono(DEFAULT_TELEFONO)
            .fechaAct(DEFAULT_FECHA_ACT);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catSucursal.setUsuario(user);
        return catSucursal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatSucursal createUpdatedEntity(EntityManager em) {
        CatSucursal catSucursal = new CatSucursal()
            .nombre(UPDATED_NOMBRE)
            .direccion(UPDATED_DIRECCION)
            .telefono(UPDATED_TELEFONO)
            .fechaAct(UPDATED_FECHA_ACT);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catSucursal.setUsuario(user);
        return catSucursal;
    }

    @BeforeEach
    public void initTest() {
        catSucursal = createEntity(em);
    }

    @Test
    @Transactional
    public void createCatSucursal() throws Exception {
        int databaseSizeBeforeCreate = catSucursalRepository.findAll().size();
        // Create the CatSucursal
        restCatSucursalMockMvc.perform(post("/api/cat-sucursals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catSucursal)))
            .andExpect(status().isCreated());

        // Validate the CatSucursal in the database
        List<CatSucursal> catSucursalList = catSucursalRepository.findAll();
        assertThat(catSucursalList).hasSize(databaseSizeBeforeCreate + 1);
        CatSucursal testCatSucursal = catSucursalList.get(catSucursalList.size() - 1);
        assertThat(testCatSucursal.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCatSucursal.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testCatSucursal.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testCatSucursal.getFechaAct()).isEqualTo(DEFAULT_FECHA_ACT);
    }

    @Test
    @Transactional
    public void createCatSucursalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = catSucursalRepository.findAll().size();

        // Create the CatSucursal with an existing ID
        catSucursal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatSucursalMockMvc.perform(post("/api/cat-sucursals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catSucursal)))
            .andExpect(status().isBadRequest());

        // Validate the CatSucursal in the database
        List<CatSucursal> catSucursalList = catSucursalRepository.findAll();
        assertThat(catSucursalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = catSucursalRepository.findAll().size();
        // set the field null
        catSucursal.setNombre(null);

        // Create the CatSucursal, which fails.


        restCatSucursalMockMvc.perform(post("/api/cat-sucursals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catSucursal)))
            .andExpect(status().isBadRequest());

        List<CatSucursal> catSucursalList = catSucursalRepository.findAll();
        assertThat(catSucursalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCatSucursals() throws Exception {
        // Initialize the database
        catSucursalRepository.saveAndFlush(catSucursal);

        // Get all the catSucursalList
        restCatSucursalMockMvc.perform(get("/api/cat-sucursals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catSucursal.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].fechaAct").value(hasItem(DEFAULT_FECHA_ACT.toString())));
    }
    
    @Test
    @Transactional
    public void getCatSucursal() throws Exception {
        // Initialize the database
        catSucursalRepository.saveAndFlush(catSucursal);

        // Get the catSucursal
        restCatSucursalMockMvc.perform(get("/api/cat-sucursals/{id}", catSucursal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(catSucursal.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.fechaAct").value(DEFAULT_FECHA_ACT.toString()));
    }


    @Test
    @Transactional
    public void getCatSucursalsByIdFiltering() throws Exception {
        // Initialize the database
        catSucursalRepository.saveAndFlush(catSucursal);

        Long id = catSucursal.getId();

        defaultCatSucursalShouldBeFound("id.equals=" + id);
        defaultCatSucursalShouldNotBeFound("id.notEquals=" + id);

        defaultCatSucursalShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCatSucursalShouldNotBeFound("id.greaterThan=" + id);

        defaultCatSucursalShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCatSucursalShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCatSucursalsByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        catSucursalRepository.saveAndFlush(catSucursal);

        // Get all the catSucursalList where nombre equals to DEFAULT_NOMBRE
        defaultCatSucursalShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the catSucursalList where nombre equals to UPDATED_NOMBRE
        defaultCatSucursalShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllCatSucursalsByNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catSucursalRepository.saveAndFlush(catSucursal);

        // Get all the catSucursalList where nombre not equals to DEFAULT_NOMBRE
        defaultCatSucursalShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

        // Get all the catSucursalList where nombre not equals to UPDATED_NOMBRE
        defaultCatSucursalShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllCatSucursalsByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        catSucursalRepository.saveAndFlush(catSucursal);

        // Get all the catSucursalList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultCatSucursalShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the catSucursalList where nombre equals to UPDATED_NOMBRE
        defaultCatSucursalShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllCatSucursalsByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        catSucursalRepository.saveAndFlush(catSucursal);

        // Get all the catSucursalList where nombre is not null
        defaultCatSucursalShouldBeFound("nombre.specified=true");

        // Get all the catSucursalList where nombre is null
        defaultCatSucursalShouldNotBeFound("nombre.specified=false");
    }
                @Test
    @Transactional
    public void getAllCatSucursalsByNombreContainsSomething() throws Exception {
        // Initialize the database
        catSucursalRepository.saveAndFlush(catSucursal);

        // Get all the catSucursalList where nombre contains DEFAULT_NOMBRE
        defaultCatSucursalShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

        // Get all the catSucursalList where nombre contains UPDATED_NOMBRE
        defaultCatSucursalShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllCatSucursalsByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        catSucursalRepository.saveAndFlush(catSucursal);

        // Get all the catSucursalList where nombre does not contain DEFAULT_NOMBRE
        defaultCatSucursalShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

        // Get all the catSucursalList where nombre does not contain UPDATED_NOMBRE
        defaultCatSucursalShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
    }


    @Test
    @Transactional
    public void getAllCatSucursalsByDireccionIsEqualToSomething() throws Exception {
        // Initialize the database
        catSucursalRepository.saveAndFlush(catSucursal);

        // Get all the catSucursalList where direccion equals to DEFAULT_DIRECCION
        defaultCatSucursalShouldBeFound("direccion.equals=" + DEFAULT_DIRECCION);

        // Get all the catSucursalList where direccion equals to UPDATED_DIRECCION
        defaultCatSucursalShouldNotBeFound("direccion.equals=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    public void getAllCatSucursalsByDireccionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catSucursalRepository.saveAndFlush(catSucursal);

        // Get all the catSucursalList where direccion not equals to DEFAULT_DIRECCION
        defaultCatSucursalShouldNotBeFound("direccion.notEquals=" + DEFAULT_DIRECCION);

        // Get all the catSucursalList where direccion not equals to UPDATED_DIRECCION
        defaultCatSucursalShouldBeFound("direccion.notEquals=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    public void getAllCatSucursalsByDireccionIsInShouldWork() throws Exception {
        // Initialize the database
        catSucursalRepository.saveAndFlush(catSucursal);

        // Get all the catSucursalList where direccion in DEFAULT_DIRECCION or UPDATED_DIRECCION
        defaultCatSucursalShouldBeFound("direccion.in=" + DEFAULT_DIRECCION + "," + UPDATED_DIRECCION);

        // Get all the catSucursalList where direccion equals to UPDATED_DIRECCION
        defaultCatSucursalShouldNotBeFound("direccion.in=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    public void getAllCatSucursalsByDireccionIsNullOrNotNull() throws Exception {
        // Initialize the database
        catSucursalRepository.saveAndFlush(catSucursal);

        // Get all the catSucursalList where direccion is not null
        defaultCatSucursalShouldBeFound("direccion.specified=true");

        // Get all the catSucursalList where direccion is null
        defaultCatSucursalShouldNotBeFound("direccion.specified=false");
    }
                @Test
    @Transactional
    public void getAllCatSucursalsByDireccionContainsSomething() throws Exception {
        // Initialize the database
        catSucursalRepository.saveAndFlush(catSucursal);

        // Get all the catSucursalList where direccion contains DEFAULT_DIRECCION
        defaultCatSucursalShouldBeFound("direccion.contains=" + DEFAULT_DIRECCION);

        // Get all the catSucursalList where direccion contains UPDATED_DIRECCION
        defaultCatSucursalShouldNotBeFound("direccion.contains=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    public void getAllCatSucursalsByDireccionNotContainsSomething() throws Exception {
        // Initialize the database
        catSucursalRepository.saveAndFlush(catSucursal);

        // Get all the catSucursalList where direccion does not contain DEFAULT_DIRECCION
        defaultCatSucursalShouldNotBeFound("direccion.doesNotContain=" + DEFAULT_DIRECCION);

        // Get all the catSucursalList where direccion does not contain UPDATED_DIRECCION
        defaultCatSucursalShouldBeFound("direccion.doesNotContain=" + UPDATED_DIRECCION);
    }


    @Test
    @Transactional
    public void getAllCatSucursalsByTelefonoIsEqualToSomething() throws Exception {
        // Initialize the database
        catSucursalRepository.saveAndFlush(catSucursal);

        // Get all the catSucursalList where telefono equals to DEFAULT_TELEFONO
        defaultCatSucursalShouldBeFound("telefono.equals=" + DEFAULT_TELEFONO);

        // Get all the catSucursalList where telefono equals to UPDATED_TELEFONO
        defaultCatSucursalShouldNotBeFound("telefono.equals=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllCatSucursalsByTelefonoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catSucursalRepository.saveAndFlush(catSucursal);

        // Get all the catSucursalList where telefono not equals to DEFAULT_TELEFONO
        defaultCatSucursalShouldNotBeFound("telefono.notEquals=" + DEFAULT_TELEFONO);

        // Get all the catSucursalList where telefono not equals to UPDATED_TELEFONO
        defaultCatSucursalShouldBeFound("telefono.notEquals=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllCatSucursalsByTelefonoIsInShouldWork() throws Exception {
        // Initialize the database
        catSucursalRepository.saveAndFlush(catSucursal);

        // Get all the catSucursalList where telefono in DEFAULT_TELEFONO or UPDATED_TELEFONO
        defaultCatSucursalShouldBeFound("telefono.in=" + DEFAULT_TELEFONO + "," + UPDATED_TELEFONO);

        // Get all the catSucursalList where telefono equals to UPDATED_TELEFONO
        defaultCatSucursalShouldNotBeFound("telefono.in=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllCatSucursalsByTelefonoIsNullOrNotNull() throws Exception {
        // Initialize the database
        catSucursalRepository.saveAndFlush(catSucursal);

        // Get all the catSucursalList where telefono is not null
        defaultCatSucursalShouldBeFound("telefono.specified=true");

        // Get all the catSucursalList where telefono is null
        defaultCatSucursalShouldNotBeFound("telefono.specified=false");
    }
                @Test
    @Transactional
    public void getAllCatSucursalsByTelefonoContainsSomething() throws Exception {
        // Initialize the database
        catSucursalRepository.saveAndFlush(catSucursal);

        // Get all the catSucursalList where telefono contains DEFAULT_TELEFONO
        defaultCatSucursalShouldBeFound("telefono.contains=" + DEFAULT_TELEFONO);

        // Get all the catSucursalList where telefono contains UPDATED_TELEFONO
        defaultCatSucursalShouldNotBeFound("telefono.contains=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllCatSucursalsByTelefonoNotContainsSomething() throws Exception {
        // Initialize the database
        catSucursalRepository.saveAndFlush(catSucursal);

        // Get all the catSucursalList where telefono does not contain DEFAULT_TELEFONO
        defaultCatSucursalShouldNotBeFound("telefono.doesNotContain=" + DEFAULT_TELEFONO);

        // Get all the catSucursalList where telefono does not contain UPDATED_TELEFONO
        defaultCatSucursalShouldBeFound("telefono.doesNotContain=" + UPDATED_TELEFONO);
    }


    @Test
    @Transactional
    public void getAllCatSucursalsByFechaActIsEqualToSomething() throws Exception {
        // Initialize the database
        catSucursalRepository.saveAndFlush(catSucursal);

        // Get all the catSucursalList where fechaAct equals to DEFAULT_FECHA_ACT
        defaultCatSucursalShouldBeFound("fechaAct.equals=" + DEFAULT_FECHA_ACT);

        // Get all the catSucursalList where fechaAct equals to UPDATED_FECHA_ACT
        defaultCatSucursalShouldNotBeFound("fechaAct.equals=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllCatSucursalsByFechaActIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catSucursalRepository.saveAndFlush(catSucursal);

        // Get all the catSucursalList where fechaAct not equals to DEFAULT_FECHA_ACT
        defaultCatSucursalShouldNotBeFound("fechaAct.notEquals=" + DEFAULT_FECHA_ACT);

        // Get all the catSucursalList where fechaAct not equals to UPDATED_FECHA_ACT
        defaultCatSucursalShouldBeFound("fechaAct.notEquals=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllCatSucursalsByFechaActIsInShouldWork() throws Exception {
        // Initialize the database
        catSucursalRepository.saveAndFlush(catSucursal);

        // Get all the catSucursalList where fechaAct in DEFAULT_FECHA_ACT or UPDATED_FECHA_ACT
        defaultCatSucursalShouldBeFound("fechaAct.in=" + DEFAULT_FECHA_ACT + "," + UPDATED_FECHA_ACT);

        // Get all the catSucursalList where fechaAct equals to UPDATED_FECHA_ACT
        defaultCatSucursalShouldNotBeFound("fechaAct.in=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllCatSucursalsByFechaActIsNullOrNotNull() throws Exception {
        // Initialize the database
        catSucursalRepository.saveAndFlush(catSucursal);

        // Get all the catSucursalList where fechaAct is not null
        defaultCatSucursalShouldBeFound("fechaAct.specified=true");

        // Get all the catSucursalList where fechaAct is null
        defaultCatSucursalShouldNotBeFound("fechaAct.specified=false");
    }

    @Test
    @Transactional
    public void getAllCatSucursalsByFechaActIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        catSucursalRepository.saveAndFlush(catSucursal);

        // Get all the catSucursalList where fechaAct is greater than or equal to DEFAULT_FECHA_ACT
        defaultCatSucursalShouldBeFound("fechaAct.greaterThanOrEqual=" + DEFAULT_FECHA_ACT);

        // Get all the catSucursalList where fechaAct is greater than or equal to UPDATED_FECHA_ACT
        defaultCatSucursalShouldNotBeFound("fechaAct.greaterThanOrEqual=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllCatSucursalsByFechaActIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        catSucursalRepository.saveAndFlush(catSucursal);

        // Get all the catSucursalList where fechaAct is less than or equal to DEFAULT_FECHA_ACT
        defaultCatSucursalShouldBeFound("fechaAct.lessThanOrEqual=" + DEFAULT_FECHA_ACT);

        // Get all the catSucursalList where fechaAct is less than or equal to SMALLER_FECHA_ACT
        defaultCatSucursalShouldNotBeFound("fechaAct.lessThanOrEqual=" + SMALLER_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllCatSucursalsByFechaActIsLessThanSomething() throws Exception {
        // Initialize the database
        catSucursalRepository.saveAndFlush(catSucursal);

        // Get all the catSucursalList where fechaAct is less than DEFAULT_FECHA_ACT
        defaultCatSucursalShouldNotBeFound("fechaAct.lessThan=" + DEFAULT_FECHA_ACT);

        // Get all the catSucursalList where fechaAct is less than UPDATED_FECHA_ACT
        defaultCatSucursalShouldBeFound("fechaAct.lessThan=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllCatSucursalsByFechaActIsGreaterThanSomething() throws Exception {
        // Initialize the database
        catSucursalRepository.saveAndFlush(catSucursal);

        // Get all the catSucursalList where fechaAct is greater than DEFAULT_FECHA_ACT
        defaultCatSucursalShouldNotBeFound("fechaAct.greaterThan=" + DEFAULT_FECHA_ACT);

        // Get all the catSucursalList where fechaAct is greater than SMALLER_FECHA_ACT
        defaultCatSucursalShouldBeFound("fechaAct.greaterThan=" + SMALLER_FECHA_ACT);
    }


    @Test
    @Transactional
    public void getAllCatSucursalsByUsuarioIsEqualToSomething() throws Exception {
        // Get already existing entity
        User usuario = catSucursal.getUsuario();
        catSucursalRepository.saveAndFlush(catSucursal);
        Long usuarioId = usuario.getId();

        // Get all the catSucursalList where usuario equals to usuarioId
        defaultCatSucursalShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the catSucursalList where usuario equals to usuarioId + 1
        defaultCatSucursalShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCatSucursalShouldBeFound(String filter) throws Exception {
        restCatSucursalMockMvc.perform(get("/api/cat-sucursals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catSucursal.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].fechaAct").value(hasItem(DEFAULT_FECHA_ACT.toString())));

        // Check, that the count call also returns 1
        restCatSucursalMockMvc.perform(get("/api/cat-sucursals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCatSucursalShouldNotBeFound(String filter) throws Exception {
        restCatSucursalMockMvc.perform(get("/api/cat-sucursals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCatSucursalMockMvc.perform(get("/api/cat-sucursals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCatSucursal() throws Exception {
        // Get the catSucursal
        restCatSucursalMockMvc.perform(get("/api/cat-sucursals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCatSucursal() throws Exception {
        // Initialize the database
        catSucursalService.save(catSucursal);

        int databaseSizeBeforeUpdate = catSucursalRepository.findAll().size();

        // Update the catSucursal
        CatSucursal updatedCatSucursal = catSucursalRepository.findById(catSucursal.getId()).get();
        // Disconnect from session so that the updates on updatedCatSucursal are not directly saved in db
        em.detach(updatedCatSucursal);
        updatedCatSucursal
            .nombre(UPDATED_NOMBRE)
            .direccion(UPDATED_DIRECCION)
            .telefono(UPDATED_TELEFONO)
            .fechaAct(UPDATED_FECHA_ACT);

        restCatSucursalMockMvc.perform(put("/api/cat-sucursals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCatSucursal)))
            .andExpect(status().isOk());

        // Validate the CatSucursal in the database
        List<CatSucursal> catSucursalList = catSucursalRepository.findAll();
        assertThat(catSucursalList).hasSize(databaseSizeBeforeUpdate);
        CatSucursal testCatSucursal = catSucursalList.get(catSucursalList.size() - 1);
        assertThat(testCatSucursal.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCatSucursal.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testCatSucursal.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testCatSucursal.getFechaAct()).isEqualTo(UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void updateNonExistingCatSucursal() throws Exception {
        int databaseSizeBeforeUpdate = catSucursalRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatSucursalMockMvc.perform(put("/api/cat-sucursals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catSucursal)))
            .andExpect(status().isBadRequest());

        // Validate the CatSucursal in the database
        List<CatSucursal> catSucursalList = catSucursalRepository.findAll();
        assertThat(catSucursalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCatSucursal() throws Exception {
        // Initialize the database
        catSucursalService.save(catSucursal);

        int databaseSizeBeforeDelete = catSucursalRepository.findAll().size();

        // Delete the catSucursal
        restCatSucursalMockMvc.perform(delete("/api/cat-sucursals/{id}", catSucursal.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CatSucursal> catSucursalList = catSucursalRepository.findAll();
        assertThat(catSucursalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
