package com.resamx.service;

import com.resamx.domain.CatSector;
import com.resamx.repository.CatSectorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CatSector}.
 */
@Service
@Transactional
public class CatSectorService {

    private final Logger log = LoggerFactory.getLogger(CatSectorService.class);

    private final CatSectorRepository catSectorRepository;

    public CatSectorService(CatSectorRepository catSectorRepository) {
        this.catSectorRepository = catSectorRepository;
    }

    /**
     * Save a catSector.
     *
     * @param catSector the entity to save.
     * @return the persisted entity.
     */
    public CatSector save(CatSector catSector) {
        log.debug("Request to save CatSector : {}", catSector);
        return catSectorRepository.save(catSector);
    }

    /**
     * Get all the catSectors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CatSector> findAll(Pageable pageable) {
        log.debug("Request to get all CatSectors");
        return catSectorRepository.findAll(pageable);
    }


    /**
     * Get one catSector by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CatSector> findOne(String id) {
        log.debug("Request to get CatSector : {}", id);
        return catSectorRepository.findById(id);
    }

    /**
     * Delete the catSector by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete CatSector : {}", id);
        catSectorRepository.deleteById(id);
    }
}
