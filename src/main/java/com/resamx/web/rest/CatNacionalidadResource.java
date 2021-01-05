package com.resamx.web.rest;

import com.resamx.domain.CatNacionalidad;
import com.resamx.service.CatNacionalidadService;
import com.resamx.web.rest.errors.BadRequestAlertException;
import com.resamx.service.dto.CatNacionalidadCriteria;
import com.resamx.service.CatNacionalidadQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.resamx.domain.CatNacionalidad}.
 */
@RestController
@RequestMapping("/api")
public class CatNacionalidadResource {

    private final Logger log = LoggerFactory.getLogger(CatNacionalidadResource.class);

    private static final String ENTITY_NAME = "catNacionalidad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CatNacionalidadService catNacionalidadService;

    private final CatNacionalidadQueryService catNacionalidadQueryService;

    public CatNacionalidadResource(CatNacionalidadService catNacionalidadService, CatNacionalidadQueryService catNacionalidadQueryService) {
        this.catNacionalidadService = catNacionalidadService;
        this.catNacionalidadQueryService = catNacionalidadQueryService;
    }

    /**
     * {@code POST  /cat-nacionalidads} : Create a new catNacionalidad.
     *
     * @param catNacionalidad the catNacionalidad to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new catNacionalidad, or with status {@code 400 (Bad Request)} if the catNacionalidad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cat-nacionalidads")
    public ResponseEntity<CatNacionalidad> createCatNacionalidad(@Valid @RequestBody CatNacionalidad catNacionalidad) throws URISyntaxException {
        log.debug("REST request to save CatNacionalidad : {}", catNacionalidad);
        if (catNacionalidad.getId() != null) {
            throw new BadRequestAlertException("A new catNacionalidad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CatNacionalidad result = catNacionalidadService.save(catNacionalidad);
        return ResponseEntity.created(new URI("/api/cat-nacionalidads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cat-nacionalidads} : Updates an existing catNacionalidad.
     *
     * @param catNacionalidad the catNacionalidad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated catNacionalidad,
     * or with status {@code 400 (Bad Request)} if the catNacionalidad is not valid,
     * or with status {@code 500 (Internal Server Error)} if the catNacionalidad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cat-nacionalidads")
    public ResponseEntity<CatNacionalidad> updateCatNacionalidad(@Valid @RequestBody CatNacionalidad catNacionalidad) throws URISyntaxException {
        log.debug("REST request to update CatNacionalidad : {}", catNacionalidad);
        if (catNacionalidad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CatNacionalidad result = catNacionalidadService.save(catNacionalidad);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, catNacionalidad.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cat-nacionalidads} : get all the catNacionalidads.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of catNacionalidads in body.
     */
    @GetMapping("/cat-nacionalidads")
    public ResponseEntity<List<CatNacionalidad>> getAllCatNacionalidads(CatNacionalidadCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CatNacionalidads by criteria: {}", criteria);
        Page<CatNacionalidad> page = catNacionalidadQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cat-nacionalidads/count} : count all the catNacionalidads.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cat-nacionalidads/count")
    public ResponseEntity<Long> countCatNacionalidads(CatNacionalidadCriteria criteria) {
        log.debug("REST request to count CatNacionalidads by criteria: {}", criteria);
        return ResponseEntity.ok().body(catNacionalidadQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cat-nacionalidads/:id} : get the "id" catNacionalidad.
     *
     * @param id the id of the catNacionalidad to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the catNacionalidad, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cat-nacionalidads/{id}")
    public ResponseEntity<CatNacionalidad> getCatNacionalidad(@PathVariable Long id) {
        log.debug("REST request to get CatNacionalidad : {}", id);
        Optional<CatNacionalidad> catNacionalidad = catNacionalidadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(catNacionalidad);
    }

    /**
     * {@code DELETE  /cat-nacionalidads/:id} : delete the "id" catNacionalidad.
     *
     * @param id the id of the catNacionalidad to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cat-nacionalidads/{id}")
    public ResponseEntity<Void> deleteCatNacionalidad(@PathVariable Long id) {
        log.debug("REST request to delete CatNacionalidad : {}", id);
        catNacionalidadService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
