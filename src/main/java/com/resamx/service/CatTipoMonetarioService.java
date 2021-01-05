package com.resamx.service;

import com.resamx.domain.CatTipoMonetario;
import com.resamx.repository.CatTipoMonetarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CatTipoMonetario}.
 */
@Service
@Transactional
public class CatTipoMonetarioService {

    private final Logger log = LoggerFactory.getLogger(CatTipoMonetarioService.class);

    private final CatTipoMonetarioRepository catTipoMonetarioRepository;

    public CatTipoMonetarioService(CatTipoMonetarioRepository catTipoMonetarioRepository) {
        this.catTipoMonetarioRepository = catTipoMonetarioRepository;
    }

    /**
     * Save a catTipoMonetario.
     *
     * @param catTipoMonetario the entity to save.
     * @return the persisted entity.
     */
    public CatTipoMonetario save(CatTipoMonetario catTipoMonetario) {
        log.debug("Request to save CatTipoMonetario : {}", catTipoMonetario);
        return catTipoMonetarioRepository.save(catTipoMonetario);
    }

    /**
     * Get all the catTipoMonetarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CatTipoMonetario> findAll(Pageable pageable) {
        log.debug("Request to get all CatTipoMonetarios");
        return catTipoMonetarioRepository.findAll(pageable);
    }


    /**
     * Get one catTipoMonetario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CatTipoMonetario> findOne(String id) {
        log.debug("Request to get CatTipoMonetario : {}", id);
        return catTipoMonetarioRepository.findById(id);
    }

    /**
     * Delete the catTipoMonetario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete CatTipoMonetario : {}", id);
        catTipoMonetarioRepository.deleteById(id);
    }
}
