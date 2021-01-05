package com.resamx.web.rest;

import com.resamx.domain.DatosUsuario;
import com.resamx.service.DatosUsuarioService;
import com.resamx.web.rest.errors.BadRequestAlertException;
import com.resamx.service.dto.DatosUsuarioCriteria;
import com.resamx.service.DatosUsuarioQueryService;

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
 * REST controller for managing {@link com.resamx.domain.DatosUsuario}.
 */
@RestController
@RequestMapping("/api")
public class DatosUsuarioResource {

    private final Logger log = LoggerFactory.getLogger(DatosUsuarioResource.class);

    private static final String ENTITY_NAME = "datosUsuario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DatosUsuarioService datosUsuarioService;

    private final DatosUsuarioQueryService datosUsuarioQueryService;

    public DatosUsuarioResource(DatosUsuarioService datosUsuarioService, DatosUsuarioQueryService datosUsuarioQueryService) {
        this.datosUsuarioService = datosUsuarioService;
        this.datosUsuarioQueryService = datosUsuarioQueryService;
    }

    /**
     * {@code POST  /datos-usuarios} : Create a new datosUsuario.
     *
     * @param datosUsuario the datosUsuario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new datosUsuario, or with status {@code 400 (Bad Request)} if the datosUsuario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/datos-usuarios")
    public ResponseEntity<DatosUsuario> createDatosUsuario(@Valid @RequestBody DatosUsuario datosUsuario) throws URISyntaxException {
        log.debug("REST request to save DatosUsuario : {}", datosUsuario);
        if (datosUsuario.getId() != null) {
            throw new BadRequestAlertException("A new datosUsuario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DatosUsuario result = datosUsuarioService.save(datosUsuario);
        return ResponseEntity.created(new URI("/api/datos-usuarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /datos-usuarios} : Updates an existing datosUsuario.
     *
     * @param datosUsuario the datosUsuario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated datosUsuario,
     * or with status {@code 400 (Bad Request)} if the datosUsuario is not valid,
     * or with status {@code 500 (Internal Server Error)} if the datosUsuario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/datos-usuarios")
    public ResponseEntity<DatosUsuario> updateDatosUsuario(@Valid @RequestBody DatosUsuario datosUsuario) throws URISyntaxException {
        log.debug("REST request to update DatosUsuario : {}", datosUsuario);
        if (datosUsuario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DatosUsuario result = datosUsuarioService.save(datosUsuario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, datosUsuario.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /datos-usuarios} : get all the datosUsuarios.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of datosUsuarios in body.
     */
    @GetMapping("/datos-usuarios")
    public ResponseEntity<List<DatosUsuario>> getAllDatosUsuarios(DatosUsuarioCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DatosUsuarios by criteria: {}", criteria);
        Page<DatosUsuario> page = datosUsuarioQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /datos-usuarios/count} : count all the datosUsuarios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/datos-usuarios/count")
    public ResponseEntity<Long> countDatosUsuarios(DatosUsuarioCriteria criteria) {
        log.debug("REST request to count DatosUsuarios by criteria: {}", criteria);
        return ResponseEntity.ok().body(datosUsuarioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /datos-usuarios/:id} : get the "id" datosUsuario.
     *
     * @param id the id of the datosUsuario to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the datosUsuario, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/datos-usuarios/{id}")
    public ResponseEntity<DatosUsuario> getDatosUsuario(@PathVariable Long id) {
        log.debug("REST request to get DatosUsuario : {}", id);
        Optional<DatosUsuario> datosUsuario = datosUsuarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(datosUsuario);
    }

    /**
     * {@code DELETE  /datos-usuarios/:id} : delete the "id" datosUsuario.
     *
     * @param id the id of the datosUsuario to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/datos-usuarios/{id}")
    public ResponseEntity<Void> deleteDatosUsuario(@PathVariable Long id) {
        log.debug("REST request to delete DatosUsuario : {}", id);
        datosUsuarioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
