package com.resamx.web.rest;

import com.resamx.domain.CatSector;
import com.resamx.domain.User;
import com.resamx.repository.UserRepository;
import com.resamx.security.SecurityUtils;
import com.resamx.service.CatSectorService;
import com.resamx.web.rest.errors.BadRequestAlertException;
import com.resamx.service.dto.CatSectorCriteria;
import com.resamx.service.CatSectorQueryService;

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
 * REST controller for managing {@link com.resamx.domain.CatSector}.
 */
@RestController
@RequestMapping("/api")
public class CatSectorResource {

    private final Logger log = LoggerFactory.getLogger(CatSectorResource.class);

    private static final String ENTITY_NAME = "catSector";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CatSectorService catSectorService;

    private final CatSectorQueryService catSectorQueryService;
    
    private final UserRepository userRepository;

    public CatSectorResource(CatSectorService catSectorService, CatSectorQueryService catSectorQueryService, UserRepository userRepository) {
        this.catSectorService = catSectorService;
        this.catSectorQueryService = catSectorQueryService;
        this.userRepository = userRepository;
    }

    /**
     * {@code POST  /cat-sectors} : Create a new catSector.
     *
     * @param catSector the catSector to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new catSector, or with status {@code 400 (Bad Request)} if the catSector has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cat-sectors")
    public ResponseEntity<CatSector> createCatSector(@Valid @RequestBody CatSector catSector) throws URISyntaxException {
        log.debug("REST request to save CatSector : {}", catSector);
        if (catSector.getId() != null) {
            throw new BadRequestAlertException("A new catSector cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        String usrLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError"));
        Optional<User> user = userRepository.findOneByLogin(usrLogin);
        if (!user.isPresent()) {
            throw new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError");
        }
        catSector.setUsuario(user.get());
        
        
        CatSector result = catSectorService.save(catSector);
        return ResponseEntity.created(new URI("/api/cat-sectors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cat-sectors} : Updates an existing catSector.
     *
     * @param catSector the catSector to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated catSector,
     * or with status {@code 400 (Bad Request)} if the catSector is not valid,
     * or with status {@code 500 (Internal Server Error)} if the catSector couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cat-sectors")
    public ResponseEntity<CatSector> updateCatSector(@Valid @RequestBody CatSector catSector) throws URISyntaxException {
        log.debug("REST request to update CatSector : {}", catSector);
        if (catSector.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        String usrLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError"));
        Optional<User> user = userRepository.findOneByLogin(usrLogin);
        if (!user.isPresent()) {
            throw new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError");
        }
        catSector.setUsuario(user.get());
        
        
        CatSector result = catSectorService.save(catSector);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, catSector.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cat-sectors} : get all the catSectors.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of catSectors in body.
     */
    @GetMapping("/cat-sectors")
    public ResponseEntity<List<CatSector>> getAllCatSectors(CatSectorCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CatSectors by criteria: {}", criteria);
        Page<CatSector> page = catSectorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cat-sectors/count} : count all the catSectors.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cat-sectors/count")
    public ResponseEntity<Long> countCatSectors(CatSectorCriteria criteria) {
        log.debug("REST request to count CatSectors by criteria: {}", criteria);
        return ResponseEntity.ok().body(catSectorQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cat-sectors/:id} : get the "id" catSector.
     *
     * @param id the id of the catSector to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the catSector, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cat-sectors/{id}")
    public ResponseEntity<CatSector> getCatSector(@PathVariable String id) {
        log.debug("REST request to get CatSector : {}", id);
        Optional<CatSector> catSector = catSectorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(catSector);
    }

    /**
     * {@code DELETE  /cat-sectors/:id} : delete the "id" catSector.
     *
     * @param id the id of the catSector to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cat-sectors/{id}")
    public ResponseEntity<Void> deleteCatSector(@PathVariable String id) {
        log.debug("REST request to delete CatSector : {}", id);
        catSectorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
