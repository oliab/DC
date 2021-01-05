package com.resamx.web.rest;

import com.resamx.ResaMxWebApp;
import com.resamx.domain.CatTipoDocumento;
import com.resamx.domain.User;
import com.resamx.repository.CatTipoDocumentoRepository;
import com.resamx.service.CatTipoDocumentoService;
import com.resamx.service.dto.CatTipoDocumentoCriteria;
import com.resamx.service.CatTipoDocumentoQueryService;

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
 * Integration tests for the {@link CatTipoDocumentoResource} REST controller.
 */
@SpringBootTest(classes = ResaMxWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CatTipoDocumentoResourceIT {

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    @Autowired
    private CatTipoDocumentoRepository catTipoDocumentoRepository;

    @Autowired
    private CatTipoDocumentoService catTipoDocumentoService;

    @Autowired
    private CatTipoDocumentoQueryService catTipoDocumentoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCatTipoDocumentoMockMvc;

    private CatTipoDocumento catTipoDocumento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatTipoDocumento createEntity(EntityManager em) {
        CatTipoDocumento catTipoDocumento = new CatTipoDocumento()
            .tipo(DEFAULT_TIPO);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catTipoDocumento.setUsuario(user);
        return catTipoDocumento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatTipoDocumento createUpdatedEntity(EntityManager em) {
        CatTipoDocumento catTipoDocumento = new CatTipoDocumento()
            .tipo(UPDATED_TIPO);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catTipoDocumento.setUsuario(user);
        return catTipoDocumento;
    }

    @BeforeEach
    public void initTest() {
        catTipoDocumento = createEntity(em);
    }

    @Test
    @Transactional
    public void createCatTipoDocumento() throws Exception {
        int databaseSizeBeforeCreate = catTipoDocumentoRepository.findAll().size();
        // Create the CatTipoDocumento
        restCatTipoDocumentoMockMvc.perform(post("/api/cat-tipo-documentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catTipoDocumento)))
            .andExpect(status().isCreated());

        // Validate the CatTipoDocumento in the database
        List<CatTipoDocumento> catTipoDocumentoList = catTipoDocumentoRepository.findAll();
        assertThat(catTipoDocumentoList).hasSize(databaseSizeBeforeCreate + 1);
        CatTipoDocumento testCatTipoDocumento = catTipoDocumentoList.get(catTipoDocumentoList.size() - 1);
        assertThat(testCatTipoDocumento.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    public void createCatTipoDocumentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = catTipoDocumentoRepository.findAll().size();

        // Create the CatTipoDocumento with an existing ID
        catTipoDocumento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatTipoDocumentoMockMvc.perform(post("/api/cat-tipo-documentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catTipoDocumento)))
            .andExpect(status().isBadRequest());

        // Validate the CatTipoDocumento in the database
        List<CatTipoDocumento> catTipoDocumentoList = catTipoDocumentoRepository.findAll();
        assertThat(catTipoDocumentoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = catTipoDocumentoRepository.findAll().size();
        // set the field null
        catTipoDocumento.setTipo(null);

        // Create the CatTipoDocumento, which fails.


        restCatTipoDocumentoMockMvc.perform(post("/api/cat-tipo-documentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catTipoDocumento)))
            .andExpect(status().isBadRequest());

        List<CatTipoDocumento> catTipoDocumentoList = catTipoDocumentoRepository.findAll();
        assertThat(catTipoDocumentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCatTipoDocumentos() throws Exception {
        // Initialize the database
        catTipoDocumentoRepository.saveAndFlush(catTipoDocumento);

        // Get all the catTipoDocumentoList
        restCatTipoDocumentoMockMvc.perform(get("/api/cat-tipo-documentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catTipoDocumento.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)));
    }
    
    @Test
    @Transactional
    public void getCatTipoDocumento() throws Exception {
        // Initialize the database
        catTipoDocumentoRepository.saveAndFlush(catTipoDocumento);

        // Get the catTipoDocumento
        restCatTipoDocumentoMockMvc.perform(get("/api/cat-tipo-documentos/{id}", catTipoDocumento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(catTipoDocumento.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO));
    }


    @Test
    @Transactional
    public void getCatTipoDocumentosByIdFiltering() throws Exception {
        // Initialize the database
        catTipoDocumentoRepository.saveAndFlush(catTipoDocumento);

        Long id = catTipoDocumento.getId();

        defaultCatTipoDocumentoShouldBeFound("id.equals=" + id);
        defaultCatTipoDocumentoShouldNotBeFound("id.notEquals=" + id);

        defaultCatTipoDocumentoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCatTipoDocumentoShouldNotBeFound("id.greaterThan=" + id);

        defaultCatTipoDocumentoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCatTipoDocumentoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCatTipoDocumentosByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        catTipoDocumentoRepository.saveAndFlush(catTipoDocumento);

        // Get all the catTipoDocumentoList where tipo equals to DEFAULT_TIPO
        defaultCatTipoDocumentoShouldBeFound("tipo.equals=" + DEFAULT_TIPO);

        // Get all the catTipoDocumentoList where tipo equals to UPDATED_TIPO
        defaultCatTipoDocumentoShouldNotBeFound("tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllCatTipoDocumentosByTipoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catTipoDocumentoRepository.saveAndFlush(catTipoDocumento);

        // Get all the catTipoDocumentoList where tipo not equals to DEFAULT_TIPO
        defaultCatTipoDocumentoShouldNotBeFound("tipo.notEquals=" + DEFAULT_TIPO);

        // Get all the catTipoDocumentoList where tipo not equals to UPDATED_TIPO
        defaultCatTipoDocumentoShouldBeFound("tipo.notEquals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllCatTipoDocumentosByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        catTipoDocumentoRepository.saveAndFlush(catTipoDocumento);

        // Get all the catTipoDocumentoList where tipo in DEFAULT_TIPO or UPDATED_TIPO
        defaultCatTipoDocumentoShouldBeFound("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO);

        // Get all the catTipoDocumentoList where tipo equals to UPDATED_TIPO
        defaultCatTipoDocumentoShouldNotBeFound("tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllCatTipoDocumentosByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        catTipoDocumentoRepository.saveAndFlush(catTipoDocumento);

        // Get all the catTipoDocumentoList where tipo is not null
        defaultCatTipoDocumentoShouldBeFound("tipo.specified=true");

        // Get all the catTipoDocumentoList where tipo is null
        defaultCatTipoDocumentoShouldNotBeFound("tipo.specified=false");
    }
                @Test
    @Transactional
    public void getAllCatTipoDocumentosByTipoContainsSomething() throws Exception {
        // Initialize the database
        catTipoDocumentoRepository.saveAndFlush(catTipoDocumento);

        // Get all the catTipoDocumentoList where tipo contains DEFAULT_TIPO
        defaultCatTipoDocumentoShouldBeFound("tipo.contains=" + DEFAULT_TIPO);

        // Get all the catTipoDocumentoList where tipo contains UPDATED_TIPO
        defaultCatTipoDocumentoShouldNotBeFound("tipo.contains=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllCatTipoDocumentosByTipoNotContainsSomething() throws Exception {
        // Initialize the database
        catTipoDocumentoRepository.saveAndFlush(catTipoDocumento);

        // Get all the catTipoDocumentoList where tipo does not contain DEFAULT_TIPO
        defaultCatTipoDocumentoShouldNotBeFound("tipo.doesNotContain=" + DEFAULT_TIPO);

        // Get all the catTipoDocumentoList where tipo does not contain UPDATED_TIPO
        defaultCatTipoDocumentoShouldBeFound("tipo.doesNotContain=" + UPDATED_TIPO);
    }


    @Test
    @Transactional
    public void getAllCatTipoDocumentosByUsuarioIsEqualToSomething() throws Exception {
        // Get already existing entity
        User usuario = catTipoDocumento.getUsuario();
        catTipoDocumentoRepository.saveAndFlush(catTipoDocumento);
        Long usuarioId = usuario.getId();

        // Get all the catTipoDocumentoList where usuario equals to usuarioId
        defaultCatTipoDocumentoShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the catTipoDocumentoList where usuario equals to usuarioId + 1
        defaultCatTipoDocumentoShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCatTipoDocumentoShouldBeFound(String filter) throws Exception {
        restCatTipoDocumentoMockMvc.perform(get("/api/cat-tipo-documentos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catTipoDocumento.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)));

        // Check, that the count call also returns 1
        restCatTipoDocumentoMockMvc.perform(get("/api/cat-tipo-documentos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCatTipoDocumentoShouldNotBeFound(String filter) throws Exception {
        restCatTipoDocumentoMockMvc.perform(get("/api/cat-tipo-documentos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCatTipoDocumentoMockMvc.perform(get("/api/cat-tipo-documentos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCatTipoDocumento() throws Exception {
        // Get the catTipoDocumento
        restCatTipoDocumentoMockMvc.perform(get("/api/cat-tipo-documentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCatTipoDocumento() throws Exception {
        // Initialize the database
        catTipoDocumentoService.save(catTipoDocumento);

        int databaseSizeBeforeUpdate = catTipoDocumentoRepository.findAll().size();

        // Update the catTipoDocumento
        CatTipoDocumento updatedCatTipoDocumento = catTipoDocumentoRepository.findById(catTipoDocumento.getId()).get();
        // Disconnect from session so that the updates on updatedCatTipoDocumento are not directly saved in db
        em.detach(updatedCatTipoDocumento);
        updatedCatTipoDocumento
            .tipo(UPDATED_TIPO);

        restCatTipoDocumentoMockMvc.perform(put("/api/cat-tipo-documentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCatTipoDocumento)))
            .andExpect(status().isOk());

        // Validate the CatTipoDocumento in the database
        List<CatTipoDocumento> catTipoDocumentoList = catTipoDocumentoRepository.findAll();
        assertThat(catTipoDocumentoList).hasSize(databaseSizeBeforeUpdate);
        CatTipoDocumento testCatTipoDocumento = catTipoDocumentoList.get(catTipoDocumentoList.size() - 1);
        assertThat(testCatTipoDocumento.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void updateNonExistingCatTipoDocumento() throws Exception {
        int databaseSizeBeforeUpdate = catTipoDocumentoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatTipoDocumentoMockMvc.perform(put("/api/cat-tipo-documentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catTipoDocumento)))
            .andExpect(status().isBadRequest());

        // Validate the CatTipoDocumento in the database
        List<CatTipoDocumento> catTipoDocumentoList = catTipoDocumentoRepository.findAll();
        assertThat(catTipoDocumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCatTipoDocumento() throws Exception {
        // Initialize the database
        catTipoDocumentoService.save(catTipoDocumento);

        int databaseSizeBeforeDelete = catTipoDocumentoRepository.findAll().size();

        // Delete the catTipoDocumento
        restCatTipoDocumentoMockMvc.perform(delete("/api/cat-tipo-documentos/{id}", catTipoDocumento.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CatTipoDocumento> catTipoDocumentoList = catTipoDocumentoRepository.findAll();
        assertThat(catTipoDocumentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
