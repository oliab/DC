package com.resamx.service;

import com.resamx.domain.CatPais;
import com.resamx.repository.CatPaisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CatPais}.
 */
@Service
@Transactional
public class CatPaisService {

    private final Logger log = LoggerFactory.getLogger(CatPaisService.class);

    private final CatPaisRepository catPaisRepository;

    public CatPaisService(CatPaisRepository catPaisRepository) {
        this.catPaisRepository = catPaisRepository;
    }

    /**
     * Save a catPais.
     *
     * @param catPais the entity to save.
     * @return the persisted entity.
     */
    public CatPais save(CatPais catPais) {
        log.debug("Request to save CatPais : {}", catPais);
        return catPaisRepository.save(catPais);
    }

    /**
     * Get all the catPais.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CatPais> findAll(Pageable pageable) {
        log.debug("Request to get all CatPais");
        return catPaisRepository.findAll(pageable);
    }


    /**
     * Get one catPais by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CatPais> findOne(String id) {
        log.debug("Request to get CatPais : {}", id);
        return catPaisRepository.findById(id);
    }

    /**
     * Delete the catPais by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete CatPais : {}", id);
        catPaisRepository.deleteById(id);
    }
}
