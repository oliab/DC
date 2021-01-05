package com.resamx.service;

import com.resamx.domain.CatIdentificacion;
import com.resamx.repository.CatIdentificacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CatIdentificacion}.
 */
@Service
@Transactional
public class CatIdentificacionService {

    private final Logger log = LoggerFactory.getLogger(CatIdentificacionService.class);

    private final CatIdentificacionRepository catIdentificacionRepository;

    public CatIdentificacionService(CatIdentificacionRepository catIdentificacionRepository) {
        this.catIdentificacionRepository = catIdentificacionRepository;
    }

    /**
     * Save a catIdentificacion.
     *
     * @param catIdentificacion the entity to save.
     * @return the persisted entity.
     */
    public CatIdentificacion save(CatIdentificacion catIdentificacion) {
        log.debug("Request to save CatIdentificacion : {}", catIdentificacion);
        return catIdentificacionRepository.save(catIdentificacion);
    }

    /**
     * Get all the catIdentificacions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CatIdentificacion> findAll(Pageable pageable) {
        log.debug("Request to get all CatIdentificacions");
        return catIdentificacionRepository.findAll(pageable);
    }


    /**
     * Get one catIdentificacion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CatIdentificacion> findOne(Long id) {
        log.debug("Request to get CatIdentificacion : {}", id);
        return catIdentificacionRepository.findById(id);
    }

    /**
     * Delete the catIdentificacion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CatIdentificacion : {}", id);
        catIdentificacionRepository.deleteById(id);
    }
}
