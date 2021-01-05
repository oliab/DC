package com.resamx.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.resamx.domain.CatSector;
import com.resamx.domain.*; // for static metamodels
import com.resamx.repository.CatSectorRepository;
import com.resamx.service.dto.CatSectorCriteria;

/**
 * Service for executing complex queries for {@link CatSector} entities in the database.
 * The main input is a {@link CatSectorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CatSector} or a {@link Page} of {@link CatSector} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CatSectorQueryService extends QueryService<CatSector> {

    private final Logger log = LoggerFactory.getLogger(CatSectorQueryService.class);

    private final CatSectorRepository catSectorRepository;

    public CatSectorQueryService(CatSectorRepository catSectorRepository) {
        this.catSectorRepository = catSectorRepository;
    }

    /**
     * Return a {@link List} of {@link CatSector} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CatSector> findByCriteria(CatSectorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CatSector> specification = createSpecification(criteria);
        return catSectorRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CatSector} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CatSector> findByCriteria(CatSectorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CatSector> specification = createSpecification(criteria);
        return catSectorRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CatSectorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CatSector> specification = createSpecification(criteria);
        return catSectorRepository.count(specification);
    }

    /**
     * Function to convert {@link CatSectorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CatSector> createSpecification(CatSectorCriteria criteria) {
        Specification<CatSector> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getId(), CatSector_.id));
            }
            if (criteria.getActividadEconomica() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActividadEconomica(), CatSector_.actividadEconomica));
            }
            if (criteria.getActividadVulnerable() != null) {
                specification = specification.and(buildSpecification(criteria.getActividadVulnerable(), CatSector_.actividadVulnerable));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioId(),
                    root -> root.join(CatSector_.usuario, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
