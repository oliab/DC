package com.resamx.web.rest;

import com.resamx.ResaMxWebApp;
import com.resamx.domain.CatNacionalidad;
import com.resamx.domain.User;
import com.resamx.repository.CatNacionalidadRepository;
import com.resamx.service.CatNacionalidadService;
import com.resamx.service.dto.CatNacionalidadCriteria;
import com.resamx.service.CatNacionalidadQueryService;

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
 * Integration tests for the {@link CatNacionalidadResource} REST controller.
 */
@SpringBootTest(classes = ResaMxWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CatNacionalidadResourceIT {

    private static final String DEFAULT_NACIONALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_NACIONALIDAD = "BBBBBBBBBB";

    @Autowired
    private CatNacionalidadRepository catNacionalidadRepository;

    @Autowired
    private CatNacionalidadService catNacionalidadService;

    @Autowired
    private CatNacionalidadQueryService catNacionalidadQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCatNacionalidadMockMvc;

    private CatNacionalidad catNacionalidad;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatNacionalidad createEntity(EntityManager em) {
        CatNacionalidad catNacionalidad = new CatNacionalidad()
            .nacionalidad(DEFAULT_NACIONALIDAD);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catNacionalidad.setUsuario(user);
        return catNacionalidad;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatNacionalidad createUpdatedEntity(EntityManager em) {
        CatNacionalidad catNacionalidad = new CatNacionalidad()
            .nacionalidad(UPDATED_NACIONALIDAD);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catNacionalidad.setUsuario(user);
        return catNacionalidad;
    }

    @BeforeEach
    public void initTest() {
        catNacionalidad = createEntity(em);
    }

    @Test
    @Transactional
    public void createCatNacionalidad() throws Exception {
        int databaseSizeBeforeCreate = catNacionalidadRepository.findAll().size();
        // Create the CatNacionalidad
        restCatNacionalidadMockMvc.perform(post("/api/cat-nacionalidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catNacionalidad)))
            .andExpect(status().isCreated());

        // Validate the CatNacionalidad in the database
        List<CatNacionalidad> catNacionalidadList = catNacionalidadRepository.findAll();
        assertThat(catNacionalidadList).hasSize(databaseSizeBeforeCreate + 1);
        CatNacionalidad testCatNacionalidad = catNacionalidadList.get(catNacionalidadList.size() - 1);
        assertThat(testCatNacionalidad.getNacionalidad()).isEqualTo(DEFAULT_NACIONALIDAD);
    }

    @Test
    @Transactional
    public void createCatNacionalidadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = catNacionalidadRepository.findAll().size();

        // Create the CatNacionalidad with an existing ID
        catNacionalidad.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatNacionalidadMockMvc.perform(post("/api/cat-nacionalidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catNacionalidad)))
            .andExpect(status().isBadRequest());

        // Validate the CatNacionalidad in the database
        List<CatNacionalidad> catNacionalidadList = catNacionalidadRepository.findAll();
        assertThat(catNacionalidadList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNacionalidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = catNacionalidadRepository.findAll().size();
        // set the field null
        catNacionalidad.setNacionalidad(null);

        // Create the CatNacionalidad, which fails.


        restCatNacionalidadMockMvc.perform(post("/api/cat-nacionalidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catNacionalidad)))
            .andExpect(status().isBadRequest());

        List<CatNacionalidad> catNacionalidadList = catNacionalidadRepository.findAll();
        assertThat(catNacionalidadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCatNacionalidads() throws Exception {
        // Initialize the database
        catNacionalidadRepository.saveAndFlush(catNacionalidad);

        // Get all the catNacionalidadList
        restCatNacionalidadMockMvc.perform(get("/api/cat-nacionalidads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catNacionalidad.getId().intValue())))
            .andExpect(jsonPath("$.[*].nacionalidad").value(hasItem(DEFAULT_NACIONALIDAD)));
    }
    
    @Test
    @Transactional
    public void getCatNacionalidad() throws Exception {
        // Initialize the database
        catNacionalidadRepository.saveAndFlush(catNacionalidad);

        // Get the catNacionalidad
        restCatNacionalidadMockMvc.perform(get("/api/cat-nacionalidads/{id}", catNacionalidad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(catNacionalidad.getId().intValue()))
            .andExpect(jsonPath("$.nacionalidad").value(DEFAULT_NACIONALIDAD));
    }


    @Test
    @Transactional
    public void getCatNacionalidadsByIdFiltering() throws Exception {
        // Initialize the database
        catNacionalidadRepository.saveAndFlush(catNacionalidad);

        Long id = catNacionalidad.getId();

        defaultCatNacionalidadShouldBeFound("id.equals=" + id);
        defaultCatNacionalidadShouldNotBeFound("id.notEquals=" + id);

        defaultCatNacionalidadShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCatNacionalidadShouldNotBeFound("id.greaterThan=" + id);

        defaultCatNacionalidadShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCatNacionalidadShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCatNacionalidadsByNacionalidadIsEqualToSomething() throws Exception {
        // Initialize the database
        catNacionalidadRepository.saveAndFlush(catNacionalidad);

        // Get all the catNacionalidadList where nacionalidad equals to DEFAULT_NACIONALIDAD
        defaultCatNacionalidadShouldBeFound("nacionalidad.equals=" + DEFAULT_NACIONALIDAD);

        // Get all the catNacionalidadList where nacionalidad equals to UPDATED_NACIONALIDAD
        defaultCatNacionalidadShouldNotBeFound("nacionalidad.equals=" + UPDATED_NACIONALIDAD);
    }

    @Test
    @Transactional
    public void getAllCatNacionalidadsByNacionalidadIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catNacionalidadRepository.saveAndFlush(catNacionalidad);

        // Get all the catNacionalidadList where nacionalidad not equals to DEFAULT_NACIONALIDAD
        defaultCatNacionalidadShouldNotBeFound("nacionalidad.notEquals=" + DEFAULT_NACIONALIDAD);

        // Get all the catNacionalidadList where nacionalidad not equals to UPDATED_NACIONALIDAD
        defaultCatNacionalidadShouldBeFound("nacionalidad.notEquals=" + UPDATED_NACIONALIDAD);
    }

    @Test
    @Transactional
    public void getAllCatNacionalidadsByNacionalidadIsInShouldWork() throws Exception {
        // Initialize the database
        catNacionalidadRepository.saveAndFlush(catNacionalidad);

        // Get all the catNacionalidadList where nacionalidad in DEFAULT_NACIONALIDAD or UPDATED_NACIONALIDAD
        defaultCatNacionalidadShouldBeFound("nacionalidad.in=" + DEFAULT_NACIONALIDAD + "," + UPDATED_NACIONALIDAD);

        // Get all the catNacionalidadList where nacionalidad equals to UPDATED_NACIONALIDAD
        defaultCatNacionalidadShouldNotBeFound("nacionalidad.in=" + UPDATED_NACIONALIDAD);
    }

    @Test
    @Transactional
    public void getAllCatNacionalidadsByNacionalidadIsNullOrNotNull() throws Exception {
        // Initialize the database
        catNacionalidadRepository.saveAndFlush(catNacionalidad);

        // Get all the catNacionalidadList where nacionalidad is not null
        defaultCatNacionalidadShouldBeFound("nacionalidad.specified=true");

        // Get all the catNacionalidadList where nacionalidad is null
        defaultCatNacionalidadShouldNotBeFound("nacionalidad.specified=false");
    }
                @Test
    @Transactional
    public void getAllCatNacionalidadsByNacionalidadContainsSomething() throws Exception {
        // Initialize the database
        catNacionalidadRepository.saveAndFlush(catNacionalidad);

        // Get all the catNacionalidadList where nacionalidad contains DEFAULT_NACIONALIDAD
        defaultCatNacionalidadShouldBeFound("nacionalidad.contains=" + DEFAULT_NACIONALIDAD);

        // Get all the catNacionalidadList where nacionalidad contains UPDATED_NACIONALIDAD
        defaultCatNacionalidadShouldNotBeFound("nacionalidad.contains=" + UPDATED_NACIONALIDAD);
    }

    @Test
    @Transactional
    public void getAllCatNacionalidadsByNacionalidadNotContainsSomething() throws Exception {
        // Initialize the database
        catNacionalidadRepository.saveAndFlush(catNacionalidad);

        // Get all the catNacionalidadList where nacionalidad does not contain DEFAULT_NACIONALIDAD
        defaultCatNacionalidadShouldNotBeFound("nacionalidad.doesNotContain=" + DEFAULT_NACIONALIDAD);

        // Get all the catNacionalidadList where nacionalidad does not contain UPDATED_NACIONALIDAD
        defaultCatNacionalidadShouldBeFound("nacionalidad.doesNotContain=" + UPDATED_NACIONALIDAD);
    }


    @Test
    @Transactional
    public void getAllCatNacionalidadsByUsuarioIsEqualToSomething() throws Exception {
        // Get already existing entity
        User usuario = catNacionalidad.getUsuario();
        catNacionalidadRepository.saveAndFlush(catNacionalidad);
        Long usuarioId = usuario.getId();

        // Get all the catNacionalidadList where usuario equals to usuarioId
        defaultCatNacionalidadShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the catNacionalidadList where usuario equals to usuarioId + 1
        defaultCatNacionalidadShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCatNacionalidadShouldBeFound(String filter) throws Exception {
        restCatNacionalidadMockMvc.perform(get("/api/cat-nacionalidads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catNacionalidad.getId().intValue())))
            .andExpect(jsonPath("$.[*].nacionalidad").value(hasItem(DEFAULT_NACIONALIDAD)));

        // Check, that the count call also returns 1
        restCatNacionalidadMockMvc.perform(get("/api/cat-nacionalidads/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCatNacionalidadShouldNotBeFound(String filter) throws Exception {
        restCatNacionalidadMockMvc.perform(get("/api/cat-nacionalidads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCatNacionalidadMockMvc.perform(get("/api/cat-nacionalidads/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCatNacionalidad() throws Exception {
        // Get the catNacionalidad
        restCatNacionalidadMockMvc.perform(get("/api/cat-nacionalidads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCatNacionalidad() throws Exception {
        // Initialize the database
        catNacionalidadService.save(catNacionalidad);

        int databaseSizeBeforeUpdate = catNacionalidadRepository.findAll().size();

        // Update the catNacionalidad
        CatNacionalidad updatedCatNacionalidad = catNacionalidadRepository.findById(catNacionalidad.getId()).get();
        // Disconnect from session so that the updates on updatedCatNacionalidad are not directly saved in db
        em.detach(updatedCatNacionalidad);
        updatedCatNacionalidad
            .nacionalidad(UPDATED_NACIONALIDAD);

        restCatNacionalidadMockMvc.perform(put("/api/cat-nacionalidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCatNacionalidad)))
            .andExpect(status().isOk());

        // Validate the CatNacionalidad in the database
        List<CatNacionalidad> catNacionalidadList = catNacionalidadRepository.findAll();
        assertThat(catNacionalidadList).hasSize(databaseSizeBeforeUpdate);
        CatNacionalidad testCatNacionalidad = catNacionalidadList.get(catNacionalidadList.size() - 1);
        assertThat(testCatNacionalidad.getNacionalidad()).isEqualTo(UPDATED_NACIONALIDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingCatNacionalidad() throws Exception {
        int databaseSizeBeforeUpdate = catNacionalidadRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatNacionalidadMockMvc.perform(put("/api/cat-nacionalidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catNacionalidad)))
            .andExpect(status().isBadRequest());

        // Validate the CatNacionalidad in the database
        List<CatNacionalidad> catNacionalidadList = catNacionalidadRepository.findAll();
        assertThat(catNacionalidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCatNacionalidad() throws Exception {
        // Initialize the database
        catNacionalidadService.save(catNacionalidad);

        int databaseSizeBeforeDelete = catNacionalidadRepository.findAll().size();

        // Delete the catNacionalidad
        restCatNacionalidadMockMvc.perform(delete("/api/cat-nacionalidads/{id}", catNacionalidad.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CatNacionalidad> catNacionalidadList = catNacionalidadRepository.findAll();
        assertThat(catNacionalidadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
