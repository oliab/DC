package com.resamx.web.rest;

import com.resamx.domain.CatMoneda;
import com.resamx.domain.User;
import com.resamx.repository.UserRepository;
import com.resamx.security.SecurityUtils;
import com.resamx.service.CatMonedaService;
import com.resamx.web.rest.errors.BadRequestAlertException;
import com.resamx.service.dto.CatMonedaCriteria;
import com.resamx.service.CatMonedaQueryService;

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
 * REST controller for managing {@link com.resamx.domain.CatMoneda}.
 */
@RestController
@RequestMapping("/api")
public class CatMonedaResource {

    private final Logger log = LoggerFactory.getLogger(CatMonedaResource.class);

    private static final String ENTITY_NAME = "catMoneda";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CatMonedaService catMonedaService;

    private final CatMonedaQueryService catMonedaQueryService;
    
    private final UserRepository userRepository;

    public CatMonedaResource(CatMonedaService catMonedaService, CatMonedaQueryService catMonedaQueryService, UserRepository userRepository) {
        this.catMonedaService = catMonedaService;
        this.catMonedaQueryService = catMonedaQueryService;
        this.userRepository = userRepository;
    }

    /**
     * {@code POST  /cat-monedas} : Create a new catMoneda.
     *
     * @param catMoneda the catMoneda to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new catMoneda, or with status {@code 400 (Bad Request)} if the catMoneda has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cat-monedas")
    public ResponseEntity<CatMoneda> createCatMoneda(@Valid @RequestBody CatMoneda catMoneda) throws URISyntaxException {
        log.debug("REST request to save CatMoneda : {}", catMoneda);
        if (catMoneda.getId() != null) {
            throw new BadRequestAlertException("A new catMoneda cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        String usrLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError"));
        Optional<User> user = userRepository.findOneByLogin(usrLogin);
        if (!user.isPresent()) {
            throw new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError");
        }
        catMoneda.setUsuario(user.get());
        
        
        CatMoneda result = catMonedaService.save(catMoneda);
        return ResponseEntity.created(new URI("/api/cat-monedas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cat-monedas} : Updates an existing catMoneda.
     *
     * @param catMoneda the catMoneda to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated catMoneda,
     * or with status {@code 400 (Bad Request)} if the catMoneda is not valid,
     * or with status {@code 500 (Internal Server Error)} if the catMoneda couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cat-monedas")
    public ResponseEntity<CatMoneda> updateCatMoneda(@Valid @RequestBody CatMoneda catMoneda) throws URISyntaxException {
        log.debug("REST request to update CatMoneda : {}", catMoneda);
        if (catMoneda.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        String usrLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError"));
        Optional<User> user = userRepository.findOneByLogin(usrLogin);
        if (!user.isPresent()) {
            throw new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError");
        }
        catMoneda.setUsuario(user.get());
        
        
        CatMoneda result = catMonedaService.save(catMoneda);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, catMoneda.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cat-monedas} : get all the catMonedas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of catMonedas in body.
     */
    @GetMapping("/cat-monedas")
    public ResponseEntity<List<CatMoneda>> getAllCatMonedas(CatMonedaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CatMonedas by criteria: {}", criteria);
        Page<CatMoneda> page = catMonedaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cat-monedas/count} : count all the catMonedas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cat-monedas/count")
    public ResponseEntity<Long> countCatMonedas(CatMonedaCriteria criteria) {
        log.debug("REST request to count CatMonedas by criteria: {}", criteria);
        return ResponseEntity.ok().body(catMonedaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cat-monedas/:id} : get the "id" catMoneda.
     *
     * @param id the id of the catMoneda to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the catMoneda, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cat-monedas/{id}")
    public ResponseEntity<CatMoneda> getCatMoneda(@PathVariable String id) {
        log.debug("REST request to get CatMoneda : {}", id);
        Optional<CatMoneda> catMoneda = catMonedaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(catMoneda);
    }

    /**
     * {@code DELETE  /cat-monedas/:id} : delete the "id" catMoneda.
     *
     * @param id the id of the catMoneda to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cat-monedas/{id}")
    public ResponseEntity<Void> deleteCatMoneda(@PathVariable String id) {
        log.debug("REST request to delete CatMoneda : {}", id);
        catMonedaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
