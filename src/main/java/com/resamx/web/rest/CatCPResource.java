package com.resamx.web.rest;

import com.resamx.domain.CatCP;
import com.resamx.domain.User;
import com.resamx.repository.UserRepository;
import com.resamx.security.SecurityUtils;
import com.resamx.service.CatCPService;
import com.resamx.web.rest.errors.BadRequestAlertException;
import com.resamx.service.dto.CatCPCriteria;
import com.resamx.service.CatCPQueryService;

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
 * REST controller for managing {@link com.resamx.domain.CatCP}.
 */
@RestController
@RequestMapping("/api")
public class CatCPResource {

    private final Logger log = LoggerFactory.getLogger(CatCPResource.class);

    private static final String ENTITY_NAME = "catCP";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CatCPService catCPService;

    private final CatCPQueryService catCPQueryService;
    
    private final UserRepository userRepository;

    public CatCPResource(CatCPService catCPService, CatCPQueryService catCPQueryService, UserRepository userRepository) {
        this.catCPService = catCPService;
        this.catCPQueryService = catCPQueryService;
        this.userRepository = userRepository;
    }

    /**
     * {@code POST  /cat-cps} : Create a new catCP.
     *
     * @param catCP the catCP to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new catCP, or with status {@code 400 (Bad Request)} if the catCP has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cat-cps")
    public ResponseEntity<CatCP> createCatCP(@Valid @RequestBody CatCP catCP) throws URISyntaxException {
        log.debug("REST request to save CatCP : {}", catCP);
        if (catCP.getId() != null) {
            throw new BadRequestAlertException("A new catCP cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        String usrLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError"));
        Optional<User> user = userRepository.findOneByLogin(usrLogin);
        if (!user.isPresent()) {
            throw new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError");
        }
        catCP.setUsuario(user.get());
        
        CatCP result = catCPService.save(catCP);
        return ResponseEntity.created(new URI("/api/cat-cps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cat-cps} : Updates an existing catCP.
     *
     * @param catCP the catCP to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated catCP,
     * or with status {@code 400 (Bad Request)} if the catCP is not valid,
     * or with status {@code 500 (Internal Server Error)} if the catCP couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cat-cps")
    public ResponseEntity<CatCP> updateCatCP(@Valid @RequestBody CatCP catCP) throws URISyntaxException {
        log.debug("REST request to update CatCP : {}", catCP);
        if (catCP.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        String usrLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError"));
        Optional<User> user = userRepository.findOneByLogin(usrLogin);
        if (!user.isPresent()) {
            throw new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError");
        }
        catCP.setUsuario(user.get());
        
        
        CatCP result = catCPService.save(catCP);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, catCP.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cat-cps} : get all the catCPS.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of catCPS in body.
     */
    @GetMapping("/cat-cps")
    public ResponseEntity<List<CatCP>> getAllCatCPS(CatCPCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CatCPS by criteria: {}", criteria);
        Page<CatCP> page = catCPQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cat-cps/count} : count all the catCPS.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cat-cps/count")
    public ResponseEntity<Long> countCatCPS(CatCPCriteria criteria) {
        log.debug("REST request to count CatCPS by criteria: {}", criteria);
        return ResponseEntity.ok().body(catCPQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cat-cps/:id} : get the "id" catCP.
     *
     * @param id the id of the catCP to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the catCP, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cat-cps/{id}")
    public ResponseEntity<CatCP> getCatCP(@PathVariable String id) {
        log.debug("REST request to get CatCP : {}", id);
        Optional<CatCP> catCP = catCPService.findOne(id);
        return ResponseUtil.wrapOrNotFound(catCP);
    }

    /**
     * {@code DELETE  /cat-cps/:id} : delete the "id" catCP.
     *
     * @param id the id of the catCP to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cat-cps/{id}")
    public ResponseEntity<Void> deleteCatCP(@PathVariable String id) {
        log.debug("REST request to delete CatCP : {}", id);
        catCPService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
