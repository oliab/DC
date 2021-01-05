package com.resamx.web.rest;

import com.resamx.ResaMxWebApp;
import com.resamx.domain.CatCP;
import com.resamx.domain.User;
import com.resamx.domain.CatEstado;
import com.resamx.domain.CatMunicipio;
import com.resamx.domain.CatRiesgo;
import com.resamx.repository.CatCPRepository;
import com.resamx.service.CatCPService;
import com.resamx.service.dto.CatCPCriteria;
import com.resamx.service.CatCPQueryService;

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
 * Integration tests for the {@link CatCPResource} REST controller.
 */
@SpringBootTest(classes = ResaMxWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CatCPResourceIT {

    private static final Integer DEFAULT_ANIO = 1;
    private static final Integer UPDATED_ANIO = 2;
    private static final Integer SMALLER_ANIO = 1 - 1;

    @Autowired
    private CatCPRepository catCPRepository;

    @Autowired
    private CatCPService catCPService;

    @Autowired
    private CatCPQueryService catCPQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCatCPMockMvc;

    private CatCP catCP;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatCP createEntity(EntityManager em) {
        CatCP catCP = new CatCP()
            .anio(DEFAULT_ANIO);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catCP.setUsuario(user);
        // Add required entity
        CatEstado catEstado;
        if (TestUtil.findAll(em, CatEstado.class).isEmpty()) {
            catEstado = CatEstadoResourceIT.createEntity(em);
            em.persist(catEstado);
            em.flush();
        } else {
            catEstado = TestUtil.findAll(em, CatEstado.class).get(0);
        }
        catCP.setEstado(catEstado);
        // Add required entity
        CatMunicipio catMunicipio;
        if (TestUtil.findAll(em, CatMunicipio.class).isEmpty()) {
            catMunicipio = CatMunicipioResourceIT.createEntity(em);
            em.persist(catMunicipio);
            em.flush();
        } else {
            catMunicipio = TestUtil.findAll(em, CatMunicipio.class).get(0);
        }
        catCP.setMunicipio(catMunicipio);
        // Add required entity
        CatRiesgo catRiesgo;
        if (TestUtil.findAll(em, CatRiesgo.class).isEmpty()) {
            catRiesgo = CatRiesgoResourceIT.createEntity(em);
            em.persist(catRiesgo);
            em.flush();
        } else {
            catRiesgo = TestUtil.findAll(em, CatRiesgo.class).get(0);
        }
        catCP.setRiesgo(catRiesgo);
        return catCP;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatCP createUpdatedEntity(EntityManager em) {
        CatCP catCP = new CatCP()
            .anio(UPDATED_ANIO);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catCP.setUsuario(user);
        // Add required entity
        CatEstado catEstado;
        if (TestUtil.findAll(em, CatEstado.class).isEmpty()) {
            catEstado = CatEstadoResourceIT.createUpdatedEntity(em);
            em.persist(catEstado);
            em.flush();
        } else {
            catEstado = TestUtil.findAll(em, CatEstado.class).get(0);
        }
        catCP.setEstado(catEstado);
        // Add required entity
        CatMunicipio catMunicipio;
        if (TestUtil.findAll(em, CatMunicipio.class).isEmpty()) {
            catMunicipio = CatMunicipioResourceIT.createUpdatedEntity(em);
            em.persist(catMunicipio);
            em.flush();
        } else {
            catMunicipio = TestUtil.findAll(em, CatMunicipio.class).get(0);
        }
        catCP.setMunicipio(catMunicipio);
        // Add required entity
        CatRiesgo catRiesgo;
        if (TestUtil.findAll(em, CatRiesgo.class).isEmpty()) {
            catRiesgo = CatRiesgoResourceIT.createUpdatedEntity(em);
            em.persist(catRiesgo);
            em.flush();
        } else {
            catRiesgo = TestUtil.findAll(em, CatRiesgo.class).get(0);
        }
        catCP.setRiesgo(catRiesgo);
        return catCP;
    }

    @BeforeEach
    public void initTest() {
        catCP = createEntity(em);
    }

    @Test
    @Transactional
    public void createCatCP() throws Exception {
        int databaseSizeBeforeCreate = catCPRepository.findAll().size();
        // Create the CatCP
        restCatCPMockMvc.perform(post("/api/cat-cps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catCP)))
            .andExpect(status().isCreated());

        // Validate the CatCP in the database
        List<CatCP> catCPList = catCPRepository.findAll();
        assertThat(catCPList).hasSize(databaseSizeBeforeCreate + 1);
        CatCP testCatCP = catCPList.get(catCPList.size() - 1);
        assertThat(testCatCP.getAnio()).isEqualTo(DEFAULT_ANIO);
    }

    @Test
    @Transactional
    public void createCatCPWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = catCPRepository.findAll().size();

        // Create the CatCP with an existing ID
        catCP.setId("1");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatCPMockMvc.perform(post("/api/cat-cps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catCP)))
            .andExpect(status().isBadRequest());

        // Validate the CatCP in the database
        List<CatCP> catCPList = catCPRepository.findAll();
        assertThat(catCPList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCatCPS() throws Exception {
        // Initialize the database
        catCPRepository.saveAndFlush(catCP);

        // Get all the catCPList
        restCatCPMockMvc.perform(get("/api/cat-cps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catCP.getId())))
            .andExpect(jsonPath("$.[*].anio").value(hasItem(DEFAULT_ANIO)));
    }
    
    @Test
    @Transactional
    public void getCatCP() throws Exception {
        // Initialize the database
        catCPRepository.saveAndFlush(catCP);

        // Get the catCP
        restCatCPMockMvc.perform(get("/api/cat-cps/{id}", catCP.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(catCP.getId()))
            .andExpect(jsonPath("$.anio").value(DEFAULT_ANIO));
    }


    @Test
    @Transactional
    public void getCatCPSByIdFiltering() throws Exception {
        // Initialize the database
        catCPRepository.saveAndFlush(catCP);

        String id = catCP.getId();

        defaultCatCPShouldBeFound("id.equals=" + id);
        defaultCatCPShouldNotBeFound("id.notEquals=" + id);

        defaultCatCPShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCatCPShouldNotBeFound("id.greaterThan=" + id);

        defaultCatCPShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCatCPShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCatCPSByAnioIsEqualToSomething() throws Exception {
        // Initialize the database
        catCPRepository.saveAndFlush(catCP);

        // Get all the catCPList where anio equals to DEFAULT_ANIO
        defaultCatCPShouldBeFound("anio.equals=" + DEFAULT_ANIO);

        // Get all the catCPList where anio equals to UPDATED_ANIO
        defaultCatCPShouldNotBeFound("anio.equals=" + UPDATED_ANIO);
    }

    @Test
    @Transactional
    public void getAllCatCPSByAnioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catCPRepository.saveAndFlush(catCP);

        // Get all the catCPList where anio not equals to DEFAULT_ANIO
        defaultCatCPShouldNotBeFound("anio.notEquals=" + DEFAULT_ANIO);

        // Get all the catCPList where anio not equals to UPDATED_ANIO
        defaultCatCPShouldBeFound("anio.notEquals=" + UPDATED_ANIO);
    }

    @Test
    @Transactional
    public void getAllCatCPSByAnioIsInShouldWork() throws Exception {
        // Initialize the database
        catCPRepository.saveAndFlush(catCP);

        // Get all the catCPList where anio in DEFAULT_ANIO or UPDATED_ANIO
        defaultCatCPShouldBeFound("anio.in=" + DEFAULT_ANIO + "," + UPDATED_ANIO);

        // Get all the catCPList where anio equals to UPDATED_ANIO
        defaultCatCPShouldNotBeFound("anio.in=" + UPDATED_ANIO);
    }

    @Test
    @Transactional
    public void getAllCatCPSByAnioIsNullOrNotNull() throws Exception {
        // Initialize the database
        catCPRepository.saveAndFlush(catCP);

        // Get all the catCPList where anio is not null
        defaultCatCPShouldBeFound("anio.specified=true");

        // Get all the catCPList where anio is null
        defaultCatCPShouldNotBeFound("anio.specified=false");
    }

    @Test
    @Transactional
    public void getAllCatCPSByAnioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        catCPRepository.saveAndFlush(catCP);

        // Get all the catCPList where anio is greater than or equal to DEFAULT_ANIO
        defaultCatCPShouldBeFound("anio.greaterThanOrEqual=" + DEFAULT_ANIO);

        // Get all the catCPList where anio is greater than or equal to UPDATED_ANIO
        defaultCatCPShouldNotBeFound("anio.greaterThanOrEqual=" + UPDATED_ANIO);
    }

    @Test
    @Transactional
    public void getAllCatCPSByAnioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        catCPRepository.saveAndFlush(catCP);

        // Get all the catCPList where anio is less than or equal to DEFAULT_ANIO
        defaultCatCPShouldBeFound("anio.lessThanOrEqual=" + DEFAULT_ANIO);

        // Get all the catCPList where anio is less than or equal to SMALLER_ANIO
        defaultCatCPShouldNotBeFound("anio.lessThanOrEqual=" + SMALLER_ANIO);
    }

    @Test
    @Transactional
    public void getAllCatCPSByAnioIsLessThanSomething() throws Exception {
        // Initialize the database
        catCPRepository.saveAndFlush(catCP);

        // Get all the catCPList where anio is less than DEFAULT_ANIO
        defaultCatCPShouldNotBeFound("anio.lessThan=" + DEFAULT_ANIO);

        // Get all the catCPList where anio is less than UPDATED_ANIO
        defaultCatCPShouldBeFound("anio.lessThan=" + UPDATED_ANIO);
    }

    @Test
    @Transactional
    public void getAllCatCPSByAnioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        catCPRepository.saveAndFlush(catCP);

        // Get all the catCPList where anio is greater than DEFAULT_ANIO
        defaultCatCPShouldNotBeFound("anio.greaterThan=" + DEFAULT_ANIO);

        // Get all the catCPList where anio is greater than SMALLER_ANIO
        defaultCatCPShouldBeFound("anio.greaterThan=" + SMALLER_ANIO);
    }


    @Test
    @Transactional
    public void getAllCatCPSByUsuarioIsEqualToSomething() throws Exception {
        // Get already existing entity
        User usuario = catCP.getUsuario();
        catCPRepository.saveAndFlush(catCP);
        Long usuarioId = usuario.getId();

        // Get all the catCPList where usuario equals to usuarioId
        defaultCatCPShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the catCPList where usuario equals to usuarioId + 1
        defaultCatCPShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }


    @Test
    @Transactional
    public void getAllCatCPSByEstadoIsEqualToSomething() throws Exception {
        // Get already existing entity
        CatEstado estado = catCP.getEstado();
        catCPRepository.saveAndFlush(catCP);
        String estadoId = estado.getId();

        // Get all the catCPList where estado equals to estadoId
        defaultCatCPShouldBeFound("estadoId.equals=" + estadoId);

        // Get all the catCPList where estado equals to estadoId + 1
        defaultCatCPShouldNotBeFound("estadoId.equals=" + (estadoId + 1));
    }


    @Test
    @Transactional
    public void getAllCatCPSByMunicipioIsEqualToSomething() throws Exception {
        // Get already existing entity
        CatMunicipio municipio = catCP.getMunicipio();
        catCPRepository.saveAndFlush(catCP);
        String municipioId = municipio.getId();

        // Get all the catCPList where municipio equals to municipioId
        defaultCatCPShouldBeFound("municipioId.equals=" + municipioId);

        // Get all the catCPList where municipio equals to municipioId + 1
        defaultCatCPShouldNotBeFound("municipioId.equals=" + (municipioId + 1));
    }


    @Test
    @Transactional
    public void getAllCatCPSByRiesgoIsEqualToSomething() throws Exception {
        // Get already existing entity
        CatRiesgo riesgo = catCP.getRiesgo();
        catCPRepository.saveAndFlush(catCP);
        Long riesgoId = riesgo.getId();

        // Get all the catCPList where riesgo equals to riesgoId
        defaultCatCPShouldBeFound("riesgoId.equals=" + riesgoId);

        // Get all the catCPList where riesgo equals to riesgoId + 1
        defaultCatCPShouldNotBeFound("riesgoId.equals=" + (riesgoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCatCPShouldBeFound(String filter) throws Exception {
        restCatCPMockMvc.perform(get("/api/cat-cps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catCP.getId())))
            .andExpect(jsonPath("$.[*].anio").value(hasItem(DEFAULT_ANIO)));

        // Check, that the count call also returns 1
        restCatCPMockMvc.perform(get("/api/cat-cps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCatCPShouldNotBeFound(String filter) throws Exception {
        restCatCPMockMvc.perform(get("/api/cat-cps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCatCPMockMvc.perform(get("/api/cat-cps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCatCP() throws Exception {
        // Get the catCP
        restCatCPMockMvc.perform(get("/api/cat-cps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCatCP() throws Exception {
        // Initialize the database
        catCPService.save(catCP);

        int databaseSizeBeforeUpdate = catCPRepository.findAll().size();

        // Update the catCP
        CatCP updatedCatCP = catCPRepository.findById(catCP.getId()).get();
        // Disconnect from session so that the updates on updatedCatCP are not directly saved in db
        em.detach(updatedCatCP);
        updatedCatCP
            .anio(UPDATED_ANIO);

        restCatCPMockMvc.perform(put("/api/cat-cps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCatCP)))
            .andExpect(status().isOk());

        // Validate the CatCP in the database
        List<CatCP> catCPList = catCPRepository.findAll();
        assertThat(catCPList).hasSize(databaseSizeBeforeUpdate);
        CatCP testCatCP = catCPList.get(catCPList.size() - 1);
        assertThat(testCatCP.getAnio()).isEqualTo(UPDATED_ANIO);
    }

    @Test
    @Transactional
    public void updateNonExistingCatCP() throws Exception {
        int databaseSizeBeforeUpdate = catCPRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatCPMockMvc.perform(put("/api/cat-cps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catCP)))
            .andExpect(status().isBadRequest());

        // Validate the CatCP in the database
        List<CatCP> catCPList = catCPRepository.findAll();
        assertThat(catCPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCatCP() throws Exception {
        // Initialize the database
        catCPService.save(catCP);

        int databaseSizeBeforeDelete = catCPRepository.findAll().size();

        // Delete the catCP
        restCatCPMockMvc.perform(delete("/api/cat-cps/{id}", catCP.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CatCP> catCPList = catCPRepository.findAll();
        assertThat(catCPList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
