package com.resamx.web.rest;

import com.resamx.domain.DomicilioUsuario;
import com.resamx.service.DomicilioUsuarioService;
import com.resamx.web.rest.errors.BadRequestAlertException;
import com.resamx.service.dto.DomicilioUsuarioCriteria;
import com.resamx.service.DomicilioUsuarioQueryService;

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
 * REST controller for managing {@link com.resamx.domain.DomicilioUsuario}.
 */
@RestController
@RequestMapping("/api")
public class DomicilioUsuarioResource {

    private final Logger log = LoggerFactory.getLogger(DomicilioUsuarioResource.class);

    private static final String ENTITY_NAME = "domicilioUsuario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DomicilioUsuarioService domicilioUsuarioService;

    private final DomicilioUsuarioQueryService domicilioUsuarioQueryService;

    public DomicilioUsuarioResource(DomicilioUsuarioService domicilioUsuarioService, DomicilioUsuarioQueryService domicilioUsuarioQueryService) {
        this.domicilioUsuarioService = domicilioUsuarioService;
        this.domicilioUsuarioQueryService = domicilioUsuarioQueryService;
    }

    /**
     * {@code POST  /domicilio-usuarios} : Create a new domicilioUsuario.
     *
     * @param domicilioUsuario the domicilioUsuario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new domicilioUsuario, or with status {@code 400 (Bad Request)} if the domicilioUsuario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/domicilio-usuarios")
    public ResponseEntity<DomicilioUsuario> createDomicilioUsuario(@Valid @RequestBody DomicilioUsuario domicilioUsuario) throws URISyntaxException {
        log.debug("REST request to save DomicilioUsuario : {}", domicilioUsuario);
        if (domicilioUsuario.getId() != null) {
            throw new BadRequestAlertException("A new domicilioUsuario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DomicilioUsuario result = domicilioUsuarioService.save(domicilioUsuario);
        return ResponseEntity.created(new URI("/api/domicilio-usuarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /domicilio-usuarios} : Updates an existing domicilioUsuario.
     *
     * @param domicilioUsuario the domicilioUsuario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated domicilioUsuario,
     * or with status {@code 400 (Bad Request)} if the domicilioUsuario is not valid,
     * or with status {@code 500 (Internal Server Error)} if the domicilioUsuario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/domicilio-usuarios")
    public ResponseEntity<DomicilioUsuario> updateDomicilioUsuario(@Valid @RequestBody DomicilioUsuario domicilioUsuario) throws URISyntaxException {
        log.debug("REST request to update DomicilioUsuario : {}", domicilioUsuario);
        if (domicilioUsuario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DomicilioUsuario result = domicilioUsuarioService.save(domicilioUsuario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, domicilioUsuario.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /domicilio-usuarios} : get all the domicilioUsuarios.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of domicilioUsuarios in body.
     */
    @GetMapping("/domicilio-usuarios")
    public ResponseEntity<List<DomicilioUsuario>> getAllDomicilioUsuarios(DomicilioUsuarioCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DomicilioUsuarios by criteria: {}", criteria);
        Page<DomicilioUsuario> page = domicilioUsuarioQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /domicilio-usuarios/count} : count all the domicilioUsuarios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/domicilio-usuarios/count")
    public ResponseEntity<Long> countDomicilioUsuarios(DomicilioUsuarioCriteria criteria) {
        log.debug("REST request to count DomicilioUsuarios by criteria: {}", criteria);
        return ResponseEntity.ok().body(domicilioUsuarioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /domicilio-usuarios/:id} : get the "id" domicilioUsuario.
     *
     * @param id the id of the domicilioUsuario to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the domicilioUsuario, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/domicilio-usuarios/{id}")
    public ResponseEntity<DomicilioUsuario> getDomicilioUsuario(@PathVariable Long id) {
        log.debug("REST request to get DomicilioUsuario : {}", id);
        Optional<DomicilioUsuario> domicilioUsuario = domicilioUsuarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(domicilioUsuario);
    }

    /**
     * {@code DELETE  /domicilio-usuarios/:id} : delete the "id" domicilioUsuario.
     *
     * @param id the id of the domicilioUsuario to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/domicilio-usuarios/{id}")
    public ResponseEntity<Void> deleteDomicilioUsuario(@PathVariable Long id) {
        log.debug("REST request to delete DomicilioUsuario : {}", id);
        domicilioUsuarioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
