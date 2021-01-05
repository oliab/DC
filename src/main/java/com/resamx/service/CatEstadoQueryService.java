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

import com.resamx.domain.CatEstado;
import com.resamx.domain.*; // for static metamodels
import com.resamx.repository.CatEstadoRepository;
import com.resamx.service.dto.CatEstadoCriteria;

/**
 * Service for executing complex queries for {@link CatEstado} entities in the database.
 * The main input is a {@link CatEstadoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CatEstado} or a {@link Page} of {@link CatEstado} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CatEstadoQueryService extends QueryService<CatEstado> {

    private final Logger log = LoggerFactory.getLogger(CatEstadoQueryService.class);

    private final CatEstadoRepository catEstadoRepository;

    public CatEstadoQueryService(CatEstadoRepository catEstadoRepository) {
        this.catEstadoRepository = catEstadoRepository;
    }

    /**
     * Return a {@link List} of {@link CatEstado} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CatEstado> findByCriteria(CatEstadoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CatEstado> specification = createSpecification(criteria);
        return catEstadoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CatEstado} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CatEstado> findByCriteria(CatEstadoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CatEstado> specification = createSpecification(criteria);
        return catEstadoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CatEstadoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CatEstado> specification = createSpecification(criteria);
        return catEstadoRepository.count(specification);
    }

    /**
     * Function to convert {@link CatEstadoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CatEstado> createSpecification(CatEstadoCriteria criteria) {
        Specification<CatEstado> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getId(), CatEstado_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), CatEstado_.nombre));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioId(),
                    root -> root.join(CatEstado_.usuario, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getPaisId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaisId(),
                    root -> root.join(CatEstado_.pais, JoinType.LEFT).get(CatPais_.id)));
            }
        }
        return specification;
    }
}
