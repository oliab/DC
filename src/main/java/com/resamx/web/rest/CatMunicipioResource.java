package com.resamx.web.rest;

import com.resamx.domain.CatMunicipio;
import com.resamx.domain.User;
import com.resamx.repository.UserRepository;
import com.resamx.security.SecurityUtils;
import com.resamx.service.CatMunicipioService;
import com.resamx.web.rest.errors.BadRequestAlertException;
import com.resamx.service.dto.CatMunicipioCriteria;
import com.resamx.service.CatMunicipioQueryService;

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
 * REST controller for managing {@link com.resamx.domain.CatMunicipio}.
 */
@RestController
@RequestMapping("/api")
public class CatMunicipioResource {

    private final Logger log = LoggerFactory.getLogger(CatMunicipioResource.class);

    private static final String ENTITY_NAME = "catMunicipio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CatMunicipioService catMunicipioService;

    private final CatMunicipioQueryService catMunicipioQueryService;
    
    private final UserRepository userRepository;

    public CatMunicipioResource(CatMunicipioService catMunicipioService, CatMunicipioQueryService catMunicipioQueryService, UserRepository userRepository) {
        this.catMunicipioService = catMunicipioService;
        this.catMunicipioQueryService = catMunicipioQueryService;
        this.userRepository = userRepository;
    }

    /**
     * {@code POST  /cat-municipios} : Create a new catMunicipio.
     *
     * @param catMunicipio the catMunicipio to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new catMunicipio, or with status {@code 400 (Bad Request)} if the catMunicipio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cat-municipios")
    public ResponseEntity<CatMunicipio> createCatMunicipio(@Valid @RequestBody CatMunicipio catMunicipio) throws URISyntaxException {
        log.debug("REST request to save CatMunicipio : {}", catMunicipio);
        if (catMunicipio.getId() != null) {
            throw new BadRequestAlertException("A new catMunicipio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        String usrLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError"));
        Optional<User> user = userRepository.findOneByLogin(usrLogin);
        if (!user.isPresent()) {
            throw new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError");
        }
        catMunicipio.setUsuario(user.get());
        
        
        CatMunicipio result = catMunicipioService.save(catMunicipio);
        return ResponseEntity.created(new URI("/api/cat-municipios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cat-municipios} : Updates an existing catMunicipio.
     *
     * @param catMunicipio the catMunicipio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated catMunicipio,
     * or with status {@code 400 (Bad Request)} if the catMunicipio is not valid,
     * or with status {@code 500 (Internal Server Error)} if the catMunicipio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cat-municipios")
    public ResponseEntity<CatMunicipio> updateCatMunicipio(@Valid @RequestBody CatMunicipio catMunicipio) throws URISyntaxException {
        log.debug("REST request to update CatMunicipio : {}", catMunicipio);
        if (catMunicipio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        String usrLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError"));
        Optional<User> user = userRepository.findOneByLogin(usrLogin);
        if (!user.isPresent()) {
            throw new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError");
        }
        catMunicipio.setUsuario(user.get());
        
        CatMunicipio result = catMunicipioService.save(catMunicipio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, catMunicipio.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cat-municipios} : get all the catMunicipios.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of catMunicipios in body.
     */
    @GetMapping("/cat-municipios")
    public ResponseEntity<List<CatMunicipio>> getAllCatMunicipios(CatMunicipioCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CatMunicipios by criteria: {}", criteria);
        Page<CatMunicipio> page = catMunicipioQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cat-municipios/count} : count all the catMunicipios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cat-municipios/count")
    public ResponseEntity<Long> countCatMunicipios(CatMunicipioCriteria criteria) {
        log.debug("REST request to count CatMunicipios by criteria: {}", criteria);
        return ResponseEntity.ok().body(catMunicipioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cat-municipios/:id} : get the "id" catMunicipio.
     *
     * @param id the id of the catMunicipio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the catMunicipio, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cat-municipios/{id}")
    public ResponseEntity<CatMunicipio> getCatMunicipio(@PathVariable String id) {
        log.debug("REST request to get CatMunicipio : {}", id);
        Optional<CatMunicipio> catMunicipio = catMunicipioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(catMunicipio);
    }

    /**
     * {@code DELETE  /cat-municipios/:id} : delete the "id" catMunicipio.
     *
     * @param id the id of the catMunicipio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cat-municipios/{id}")
    public ResponseEntity<Void> deleteCatMunicipio(@PathVariable String id) {
        log.debug("REST request to delete CatMunicipio : {}", id);
        catMunicipioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
