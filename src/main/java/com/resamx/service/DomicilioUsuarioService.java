package com.resamx.service;

import com.resamx.domain.DomicilioUsuario;
import com.resamx.repository.DomicilioUsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DomicilioUsuario}.
 */
@Service
@Transactional
public class DomicilioUsuarioService {

    private final Logger log = LoggerFactory.getLogger(DomicilioUsuarioService.class);

    private final DomicilioUsuarioRepository domicilioUsuarioRepository;

    public DomicilioUsuarioService(DomicilioUsuarioRepository domicilioUsuarioRepository) {
        this.domicilioUsuarioRepository = domicilioUsuarioRepository;
    }

    /**
     * Save a domicilioUsuario.
     *
     * @param domicilioUsuario the entity to save.
     * @return the persisted entity.
     */
    public DomicilioUsuario save(DomicilioUsuario domicilioUsuario) {
        log.debug("Request to save DomicilioUsuario : {}", domicilioUsuario);
        return domicilioUsuarioRepository.save(domicilioUsuario);
    }

    /**
     * Get all the domicilioUsuarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DomicilioUsuario> findAll(Pageable pageable) {
        log.debug("Request to get all DomicilioUsuarios");
        return domicilioUsuarioRepository.findAll(pageable);
    }


    /**
     * Get one domicilioUsuario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DomicilioUsuario> findOne(Long id) {
        log.debug("Request to get DomicilioUsuario : {}", id);
        return domicilioUsuarioRepository.findById(id);
    }

    /**
     * Delete the domicilioUsuario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DomicilioUsuario : {}", id);
        domicilioUsuarioRepository.deleteById(id);
    }
}
