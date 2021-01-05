package com.resamx.web.rest;

import com.resamx.domain.ExpedienteCliente;
import com.resamx.service.ExpedienteClienteService;
import com.resamx.web.rest.errors.BadRequestAlertException;
import com.resamx.service.dto.ExpedienteClienteCriteria;
import com.resamx.service.ExpedienteClienteQueryService;

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
 * REST controller for managing {@link com.resamx.domain.ExpedienteCliente}.
 */
@RestController
@RequestMapping("/api")
public class ExpedienteClienteResource {

    private final Logger log = LoggerFactory.getLogger(ExpedienteClienteResource.class);

    private static final String ENTITY_NAME = "expedienteCliente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExpedienteClienteService expedienteClienteService;

    private final ExpedienteClienteQueryService expedienteClienteQueryService;

    public ExpedienteClienteResource(ExpedienteClienteService expedienteClienteService, ExpedienteClienteQueryService expedienteClienteQueryService) {
        this.expedienteClienteService = expedienteClienteService;
        this.expedienteClienteQueryService = expedienteClienteQueryService;
    }

    /**
     * {@code POST  /expediente-clientes} : Create a new expedienteCliente.
     *
     * @param expedienteCliente the expedienteCliente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new expedienteCliente, or with status {@code 400 (Bad Request)} if the expedienteCliente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/expediente-clientes")
    public ResponseEntity<ExpedienteCliente> createExpedienteCliente(@Valid @RequestBody ExpedienteCliente expedienteCliente) throws URISyntaxException {
        log.debug("REST request to save ExpedienteCliente : {}", expedienteCliente);
        if (expedienteCliente.getId() != null) {
            throw new BadRequestAlertException("A new expedienteCliente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExpedienteCliente result = expedienteClienteService.save(expedienteCliente);
        return ResponseEntity.created(new URI("/api/expediente-clientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /expediente-clientes} : Updates an existing expedienteCliente.
     *
     * @param expedienteCliente the expedienteCliente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated expedienteCliente,
     * or with status {@code 400 (Bad Request)} if the expedienteCliente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the expedienteCliente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/expediente-clientes")
    public ResponseEntity<ExpedienteCliente> updateExpedienteCliente(@Valid @RequestBody ExpedienteCliente expedienteCliente) throws URISyntaxException {
        log.debug("REST request to update ExpedienteCliente : {}", expedienteCliente);
        if (expedienteCliente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExpedienteCliente result = expedienteClienteService.save(expedienteCliente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, expedienteCliente.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /expediente-clientes} : get all the expedienteClientes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of expedienteClientes in body.
     */
    @GetMapping("/expediente-clientes")
    public ResponseEntity<List<ExpedienteCliente>> getAllExpedienteClientes(ExpedienteClienteCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ExpedienteClientes by criteria: {}", criteria);
        Page<ExpedienteCliente> page = expedienteClienteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /expediente-clientes/count} : count all the expedienteClientes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/expediente-clientes/count")
    public ResponseEntity<Long> countExpedienteClientes(ExpedienteClienteCriteria criteria) {
        log.debug("REST request to count ExpedienteClientes by criteria: {}", criteria);
        return ResponseEntity.ok().body(expedienteClienteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /expediente-clientes/:id} : get the "id" expedienteCliente.
     *
     * @param id the id of the expedienteCliente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the expedienteCliente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/expediente-clientes/{id}")
    public ResponseEntity<ExpedienteCliente> getExpedienteCliente(@PathVariable Long id) {
        log.debug("REST request to get ExpedienteCliente : {}", id);
        Optional<ExpedienteCliente> expedienteCliente = expedienteClienteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(expedienteCliente);
    }

    /**
     * {@code DELETE  /expediente-clientes/:id} : delete the "id" expedienteCliente.
     *
     * @param id the id of the expedienteCliente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/expediente-clientes/{id}")
    public ResponseEntity<Void> deleteExpedienteCliente(@PathVariable Long id) {
        log.debug("REST request to delete ExpedienteCliente : {}", id);
        expedienteClienteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
