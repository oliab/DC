package com.resamx.service;

import com.resamx.domain.CatMunicipio;
import com.resamx.repository.CatMunicipioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CatMunicipio}.
 */
@Service
@Transactional
public class CatMunicipioService {

    private final Logger log = LoggerFactory.getLogger(CatMunicipioService.class);

    private final CatMunicipioRepository catMunicipioRepository;

    public CatMunicipioService(CatMunicipioRepository catMunicipioRepository) {
        this.catMunicipioRepository = catMunicipioRepository;
    }

    /**
     * Save a catMunicipio.
     *
     * @param catMunicipio the entity to save.
     * @return the persisted entity.
     */
    public CatMunicipio save(CatMunicipio catMunicipio) {
        log.debug("Request to save CatMunicipio : {}", catMunicipio);
        return catMunicipioRepository.save(catMunicipio);
    }

    /**
     * Get all the catMunicipios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CatMunicipio> findAll(Pageable pageable) {
        log.debug("Request to get all CatMunicipios");
        return catMunicipioRepository.findAll(pageable);
    }


    /**
     * Get one catMunicipio by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CatMunicipio> findOne(String id) {
        log.debug("Request to get CatMunicipio : {}", id);
        return catMunicipioRepository.findById(id);
    }

    /**
     * Delete the catMunicipio by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete CatMunicipio : {}", id);
        catMunicipioRepository.deleteById(id);
    }
}
