package com.resamx.web.rest;

import com.resamx.ResaMxWebApp;
import com.resamx.domain.CatSector;
import com.resamx.domain.User;
import com.resamx.repository.CatSectorRepository;
import com.resamx.service.CatSectorService;
import com.resamx.service.dto.CatSectorCriteria;
import com.resamx.service.CatSectorQueryService;

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
 * Integration tests for the {@link CatSectorResource} REST controller.
 */
@SpringBootTest(classes = ResaMxWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CatSectorResourceIT {

    private static final String DEFAULT_ACTIVIDAD_ECONOMICA = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVIDAD_ECONOMICA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVIDAD_VULNERABLE = false;
    private static final Boolean UPDATED_ACTIVIDAD_VULNERABLE = true;

    @Autowired
    private CatSectorRepository catSectorRepository;

    @Autowired
    private CatSectorService catSectorService;

    @Autowired
    private CatSectorQueryService catSectorQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCatSectorMockMvc;

    private CatSector catSector;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatSector createEntity(EntityManager em) {
        CatSector catSector = new CatSector()
            .actividadEconomica(DEFAULT_ACTIVIDAD_ECONOMICA)
            .actividadVulnerable(DEFAULT_ACTIVIDAD_VULNERABLE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catSector.setUsuario(user);
        return catSector;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatSector createUpdatedEntity(EntityManager em) {
        CatSector catSector = new CatSector()
            .actividadEconomica(UPDATED_ACTIVIDAD_ECONOMICA)
            .actividadVulnerable(UPDATED_ACTIVIDAD_VULNERABLE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catSector.setUsuario(user);
        return catSector;
    }

    @BeforeEach
    public void initTest() {
        catSector = createEntity(em);
    }

    @Test
    @Transactional
    public void createCatSector() throws Exception {
        int databaseSizeBeforeCreate = catSectorRepository.findAll().size();
        // Create the CatSector
        restCatSectorMockMvc.perform(post("/api/cat-sectors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catSector)))
            .andExpect(status().isCreated());

        // Validate the CatSector in the database
        List<CatSector> catSectorList = catSectorRepository.findAll();
        assertThat(catSectorList).hasSize(databaseSizeBeforeCreate + 1);
        CatSector testCatSector = catSectorList.get(catSectorList.size() - 1);
        assertThat(testCatSector.getActividadEconomica()).isEqualTo(DEFAULT_ACTIVIDAD_ECONOMICA);
        assertThat(testCatSector.isActividadVulnerable()).isEqualTo(DEFAULT_ACTIVIDAD_VULNERABLE);
    }

    @Test
    @Transactional
    public void createCatSectorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = catSectorRepository.findAll().size();

        // Create the CatSector with an existing ID
        catSector.setId("1");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatSectorMockMvc.perform(post("/api/cat-sectors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catSector)))
            .andExpect(status().isBadRequest());

        // Validate the CatSector in the database
        List<CatSector> catSectorList = catSectorRepository.findAll();
        assertThat(catSectorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkActividadEconomicaIsRequired() throws Exception {
        int databaseSizeBeforeTest = catSectorRepository.findAll().size();
        // set the field null
        catSector.setActividadEconomica(null);

        // Create the CatSector, which fails.


        restCatSectorMockMvc.perform(post("/api/cat-sectors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catSector)))
            .andExpect(status().isBadRequest());

        List<CatSector> catSectorList = catSectorRepository.findAll();
        assertThat(catSectorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActividadVulnerableIsRequired() throws Exception {
        int databaseSizeBeforeTest = catSectorRepository.findAll().size();
        // set the field null
        catSector.setActividadVulnerable(null);

        // Create the CatSector, which fails.


        restCatSectorMockMvc.perform(post("/api/cat-sectors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catSector)))
            .andExpect(status().isBadRequest());

        List<CatSector> catSectorList = catSectorRepository.findAll();
        assertThat(catSectorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCatSectors() throws Exception {
        // Initialize the database
        catSectorRepository.saveAndFlush(catSector);

        // Get all the catSectorList
        restCatSectorMockMvc.perform(get("/api/cat-sectors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catSector.getId())))
            .andExpect(jsonPath("$.[*].actividadEconomica").value(hasItem(DEFAULT_ACTIVIDAD_ECONOMICA)))
            .andExpect(jsonPath("$.[*].actividadVulnerable").value(hasItem(DEFAULT_ACTIVIDAD_VULNERABLE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCatSector() throws Exception {
        // Initialize the database
        catSectorRepository.saveAndFlush(catSector);

        // Get the catSector
        restCatSectorMockMvc.perform(get("/api/cat-sectors/{id}", catSector.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(catSector.getId()))
            .andExpect(jsonPath("$.actividadEconomica").value(DEFAULT_ACTIVIDAD_ECONOMICA))
            .andExpect(jsonPath("$.actividadVulnerable").value(DEFAULT_ACTIVIDAD_VULNERABLE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCatSectorsByIdFiltering() throws Exception {
        // Initialize the database
        catSectorRepository.saveAndFlush(catSector);

        String id = catSector.getId();

        defaultCatSectorShouldBeFound("id.equals=" + id);
        defaultCatSectorShouldNotBeFound("id.notEquals=" + id);

        defaultCatSectorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCatSectorShouldNotBeFound("id.greaterThan=" + id);

        defaultCatSectorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCatSectorShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCatSectorsByActividadEconomicaIsEqualToSomething() throws Exception {
        // Initialize the database
        catSectorRepository.saveAndFlush(catSector);

        // Get all the catSectorList where actividadEconomica equals to DEFAULT_ACTIVIDAD_ECONOMICA
        defaultCatSectorShouldBeFound("actividadEconomica.equals=" + DEFAULT_ACTIVIDAD_ECONOMICA);

        // Get all the catSectorList where actividadEconomica equals to UPDATED_ACTIVIDAD_ECONOMICA
        defaultCatSectorShouldNotBeFound("actividadEconomica.equals=" + UPDATED_ACTIVIDAD_ECONOMICA);
    }

    @Test
    @Transactional
    public void getAllCatSectorsByActividadEconomicaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catSectorRepository.saveAndFlush(catSector);

        // Get all the catSectorList where actividadEconomica not equals to DEFAULT_ACTIVIDAD_ECONOMICA
        defaultCatSectorShouldNotBeFound("actividadEconomica.notEquals=" + DEFAULT_ACTIVIDAD_ECONOMICA);

        // Get all the catSectorList where actividadEconomica not equals to UPDATED_ACTIVIDAD_ECONOMICA
        defaultCatSectorShouldBeFound("actividadEconomica.notEquals=" + UPDATED_ACTIVIDAD_ECONOMICA);
    }

    @Test
    @Transactional
    public void getAllCatSectorsByActividadEconomicaIsInShouldWork() throws Exception {
        // Initialize the database
        catSectorRepository.saveAndFlush(catSector);

        // Get all the catSectorList where actividadEconomica in DEFAULT_ACTIVIDAD_ECONOMICA or UPDATED_ACTIVIDAD_ECONOMICA
        defaultCatSectorShouldBeFound("actividadEconomica.in=" + DEFAULT_ACTIVIDAD_ECONOMICA + "," + UPDATED_ACTIVIDAD_ECONOMICA);

        // Get all the catSectorList where actividadEconomica equals to UPDATED_ACTIVIDAD_ECONOMICA
        defaultCatSectorShouldNotBeFound("actividadEconomica.in=" + UPDATED_ACTIVIDAD_ECONOMICA);
    }

    @Test
    @Transactional
    public void getAllCatSectorsByActividadEconomicaIsNullOrNotNull() throws Exception {
        // Initialize the database
        catSectorRepository.saveAndFlush(catSector);

        // Get all the catSectorList where actividadEconomica is not null
        defaultCatSectorShouldBeFound("actividadEconomica.specified=true");

        // Get all the catSectorList where actividadEconomica is null
        defaultCatSectorShouldNotBeFound("actividadEconomica.specified=false");
    }
                @Test
    @Transactional
    public void getAllCatSectorsByActividadEconomicaContainsSomething() throws Exception {
        // Initialize the database
        catSectorRepository.saveAndFlush(catSector);

        // Get all the catSectorList where actividadEconomica contains DEFAULT_ACTIVIDAD_ECONOMICA
        defaultCatSectorShouldBeFound("actividadEconomica.contains=" + DEFAULT_ACTIVIDAD_ECONOMICA);

        // Get all the catSectorList where actividadEconomica contains UPDATED_ACTIVIDAD_ECONOMICA
        defaultCatSectorShouldNotBeFound("actividadEconomica.contains=" + UPDATED_ACTIVIDAD_ECONOMICA);
    }

    @Test
    @Transactional
    public void getAllCatSectorsByActividadEconomicaNotContainsSomething() throws Exception {
        // Initialize the database
        catSectorRepository.saveAndFlush(catSector);

        // Get all the catSectorList where actividadEconomica does not contain DEFAULT_ACTIVIDAD_ECONOMICA
        defaultCatSectorShouldNotBeFound("actividadEconomica.doesNotContain=" + DEFAULT_ACTIVIDAD_ECONOMICA);

        // Get all the catSectorList where actividadEconomica does not contain UPDATED_ACTIVIDAD_ECONOMICA
        defaultCatSectorShouldBeFound("actividadEconomica.doesNotContain=" + UPDATED_ACTIVIDAD_ECONOMICA);
    }


    @Test
    @Transactional
    public void getAllCatSectorsByActividadVulnerableIsEqualToSomething() throws Exception {
        // Initialize the database
        catSectorRepository.saveAndFlush(catSector);

        // Get all the catSectorList where actividadVulnerable equals to DEFAULT_ACTIVIDAD_VULNERABLE
        defaultCatSectorShouldBeFound("actividadVulnerable.equals=" + DEFAULT_ACTIVIDAD_VULNERABLE);

        // Get all the catSectorList where actividadVulnerable equals to UPDATED_ACTIVIDAD_VULNERABLE
        defaultCatSectorShouldNotBeFound("actividadVulnerable.equals=" + UPDATED_ACTIVIDAD_VULNERABLE);
    }

    @Test
    @Transactional
    public void getAllCatSectorsByActividadVulnerableIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catSectorRepository.saveAndFlush(catSector);

        // Get all the catSectorList where actividadVulnerable not equals to DEFAULT_ACTIVIDAD_VULNERABLE
        defaultCatSectorShouldNotBeFound("actividadVulnerable.notEquals=" + DEFAULT_ACTIVIDAD_VULNERABLE);

        // Get all the catSectorList where actividadVulnerable not equals to UPDATED_ACTIVIDAD_VULNERABLE
        defaultCatSectorShouldBeFound("actividadVulnerable.notEquals=" + UPDATED_ACTIVIDAD_VULNERABLE);
    }

    @Test
    @Transactional
    public void getAllCatSectorsByActividadVulnerableIsInShouldWork() throws Exception {
        // Initialize the database
        catSectorRepository.saveAndFlush(catSector);

        // Get all the catSectorList where actividadVulnerable in DEFAULT_ACTIVIDAD_VULNERABLE or UPDATED_ACTIVIDAD_VULNERABLE
        defaultCatSectorShouldBeFound("actividadVulnerable.in=" + DEFAULT_ACTIVIDAD_VULNERABLE + "," + UPDATED_ACTIVIDAD_VULNERABLE);

        // Get all the catSectorList where actividadVulnerable equals to UPDATED_ACTIVIDAD_VULNERABLE
        defaultCatSectorShouldNotBeFound("actividadVulnerable.in=" + UPDATED_ACTIVIDAD_VULNERABLE);
    }

    @Test
    @Transactional
    public void getAllCatSectorsByActividadVulnerableIsNullOrNotNull() throws Exception {
        // Initialize the database
        catSectorRepository.saveAndFlush(catSector);

        // Get all the catSectorList where actividadVulnerable is not null
        defaultCatSectorShouldBeFound("actividadVulnerable.specified=true");

        // Get all the catSectorList where actividadVulnerable is null
        defaultCatSectorShouldNotBeFound("actividadVulnerable.specified=false");
    }

    @Test
    @Transactional
    public void getAllCatSectorsByUsuarioIsEqualToSomething() throws Exception {
        // Get already existing entity
        User usuario = catSector.getUsuario();
        catSectorRepository.saveAndFlush(catSector);
        Long usuarioId = usuario.getId();

        // Get all the catSectorList where usuario equals to usuarioId
        defaultCatSectorShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the catSectorList where usuario equals to usuarioId + 1
        defaultCatSectorShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCatSectorShouldBeFound(String filter) throws Exception {
        restCatSectorMockMvc.perform(get("/api/cat-sectors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catSector.getId())))
            .andExpect(jsonPath("$.[*].actividadEconomica").value(hasItem(DEFAULT_ACTIVIDAD_ECONOMICA)))
            .andExpect(jsonPath("$.[*].actividadVulnerable").value(hasItem(DEFAULT_ACTIVIDAD_VULNERABLE.booleanValue())));

        // Check, that the count call also returns 1
        restCatSectorMockMvc.perform(get("/api/cat-sectors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCatSectorShouldNotBeFound(String filter) throws Exception {
        restCatSectorMockMvc.perform(get("/api/cat-sectors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCatSectorMockMvc.perform(get("/api/cat-sectors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCatSector() throws Exception {
        // Get the catSector
        restCatSectorMockMvc.perform(get("/api/cat-sectors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCatSector() throws Exception {
        // Initialize the database
        catSectorService.save(catSector);

        int databaseSizeBeforeUpdate = catSectorRepository.findAll().size();

        // Update the catSector
        CatSector updatedCatSector = catSectorRepository.findById(catSector.getId()).get();
        // Disconnect from session so that the updates on updatedCatSector are not directly saved in db
        em.detach(updatedCatSector);
        updatedCatSector
            .actividadEconomica(UPDATED_ACTIVIDAD_ECONOMICA)
            .actividadVulnerable(UPDATED_ACTIVIDAD_VULNERABLE);

        restCatSectorMockMvc.perform(put("/api/cat-sectors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCatSector)))
            .andExpect(status().isOk());

        // Validate the CatSector in the database
        List<CatSector> catSectorList = catSectorRepository.findAll();
        assertThat(catSectorList).hasSize(databaseSizeBeforeUpdate);
        CatSector testCatSector = catSectorList.get(catSectorList.size() - 1);
        assertThat(testCatSector.getActividadEconomica()).isEqualTo(UPDATED_ACTIVIDAD_ECONOMICA);
        assertThat(testCatSector.isActividadVulnerable()).isEqualTo(UPDATED_ACTIVIDAD_VULNERABLE);
    }

    @Test
    @Transactional
    public void updateNonExistingCatSector() throws Exception {
        int databaseSizeBeforeUpdate = catSectorRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatSectorMockMvc.perform(put("/api/cat-sectors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catSector)))
            .andExpect(status().isBadRequest());

        // Validate the CatSector in the database
        List<CatSector> catSectorList = catSectorRepository.findAll();
        assertThat(catSectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCatSector() throws Exception {
        // Initialize the database
        catSectorService.save(catSector);

        int databaseSizeBeforeDelete = catSectorRepository.findAll().size();

        // Delete the catSector
        restCatSectorMockMvc.perform(delete("/api/cat-sectors/{id}", catSector.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CatSector> catSectorList = catSectorRepository.findAll();
        assertThat(catSectorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
