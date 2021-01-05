package com.resamx.service;

import com.resamx.domain.CatTipoOperacion;
import com.resamx.repository.CatTipoOperacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CatTipoOperacion}.
 */
@Service
@Transactional
public class CatTipoOperacionService {

    private final Logger log = LoggerFactory.getLogger(CatTipoOperacionService.class);

    private final CatTipoOperacionRepository catTipoOperacionRepository;

    public CatTipoOperacionService(CatTipoOperacionRepository catTipoOperacionRepository) {
        this.catTipoOperacionRepository = catTipoOperacionRepository;
    }

    /**
     * Save a catTipoOperacion.
     *
     * @param catTipoOperacion the entity to save.
     * @return the persisted entity.
     */
    public CatTipoOperacion save(CatTipoOperacion catTipoOperacion) {
        log.debug("Request to save CatTipoOperacion : {}", catTipoOperacion);
        return catTipoOperacionRepository.save(catTipoOperacion);
    }

    /**
     * Get all the catTipoOperacions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CatTipoOperacion> findAll(Pageable pageable) {
        log.debug("Request to get all CatTipoOperacions");
        return catTipoOperacionRepository.findAll(pageable);
    }


    /**
     * Get one catTipoOperacion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CatTipoOperacion> findOne(Long id) {
        log.debug("Request to get CatTipoOperacion : {}", id);
        return catTipoOperacionRepository.findById(id);
    }

    /**
     * Delete the catTipoOperacion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CatTipoOperacion : {}", id);
        catTipoOperacionRepository.deleteById(id);
    }
}
