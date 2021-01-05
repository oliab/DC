package com.resamx.service;

import com.resamx.domain.CatRiesgo;
import com.resamx.repository.CatRiesgoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CatRiesgo}.
 */
@Service
@Transactional
public class CatRiesgoService {

    private final Logger log = LoggerFactory.getLogger(CatRiesgoService.class);

    private final CatRiesgoRepository catRiesgoRepository;

    public CatRiesgoService(CatRiesgoRepository catRiesgoRepository) {
        this.catRiesgoRepository = catRiesgoRepository;
    }

    /**
     * Save a catRiesgo.
     *
     * @param catRiesgo the entity to save.
     * @return the persisted entity.
     */
    public CatRiesgo save(CatRiesgo catRiesgo) {
        log.debug("Request to save CatRiesgo : {}", catRiesgo);
        return catRiesgoRepository.save(catRiesgo);
    }

    /**
     * Get all the catRiesgos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CatRiesgo> findAll(Pageable pageable) {
        log.debug("Request to get all CatRiesgos");
        return catRiesgoRepository.findAll(pageable);
    }


    /**
     * Get one catRiesgo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CatRiesgo> findOne(Long id) {
        log.debug("Request to get CatRiesgo : {}", id);
        return catRiesgoRepository.findById(id);
    }

    /**
     * Delete the catRiesgo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CatRiesgo : {}", id);
        catRiesgoRepository.deleteById(id);
    }
}
