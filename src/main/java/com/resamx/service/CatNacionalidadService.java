package com.resamx.service;

import com.resamx.domain.CatNacionalidad;
import com.resamx.repository.CatNacionalidadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CatNacionalidad}.
 */
@Service
@Transactional
public class CatNacionalidadService {

    private final Logger log = LoggerFactory.getLogger(CatNacionalidadService.class);

    private final CatNacionalidadRepository catNacionalidadRepository;

    public CatNacionalidadService(CatNacionalidadRepository catNacionalidadRepository) {
        this.catNacionalidadRepository = catNacionalidadRepository;
    }

    /**
     * Save a catNacionalidad.
     *
     * @param catNacionalidad the entity to save.
     * @return the persisted entity.
     */
    public CatNacionalidad save(CatNacionalidad catNacionalidad) {
        log.debug("Request to save CatNacionalidad : {}", catNacionalidad);
        return catNacionalidadRepository.save(catNacionalidad);
    }

    /**
     * Get all the catNacionalidads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CatNacionalidad> findAll(Pageable pageable) {
        log.debug("Request to get all CatNacionalidads");
        return catNacionalidadRepository.findAll(pageable);
    }


    /**
     * Get one catNacionalidad by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CatNacionalidad> findOne(Long id) {
        log.debug("Request to get CatNacionalidad : {}", id);
        return catNacionalidadRepository.findById(id);
    }

    /**
     * Delete the catNacionalidad by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CatNacionalidad : {}", id);
        catNacionalidadRepository.deleteById(id);
    }
}
