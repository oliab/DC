package com.resamx.web.rest;

import com.resamx.domain.CatLocalidad;
import com.resamx.domain.User;
import com.resamx.repository.UserRepository;
import com.resamx.security.SecurityUtils;
import com.resamx.service.CatLocalidadService;
import com.resamx.web.rest.errors.BadRequestAlertException;
import com.resamx.service.dto.CatLocalidadCriteria;
import com.resamx.service.CatLocalidadQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.resamx.domain.CatLocalidad}.
 */
@RestController
@RequestMapping("/api")
public class CatLocalidadResource {

    private final Logger log = LoggerFactory.getLogger(CatLocalidadResource.class);

    private static final String ENTITY_NAME = "catLocalidad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CatLocalidadService catLocalidadService;

    private final CatLocalidadQueryService catLocalidadQueryService;
    
    private final UserRepository userRepository;

    public CatLocalidadResource(CatLocalidadService catLocalidadService, CatLocalidadQueryService catLocalidadQueryService, UserRepository userRepository) {
        this.catLocalidadService = catLocalidadService;
        this.catLocalidadQueryService = catLocalidadQueryService;
        this.userRepository = userRepository;
    }

    /**
     * {@code POST  /cat-localidads} : Create a new catLocalidad.
     *
     * @param catLocalidad the catLocalidad to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new catLocalidad, or with status {@code 400 (Bad Request)} if the catLocalidad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cat-localidads")
    public ResponseEntity<CatLocalidad> createCatLocalidad(@Valid @RequestBody CatLocalidad catLocalidad) throws URISyntaxException {
        log.debug("REST request to save CatLocalidad : {}", catLocalidad);
        if (catLocalidad.getId() != null) {
            throw new BadRequestAlertException("A new catLocalidad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        String usrLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError"));
        Optional<User> user = userRepository.findOneByLogin(usrLogin);
        if (!user.isPresent()) {
            throw new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError");
        }
        catLocalidad.setUsuario(user.get());
        
        CatLocalidad result = catLocalidadService.save(catLocalidad);
        return ResponseEntity.created(new URI("/api/cat-localidads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cat-localidads} : Updates an existing catLocalidad.
     *
     * @param catLocalidad the catLocalidad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated catLocalidad,
     * or with status {@code 400 (Bad Request)} if the catLocalidad is not valid,
     * or with status {@code 500 (Internal Server Error)} if the catLocalidad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cat-localidads")
    public ResponseEntity<CatLocalidad> updateCatLocalidad(@Valid @RequestBody CatLocalidad catLocalidad) throws URISyntaxException {
        log.debug("REST request to update CatLocalidad : {}", catLocalidad);
        if (catLocalidad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        String usrLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError"));
        Optional<User> user = userRepository.findOneByLogin(usrLogin);
        if (!user.isPresent()) {
            throw new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError");
        }
        catLocalidad.setUsuario(user.get());
        
        
        CatLocalidad result = catLocalidadService.save(catLocalidad);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, catLocalidad.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cat-localidads} : get all the catLocalidads.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of catLocalidads in body.
     */
    @GetMapping("/cat-localidads")
    public ResponseEntity<List<CatLocalidad>> getAllCatLocalidads(CatLocalidadCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CatLocalidads by criteria: {}", criteria);
        Page<CatLocalidad> page = catLocalidadQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cat-localidads/count} : count all the catLocalidads.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cat-localidads/count")
    public ResponseEntity<Long> countCatLocalidads(CatLocalidadCriteria criteria) {
        log.debug("REST request to count CatLocalidads by criteria: {}", criteria);
        return ResponseEntity.ok().body(catLocalidadQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cat-localidads/:id} : get the "id" catLocalidad.
     *
     * @param id the id of the catLocalidad to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the catLocalidad, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cat-localidads/{id}")
    public ResponseEntity<CatLocalidad> getCatLocalidad(@PathVariable String id) {
        log.debug("REST request to get CatLocalidad : {}", id);
        Optional<CatLocalidad> catLocalidad = catLocalidadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(catLocalidad);
    }

    /**
     * {@code DELETE  /cat-localidads/:id} : delete the "id" catLocalidad.
     *
     * @param id the id of the catLocalidad to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cat-localidads/{id}")
    public ResponseEntity<Void> deleteCatLocalidad(@PathVariable String id) {
        log.debug("REST request to delete CatLocalidad : {}", id);
        catLocalidadService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
