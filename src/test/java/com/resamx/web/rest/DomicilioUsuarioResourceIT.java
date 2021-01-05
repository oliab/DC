package com.resamx.web.rest;

import com.resamx.ResaMxWebApp;
import com.resamx.domain.DomicilioUsuario;
import com.resamx.domain.CatNacionalidad;
import com.resamx.domain.CatPais;
import com.resamx.domain.CatEstado;
import com.resamx.domain.CatMunicipio;
import com.resamx.domain.CatLocalidad;
import com.resamx.domain.CatCP;
import com.resamx.domain.User;
import com.resamx.repository.DomicilioUsuarioRepository;
import com.resamx.service.DomicilioUsuarioService;
import com.resamx.service.dto.DomicilioUsuarioCriteria;
import com.resamx.service.DomicilioUsuarioQueryService;

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
 * Integration tests for the {@link DomicilioUsuarioResource} REST controller.
 */
@SpringBootTest(classes = ResaMxWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DomicilioUsuarioResourceIT {

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
    private DomicilioUsuarioRepository domicilioUsuarioRepository;

    @Autowired
    private DomicilioUsuarioService domicilioUsuarioService;

    @Autowired
    private DomicilioUsuarioQueryService domicilioUsuarioQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDomicilioUsuarioMockMvc;

    private DomicilioUsuario domicilioUsuario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DomicilioUsuario createEntity(EntityManager em) {
        DomicilioUsuario domicilioUsuario = new DomicilioUsuario()
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
        domicilioUsuario.setNacionalidad(catNacionalidad);
        // Add required entity
        CatPais catPais;
        if (TestUtil.findAll(em, CatPais.class).isEmpty()) {
            catPais = CatPaisResourceIT.createEntity(em);
            em.persist(catPais);
            em.flush();
        } else {
            catPais = TestUtil.findAll(em, CatPais.class).get(0);
        }
        domicilioUsuario.setPaisOrigen(catPais);
        // Add required entity
        domicilioUsuario.setPais(catPais);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        domicilioUsuario.setUser(user);
        return domicilioUsuario;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DomicilioUsuario createUpdatedEntity(EntityManager em) {
        DomicilioUsuario domicilioUsuario = new DomicilioUsuario()
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
        domicilioUsuario.setNacionalidad(catNacionalidad);
        // Add required entity
        CatPais catPais;
        if (TestUtil.findAll(em, CatPais.class).isEmpty()) {
            catPais = CatPaisResourceIT.createUpdatedEntity(em);
            em.persist(catPais);
            em.flush();
        } else {
            catPais = TestUtil.findAll(em, CatPais.class).get(0);
        }
        domicilioUsuario.setPaisOrigen(catPais);
        // Add required entity
        domicilioUsuario.setPais(catPais);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        domicilioUsuario.setUser(user);
        return domicilioUsuario;
    }

    @BeforeEach
    public void initTest() {
        domicilioUsuario = createEntity(em);
    }

    @Test
    @Transactional
    public void createDomicilioUsuario() throws Exception {
        int databaseSizeBeforeCreate = domicilioUsuarioRepository.findAll().size();
        // Create the DomicilioUsuario
        restDomicilioUsuarioMockMvc.perform(post("/api/domicilio-usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(domicilioUsuario)))
            .andExpect(status().isCreated());

        // Validate the DomicilioUsuario in the database
        List<DomicilioUsuario> domicilioUsuarioList = domicilioUsuarioRepository.findAll();
        assertThat(domicilioUsuarioList).hasSize(databaseSizeBeforeCreate + 1);
        DomicilioUsuario testDomicilioUsuario = domicilioUsuarioList.get(domicilioUsuarioList.size() - 1);
        assertThat(testDomicilioUsuario.getColonia()).isEqualTo(DEFAULT_COLONIA);
        assertThat(testDomicilioUsuario.getCalle()).isEqualTo(DEFAULT_CALLE);
        assertThat(testDomicilioUsuario.getNoExt()).isEqualTo(DEFAULT_NO_EXT);
        assertThat(testDomicilioUsuario.getNoInt()).isEqualTo(DEFAULT_NO_INT);
        assertThat(testDomicilioUsuario.getDomicilio()).isEqualTo(DEFAULT_DOMICILIO);
        assertThat(testDomicilioUsuario.getFechaAct()).isEqualTo(DEFAULT_FECHA_ACT);
    }

    @Test
    @Transactional
    public void createDomicilioUsuarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = domicilioUsuarioRepository.findAll().size();

        // Create the DomicilioUsuario with an existing ID
        domicilioUsuario.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDomicilioUsuarioMockMvc.perform(post("/api/domicilio-usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(domicilioUsuario)))
            .andExpect(status().isBadRequest());

        // Validate the DomicilioUsuario in the database
        List<DomicilioUsuario> domicilioUsuarioList = domicilioUsuarioRepository.findAll();
        assertThat(domicilioUsuarioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDomicilioUsuarios() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList
        restDomicilioUsuarioMockMvc.perform(get("/api/domicilio-usuarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(domicilioUsuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].colonia").value(hasItem(DEFAULT_COLONIA)))
            .andExpect(jsonPath("$.[*].calle").value(hasItem(DEFAULT_CALLE)))
            .andExpect(jsonPath("$.[*].noExt").value(hasItem(DEFAULT_NO_EXT)))
            .andExpect(jsonPath("$.[*].noInt").value(hasItem(DEFAULT_NO_INT)))
            .andExpect(jsonPath("$.[*].domicilio").value(hasItem(DEFAULT_DOMICILIO)))
            .andExpect(jsonPath("$.[*].fechaAct").value(hasItem(DEFAULT_FECHA_ACT.toString())));
    }
    
    @Test
    @Transactional
    public void getDomicilioUsuario() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get the domicilioUsuario
        restDomicilioUsuarioMockMvc.perform(get("/api/domicilio-usuarios/{id}", domicilioUsuario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(domicilioUsuario.getId().intValue()))
            .andExpect(jsonPath("$.colonia").value(DEFAULT_COLONIA))
            .andExpect(jsonPath("$.calle").value(DEFAULT_CALLE))
            .andExpect(jsonPath("$.noExt").value(DEFAULT_NO_EXT))
            .andExpect(jsonPath("$.noInt").value(DEFAULT_NO_INT))
            .andExpect(jsonPath("$.domicilio").value(DEFAULT_DOMICILIO))
            .andExpect(jsonPath("$.fechaAct").value(DEFAULT_FECHA_ACT.toString()));
    }


    @Test
    @Transactional
    public void getDomicilioUsuariosByIdFiltering() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        Long id = domicilioUsuario.getId();

        defaultDomicilioUsuarioShouldBeFound("id.equals=" + id);
        defaultDomicilioUsuarioShouldNotBeFound("id.notEquals=" + id);

        defaultDomicilioUsuarioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDomicilioUsuarioShouldNotBeFound("id.greaterThan=" + id);

        defaultDomicilioUsuarioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDomicilioUsuarioShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDomicilioUsuariosByColoniaIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where colonia equals to DEFAULT_COLONIA
        defaultDomicilioUsuarioShouldBeFound("colonia.equals=" + DEFAULT_COLONIA);

        // Get all the domicilioUsuarioList where colonia equals to UPDATED_COLONIA
        defaultDomicilioUsuarioShouldNotBeFound("colonia.equals=" + UPDATED_COLONIA);
    }

    @Test
    @Transactional
    public void getAllDomicilioUsuariosByColoniaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where colonia not equals to DEFAULT_COLONIA
        defaultDomicilioUsuarioShouldNotBeFound("colonia.notEquals=" + DEFAULT_COLONIA);

        // Get all the domicilioUsuarioList where colonia not equals to UPDATED_COLONIA
        defaultDomicilioUsuarioShouldBeFound("colonia.notEquals=" + UPDATED_COLONIA);
    }

    @Test
    @Transactional
    public void getAllDomicilioUsuariosByColoniaIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where colonia in DEFAULT_COLONIA or UPDATED_COLONIA
        defaultDomicilioUsuarioShouldBeFound("colonia.in=" + DEFAULT_COLONIA + "," + UPDATED_COLONIA);

        // Get all the domicilioUsuarioList where colonia equals to UPDATED_COLONIA
        defaultDomicilioUsuarioShouldNotBeFound("colonia.in=" + UPDATED_COLONIA);
    }

    @Test
    @Transactional
    public void getAllDomicilioUsuariosByColoniaIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where colonia is not null
        defaultDomicilioUsuarioShouldBeFound("colonia.specified=true");

        // Get all the domicilioUsuarioList where colonia is null
        defaultDomicilioUsuarioShouldNotBeFound("colonia.specified=false");
    }
                @Test
    @Transactional
    public void getAllDomicilioUsuariosByColoniaContainsSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where colonia contains DEFAULT_COLONIA
        defaultDomicilioUsuarioShouldBeFound("colonia.contains=" + DEFAULT_COLONIA);

        // Get all the domicilioUsuarioList where colonia contains UPDATED_COLONIA
        defaultDomicilioUsuarioShouldNotBeFound("colonia.contains=" + UPDATED_COLONIA);
    }

    @Test
    @Transactional
    public void getAllDomicilioUsuariosByColoniaNotContainsSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where colonia does not contain DEFAULT_COLONIA
        defaultDomicilioUsuarioShouldNotBeFound("colonia.doesNotContain=" + DEFAULT_COLONIA);

        // Get all the domicilioUsuarioList where colonia does not contain UPDATED_COLONIA
        defaultDomicilioUsuarioShouldBeFound("colonia.doesNotContain=" + UPDATED_COLONIA);
    }


    @Test
    @Transactional
    public void getAllDomicilioUsuariosByCalleIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where calle equals to DEFAULT_CALLE
        defaultDomicilioUsuarioShouldBeFound("calle.equals=" + DEFAULT_CALLE);

        // Get all the domicilioUsuarioList where calle equals to UPDATED_CALLE
        defaultDomicilioUsuarioShouldNotBeFound("calle.equals=" + UPDATED_CALLE);
    }

    @Test
    @Transactional
    public void getAllDomicilioUsuariosByCalleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where calle not equals to DEFAULT_CALLE
        defaultDomicilioUsuarioShouldNotBeFound("calle.notEquals=" + DEFAULT_CALLE);

        // Get all the domicilioUsuarioList where calle not equals to UPDATED_CALLE
        defaultDomicilioUsuarioShouldBeFound("calle.notEquals=" + UPDATED_CALLE);
    }

    @Test
    @Transactional
    public void getAllDomicilioUsuariosByCalleIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where calle in DEFAULT_CALLE or UPDATED_CALLE
        defaultDomicilioUsuarioShouldBeFound("calle.in=" + DEFAULT_CALLE + "," + UPDATED_CALLE);

        // Get all the domicilioUsuarioList where calle equals to UPDATED_CALLE
        defaultDomicilioUsuarioShouldNotBeFound("calle.in=" + UPDATED_CALLE);
    }

    @Test
    @Transactional
    public void getAllDomicilioUsuariosByCalleIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where calle is not null
        defaultDomicilioUsuarioShouldBeFound("calle.specified=true");

        // Get all the domicilioUsuarioList where calle is null
        defaultDomicilioUsuarioShouldNotBeFound("calle.specified=false");
    }
                @Test
    @Transactional
    public void getAllDomicilioUsuariosByCalleContainsSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where calle contains DEFAULT_CALLE
        defaultDomicilioUsuarioShouldBeFound("calle.contains=" + DEFAULT_CALLE);

        // Get all the domicilioUsuarioList where calle contains UPDATED_CALLE
        defaultDomicilioUsuarioShouldNotBeFound("calle.contains=" + UPDATED_CALLE);
    }

    @Test
    @Transactional
    public void getAllDomicilioUsuariosByCalleNotContainsSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where calle does not contain DEFAULT_CALLE
        defaultDomicilioUsuarioShouldNotBeFound("calle.doesNotContain=" + DEFAULT_CALLE);

        // Get all the domicilioUsuarioList where calle does not contain UPDATED_CALLE
        defaultDomicilioUsuarioShouldBeFound("calle.doesNotContain=" + UPDATED_CALLE);
    }


    @Test
    @Transactional
    public void getAllDomicilioUsuariosByNoExtIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where noExt equals to DEFAULT_NO_EXT
        defaultDomicilioUsuarioShouldBeFound("noExt.equals=" + DEFAULT_NO_EXT);

        // Get all the domicilioUsuarioList where noExt equals to UPDATED_NO_EXT
        defaultDomicilioUsuarioShouldNotBeFound("noExt.equals=" + UPDATED_NO_EXT);
    }

    @Test
    @Transactional
    public void getAllDomicilioUsuariosByNoExtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where noExt not equals to DEFAULT_NO_EXT
        defaultDomicilioUsuarioShouldNotBeFound("noExt.notEquals=" + DEFAULT_NO_EXT);

        // Get all the domicilioUsuarioList where noExt not equals to UPDATED_NO_EXT
        defaultDomicilioUsuarioShouldBeFound("noExt.notEquals=" + UPDATED_NO_EXT);
    }

    @Test
    @Transactional
    public void getAllDomicilioUsuariosByNoExtIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where noExt in DEFAULT_NO_EXT or UPDATED_NO_EXT
        defaultDomicilioUsuarioShouldBeFound("noExt.in=" + DEFAULT_NO_EXT + "," + UPDATED_NO_EXT);

        // Get all the domicilioUsuarioList where noExt equals to UPDATED_NO_EXT
        defaultDomicilioUsuarioShouldNotBeFound("noExt.in=" + UPDATED_NO_EXT);
    }

    @Test
    @Transactional
    public void getAllDomicilioUsuariosByNoExtIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where noExt is not null
        defaultDomicilioUsuarioShouldBeFound("noExt.specified=true");

        // Get all the domicilioUsuarioList where noExt is null
        defaultDomicilioUsuarioShouldNotBeFound("noExt.specified=false");
    }
                @Test
    @Transactional
    public void getAllDomicilioUsuariosByNoExtContainsSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where noExt contains DEFAULT_NO_EXT
        defaultDomicilioUsuarioShouldBeFound("noExt.contains=" + DEFAULT_NO_EXT);

        // Get all the domicilioUsuarioList where noExt contains UPDATED_NO_EXT
        defaultDomicilioUsuarioShouldNotBeFound("noExt.contains=" + UPDATED_NO_EXT);
    }

    @Test
    @Transactional
    public void getAllDomicilioUsuariosByNoExtNotContainsSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where noExt does not contain DEFAULT_NO_EXT
        defaultDomicilioUsuarioShouldNotBeFound("noExt.doesNotContain=" + DEFAULT_NO_EXT);

        // Get all the domicilioUsuarioList where noExt does not contain UPDATED_NO_EXT
        defaultDomicilioUsuarioShouldBeFound("noExt.doesNotContain=" + UPDATED_NO_EXT);
    }


    @Test
    @Transactional
    public void getAllDomicilioUsuariosByNoIntIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where noInt equals to DEFAULT_NO_INT
        defaultDomicilioUsuarioShouldBeFound("noInt.equals=" + DEFAULT_NO_INT);

        // Get all the domicilioUsuarioList where noInt equals to UPDATED_NO_INT
        defaultDomicilioUsuarioShouldNotBeFound("noInt.equals=" + UPDATED_NO_INT);
    }

    @Test
    @Transactional
    public void getAllDomicilioUsuariosByNoIntIsNotEqualToSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where noInt not equals to DEFAULT_NO_INT
        defaultDomicilioUsuarioShouldNotBeFound("noInt.notEquals=" + DEFAULT_NO_INT);

        // Get all the domicilioUsuarioList where noInt not equals to UPDATED_NO_INT
        defaultDomicilioUsuarioShouldBeFound("noInt.notEquals=" + UPDATED_NO_INT);
    }

    @Test
    @Transactional
    public void getAllDomicilioUsuariosByNoIntIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where noInt in DEFAULT_NO_INT or UPDATED_NO_INT
        defaultDomicilioUsuarioShouldBeFound("noInt.in=" + DEFAULT_NO_INT + "," + UPDATED_NO_INT);

        // Get all the domicilioUsuarioList where noInt equals to UPDATED_NO_INT
        defaultDomicilioUsuarioShouldNotBeFound("noInt.in=" + UPDATED_NO_INT);
    }

    @Test
    @Transactional
    public void getAllDomicilioUsuariosByNoIntIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where noInt is not null
        defaultDomicilioUsuarioShouldBeFound("noInt.specified=true");

        // Get all the domicilioUsuarioList where noInt is null
        defaultDomicilioUsuarioShouldNotBeFound("noInt.specified=false");
    }
                @Test
    @Transactional
    public void getAllDomicilioUsuariosByNoIntContainsSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where noInt contains DEFAULT_NO_INT
        defaultDomicilioUsuarioShouldBeFound("noInt.contains=" + DEFAULT_NO_INT);

        // Get all the domicilioUsuarioList where noInt contains UPDATED_NO_INT
        defaultDomicilioUsuarioShouldNotBeFound("noInt.contains=" + UPDATED_NO_INT);
    }

    @Test
    @Transactional
    public void getAllDomicilioUsuariosByNoIntNotContainsSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where noInt does not contain DEFAULT_NO_INT
        defaultDomicilioUsuarioShouldNotBeFound("noInt.doesNotContain=" + DEFAULT_NO_INT);

        // Get all the domicilioUsuarioList where noInt does not contain UPDATED_NO_INT
        defaultDomicilioUsuarioShouldBeFound("noInt.doesNotContain=" + UPDATED_NO_INT);
    }


    @Test
    @Transactional
    public void getAllDomicilioUsuariosByDomicilioIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where domicilio equals to DEFAULT_DOMICILIO
        defaultDomicilioUsuarioShouldBeFound("domicilio.equals=" + DEFAULT_DOMICILIO);

        // Get all the domicilioUsuarioList where domicilio equals to UPDATED_DOMICILIO
        defaultDomicilioUsuarioShouldNotBeFound("domicilio.equals=" + UPDATED_DOMICILIO);
    }

    @Test
    @Transactional
    public void getAllDomicilioUsuariosByDomicilioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where domicilio not equals to DEFAULT_DOMICILIO
        defaultDomicilioUsuarioShouldNotBeFound("domicilio.notEquals=" + DEFAULT_DOMICILIO);

        // Get all the domicilioUsuarioList where domicilio not equals to UPDATED_DOMICILIO
        defaultDomicilioUsuarioShouldBeFound("domicilio.notEquals=" + UPDATED_DOMICILIO);
    }

    @Test
    @Transactional
    public void getAllDomicilioUsuariosByDomicilioIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where domicilio in DEFAULT_DOMICILIO or UPDATED_DOMICILIO
        defaultDomicilioUsuarioShouldBeFound("domicilio.in=" + DEFAULT_DOMICILIO + "," + UPDATED_DOMICILIO);

        // Get all the domicilioUsuarioList where domicilio equals to UPDATED_DOMICILIO
        defaultDomicilioUsuarioShouldNotBeFound("domicilio.in=" + UPDATED_DOMICILIO);
    }

    @Test
    @Transactional
    public void getAllDomicilioUsuariosByDomicilioIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where domicilio is not null
        defaultDomicilioUsuarioShouldBeFound("domicilio.specified=true");

        // Get all the domicilioUsuarioList where domicilio is null
        defaultDomicilioUsuarioShouldNotBeFound("domicilio.specified=false");
    }
                @Test
    @Transactional
    public void getAllDomicilioUsuariosByDomicilioContainsSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where domicilio contains DEFAULT_DOMICILIO
        defaultDomicilioUsuarioShouldBeFound("domicilio.contains=" + DEFAULT_DOMICILIO);

        // Get all the domicilioUsuarioList where domicilio contains UPDATED_DOMICILIO
        defaultDomicilioUsuarioShouldNotBeFound("domicilio.contains=" + UPDATED_DOMICILIO);
    }

    @Test
    @Transactional
    public void getAllDomicilioUsuariosByDomicilioNotContainsSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where domicilio does not contain DEFAULT_DOMICILIO
        defaultDomicilioUsuarioShouldNotBeFound("domicilio.doesNotContain=" + DEFAULT_DOMICILIO);

        // Get all the domicilioUsuarioList where domicilio does not contain UPDATED_DOMICILIO
        defaultDomicilioUsuarioShouldBeFound("domicilio.doesNotContain=" + UPDATED_DOMICILIO);
    }


    @Test
    @Transactional
    public void getAllDomicilioUsuariosByFechaActIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where fechaAct equals to DEFAULT_FECHA_ACT
        defaultDomicilioUsuarioShouldBeFound("fechaAct.equals=" + DEFAULT_FECHA_ACT);

        // Get all the domicilioUsuarioList where fechaAct equals to UPDATED_FECHA_ACT
        defaultDomicilioUsuarioShouldNotBeFound("fechaAct.equals=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllDomicilioUsuariosByFechaActIsNotEqualToSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where fechaAct not equals to DEFAULT_FECHA_ACT
        defaultDomicilioUsuarioShouldNotBeFound("fechaAct.notEquals=" + DEFAULT_FECHA_ACT);

        // Get all the domicilioUsuarioList where fechaAct not equals to UPDATED_FECHA_ACT
        defaultDomicilioUsuarioShouldBeFound("fechaAct.notEquals=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllDomicilioUsuariosByFechaActIsInShouldWork() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where fechaAct in DEFAULT_FECHA_ACT or UPDATED_FECHA_ACT
        defaultDomicilioUsuarioShouldBeFound("fechaAct.in=" + DEFAULT_FECHA_ACT + "," + UPDATED_FECHA_ACT);

        // Get all the domicilioUsuarioList where fechaAct equals to UPDATED_FECHA_ACT
        defaultDomicilioUsuarioShouldNotBeFound("fechaAct.in=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllDomicilioUsuariosByFechaActIsNullOrNotNull() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where fechaAct is not null
        defaultDomicilioUsuarioShouldBeFound("fechaAct.specified=true");

        // Get all the domicilioUsuarioList where fechaAct is null
        defaultDomicilioUsuarioShouldNotBeFound("fechaAct.specified=false");
    }

    @Test
    @Transactional
    public void getAllDomicilioUsuariosByFechaActIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where fechaAct is greater than or equal to DEFAULT_FECHA_ACT
        defaultDomicilioUsuarioShouldBeFound("fechaAct.greaterThanOrEqual=" + DEFAULT_FECHA_ACT);

        // Get all the domicilioUsuarioList where fechaAct is greater than or equal to UPDATED_FECHA_ACT
        defaultDomicilioUsuarioShouldNotBeFound("fechaAct.greaterThanOrEqual=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllDomicilioUsuariosByFechaActIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where fechaAct is less than or equal to DEFAULT_FECHA_ACT
        defaultDomicilioUsuarioShouldBeFound("fechaAct.lessThanOrEqual=" + DEFAULT_FECHA_ACT);

        // Get all the domicilioUsuarioList where fechaAct is less than or equal to SMALLER_FECHA_ACT
        defaultDomicilioUsuarioShouldNotBeFound("fechaAct.lessThanOrEqual=" + SMALLER_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllDomicilioUsuariosByFechaActIsLessThanSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where fechaAct is less than DEFAULT_FECHA_ACT
        defaultDomicilioUsuarioShouldNotBeFound("fechaAct.lessThan=" + DEFAULT_FECHA_ACT);

        // Get all the domicilioUsuarioList where fechaAct is less than UPDATED_FECHA_ACT
        defaultDomicilioUsuarioShouldBeFound("fechaAct.lessThan=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllDomicilioUsuariosByFechaActIsGreaterThanSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);

        // Get all the domicilioUsuarioList where fechaAct is greater than DEFAULT_FECHA_ACT
        defaultDomicilioUsuarioShouldNotBeFound("fechaAct.greaterThan=" + DEFAULT_FECHA_ACT);

        // Get all the domicilioUsuarioList where fechaAct is greater than SMALLER_FECHA_ACT
        defaultDomicilioUsuarioShouldBeFound("fechaAct.greaterThan=" + SMALLER_FECHA_ACT);
    }


    @Test
    @Transactional
    public void getAllDomicilioUsuariosByNacionalidadIsEqualToSomething() throws Exception {
        // Get already existing entity
        CatNacionalidad nacionalidad = domicilioUsuario.getNacionalidad();
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);
        Long nacionalidadId = nacionalidad.getId();

        // Get all the domicilioUsuarioList where nacionalidad equals to nacionalidadId
        defaultDomicilioUsuarioShouldBeFound("nacionalidadId.equals=" + nacionalidadId);

        // Get all the domicilioUsuarioList where nacionalidad equals to nacionalidadId + 1
        defaultDomicilioUsuarioShouldNotBeFound("nacionalidadId.equals=" + (nacionalidadId + 1));
    }


    @Test
    @Transactional
    public void getAllDomicilioUsuariosByPaisOrigenIsEqualToSomething() throws Exception {
        // Get already existing entity
        CatPais paisOrigen = domicilioUsuario.getPaisOrigen();
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);
        String paisOrigenId = paisOrigen.getId();

        // Get all the domicilioUsuarioList where paisOrigen equals to paisOrigenId
        defaultDomicilioUsuarioShouldBeFound("paisOrigenId.equals=" + paisOrigenId);

        // Get all the domicilioUsuarioList where paisOrigen equals to paisOrigenId + 1
        defaultDomicilioUsuarioShouldNotBeFound("paisOrigenId.equals=" + (paisOrigenId + 1));
    }


    @Test
    @Transactional
    public void getAllDomicilioUsuariosByPaisIsEqualToSomething() throws Exception {
        // Get already existing entity
        CatPais pais = domicilioUsuario.getPais();
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);
        String paisId = pais.getId();

        // Get all the domicilioUsuarioList where pais equals to paisId
        defaultDomicilioUsuarioShouldBeFound("paisId.equals=" + paisId);

        // Get all the domicilioUsuarioList where pais equals to paisId + 1
        defaultDomicilioUsuarioShouldNotBeFound("paisId.equals=" + (paisId + 1));
    }


    @Test
    @Transactional
    public void getAllDomicilioUsuariosByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);
        CatEstado estado = CatEstadoResourceIT.createEntity(em);
        em.persist(estado);
        em.flush();
        domicilioUsuario.setEstado(estado);
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);
        String estadoId = estado.getId();

        // Get all the domicilioUsuarioList where estado equals to estadoId
        defaultDomicilioUsuarioShouldBeFound("estadoId.equals=" + estadoId);

        // Get all the domicilioUsuarioList where estado equals to estadoId + 1
        defaultDomicilioUsuarioShouldNotBeFound("estadoId.equals=" + (estadoId + 1));
    }


    @Test
    @Transactional
    public void getAllDomicilioUsuariosByMunicipioIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);
        CatMunicipio municipio = CatMunicipioResourceIT.createEntity(em);
        em.persist(municipio);
        em.flush();
        domicilioUsuario.setMunicipio(municipio);
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);
        String municipioId = municipio.getId();

        // Get all the domicilioUsuarioList where municipio equals to municipioId
        defaultDomicilioUsuarioShouldBeFound("municipioId.equals=" + municipioId);

        // Get all the domicilioUsuarioList where municipio equals to municipioId + 1
        defaultDomicilioUsuarioShouldNotBeFound("municipioId.equals=" + (municipioId + 1));
    }


    @Test
    @Transactional
    public void getAllDomicilioUsuariosByLocalidadIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);
        CatLocalidad localidad = CatLocalidadResourceIT.createEntity(em);
        em.persist(localidad);
        em.flush();
        domicilioUsuario.setLocalidad(localidad);
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);
        String localidadId = localidad.getId();

        // Get all the domicilioUsuarioList where localidad equals to localidadId
        defaultDomicilioUsuarioShouldBeFound("localidadId.equals=" + localidadId);

        // Get all the domicilioUsuarioList where localidad equals to localidadId + 1
        defaultDomicilioUsuarioShouldNotBeFound("localidadId.equals=" + (localidadId + 1));
    }


    @Test
    @Transactional
    public void getAllDomicilioUsuariosByCpIsEqualToSomething() throws Exception {
        // Initialize the database
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);
        CatCP cp = CatCPResourceIT.createEntity(em);
        em.persist(cp);
        em.flush();
        domicilioUsuario.setCp(cp);
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);
        String cpId = cp.getId();

        // Get all the domicilioUsuarioList where cp equals to cpId
        defaultDomicilioUsuarioShouldBeFound("cpId.equals=" + cpId);

        // Get all the domicilioUsuarioList where cp equals to cpId + 1
        defaultDomicilioUsuarioShouldNotBeFound("cpId.equals=" + (cpId + 1));
    }


    @Test
    @Transactional
    public void getAllDomicilioUsuariosByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = domicilioUsuario.getUser();
        domicilioUsuarioRepository.saveAndFlush(domicilioUsuario);
        Long userId = user.getId();

        // Get all the domicilioUsuarioList where user equals to userId
        defaultDomicilioUsuarioShouldBeFound("userId.equals=" + userId);

        // Get all the domicilioUsuarioList where user equals to userId + 1
        defaultDomicilioUsuarioShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDomicilioUsuarioShouldBeFound(String filter) throws Exception {
        restDomicilioUsuarioMockMvc.perform(get("/api/domicilio-usuarios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(domicilioUsuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].colonia").value(hasItem(DEFAULT_COLONIA)))
            .andExpect(jsonPath("$.[*].calle").value(hasItem(DEFAULT_CALLE)))
            .andExpect(jsonPath("$.[*].noExt").value(hasItem(DEFAULT_NO_EXT)))
            .andExpect(jsonPath("$.[*].noInt").value(hasItem(DEFAULT_NO_INT)))
            .andExpect(jsonPath("$.[*].domicilio").value(hasItem(DEFAULT_DOMICILIO)))
            .andExpect(jsonPath("$.[*].fechaAct").value(hasItem(DEFAULT_FECHA_ACT.toString())));

        // Check, that the count call also returns 1
        restDomicilioUsuarioMockMvc.perform(get("/api/domicilio-usuarios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDomicilioUsuarioShouldNotBeFound(String filter) throws Exception {
        restDomicilioUsuarioMockMvc.perform(get("/api/domicilio-usuarios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDomicilioUsuarioMockMvc.perform(get("/api/domicilio-usuarios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDomicilioUsuario() throws Exception {
        // Get the domicilioUsuario
        restDomicilioUsuarioMockMvc.perform(get("/api/domicilio-usuarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDomicilioUsuario() throws Exception {
        // Initialize the database
        domicilioUsuarioService.save(domicilioUsuario);

        int databaseSizeBeforeUpdate = domicilioUsuarioRepository.findAll().size();

        // Update the domicilioUsuario
        DomicilioUsuario updatedDomicilioUsuario = domicilioUsuarioRepository.findById(domicilioUsuario.getId()).get();
        // Disconnect from session so that the updates on updatedDomicilioUsuario are not directly saved in db
        em.detach(updatedDomicilioUsuario);
        updatedDomicilioUsuario
            .colonia(UPDATED_COLONIA)
            .calle(UPDATED_CALLE)
            .noExt(UPDATED_NO_EXT)
            .noInt(UPDATED_NO_INT)
            .domicilio(UPDATED_DOMICILIO)
            .fechaAct(UPDATED_FECHA_ACT);

        restDomicilioUsuarioMockMvc.perform(put("/api/domicilio-usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDomicilioUsuario)))
            .andExpect(status().isOk());

        // Validate the DomicilioUsuario in the database
        List<DomicilioUsuario> domicilioUsuarioList = domicilioUsuarioRepository.findAll();
        assertThat(domicilioUsuarioList).hasSize(databaseSizeBeforeUpdate);
        DomicilioUsuario testDomicilioUsuario = domicilioUsuarioList.get(domicilioUsuarioList.size() - 1);
        assertThat(testDomicilioUsuario.getColonia()).isEqualTo(UPDATED_COLONIA);
        assertThat(testDomicilioUsuario.getCalle()).isEqualTo(UPDATED_CALLE);
        assertThat(testDomicilioUsuario.getNoExt()).isEqualTo(UPDATED_NO_EXT);
        assertThat(testDomicilioUsuario.getNoInt()).isEqualTo(UPDATED_NO_INT);
        assertThat(testDomicilioUsuario.getDomicilio()).isEqualTo(UPDATED_DOMICILIO);
        assertThat(testDomicilioUsuario.getFechaAct()).isEqualTo(UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void updateNonExistingDomicilioUsuario() throws Exception {
        int databaseSizeBeforeUpdate = domicilioUsuarioRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDomicilioUsuarioMockMvc.perform(put("/api/domicilio-usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(domicilioUsuario)))
            .andExpect(status().isBadRequest());

        // Validate the DomicilioUsuario in the database
        List<DomicilioUsuario> domicilioUsuarioList = domicilioUsuarioRepository.findAll();
        assertThat(domicilioUsuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDomicilioUsuario() throws Exception {
        // Initialize the database
        domicilioUsuarioService.save(domicilioUsuario);

        int databaseSizeBeforeDelete = domicilioUsuarioRepository.findAll().size();

        // Delete the domicilioUsuario
        restDomicilioUsuarioMockMvc.perform(delete("/api/domicilio-usuarios/{id}", domicilioUsuario.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DomicilioUsuario> domicilioUsuarioList = domicilioUsuarioRepository.findAll();
        assertThat(domicilioUsuarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
