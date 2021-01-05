package com.resamx.web.rest;

import com.resamx.domain.CatEstado;
import com.resamx.domain.User;
import com.resamx.repository.UserRepository;
import com.resamx.security.SecurityUtils;
import com.resamx.service.CatEstadoService;
import com.resamx.web.rest.errors.BadRequestAlertException;
import com.resamx.service.dto.CatEstadoCriteria;
import com.resamx.service.CatEstadoQueryService;

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
 * REST controller for managing {@link com.resamx.domain.CatEstado}.
 */
@RestController
@RequestMapping("/api")
public class CatEstadoResource {

    private final Logger log = LoggerFactory.getLogger(CatEstadoResource.class);

    private static final String ENTITY_NAME = "catEstado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CatEstadoService catEstadoService;

    private final CatEstadoQueryService catEstadoQueryService;
    
    private final UserRepository userRepository;

    public CatEstadoResource(CatEstadoService catEstadoService, CatEstadoQueryService catEstadoQueryService, UserRepository userRepository) {
        this.catEstadoService = catEstadoService;
        this.catEstadoQueryService = catEstadoQueryService;
        this.userRepository = userRepository;
    }

    /**
     * {@code POST  /cat-estados} : Create a new catEstado.
     *
     * @param catEstado the catEstado to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new catEstado, or with status {@code 400 (Bad Request)} if the catEstado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cat-estados")
    public ResponseEntity<CatEstado> createCatEstado(@Valid @RequestBody CatEstado catEstado) throws URISyntaxException {
        log.debug("REST request to save CatEstado : {}", catEstado);
        if (catEstado.getId() != null) {
            throw new BadRequestAlertException("A new catEstado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        String usrLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError"));
        Optional<User> user = userRepository.findOneByLogin(usrLogin);
        if (!user.isPresent()) {
            throw new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError");
        }
        catEstado.setUsuario(user.get());
        
        CatEstado result = catEstadoService.save(catEstado);
        return ResponseEntity.created(new URI("/api/cat-estados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cat-estados} : Updates an existing catEstado.
     *
     * @param catEstado the catEstado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated catEstado,
     * or with status {@code 400 (Bad Request)} if the catEstado is not valid,
     * or with status {@code 500 (Internal Server Error)} if the catEstado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cat-estados")
    public ResponseEntity<CatEstado> updateCatEstado(@Valid @RequestBody CatEstado catEstado) throws URISyntaxException {
        log.debug("REST request to update CatEstado : {}", catEstado);
        if (catEstado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        String usrLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError"));
        Optional<User> user = userRepository.findOneByLogin(usrLogin);
        if (!user.isPresent()) {
            throw new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError");
        }
        catEstado.setUsuario(user.get());
        
        CatEstado result = catEstadoService.save(catEstado);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, catEstado.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cat-estados} : get all the catEstados.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of catEstados in body.
     */
    @GetMapping("/cat-estados")
    public ResponseEntity<List<CatEstado>> getAllCatEstados(CatEstadoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CatEstados by criteria: {}", criteria);
        Page<CatEstado> page = catEstadoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cat-estados/count} : count all the catEstados.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cat-estados/count")
    public ResponseEntity<Long> countCatEstados(CatEstadoCriteria criteria) {
        log.debug("REST request to count CatEstados by criteria: {}", criteria);
        return ResponseEntity.ok().body(catEstadoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cat-estados/:id} : get the "id" catEstado.
     *
     * @param id the id of the catEstado to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the catEstado, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cat-estados/{id}")
    public ResponseEntity<CatEstado> getCatEstado(@PathVariable String id) {
        log.debug("REST request to get CatEstado : {}", id);
        Optional<CatEstado> catEstado = catEstadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(catEstado);
    }

    /**
     * {@code DELETE  /cat-estados/:id} : delete the "id" catEstado.
     *
     * @param id the id of the catEstado to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cat-estados/{id}")
    public ResponseEntity<Void> deleteCatEstado(@PathVariable String id) {
        log.debug("REST request to delete CatEstado : {}", id);
        catEstadoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
