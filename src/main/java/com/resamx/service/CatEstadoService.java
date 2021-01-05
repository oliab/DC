package com.resamx.service;

import com.resamx.domain.CatEstado;
import com.resamx.repository.CatEstadoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CatEstado}.
 */
@Service
@Transactional
public class CatEstadoService {

    private final Logger log = LoggerFactory.getLogger(CatEstadoService.class);

    private final CatEstadoRepository catEstadoRepository;

    public CatEstadoService(CatEstadoRepository catEstadoRepository) {
        this.catEstadoRepository = catEstadoRepository;
    }

    /**
     * Save a catEstado.
     *
     * @param catEstado the entity to save.
     * @return the persisted entity.
     */
    public CatEstado save(CatEstado catEstado) {
        log.debug("Request to save CatEstado : {}", catEstado);
        return catEstadoRepository.save(catEstado);
    }

    /**
     * Get all the catEstados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CatEstado> findAll(Pageable pageable) {
        log.debug("Request to get all CatEstados");
        return catEstadoRepository.findAll(pageable);
    }


    /**
     * Get one catEstado by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CatEstado> findOne(String id) {
        log.debug("Request to get CatEstado : {}", id);
        return catEstadoRepository.findById(id);
    }

    /**
     * Delete the catEstado by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete CatEstado : {}", id);
        catEstadoRepository.deleteById(id);
    }
}
