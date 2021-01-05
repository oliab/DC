package com.resamx.service;

import com.resamx.domain.CatCP;
import com.resamx.repository.CatCPRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CatCP}.
 */
@Service
@Transactional
public class CatCPService {

    private final Logger log = LoggerFactory.getLogger(CatCPService.class);

    private final CatCPRepository catCPRepository;

    public CatCPService(CatCPRepository catCPRepository) {
        this.catCPRepository = catCPRepository;
    }

    /**
     * Save a catCP.
     *
     * @param catCP the entity to save.
     * @return the persisted entity.
     */
    public CatCP save(CatCP catCP) {
        log.debug("Request to save CatCP : {}", catCP);
        return catCPRepository.save(catCP);
    }

    /**
     * Get all the catCPS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CatCP> findAll(Pageable pageable) {
        log.debug("Request to get all CatCPS");
        return catCPRepository.findAll(pageable);
    }


    /**
     * Get one catCP by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CatCP> findOne(String id) {
        log.debug("Request to get CatCP : {}", id);
        return catCPRepository.findById(id);
    }

    /**
     * Delete the catCP by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete CatCP : {}", id);
        catCPRepository.deleteById(id);
    }
}
