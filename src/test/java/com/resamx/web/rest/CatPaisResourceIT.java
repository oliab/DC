package com.resamx.web.rest;

import com.resamx.ResaMxWebApp;
import com.resamx.domain.CatPais;
import com.resamx.domain.User;
import com.resamx.repository.CatPaisRepository;
import com.resamx.service.CatPaisService;
import com.resamx.service.dto.CatPaisCriteria;
import com.resamx.service.CatPaisQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CatPaisResource} REST controller.
 */
@SpringBootTest(classes = ResaMxWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CatPaisResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_A_2 = "AA";
    private static final String UPDATED_CODIGO_A_2 = "BB";

    private static final String DEFAULT_CODIGO_A_3 = "AAA";
    private static final String UPDATED_CODIGO_A_3 = "BBB";

    @Autowired
    private CatPaisRepository catPaisRepository;

    @Autowired
    private CatPaisService catPaisService;

    @Autowired
    private CatPaisQueryService catPaisQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCatPaisMockMvc;

    private CatPais catPais;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatPais createEntity(EntityManager em) {
        CatPais catPais = new CatPais()
            .nombre(DEFAULT_NOMBRE)
            .codigoA2(DEFAULT_CODIGO_A_2)
            .codigoA3(DEFAULT_CODIGO_A_3);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catPais.setUsuario(user);
        return catPais;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatPais createUpdatedEntity(EntityManager em) {
        CatPais catPais = new CatPais()
            .nombre(UPDATED_NOMBRE)
            .codigoA2(UPDATED_CODIGO_A_2)
            .codigoA3(UPDATED_CODIGO_A_3);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catPais.setUsuario(user);
        return catPais;
    }

    @BeforeEach
    public void initTest() {
        catPais = createEntity(em);
    }

    @Test
    @Transactional
    public void createCatPais() throws Exception {
        int databaseSizeBeforeCreate = catPaisRepository.findAll().size();
        // Create the CatPais
        restCatPaisMockMvc.perform(post("/api/cat-pais")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catPais)))
            .andExpect(status().isCreated());

        // Validate the CatPais in the database
        List<CatPais> catPaisList = catPaisRepository.findAll();
        assertThat(catPaisList).hasSize(databaseSizeBeforeCreate + 1);
        CatPais testCatPais = catPaisList.get(catPaisList.size() - 1);
        assertThat(testCatPais.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCatPais.getCodigoA2()).isEqualTo(DEFAULT_CODIGO_A_2);
        assertThat(testCatPais.getCodigoA3()).isEqualTo(DEFAULT_CODIGO_A_3);
    }

    @Test
    @Transactional
    public void createCatPaisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = catPaisRepository.findAll().size();

        // Create the CatPais with an existing ID
        catPais.setId("1");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatPaisMockMvc.perform(post("/api/cat-pais")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catPais)))
            .andExpect(status().isBadRequest());

        // Validate the CatPais in the database
        List<CatPais> catPaisList = catPaisRepository.findAll();
        assertThat(catPaisList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = catPaisRepository.findAll().size();
        // set the field null
        catPais.setNombre(null);

        // Create the CatPais, which fails.


        restCatPaisMockMvc.perform(post("/api/cat-pais")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catPais)))
            .andExpect(status().isBadRequest());

        List<CatPais> catPaisList = catPaisRepository.findAll();
        assertThat(catPaisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodigoA2IsRequired() throws Exception {
        int databaseSizeBeforeTest = catPaisRepository.findAll().size();
        // set the field null
        catPais.setCodigoA2(null);

        // Create the CatPais, which fails.


        restCatPaisMockMvc.perform(post("/api/cat-pais")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catPais)))
            .andExpect(status().isBadRequest());

        List<CatPais> catPaisList = catPaisRepository.findAll();
        assertThat(catPaisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodigoA3IsRequired() throws Exception {
        int databaseSizeBeforeTest = catPaisRepository.findAll().size();
        // set the field null
        catPais.setCodigoA3(null);

        // Create the CatPais, which fails.


        restCatPaisMockMvc.perform(post("/api/cat-pais")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catPais)))
            .andExpect(status().isBadRequest());

        List<CatPais> catPaisList = catPaisRepository.findAll();
        assertThat(catPaisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCatPais() throws Exception {
        // Initialize the database
        catPaisRepository.saveAndFlush(catPais);

        // Get all the catPaisList
        restCatPaisMockMvc.perform(get("/api/cat-pais?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catPais.getId())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].codigoA2").value(hasItem(DEFAULT_CODIGO_A_2)))
            .andExpect(jsonPath("$.[*].codigoA3").value(hasItem(DEFAULT_CODIGO_A_3)));
    }
    
    @Test
    @Transactional
    public void getCatPais() throws Exception {
        // Initialize the database
        catPaisRepository.saveAndFlush(catPais);

        // Get the catPais
        restCatPaisMockMvc.perform(get("/api/cat-pais/{id}", catPais.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(catPais.getId()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.codigoA2").value(DEFAULT_CODIGO_A_2))
            .andExpect(jsonPath("$.codigoA3").value(DEFAULT_CODIGO_A_3));
    }


    @Test
    @Transactional
    public void getCatPaisByIdFiltering() throws Exception {
        // Initialize the database
        catPaisRepository.saveAndFlush(catPais);

        String id = catPais.getId();

        defaultCatPaisShouldBeFound("id.equals=" + id);
        defaultCatPaisShouldNotBeFound("id.notEquals=" + id);

        defaultCatPaisShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCatPaisShouldNotBeFound("id.greaterThan=" + id);

        defaultCatPaisShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCatPaisShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCatPaisByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        catPaisRepository.saveAndFlush(catPais);

        // Get all the catPaisList where nombre equals to DEFAULT_NOMBRE
        defaultCatPaisShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the catPaisList where nombre equals to UPDATED_NOMBRE
        defaultCatPaisShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllCatPaisByNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catPaisRepository.saveAndFlush(catPais);

        // Get all the catPaisList where nombre not equals to DEFAULT_NOMBRE
        defaultCatPaisShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

        // Get all the catPaisList where nombre not equals to UPDATED_NOMBRE
        defaultCatPaisShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllCatPaisByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        catPaisRepository.saveAndFlush(catPais);

        // Get all the catPaisList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultCatPaisShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the catPaisList where nombre equals to UPDATED_NOMBRE
        defaultCatPaisShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllCatPaisByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        catPaisRepository.saveAndFlush(catPais);

        // Get all the catPaisList where nombre is not null
        defaultCatPaisShouldBeFound("nombre.specified=true");

        // Get all the catPaisList where nombre is null
        defaultCatPaisShouldNotBeFound("nombre.specified=false");
    }
                @Test
    @Transactional
    public void getAllCatPaisByNombreContainsSomething() throws Exception {
        // Initialize the database
        catPaisRepository.saveAndFlush(catPais);

        // Get all the catPaisList where nombre contains DEFAULT_NOMBRE
        defaultCatPaisShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

        // Get all the catPaisList where nombre contains UPDATED_NOMBRE
        defaultCatPaisShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllCatPaisByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        catPaisRepository.saveAndFlush(catPais);

        // Get all the catPaisList where nombre does not contain DEFAULT_NOMBRE
        defaultCatPaisShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

        // Get all the catPaisList where nombre does not contain UPDATED_NOMBRE
        defaultCatPaisShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
    }


    @Test
    @Transactional
    public void getAllCatPaisByCodigoA2IsEqualToSomething() throws Exception {
        // Initialize the database
        catPaisRepository.saveAndFlush(catPais);

        // Get all the catPaisList where codigoA2 equals to DEFAULT_CODIGO_A_2
        defaultCatPaisShouldBeFound("codigoA2.equals=" + DEFAULT_CODIGO_A_2);

        // Get all the catPaisList where codigoA2 equals to UPDATED_CODIGO_A_2
        defaultCatPaisShouldNotBeFound("codigoA2.equals=" + UPDATED_CODIGO_A_2);
    }

    @Test
    @Transactional
    public void getAllCatPaisByCodigoA2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        catPaisRepository.saveAndFlush(catPais);

        // Get all the catPaisList where codigoA2 not equals to DEFAULT_CODIGO_A_2
        defaultCatPaisShouldNotBeFound("codigoA2.notEquals=" + DEFAULT_CODIGO_A_2);

        // Get all the catPaisList where codigoA2 not equals to UPDATED_CODIGO_A_2
        defaultCatPaisShouldBeFound("codigoA2.notEquals=" + UPDATED_CODIGO_A_2);
    }

    @Test
    @Transactional
    public void getAllCatPaisByCodigoA2IsInShouldWork() throws Exception {
        // Initialize the database
        catPaisRepository.saveAndFlush(catPais);

        // Get all the catPaisList where codigoA2 in DEFAULT_CODIGO_A_2 or UPDATED_CODIGO_A_2
        defaultCatPaisShouldBeFound("codigoA2.in=" + DEFAULT_CODIGO_A_2 + "," + UPDATED_CODIGO_A_2);

        // Get all the catPaisList where codigoA2 equals to UPDATED_CODIGO_A_2
        defaultCatPaisShouldNotBeFound("codigoA2.in=" + UPDATED_CODIGO_A_2);
    }

    @Test
    @Transactional
    public void getAllCatPaisByCodigoA2IsNullOrNotNull() throws Exception {
        // Initialize the database
        catPaisRepository.saveAndFlush(catPais);

        // Get all the catPaisList where codigoA2 is not null
        defaultCatPaisShouldBeFound("codigoA2.specified=true");

        // Get all the catPaisList where codigoA2 is null
        defaultCatPaisShouldNotBeFound("codigoA2.specified=false");
    }
                @Test
    @Transactional
    public void getAllCatPaisByCodigoA2ContainsSomething() throws Exception {
        // Initialize the database
        catPaisRepository.saveAndFlush(catPais);

        // Get all the catPaisList where codigoA2 contains DEFAULT_CODIGO_A_2
        defaultCatPaisShouldBeFound("codigoA2.contains=" + DEFAULT_CODIGO_A_2);

        // Get all the catPaisList where codigoA2 contains UPDATED_CODIGO_A_2
        defaultCatPaisShouldNotBeFound("codigoA2.contains=" + UPDATED_CODIGO_A_2);
    }

    @Test
    @Transactional
    public void getAllCatPaisByCodigoA2NotContainsSomething() throws Exception {
        // Initialize the database
        catPaisRepository.saveAndFlush(catPais);

        // Get all the catPaisList where codigoA2 does not contain DEFAULT_CODIGO_A_2
        defaultCatPaisShouldNotBeFound("codigoA2.doesNotContain=" + DEFAULT_CODIGO_A_2);

        // Get all the catPaisList where codigoA2 does not contain UPDATED_CODIGO_A_2
        defaultCatPaisShouldBeFound("codigoA2.doesNotContain=" + UPDATED_CODIGO_A_2);
    }


    @Test
    @Transactional
    public void getAllCatPaisByCodigoA3IsEqualToSomething() throws Exception {
        // Initialize the database
        catPaisRepository.saveAndFlush(catPais);

        // Get all the catPaisList where codigoA3 equals to DEFAULT_CODIGO_A_3
        defaultCatPaisShouldBeFound("codigoA3.equals=" + DEFAULT_CODIGO_A_3);

        // Get all the catPaisList where codigoA3 equals to UPDATED_CODIGO_A_3
        defaultCatPaisShouldNotBeFound("codigoA3.equals=" + UPDATED_CODIGO_A_3);
    }

    @Test
    @Transactional
    public void getAllCatPaisByCodigoA3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        catPaisRepository.saveAndFlush(catPais);

        // Get all the catPaisList where codigoA3 not equals to DEFAULT_CODIGO_A_3
        defaultCatPaisShouldNotBeFound("codigoA3.notEquals=" + DEFAULT_CODIGO_A_3);

        // Get all the catPaisList where codigoA3 not equals to UPDATED_CODIGO_A_3
        defaultCatPaisShouldBeFound("codigoA3.notEquals=" + UPDATED_CODIGO_A_3);
    }

    @Test
    @Transactional
    public void getAllCatPaisByCodigoA3IsInShouldWork() throws Exception {
        // Initialize the database
        catPaisRepository.saveAndFlush(catPais);

        // Get all the catPaisList where codigoA3 in DEFAULT_CODIGO_A_3 or UPDATED_CODIGO_A_3
        defaultCatPaisShouldBeFound("codigoA3.in=" + DEFAULT_CODIGO_A_3 + "," + UPDATED_CODIGO_A_3);

        // Get all the catPaisList where codigoA3 equals to UPDATED_CODIGO_A_3
        defaultCatPaisShouldNotBeFound("codigoA3.in=" + UPDATED_CODIGO_A_3);
    }

    @Test
    @Transactional
    public void getAllCatPaisByCodigoA3IsNullOrNotNull() throws Exception {
        // Initialize the database
        catPaisRepository.saveAndFlush(catPais);

        // Get all the catPaisList where codigoA3 is not null
        defaultCatPaisShouldBeFound("codigoA3.specified=true");

        // Get all the catPaisList where codigoA3 is null
        defaultCatPaisShouldNotBeFound("codigoA3.specified=false");
    }
                @Test
    @Transactional
    public void getAllCatPaisByCodigoA3ContainsSomething() throws Exception {
        // Initialize the database
        catPaisRepository.saveAndFlush(catPais);

        // Get all the catPaisList where codigoA3 contains DEFAULT_CODIGO_A_3
        defaultCatPaisShouldBeFound("codigoA3.contains=" + DEFAULT_CODIGO_A_3);

        // Get all the catPaisList where codigoA3 contains UPDATED_CODIGO_A_3
        defaultCatPaisShouldNotBeFound("codigoA3.contains=" + UPDATED_CODIGO_A_3);
    }

    @Test
    @Transactional
    public void getAllCatPaisByCodigoA3NotContainsSomething() throws Exception {
        // Initialize the database
        catPaisRepository.saveAndFlush(catPais);

        // Get all the catPaisList where codigoA3 does not contain DEFAULT_CODIGO_A_3
        defaultCatPaisShouldNotBeFound("codigoA3.doesNotContain=" + DEFAULT_CODIGO_A_3);

        // Get all the catPaisList where codigoA3 does not contain UPDATED_CODIGO_A_3
        defaultCatPaisShouldBeFound("codigoA3.doesNotContain=" + UPDATED_CODIGO_A_3);
    }


    @Test
    @Transactional
    public void getAllCatPaisByUsuarioIsEqualToSomething() throws Exception {
        // Get already existing entity
        User usuario = catPais.getUsuario();
        catPaisRepository.saveAndFlush(catPais);
        Long usuarioId = usuario.getId();

        // Get all the catPaisList where usuario equals to usuarioId
        defaultCatPaisShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the catPaisList where usuario equals to usuarioId + 1
        defaultCatPaisShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCatPaisShouldBeFound(String filter) throws Exception {
        restCatPaisMockMvc.perform(get("/api/cat-pais?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catPais.getId())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].codigoA2").value(hasItem(DEFAULT_CODIGO_A_2)))
            .andExpect(jsonPath("$.[*].codigoA3").value(hasItem(DEFAULT_CODIGO_A_3)));

        // Check, that the count call also returns 1
        restCatPaisMockMvc.perform(get("/api/cat-pais/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCatPaisShouldNotBeFound(String filter) throws Exception {
        restCatPaisMockMvc.perform(get("/api/cat-pais?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCatPaisMockMvc.perform(get("/api/cat-pais/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCatPais() throws Exception {
        // Get the catPais
        restCatPaisMockMvc.perform(get("/api/cat-pais/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCatPais() throws Exception {
        // Initialize the database
        catPaisService.save(catPais);

        int databaseSizeBeforeUpdate = catPaisRepository.findAll().size();

        // Update the catPais
        CatPais updatedCatPais = catPaisRepository.findById(catPais.getId()).get();
        // Disconnect from session so that the updates on updatedCatPais are not directly saved in db
        em.detach(updatedCatPais);
        updatedCatPais
            .nombre(UPDATED_NOMBRE)
            .codigoA2(UPDATED_CODIGO_A_2)
            .codigoA3(UPDATED_CODIGO_A_3);

        restCatPaisMockMvc.perform(put("/api/cat-pais")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCatPais)))
            .andExpect(status().isOk());

        // Validate the CatPais in the database
        List<CatPais> catPaisList = catPaisRepository.findAll();
        assertThat(catPaisList).hasSize(databaseSizeBeforeUpdate);
        CatPais testCatPais = catPaisList.get(catPaisList.size() - 1);
        assertThat(testCatPais.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCatPais.getCodigoA2()).isEqualTo(UPDATED_CODIGO_A_2);
        assertThat(testCatPais.getCodigoA3()).isEqualTo(UPDATED_CODIGO_A_3);
    }

    @Test
    @Transactional
    public void updateNonExistingCatPais() throws Exception {
        int databaseSizeBeforeUpdate = catPaisRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatPaisMockMvc.perform(put("/api/cat-pais")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catPais)))
            .andExpect(status().isBadRequest());

        // Validate the CatPais in the database
        List<CatPais> catPaisList = catPaisRepository.findAll();
        assertThat(catPaisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCatPais() throws Exception {
        // Initialize the database
        catPaisService.save(catPais);

        int databaseSizeBeforeDelete = catPaisRepository.findAll().size();

        // Delete the catPais
        restCatPaisMockMvc.perform(delete("/api/cat-pais/{id}", catPais.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CatPais> catPaisList = catPaisRepository.findAll();
        assertThat(catPaisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
