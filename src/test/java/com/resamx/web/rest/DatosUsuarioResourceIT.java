package com.resamx.web.rest;

import com.resamx.ResaMxWebApp;
import com.resamx.domain.DatosUsuario;
import com.resamx.domain.CatSucursal;
import com.resamx.domain.User;
import com.resamx.repository.DatosUsuarioRepository;
import com.resamx.service.DatosUsuarioService;
import com.resamx.service.dto.DatosUsuarioCriteria;
import com.resamx.service.DatosUsuarioQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DatosUsuarioResource} REST controller.
 */
@SpringBootTest(classes = ResaMxWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DatosUsuarioResourceIT {

    private static final String DEFAULT_PUESTO = "AAAAAAAAAA";
    private static final String UPDATED_PUESTO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_ACT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ACT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_ACT = LocalDate.ofEpochDay(-1L);

    @Autowired
    private DatosUsuarioRepository datosUsuarioRepository;

    @Autowired
    private DatosUsuarioService datosUsuarioService;

    @Autowired
    private DatosUsuarioQueryService datosUsuarioQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDatosUsuarioMockMvc;

    private DatosUsuario datosUsuario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DatosUsuario createEntity(EntityManager em) {
        DatosUsuario datosUsuario = new DatosUsuario()
            .puesto(DEFAULT_PUESTO)
            .fechaAct(DEFAULT_FECHA_ACT);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        datosUsuario.setUser(user);
        return datosUsuario;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DatosUsuario createUpdatedEntity(EntityManager em) {
        DatosUsuario datosUsuario = new DatosUsuario()
            .puesto(UPDATED_PUESTO)
            .fechaAct(UPDATED_FECHA_ACT);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        datosUsuario.setUser(user);
        return datosUsuario;
    }

    @BeforeEach
    public void initTest() {
        datosUsuario = createEntity(em);
    }

    @Test
    @Transactional
    public void createDatosUsuario() throws Exception {
        int databaseSizeBeforeCreate = datosUsuarioRepository.findAll().size();
        // Create the DatosUsuario
        restDatosUsuarioMockMvc.perform(post("/api/datos-usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(datosUsuario)))
            .andExpect(status().isCreated());

        // Validate the DatosUsuario in the database
        List<DatosUsuario> datosUsuarioList = datosUsuarioRepository.findAll();
        assertThat(datosUsuarioList).hasSize(databaseSizeBeforeCreate + 1);
        DatosUsuario testDatosUsuario = datosUsuarioList.get(datosUsuarioList.size() - 1);
        assertThat(testDatosUsuario.getPuesto()).isEqualTo(DEFAULT_PUESTO);
        assertThat(testDatosUsuario.getFechaAct()).isEqualTo(DEFAULT_FECHA_ACT);
    }

    @Test
    @Transactional
    public void createDatosUsuarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = datosUsuarioRepository.findAll().size();

        // Create the DatosUsuario with an existing ID
        datosUsuario.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDatosUsuarioMockMvc.perform(post("/api/datos-usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(datosUsuario)))
            .andExpect(status().isBadRequest());

        // Validate the DatosUsuario in the database
        List<DatosUsuario> datosUsuarioList = datosUsuarioRepository.findAll();
        assertThat(datosUsuarioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPuestoIsRequired() throws Exception {
        int databaseSizeBeforeTest = datosUsuarioRepository.findAll().size();
        // set the field null
        datosUsuario.setPuesto(null);

        // Create the DatosUsuario, which fails.


        restDatosUsuarioMockMvc.perform(post("/api/datos-usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(datosUsuario)))
            .andExpect(status().isBadRequest());

        List<DatosUsuario> datosUsuarioList = datosUsuarioRepository.findAll();
        assertThat(datosUsuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDatosUsuarios() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList
        restDatosUsuarioMockMvc.perform(get("/api/datos-usuarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(datosUsuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].puesto").value(hasItem(DEFAULT_PUESTO)))
            .andExpect(jsonPath("$.[*].fechaAct").value(hasItem(DEFAULT_FECHA_ACT.toString())));
    }
    
    @Test
    @Transactional
    public void getDatosUsuario() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get the datosUsuario
        restDatosUsuarioMockMvc.perform(get("/api/datos-usuarios/{id}", datosUsuario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(datosUsuario.getId().intValue()))
            .andExpect(jsonPath("$.puesto").value(DEFAULT_PUESTO))
            .andExpect(jsonPath("$.fechaAct").value(DEFAULT_FECHA_ACT.toString()));
    }


    @Test
    @Transactional
    public void getDatosUsuariosByIdFiltering() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        Long id = datosUsuario.getId();

        defaultDatosUsuarioShouldBeFound("id.equals=" + id);
        defaultDatosUsuarioShouldNotBeFound("id.notEquals=" + id);

        defaultDatosUsuarioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDatosUsuarioShouldNotBeFound("id.greaterThan=" + id);

        defaultDatosUsuarioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDatosUsuarioShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDatosUsuariosByPuestoIsEqualToSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where puesto equals to DEFAULT_PUESTO
        defaultDatosUsuarioShouldBeFound("puesto.equals=" + DEFAULT_PUESTO);

        // Get all the datosUsuarioList where puesto equals to UPDATED_PUESTO
        defaultDatosUsuarioShouldNotBeFound("puesto.equals=" + UPDATED_PUESTO);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByPuestoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where puesto not equals to DEFAULT_PUESTO
        defaultDatosUsuarioShouldNotBeFound("puesto.notEquals=" + DEFAULT_PUESTO);

        // Get all the datosUsuarioList where puesto not equals to UPDATED_PUESTO
        defaultDatosUsuarioShouldBeFound("puesto.notEquals=" + UPDATED_PUESTO);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByPuestoIsInShouldWork() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where puesto in DEFAULT_PUESTO or UPDATED_PUESTO
        defaultDatosUsuarioShouldBeFound("puesto.in=" + DEFAULT_PUESTO + "," + UPDATED_PUESTO);

        // Get all the datosUsuarioList where puesto equals to UPDATED_PUESTO
        defaultDatosUsuarioShouldNotBeFound("puesto.in=" + UPDATED_PUESTO);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByPuestoIsNullOrNotNull() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where puesto is not null
        defaultDatosUsuarioShouldBeFound("puesto.specified=true");

        // Get all the datosUsuarioList where puesto is null
        defaultDatosUsuarioShouldNotBeFound("puesto.specified=false");
    }
                @Test
    @Transactional
    public void getAllDatosUsuariosByPuestoContainsSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where puesto contains DEFAULT_PUESTO
        defaultDatosUsuarioShouldBeFound("puesto.contains=" + DEFAULT_PUESTO);

        // Get all the datosUsuarioList where puesto contains UPDATED_PUESTO
        defaultDatosUsuarioShouldNotBeFound("puesto.contains=" + UPDATED_PUESTO);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByPuestoNotContainsSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where puesto does not contain DEFAULT_PUESTO
        defaultDatosUsuarioShouldNotBeFound("puesto.doesNotContain=" + DEFAULT_PUESTO);

        // Get all the datosUsuarioList where puesto does not contain UPDATED_PUESTO
        defaultDatosUsuarioShouldBeFound("puesto.doesNotContain=" + UPDATED_PUESTO);
    }


    @Test
    @Transactional
    public void getAllDatosUsuariosByFechaActIsEqualToSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where fechaAct equals to DEFAULT_FECHA_ACT
        defaultDatosUsuarioShouldBeFound("fechaAct.equals=" + DEFAULT_FECHA_ACT);

        // Get all the datosUsuarioList where fechaAct equals to UPDATED_FECHA_ACT
        defaultDatosUsuarioShouldNotBeFound("fechaAct.equals=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByFechaActIsNotEqualToSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where fechaAct not equals to DEFAULT_FECHA_ACT
        defaultDatosUsuarioShouldNotBeFound("fechaAct.notEquals=" + DEFAULT_FECHA_ACT);

        // Get all the datosUsuarioList where fechaAct not equals to UPDATED_FECHA_ACT
        defaultDatosUsuarioShouldBeFound("fechaAct.notEquals=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByFechaActIsInShouldWork() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where fechaAct in DEFAULT_FECHA_ACT or UPDATED_FECHA_ACT
        defaultDatosUsuarioShouldBeFound("fechaAct.in=" + DEFAULT_FECHA_ACT + "," + UPDATED_FECHA_ACT);

        // Get all the datosUsuarioList where fechaAct equals to UPDATED_FECHA_ACT
        defaultDatosUsuarioShouldNotBeFound("fechaAct.in=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByFechaActIsNullOrNotNull() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where fechaAct is not null
        defaultDatosUsuarioShouldBeFound("fechaAct.specified=true");

        // Get all the datosUsuarioList where fechaAct is null
        defaultDatosUsuarioShouldNotBeFound("fechaAct.specified=false");
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByFechaActIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where fechaAct is greater than or equal to DEFAULT_FECHA_ACT
        defaultDatosUsuarioShouldBeFound("fechaAct.greaterThanOrEqual=" + DEFAULT_FECHA_ACT);

        // Get all the datosUsuarioList where fechaAct is greater than or equal to UPDATED_FECHA_ACT
        defaultDatosUsuarioShouldNotBeFound("fechaAct.greaterThanOrEqual=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByFechaActIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where fechaAct is less than or equal to DEFAULT_FECHA_ACT
        defaultDatosUsuarioShouldBeFound("fechaAct.lessThanOrEqual=" + DEFAULT_FECHA_ACT);

        // Get all the datosUsuarioList where fechaAct is less than or equal to SMALLER_FECHA_ACT
        defaultDatosUsuarioShouldNotBeFound("fechaAct.lessThanOrEqual=" + SMALLER_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByFechaActIsLessThanSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where fechaAct is less than DEFAULT_FECHA_ACT
        defaultDatosUsuarioShouldNotBeFound("fechaAct.lessThan=" + DEFAULT_FECHA_ACT);

        // Get all the datosUsuarioList where fechaAct is less than UPDATED_FECHA_ACT
        defaultDatosUsuarioShouldBeFound("fechaAct.lessThan=" + UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByFechaActIsGreaterThanSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where fechaAct is greater than DEFAULT_FECHA_ACT
        defaultDatosUsuarioShouldNotBeFound("fechaAct.greaterThan=" + DEFAULT_FECHA_ACT);

        // Get all the datosUsuarioList where fechaAct is greater than SMALLER_FECHA_ACT
        defaultDatosUsuarioShouldBeFound("fechaAct.greaterThan=" + SMALLER_FECHA_ACT);
    }


    @Test
    @Transactional
    public void getAllDatosUsuariosBySucursalIsEqualToSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);
        CatSucursal sucursal = CatSucursalResourceIT.createEntity(em);
        em.persist(sucursal);
        em.flush();
        datosUsuario.setSucursal(sucursal);
        datosUsuarioRepository.saveAndFlush(datosUsuario);
        Long sucursalId = sucursal.getId();

        // Get all the datosUsuarioList where sucursal equals to sucursalId
        defaultDatosUsuarioShouldBeFound("sucursalId.equals=" + sucursalId);

        // Get all the datosUsuarioList where sucursal equals to sucursalId + 1
        defaultDatosUsuarioShouldNotBeFound("sucursalId.equals=" + (sucursalId + 1));
    }


    @Test
    @Transactional
    public void getAllDatosUsuariosByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = datosUsuario.getUser();
        datosUsuarioRepository.saveAndFlush(datosUsuario);
        Long userId = user.getId();

        // Get all the datosUsuarioList where user equals to userId
        defaultDatosUsuarioShouldBeFound("userId.equals=" + userId);

        // Get all the datosUsuarioList where user equals to userId + 1
        defaultDatosUsuarioShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDatosUsuarioShouldBeFound(String filter) throws Exception {
        restDatosUsuarioMockMvc.perform(get("/api/datos-usuarios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(datosUsuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].puesto").value(hasItem(DEFAULT_PUESTO)))
            .andExpect(jsonPath("$.[*].fechaAct").value(hasItem(DEFAULT_FECHA_ACT.toString())));

        // Check, that the count call also returns 1
        restDatosUsuarioMockMvc.perform(get("/api/datos-usuarios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDatosUsuarioShouldNotBeFound(String filter) throws Exception {
        restDatosUsuarioMockMvc.perform(get("/api/datos-usuarios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDatosUsuarioMockMvc.perform(get("/api/datos-usuarios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDatosUsuario() throws Exception {
        // Get the datosUsuario
        restDatosUsuarioMockMvc.perform(get("/api/datos-usuarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDatosUsuario() throws Exception {
        // Initialize the database
        datosUsuarioService.save(datosUsuario);

        int databaseSizeBeforeUpdate = datosUsuarioRepository.findAll().size();

        // Update the datosUsuario
        DatosUsuario updatedDatosUsuario = datosUsuarioRepository.findById(datosUsuario.getId()).get();
        // Disconnect from session so that the updates on updatedDatosUsuario are not directly saved in db
        em.detach(updatedDatosUsuario);
        updatedDatosUsuario
            .puesto(UPDATED_PUESTO)
            .fechaAct(UPDATED_FECHA_ACT);

        restDatosUsuarioMockMvc.perform(put("/api/datos-usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDatosUsuario)))
            .andExpect(status().isOk());

        // Validate the DatosUsuario in the database
        List<DatosUsuario> datosUsuarioList = datosUsuarioRepository.findAll();
        assertThat(datosUsuarioList).hasSize(databaseSizeBeforeUpdate);
        DatosUsuario testDatosUsuario = datosUsuarioList.get(datosUsuarioList.size() - 1);
        assertThat(testDatosUsuario.getPuesto()).isEqualTo(UPDATED_PUESTO);
        assertThat(testDatosUsuario.getFechaAct()).isEqualTo(UPDATED_FECHA_ACT);
    }

    @Test
    @Transactional
    public void updateNonExistingDatosUsuario() throws Exception {
        int databaseSizeBeforeUpdate = datosUsuarioRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDatosUsuarioMockMvc.perform(put("/api/datos-usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(datosUsuario)))
            .andExpect(status().isBadRequest());

        // Validate the DatosUsuario in the database
        List<DatosUsuario> datosUsuarioList = datosUsuarioRepository.findAll();
        assertThat(datosUsuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDatosUsuario() throws Exception {
        // Initialize the database
        datosUsuarioService.save(datosUsuario);

        int databaseSizeBeforeDelete = datosUsuarioRepository.findAll().size();

        // Delete the datosUsuario
        restDatosUsuarioMockMvc.perform(delete("/api/datos-usuarios/{id}", datosUsuario.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DatosUsuario> datosUsuarioList = datosUsuarioRepository.findAll();
        assertThat(datosUsuarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
