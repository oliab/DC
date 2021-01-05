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

import com.resamx.domain.CatTipoOperacion;
import com.resamx.domain.*; // for static metamodels
import com.resamx.repository.CatTipoOperacionRepository;
import com.resamx.service.dto.CatTipoOperacionCriteria;

/**
 * Service for executing complex queries for {@link CatTipoOperacion} entities in the database.
 * The main input is a {@link CatTipoOperacionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CatTipoOperacion} or a {@link Page} of {@link CatTipoOperacion} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CatTipoOperacionQueryService extends QueryService<CatTipoOperacion> {

    private final Logger log = LoggerFactory.getLogger(CatTipoOperacionQueryService.class);

    private final CatTipoOperacionRepository catTipoOperacionRepository;

    public CatTipoOperacionQueryService(CatTipoOperacionRepository catTipoOperacionRepository) {
        this.catTipoOperacionRepository = catTipoOperacionRepository;
    }

    /**
     * Return a {@link List} of {@link CatTipoOperacion} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CatTipoOperacion> findByCriteria(CatTipoOperacionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CatTipoOperacion> specification = createSpecification(criteria);
        return catTipoOperacionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CatTipoOperacion} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CatTipoOperacion> findByCriteria(CatTipoOperacionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CatTipoOperacion> specification = createSpecification(criteria);
        return catTipoOperacionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CatTipoOperacionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CatTipoOperacion> specification = createSpecification(criteria);
        return catTipoOperacionRepository.count(specification);
    }

    /**
     * Function to convert {@link CatTipoOperacionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CatTipoOperacion> createSpecification(CatTipoOperacionCriteria criteria) {
        Specification<CatTipoOperacion> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CatTipoOperacion_.id));
            }
            if (criteria.getOperacion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOperacion(), CatTipoOperacion_.operacion));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), CatTipoOperacion_.descripcion));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioId(),
                    root -> root.join(CatTipoOperacion_.usuario, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
