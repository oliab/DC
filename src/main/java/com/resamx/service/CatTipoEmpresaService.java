package com.resamx.service;

import com.resamx.domain.CatTipoEmpresa;
import com.resamx.repository.CatTipoEmpresaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CatTipoEmpresa}.
 */
@Service
@Transactional
public class CatTipoEmpresaService {

    private final Logger log = LoggerFactory.getLogger(CatTipoEmpresaService.class);

    private final CatTipoEmpresaRepository catTipoEmpresaRepository;

    public CatTipoEmpresaService(CatTipoEmpresaRepository catTipoEmpresaRepository) {
        this.catTipoEmpresaRepository = catTipoEmpresaRepository;
    }

    /**
     * Save a catTipoEmpresa.
     *
     * @param catTipoEmpresa the entity to save.
     * @return the persisted entity.
     */
    public CatTipoEmpresa save(CatTipoEmpresa catTipoEmpresa) {
        log.debug("Request to save CatTipoEmpresa : {}", catTipoEmpresa);
        return catTipoEmpresaRepository.save(catTipoEmpresa);
    }

    /**
     * Get all the catTipoEmpresas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CatTipoEmpresa> findAll(Pageable pageable) {
        log.debug("Request to get all CatTipoEmpresas");
        return catTipoEmpresaRepository.findAll(pageable);
    }


    /**
     * Get one catTipoEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CatTipoEmpresa> findOne(Long id) {
        log.debug("Request to get CatTipoEmpresa : {}", id);
        return catTipoEmpresaRepository.findById(id);
    }

    /**
     * Delete the catTipoEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CatTipoEmpresa : {}", id);
        catTipoEmpresaRepository.deleteById(id);
    }
}
