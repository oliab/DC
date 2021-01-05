package com.resamx.web.rest;

import com.resamx.domain.CatPais;
import com.resamx.domain.User;
import com.resamx.repository.UserRepository;
import com.resamx.security.SecurityUtils;
import com.resamx.service.CatPaisService;
import com.resamx.web.rest.errors.BadRequestAlertException;
import com.resamx.service.dto.CatPaisCriteria;
import com.resamx.service.CatPaisQueryService;

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
 * REST controller for managing {@link com.resamx.domain.CatPais}.
 */
@RestController
@RequestMapping("/api")
public class CatPaisResource {

    private final Logger log = LoggerFactory.getLogger(CatPaisResource.class);

    private static final String ENTITY_NAME = "catPais";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CatPaisService catPaisService;

    private final CatPaisQueryService catPaisQueryService;
    
    private final UserRepository userRepository;

    public CatPaisResource(CatPaisService catPaisService, CatPaisQueryService catPaisQueryService, UserRepository userRepository) {
        this.catPaisService = catPaisService;
        this.catPaisQueryService = catPaisQueryService;
        this.userRepository = userRepository;
    }

    /**
     * {@code POST  /cat-pais} : Create a new catPais.
     *
     * @param catPais the catPais to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new catPais, or with status {@code 400 (Bad Request)} if the catPais has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cat-pais")
    public ResponseEntity<CatPais> createCatPais(@Valid @RequestBody CatPais catPais) throws URISyntaxException {
        log.debug("REST request to save CatPais : {}", catPais);
        if (catPais.getId() != null) {
            throw new BadRequestAlertException("A new catPais cannot already have an ID", ENTITY_NAME, "idexists");
        }

        String usrLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError"));
        Optional<User> user = userRepository.findOneByLogin(usrLogin);
        if (!user.isPresent()) {
            throw new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError");
        }
        catPais.setUsuario(user.get());

        CatPais result = catPaisService.save(catPais);
        return ResponseEntity.created(new URI("/api/cat-pais/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cat-pais} : Updates an existing catPais.
     *
     * @param catPais the catPais to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated catPais,
     * or with status {@code 400 (Bad Request)} if the catPais is not valid,
     * or with status {@code 500 (Internal Server Error)} if the catPais couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cat-pais")
    public ResponseEntity<CatPais> updateCatPais(@Valid @RequestBody CatPais catPais) throws URISyntaxException {
        log.debug("REST request to update CatPais : {}", catPais);
        if (catPais.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        String usrLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError"));
        Optional<User> user = userRepository.findOneByLogin(usrLogin);
        if (!user.isPresent()) {
            throw new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError");
        }
        catPais.setUsuario(user.get());
        
        CatPais result = catPaisService.save(catPais);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, catPais.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cat-pais} : get all the catPais.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of catPais in body.
     */
    @GetMapping("/cat-pais")
    public ResponseEntity<List<CatPais>> getAllCatPais(CatPaisCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CatPais by criteria: {}", criteria);
        Page<CatPais> page = catPaisQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cat-pais/count} : count all the catPais.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cat-pais/count")
    public ResponseEntity<Long> countCatPais(CatPaisCriteria criteria) {
        log.debug("REST request to count CatPais by criteria: {}", criteria);
        return ResponseEntity.ok().body(catPaisQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cat-pais/:id} : get the "id" catPais.
     *
     * @param id the id of the catPais to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the catPais, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cat-pais/{id}")
    public ResponseEntity<CatPais> getCatPais(@PathVariable String id) {
        log.debug("REST request to get CatPais : {}", id);
        Optional<CatPais> catPais = catPaisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(catPais);
    }

    /**
     * {@code DELETE  /cat-pais/:id} : delete the "id" catPais.
     *
     * @param id the id of the catPais to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cat-pais/{id}")
    public ResponseEntity<Void> deleteCatPais(@PathVariable String id) {
        log.debug("REST request to delete CatPais : {}", id);
        catPaisService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
