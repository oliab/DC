package com.resamx.web.rest;

import com.resamx.ResaMxWebApp;
import com.resamx.domain.CatTipoOperacion;
import com.resamx.domain.User;
import com.resamx.repository.CatTipoOperacionRepository;
import com.resamx.service.CatTipoOperacionService;
import com.resamx.service.dto.CatTipoOperacionCriteria;
import com.resamx.service.CatTipoOperacionQueryService;

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
 * Integration tests for the {@link CatTipoOperacionResource} REST controller.
 */
@SpringBootTest(classes = ResaMxWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CatTipoOperacionResourceIT {

    private static final String DEFAULT_OPERACION = "AAAAAAAAAA";
    private static final String UPDATED_OPERACION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private CatTipoOperacionRepository catTipoOperacionRepository;

    @Autowired
    private CatTipoOperacionService catTipoOperacionService;

    @Autowired
    private CatTipoOperacionQueryService catTipoOperacionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCatTipoOperacionMockMvc;

    private CatTipoOperacion catTipoOperacion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatTipoOperacion createEntity(EntityManager em) {
        CatTipoOperacion catTipoOperacion = new CatTipoOperacion()
            .operacion(DEFAULT_OPERACION)
            .descripcion(DEFAULT_DESCRIPCION);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catTipoOperacion.setUsuario(user);
        return catTipoOperacion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatTipoOperacion createUpdatedEntity(EntityManager em) {
        CatTipoOperacion catTipoOperacion = new CatTipoOperacion()
            .operacion(UPDATED_OPERACION)
            .descripcion(UPDATED_DESCRIPCION);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catTipoOperacion.setUsuario(user);
        return catTipoOperacion;
    }

    @BeforeEach
    public void initTest() {
        catTipoOperacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createCatTipoOperacion() throws Exception {
        int databaseSizeBeforeCreate = catTipoOperacionRepository.findAll().size();
        // Create the CatTipoOperacion
        restCatTipoOperacionMockMvc.perform(post("/api/cat-tipo-operacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catTipoOperacion)))
            .andExpect(status().isCreated());

        // Validate the CatTipoOperacion in the database
        List<CatTipoOperacion> catTipoOperacionList = catTipoOperacionRepository.findAll();
        assertThat(catTipoOperacionList).hasSize(databaseSizeBeforeCreate + 1);
        CatTipoOperacion testCatTipoOperacion = catTipoOperacionList.get(catTipoOperacionList.size() - 1);
        assertThat(testCatTipoOperacion.getOperacion()).isEqualTo(DEFAULT_OPERACION);
        assertThat(testCatTipoOperacion.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createCatTipoOperacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = catTipoOperacionRepository.findAll().size();

        // Create the CatTipoOperacion with an existing ID
        catTipoOperacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatTipoOperacionMockMvc.perform(post("/api/cat-tipo-operacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catTipoOperacion)))
            .andExpect(status().isBadRequest());

        // Validate the CatTipoOperacion in the database
        List<CatTipoOperacion> catTipoOperacionList = catTipoOperacionRepository.findAll();
        assertThat(catTipoOperacionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkOperacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = catTipoOperacionRepository.findAll().size();
        // set the field null
        catTipoOperacion.setOperacion(null);

        // Create the CatTipoOperacion, which fails.


        restCatTipoOperacionMockMvc.perform(post("/api/cat-tipo-operacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catTipoOperacion)))
            .andExpect(status().isBadRequest());

        List<CatTipoOperacion> catTipoOperacionList = catTipoOperacionRepository.findAll();
        assertThat(catTipoOperacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCatTipoOperacions() throws Exception {
        // Initialize the database
        catTipoOperacionRepository.saveAndFlush(catTipoOperacion);

        // Get all the catTipoOperacionList
        restCatTipoOperacionMockMvc.perform(get("/api/cat-tipo-operacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catTipoOperacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].operacion").value(hasItem(DEFAULT_OPERACION)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }
    
    @Test
    @Transactional
    public void getCatTipoOperacion() throws Exception {
        // Initialize the database
        catTipoOperacionRepository.saveAndFlush(catTipoOperacion);

        // Get the catTipoOperacion
        restCatTipoOperacionMockMvc.perform(get("/api/cat-tipo-operacions/{id}", catTipoOperacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(catTipoOperacion.getId().intValue()))
            .andExpect(jsonPath("$.operacion").value(DEFAULT_OPERACION))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }


    @Test
    @Transactional
    public void getCatTipoOperacionsByIdFiltering() throws Exception {
        // Initialize the database
        catTipoOperacionRepository.saveAndFlush(catTipoOperacion);

        Long id = catTipoOperacion.getId();

        defaultCatTipoOperacionShouldBeFound("id.equals=" + id);
        defaultCatTipoOperacionShouldNotBeFound("id.notEquals=" + id);

        defaultCatTipoOperacionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCatTipoOperacionShouldNotBeFound("id.greaterThan=" + id);

        defaultCatTipoOperacionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCatTipoOperacionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCatTipoOperacionsByOperacionIsEqualToSomething() throws Exception {
        // Initialize the database
        catTipoOperacionRepository.saveAndFlush(catTipoOperacion);

        // Get all the catTipoOperacionList where operacion equals to DEFAULT_OPERACION
        defaultCatTipoOperacionShouldBeFound("operacion.equals=" + DEFAULT_OPERACION);

        // Get all the catTipoOperacionList where operacion equals to UPDATED_OPERACION
        defaultCatTipoOperacionShouldNotBeFound("operacion.equals=" + UPDATED_OPERACION);
    }

    @Test
    @Transactional
    public void getAllCatTipoOperacionsByOperacionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catTipoOperacionRepository.saveAndFlush(catTipoOperacion);

        // Get all the catTipoOperacionList where operacion not equals to DEFAULT_OPERACION
        defaultCatTipoOperacionShouldNotBeFound("operacion.notEquals=" + DEFAULT_OPERACION);

        // Get all the catTipoOperacionList where operacion not equals to UPDATED_OPERACION
        defaultCatTipoOperacionShouldBeFound("operacion.notEquals=" + UPDATED_OPERACION);
    }

    @Test
    @Transactional
    public void getAllCatTipoOperacionsByOperacionIsInShouldWork() throws Exception {
        // Initialize the database
        catTipoOperacionRepository.saveAndFlush(catTipoOperacion);

        // Get all the catTipoOperacionList where operacion in DEFAULT_OPERACION or UPDATED_OPERACION
        defaultCatTipoOperacionShouldBeFound("operacion.in=" + DEFAULT_OPERACION + "," + UPDATED_OPERACION);

        // Get all the catTipoOperacionList where operacion equals to UPDATED_OPERACION
        defaultCatTipoOperacionShouldNotBeFound("operacion.in=" + UPDATED_OPERACION);
    }

    @Test
    @Transactional
    public void getAllCatTipoOperacionsByOperacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        catTipoOperacionRepository.saveAndFlush(catTipoOperacion);

        // Get all the catTipoOperacionList where operacion is not null
        defaultCatTipoOperacionShouldBeFound("operacion.specified=true");

        // Get all the catTipoOperacionList where operacion is null
        defaultCatTipoOperacionShouldNotBeFound("operacion.specified=false");
    }
                @Test
    @Transactional
    public void getAllCatTipoOperacionsByOperacionContainsSomething() throws Exception {
        // Initialize the database
        catTipoOperacionRepository.saveAndFlush(catTipoOperacion);

        // Get all the catTipoOperacionList where operacion contains DEFAULT_OPERACION
        defaultCatTipoOperacionShouldBeFound("operacion.contains=" + DEFAULT_OPERACION);

        // Get all the catTipoOperacionList where operacion contains UPDATED_OPERACION
        defaultCatTipoOperacionShouldNotBeFound("operacion.contains=" + UPDATED_OPERACION);
    }

    @Test
    @Transactional
    public void getAllCatTipoOperacionsByOperacionNotContainsSomething() throws Exception {
        // Initialize the database
        catTipoOperacionRepository.saveAndFlush(catTipoOperacion);

        // Get all the catTipoOperacionList where operacion does not contain DEFAULT_OPERACION
        defaultCatTipoOperacionShouldNotBeFound("operacion.doesNotContain=" + DEFAULT_OPERACION);

        // Get all the catTipoOperacionList where operacion does not contain UPDATED_OPERACION
        defaultCatTipoOperacionShouldBeFound("operacion.doesNotContain=" + UPDATED_OPERACION);
    }


    @Test
    @Transactional
    public void getAllCatTipoOperacionsByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        catTipoOperacionRepository.saveAndFlush(catTipoOperacion);

        // Get all the catTipoOperacionList where descripcion equals to DEFAULT_DESCRIPCION
        defaultCatTipoOperacionShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the catTipoOperacionList where descripcion equals to UPDATED_DESCRIPCION
        defaultCatTipoOperacionShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllCatTipoOperacionsByDescripcionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catTipoOperacionRepository.saveAndFlush(catTipoOperacion);

        // Get all the catTipoOperacionList where descripcion not equals to DEFAULT_DESCRIPCION
        defaultCatTipoOperacionShouldNotBeFound("descripcion.notEquals=" + DEFAULT_DESCRIPCION);

        // Get all the catTipoOperacionList where descripcion not equals to UPDATED_DESCRIPCION
        defaultCatTipoOperacionShouldBeFound("descripcion.notEquals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllCatTipoOperacionsByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        catTipoOperacionRepository.saveAndFlush(catTipoOperacion);

        // Get all the catTipoOperacionList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultCatTipoOperacionShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the catTipoOperacionList where descripcion equals to UPDATED_DESCRIPCION
        defaultCatTipoOperacionShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllCatTipoOperacionsByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        catTipoOperacionRepository.saveAndFlush(catTipoOperacion);

        // Get all the catTipoOperacionList where descripcion is not null
        defaultCatTipoOperacionShouldBeFound("descripcion.specified=true");

        // Get all the catTipoOperacionList where descripcion is null
        defaultCatTipoOperacionShouldNotBeFound("descripcion.specified=false");
    }
                @Test
    @Transactional
    public void getAllCatTipoOperacionsByDescripcionContainsSomething() throws Exception {
        // Initialize the database
        catTipoOperacionRepository.saveAndFlush(catTipoOperacion);

        // Get all the catTipoOperacionList where descripcion contains DEFAULT_DESCRIPCION
        defaultCatTipoOperacionShouldBeFound("descripcion.contains=" + DEFAULT_DESCRIPCION);

        // Get all the catTipoOperacionList where descripcion contains UPDATED_DESCRIPCION
        defaultCatTipoOperacionShouldNotBeFound("descripcion.contains=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllCatTipoOperacionsByDescripcionNotContainsSomething() throws Exception {
        // Initialize the database
        catTipoOperacionRepository.saveAndFlush(catTipoOperacion);

        // Get all the catTipoOperacionList where descripcion does not contain DEFAULT_DESCRIPCION
        defaultCatTipoOperacionShouldNotBeFound("descripcion.doesNotContain=" + DEFAULT_DESCRIPCION);

        // Get all the catTipoOperacionList where descripcion does not contain UPDATED_DESCRIPCION
        defaultCatTipoOperacionShouldBeFound("descripcion.doesNotContain=" + UPDATED_DESCRIPCION);
    }


    @Test
    @Transactional
    public void getAllCatTipoOperacionsByUsuarioIsEqualToSomething() throws Exception {
        // Get already existing entity
        User usuario = catTipoOperacion.getUsuario();
        catTipoOperacionRepository.saveAndFlush(catTipoOperacion);
        Long usuarioId = usuario.getId();

        // Get all the catTipoOperacionList where usuario equals to usuarioId
        defaultCatTipoOperacionShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the catTipoOperacionList where usuario equals to usuarioId + 1
        defaultCatTipoOperacionShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCatTipoOperacionShouldBeFound(String filter) throws Exception {
        restCatTipoOperacionMockMvc.perform(get("/api/cat-tipo-operacions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catTipoOperacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].operacion").value(hasItem(DEFAULT_OPERACION)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));

        // Check, that the count call also returns 1
        restCatTipoOperacionMockMvc.perform(get("/api/cat-tipo-operacions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCatTipoOperacionShouldNotBeFound(String filter) throws Exception {
        restCatTipoOperacionMockMvc.perform(get("/api/cat-tipo-operacions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCatTipoOperacionMockMvc.perform(get("/api/cat-tipo-operacions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCatTipoOperacion() throws Exception {
        // Get the catTipoOperacion
        restCatTipoOperacionMockMvc.perform(get("/api/cat-tipo-operacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCatTipoOperacion() throws Exception {
        // Initialize the database
        catTipoOperacionService.save(catTipoOperacion);

        int databaseSizeBeforeUpdate = catTipoOperacionRepository.findAll().size();

        // Update the catTipoOperacion
        CatTipoOperacion updatedCatTipoOperacion = catTipoOperacionRepository.findById(catTipoOperacion.getId()).get();
        // Disconnect from session so that the updates on updatedCatTipoOperacion are not directly saved in db
        em.detach(updatedCatTipoOperacion);
        updatedCatTipoOperacion
            .operacion(UPDATED_OPERACION)
            .descripcion(UPDATED_DESCRIPCION);

        restCatTipoOperacionMockMvc.perform(put("/api/cat-tipo-operacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCatTipoOperacion)))
            .andExpect(status().isOk());

        // Validate the CatTipoOperacion in the database
        List<CatTipoOperacion> catTipoOperacionList = catTipoOperacionRepository.findAll();
        assertThat(catTipoOperacionList).hasSize(databaseSizeBeforeUpdate);
        CatTipoOperacion testCatTipoOperacion = catTipoOperacionList.get(catTipoOperacionList.size() - 1);
        assertThat(testCatTipoOperacion.getOperacion()).isEqualTo(UPDATED_OPERACION);
        assertThat(testCatTipoOperacion.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingCatTipoOperacion() throws Exception {
        int databaseSizeBeforeUpdate = catTipoOperacionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatTipoOperacionMockMvc.perform(put("/api/cat-tipo-operacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catTipoOperacion)))
            .andExpect(status().isBadRequest());

        // Validate the CatTipoOperacion in the database
        List<CatTipoOperacion> catTipoOperacionList = catTipoOperacionRepository.findAll();
        assertThat(catTipoOperacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCatTipoOperacion() throws Exception {
        // Initialize the database
        catTipoOperacionService.save(catTipoOperacion);

        int databaseSizeBeforeDelete = catTipoOperacionRepository.findAll().size();

        // Delete the catTipoOperacion
        restCatTipoOperacionMockMvc.perform(delete("/api/cat-tipo-operacions/{id}", catTipoOperacion.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CatTipoOperacion> catTipoOperacionList = catTipoOperacionRepository.findAll();
        assertThat(catTipoOperacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
