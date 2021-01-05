package com.resamx.web.rest;

import com.resamx.domain.CatTipoMonetario;
import com.resamx.domain.User;
import com.resamx.repository.UserRepository;
import com.resamx.security.SecurityUtils;
import com.resamx.service.CatTipoMonetarioService;
import com.resamx.web.rest.errors.BadRequestAlertException;
import com.resamx.service.dto.CatTipoMonetarioCriteria;
import com.resamx.service.CatTipoMonetarioQueryService;

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
 * REST controller for managing {@link com.resamx.domain.CatTipoMonetario}.
 */
@RestController
@RequestMapping("/api")
public class CatTipoMonetarioResource {

    private final Logger log = LoggerFactory.getLogger(CatTipoMonetarioResource.class);

    private static final String ENTITY_NAME = "catTipoMonetario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CatTipoMonetarioService catTipoMonetarioService;

    private final CatTipoMonetarioQueryService catTipoMonetarioQueryService;
    
    private final UserRepository userRepository;

    public CatTipoMonetarioResource(CatTipoMonetarioService catTipoMonetarioService, CatTipoMonetarioQueryService catTipoMonetarioQueryService, UserRepository userRepository) {
        this.catTipoMonetarioService = catTipoMonetarioService;
        this.catTipoMonetarioQueryService = catTipoMonetarioQueryService;
        this.userRepository = userRepository;
    }

    /**
     * {@code POST  /cat-tipo-monetarios} : Create a new catTipoMonetario.
     *
     * @param catTipoMonetario the catTipoMonetario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new catTipoMonetario, or with status {@code 400 (Bad Request)} if the catTipoMonetario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cat-tipo-monetarios")
    public ResponseEntity<CatTipoMonetario> createCatTipoMonetario(@Valid @RequestBody CatTipoMonetario catTipoMonetario) throws URISyntaxException {
        log.debug("REST request to save CatTipoMonetario : {}", catTipoMonetario);
        if (catTipoMonetario.getId() != null) {
            throw new BadRequestAlertException("A new catTipoMonetario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        String usrLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError"));
        Optional<User> user = userRepository.findOneByLogin(usrLogin);
        if (!user.isPresent()) {
            throw new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError");
        }
        catTipoMonetario.setUsuario(user.get());
        
        
        CatTipoMonetario result = catTipoMonetarioService.save(catTipoMonetario);
        return ResponseEntity.created(new URI("/api/cat-tipo-monetarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cat-tipo-monetarios} : Updates an existing catTipoMonetario.
     *
     * @param catTipoMonetario the catTipoMonetario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated catTipoMonetario,
     * or with status {@code 400 (Bad Request)} if the catTipoMonetario is not valid,
     * or with status {@code 500 (Internal Server Error)} if the catTipoMonetario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cat-tipo-monetarios")
    public ResponseEntity<CatTipoMonetario> updateCatTipoMonetario(@Valid @RequestBody CatTipoMonetario catTipoMonetario) throws URISyntaxException {
        log.debug("REST request to update CatTipoMonetario : {}", catTipoMonetario);
        if (catTipoMonetario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        String usrLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError"));
        Optional<User> user = userRepository.findOneByLogin(usrLogin);
        if (!user.isPresent()) {
            throw new BadRequestAlertException("El usuario actual no es válido", ENTITY_NAME, "internalServerError");
        }
        catTipoMonetario.setUsuario(user.get());
        
        
        CatTipoMonetario result = catTipoMonetarioService.save(catTipoMonetario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, catTipoMonetario.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cat-tipo-monetarios} : get all the catTipoMonetarios.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of catTipoMonetarios in body.
     */
    @GetMapping("/cat-tipo-monetarios")
    public ResponseEntity<List<CatTipoMonetario>> getAllCatTipoMonetarios(CatTipoMonetarioCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CatTipoMonetarios by criteria: {}", criteria);
        Page<CatTipoMonetario> page = catTipoMonetarioQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cat-tipo-monetarios/count} : count all the catTipoMonetarios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cat-tipo-monetarios/count")
    public ResponseEntity<Long> countCatTipoMonetarios(CatTipoMonetarioCriteria criteria) {
        log.debug("REST request to count CatTipoMonetarios by criteria: {}", criteria);
        return ResponseEntity.ok().body(catTipoMonetarioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cat-tipo-monetarios/:id} : get the "id" catTipoMonetario.
     *
     * @param id the id of the catTipoMonetario to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the catTipoMonetario, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cat-tipo-monetarios/{id}")
    public ResponseEntity<CatTipoMonetario> getCatTipoMonetario(@PathVariable String id) {
        log.debug("REST request to get CatTipoMonetario : {}", id);
        Optional<CatTipoMonetario> catTipoMonetario = catTipoMonetarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(catTipoMonetario);
    }

    /**
     * {@code DELETE  /cat-tipo-monetarios/:id} : delete the "id" catTipoMonetario.
     *
     * @param id the id of the catTipoMonetario to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cat-tipo-monetarios/{id}")
    public ResponseEntity<Void> deleteCatTipoMonetario(@PathVariable String id) {
        log.debug("REST request to delete CatTipoMonetario : {}", id);
        catTipoMonetarioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
