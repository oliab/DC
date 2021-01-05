package com.resamx.web.rest;

import com.resamx.domain.CatTipoEmpresa;
import com.resamx.service.CatTipoEmpresaService;
import com.resamx.web.rest.errors.BadRequestAlertException;
import com.resamx.service.dto.CatTipoEmpresaCriteria;
import com.resamx.service.CatTipoEmpresaQueryService;

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
 * REST controller for managing {@link com.resamx.domain.CatTipoEmpresa}.
 */
@RestController
@RequestMapping("/api")
public class CatTipoEmpresaResource {

    private final Logger log = LoggerFactory.getLogger(CatTipoEmpresaResource.class);

    private static final String ENTITY_NAME = "catTipoEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CatTipoEmpresaService catTipoEmpresaService;

    private final CatTipoEmpresaQueryService catTipoEmpresaQueryService;

    public CatTipoEmpresaResource(CatTipoEmpresaService catTipoEmpresaService, CatTipoEmpresaQueryService catTipoEmpresaQueryService) {
        this.catTipoEmpresaService = catTipoEmpresaService;
        this.catTipoEmpresaQueryService = catTipoEmpresaQueryService;
    }

    /**
     * {@code POST  /cat-tipo-empresas} : Create a new catTipoEmpresa.
     *
     * @param catTipoEmpresa the catTipoEmpresa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new catTipoEmpresa, or with status {@code 400 (Bad Request)} if the catTipoEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cat-tipo-empresas")
    public ResponseEntity<CatTipoEmpresa> createCatTipoEmpresa(@Valid @RequestBody CatTipoEmpresa catTipoEmpresa) throws URISyntaxException {
        log.debug("REST request to save CatTipoEmpresa : {}", catTipoEmpresa);
        if (catTipoEmpresa.getId() != null) {
            throw new BadRequestAlertException("A new catTipoEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CatTipoEmpresa result = catTipoEmpresaService.save(catTipoEmpresa);
        return ResponseEntity.created(new URI("/api/cat-tipo-empresas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cat-tipo-empresas} : Updates an existing catTipoEmpresa.
     *
     * @param catTipoEmpresa the catTipoEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated catTipoEmpresa,
     * or with status {@code 400 (Bad Request)} if the catTipoEmpresa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the catTipoEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cat-tipo-empresas")
    public ResponseEntity<CatTipoEmpresa> updateCatTipoEmpresa(@Valid @RequestBody CatTipoEmpresa catTipoEmpresa) throws URISyntaxException {
        log.debug("REST request to update CatTipoEmpresa : {}", catTipoEmpresa);
        if (catTipoEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CatTipoEmpresa result = catTipoEmpresaService.save(catTipoEmpresa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, catTipoEmpresa.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cat-tipo-empresas} : get all the catTipoEmpresas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of catTipoEmpresas in body.
     */
    @GetMapping("/cat-tipo-empresas")
    public ResponseEntity<List<CatTipoEmpresa>> getAllCatTipoEmpresas(CatTipoEmpresaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CatTipoEmpresas by criteria: {}", criteria);
        Page<CatTipoEmpresa> page = catTipoEmpresaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cat-tipo-empresas/count} : count all the catTipoEmpresas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cat-tipo-empresas/count")
    public ResponseEntity<Long> countCatTipoEmpresas(CatTipoEmpresaCriteria criteria) {
        log.debug("REST request to count CatTipoEmpresas by criteria: {}", criteria);
        return ResponseEntity.ok().body(catTipoEmpresaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cat-tipo-empresas/:id} : get the "id" catTipoEmpresa.
     *
     * @param id the id of the catTipoEmpresa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the catTipoEmpresa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cat-tipo-empresas/{id}")
    public ResponseEntity<CatTipoEmpresa> getCatTipoEmpresa(@PathVariable Long id) {
        log.debug("REST request to get CatTipoEmpresa : {}", id);
        Optional<CatTipoEmpresa> catTipoEmpresa = catTipoEmpresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(catTipoEmpresa);
    }

    /**
     * {@code DELETE  /cat-tipo-empresas/:id} : delete the "id" catTipoEmpresa.
     *
     * @param id the id of the catTipoEmpresa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cat-tipo-empresas/{id}")
    public ResponseEntity<Void> deleteCatTipoEmpresa(@PathVariable Long id) {
        log.debug("REST request to delete CatTipoEmpresa : {}", id);
        catTipoEmpresaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
