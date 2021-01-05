package com.resamx.web.rest;

import com.resamx.ResaMxWebApp;
import com.resamx.domain.CatMoneda;
import com.resamx.domain.User;
import com.resamx.domain.CatPais;
import com.resamx.repository.CatMonedaRepository;
import com.resamx.service.CatMonedaService;
import com.resamx.service.dto.CatMonedaCriteria;
import com.resamx.service.CatMonedaQueryService;

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
 * Integration tests for the {@link CatMonedaResource} REST controller.
 */
@SpringBootTest(classes = ResaMxWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CatMonedaResourceIT {

    private static final String DEFAULT_MONEDA = "AAAAAAAAAA";
    private static final String UPDATED_MONEDA = "BBBBBBBBBB";

    @Autowired
    private CatMonedaRepository catMonedaRepository;

    @Autowired
    private CatMonedaService catMonedaService;

    @Autowired
    private CatMonedaQueryService catMonedaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCatMonedaMockMvc;

    private CatMoneda catMoneda;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatMoneda createEntity(EntityManager em) {
        CatMoneda catMoneda = new CatMoneda()
            .moneda(DEFAULT_MONEDA);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catMoneda.setUsuario(user);
        // Add required entity
        CatPais catPais;
        if (TestUtil.findAll(em, CatPais.class).isEmpty()) {
            catPais = CatPaisResourceIT.createEntity(em);
            em.persist(catPais);
            em.flush();
        } else {
            catPais = TestUtil.findAll(em, CatPais.class).get(0);
        }
        catMoneda.setPais(catPais);
        return catMoneda;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatMoneda createUpdatedEntity(EntityManager em) {
        CatMoneda catMoneda = new CatMoneda()
            .moneda(UPDATED_MONEDA);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        catMoneda.setUsuario(user);
        // Add required entity
        CatPais catPais;
        if (TestUtil.findAll(em, CatPais.class).isEmpty()) {
            catPais = CatPaisResourceIT.createUpdatedEntity(em);
            em.persist(catPais);
            em.flush();
        } else {
            catPais = TestUtil.findAll(em, CatPais.class).get(0);
        }
        catMoneda.setPais(catPais);
        return catMoneda;
    }

    @BeforeEach
    public void initTest() {
        catMoneda = createEntity(em);
    }

    @Test
    @Transactional
    public void createCatMoneda() throws Exception {
        int databaseSizeBeforeCreate = catMonedaRepository.findAll().size();
        // Create the CatMoneda
        restCatMonedaMockMvc.perform(post("/api/cat-monedas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catMoneda)))
            .andExpect(status().isCreated());

        // Validate the CatMoneda in the database
        List<CatMoneda> catMonedaList = catMonedaRepository.findAll();
        assertThat(catMonedaList).hasSize(databaseSizeBeforeCreate + 1);
        CatMoneda testCatMoneda = catMonedaList.get(catMonedaList.size() - 1);
        assertThat(testCatMoneda.getMoneda()).isEqualTo(DEFAULT_MONEDA);
    }

    @Test
    @Transactional
    public void createCatMonedaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = catMonedaRepository.findAll().size();

        // Create the CatMoneda with an existing ID
        catMoneda.setId("1");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatMonedaMockMvc.perform(post("/api/cat-monedas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catMoneda)))
            .andExpect(status().isBadRequest());

        // Validate the CatMoneda in the database
        List<CatMoneda> catMonedaList = catMonedaRepository.findAll();
        assertThat(catMonedaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMonedaIsRequired() throws Exception {
        int databaseSizeBeforeTest = catMonedaRepository.findAll().size();
        // set the field null
        catMoneda.setMoneda(null);

        // Create the CatMoneda, which fails.


        restCatMonedaMockMvc.perform(post("/api/cat-monedas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catMoneda)))
            .andExpect(status().isBadRequest());

        List<CatMoneda> catMonedaList = catMonedaRepository.findAll();
        assertThat(catMonedaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCatMonedas() throws Exception {
        // Initialize the database
        catMonedaRepository.saveAndFlush(catMoneda);

        // Get all the catMonedaList
        restCatMonedaMockMvc.perform(get("/api/cat-monedas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catMoneda.getId())))
            .andExpect(jsonPath("$.[*].moneda").value(hasItem(DEFAULT_MONEDA)));
    }
    
    @Test
    @Transactional
    public void getCatMoneda() throws Exception {
        // Initialize the database
        catMonedaRepository.saveAndFlush(catMoneda);

        // Get the catMoneda
        restCatMonedaMockMvc.perform(get("/api/cat-monedas/{id}", catMoneda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(catMoneda.getId()))
            .andExpect(jsonPath("$.moneda").value(DEFAULT_MONEDA));
    }


    @Test
    @Transactional
    public void getCatMonedasByIdFiltering() throws Exception {
        // Initialize the database
        catMonedaRepository.saveAndFlush(catMoneda);

        String id = catMoneda.getId();

        defaultCatMonedaShouldBeFound("id.equals=" + id);
        defaultCatMonedaShouldNotBeFound("id.notEquals=" + id);

        defaultCatMonedaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCatMonedaShouldNotBeFound("id.greaterThan=" + id);

        defaultCatMonedaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCatMonedaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCatMonedasByMonedaIsEqualToSomething() throws Exception {
        // Initialize the database
        catMonedaRepository.saveAndFlush(catMoneda);

        // Get all the catMonedaList where moneda equals to DEFAULT_MONEDA
        defaultCatMonedaShouldBeFound("moneda.equals=" + DEFAULT_MONEDA);

        // Get all the catMonedaList where moneda equals to UPDATED_MONEDA
        defaultCatMonedaShouldNotBeFound("moneda.equals=" + UPDATED_MONEDA);
    }

    @Test
    @Transactional
    public void getAllCatMonedasByMonedaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catMonedaRepository.saveAndFlush(catMoneda);

        // Get all the catMonedaList where moneda not equals to DEFAULT_MONEDA
        defaultCatMonedaShouldNotBeFound("moneda.notEquals=" + DEFAULT_MONEDA);

        // Get all the catMonedaList where moneda not equals to UPDATED_MONEDA
        defaultCatMonedaShouldBeFound("moneda.notEquals=" + UPDATED_MONEDA);
    }

    @Test
    @Transactional
    public void getAllCatMonedasByMonedaIsInShouldWork() throws Exception {
        // Initialize the database
        catMonedaRepository.saveAndFlush(catMoneda);

        // Get all the catMonedaList where moneda in DEFAULT_MONEDA or UPDATED_MONEDA
        defaultCatMonedaShouldBeFound("moneda.in=" + DEFAULT_MONEDA + "," + UPDATED_MONEDA);

        // Get all the catMonedaList where moneda equals to UPDATED_MONEDA
        defaultCatMonedaShouldNotBeFound("moneda.in=" + UPDATED_MONEDA);
    }

    @Test
    @Transactional
    public void getAllCatMonedasByMonedaIsNullOrNotNull() throws Exception {
        // Initialize the database
        catMonedaRepository.saveAndFlush(catMoneda);

        // Get all the catMonedaList where moneda is not null
        defaultCatMonedaShouldBeFound("moneda.specified=true");

        // Get all the catMonedaList where moneda is null
        defaultCatMonedaShouldNotBeFound("moneda.specified=false");
    }
                @Test
    @Transactional
    public void getAllCatMonedasByMonedaContainsSomething() throws Exception {
        // Initialize the database
        catMonedaRepository.saveAndFlush(catMoneda);

        // Get all the catMonedaList where moneda contains DEFAULT_MONEDA
        defaultCatMonedaShouldBeFound("moneda.contains=" + DEFAULT_MONEDA);

        // Get all the catMonedaList where moneda contains UPDATED_MONEDA
        defaultCatMonedaShouldNotBeFound("moneda.contains=" + UPDATED_MONEDA);
    }

    @Test
    @Transactional
    public void getAllCatMonedasByMonedaNotContainsSomething() throws Exception {
        // Initialize the database
        catMonedaRepository.saveAndFlush(catMoneda);

        // Get all the catMonedaList where moneda does not contain DEFAULT_MONEDA
        defaultCatMonedaShouldNotBeFound("moneda.doesNotContain=" + DEFAULT_MONEDA);

        // Get all the catMonedaList where moneda does not contain UPDATED_MONEDA
        defaultCatMonedaShouldBeFound("moneda.doesNotContain=" + UPDATED_MONEDA);
    }


    @Test
    @Transactional
    public void getAllCatMonedasByUsuarioIsEqualToSomething() throws Exception {
        // Get already existing entity
        User usuario = catMoneda.getUsuario();
        catMonedaRepository.saveAndFlush(catMoneda);
        Long usuarioId = usuario.getId();

        // Get all the catMonedaList where usuario equals to usuarioId
        defaultCatMonedaShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the catMonedaList where usuario equals to usuarioId + 1
        defaultCatMonedaShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }


    @Test
    @Transactional
    public void getAllCatMonedasByPaisIsEqualToSomething() throws Exception {
        // Get already existing entity
        CatPais pais = catMoneda.getPais();
        catMonedaRepository.saveAndFlush(catMoneda);
        String paisId = pais.getId();

        // Get all the catMonedaList where pais equals to paisId
        defaultCatMonedaShouldBeFound("paisId.equals=" + paisId);

        // Get all the catMonedaList where pais equals to paisId + 1
        defaultCatMonedaShouldNotBeFound("paisId.equals=" + (paisId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCatMonedaShouldBeFound(String filter) throws Exception {
        restCatMonedaMockMvc.perform(get("/api/cat-monedas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catMoneda.getId())))
            .andExpect(jsonPath("$.[*].moneda").value(hasItem(DEFAULT_MONEDA)));

        // Check, that the count call also returns 1
        restCatMonedaMockMvc.perform(get("/api/cat-monedas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCatMonedaShouldNotBeFound(String filter) throws Exception {
        restCatMonedaMockMvc.perform(get("/api/cat-monedas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCatMonedaMockMvc.perform(get("/api/cat-monedas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCatMoneda() throws Exception {
        // Get the catMoneda
        restCatMonedaMockMvc.perform(get("/api/cat-monedas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCatMoneda() throws Exception {
        // Initialize the database
        catMonedaService.save(catMoneda);

        int databaseSizeBeforeUpdate = catMonedaRepository.findAll().size();

        // Update the catMoneda
        CatMoneda updatedCatMoneda = catMonedaRepository.findById(catMoneda.getId()).get();
        // Disconnect from session so that the updates on updatedCatMoneda are not directly saved in db
        em.detach(updatedCatMoneda);
        updatedCatMoneda
            .moneda(UPDATED_MONEDA);

        restCatMonedaMockMvc.perform(put("/api/cat-monedas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCatMoneda)))
            .andExpect(status().isOk());

        // Validate the CatMoneda in the database
        List<CatMoneda> catMonedaList = catMonedaRepository.findAll();
        assertThat(catMonedaList).hasSize(databaseSizeBeforeUpdate);
        CatMoneda testCatMoneda = catMonedaList.get(catMonedaList.size() - 1);
        assertThat(testCatMoneda.getMoneda()).isEqualTo(UPDATED_MONEDA);
    }

    @Test
    @Transactional
    public void updateNonExistingCatMoneda() throws Exception {
        int databaseSizeBeforeUpdate = catMonedaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatMonedaMockMvc.perform(put("/api/cat-monedas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catMoneda)))
            .andExpect(status().isBadRequest());

        // Validate the CatMoneda in the database
        List<CatMoneda> catMonedaList = catMonedaRepository.findAll();
        assertThat(catMonedaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCatMoneda() throws Exception {
        // Initialize the database
        catMonedaService.save(catMoneda);

        int databaseSizeBeforeDelete = catMonedaRepository.findAll().size();

        // Delete the catMoneda
        restCatMonedaMockMvc.perform(delete("/api/cat-monedas/{id}", catMoneda.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CatMoneda> catMonedaList = catMonedaRepository.findAll();
        assertThat(catMonedaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
