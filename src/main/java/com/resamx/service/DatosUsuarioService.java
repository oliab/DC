package com.resamx.service;

import com.resamx.domain.DatosUsuario;
import com.resamx.repository.DatosUsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DatosUsuario}.
 */
@Service
@Transactional
public class DatosUsuarioService {

    private final Logger log = LoggerFactory.getLogger(DatosUsuarioService.class);

    private final DatosUsuarioRepository datosUsuarioRepository;

    public DatosUsuarioService(DatosUsuarioRepository datosUsuarioRepository) {
        this.datosUsuarioRepository = datosUsuarioRepository;
    }

    /**
     * Save a datosUsuario.
     *
     * @param datosUsuario the entity to save.
     * @return the persisted entity.
     */
    public DatosUsuario save(DatosUsuario datosUsuario) {
        log.debug("Request to save DatosUsuario : {}", datosUsuario);
        return datosUsuarioRepository.save(datosUsuario);
    }

    /**
     * Get all the datosUsuarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DatosUsuario> findAll(Pageable pageable) {
        log.debug("Request to get all DatosUsuarios");
        return datosUsuarioRepository.findAll(pageable);
    }


    /**
     * Get one datosUsuario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DatosUsuario> findOne(Long id) {
        log.debug("Request to get DatosUsuario : {}", id);
        return datosUsuarioRepository.findById(id);
    }

    /**
     * Delete the datosUsuario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DatosUsuario : {}", id);
        datosUsuarioRepository.deleteById(id);
    }
}
