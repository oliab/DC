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

import com.resamx.domain.CatIdentificacion;
import com.resamx.domain.*; // for static metamodels
import com.resamx.repository.CatIdentificacionRepository;
import com.resamx.service.dto.CatIdentificacionCriteria;

/**
 * Service for executing complex queries for {@link CatIdentificacion} entities in the database.
 * The main input is a {@link CatIdentificacionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CatIdentificacion} or a {@link Page} of {@link CatIdentificacion} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CatIdentificacionQueryService extends QueryService<CatIdentificacion> {

    private final Logger log = LoggerFactory.getLogger(CatIdentificacionQueryService.class);

    private final CatIdentificacionRepository catIdentificacionRepository;

    public CatIdentificacionQueryService(CatIdentificacionRepository catIdentificacionRepository) {
        this.catIdentificacionRepository = catIdentificacionRepository;
    }

    /**
     * Return a {@link List} of {@link CatIdentificacion} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CatIdentificacion> findByCriteria(CatIdentificacionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CatIdentificacion> specification = createSpecification(criteria);
        return catIdentificacionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CatIdentificacion} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CatIdentificacion> findByCriteria(CatIdentificacionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CatIdentificacion> specification = createSpecification(criteria);
        return catIdentificacionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CatIdentificacionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CatIdentificacion> specification = createSpecification(criteria);
        return catIdentificacionRepository.count(specification);
    }

    /**
     * Function to convert {@link CatIdentificacionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CatIdentificacion> createSpecification(CatIdentificacionCriteria criteria) {
        Specification<CatIdentificacion> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CatIdentificacion_.id));
            }
            if (criteria.getIdentificacion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdentificacion(), CatIdentificacion_.identificacion));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioId(),
                    root -> root.join(CatIdentificacion_.usuario, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
