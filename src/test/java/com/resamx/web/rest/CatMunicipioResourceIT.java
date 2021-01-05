package com.resamx.web.rest;

import com.resamx.ResaMxWebApp;
import com.resamx.domain.CatMunicipio;
import com.resamx.domain.User;
import com.resamx.domain.CatEstado;
import com.resamx.repository.CatMunicipioRepository;
import com.resamx.service.CatMunicipioService;
import com.resamx.service.dto.CatMunicipioCriteria;
import com.resamx.service.CatMunicipioQueryService;

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
 * Integration tests for the {@link CatMunicipioResource} REST controller.
 */
@SpringBootTest(classes = ResaMxWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CatMunicipioResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_CLAVE = "AAA";
    private static final String UPDATED_CLAVE = "BBB";

    @Autowired
    private CatMunicipioRepository catMunicipioRepository;

    @Autowired
    private CatMunicipioService catMunicipioService;

    @Autowired
    private CatMunicipioQueryService catMunicipioQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCatMunicipioMockMvc;

    private CatMunicipio catMunicipio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatMunicipio createEntity(EntityManager em) {
        CatMunicipio catMunicipio = new CatMunicipio()
            .nombre(DEFAULT_NOMBRE)
            .clave(DEFAULT_CLAVE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catMunicipio.setUsuario(user);
        // Add required entity
        CatEstado catEstado;
        if (TestUtil.findAll(em, CatEstado.class).isEmpty()) {
            catEstado = CatEstadoResourceIT.createEntity(em);
            em.persist(catEstado);
            em.flush();
        } else {
            catEstado = TestUtil.findAll(em, CatEstado.class).get(0);
        }
        catMunicipio.setEstado(catEstado);
        return catMunicipio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatMunicipio createUpdatedEntity(EntityManager em) {
        CatMunicipio catMunicipio = new CatMunicipio()
            .nombre(UPDATED_NOMBRE)
            .clave(UPDATED_CLAVE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catMunicipio.setUsuario(user);
        // Add required entity
        CatEstado catEstado;
        if (TestUtil.findAll(em, CatEstado.class).isEmpty()) {
            catEstado = CatEstadoResourceIT.createUpdatedEntity(em);
            em.persist(catEstado);
            em.flush();
        } else {
            catEstado = TestUtil.findAll(em, CatEstado.class).get(0);
        }
        catMunicipio.setEstado(catEstado);
        return catMunicipio;
    }

    @BeforeEach
    public void initTest() {
        catMunicipio = createEntity(em);
    }

    @Test
    @Transactional
    public void createCatMunicipio() throws Exception {
        int databaseSizeBeforeCreate = catMunicipioRepository.findAll().size();
        // Create the CatMunicipio
        restCatMunicipioMockMvc.perform(post("/api/cat-municipios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catMunicipio)))
            .andExpect(status().isCreated());

        // Validate the CatMunicipio in the database
        List<CatMunicipio> catMunicipioList = catMunicipioRepository.findAll();
        assertThat(catMunicipioList).hasSize(databaseSizeBeforeCreate + 1);
        CatMunicipio testCatMunicipio = catMunicipioList.get(catMunicipioList.size() - 1);
        assertThat(testCatMunicipio.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCatMunicipio.getClave()).isEqualTo(DEFAULT_CLAVE);
    }

    @Test
    @Transactional
    public void createCatMunicipioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = catMunicipioRepository.findAll().size();

        // Create the CatMunicipio with an existing ID
        catMunicipio.setId("1");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatMunicipioMockMvc.perform(post("/api/cat-municipios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catMunicipio)))
            .andExpect(status().isBadRequest());

        // Validate the CatMunicipio in the database
        List<CatMunicipio> catMunicipioList = catMunicipioRepository.findAll();
        assertThat(catMunicipioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = catMunicipioRepository.findAll().size();
        // set the field null
        catMunicipio.setNombre(null);

        // Create the CatMunicipio, which fails.


        restCatMunicipioMockMvc.perform(post("/api/cat-municipios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catMunicipio)))
            .andExpect(status().isBadRequest());

        List<CatMunicipio> catMunicipioList = catMunicipioRepository.findAll();
        assertThat(catMunicipioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClaveIsRequired() throws Exception {
        int databaseSizeBeforeTest = catMunicipioRepository.findAll().size();
        // set the field null
        catMunicipio.setClave(null);

        // Create the CatMunicipio, which fails.


        restCatMunicipioMockMvc.perform(post("/api/cat-municipios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catMunicipio)))
            .andExpect(status().isBadRequest());

        List<CatMunicipio> catMunicipioList = catMunicipioRepository.findAll();
        assertThat(catMunicipioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCatMunicipios() throws Exception {
        // Initialize the database
        catMunicipioRepository.saveAndFlush(catMunicipio);

        // Get all the catMunicipioList
        restCatMunicipioMockMvc.perform(get("/api/cat-municipios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catMunicipio.getId())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE)));
    }
    
    @Test
    @Transactional
    public void getCatMunicipio() throws Exception {
        // Initialize the database
        catMunicipioRepository.saveAndFlush(catMunicipio);

        // Get the catMunicipio
        restCatMunicipioMockMvc.perform(get("/api/cat-municipios/{id}", catMunicipio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(catMunicipio.getId()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.clave").value(DEFAULT_CLAVE));
    }


    @Test
    @Transactional
    public void getCatMunicipiosByIdFiltering() throws Exception {
        // Initialize the database
        catMunicipioRepository.saveAndFlush(catMunicipio);

        String id = catMunicipio.getId();

        defaultCatMunicipioShouldBeFound("id.equals=" + id);
        defaultCatMunicipioShouldNotBeFound("id.notEquals=" + id);

        defaultCatMunicipioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCatMunicipioShouldNotBeFound("id.greaterThan=" + id);

        defaultCatMunicipioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCatMunicipioShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCatMunicipiosByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        catMunicipioRepository.saveAndFlush(catMunicipio);

        // Get all the catMunicipioList where nombre equals to DEFAULT_NOMBRE
        defaultCatMunicipioShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the catMunicipioList where nombre equals to UPDATED_NOMBRE
        defaultCatMunicipioShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllCatMunicipiosByNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catMunicipioRepository.saveAndFlush(catMunicipio);

        // Get all the catMunicipioList where nombre not equals to DEFAULT_NOMBRE
        defaultCatMunicipioShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

        // Get all the catMunicipioList where nombre not equals to UPDATED_NOMBRE
        defaultCatMunicipioShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllCatMunicipiosByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        catMunicipioRepository.saveAndFlush(catMunicipio);

        // Get all the catMunicipioList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultCatMunicipioShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the catMunicipioList where nombre equals to UPDATED_NOMBRE
        defaultCatMunicipioShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllCatMunicipiosByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        catMunicipioRepository.saveAndFlush(catMunicipio);

        // Get all the catMunicipioList where nombre is not null
        defaultCatMunicipioShouldBeFound("nombre.specified=true");

        // Get all the catMunicipioList where nombre is null
        defaultCatMunicipioShouldNotBeFound("nombre.specified=false");
    }
                @Test
    @Transactional
    public void getAllCatMunicipiosByNombreContainsSomething() throws Exception {
        // Initialize the database
        catMunicipioRepository.saveAndFlush(catMunicipio);

        // Get all the catMunicipioList where nombre contains DEFAULT_NOMBRE
        defaultCatMunicipioShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

        // Get all the catMunicipioList where nombre contains UPDATED_NOMBRE
        defaultCatMunicipioShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllCatMunicipiosByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        catMunicipioRepository.saveAndFlush(catMunicipio);

        // Get all the catMunicipioList where nombre does not contain DEFAULT_NOMBRE
        defaultCatMunicipioShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

        // Get all the catMunicipioList where nombre does not contain UPDATED_NOMBRE
        defaultCatMunicipioShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
    }


    @Test
    @Transactional
    public void getAllCatMunicipiosByClaveIsEqualToSomething() throws Exception {
        // Initialize the database
        catMunicipioRepository.saveAndFlush(catMunicipio);

        // Get all the catMunicipioList where clave equals to DEFAULT_CLAVE
        defaultCatMunicipioShouldBeFound("clave.equals=" + DEFAULT_CLAVE);

        // Get all the catMunicipioList where clave equals to UPDATED_CLAVE
        defaultCatMunicipioShouldNotBeFound("clave.equals=" + UPDATED_CLAVE);
    }

    @Test
    @Transactional
    public void getAllCatMunicipiosByClaveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catMunicipioRepository.saveAndFlush(catMunicipio);

        // Get all the catMunicipioList where clave not equals to DEFAULT_CLAVE
        defaultCatMunicipioShouldNotBeFound("clave.notEquals=" + DEFAULT_CLAVE);

        // Get all the catMunicipioList where clave not equals to UPDATED_CLAVE
        defaultCatMunicipioShouldBeFound("clave.notEquals=" + UPDATED_CLAVE);
    }

    @Test
    @Transactional
    public void getAllCatMunicipiosByClaveIsInShouldWork() throws Exception {
        // Initialize the database
        catMunicipioRepository.saveAndFlush(catMunicipio);

        // Get all the catMunicipioList where clave in DEFAULT_CLAVE or UPDATED_CLAVE
        defaultCatMunicipioShouldBeFound("clave.in=" + DEFAULT_CLAVE + "," + UPDATED_CLAVE);

        // Get all the catMunicipioList where clave equals to UPDATED_CLAVE
        defaultCatMunicipioShouldNotBeFound("clave.in=" + UPDATED_CLAVE);
    }

    @Test
    @Transactional
    public void getAllCatMunicipiosByClaveIsNullOrNotNull() throws Exception {
        // Initialize the database
        catMunicipioRepository.saveAndFlush(catMunicipio);

        // Get all the catMunicipioList where clave is not null
        defaultCatMunicipioShouldBeFound("clave.specified=true");

        // Get all the catMunicipioList where clave is null
        defaultCatMunicipioShouldNotBeFound("clave.specified=false");
    }
                @Test
    @Transactional
    public void getAllCatMunicipiosByClaveContainsSomething() throws Exception {
        // Initialize the database
        catMunicipioRepository.saveAndFlush(catMunicipio);

        // Get all the catMunicipioList where clave contains DEFAULT_CLAVE
        defaultCatMunicipioShouldBeFound("clave.contains=" + DEFAULT_CLAVE);

        // Get all the catMunicipioList where clave contains UPDATED_CLAVE
        defaultCatMunicipioShouldNotBeFound("clave.contains=" + UPDATED_CLAVE);
    }

    @Test
    @Transactional
    public void getAllCatMunicipiosByClaveNotContainsSomething() throws Exception {
        // Initialize the database
        catMunicipioRepository.saveAndFlush(catMunicipio);

        // Get all the catMunicipioList where clave does not contain DEFAULT_CLAVE
        defaultCatMunicipioShouldNotBeFound("clave.doesNotContain=" + DEFAULT_CLAVE);

        // Get all the catMunicipioList where clave does not contain UPDATED_CLAVE
        defaultCatMunicipioShouldBeFound("clave.doesNotContain=" + UPDATED_CLAVE);
    }


    @Test
    @Transactional
    public void getAllCatMunicipiosByUsuarioIsEqualToSomething() throws Exception {
        // Get already existing entity
        User usuario = catMunicipio.getUsuario();
        catMunicipioRepository.saveAndFlush(catMunicipio);
        Long usuarioId = usuario.getId();

        // Get all the catMunicipioList where usuario equals to usuarioId
        defaultCatMunicipioShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the catMunicipioList where usuario equals to usuarioId + 1
        defaultCatMunicipioShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }


    @Test
    @Transactional
    public void getAllCatMunicipiosByEstadoIsEqualToSomething() throws Exception {
        // Get already existing entity
        CatEstado estado = catMunicipio.getEstado();
        catMunicipioRepository.saveAndFlush(catMunicipio);
        String estadoId = estado.getId();

        // Get all the catMunicipioList where estado equals to estadoId
        defaultCatMunicipioShouldBeFound("estadoId.equals=" + estadoId);

        // Get all the catMunicipioList where estado equals to estadoId + 1
        defaultCatMunicipioShouldNotBeFound("estadoId.equals=" + (estadoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCatMunicipioShouldBeFound(String filter) throws Exception {
        restCatMunicipioMockMvc.perform(get("/api/cat-municipios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catMunicipio.getId())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE)));

        // Check, that the count call also returns 1
        restCatMunicipioMockMvc.perform(get("/api/cat-municipios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCatMunicipioShouldNotBeFound(String filter) throws Exception {
        restCatMunicipioMockMvc.perform(get("/api/cat-municipios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCatMunicipioMockMvc.perform(get("/api/cat-municipios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCatMunicipio() throws Exception {
        // Get the catMunicipio
        restCatMunicipioMockMvc.perform(get("/api/cat-municipios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCatMunicipio() throws Exception {
        // Initialize the database
        catMunicipioService.save(catMunicipio);

        int databaseSizeBeforeUpdate = catMunicipioRepository.findAll().size();

        // Update the catMunicipio
        CatMunicipio updatedCatMunicipio = catMunicipioRepository.findById(catMunicipio.getId()).get();
        // Disconnect from session so that the updates on updatedCatMunicipio are not directly saved in db
        em.detach(updatedCatMunicipio);
        updatedCatMunicipio
            .nombre(UPDATED_NOMBRE)
            .clave(UPDATED_CLAVE);

        restCatMunicipioMockMvc.perform(put("/api/cat-municipios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCatMunicipio)))
            .andExpect(status().isOk());

        // Validate the CatMunicipio in the database
        List<CatMunicipio> catMunicipioList = catMunicipioRepository.findAll();
        assertThat(catMunicipioList).hasSize(databaseSizeBeforeUpdate);
        CatMunicipio testCatMunicipio = catMunicipioList.get(catMunicipioList.size() - 1);
        assertThat(testCatMunicipio.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCatMunicipio.getClave()).isEqualTo(UPDATED_CLAVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCatMunicipio() throws Exception {
        int databaseSizeBeforeUpdate = catMunicipioRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatMunicipioMockMvc.perform(put("/api/cat-municipios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catMunicipio)))
            .andExpect(status().isBadRequest());

        // Validate the CatMunicipio in the database
        List<CatMunicipio> catMunicipioList = catMunicipioRepository.findAll();
        assertThat(catMunicipioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCatMunicipio() throws Exception {
        // Initialize the database
        catMunicipioService.save(catMunicipio);

        int databaseSizeBeforeDelete = catMunicipioRepository.findAll().size();

        // Delete the catMunicipio
        restCatMunicipioMockMvc.perform(delete("/api/cat-municipios/{id}", catMunicipio.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CatMunicipio> catMunicipioList = catMunicipioRepository.findAll();
        assertThat(catMunicipioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
