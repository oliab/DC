package com.resamx.web.rest;

import com.resamx.domain.CatTipoDocumento;
import com.resamx.service.CatTipoDocumentoService;
import com.resamx.web.rest.errors.BadRequestAlertException;
import com.resamx.service.dto.CatTipoDocumentoCriteria;
import com.resamx.service.CatTipoDocumentoQueryService;

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
 * REST controller for managing {@link com.resamx.domain.CatTipoDocumento}.
 */
@RestController
@RequestMapping("/api")
public class CatTipoDocumentoResource {

    private final Logger log = LoggerFactory.getLogger(CatTipoDocumentoResource.class);

    private static final String ENTITY_NAME = "catTipoDocumento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CatTipoDocumentoService catTipoDocumentoService;

    private final CatTipoDocumentoQueryService catTipoDocumentoQueryService;

    public CatTipoDocumentoResource(CatTipoDocumentoService catTipoDocumentoService, CatTipoDocumentoQueryService catTipoDocumentoQueryService) {
        this.catTipoDocumentoService = catTipoDocumentoService;
        this.catTipoDocumentoQueryService = catTipoDocumentoQueryService;
    }

    /**
     * {@code POST  /cat-tipo-documentos} : Create a new catTipoDocumento.
     *
     * @param catTipoDocumento the catTipoDocumento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new catTipoDocumento, or with status {@code 400 (Bad Request)} if the catTipoDocumento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cat-tipo-documentos")
    public ResponseEntity<CatTipoDocumento> createCatTipoDocumento(@Valid @RequestBody CatTipoDocumento catTipoDocumento) throws URISyntaxException {
        log.debug("REST request to save CatTipoDocumento : {}", catTipoDocumento);
        if (catTipoDocumento.getId() != null) {
            throw new BadRequestAlertException("A new catTipoDocumento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CatTipoDocumento result = catTipoDocumentoService.save(catTipoDocumento);
        return ResponseEntity.created(new URI("/api/cat-tipo-documentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cat-tipo-documentos} : Updates an existing catTipoDocumento.
     *
     * @param catTipoDocumento the catTipoDocumento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated catTipoDocumento,
     * or with status {@code 400 (Bad Request)} if the catTipoDocumento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the catTipoDocumento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cat-tipo-documentos")
    public ResponseEntity<CatTipoDocumento> updateCatTipoDocumento(@Valid @RequestBody CatTipoDocumento catTipoDocumento) throws URISyntaxException {
        log.debug("REST request to update CatTipoDocumento : {}", catTipoDocumento);
        if (catTipoDocumento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CatTipoDocumento result = catTipoDocumentoService.save(catTipoDocumento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, catTipoDocumento.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cat-tipo-documentos} : get all the catTipoDocumentos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of catTipoDocumentos in body.
     */
    @GetMapping("/cat-tipo-documentos")
    public ResponseEntity<List<CatTipoDocumento>> getAllCatTipoDocumentos(CatTipoDocumentoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CatTipoDocumentos by criteria: {}", criteria);
        Page<CatTipoDocumento> page = catTipoDocumentoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cat-tipo-documentos/count} : count all the catTipoDocumentos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cat-tipo-documentos/count")
    public ResponseEntity<Long> countCatTipoDocumentos(CatTipoDocumentoCriteria criteria) {
        log.debug("REST request to count CatTipoDocumentos by criteria: {}", criteria);
        return ResponseEntity.ok().body(catTipoDocumentoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cat-tipo-documentos/:id} : get the "id" catTipoDocumento.
     *
     * @param id the id of the catTipoDocumento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the catTipoDocumento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cat-tipo-documentos/{id}")
    public ResponseEntity<CatTipoDocumento> getCatTipoDocumento(@PathVariable Long id) {
        log.debug("REST request to get CatTipoDocumento : {}", id);
        Optional<CatTipoDocumento> catTipoDocumento = catTipoDocumentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(catTipoDocumento);
    }

    /**
     * {@code DELETE  /cat-tipo-documentos/:id} : delete the "id" catTipoDocumento.
     *
     * @param id the id of the catTipoDocumento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cat-tipo-documentos/{id}")
    public ResponseEntity<Void> deleteCatTipoDocumento(@PathVariable Long id) {
        log.debug("REST request to delete CatTipoDocumento : {}", id);
        catTipoDocumentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
