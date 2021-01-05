package com.resamx.web.rest;

import com.resamx.ResaMxWebApp;
import com.resamx.domain.CatLocalidad;
import com.resamx.domain.User;
import com.resamx.domain.CatEstado;
import com.resamx.domain.CatMunicipio;
import com.resamx.repository.CatLocalidadRepository;
import com.resamx.service.CatLocalidadService;
import com.resamx.service.dto.CatLocalidadCriteria;
import com.resamx.service.CatLocalidadQueryService;

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
 * Integration tests for the {@link CatLocalidadResource} REST controller.
 */
@SpringBootTest(classes = ResaMxWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CatLocalidadResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_CLAVE = "AAAA";
    private static final String UPDATED_CLAVE = "BBBB";

    @Autowired
    private CatLocalidadRepository catLocalidadRepository;

    @Autowired
    private CatLocalidadService catLocalidadService;

    @Autowired
    private CatLocalidadQueryService catLocalidadQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCatLocalidadMockMvc;

    private CatLocalidad catLocalidad;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatLocalidad createEntity(EntityManager em) {
        CatLocalidad catLocalidad = new CatLocalidad()
            .nombre(DEFAULT_NOMBRE)
            .clave(DEFAULT_CLAVE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catLocalidad.setUsuario(user);
        // Add required entity
        CatEstado catEstado;
        if (TestUtil.findAll(em, CatEstado.class).isEmpty()) {
            catEstado = CatEstadoResourceIT.createEntity(em);
            em.persist(catEstado);
            em.flush();
        } else {
            catEstado = TestUtil.findAll(em, CatEstado.class).get(0);
        }
        catLocalidad.setEstado(catEstado);
        // Add required entity
        CatMunicipio catMunicipio;
        if (TestUtil.findAll(em, CatMunicipio.class).isEmpty()) {
            catMunicipio = CatMunicipioResourceIT.createEntity(em);
            em.persist(catMunicipio);
            em.flush();
        } else {
            catMunicipio = TestUtil.findAll(em, CatMunicipio.class).get(0);
        }
        catLocalidad.setMunicipio(catMunicipio);
        return catLocalidad;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatLocalidad createUpdatedEntity(EntityManager em) {
        CatLocalidad catLocalidad = new CatLocalidad()
            .nombre(UPDATED_NOMBRE)
            .clave(UPDATED_CLAVE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catLocalidad.setUsuario(user);
        // Add required entity
        CatEstado catEstado;
        if (TestUtil.findAll(em, CatEstado.class).isEmpty()) {
            catEstado = CatEstadoResourceIT.createUpdatedEntity(em);
            em.persist(catEstado);
            em.flush();
        } else {
            catEstado = TestUtil.findAll(em, CatEstado.class).get(0);
        }
        catLocalidad.setEstado(catEstado);
        // Add required entity
        CatMunicipio catMunicipio;
        if (TestUtil.findAll(em, CatMunicipio.class).isEmpty()) {
            catMunicipio = CatMunicipioResourceIT.createUpdatedEntity(em);
            em.persist(catMunicipio);
            em.flush();
        } else {
            catMunicipio = TestUtil.findAll(em, CatMunicipio.class).get(0);
        }
        catLocalidad.setMunicipio(catMunicipio);
        return catLocalidad;
    }

    @BeforeEach
    public void initTest() {
        catLocalidad = createEntity(em);
    }

    @Test
    @Transactional
    public void createCatLocalidad() throws Exception {
        int databaseSizeBeforeCreate = catLocalidadRepository.findAll().size();
        // Create the CatLocalidad
        restCatLocalidadMockMvc.perform(post("/api/cat-localidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catLocalidad)))
            .andExpect(status().isCreated());

        // Validate the CatLocalidad in the database
        List<CatLocalidad> catLocalidadList = catLocalidadRepository.findAll();
        assertThat(catLocalidadList).hasSize(databaseSizeBeforeCreate + 1);
        CatLocalidad testCatLocalidad = catLocalidadList.get(catLocalidadList.size() - 1);
        assertThat(testCatLocalidad.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCatLocalidad.getClave()).isEqualTo(DEFAULT_CLAVE);
    }

    @Test
    @Transactional
    public void createCatLocalidadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = catLocalidadRepository.findAll().size();

        // Create the CatLocalidad with an existing ID
        catLocalidad.setId("1");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatLocalidadMockMvc.perform(post("/api/cat-localidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catLocalidad)))
            .andExpect(status().isBadRequest());

        // Validate the CatLocalidad in the database
        List<CatLocalidad> catLocalidadList = catLocalidadRepository.findAll();
        assertThat(catLocalidadList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = catLocalidadRepository.findAll().size();
        // set the field null
        catLocalidad.setNombre(null);

        // Create the CatLocalidad, which fails.


        restCatLocalidadMockMvc.perform(post("/api/cat-localidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catLocalidad)))
            .andExpect(status().isBadRequest());

        List<CatLocalidad> catLocalidadList = catLocalidadRepository.findAll();
        assertThat(catLocalidadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClaveIsRequired() throws Exception {
        int databaseSizeBeforeTest = catLocalidadRepository.findAll().size();
        // set the field null
        catLocalidad.setClave(null);

        // Create the CatLocalidad, which fails.


        restCatLocalidadMockMvc.perform(post("/api/cat-localidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catLocalidad)))
            .andExpect(status().isBadRequest());

        List<CatLocalidad> catLocalidadList = catLocalidadRepository.findAll();
        assertThat(catLocalidadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCatLocalidads() throws Exception {
        // Initialize the database
        catLocalidadRepository.saveAndFlush(catLocalidad);

        // Get all the catLocalidadList
        restCatLocalidadMockMvc.perform(get("/api/cat-localidads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catLocalidad.getId())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE)));
    }
    
    @Test
    @Transactional
    public void getCatLocalidad() throws Exception {
        // Initialize the database
        catLocalidadRepository.saveAndFlush(catLocalidad);

        // Get the catLocalidad
        restCatLocalidadMockMvc.perform(get("/api/cat-localidads/{id}", catLocalidad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(catLocalidad.getId()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.clave").value(DEFAULT_CLAVE));
    }


    @Test
    @Transactional
    public void getCatLocalidadsByIdFiltering() throws Exception {
        // Initialize the database
        catLocalidadRepository.saveAndFlush(catLocalidad);

        String id = catLocalidad.getId();

        defaultCatLocalidadShouldBeFound("id.equals=" + id);
        defaultCatLocalidadShouldNotBeFound("id.notEquals=" + id);

        defaultCatLocalidadShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCatLocalidadShouldNotBeFound("id.greaterThan=" + id);

        defaultCatLocalidadShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCatLocalidadShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCatLocalidadsByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        catLocalidadRepository.saveAndFlush(catLocalidad);

        // Get all the catLocalidadList where nombre equals to DEFAULT_NOMBRE
        defaultCatLocalidadShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the catLocalidadList where nombre equals to UPDATED_NOMBRE
        defaultCatLocalidadShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllCatLocalidadsByNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catLocalidadRepository.saveAndFlush(catLocalidad);

        // Get all the catLocalidadList where nombre not equals to DEFAULT_NOMBRE
        defaultCatLocalidadShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

        // Get all the catLocalidadList where nombre not equals to UPDATED_NOMBRE
        defaultCatLocalidadShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllCatLocalidadsByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        catLocalidadRepository.saveAndFlush(catLocalidad);

        // Get all the catLocalidadList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultCatLocalidadShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the catLocalidadList where nombre equals to UPDATED_NOMBRE
        defaultCatLocalidadShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllCatLocalidadsByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        catLocalidadRepository.saveAndFlush(catLocalidad);

        // Get all the catLocalidadList where nombre is not null
        defaultCatLocalidadShouldBeFound("nombre.specified=true");

        // Get all the catLocalidadList where nombre is null
        defaultCatLocalidadShouldNotBeFound("nombre.specified=false");
    }
                @Test
    @Transactional
    public void getAllCatLocalidadsByNombreContainsSomething() throws Exception {
        // Initialize the database
        catLocalidadRepository.saveAndFlush(catLocalidad);

        // Get all the catLocalidadList where nombre contains DEFAULT_NOMBRE
        defaultCatLocalidadShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

        // Get all the catLocalidadList where nombre contains UPDATED_NOMBRE
        defaultCatLocalidadShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllCatLocalidadsByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        catLocalidadRepository.saveAndFlush(catLocalidad);

        // Get all the catLocalidadList where nombre does not contain DEFAULT_NOMBRE
        defaultCatLocalidadShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

        // Get all the catLocalidadList where nombre does not contain UPDATED_NOMBRE
        defaultCatLocalidadShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
    }


    @Test
    @Transactional
    public void getAllCatLocalidadsByClaveIsEqualToSomething() throws Exception {
        // Initialize the database
        catLocalidadRepository.saveAndFlush(catLocalidad);

        // Get all the catLocalidadList where clave equals to DEFAULT_CLAVE
        defaultCatLocalidadShouldBeFound("clave.equals=" + DEFAULT_CLAVE);

        // Get all the catLocalidadList where clave equals to UPDATED_CLAVE
        defaultCatLocalidadShouldNotBeFound("clave.equals=" + UPDATED_CLAVE);
    }

    @Test
    @Transactional
    public void getAllCatLocalidadsByClaveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catLocalidadRepository.saveAndFlush(catLocalidad);

        // Get all the catLocalidadList where clave not equals to DEFAULT_CLAVE
        defaultCatLocalidadShouldNotBeFound("clave.notEquals=" + DEFAULT_CLAVE);

        // Get all the catLocalidadList where clave not equals to UPDATED_CLAVE
        defaultCatLocalidadShouldBeFound("clave.notEquals=" + UPDATED_CLAVE);
    }

    @Test
    @Transactional
    public void getAllCatLocalidadsByClaveIsInShouldWork() throws Exception {
        // Initialize the database
        catLocalidadRepository.saveAndFlush(catLocalidad);

        // Get all the catLocalidadList where clave in DEFAULT_CLAVE or UPDATED_CLAVE
        defaultCatLocalidadShouldBeFound("clave.in=" + DEFAULT_CLAVE + "," + UPDATED_CLAVE);

        // Get all the catLocalidadList where clave equals to UPDATED_CLAVE
        defaultCatLocalidadShouldNotBeFound("clave.in=" + UPDATED_CLAVE);
    }

    @Test
    @Transactional
    public void getAllCatLocalidadsByClaveIsNullOrNotNull() throws Exception {
        // Initialize the database
        catLocalidadRepository.saveAndFlush(catLocalidad);

        // Get all the catLocalidadList where clave is not null
        defaultCatLocalidadShouldBeFound("clave.specified=true");

        // Get all the catLocalidadList where clave is null
        defaultCatLocalidadShouldNotBeFound("clave.specified=false");
    }
                @Test
    @Transactional
    public void getAllCatLocalidadsByClaveContainsSomething() throws Exception {
        // Initialize the database
        catLocalidadRepository.saveAndFlush(catLocalidad);

        // Get all the catLocalidadList where clave contains DEFAULT_CLAVE
        defaultCatLocalidadShouldBeFound("clave.contains=" + DEFAULT_CLAVE);

        // Get all the catLocalidadList where clave contains UPDATED_CLAVE
        defaultCatLocalidadShouldNotBeFound("clave.contains=" + UPDATED_CLAVE);
    }

    @Test
    @Transactional
    public void getAllCatLocalidadsByClaveNotContainsSomething() throws Exception {
        // Initialize the database
        catLocalidadRepository.saveAndFlush(catLocalidad);

        // Get all the catLocalidadList where clave does not contain DEFAULT_CLAVE
        defaultCatLocalidadShouldNotBeFound("clave.doesNotContain=" + DEFAULT_CLAVE);

        // Get all the catLocalidadList where clave does not contain UPDATED_CLAVE
        defaultCatLocalidadShouldBeFound("clave.doesNotContain=" + UPDATED_CLAVE);
    }


    @Test
    @Transactional
    public void getAllCatLocalidadsByUsuarioIsEqualToSomething() throws Exception {
        // Get already existing entity
        User usuario = catLocalidad.getUsuario();
        catLocalidadRepository.saveAndFlush(catLocalidad);
        Long usuarioId = usuario.getId();

        // Get all the catLocalidadList where usuario equals to usuarioId
        defaultCatLocalidadShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the catLocalidadList where usuario equals to usuarioId + 1
        defaultCatLocalidadShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }


    @Test
    @Transactional
    public void getAllCatLocalidadsByEstadoIsEqualToSomething() throws Exception {
        // Get already existing entity
        CatEstado estado = catLocalidad.getEstado();
        catLocalidadRepository.saveAndFlush(catLocalidad);
        String estadoId = estado.getId();

        // Get all the catLocalidadList where estado equals to estadoId
        defaultCatLocalidadShouldBeFound("estadoId.equals=" + estadoId);

        // Get all the catLocalidadList where estado equals to estadoId + 1
        defaultCatLocalidadShouldNotBeFound("estadoId.equals=" + (estadoId + 1));
    }


    @Test
    @Transactional
    public void getAllCatLocalidadsByMunicipioIsEqualToSomething() throws Exception {
        // Get already existing entity
        CatMunicipio municipio = catLocalidad.getMunicipio();
        catLocalidadRepository.saveAndFlush(catLocalidad);
        String municipioId = municipio.getId();

        // Get all the catLocalidadList where municipio equals to municipioId
        defaultCatLocalidadShouldBeFound("municipioId.equals=" + municipioId);

        // Get all the catLocalidadList where municipio equals to municipioId + 1
        defaultCatLocalidadShouldNotBeFound("municipioId.equals=" + (municipioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCatLocalidadShouldBeFound(String filter) throws Exception {
        restCatLocalidadMockMvc.perform(get("/api/cat-localidads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catLocalidad.getId())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE)));

        // Check, that the count call also returns 1
        restCatLocalidadMockMvc.perform(get("/api/cat-localidads/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCatLocalidadShouldNotBeFound(String filter) throws Exception {
        restCatLocalidadMockMvc.perform(get("/api/cat-localidads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCatLocalidadMockMvc.perform(get("/api/cat-localidads/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCatLocalidad() throws Exception {
        // Get the catLocalidad
        restCatLocalidadMockMvc.perform(get("/api/cat-localidads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCatLocalidad() throws Exception {
        // Initialize the database
        catLocalidadService.save(catLocalidad);

        int databaseSizeBeforeUpdate = catLocalidadRepository.findAll().size();

        // Update the catLocalidad
        CatLocalidad updatedCatLocalidad = catLocalidadRepository.findById(catLocalidad.getId()).get();
        // Disconnect from session so that the updates on updatedCatLocalidad are not directly saved in db
        em.detach(updatedCatLocalidad);
        updatedCatLocalidad
            .nombre(UPDATED_NOMBRE)
            .clave(UPDATED_CLAVE);

        restCatLocalidadMockMvc.perform(put("/api/cat-localidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCatLocalidad)))
            .andExpect(status().isOk());

        // Validate the CatLocalidad in the database
        List<CatLocalidad> catLocalidadList = catLocalidadRepository.findAll();
        assertThat(catLocalidadList).hasSize(databaseSizeBeforeUpdate);
        CatLocalidad testCatLocalidad = catLocalidadList.get(catLocalidadList.size() - 1);
        assertThat(testCatLocalidad.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCatLocalidad.getClave()).isEqualTo(UPDATED_CLAVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCatLocalidad() throws Exception {
        int databaseSizeBeforeUpdate = catLocalidadRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatLocalidadMockMvc.perform(put("/api/cat-localidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catLocalidad)))
            .andExpect(status().isBadRequest());

        // Validate the CatLocalidad in the database
        List<CatLocalidad> catLocalidadList = catLocalidadRepository.findAll();
        assertThat(catLocalidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCatLocalidad() throws Exception {
        // Initialize the database
        catLocalidadService.save(catLocalidad);

        int databaseSizeBeforeDelete = catLocalidadRepository.findAll().size();

        // Delete the catLocalidad
        restCatLocalidadMockMvc.perform(delete("/api/cat-localidads/{id}", catLocalidad.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CatLocalidad> catLocalidadList = catLocalidadRepository.findAll();
        assertThat(catLocalidadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
