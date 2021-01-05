package com.resamx.service;

import com.resamx.domain.CatSucursal;
import com.resamx.repository.CatSucursalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CatSucursal}.
 */
@Service
@Transactional
public class CatSucursalService {

    private final Logger log = LoggerFactory.getLogger(CatSucursalService.class);

    private final CatSucursalRepository catSucursalRepository;

    public CatSucursalService(CatSucursalRepository catSucursalRepository) {
        this.catSucursalRepository = catSucursalRepository;
    }

    /**
     * Save a catSucursal.
     *
     * @param catSucursal the entity to save.
     * @return the persisted entity.
     */
    public CatSucursal save(CatSucursal catSucursal) {
        log.debug("Request to save CatSucursal : {}", catSucursal);
        return catSucursalRepository.save(catSucursal);
    }

    /**
     * Get all the catSucursals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CatSucursal> findAll(Pageable pageable) {
        log.debug("Request to get all CatSucursals");
        return catSucursalRepository.findAll(pageable);
    }


    /**
     * Get one catSucursal by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CatSucursal> findOne(Long id) {
        log.debug("Request to get CatSucursal : {}", id);
        return catSucursalRepository.findById(id);
    }

    /**
     * Delete the catSucursal by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CatSucursal : {}", id);
        catSucursalRepository.deleteById(id);
    }
}
