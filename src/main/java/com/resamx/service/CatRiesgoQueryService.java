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

import com.resamx.domain.CatRiesgo;
import com.resamx.domain.*; // for static metamodels
import com.resamx.repository.CatRiesgoRepository;
import com.resamx.service.dto.CatRiesgoCriteria;

/**
 * Service for executing complex queries for {@link CatRiesgo} entities in the database.
 * The main input is a {@link CatRiesgoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CatRiesgo} or a {@link Page} of {@link CatRiesgo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CatRiesgoQueryService extends QueryService<CatRiesgo> {

    private final Logger log = LoggerFactory.getLogger(CatRiesgoQueryService.class);

    private final CatRiesgoRepository catRiesgoRepository;

    public CatRiesgoQueryService(CatRiesgoRepository catRiesgoRepository) {
        this.catRiesgoRepository = catRiesgoRepository;
    }

    /**
     * Return a {@link List} of {@link CatRiesgo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CatRiesgo> findByCriteria(CatRiesgoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CatRiesgo> specification = createSpecification(criteria);
        return catRiesgoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CatRiesgo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CatRiesgo> findByCriteria(CatRiesgoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CatRiesgo> specification = createSpecification(criteria);
        return catRiesgoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CatRiesgoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CatRiesgo> specification = createSpecification(criteria);
        return catRiesgoRepository.count(specification);
    }

    /**
     * Function to convert {@link CatRiesgoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CatRiesgo> createSpecification(CatRiesgoCriteria criteria) {
        Specification<CatRiesgo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CatRiesgo_.id));
            }
            if (criteria.getRiesgo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRiesgo(), CatRiesgo_.riesgo));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioId(),
                    root -> root.join(CatRiesgo_.usuario, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
