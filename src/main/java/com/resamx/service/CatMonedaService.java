package com.resamx.service;

import com.resamx.domain.CatMoneda;
import com.resamx.repository.CatMonedaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CatMoneda}.
 */
@Service
@Transactional
public class CatMonedaService {

    private final Logger log = LoggerFactory.getLogger(CatMonedaService.class);

    private final CatMonedaRepository catMonedaRepository;

    public CatMonedaService(CatMonedaRepository catMonedaRepository) {
        this.catMonedaRepository = catMonedaRepository;
    }

    /**
     * Save a catMoneda.
     *
     * @param catMoneda the entity to save.
     * @return the persisted entity.
     */
    public CatMoneda save(CatMoneda catMoneda) {
        log.debug("Request to save CatMoneda : {}", catMoneda);
        return catMonedaRepository.save(catMoneda);
    }

    /**
     * Get all the catMonedas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CatMoneda> findAll(Pageable pageable) {
        log.debug("Request to get all CatMonedas");
        return catMonedaRepository.findAll(pageable);
    }


    /**
     * Get one catMoneda by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CatMoneda> findOne(String id) {
        log.debug("Request to get CatMoneda : {}", id);
        return catMonedaRepository.findById(id);
    }

    /**
     * Delete the catMoneda by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete CatMoneda : {}", id);
        catMonedaRepository.deleteById(id);
    }
}
