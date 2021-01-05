package com.resamx.service;

import com.resamx.domain.DomicilioEmpresa;
import com.resamx.repository.DomicilioEmpresaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DomicilioEmpresa}.
 */
@Service
@Transactional
public class DomicilioEmpresaService {

    private final Logger log = LoggerFactory.getLogger(DomicilioEmpresaService.class);

    private final DomicilioEmpresaRepository domicilioEmpresaRepository;

    public DomicilioEmpresaService(DomicilioEmpresaRepository domicilioEmpresaRepository) {
        this.domicilioEmpresaRepository = domicilioEmpresaRepository;
    }

    /**
     * Save a domicilioEmpresa.
     *
     * @param domicilioEmpresa the entity to save.
     * @return the persisted entity.
     */
    public DomicilioEmpresa save(DomicilioEmpresa domicilioEmpresa) {
        log.debug("Request to save DomicilioEmpresa : {}", domicilioEmpresa);
        return domicilioEmpresaRepository.save(domicilioEmpresa);
    }

    /**
     * Get all the domicilioEmpresas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DomicilioEmpresa> findAll(Pageable pageable) {
        log.debug("Request to get all DomicilioEmpresas");
        return domicilioEmpresaRepository.findAll(pageable);
    }


    /**
     * Get one domicilioEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DomicilioEmpresa> findOne(Long id) {
        log.debug("Request to get DomicilioEmpresa : {}", id);
        return domicilioEmpresaRepository.findById(id);
    }

    /**
     * Delete the domicilioEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DomicilioEmpresa : {}", id);
        domicilioEmpresaRepository.deleteById(id);
    }
}
