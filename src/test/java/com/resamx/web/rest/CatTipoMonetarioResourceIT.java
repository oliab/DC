package com.resamx.web.rest;

import com.resamx.ResaMxWebApp;
import com.resamx.domain.CatTipoMonetario;
import com.resamx.domain.User;
import com.resamx.repository.CatTipoMonetarioRepository;
import com.resamx.service.CatTipoMonetarioService;
import com.resamx.service.dto.CatTipoMonetarioCriteria;
import com.resamx.service.CatTipoMonetarioQueryService;

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
 * Integration tests for the {@link CatTipoMonetarioResource} REST controller.
 */
@SpringBootTest(classes = ResaMxWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CatTipoMonetarioResourceIT {

    private static final String DEFAULT_INSTRUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private CatTipoMonetarioRepository catTipoMonetarioRepository;

    @Autowired
    private CatTipoMonetarioService catTipoMonetarioService;

    @Autowired
    private CatTipoMonetarioQueryService catTipoMonetarioQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCatTipoMonetarioMockMvc;

    private CatTipoMonetario catTipoMonetario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatTipoMonetario createEntity(EntityManager em) {
        CatTipoMonetario catTipoMonetario = new CatTipoMonetario()
            .instrumento(DEFAULT_INSTRUMENTO)
            .descripcion(DEFAULT_DESCRIPCION);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catTipoMonetario.setUsuario(user);
        return catTipoMonetario;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatTipoMonetario createUpdatedEntity(EntityManager em) {
        CatTipoMonetario catTipoMonetario = new CatTipoMonetario()
            .instrumento(UPDATED_INSTRUMENTO)
            .descripcion(UPDATED_DESCRIPCION);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catTipoMonetario.setUsuario(user);
        return catTipoMonetario;
    }

    @BeforeEach
    public void initTest() {
        catTipoMonetario = createEntity(em);
    }

    @Test
    @Transactional
    public void createCatTipoMonetario() throws Exception {
        int databaseSizeBeforeCreate = catTipoMonetarioRepository.findAll().size();
        // Create the CatTipoMonetario
        restCatTipoMonetarioMockMvc.perform(post("/api/cat-tipo-monetarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catTipoMonetario)))
            .andExpect(status().isCreated());

        // Validate the CatTipoMonetario in the database
        List<CatTipoMonetario> catTipoMonetarioList = catTipoMonetarioRepository.findAll();
        assertThat(catTipoMonetarioList).hasSize(databaseSizeBeforeCreate + 1);
        CatTipoMonetario testCatTipoMonetario = catTipoMonetarioList.get(catTipoMonetarioList.size() - 1);
        assertThat(testCatTipoMonetario.getInstrumento()).isEqualTo(DEFAULT_INSTRUMENTO);
        assertThat(testCatTipoMonetario.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createCatTipoMonetarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = catTipoMonetarioRepository.findAll().size();

        // Create the CatTipoMonetario with an existing ID
        catTipoMonetario.setId("1");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatTipoMonetarioMockMvc.perform(post("/api/cat-tipo-monetarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catTipoMonetario)))
            .andExpect(status().isBadRequest());

        // Validate the CatTipoMonetario in the database
        List<CatTipoMonetario> catTipoMonetarioList = catTipoMonetarioRepository.findAll();
        assertThat(catTipoMonetarioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkInstrumentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = catTipoMonetarioRepository.findAll().size();
        // set the field null
        catTipoMonetario.setInstrumento(null);

        // Create the CatTipoMonetario, which fails.


        restCatTipoMonetarioMockMvc.perform(post("/api/cat-tipo-monetarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catTipoMonetario)))
            .andExpect(status().isBadRequest());

        List<CatTipoMonetario> catTipoMonetarioList = catTipoMonetarioRepository.findAll();
        assertThat(catTipoMonetarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCatTipoMonetarios() throws Exception {
        // Initialize the database
        catTipoMonetarioRepository.saveAndFlush(catTipoMonetario);

        // Get all the catTipoMonetarioList
        restCatTipoMonetarioMockMvc.perform(get("/api/cat-tipo-monetarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catTipoMonetario.getId())))
            .andExpect(jsonPath("$.[*].instrumento").value(hasItem(DEFAULT_INSTRUMENTO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }
    
    @Test
    @Transactional
    public void getCatTipoMonetario() throws Exception {
        // Initialize the database
        catTipoMonetarioRepository.saveAndFlush(catTipoMonetario);

        // Get the catTipoMonetario
        restCatTipoMonetarioMockMvc.perform(get("/api/cat-tipo-monetarios/{id}", catTipoMonetario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(catTipoMonetario.getId()))
            .andExpect(jsonPath("$.instrumento").value(DEFAULT_INSTRUMENTO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }


    @Test
    @Transactional
    public void getCatTipoMonetariosByIdFiltering() throws Exception {
        // Initialize the database
        catTipoMonetarioRepository.saveAndFlush(catTipoMonetario);

        String id = catTipoMonetario.getId();

        defaultCatTipoMonetarioShouldBeFound("id.equals=" + id);
        defaultCatTipoMonetarioShouldNotBeFound("id.notEquals=" + id);

        defaultCatTipoMonetarioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCatTipoMonetarioShouldNotBeFound("id.greaterThan=" + id);

        defaultCatTipoMonetarioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCatTipoMonetarioShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCatTipoMonetariosByInstrumentoIsEqualToSomething() throws Exception {
        // Initialize the database
        catTipoMonetarioRepository.saveAndFlush(catTipoMonetario);

        // Get all the catTipoMonetarioList where instrumento equals to DEFAULT_INSTRUMENTO
        defaultCatTipoMonetarioShouldBeFound("instrumento.equals=" + DEFAULT_INSTRUMENTO);

        // Get all the catTipoMonetarioList where instrumento equals to UPDATED_INSTRUMENTO
        defaultCatTipoMonetarioShouldNotBeFound("instrumento.equals=" + UPDATED_INSTRUMENTO);
    }

    @Test
    @Transactional
    public void getAllCatTipoMonetariosByInstrumentoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catTipoMonetarioRepository.saveAndFlush(catTipoMonetario);

        // Get all the catTipoMonetarioList where instrumento not equals to DEFAULT_INSTRUMENTO
        defaultCatTipoMonetarioShouldNotBeFound("instrumento.notEquals=" + DEFAULT_INSTRUMENTO);

        // Get all the catTipoMonetarioList where instrumento not equals to UPDATED_INSTRUMENTO
        defaultCatTipoMonetarioShouldBeFound("instrumento.notEquals=" + UPDATED_INSTRUMENTO);
    }

    @Test
    @Transactional
    public void getAllCatTipoMonetariosByInstrumentoIsInShouldWork() throws Exception {
        // Initialize the database
        catTipoMonetarioRepository.saveAndFlush(catTipoMonetario);

        // Get all the catTipoMonetarioList where instrumento in DEFAULT_INSTRUMENTO or UPDATED_INSTRUMENTO
        defaultCatTipoMonetarioShouldBeFound("instrumento.in=" + DEFAULT_INSTRUMENTO + "," + UPDATED_INSTRUMENTO);

        // Get all the catTipoMonetarioList where instrumento equals to UPDATED_INSTRUMENTO
        defaultCatTipoMonetarioShouldNotBeFound("instrumento.in=" + UPDATED_INSTRUMENTO);
    }

    @Test
    @Transactional
    public void getAllCatTipoMonetariosByInstrumentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        catTipoMonetarioRepository.saveAndFlush(catTipoMonetario);

        // Get all the catTipoMonetarioList where instrumento is not null
        defaultCatTipoMonetarioShouldBeFound("instrumento.specified=true");

        // Get all the catTipoMonetarioList where instrumento is null
        defaultCatTipoMonetarioShouldNotBeFound("instrumento.specified=false");
    }
                @Test
    @Transactional
    public void getAllCatTipoMonetariosByInstrumentoContainsSomething() throws Exception {
        // Initialize the database
        catTipoMonetarioRepository.saveAndFlush(catTipoMonetario);

        // Get all the catTipoMonetarioList where instrumento contains DEFAULT_INSTRUMENTO
        defaultCatTipoMonetarioShouldBeFound("instrumento.contains=" + DEFAULT_INSTRUMENTO);

        // Get all the catTipoMonetarioList where instrumento contains UPDATED_INSTRUMENTO
        defaultCatTipoMonetarioShouldNotBeFound("instrumento.contains=" + UPDATED_INSTRUMENTO);
    }

    @Test
    @Transactional
    public void getAllCatTipoMonetariosByInstrumentoNotContainsSomething() throws Exception {
        // Initialize the database
        catTipoMonetarioRepository.saveAndFlush(catTipoMonetario);

        // Get all the catTipoMonetarioList where instrumento does not contain DEFAULT_INSTRUMENTO
        defaultCatTipoMonetarioShouldNotBeFound("instrumento.doesNotContain=" + DEFAULT_INSTRUMENTO);

        // Get all the catTipoMonetarioList where instrumento does not contain UPDATED_INSTRUMENTO
        defaultCatTipoMonetarioShouldBeFound("instrumento.doesNotContain=" + UPDATED_INSTRUMENTO);
    }


    @Test
    @Transactional
    public void getAllCatTipoMonetariosByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        catTipoMonetarioRepository.saveAndFlush(catTipoMonetario);

        // Get all the catTipoMonetarioList where descripcion equals to DEFAULT_DESCRIPCION
        defaultCatTipoMonetarioShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the catTipoMonetarioList where descripcion equals to UPDATED_DESCRIPCION
        defaultCatTipoMonetarioShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllCatTipoMonetariosByDescripcionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catTipoMonetarioRepository.saveAndFlush(catTipoMonetario);

        // Get all the catTipoMonetarioList where descripcion not equals to DEFAULT_DESCRIPCION
        defaultCatTipoMonetarioShouldNotBeFound("descripcion.notEquals=" + DEFAULT_DESCRIPCION);

        // Get all the catTipoMonetarioList where descripcion not equals to UPDATED_DESCRIPCION
        defaultCatTipoMonetarioShouldBeFound("descripcion.notEquals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllCatTipoMonetariosByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        catTipoMonetarioRepository.saveAndFlush(catTipoMonetario);

        // Get all the catTipoMonetarioList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultCatTipoMonetarioShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the catTipoMonetarioList where descripcion equals to UPDATED_DESCRIPCION
        defaultCatTipoMonetarioShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllCatTipoMonetariosByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        catTipoMonetarioRepository.saveAndFlush(catTipoMonetario);

        // Get all the catTipoMonetarioList where descripcion is not null
        defaultCatTipoMonetarioShouldBeFound("descripcion.specified=true");

        // Get all the catTipoMonetarioList where descripcion is null
        defaultCatTipoMonetarioShouldNotBeFound("descripcion.specified=false");
    }
                @Test
    @Transactional
    public void getAllCatTipoMonetariosByDescripcionContainsSomething() throws Exception {
        // Initialize the database
        catTipoMonetarioRepository.saveAndFlush(catTipoMonetario);

        // Get all the catTipoMonetarioList where descripcion contains DEFAULT_DESCRIPCION
        defaultCatTipoMonetarioShouldBeFound("descripcion.contains=" + DEFAULT_DESCRIPCION);

        // Get all the catTipoMonetarioList where descripcion contains UPDATED_DESCRIPCION
        defaultCatTipoMonetarioShouldNotBeFound("descripcion.contains=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllCatTipoMonetariosByDescripcionNotContainsSomething() throws Exception {
        // Initialize the database
        catTipoMonetarioRepository.saveAndFlush(catTipoMonetario);

        // Get all the catTipoMonetarioList where descripcion does not contain DEFAULT_DESCRIPCION
        defaultCatTipoMonetarioShouldNotBeFound("descripcion.doesNotContain=" + DEFAULT_DESCRIPCION);

        // Get all the catTipoMonetarioList where descripcion does not contain UPDATED_DESCRIPCION
        defaultCatTipoMonetarioShouldBeFound("descripcion.doesNotContain=" + UPDATED_DESCRIPCION);
    }


    @Test
    @Transactional
    public void getAllCatTipoMonetariosByUsuarioIsEqualToSomething() throws Exception {
        // Get already existing entity
        User usuario = catTipoMonetario.getUsuario();
        catTipoMonetarioRepository.saveAndFlush(catTipoMonetario);
        Long usuarioId = usuario.getId();

        // Get all the catTipoMonetarioList where usuario equals to usuarioId
        defaultCatTipoMonetarioShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the catTipoMonetarioList where usuario equals to usuarioId + 1
        defaultCatTipoMonetarioShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCatTipoMonetarioShouldBeFound(String filter) throws Exception {
        restCatTipoMonetarioMockMvc.perform(get("/api/cat-tipo-monetarios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catTipoMonetario.getId())))
            .andExpect(jsonPath("$.[*].instrumento").value(hasItem(DEFAULT_INSTRUMENTO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));

        // Check, that the count call also returns 1
        restCatTipoMonetarioMockMvc.perform(get("/api/cat-tipo-monetarios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCatTipoMonetarioShouldNotBeFound(String filter) throws Exception {
        restCatTipoMonetarioMockMvc.perform(get("/api/cat-tipo-monetarios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCatTipoMonetarioMockMvc.perform(get("/api/cat-tipo-monetarios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCatTipoMonetario() throws Exception {
        // Get the catTipoMonetario
        restCatTipoMonetarioMockMvc.perform(get("/api/cat-tipo-monetarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCatTipoMonetario() throws Exception {
        // Initialize the database
        catTipoMonetarioService.save(catTipoMonetario);

        int databaseSizeBeforeUpdate = catTipoMonetarioRepository.findAll().size();

        // Update the catTipoMonetario
        CatTipoMonetario updatedCatTipoMonetario = catTipoMonetarioRepository.findById(catTipoMonetario.getId()).get();
        // Disconnect from session so that the updates on updatedCatTipoMonetario are not directly saved in db
        em.detach(updatedCatTipoMonetario);
        updatedCatTipoMonetario
            .instrumento(UPDATED_INSTRUMENTO)
            .descripcion(UPDATED_DESCRIPCION);

        restCatTipoMonetarioMockMvc.perform(put("/api/cat-tipo-monetarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCatTipoMonetario)))
            .andExpect(status().isOk());

        // Validate the CatTipoMonetario in the database
        List<CatTipoMonetario> catTipoMonetarioList = catTipoMonetarioRepository.findAll();
        assertThat(catTipoMonetarioList).hasSize(databaseSizeBeforeUpdate);
        CatTipoMonetario testCatTipoMonetario = catTipoMonetarioList.get(catTipoMonetarioList.size() - 1);
        assertThat(testCatTipoMonetario.getInstrumento()).isEqualTo(UPDATED_INSTRUMENTO);
        assertThat(testCatTipoMonetario.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingCatTipoMonetario() throws Exception {
        int databaseSizeBeforeUpdate = catTipoMonetarioRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatTipoMonetarioMockMvc.perform(put("/api/cat-tipo-monetarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catTipoMonetario)))
            .andExpect(status().isBadRequest());

        // Validate the CatTipoMonetario in the database
        List<CatTipoMonetario> catTipoMonetarioList = catTipoMonetarioRepository.findAll();
        assertThat(catTipoMonetarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCatTipoMonetario() throws Exception {
        // Initialize the database
        catTipoMonetarioService.save(catTipoMonetario);

        int databaseSizeBeforeDelete = catTipoMonetarioRepository.findAll().size();

        // Delete the catTipoMonetario
        restCatTipoMonetarioMockMvc.perform(delete("/api/cat-tipo-monetarios/{id}", catTipoMonetario.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CatTipoMonetario> catTipoMonetarioList = catTipoMonetarioRepository.findAll();
        assertThat(catTipoMonetarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
