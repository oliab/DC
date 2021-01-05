package com.resamx.service;

import com.resamx.domain.ExpedienteCliente;
import com.resamx.repository.ExpedienteClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ExpedienteCliente}.
 */
@Service
@Transactional
public class ExpedienteClienteService {

    private final Logger log = LoggerFactory.getLogger(ExpedienteClienteService.class);

    private final ExpedienteClienteRepository expedienteClienteRepository;

    public ExpedienteClienteService(ExpedienteClienteRepository expedienteClienteRepository) {
        this.expedienteClienteRepository = expedienteClienteRepository;
    }

    /**
     * Save a expedienteCliente.
     *
     * @param expedienteCliente the entity to save.
     * @return the persisted entity.
     */
    public ExpedienteCliente save(ExpedienteCliente expedienteCliente) {
        log.debug("Request to save ExpedienteCliente : {}", expedienteCliente);
        return expedienteClienteRepository.save(expedienteCliente);
    }

    /**
     * Get all the expedienteClientes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ExpedienteCliente> findAll(Pageable pageable) {
        log.debug("Request to get all ExpedienteClientes");
        return expedienteClienteRepository.findAll(pageable);
    }


    /**
     * Get one expedienteCliente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ExpedienteCliente> findOne(Long id) {
        log.debug("Request to get ExpedienteCliente : {}", id);
        return expedienteClienteRepository.findById(id);
    }

    /**
     * Delete the expedienteCliente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ExpedienteCliente : {}", id);
        expedienteClienteRepository.deleteById(id);
    }
}
