package com.resamx.web.rest;

import com.resamx.ResaMxWebApp;
import com.resamx.domain.CatIdentificacion;
import com.resamx.domain.User;
import com.resamx.repository.CatIdentificacionRepository;
import com.resamx.service.CatIdentificacionService;
import com.resamx.service.dto.CatIdentificacionCriteria;
import com.resamx.service.CatIdentificacionQueryService;

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
 * Integration tests for the {@link CatIdentificacionResource} REST controller.
 */
@SpringBootTest(classes = ResaMxWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CatIdentificacionResourceIT {

    private static final String DEFAULT_IDENTIFICACION = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICACION = "BBBBBBBBBB";

    @Autowired
    private CatIdentificacionRepository catIdentificacionRepository;

    @Autowired
    private CatIdentificacionService catIdentificacionService;

    @Autowired
    private CatIdentificacionQueryService catIdentificacionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCatIdentificacionMockMvc;

    private CatIdentificacion catIdentificacion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatIdentificacion createEntity(EntityManager em) {
        CatIdentificacion catIdentificacion = new CatIdentificacion()
            .identificacion(DEFAULT_IDENTIFICACION);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catIdentificacion.setUsuario(user);
        return catIdentificacion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatIdentificacion createUpdatedEntity(EntityManager em) {
        CatIdentificacion catIdentificacion = new CatIdentificacion()
            .identificacion(UPDATED_IDENTIFICACION);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catIdentificacion.setUsuario(user);
        return catIdentificacion;
    }

    @BeforeEach
    public void initTest() {
        catIdentificacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createCatIdentificacion() throws Exception {
        int databaseSizeBeforeCreate = catIdentificacionRepository.findAll().size();
        // Create the CatIdentificacion
        restCatIdentificacionMockMvc.perform(post("/api/cat-identificacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catIdentificacion)))
            .andExpect(status().isCreated());

        // Validate the CatIdentificacion in the database
        List<CatIdentificacion> catIdentificacionList = catIdentificacionRepository.findAll();
        assertThat(catIdentificacionList).hasSize(databaseSizeBeforeCreate + 1);
        CatIdentificacion testCatIdentificacion = catIdentificacionList.get(catIdentificacionList.size() - 1);
        assertThat(testCatIdentificacion.getIdentificacion()).isEqualTo(DEFAULT_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void createCatIdentificacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = catIdentificacionRepository.findAll().size();

        // Create the CatIdentificacion with an existing ID
        catIdentificacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatIdentificacionMockMvc.perform(post("/api/cat-identificacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catIdentificacion)))
            .andExpect(status().isBadRequest());

        // Validate the CatIdentificacion in the database
        List<CatIdentificacion> catIdentificacionList = catIdentificacionRepository.findAll();
        assertThat(catIdentificacionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdentificacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = catIdentificacionRepository.findAll().size();
        // set the field null
        catIdentificacion.setIdentificacion(null);

        // Create the CatIdentificacion, which fails.


        restCatIdentificacionMockMvc.perform(post("/api/cat-identificacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catIdentificacion)))
            .andExpect(status().isBadRequest());

        List<CatIdentificacion> catIdentificacionList = catIdentificacionRepository.findAll();
        assertThat(catIdentificacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCatIdentificacions() throws Exception {
        // Initialize the database
        catIdentificacionRepository.saveAndFlush(catIdentificacion);

        // Get all the catIdentificacionList
        restCatIdentificacionMockMvc.perform(get("/api/cat-identificacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catIdentificacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].identificacion").value(hasItem(DEFAULT_IDENTIFICACION)));
    }
    
    @Test
    @Transactional
    public void getCatIdentificacion() throws Exception {
        // Initialize the database
        catIdentificacionRepository.saveAndFlush(catIdentificacion);

        // Get the catIdentificacion
        restCatIdentificacionMockMvc.perform(get("/api/cat-identificacions/{id}", catIdentificacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(catIdentificacion.getId().intValue()))
            .andExpect(jsonPath("$.identificacion").value(DEFAULT_IDENTIFICACION));
    }


    @Test
    @Transactional
    public void getCatIdentificacionsByIdFiltering() throws Exception {
        // Initialize the database
        catIdentificacionRepository.saveAndFlush(catIdentificacion);

        Long id = catIdentificacion.getId();

        defaultCatIdentificacionShouldBeFound("id.equals=" + id);
        defaultCatIdentificacionShouldNotBeFound("id.notEquals=" + id);

        defaultCatIdentificacionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCatIdentificacionShouldNotBeFound("id.greaterThan=" + id);

        defaultCatIdentificacionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCatIdentificacionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCatIdentificacionsByIdentificacionIsEqualToSomething() throws Exception {
        // Initialize the database
        catIdentificacionRepository.saveAndFlush(catIdentificacion);

        // Get all the catIdentificacionList where identificacion equals to DEFAULT_IDENTIFICACION
        defaultCatIdentificacionShouldBeFound("identificacion.equals=" + DEFAULT_IDENTIFICACION);

        // Get all the catIdentificacionList where identificacion equals to UPDATED_IDENTIFICACION
        defaultCatIdentificacionShouldNotBeFound("identificacion.equals=" + UPDATED_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void getAllCatIdentificacionsByIdentificacionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catIdentificacionRepository.saveAndFlush(catIdentificacion);

        // Get all the catIdentificacionList where identificacion not equals to DEFAULT_IDENTIFICACION
        defaultCatIdentificacionShouldNotBeFound("identificacion.notEquals=" + DEFAULT_IDENTIFICACION);

        // Get all the catIdentificacionList where identificacion not equals to UPDATED_IDENTIFICACION
        defaultCatIdentificacionShouldBeFound("identificacion.notEquals=" + UPDATED_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void getAllCatIdentificacionsByIdentificacionIsInShouldWork() throws Exception {
        // Initialize the database
        catIdentificacionRepository.saveAndFlush(catIdentificacion);

        // Get all the catIdentificacionList where identificacion in DEFAULT_IDENTIFICACION or UPDATED_IDENTIFICACION
        defaultCatIdentificacionShouldBeFound("identificacion.in=" + DEFAULT_IDENTIFICACION + "," + UPDATED_IDENTIFICACION);

        // Get all the catIdentificacionList where identificacion equals to UPDATED_IDENTIFICACION
        defaultCatIdentificacionShouldNotBeFound("identificacion.in=" + UPDATED_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void getAllCatIdentificacionsByIdentificacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        catIdentificacionRepository.saveAndFlush(catIdentificacion);

        // Get all the catIdentificacionList where identificacion is not null
        defaultCatIdentificacionShouldBeFound("identificacion.specified=true");

        // Get all the catIdentificacionList where identificacion is null
        defaultCatIdentificacionShouldNotBeFound("identificacion.specified=false");
    }
                @Test
    @Transactional
    public void getAllCatIdentificacionsByIdentificacionContainsSomething() throws Exception {
        // Initialize the database
        catIdentificacionRepository.saveAndFlush(catIdentificacion);

        // Get all the catIdentificacionList where identificacion contains DEFAULT_IDENTIFICACION
        defaultCatIdentificacionShouldBeFound("identificacion.contains=" + DEFAULT_IDENTIFICACION);

        // Get all the catIdentificacionList where identificacion contains UPDATED_IDENTIFICACION
        defaultCatIdentificacionShouldNotBeFound("identificacion.contains=" + UPDATED_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void getAllCatIdentificacionsByIdentificacionNotContainsSomething() throws Exception {
        // Initialize the database
        catIdentificacionRepository.saveAndFlush(catIdentificacion);

        // Get all the catIdentificacionList where identificacion does not contain DEFAULT_IDENTIFICACION
        defaultCatIdentificacionShouldNotBeFound("identificacion.doesNotContain=" + DEFAULT_IDENTIFICACION);

        // Get all the catIdentificacionList where identificacion does not contain UPDATED_IDENTIFICACION
        defaultCatIdentificacionShouldBeFound("identificacion.doesNotContain=" + UPDATED_IDENTIFICACION);
    }


    @Test
    @Transactional
    public void getAllCatIdentificacionsByUsuarioIsEqualToSomething() throws Exception {
        // Get already existing entity
        User usuario = catIdentificacion.getUsuario();
        catIdentificacionRepository.saveAndFlush(catIdentificacion);
        Long usuarioId = usuario.getId();

        // Get all the catIdentificacionList where usuario equals to usuarioId
        defaultCatIdentificacionShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the catIdentificacionList where usuario equals to usuarioId + 1
        defaultCatIdentificacionShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCatIdentificacionShouldBeFound(String filter) throws Exception {
        restCatIdentificacionMockMvc.perform(get("/api/cat-identificacions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catIdentificacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].identificacion").value(hasItem(DEFAULT_IDENTIFICACION)));

        // Check, that the count call also returns 1
        restCatIdentificacionMockMvc.perform(get("/api/cat-identificacions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCatIdentificacionShouldNotBeFound(String filter) throws Exception {
        restCatIdentificacionMockMvc.perform(get("/api/cat-identificacions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCatIdentificacionMockMvc.perform(get("/api/cat-identificacions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCatIdentificacion() throws Exception {
        // Get the catIdentificacion
        restCatIdentificacionMockMvc.perform(get("/api/cat-identificacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCatIdentificacion() throws Exception {
        // Initialize the database
        catIdentificacionService.save(catIdentificacion);

        int databaseSizeBeforeUpdate = catIdentificacionRepository.findAll().size();

        // Update the catIdentificacion
        CatIdentificacion updatedCatIdentificacion = catIdentificacionRepository.findById(catIdentificacion.getId()).get();
        // Disconnect from session so that the updates on updatedCatIdentificacion are not directly saved in db
        em.detach(updatedCatIdentificacion);
        updatedCatIdentificacion
            .identificacion(UPDATED_IDENTIFICACION);

        restCatIdentificacionMockMvc.perform(put("/api/cat-identificacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCatIdentificacion)))
            .andExpect(status().isOk());

        // Validate the CatIdentificacion in the database
        List<CatIdentificacion> catIdentificacionList = catIdentificacionRepository.findAll();
        assertThat(catIdentificacionList).hasSize(databaseSizeBeforeUpdate);
        CatIdentificacion testCatIdentificacion = catIdentificacionList.get(catIdentificacionList.size() - 1);
        assertThat(testCatIdentificacion.getIdentificacion()).isEqualTo(UPDATED_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void updateNonExistingCatIdentificacion() throws Exception {
        int databaseSizeBeforeUpdate = catIdentificacionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatIdentificacionMockMvc.perform(put("/api/cat-identificacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catIdentificacion)))
            .andExpect(status().isBadRequest());

        // Validate the CatIdentificacion in the database
        List<CatIdentificacion> catIdentificacionList = catIdentificacionRepository.findAll();
        assertThat(catIdentificacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCatIdentificacion() throws Exception {
        // Initialize the database
        catIdentificacionService.save(catIdentificacion);

        int databaseSizeBeforeDelete = catIdentificacionRepository.findAll().size();

        // Delete the catIdentificacion
        restCatIdentificacionMockMvc.perform(delete("/api/cat-identificacions/{id}", catIdentificacion.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CatIdentificacion> catIdentificacionList = catIdentificacionRepository.findAll();
        assertThat(catIdentificacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
