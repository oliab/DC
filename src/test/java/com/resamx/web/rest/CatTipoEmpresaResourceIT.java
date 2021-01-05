package com.resamx.web.rest;

import com.resamx.ResaMxWebApp;
import com.resamx.domain.CatTipoEmpresa;
import com.resamx.domain.User;
import com.resamx.repository.CatTipoEmpresaRepository;
import com.resamx.service.CatTipoEmpresaService;
import com.resamx.service.dto.CatTipoEmpresaCriteria;
import com.resamx.service.CatTipoEmpresaQueryService;

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
 * Integration tests for the {@link CatTipoEmpresaResource} REST controller.
 */
@SpringBootTest(classes = ResaMxWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CatTipoEmpresaResourceIT {

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    @Autowired
    private CatTipoEmpresaRepository catTipoEmpresaRepository;

    @Autowired
    private CatTipoEmpresaService catTipoEmpresaService;

    @Autowired
    private CatTipoEmpresaQueryService catTipoEmpresaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCatTipoEmpresaMockMvc;

    private CatTipoEmpresa catTipoEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatTipoEmpresa createEntity(EntityManager em) {
        CatTipoEmpresa catTipoEmpresa = new CatTipoEmpresa()
            .tipo(DEFAULT_TIPO);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catTipoEmpresa.setUsuario(user);
        return catTipoEmpresa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatTipoEmpresa createUpdatedEntity(EntityManager em) {
        CatTipoEmpresa catTipoEmpresa = new CatTipoEmpresa()
            .tipo(UPDATED_TIPO);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catTipoEmpresa.setUsuario(user);
        return catTipoEmpresa;
    }

    @BeforeEach
    public void initTest() {
        catTipoEmpresa = createEntity(em);
    }

    @Test
    @Transactional
    public void createCatTipoEmpresa() throws Exception {
        int databaseSizeBeforeCreate = catTipoEmpresaRepository.findAll().size();
        // Create the CatTipoEmpresa
        restCatTipoEmpresaMockMvc.perform(post("/api/cat-tipo-empresas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catTipoEmpresa)))
            .andExpect(status().isCreated());

        // Validate the CatTipoEmpresa in the database
        List<CatTipoEmpresa> catTipoEmpresaList = catTipoEmpresaRepository.findAll();
        assertThat(catTipoEmpresaList).hasSize(databaseSizeBeforeCreate + 1);
        CatTipoEmpresa testCatTipoEmpresa = catTipoEmpresaList.get(catTipoEmpresaList.size() - 1);
        assertThat(testCatTipoEmpresa.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    public void createCatTipoEmpresaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = catTipoEmpresaRepository.findAll().size();

        // Create the CatTipoEmpresa with an existing ID
        catTipoEmpresa.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatTipoEmpresaMockMvc.perform(post("/api/cat-tipo-empresas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catTipoEmpresa)))
            .andExpect(status().isBadRequest());

        // Validate the CatTipoEmpresa in the database
        List<CatTipoEmpresa> catTipoEmpresaList = catTipoEmpresaRepository.findAll();
        assertThat(catTipoEmpresaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = catTipoEmpresaRepository.findAll().size();
        // set the field null
        catTipoEmpresa.setTipo(null);

        // Create the CatTipoEmpresa, which fails.


        restCatTipoEmpresaMockMvc.perform(post("/api/cat-tipo-empresas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catTipoEmpresa)))
            .andExpect(status().isBadRequest());

        List<CatTipoEmpresa> catTipoEmpresaList = catTipoEmpresaRepository.findAll();
        assertThat(catTipoEmpresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCatTipoEmpresas() throws Exception {
        // Initialize the database
        catTipoEmpresaRepository.saveAndFlush(catTipoEmpresa);

        // Get all the catTipoEmpresaList
        restCatTipoEmpresaMockMvc.perform(get("/api/cat-tipo-empresas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catTipoEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)));
    }
    
    @Test
    @Transactional
    public void getCatTipoEmpresa() throws Exception {
        // Initialize the database
        catTipoEmpresaRepository.saveAndFlush(catTipoEmpresa);

        // Get the catTipoEmpresa
        restCatTipoEmpresaMockMvc.perform(get("/api/cat-tipo-empresas/{id}", catTipoEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(catTipoEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO));
    }


    @Test
    @Transactional
    public void getCatTipoEmpresasByIdFiltering() throws Exception {
        // Initialize the database
        catTipoEmpresaRepository.saveAndFlush(catTipoEmpresa);

        Long id = catTipoEmpresa.getId();

        defaultCatTipoEmpresaShouldBeFound("id.equals=" + id);
        defaultCatTipoEmpresaShouldNotBeFound("id.notEquals=" + id);

        defaultCatTipoEmpresaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCatTipoEmpresaShouldNotBeFound("id.greaterThan=" + id);

        defaultCatTipoEmpresaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCatTipoEmpresaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCatTipoEmpresasByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        catTipoEmpresaRepository.saveAndFlush(catTipoEmpresa);

        // Get all the catTipoEmpresaList where tipo equals to DEFAULT_TIPO
        defaultCatTipoEmpresaShouldBeFound("tipo.equals=" + DEFAULT_TIPO);

        // Get all the catTipoEmpresaList where tipo equals to UPDATED_TIPO
        defaultCatTipoEmpresaShouldNotBeFound("tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllCatTipoEmpresasByTipoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catTipoEmpresaRepository.saveAndFlush(catTipoEmpresa);

        // Get all the catTipoEmpresaList where tipo not equals to DEFAULT_TIPO
        defaultCatTipoEmpresaShouldNotBeFound("tipo.notEquals=" + DEFAULT_TIPO);

        // Get all the catTipoEmpresaList where tipo not equals to UPDATED_TIPO
        defaultCatTipoEmpresaShouldBeFound("tipo.notEquals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllCatTipoEmpresasByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        catTipoEmpresaRepository.saveAndFlush(catTipoEmpresa);

        // Get all the catTipoEmpresaList where tipo in DEFAULT_TIPO or UPDATED_TIPO
        defaultCatTipoEmpresaShouldBeFound("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO);

        // Get all the catTipoEmpresaList where tipo equals to UPDATED_TIPO
        defaultCatTipoEmpresaShouldNotBeFound("tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllCatTipoEmpresasByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        catTipoEmpresaRepository.saveAndFlush(catTipoEmpresa);

        // Get all the catTipoEmpresaList where tipo is not null
        defaultCatTipoEmpresaShouldBeFound("tipo.specified=true");

        // Get all the catTipoEmpresaList where tipo is null
        defaultCatTipoEmpresaShouldNotBeFound("tipo.specified=false");
    }
                @Test
    @Transactional
    public void getAllCatTipoEmpresasByTipoContainsSomething() throws Exception {
        // Initialize the database
        catTipoEmpresaRepository.saveAndFlush(catTipoEmpresa);

        // Get all the catTipoEmpresaList where tipo contains DEFAULT_TIPO
        defaultCatTipoEmpresaShouldBeFound("tipo.contains=" + DEFAULT_TIPO);

        // Get all the catTipoEmpresaList where tipo contains UPDATED_TIPO
        defaultCatTipoEmpresaShouldNotBeFound("tipo.contains=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllCatTipoEmpresasByTipoNotContainsSomething() throws Exception {
        // Initialize the database
        catTipoEmpresaRepository.saveAndFlush(catTipoEmpresa);

        // Get all the catTipoEmpresaList where tipo does not contain DEFAULT_TIPO
        defaultCatTipoEmpresaShouldNotBeFound("tipo.doesNotContain=" + DEFAULT_TIPO);

        // Get all the catTipoEmpresaList where tipo does not contain UPDATED_TIPO
        defaultCatTipoEmpresaShouldBeFound("tipo.doesNotContain=" + UPDATED_TIPO);
    }


    @Test
    @Transactional
    public void getAllCatTipoEmpresasByUsuarioIsEqualToSomething() throws Exception {
        // Get already existing entity
        User usuario = catTipoEmpresa.getUsuario();
        catTipoEmpresaRepository.saveAndFlush(catTipoEmpresa);
        Long usuarioId = usuario.getId();

        // Get all the catTipoEmpresaList where usuario equals to usuarioId
        defaultCatTipoEmpresaShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the catTipoEmpresaList where usuario equals to usuarioId + 1
        defaultCatTipoEmpresaShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCatTipoEmpresaShouldBeFound(String filter) throws Exception {
        restCatTipoEmpresaMockMvc.perform(get("/api/cat-tipo-empresas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catTipoEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)));

        // Check, that the count call also returns 1
        restCatTipoEmpresaMockMvc.perform(get("/api/cat-tipo-empresas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCatTipoEmpresaShouldNotBeFound(String filter) throws Exception {
        restCatTipoEmpresaMockMvc.perform(get("/api/cat-tipo-empresas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCatTipoEmpresaMockMvc.perform(get("/api/cat-tipo-empresas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCatTipoEmpresa() throws Exception {
        // Get the catTipoEmpresa
        restCatTipoEmpresaMockMvc.perform(get("/api/cat-tipo-empresas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCatTipoEmpresa() throws Exception {
        // Initialize the database
        catTipoEmpresaService.save(catTipoEmpresa);

        int databaseSizeBeforeUpdate = catTipoEmpresaRepository.findAll().size();

        // Update the catTipoEmpresa
        CatTipoEmpresa updatedCatTipoEmpresa = catTipoEmpresaRepository.findById(catTipoEmpresa.getId()).get();
        // Disconnect from session so that the updates on updatedCatTipoEmpresa are not directly saved in db
        em.detach(updatedCatTipoEmpresa);
        updatedCatTipoEmpresa
            .tipo(UPDATED_TIPO);

        restCatTipoEmpresaMockMvc.perform(put("/api/cat-tipo-empresas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCatTipoEmpresa)))
            .andExpect(status().isOk());

        // Validate the CatTipoEmpresa in the database
        List<CatTipoEmpresa> catTipoEmpresaList = catTipoEmpresaRepository.findAll();
        assertThat(catTipoEmpresaList).hasSize(databaseSizeBeforeUpdate);
        CatTipoEmpresa testCatTipoEmpresa = catTipoEmpresaList.get(catTipoEmpresaList.size() - 1);
        assertThat(testCatTipoEmpresa.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void updateNonExistingCatTipoEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = catTipoEmpresaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatTipoEmpresaMockMvc.perform(put("/api/cat-tipo-empresas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catTipoEmpresa)))
            .andExpect(status().isBadRequest());

        // Validate the CatTipoEmpresa in the database
        List<CatTipoEmpresa> catTipoEmpresaList = catTipoEmpresaRepository.findAll();
        assertThat(catTipoEmpresaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCatTipoEmpresa() throws Exception {
        // Initialize the database
        catTipoEmpresaService.save(catTipoEmpresa);

        int databaseSizeBeforeDelete = catTipoEmpresaRepository.findAll().size();

        // Delete the catTipoEmpresa
        restCatTipoEmpresaMockMvc.perform(delete("/api/cat-tipo-empresas/{id}", catTipoEmpresa.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CatTipoEmpresa> catTipoEmpresaList = catTipoEmpresaRepository.findAll();
        assertThat(catTipoEmpresaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
