package com.resamx.service;

import com.resamx.domain.CatTipoDocumento;
import com.resamx.repository.CatTipoDocumentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CatTipoDocumento}.
 */
@Service
@Transactional
public class CatTipoDocumentoService {

    private final Logger log = LoggerFactory.getLogger(CatTipoDocumentoService.class);

    private final CatTipoDocumentoRepository catTipoDocumentoRepository;

    public CatTipoDocumentoService(CatTipoDocumentoRepository catTipoDocumentoRepository) {
        this.catTipoDocumentoRepository = catTipoDocumentoRepository;
    }

    /**
     * Save a catTipoDocumento.
     *
     * @param catTipoDocumento the entity to save.
     * @return the persisted entity.
     */
    public CatTipoDocumento save(CatTipoDocumento catTipoDocumento) {
        log.debug("Request to save CatTipoDocumento : {}", catTipoDocumento);
        return catTipoDocumentoRepository.save(catTipoDocumento);
    }

    /**
     * Get all the catTipoDocumentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CatTipoDocumento> findAll(Pageable pageable) {
        log.debug("Request to get all CatTipoDocumentos");
        return catTipoDocumentoRepository.findAll(pageable);
    }


    /**
     * Get one catTipoDocumento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CatTipoDocumento> findOne(Long id) {
        log.debug("Request to get CatTipoDocumento : {}", id);
        return catTipoDocumentoRepository.findById(id);
    }

    /**
     * Delete the catTipoDocumento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CatTipoDocumento : {}", id);
        catTipoDocumentoRepository.deleteById(id);
    }
}
