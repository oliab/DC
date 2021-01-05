package com.resamx.web.rest;

import com.resamx.ResaMxWebApp;
import com.resamx.domain.DomicilioEmpresa;
import com.resamx.domain.CatNacionalidad;
import com.resamx.domain.CatPais;
import com.resamx.domain.CatEstado;
import com.resamx.domain.CatMunicipio;
import com.resamx.domain.CatLocalidad;
import com.resamx.domain.CatCP;
import com.resamx.domain.User;
import com.resamx.repository.DomicilioEmpresaRepository;
import com.resamx.service.DomicilioEmpresaService;
import com.resamx.service.dto.DomicilioEmpresaCriteria;
import com.resamx.service.DomicilioEmpresaQueryService;

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
 * Integration tests for the {@link DomicilioEmpresaResource} REST controller.
 */
@SpringBootTest(classes = ResaMxWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DomicilioEmpresaResourceIT {

    private static final String DEFAULT_COLONIA = "AAAAAAAAAA";
    private static final String UPDATED_COLONIA = "BBBBBBBBBB";

    private static final String DEFAULT_CALLE = "AAAAAAAAAA";
    private static final String UPDATED_CALLE = "BBBBBBBBBB";

    private static final String DEFAULT_NO_EXT = "AAAAAAAAAA";
    private static final String UPDATED_NO_EXT = "BBBBBBBBBB";

    private static final String DEFAULT_NO_INT = "AAAAAAAAAA";
    private static final String UPDATED_NO_INT = "BBBBBBBBBB";

    private static final String DEFAULT_DOMICILIO = "AAAAAAAAAA";
    private static final String UPDATED_DOMICILIO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_ACT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ACT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_ACT = LocalDate.ofEpochDay(-1L);

    @Autowired
    private DomicilioEmpresaRepository domicilioEmpresaRepository;

    @Autowired
    private DomicilioEmpresaService domicilioEmpresaService;

    @Autowired
    private DomicilioEmpresaQueryService domicilioEmpresaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDomicilioEmpresaMockMvc;

    private DomicilioEmpresa domicilioEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DomicilioEmpresa createEntity(EntityManager em) {
        DomicilioEmpresa domicilioEmpresa = new DomicilioEmpresa()
            .colonia(DEFAULT_COLONIA)
            .calle(DEFAULT_CALLE)
            .noExt(DEFAULT_NO_EXT)
            .noInt(DEFAULT_NO_INT)
            .domicilio(DEFAULT_DOMICILIO)
            .fechaAct(DEFAULT_FECHA_ACT);
        // Add required entity
        CatNacionalidad catNacionalidad;
        if (TestUtil.findAll(em, CatNacionalidad.class).isEmpty()) {
            catNacionalidad = CatNacionalidadResourceIT.createEntity(em);
            em.persist(catNacionalidad);
            em.flush();
        } else {
            catNacionalidad = TestUtil.findAll(em, CatNacionalidad.class).get(0);
        }
        domicilioEmpresa.setNacionalidad(catNacionalidad);
        // Add required entity
        CatPais catPais;
        if (TestUtil.findAll(em, CatPais.class).isEmpty()) {
            catPais = CatPaisResourceIT.createEntity(em);
            em.persist(catPais);
            em.flush();
        } else {
            catPais = TestUtil.findAll(em, CatPais.class).get(0);
        }
        domicilioEmpresa.setPaisOrigen(catPais);
        // Add required entity
        domicilioEmpresa.setPais(catPais);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        domicilioEmpresa.setUser(user);
        return domicilioEmpresa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DomicilioEmpresa createUpdatedEntity(EntityManager em) {
        DomicilioEmpresa domicilioEmpresa = new DomicilioEmpresa()
            .colonia(UPDATED_COLONIA)
            .calle(UPDATED_CALLE)
            .noExt(UPDATED_NO_EXT)
            .noInt(UPDATED_NO_INT)
            .domicilio(UPDATED_DOMICILIO)
            .fechaAct(UPDATED_FECHA_ACT);
        // Add required entity
        CatNacionalidad catNacionalidad;
        if (TestUtil.findAll(em, CatNacionalidad.class).isEmpty()) {
            catNacionalidad = CatNacionalidadResourceIT.createUpdatedEntity(em);
            em.persist(catNacionalidad);
            em.flush();
        } else {
            catNacionalidad = TestUtil.findAll(em, CatNacionalidad.class).get(0);
        }
        domicilioEmpresa.setNacionalidad(catNacionalidad);
        // Add required entity
        CatPais catPais;
        if (TestUtil.findAll(em, CatPais.class).isEmpty()) {
            catPais = CatPaisResourceIT.createUpdatedEntity(em);
            em.persist(catPais);
            em.flush();
        } else {
            catPais = TestUtil.findAll(em, CatPais.class).get(0);
        }
        domicilioEmpresa.setPaisOrigen(catPais);
        // Add required entity
        domicilioEmpresa.setPais(catPais);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        domicilioEmpresa.setUser(user);
        return domicilioEmpresa;
    }

    @BeforeEach
    public void initTest() {
        domicilioEmpresa = createEntity(em);
    }

    @Test
    @Transactional
    public void createDomicilioEmpresa() throws Exception {
        int databaseSizeBeforeCreate = domicilioEmpresaRepository.findAll().size();
        // Create the DomicilioEmpresa
        restDomicilioEmpresaMockMvc.perform(post("/api/domicilio-empresas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(domicilioEmpresa)))
            .andExpect(status().isCreated());

        // Validate the DomicilioEmpresa in the database
        List<DomicilioEmpresa> domicilioEmpresaList = domicilioEmpresaRepository.findAll();
        assertThat(domicilioEmpresaList).hasSize(databaseSizeBeforeCreate + 1);
        DomicilioEmpresa testDomicilioEmpresa = domicilioEmpresaList.get(domicilioEmpresaList.size() - 1);
        assertThat(testDomicilioEmpresa.getColonia()).isEqualTo(DEFAULT_COLONIA);
        assertThat(testDomicilioEmpresa.getCalle()).isEqualTo(DEFAULT_CALLE);
        assertThat(testDomicilioEmpresa.getNoExt()).isEqualTo(DEFAULT_NO_EXT);
        assertThat(testDomicilioEmpresa.getNoInt()).isEqualTo(DEFAULT_NO_INT);
        assertThat(testDomicilioEmpresa.getDomicilio()).isEqualTo(DEFAULT_DOMICILIO);
        assertThat(testDomicilioEmpresa.getFechaAct()).isEqualTo(DEFAULT_FECHA_ACT);
    }

    @Test
    @Transactional
    public void createDomicilioEmpresaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = domicilioEmpresaRepository.findAll().size();

        // Create the DomicilioEmpresa with an existing ID
        domicilioEmpresa.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDomicilioEmpresaMockMvc.perform(post("/api/domicilio-empresas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(domicilioEmpresa)))
            .andExpect(status().isBadRequest());

        // Validate the DomicilioEmpresa in the database
        List<DomicilioEmpresa> domicilioEmpresaList = domicilioEmpresaRepository.findAll();
        assertThat(domicilioEmpresaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDomicilioEmpresas() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList
        restDomicilioEmpresaMockMvc.perform(get("/api/domicilio-empresas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(domicilioEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].colonia").value(hasItem(DEFAULT_COLONIA)))
            .andExpect(jsonPath("$.[*].calle").value(hasItem(DEFAULT_CALLE)))
            .andExpect(jsonPath("$.[*].noExt").value(hasItem(DEFAULT_NO_EXT)))
            .andExpect(jsonPath("$.[*].noInt").value(hasItem(DEFAULT_NO_INT)))
            .andExpect(jsonPath("$.[*].domicilio").value(hasItem(DEFAULT_DOMICILIO)))
            .andExpect(jsonPath("$.[*].fechaAct").value(hasItem(DEFAULT_FECHA_ACT.toString())));
    }
    
    @Test
    @Transactional
    public void getDomicilioEmpresa() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get the domicilioEmpresa
        restDomicilioEmpresaMockMvc.perform(get("/api/domicilio-empresas/{id}", domicilioEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(domicilioEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.colonia").value(DEFAULT_COLONIA))
            .andExpect(jsonPath("$.calle").value(DEFAULT_CALLE))
            .andExpect(jsonPath("$.noExt").value(DEFAULT_NO_EXT))
            .andExpect(jsonPath("$.noInt").value(DEFAULT_NO_INT))
            .andExpect(jsonPath("$.domicilio").value(DEFAULT_DOMICILIO))
            .andExpect(jsonPath("$.fechaAct").value(DEFAULT_FECHA_ACT.toString()));
    }


    @Test
    @Transactional
    public void getDomicilioEmpresasByIdFiltering() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        Long id = domicilioEmpresa.getId();

        defaultDomicilioEmpresaShouldBeFound("id.equals=" + id);
        defaultDomicilioEmpresaShouldNotBeFound("id.notEquals=" + id);

        defaultDomicilioEmpresaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDomicilioEmpresaShouldNotBeFound("id.greaterThan=" + id);

        defaultDomicilioEmpresaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDomicilioEmpresaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDomicilioEmpresasByColoniaIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where colonia equals to DEFAULT_COLONIA
        defaultDomicilioEmpresaShouldBeFound("colonia.equals=" + DEFAULT_COLONIA);

        // Get all the domicilioEmpresaList where colonia equals to UPDATED_COLONIA
        defaultDomicilioEmpresaShouldNotBeFound("colonia.equals=" + UPDATED_COLONIA);
    }

    @Test
    @Transactional
    public void getAllDomicilioEmpresasByColoniaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where colonia not equals to DEFAULT_COLONIA
        defaultDomicilioEmpresaShouldNotBeFound("colonia.notEquals=" + DEFAULT_COLONIA);

        // Get all the domicilioEmpresaList where colonia not equals to UPDATED_COLONIA
        defaultDomicilioEmpresaShouldBeFound("colonia.notEquals=" + UPDATED_COLONIA);
    }

    @Test
    @Transactional
    public void getAllDomicilioEmpresasByColoniaIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where colonia in DEFAULT_COLONIA or UPDATED_COLONIA
        defaultDomicilioEmpresaShouldBeFound("colonia.in=" + DEFAULT_COLONIA + "," + UPDATED_COLONIA);

        // Get all the domicilioEmpresaList where colonia equals to UPDATED_COLONIA
        defaultDomicilioEmpresaShouldNotBeFound("colonia.in=" + UPDATED_COLONIA);
    }

    @Test
    @Transactional
    public void getAllDomicilioEmpresasByColoniaIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where colonia is not null
        defaultDomicilioEmpresaShouldBeFound("colonia.specified=true");

        // Get all the domicilioEmpresaList where colonia is null
        defaultDomicilioEmpresaShouldNotBeFound("colonia.specified=false");
    }
                @Test
    @Transactional
    public void getAllDomicilioEmpresasByColoniaContainsSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where colonia contains DEFAULT_COLONIA
        defaultDomicilioEmpresaShouldBeFound("colonia.contains=" + DEFAULT_COLONIA);

        // Get all the domicilioEmpresaList where colonia contains UPDATED_COLONIA
        defaultDomicilioEmpresaShouldNotBeFound("colonia.contains=" + UPDATED_COLONIA);
    }

    @Test
    @Transactional
    public void getAllDomicilioEmpresasByColoniaNotContainsSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where colonia does not contain DEFAULT_COLONIA
        defaultDomicilioEmpresaShouldNotBeFound("colonia.doesNotContain=" + DEFAULT_COLONIA);

        // Get all the domicilioEmpresaList where colonia does not contain UPDATED_COLONIA
        defaultDomicilioEmpresaShouldBeFound("colonia.doesNotContain=" + UPDATED_COLONIA);
    }


    @Test
    @Transactional
    public void getAllDomicilioEmpresasByCalleIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where calle equals to DEFAULT_CALLE
        defaultDomicilioEmpresaShouldBeFound("calle.equals=" + DEFAULT_CALLE);

        // Get all the domicilioEmpresaList where calle equals to UPDATED_CALLE
        defaultDomicilioEmpresaShouldNotBeFound("calle.equals=" + UPDATED_CALLE);
    }

    @Test
    @Transactional
    public void getAllDomicilioEmpresasByCalleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where calle not equals to DEFAULT_CALLE
        defaultDomicilioEmpresaShouldNotBeFound("calle.notEquals=" + DEFAULT_CALLE);

        // Get all the domicilioEmpresaList where calle not equals to UPDATED_CALLE
        defaultDomicilioEmpresaShouldBeFound("calle.notEquals=" + UPDATED_CALLE);
    }

    @Test
    @Transactional
    public void getAllDomicilioEmpresasByCalleIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where calle in DEFAULT_CALLE or UPDATED_CALLE
        defaultDomicilioEmpresaShouldBeFound("calle.in=" + DEFAULT_CALLE + "," + UPDATED_CALLE);

        // Get all the domicilioEmpresaList where calle equals to UPDATED_CALLE
        defaultDomicilioEmpresaShouldNotBeFound("calle.in=" + UPDATED_CALLE);
    }

    @Test
    @Transactional
    public void getAllDomicilioEmpresasByCalleIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where calle is not null
        defaultDomicilioEmpresaShouldBeFound("calle.specified=true");

        // Get all the domicilioEmpresaList where calle is null
        defaultDomicilioEmpresaShouldNotBeFound("calle.specified=false");
    }
                @Test
    @Transactional
    public void getAllDomicilioEmpresasByCalleContainsSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where calle contains DEFAULT_CALLE
        defaultDomicilioEmpresaShouldBeFound("calle.contains=" + DEFAULT_CALLE);

        // Get all the domicilioEmpresaList where calle contains UPDATED_CALLE
        defaultDomicilioEmpresaShouldNotBeFound("calle.contains=" + UPDATED_CALLE);
    }

    @Test
    @Transactional
    public void getAllDomicilioEmpresasByCalleNotContainsSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where calle does not contain DEFAULT_CALLE
        defaultDomicilioEmpresaShouldNotBeFound("calle.doesNotContain=" + DEFAULT_CALLE);

        // Get all the domicilioEmpresaList where calle does not contain UPDATED_CALLE
        defaultDomicilioEmpresaShouldBeFound("calle.doesNotContain=" + UPDATED_CALLE);
    }


    @Test
    @Transactional
    public void getAllDomicilioEmpresasByNoExtIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where noExt equals to DEFAULT_NO_EXT
        defaultDomicilioEmpresaShouldBeFound("noExt.equals=" + DEFAULT_NO_EXT);

        // Get all the domicilioEmpresaList where noExt equals to UPDATED_NO_EXT
        defaultDomicilioEmpresaShouldNotBeFound("noExt.equals=" + UPDATED_NO_EXT);
    }

    @Test
    @Transactional
    public void getAllDomicilioEmpresasByNoExtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where noExt not equals to DEFAULT_NO_EXT
        defaultDomicilioEmpresaShouldNotBeFound("noExt.notEquals=" + DEFAULT_NO_EXT);

        // Get all the domicilioEmpresaList where noExt not equals to UPDATED_NO_EXT
        defaultDomicilioEmpresaShouldBeFound("noExt.notEquals=" + UPDATED_NO_EXT);
    }

    @Test
    @Transactional
    public void getAllDomicilioEmpresasByNoExtIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where noExt in DEFAULT_NO_EXT or UPDATED_NO_EXT
        defaultDomicilioEmpresaShouldBeFound("noExt.in=" + DEFAULT_NO_EXT + "," + UPDATED_NO_EXT);

        // Get all the domicilioEmpresaList where noExt equals to UPDATED_NO_EXT
        defaultDomicilioEmpresaShouldNotBeFound("noExt.in=" + UPDATED_NO_EXT);
    }

    @Test
    @Transactional
    public void getAllDomicilioEmpresasByNoExtIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where noExt is not null
        defaultDomicilioEmpresaShouldBeFound("noExt.specified=true");

        // Get all the domicilioEmpresaList where noExt is null
        defaultDomicilioEmpresaShouldNotBeFound("noExt.specified=false");
    }
                @Test
    @Transactional
    public void getAllDomicilioEmpresasByNoExtContainsSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where noExt contains DEFAULT_NO_EXT
        defaultDomicilioEmpresaShouldBeFound("noExt.contains=" + DEFAULT_NO_EXT);

        // Get all the domicilioEmpresaList where noExt contains UPDATED_NO_EXT
        defaultDomicilioEmpresaShouldNotBeFound("noExt.contains=" + UPDATED_NO_EXT);
    }

    @Test
    @Transactional
    public void getAllDomicilioEmpresasByNoExtNotContainsSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where noExt does not contain DEFAULT_NO_EXT
        defaultDomicilioEmpresaShouldNotBeFound("noExt.doesNotContain=" + DEFAULT_NO_EXT);

        // Get all the domicilioEmpresaList where noExt does not contain UPDATED_NO_EXT
        defaultDomicilioEmpresaShouldBeFound("noExt.doesNotContain=" + UPDATED_NO_EXT);
    }


    @Test
    @Transactional
    public void getAllDomicilioEmpresasByNoIntIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where noInt equals to DEFAULT_NO_INT
        defaultDomicilioEmpresaShouldBeFound("noInt.equals=" + DEFAULT_NO_INT);

        // Get all the domicilioEmpresaList where noInt equals to UPDATED_NO_INT
        defaultDomicilioEmpresaShouldNotBeFound("noInt.equals=" + UPDATED_NO_INT);
    }

    @Test
    @Transactional
    public void getAllDomicilioEmpresasByNoIntIsNotEqualToSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where noInt not equals to DEFAULT_NO_INT
        defaultDomicilioEmpresaShouldNotBeFound("noInt.notEquals=" + DEFAULT_NO_INT);

        // Get all the domicilioEmpresaList where noInt not equals to UPDATED_NO_INT
        defaultDomicilioEmpresaShouldBeFound("noInt.notEquals=" + UPDATED_NO_INT);
    }

    @Test
    @Transactional
    public void getAllDomicilioEmpresasByNoIntIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where noInt in DEFAULT_NO_INT or UPDATED_NO_INT
        defaultDomicilioEmpresaShouldBeFound("noInt.in=" + DEFAULT_NO_INT + "," + UPDATED_NO_INT);

        // Get all the domicilioEmpresaList where noInt equals to UPDATED_NO_INT
        defaultDomicilioEmpresaShouldNotBeFound("noInt.in=" + UPDATED_NO_INT);
    }

    @Test
    @Transactional
    public void getAllDomicilioEmpresasByNoIntIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where noInt is not null
        defaultDomicilioEmpresaShouldBeFound("noInt.specified=true");

        // Get all the domicilioEmpresaList where noInt is null
        defaultDomicilioEmpresaShouldNotBeFound("noInt.specified=false");
    }
                @Test
    @Transactional
    public void getAllDomicilioEmpresasByNoIntContainsSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where noInt contains DEFAULT_NO_INT
        defaultDomicilioEmpresaShouldBeFound("noInt.contains=" + DEFAULT_NO_INT);

        // Get all the domicilioEmpresaList where noInt contains UPDATED_NO_INT
        defaultDomicilioEmpresaShouldNotBeFound("noInt.contains=" + UPDATED_NO_INT);
    }

    @Test
    @Transactional
    public void getAllDomicilioEmpresasByNoIntNotContainsSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where noInt does not contain DEFAULT_NO_INT
        defaultDomicilioEmpresaShouldNotBeFound("noInt.doesNotContain=" + DEFAULT_NO_INT);

        // Get all the domicilioEmpresaList where noInt does not contain UPDATED_NO_INT
        defaultDomicilioEmpresaShouldBeFound("noInt.doesNotContain=" + UPDATED_NO_INT);
    }


    @Test
    @Transactional
    public void getAllDomicilioEmpresasByDomicilioIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where domicilio equals to DEFAULT_DOMICILIO
        defaultDomicilioEmpresaShouldBeFound("domicilio.equals=" + DEFAULT_DOMICILIO);

        // Get all the domicilioEmpresaList where domicilio equals to UPDATED_DOMICILIO
        defaultDomicilioEmpresaShouldNotBeFound("domicilio.equals=" + UPDATED_DOMICILIO);
    }

    @Test
    @Transactional
    public void getAllDomicilioEmpresasByDomicilioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where domicilio not equals to DEFAULT_DOMICILIO
        defaultDomicilioEmpresaShouldNotBeFound("domicilio.notEquals=" + DEFAULT_DOMICILIO);

        // Get all the domicilioEmpresaList where domicilio not equals to UPDATED_DOMICILIO
        defaultDomicilioEmpresaShouldBeFound("domicilio.notEquals=" + UPDATED_DOMICILIO);
    }

    @Test
    @Transactional
    public void getAllDomicilioEmpresasByDomicilioIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where domicilio in DEFAULT_DOMICILIO or UPDATED_DOMICILIO
        defaultDomicilioEmpresaShouldBeFound("domicilio.in=" + DEFAULT_DOMICILIO + "," + UPDATED_DOMICILIO);

        // Get all the domicilioEmpresaList where domicilio equals to UPDATED_DOMICILIO
        defaultDomicilioEmpresaShouldNotBeFound("domicilio.in=" + UPDATED_DOMICILIO);
    }

    @Test
    @Transactional
    public void getAllDomicilioEmpresasByDomicilioIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where domicilio is not null
        defaultDomicilioEmpresaShouldBeFound("domicilio.specified=true");

        // Get all the domicilioEmpresaList where domicilio is null
        defaultDomicilioEmpresaShouldNotBeFound("domicilio.specified=false");
    }
                @Test
    @Transactional
    public void getAllDomicilioEmpresasByDomicilioContainsSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where domicilio contains DEFAULT_DOMICILIO
        defaultDomicilioEmpresaShouldBeFound("domicilio.contains=" + DEFAULT_DOMICILIO);

        // Get all the domicilioEmpresaList where domicilio contains UPDATED_DOMICILIO
        defaultDomicilioEmpresaShouldNotBeFound("domicilio.contains=" + UPDATED_DOMICILIO);
    }

    @Test
    @Transactional
    public void getAllDomicilioEmpresasByDomicilioNotContainsSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where domicilio does not contain DEFAULT_DOMICILIO
        defaultDomicilioEmpresaShouldNotBeFound("domicilio.doesNotContain=" + DEFAULT_DOMICILIO);

        // Get all the domicilioEmpresaList where domicilio does not contain UPDATED_DOMICILIO
        defaultDomicilioEmpresaShouldBeFound("domicilio.doesNotContain=" + UPDATED_DOMICILIO);
    }


    @Test
    @Transactional
    public void getAllDomicilioEmpresasByFechaActIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where fechaAct equals to DEFAULT_FECHA_ACT
        defaultDomicilioEmpresaShouldBeFound("fechaAct.equals=" + DEFAULT_FECHA_ACT);

        // Get all the domicilioEmpresaList where fechaAct equals to UPDATED_FECHA_ACT
        defaultDomicilioEmpresaShouldNotBeFound("fechaAct.equals=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllDomicilioEmpresasByFechaActIsNotEqualToSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where fechaAct not equals to DEFAULT_FECHA_ACT
        defaultDomicilioEmpresaShouldNotBeFound("fechaAct.notEquals=" + DEFAULT_FECHA_ACT);

        // Get all the domicilioEmpresaList where fechaAct not equals to UPDATED_FECHA_ACT
        defaultDomicilioEmpresaShouldBeFound("fechaAct.notEquals=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllDomicilioEmpresasByFechaActIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where fechaAct in DEFAULT_FECHA_ACT or UPDATED_FECHA_ACT
        defaultDomicilioEmpresaShouldBeFound("fechaAct.in=" + DEFAULT_FECHA_ACT + "," + UPDATED_FECHA_ACT);

        // Get all the domicilioEmpresaList where fechaAct equals to UPDATED_FECHA_ACT
        defaultDomicilioEmpresaShouldNotBeFound("fechaAct.in=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllDomicilioEmpresasByFechaActIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where fechaAct is not null
        defaultDomicilioEmpresaShouldBeFound("fechaAct.specified=true");

        // Get all the domicilioEmpresaList where fechaAct is null
        defaultDomicilioEmpresaShouldNotBeFound("fechaAct.specified=false");
    }

    @Test
    @Transactional
    public void getAllDomicilioEmpresasByFechaActIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where fechaAct is greater than or equal to DEFAULT_FECHA_ACT
        defaultDomicilioEmpresaShouldBeFound("fechaAct.greaterThanOrEqual=" + DEFAULT_FECHA_ACT);

        // Get all the domicilioEmpresaList where fechaAct is greater than or equal to UPDATED_FECHA_ACT
        defaultDomicilioEmpresaShouldNotBeFound("fechaAct.greaterThanOrEqual=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllDomicilioEmpresasByFechaActIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where fechaAct is less than or equal to DEFAULT_FECHA_ACT
        defaultDomicilioEmpresaShouldBeFound("fechaAct.lessThanOrEqual=" + DEFAULT_FECHA_ACT);

        // Get all the domicilioEmpresaList where fechaAct is less than or equal to SMALLER_FECHA_ACT
        defaultDomicilioEmpresaShouldNotBeFound("fechaAct.lessThanOrEqual=" + SMALLER_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllDomicilioEmpresasByFechaActIsLessThanSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where fechaAct is less than DEFAULT_FECHA_ACT
        defaultDomicilioEmpresaShouldNotBeFound("fechaAct.lessThan=" + DEFAULT_FECHA_ACT);

        // Get all the domicilioEmpresaList where fechaAct is less than UPDATED_FECHA_ACT
        defaultDomicilioEmpresaShouldBeFound("fechaAct.lessThan=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllDomicilioEmpresasByFechaActIsGreaterThanSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);

        // Get all the domicilioEmpresaList where fechaAct is greater than DEFAULT_FECHA_ACT
        defaultDomicilioEmpresaShouldNotBeFound("fechaAct.greaterThan=" + DEFAULT_FECHA_ACT);

        // Get all the domicilioEmpresaList where fechaAct is greater than SMALLER_FECHA_ACT
        defaultDomicilioEmpresaShouldBeFound("fechaAct.greaterThan=" + SMALLER_FECHA_ACT);
    }


    @Test
    @Transactional
    public void getAllDomicilioEmpresasByNacionalidadIsEqualToSomething() throws Exception {
        // Get already existing entity
        CatNacionalidad nacionalidad = domicilioEmpresa.getNacionalidad();
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);
        Long nacionalidadId = nacionalidad.getId();

        // Get all the domicilioEmpresaList where nacionalidad equals to nacionalidadId
        defaultDomicilioEmpresaShouldBeFound("nacionalidadId.equals=" + nacionalidadId);

        // Get all the domicilioEmpresaList where nacionalidad equals to nacionalidadId + 1
        defaultDomicilioEmpresaShouldNotBeFound("nacionalidadId.equals=" + (nacionalidadId + 1));
    }


    @Test
    @Transactional
    public void getAllDomicilioEmpresasByPaisOrigenIsEqualToSomething() throws Exception {
        // Get already existing entity
        CatPais paisOrigen = domicilioEmpresa.getPaisOrigen();
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);
        String paisOrigenId = paisOrigen.getId();

        // Get all the domicilioEmpresaList where paisOrigen equals to paisOrigenId
        defaultDomicilioEmpresaShouldBeFound("paisOrigenId.equals=" + paisOrigenId);

        // Get all the domicilioEmpresaList where paisOrigen equals to paisOrigenId + 1
        defaultDomicilioEmpresaShouldNotBeFound("paisOrigenId.equals=" + (paisOrigenId + 1));
    }


    @Test
    @Transactional
    public void getAllDomicilioEmpresasByPaisIsEqualToSomething() throws Exception {
        // Get already existing entity
        CatPais pais = domicilioEmpresa.getPais();
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);
        String paisId = pais.getId();

        // Get all the domicilioEmpresaList where pais equals to paisId
        defaultDomicilioEmpresaShouldBeFound("paisId.equals=" + paisId);

        // Get all the domicilioEmpresaList where pais equals to paisId + 1
        defaultDomicilioEmpresaShouldNotBeFound("paisId.equals=" + (paisId + 1));
    }


    @Test
    @Transactional
    public void getAllDomicilioEmpresasByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);
        CatEstado estado = CatEstadoResourceIT.createEntity(em);
        em.persist(estado);
        em.flush();
        domicilioEmpresa.setEstado(estado);
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);
        String estadoId = estado.getId();

        // Get all the domicilioEmpresaList where estado equals to estadoId
        defaultDomicilioEmpresaShouldBeFound("estadoId.equals=" + estadoId);

        // Get all the domicilioEmpresaList where estado equals to estadoId + 1
        defaultDomicilioEmpresaShouldNotBeFound("estadoId.equals=" + (estadoId + 1));
    }


    @Test
    @Transactional
    public void getAllDomicilioEmpresasByMunicipioIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);
        CatMunicipio municipio = CatMunicipioResourceIT.createEntity(em);
        em.persist(municipio);
        em.flush();
        domicilioEmpresa.setMunicipio(municipio);
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);
        String municipioId = municipio.getId();

        // Get all the domicilioEmpresaList where municipio equals to municipioId
        defaultDomicilioEmpresaShouldBeFound("municipioId.equals=" + municipioId);

        // Get all the domicilioEmpresaList where municipio equals to municipioId + 1
        defaultDomicilioEmpresaShouldNotBeFound("municipioId.equals=" + (municipioId + 1));
    }


    @Test
    @Transactional
    public void getAllDomicilioEmpresasByLocalidadIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);
        CatLocalidad localidad = CatLocalidadResourceIT.createEntity(em);
        em.persist(localidad);
        em.flush();
        domicilioEmpresa.setLocalidad(localidad);
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);
        String localidadId = localidad.getId();

        // Get all the domicilioEmpresaList where localidad equals to localidadId
        defaultDomicilioEmpresaShouldBeFound("localidadId.equals=" + localidadId);

        // Get all the domicilioEmpresaList where localidad equals to localidadId + 1
        defaultDomicilioEmpresaShouldNotBeFound("localidadId.equals=" + (localidadId + 1));
    }


    @Test
    @Transactional
    public void getAllDomicilioEmpresasByCpIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);
        CatCP cp = CatCPResourceIT.createEntity(em);
        em.persist(cp);
        em.flush();
        domicilioEmpresa.setCp(cp);
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);
        String cpId = cp.getId();

        // Get all the domicilioEmpresaList where cp equals to cpId
        defaultDomicilioEmpresaShouldBeFound("cpId.equals=" + cpId);

        // Get all the domicilioEmpresaList where cp equals to cpId + 1
        defaultDomicilioEmpresaShouldNotBeFound("cpId.equals=" + (cpId + 1));
    }


    @Test
    @Transactional
    public void getAllDomicilioEmpresasByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = domicilioEmpresa.getUser();
        domicilioEmpresaRepository.saveAndFlush(domicilioEmpresa);
        Long userId = user.getId();

        // Get all the domicilioEmpresaList where user equals to userId
        defaultDomicilioEmpresaShouldBeFound("userId.equals=" + userId);

        // Get all the domicilioEmpresaList where user equals to userId + 1
        defaultDomicilioEmpresaShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDomicilioEmpresaShouldBeFound(String filter) throws Exception {
        restDomicilioEmpresaMockMvc.perform(get("/api/domicilio-empresas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(domicilioEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].colonia").value(hasItem(DEFAULT_COLONIA)))
            .andExpect(jsonPath("$.[*].calle").value(hasItem(DEFAULT_CALLE)))
            .andExpect(jsonPath("$.[*].noExt").value(hasItem(DEFAULT_NO_EXT)))
            .andExpect(jsonPath("$.[*].noInt").value(hasItem(DEFAULT_NO_INT)))
            .andExpect(jsonPath("$.[*].domicilio").value(hasItem(DEFAULT_DOMICILIO)))
            .andExpect(jsonPath("$.[*].fechaAct").value(hasItem(DEFAULT_FECHA_ACT.toString())));

        // Check, that the count call also returns 1
        restDomicilioEmpresaMockMvc.perform(get("/api/domicilio-empresas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDomicilioEmpresaShouldNotBeFound(String filter) throws Exception {
        restDomicilioEmpresaMockMvc.perform(get("/api/domicilio-empresas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDomicilioEmpresaMockMvc.perform(get("/api/domicilio-empresas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDomicilioEmpresa() throws Exception {
        // Get the domicilioEmpresa
        restDomicilioEmpresaMockMvc.perform(get("/api/domicilio-empresas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDomicilioEmpresa() throws Exception {
        // Initialize the database
        domicilioEmpresaService.save(domicilioEmpresa);

        int databaseSizeBeforeUpdate = domicilioEmpresaRepository.findAll().size();

        // Update the domicilioEmpresa
        DomicilioEmpresa updatedDomicilioEmpresa = domicilioEmpresaRepository.findById(domicilioEmpresa.getId()).get();
        // Disconnect from session so that the updates on updatedDomicilioEmpresa are not directly saved in db
        em.detach(updatedDomicilioEmpresa);
        updatedDomicilioEmpresa
            .colonia(UPDATED_COLONIA)
            .calle(UPDATED_CALLE)
            .noExt(UPDATED_NO_EXT)
            .noInt(UPDATED_NO_INT)
            .domicilio(UPDATED_DOMICILIO)
            .fechaAct(UPDATED_FECHA_ACT);

        restDomicilioEmpresaMockMvc.perform(put("/api/domicilio-empresas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDomicilioEmpresa)))
            .andExpect(status().isOk());

        // Validate the DomicilioEmpresa in the database
        List<DomicilioEmpresa> domicilioEmpresaList = domicilioEmpresaRepository.findAll();
        assertThat(domicilioEmpresaList).hasSize(databaseSizeBeforeUpdate);
        DomicilioEmpresa testDomicilioEmpresa = domicilioEmpresaList.get(domicilioEmpresaList.size() - 1);
        assertThat(testDomicilioEmpresa.getColonia()).isEqualTo(UPDATED_COLONIA);
        assertThat(testDomicilioEmpresa.getCalle()).isEqualTo(UPDATED_CALLE);
        assertThat(testDomicilioEmpresa.getNoExt()).isEqualTo(UPDATED_NO_EXT);
        assertThat(testDomicilioEmpresa.getNoInt()).isEqualTo(UPDATED_NO_INT);
        assertThat(testDomicilioEmpresa.getDomicilio()).isEqualTo(UPDATED_DOMICILIO);
        assertThat(testDomicilioEmpresa.getFechaAct()).isEqualTo(UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void updateNonExistingDomicilioEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = domicilioEmpresaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDomicilioEmpresaMockMvc.perform(put("/api/domicilio-empresas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(domicilioEmpresa)))
            .andExpect(status().isBadRequest());

        // Validate the DomicilioEmpresa in the database
        List<DomicilioEmpresa> domicilioEmpresaList = domicilioEmpresaRepository.findAll();
        assertThat(domicilioEmpresaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDomicilioEmpresa() throws Exception {
        // Initialize the database
        domicilioEmpresaService.save(domicilioEmpresa);

        int databaseSizeBeforeDelete = domicilioEmpresaRepository.findAll().size();

        // Delete the domicilioEmpresa
        restDomicilioEmpresaMockMvc.perform(delete("/api/domicilio-empresas/{id}", domicilioEmpresa.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DomicilioEmpresa> domicilioEmpresaList = domicilioEmpresaRepository.findAll();
        assertThat(domicilioEmpresaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
