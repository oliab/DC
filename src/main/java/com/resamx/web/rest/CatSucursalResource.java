package com.resamx.web.rest;

import com.resamx.domain.CatSucursal;
import com.resamx.domain.User;
import com.resamx.repository.UserRepository;
import com.resamx.security.SecurityUtils;
import com.resamx.service.CatSucursalService;
import com.resamx.web.rest.errors.BadRequestAlertException;
import com.resamx.service.dto.CatSucursalCriteria;
import com.resamx.service.CatSucursalQueryService;

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
 * REST controller for managing {@link com.resamx.domain.CatSucursal}.
 */
@RestController
@RequestMapping("/api")
public class CatSucursalResource {

    private final Logger log = LoggerFactory.getLogger(CatSucursalResource.class);

    private static final String ENTITY_NAME = "catSucursal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CatSucursalService catSucursalService;

    private final CatSucursalQueryService catSucursalQueryService;
    
    private final UserRepository userRepository;

    public CatSucursalResource(CatSucursalService catSucursalService, CatSucursalQueryService catSucursalQueryService, UserRepository userRepository) {
        this.catSucursalService = catSucursalService;
        this.catSucursalQueryService = catSucursalQueryService;
        this.userRepository = userRepository;
    }

    /**
     * {@code POST  /cat-sucursals} : Create a new catSucursal.
     *
     * @param catSucursal the catSucursal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new catSucursal, or with status {@code 400 (Bad Request)} if the catSucursal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cat-sucursals")
    public ResponseEntity<CatSucursal> createCatSucursal(@Valid @RequestBody CatSucursal catSucursal) throws URISyntaxException {
        log.debug("REST request to save CatSucursal : {}", catSucursal);
        if (catSucursal.getId() != null) {
            throw new BadRequestAlertException("A new catSucursal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        String usrLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError"));
        Optional<User> user = userRepository.findOneByLogin(usrLogin);
        if (!user.isPresent()) {
            throw new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError");
        }
        catSucursal.setUsuario(user.get());
        
        CatSucursal result = catSucursalService.save(catSucursal);
        return ResponseEntity.created(new URI("/api/cat-sucursals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cat-sucursals} : Updates an existing catSucursal.
     *
     * @param catSucursal the catSucursal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated catSucursal,
     * or with status {@code 400 (Bad Request)} if the catSucursal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the catSucursal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cat-sucursals")
    public ResponseEntity<CatSucursal> updateCatSucursal(@Valid @RequestBody CatSucursal catSucursal) throws URISyntaxException {
        log.debug("REST request to update CatSucursal : {}", catSucursal);
        if (catSucursal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        String usrLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError"));
        Optional<User> user = userRepository.findOneByLogin(usrLogin);
        if (!user.isPresent()) {
            throw new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError");
        }
        catSucursal.setUsuario(user.get());
        
        
        CatSucursal result = catSucursalService.save(catSucursal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, catSucursal.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cat-sucursals} : get all the catSucursals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of catSucursals in body.
     */
    @GetMapping("/cat-sucursals")
    public ResponseEntity<List<CatSucursal>> getAllCatSucursals(CatSucursalCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CatSucursals by criteria: {}", criteria);
        Page<CatSucursal> page = catSucursalQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cat-sucursals/count} : count all the catSucursals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cat-sucursals/count")
    public ResponseEntity<Long> countCatSucursals(CatSucursalCriteria criteria) {
        log.debug("REST request to count CatSucursals by criteria: {}", criteria);
        return ResponseEntity.ok().body(catSucursalQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cat-sucursals/:id} : get the "id" catSucursal.
     *
     * @param id the id of the catSucursal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the catSucursal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cat-sucursals/{id}")
    public ResponseEntity<CatSucursal> getCatSucursal(@PathVariable Long id) {
        log.debug("REST request to get CatSucursal : {}", id);
        Optional<CatSucursal> catSucursal = catSucursalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(catSucursal);
    }

    /**
     * {@code DELETE  /cat-sucursals/:id} : delete the "id" catSucursal.
     *
     * @param id the id of the catSucursal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cat-sucursals/{id}")
    public ResponseEntity<Void> deleteCatSucursal(@PathVariable Long id) {
        log.debug("REST request to delete CatSucursal : {}", id);
        catSucursalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
