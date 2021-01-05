package com.resamx.web.rest;

import com.resamx.domain.CatTipoOperacion;
import com.resamx.domain.User;
import com.resamx.repository.UserRepository;
import com.resamx.security.SecurityUtils;
import com.resamx.service.CatTipoOperacionService;
import com.resamx.web.rest.errors.BadRequestAlertException;
import com.resamx.service.dto.CatTipoOperacionCriteria;
import com.resamx.service.CatTipoOperacionQueryService;

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
 * REST controller for managing {@link com.resamx.domain.CatTipoOperacion}.
 */
@RestController
@RequestMapping("/api")
public class CatTipoOperacionResource {

    private final Logger log = LoggerFactory.getLogger(CatTipoOperacionResource.class);

    private static final String ENTITY_NAME = "catTipoOperacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CatTipoOperacionService catTipoOperacionService;

    private final CatTipoOperacionQueryService catTipoOperacionQueryService;
    
    private final UserRepository userRepository;

    public CatTipoOperacionResource(CatTipoOperacionService catTipoOperacionService, CatTipoOperacionQueryService catTipoOperacionQueryService, UserRepository userRepository) {
        this.catTipoOperacionService = catTipoOperacionService;
        this.catTipoOperacionQueryService = catTipoOperacionQueryService;
        this.userRepository = userRepository;
    }

    /**
     * {@code POST  /cat-tipo-operacions} : Create a new catTipoOperacion.
     *
     * @param catTipoOperacion the catTipoOperacion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new catTipoOperacion, or with status {@code 400 (Bad Request)} if the catTipoOperacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cat-tipo-operacions")
    public ResponseEntity<CatTipoOperacion> createCatTipoOperacion(@Valid @RequestBody CatTipoOperacion catTipoOperacion) throws URISyntaxException {
        log.debug("REST request to save CatTipoOperacion : {}", catTipoOperacion);
        if (catTipoOperacion.getId() != null) {
            throw new BadRequestAlertException("A new catTipoOperacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        String usrLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError"));
        Optional<User> user = userRepository.findOneByLogin(usrLogin);
        if (!user.isPresent()) {
            throw new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError");
        }
        catTipoOperacion.setUsuario(user.get());
        
        CatTipoOperacion result = catTipoOperacionService.save(catTipoOperacion);
        return ResponseEntity.created(new URI("/api/cat-tipo-operacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cat-tipo-operacions} : Updates an existing catTipoOperacion.
     *
     * @param catTipoOperacion the catTipoOperacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated catTipoOperacion,
     * or with status {@code 400 (Bad Request)} if the catTipoOperacion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the catTipoOperacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cat-tipo-operacions")
    public ResponseEntity<CatTipoOperacion> updateCatTipoOperacion(@Valid @RequestBody CatTipoOperacion catTipoOperacion) throws URISyntaxException {
        log.debug("REST request to update CatTipoOperacion : {}", catTipoOperacion);
        if (catTipoOperacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        String usrLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError"));
        Optional<User> user = userRepository.findOneByLogin(usrLogin);
        if (!user.isPresent()) {
            throw new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError");
        }
        catTipoOperacion.setUsuario(user.get());
        
        
        CatTipoOperacion result = catTipoOperacionService.save(catTipoOperacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, catTipoOperacion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cat-tipo-operacions} : get all the catTipoOperacions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of catTipoOperacions in body.
     */
    @GetMapping("/cat-tipo-operacions")
    public ResponseEntity<List<CatTipoOperacion>> getAllCatTipoOperacions(CatTipoOperacionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CatTipoOperacions by criteria: {}", criteria);
        Page<CatTipoOperacion> page = catTipoOperacionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cat-tipo-operacions/count} : count all the catTipoOperacions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cat-tipo-operacions/count")
    public ResponseEntity<Long> countCatTipoOperacions(CatTipoOperacionCriteria criteria) {
        log.debug("REST request to count CatTipoOperacions by criteria: {}", criteria);
        return ResponseEntity.ok().body(catTipoOperacionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cat-tipo-operacions/:id} : get the "id" catTipoOperacion.
     *
     * @param id the id of the catTipoOperacion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the catTipoOperacion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cat-tipo-operacions/{id}")
    public ResponseEntity<CatTipoOperacion> getCatTipoOperacion(@PathVariable Long id) {
        log.debug("REST request to get CatTipoOperacion : {}", id);
        Optional<CatTipoOperacion> catTipoOperacion = catTipoOperacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(catTipoOperacion);
    }

    /**
     * {@code DELETE  /cat-tipo-operacions/:id} : delete the "id" catTipoOperacion.
     *
     * @param id the id of the catTipoOperacion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cat-tipo-operacions/{id}")
    public ResponseEntity<Void> deleteCatTipoOperacion(@PathVariable Long id) {
        log.debug("REST request to delete CatTipoOperacion : {}", id);
        catTipoOperacionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
