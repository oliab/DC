package com.resamx.service;

import com.resamx.domain.CatLocalidad;
import com.resamx.repository.CatLocalidadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CatLocalidad}.
 */
@Service
@Transactional
public class CatLocalidadService {

    private final Logger log = LoggerFactory.getLogger(CatLocalidadService.class);

    private final CatLocalidadRepository catLocalidadRepository;

    public CatLocalidadService(CatLocalidadRepository catLocalidadRepository) {
        this.catLocalidadRepository = catLocalidadRepository;
    }

    /**
     * Save a catLocalidad.
     *
     * @param catLocalidad the entity to save.
     * @return the persisted entity.
     */
    public CatLocalidad save(CatLocalidad catLocalidad) {
        log.debug("Request to save CatLocalidad : {}", catLocalidad);
        return catLocalidadRepository.save(catLocalidad);
    }

    /**
     * Get all the catLocalidads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CatLocalidad> findAll(Pageable pageable) {
        log.debug("Request to get all CatLocalidads");
        return catLocalidadRepository.findAll(pageable);
    }


    /**
     * Get one catLocalidad by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CatLocalidad> findOne(String id) {
        log.debug("Request to get CatLocalidad : {}", id);
        return catLocalidadRepository.findById(id);
    }

    /**
     * Delete the catLocalidad by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete CatLocalidad : {}", id);
        catLocalidadRepository.deleteById(id);
    }
}
