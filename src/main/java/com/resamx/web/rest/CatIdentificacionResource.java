package com.resamx.web.rest;

import com.resamx.domain.CatIdentificacion;
import com.resamx.service.CatIdentificacionService;
import com.resamx.web.rest.errors.BadRequestAlertException;
import com.resamx.service.dto.CatIdentificacionCriteria;
import com.resamx.service.CatIdentificacionQueryService;

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
 * REST controller for managing {@link com.resamx.domain.CatIdentificacion}.
 */
@RestController
@RequestMapping("/api")
public class CatIdentificacionResource {

    private final Logger log = LoggerFactory.getLogger(CatIdentificacionResource.class);

    private static final String ENTITY_NAME = "catIdentificacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CatIdentificacionService catIdentificacionService;

    private final CatIdentificacionQueryService catIdentificacionQueryService;

    public CatIdentificacionResource(CatIdentificacionService catIdentificacionService, CatIdentificacionQueryService catIdentificacionQueryService) {
        this.catIdentificacionService = catIdentificacionService;
        this.catIdentificacionQueryService = catIdentificacionQueryService;
    }

    /**
     * {@code POST  /cat-identificacions} : Create a new catIdentificacion.
     *
     * @param catIdentificacion the catIdentificacion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new catIdentificacion, or with status {@code 400 (Bad Request)} if the catIdentificacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cat-identificacions")
    public ResponseEntity<CatIdentificacion> createCatIdentificacion(@Valid @RequestBody CatIdentificacion catIdentificacion) throws URISyntaxException {
        log.debug("REST request to save CatIdentificacion : {}", catIdentificacion);
        if (catIdentificacion.getId() != null) {
            throw new BadRequestAlertException("A new catIdentificacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CatIdentificacion result = catIdentificacionService.save(catIdentificacion);
        return ResponseEntity.created(new URI("/api/cat-identificacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cat-identificacions} : Updates an existing catIdentificacion.
     *
     * @param catIdentificacion the catIdentificacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated catIdentificacion,
     * or with status {@code 400 (Bad Request)} if the catIdentificacion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the catIdentificacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cat-identificacions")
    public ResponseEntity<CatIdentificacion> updateCatIdentificacion(@Valid @RequestBody CatIdentificacion catIdentificacion) throws URISyntaxException {
        log.debug("REST request to update CatIdentificacion : {}", catIdentificacion);
        if (catIdentificacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CatIdentificacion result = catIdentificacionService.save(catIdentificacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, catIdentificacion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cat-identificacions} : get all the catIdentificacions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of catIdentificacions in body.
     */
    @GetMapping("/cat-identificacions")
    public ResponseEntity<List<CatIdentificacion>> getAllCatIdentificacions(CatIdentificacionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CatIdentificacions by criteria: {}", criteria);
        Page<CatIdentificacion> page = catIdentificacionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cat-identificacions/count} : count all the catIdentificacions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cat-identificacions/count")
    public ResponseEntity<Long> countCatIdentificacions(CatIdentificacionCriteria criteria) {
        log.debug("REST request to count CatIdentificacions by criteria: {}", criteria);
        return ResponseEntity.ok().body(catIdentificacionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cat-identificacions/:id} : get the "id" catIdentificacion.
     *
     * @param id the id of the catIdentificacion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the catIdentificacion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cat-identificacions/{id}")
    public ResponseEntity<CatIdentificacion> getCatIdentificacion(@PathVariable Long id) {
        log.debug("REST request to get CatIdentificacion : {}", id);
        Optional<CatIdentificacion> catIdentificacion = catIdentificacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(catIdentificacion);
    }

    /**
     * {@code DELETE  /cat-identificacions/:id} : delete the "id" catIdentificacion.
     *
     * @param id the id of the catIdentificacion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cat-identificacions/{id}")
    public ResponseEntity<Void> deleteCatIdentificacion(@PathVariable Long id) {
        log.debug("REST request to delete CatIdentificacion : {}", id);
        catIdentificacionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
