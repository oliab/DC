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

import com.resamx.domain.CatMoneda;
import com.resamx.domain.*; // for static metamodels
import com.resamx.repository.CatMonedaRepository;
import com.resamx.service.dto.CatMonedaCriteria;

/**
 * Service for executing complex queries for {@link CatMoneda} entities in the database.
 * The main input is a {@link CatMonedaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CatMoneda} or a {@link Page} of {@link CatMoneda} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CatMonedaQueryService extends QueryService<CatMoneda> {

    private final Logger log = LoggerFactory.getLogger(CatMonedaQueryService.class);

    private final CatMonedaRepository catMonedaRepository;

    public CatMonedaQueryService(CatMonedaRepository catMonedaRepository) {
        this.catMonedaRepository = catMonedaRepository;
    }

    /**
     * Return a {@link List} of {@link CatMoneda} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CatMoneda> findByCriteria(CatMonedaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CatMoneda> specification = createSpecification(criteria);
        return catMonedaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CatMoneda} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CatMoneda> findByCriteria(CatMonedaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CatMoneda> specification = createSpecification(criteria);
        return catMonedaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CatMonedaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CatMoneda> specification = createSpecification(criteria);
        return catMonedaRepository.count(specification);
    }

    /**
     * Function to convert {@link CatMonedaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CatMoneda> createSpecification(CatMonedaCriteria criteria) {
        Specification<CatMoneda> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getId(), CatMoneda_.id));
            }
            if (criteria.getMoneda() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMoneda(), CatMoneda_.moneda));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioId(),
                    root -> root.join(CatMoneda_.usuario, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getPaisId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaisId(),
                    root -> root.join(CatMoneda_.pais, JoinType.LEFT).get(CatPais_.id)));
            }
        }
        return specification;
    }
}
