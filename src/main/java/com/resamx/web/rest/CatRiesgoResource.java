package com.resamx.web.rest;

import com.resamx.domain.CatRiesgo;
import com.resamx.domain.User;
import com.resamx.repository.UserRepository;
import com.resamx.security.SecurityUtils;
import com.resamx.service.CatRiesgoService;
import com.resamx.web.rest.errors.BadRequestAlertException;
import com.resamx.service.dto.CatRiesgoCriteria;
import com.resamx.service.CatRiesgoQueryService;

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
 * REST controller for managing {@link com.resamx.domain.CatRiesgo}.
 */
@RestController
@RequestMapping("/api")
public class CatRiesgoResource {

    private final Logger log = LoggerFactory.getLogger(CatRiesgoResource.class);

    private static final String ENTITY_NAME = "catRiesgo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CatRiesgoService catRiesgoService;

    private final CatRiesgoQueryService catRiesgoQueryService;
    
    private final UserRepository userRepository;

    public CatRiesgoResource(CatRiesgoService catRiesgoService, CatRiesgoQueryService catRiesgoQueryService, UserRepository userRepository) {
        this.catRiesgoService = catRiesgoService;
        this.catRiesgoQueryService = catRiesgoQueryService;
        this.userRepository = userRepository;
    }

    /**
     * {@code POST  /cat-riesgos} : Create a new catRiesgo.
     *
     * @param catRiesgo the catRiesgo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new catRiesgo, or with status {@code 400 (Bad Request)} if the catRiesgo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cat-riesgos")
    public ResponseEntity<CatRiesgo> createCatRiesgo(@Valid @RequestBody CatRiesgo catRiesgo) throws URISyntaxException {
        log.debug("REST request to save CatRiesgo : {}", catRiesgo);
        if (catRiesgo.getId() != null) {
            throw new BadRequestAlertException("A new catRiesgo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        String usrLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError"));
        Optional<User> user = userRepository.findOneByLogin(usrLogin);
        if (!user.isPresent()) {
            throw new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError");
        }
        catRiesgo.setUsuario(user.get());
        
        CatRiesgo result = catRiesgoService.save(catRiesgo);
        return ResponseEntity.created(new URI("/api/cat-riesgos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cat-riesgos} : Updates an existing catRiesgo.
     *
     * @param catRiesgo the catRiesgo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated catRiesgo,
     * or with status {@code 400 (Bad Request)} if the catRiesgo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the catRiesgo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cat-riesgos")
    public ResponseEntity<CatRiesgo> updateCatRiesgo(@Valid @RequestBody CatRiesgo catRiesgo) throws URISyntaxException {
        log.debug("REST request to update CatRiesgo : {}", catRiesgo);
        if (catRiesgo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        String usrLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError"));
        Optional<User> user = userRepository.findOneByLogin(usrLogin);
        if (!user.isPresent()) {
            throw new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError");
        }
        catRiesgo.setUsuario(user.get());
        
        CatRiesgo result = catRiesgoService.save(catRiesgo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, catRiesgo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cat-riesgos} : get all the catRiesgos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of catRiesgos in body.
     */
    @GetMapping("/cat-riesgos")
    public ResponseEntity<List<CatRiesgo>> getAllCatRiesgos(CatRiesgoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CatRiesgos by criteria: {}", criteria);
        Page<CatRiesgo> page = catRiesgoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cat-riesgos/count} : count all the catRiesgos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cat-riesgos/count")
    public ResponseEntity<Long> countCatRiesgos(CatRiesgoCriteria criteria) {
        log.debug("REST request to count CatRiesgos by criteria: {}", criteria);
        return ResponseEntity.ok().body(catRiesgoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cat-riesgos/:id} : get the "id" catRiesgo.
     *
     * @param id the id of the catRiesgo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the catRiesgo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cat-riesgos/{id}")
    public ResponseEntity<CatRiesgo> getCatRiesgo(@PathVariable Long id) {
        log.debug("REST request to get CatRiesgo : {}", id);
        Optional<CatRiesgo> catRiesgo = catRiesgoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(catRiesgo);
    }

    /**
     * {@code DELETE  /cat-riesgos/:id} : delete the "id" catRiesgo.
     *
     * @param id the id of the catRiesgo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cat-riesgos/{id}")
    public ResponseEntity<Void> deleteCatRiesgo(@PathVariable Long id) {
        log.debug("REST request to delete CatRiesgo : {}", id);
        catRiesgoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
