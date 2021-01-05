package com.resamx.web.rest;

import com.resamx.ResaMxWebApp;
import com.resamx.domain.CatEstado;
import com.resamx.domain.User;
import com.resamx.domain.CatPais;
import com.resamx.repository.CatEstadoRepository;
import com.resamx.service.CatEstadoService;
import com.resamx.service.dto.CatEstadoCriteria;
import com.resamx.service.CatEstadoQueryService;

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
 * Integration tests for the {@link CatEstadoResource} REST controller.
 */
@SpringBootTest(classes = ResaMxWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CatEstadoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private CatEstadoRepository catEstadoRepository;

    @Autowired
    private CatEstadoService catEstadoService;

    @Autowired
    private CatEstadoQueryService catEstadoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCatEstadoMockMvc;

    private CatEstado catEstado;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatEstado createEntity(EntityManager em) {
        CatEstado catEstado = new CatEstado()
            .nombre(DEFAULT_NOMBRE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catEstado.setUsuario(user);
        // Add required entity
        CatPais catPais;
        if (TestUtil.findAll(em, CatPais.class).isEmpty()) {
            catPais = CatPaisResourceIT.createEntity(em);
            em.persist(catPais);
            em.flush();
        } else {
            catPais = TestUtil.findAll(em, CatPais.class).get(0);
        }
        catEstado.setPais(catPais);
        return catEstado;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatEstado createUpdatedEntity(EntityManager em) {
        CatEstado catEstado = new CatEstado()
            .nombre(UPDATED_NOMBRE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catEstado.setUsuario(user);
        // Add required entity
        CatPais catPais;
        if (TestUtil.findAll(em, CatPais.class).isEmpty()) {
            catPais = CatPaisResourceIT.createUpdatedEntity(em);
            em.persist(catPais);
            em.flush();
        } else {
            catPais = TestUtil.findAll(em, CatPais.class).get(0);
        }
        catEstado.setPais(catPais);
        return catEstado;
    }

    @BeforeEach
    public void initTest() {
        catEstado = createEntity(em);
    }

    @Test
    @Transactional
    public void createCatEstado() throws Exception {
        int databaseSizeBeforeCreate = catEstadoRepository.findAll().size();
        // Create the CatEstado
        restCatEstadoMockMvc.perform(post("/api/cat-estados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catEstado)))
            .andExpect(status().isCreated());

        // Validate the CatEstado in the database
        List<CatEstado> catEstadoList = catEstadoRepository.findAll();
        assertThat(catEstadoList).hasSize(databaseSizeBeforeCreate + 1);
        CatEstado testCatEstado = catEstadoList.get(catEstadoList.size() - 1);
        assertThat(testCatEstado.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createCatEstadoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = catEstadoRepository.findAll().size();

        // Create the CatEstado with an existing ID
        catEstado.setId("1");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatEstadoMockMvc.perform(post("/api/cat-estados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catEstado)))
            .andExpect(status().isBadRequest());

        // Validate the CatEstado in the database
        List<CatEstado> catEstadoList = catEstadoRepository.findAll();
        assertThat(catEstadoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = catEstadoRepository.findAll().size();
        // set the field null
        catEstado.setNombre(null);

        // Create the CatEstado, which fails.


        restCatEstadoMockMvc.perform(post("/api/cat-estados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catEstado)))
            .andExpect(status().isBadRequest());

        List<CatEstado> catEstadoList = catEstadoRepository.findAll();
        assertThat(catEstadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCatEstados() throws Exception {
        // Initialize the database
        catEstadoRepository.saveAndFlush(catEstado);

        // Get all the catEstadoList
        restCatEstadoMockMvc.perform(get("/api/cat-estados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catEstado.getId())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }
    
    @Test
    @Transactional
    public void getCatEstado() throws Exception {
        // Initialize the database
        catEstadoRepository.saveAndFlush(catEstado);

        // Get the catEstado
        restCatEstadoMockMvc.perform(get("/api/cat-estados/{id}", catEstado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(catEstado.getId()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }


    @Test
    @Transactional
    public void getCatEstadosByIdFiltering() throws Exception {
        // Initialize the database
        catEstadoRepository.saveAndFlush(catEstado);

        String id = catEstado.getId();

        defaultCatEstadoShouldBeFound("id.equals=" + id);
        defaultCatEstadoShouldNotBeFound("id.notEquals=" + id);

        defaultCatEstadoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCatEstadoShouldNotBeFound("id.greaterThan=" + id);

        defaultCatEstadoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCatEstadoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCatEstadosByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        catEstadoRepository.saveAndFlush(catEstado);

        // Get all the catEstadoList where nombre equals to DEFAULT_NOMBRE
        defaultCatEstadoShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the catEstadoList where nombre equals to UPDATED_NOMBRE
        defaultCatEstadoShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllCatEstadosByNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catEstadoRepository.saveAndFlush(catEstado);

        // Get all the catEstadoList where nombre not equals to DEFAULT_NOMBRE
        defaultCatEstadoShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

        // Get all the catEstadoList where nombre not equals to UPDATED_NOMBRE
        defaultCatEstadoShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllCatEstadosByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        catEstadoRepository.saveAndFlush(catEstado);

        // Get all the catEstadoList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultCatEstadoShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the catEstadoList where nombre equals to UPDATED_NOMBRE
        defaultCatEstadoShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllCatEstadosByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        catEstadoRepository.saveAndFlush(catEstado);

        // Get all the catEstadoList where nombre is not null
        defaultCatEstadoShouldBeFound("nombre.specified=true");

        // Get all the catEstadoList where nombre is null
        defaultCatEstadoShouldNotBeFound("nombre.specified=false");
    }
                @Test
    @Transactional
    public void getAllCatEstadosByNombreContainsSomething() throws Exception {
        // Initialize the database
        catEstadoRepository.saveAndFlush(catEstado);

        // Get all the catEstadoList where nombre contains DEFAULT_NOMBRE
        defaultCatEstadoShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

        // Get all the catEstadoList where nombre contains UPDATED_NOMBRE
        defaultCatEstadoShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllCatEstadosByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        catEstadoRepository.saveAndFlush(catEstado);

        // Get all the catEstadoList where nombre does not contain DEFAULT_NOMBRE
        defaultCatEstadoShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

        // Get all the catEstadoList where nombre does not contain UPDATED_NOMBRE
        defaultCatEstadoShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
    }


    @Test
    @Transactional
    public void getAllCatEstadosByUsuarioIsEqualToSomething() throws Exception {
        // Get already existing entity
        User usuario = catEstado.getUsuario();
        catEstadoRepository.saveAndFlush(catEstado);
        Long usuarioId = usuario.getId();

        // Get all the catEstadoList where usuario equals to usuarioId
        defaultCatEstadoShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the catEstadoList where usuario equals to usuarioId + 1
        defaultCatEstadoShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }


    @Test
    @Transactional
    public void getAllCatEstadosByPaisIsEqualToSomething() throws Exception {
        // Get already existing entity
        CatPais pais = catEstado.getPais();
        catEstadoRepository.saveAndFlush(catEstado);
        String paisId = pais.getId();

        // Get all the catEstadoList where pais equals to paisId
        defaultCatEstadoShouldBeFound("paisId.equals=" + paisId);

        // Get all the catEstadoList where pais equals to paisId + 1
        defaultCatEstadoShouldNotBeFound("paisId.equals=" + (paisId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCatEstadoShouldBeFound(String filter) throws Exception {
        restCatEstadoMockMvc.perform(get("/api/cat-estados?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catEstado.getId())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));

        // Check, that the count call also returns 1
        restCatEstadoMockMvc.perform(get("/api/cat-estados/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCatEstadoShouldNotBeFound(String filter) throws Exception {
        restCatEstadoMockMvc.perform(get("/api/cat-estados?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCatEstadoMockMvc.perform(get("/api/cat-estados/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCatEstado() throws Exception {
        // Get the catEstado
        restCatEstadoMockMvc.perform(get("/api/cat-estados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCatEstado() throws Exception {
        // Initialize the database
        catEstadoService.save(catEstado);

        int databaseSizeBeforeUpdate = catEstadoRepository.findAll().size();

        // Update the catEstado
        CatEstado updatedCatEstado = catEstadoRepository.findById(catEstado.getId()).get();
        // Disconnect from session so that the updates on updatedCatEstado are not directly saved in db
        em.detach(updatedCatEstado);
        updatedCatEstado
            .nombre(UPDATED_NOMBRE);

        restCatEstadoMockMvc.perform(put("/api/cat-estados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCatEstado)))
            .andExpect(status().isOk());

        // Validate the CatEstado in the database
        List<CatEstado> catEstadoList = catEstadoRepository.findAll();
        assertThat(catEstadoList).hasSize(databaseSizeBeforeUpdate);
        CatEstado testCatEstado = catEstadoList.get(catEstadoList.size() - 1);
        assertThat(testCatEstado.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingCatEstado() throws Exception {
        int databaseSizeBeforeUpdate = catEstadoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatEstadoMockMvc.perform(put("/api/cat-estados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catEstado)))
            .andExpect(status().isBadRequest());

        // Validate the CatEstado in the database
        List<CatEstado> catEstadoList = catEstadoRepository.findAll();
        assertThat(catEstadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCatEstado() throws Exception {
        // Initialize the database
        catEstadoService.save(catEstado);

        int databaseSizeBeforeDelete = catEstadoRepository.findAll().size();

        // Delete the catEstado
        restCatEstadoMockMvc.perform(delete("/api/cat-estados/{id}", catEstado.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CatEstado> catEstadoList = catEstadoRepository.findAll();
        assertThat(catEstadoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
