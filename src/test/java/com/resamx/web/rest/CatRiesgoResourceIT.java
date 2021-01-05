package com.resamx.web.rest;

import com.resamx.ResaMxWebApp;
import com.resamx.domain.CatRiesgo;
import com.resamx.domain.User;
import com.resamx.repository.CatRiesgoRepository;
import com.resamx.service.CatRiesgoService;
import com.resamx.service.dto.CatRiesgoCriteria;
import com.resamx.service.CatRiesgoQueryService;

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
 * Integration tests for the {@link CatRiesgoResource} REST controller.
 */
@SpringBootTest(classes = ResaMxWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CatRiesgoResourceIT {

    private static final String DEFAULT_RIESGO = "AAAAAAAAAA";
    private static final String UPDATED_RIESGO = "BBBBBBBBBB";

    @Autowired
    private CatRiesgoRepository catRiesgoRepository;

    @Autowired
    private CatRiesgoService catRiesgoService;

    @Autowired
    private CatRiesgoQueryService catRiesgoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCatRiesgoMockMvc;

    private CatRiesgo catRiesgo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatRiesgo createEntity(EntityManager em) {
        CatRiesgo catRiesgo = new CatRiesgo()
            .riesgo(DEFAULT_RIESGO);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catRiesgo.setUsuario(user);
        return catRiesgo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatRiesgo createUpdatedEntity(EntityManager em) {
        CatRiesgo catRiesgo = new CatRiesgo()
            .riesgo(UPDATED_RIESGO);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catRiesgo.setUsuario(user);
        return catRiesgo;
    }

    @BeforeEach
    public void initTest() {
        catRiesgo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCatRiesgo() throws Exception {
        int databaseSizeBeforeCreate = catRiesgoRepository.findAll().size();
        // Create the CatRiesgo
        restCatRiesgoMockMvc.perform(post("/api/cat-riesgos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catRiesgo)))
            .andExpect(status().isCreated());

        // Validate the CatRiesgo in the database
        List<CatRiesgo> catRiesgoList = catRiesgoRepository.findAll();
        assertThat(catRiesgoList).hasSize(databaseSizeBeforeCreate + 1);
        CatRiesgo testCatRiesgo = catRiesgoList.get(catRiesgoList.size() - 1);
        assertThat(testCatRiesgo.getRiesgo()).isEqualTo(DEFAULT_RIESGO);
    }

    @Test
    @Transactional
    public void createCatRiesgoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = catRiesgoRepository.findAll().size();

        // Create the CatRiesgo with an existing ID
        catRiesgo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatRiesgoMockMvc.perform(post("/api/cat-riesgos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catRiesgo)))
            .andExpect(status().isBadRequest());

        // Validate the CatRiesgo in the database
        List<CatRiesgo> catRiesgoList = catRiesgoRepository.findAll();
        assertThat(catRiesgoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRiesgoIsRequired() throws Exception {
        int databaseSizeBeforeTest = catRiesgoRepository.findAll().size();
        // set the field null
        catRiesgo.setRiesgo(null);

        // Create the CatRiesgo, which fails.


        restCatRiesgoMockMvc.perform(post("/api/cat-riesgos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catRiesgo)))
            .andExpect(status().isBadRequest());

        List<CatRiesgo> catRiesgoList = catRiesgoRepository.findAll();
        assertThat(catRiesgoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCatRiesgos() throws Exception {
        // Initialize the database
        catRiesgoRepository.saveAndFlush(catRiesgo);

        // Get all the catRiesgoList
        restCatRiesgoMockMvc.perform(get("/api/cat-riesgos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catRiesgo.getId().intValue())))
            .andExpect(jsonPath("$.[*].riesgo").value(hasItem(DEFAULT_RIESGO)));
    }
    
    @Test
    @Transactional
    public void getCatRiesgo() throws Exception {
        // Initialize the database
        catRiesgoRepository.saveAndFlush(catRiesgo);

        // Get the catRiesgo
        restCatRiesgoMockMvc.perform(get("/api/cat-riesgos/{id}", catRiesgo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(catRiesgo.getId().intValue()))
            .andExpect(jsonPath("$.riesgo").value(DEFAULT_RIESGO));
    }


    @Test
    @Transactional
    public void getCatRiesgosByIdFiltering() throws Exception {
        // Initialize the database
        catRiesgoRepository.saveAndFlush(catRiesgo);

        Long id = catRiesgo.getId();

        defaultCatRiesgoShouldBeFound("id.equals=" + id);
        defaultCatRiesgoShouldNotBeFound("id.notEquals=" + id);

        defaultCatRiesgoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCatRiesgoShouldNotBeFound("id.greaterThan=" + id);

        defaultCatRiesgoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCatRiesgoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCatRiesgosByRiesgoIsEqualToSomething() throws Exception {
        // Initialize the database
        catRiesgoRepository.saveAndFlush(catRiesgo);

        // Get all the catRiesgoList where riesgo equals to DEFAULT_RIESGO
        defaultCatRiesgoShouldBeFound("riesgo.equals=" + DEFAULT_RIESGO);

        // Get all the catRiesgoList where riesgo equals to UPDATED_RIESGO
        defaultCatRiesgoShouldNotBeFound("riesgo.equals=" + UPDATED_RIESGO);
    }

    @Test
    @Transactional
    public void getAllCatRiesgosByRiesgoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catRiesgoRepository.saveAndFlush(catRiesgo);

        // Get all the catRiesgoList where riesgo not equals to DEFAULT_RIESGO
        defaultCatRiesgoShouldNotBeFound("riesgo.notEquals=" + DEFAULT_RIESGO);

        // Get all the catRiesgoList where riesgo not equals to UPDATED_RIESGO
        defaultCatRiesgoShouldBeFound("riesgo.notEquals=" + UPDATED_RIESGO);
    }

    @Test
    @Transactional
    public void getAllCatRiesgosByRiesgoIsInShouldWork() throws Exception {
        // Initialize the database
        catRiesgoRepository.saveAndFlush(catRiesgo);

        // Get all the catRiesgoList where riesgo in DEFAULT_RIESGO or UPDATED_RIESGO
        defaultCatRiesgoShouldBeFound("riesgo.in=" + DEFAULT_RIESGO + "," + UPDATED_RIESGO);

        // Get all the catRiesgoList where riesgo equals to UPDATED_RIESGO
        defaultCatRiesgoShouldNotBeFound("riesgo.in=" + UPDATED_RIESGO);
    }

    @Test
    @Transactional
    public void getAllCatRiesgosByRiesgoIsNullOrNotNull() throws Exception {
        // Initialize the database
        catRiesgoRepository.saveAndFlush(catRiesgo);

        // Get all the catRiesgoList where riesgo is not null
        defaultCatRiesgoShouldBeFound("riesgo.specified=true");

        // Get all the catRiesgoList where riesgo is null
        defaultCatRiesgoShouldNotBeFound("riesgo.specified=false");
    }
                @Test
    @Transactional
    public void getAllCatRiesgosByRiesgoContainsSomething() throws Exception {
        // Initialize the database
        catRiesgoRepository.saveAndFlush(catRiesgo);

        // Get all the catRiesgoList where riesgo contains DEFAULT_RIESGO
        defaultCatRiesgoShouldBeFound("riesgo.contains=" + DEFAULT_RIESGO);

        // Get all the catRiesgoList where riesgo contains UPDATED_RIESGO
        defaultCatRiesgoShouldNotBeFound("riesgo.contains=" + UPDATED_RIESGO);
    }

    @Test
    @Transactional
    public void getAllCatRiesgosByRiesgoNotContainsSomething() throws Exception {
        // Initialize the database
        catRiesgoRepository.saveAndFlush(catRiesgo);

        // Get all the catRiesgoList where riesgo does not contain DEFAULT_RIESGO
        defaultCatRiesgoShouldNotBeFound("riesgo.doesNotContain=" + DEFAULT_RIESGO);

        // Get all the catRiesgoList where riesgo does not contain UPDATED_RIESGO
        defaultCatRiesgoShouldBeFound("riesgo.doesNotContain=" + UPDATED_RIESGO);
    }


    @Test
    @Transactional
    public void getAllCatRiesgosByUsuarioIsEqualToSomething() throws Exception {
        // Get already existing entity
        User usuario = catRiesgo.getUsuario();
        catRiesgoRepository.saveAndFlush(catRiesgo);
        Long usuarioId = usuario.getId();

        // Get all the catRiesgoList where usuario equals to usuarioId
        defaultCatRiesgoShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the catRiesgoList where usuario equals to usuarioId + 1
        defaultCatRiesgoShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCatRiesgoShouldBeFound(String filter) throws Exception {
        restCatRiesgoMockMvc.perform(get("/api/cat-riesgos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catRiesgo.getId().intValue())))
            .andExpect(jsonPath("$.[*].riesgo").value(hasItem(DEFAULT_RIESGO)));

        // Check, that the count call also returns 1
        restCatRiesgoMockMvc.perform(get("/api/cat-riesgos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCatRiesgoShouldNotBeFound(String filter) throws Exception {
        restCatRiesgoMockMvc.perform(get("/api/cat-riesgos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCatRiesgoMockMvc.perform(get("/api/cat-riesgos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCatRiesgo() throws Exception {
        // Get the catRiesgo
        restCatRiesgoMockMvc.perform(get("/api/cat-riesgos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCatRiesgo() throws Exception {
        // Initialize the database
        catRiesgoService.save(catRiesgo);

        int databaseSizeBeforeUpdate = catRiesgoRepository.findAll().size();

        // Update the catRiesgo
        CatRiesgo updatedCatRiesgo = catRiesgoRepository.findById(catRiesgo.getId()).get();
        // Disconnect from session so that the updates on updatedCatRiesgo are not directly saved in db
        em.detach(updatedCatRiesgo);
        updatedCatRiesgo
            .riesgo(UPDATED_RIESGO);

        restCatRiesgoMockMvc.perform(put("/api/cat-riesgos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCatRiesgo)))
            .andExpect(status().isOk());

        // Validate the CatRiesgo in the database
        List<CatRiesgo> catRiesgoList = catRiesgoRepository.findAll();
        assertThat(catRiesgoList).hasSize(databaseSizeBeforeUpdate);
        CatRiesgo testCatRiesgo = catRiesgoList.get(catRiesgoList.size() - 1);
        assertThat(testCatRiesgo.getRiesgo()).isEqualTo(UPDATED_RIESGO);
    }

    @Test
    @Transactional
    public void updateNonExistingCatRiesgo() throws Exception {
        int databaseSizeBeforeUpdate = catRiesgoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatRiesgoMockMvc.perform(put("/api/cat-riesgos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catRiesgo)))
            .andExpect(status().isBadRequest());

        // Validate the CatRiesgo in the database
        List<CatRiesgo> catRiesgoList = catRiesgoRepository.findAll();
        assertThat(catRiesgoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCatRiesgo() throws Exception {
        // Initialize the database
        catRiesgoService.save(catRiesgo);

        int databaseSizeBeforeDelete = catRiesgoRepository.findAll().size();

        // Delete the catRiesgo
        restCatRiesgoMockMvc.perform(delete("/api/cat-riesgos/{id}", catRiesgo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CatRiesgo> catRiesgoList = catRiesgoRepository.findAll();
        assertThat(catRiesgoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
