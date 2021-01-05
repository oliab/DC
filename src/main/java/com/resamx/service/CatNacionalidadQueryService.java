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

import com.resamx.domain.CatNacionalidad;
import com.resamx.domain.*; // for static metamodels
import com.resamx.repository.CatNacionalidadRepository;
import com.resamx.service.dto.CatNacionalidadCriteria;

/**
 * Service for executing complex queries for {@link CatNacionalidad} entities in the database.
 * The main input is a {@link CatNacionalidadCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CatNacionalidad} or a {@link Page} of {@link CatNacionalidad} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CatNacionalidadQueryService extends QueryService<CatNacionalidad> {

    private final Logger log = LoggerFactory.getLogger(CatNacionalidadQueryService.class);

    private final CatNacionalidadRepository catNacionalidadRepository;

    public CatNacionalidadQueryService(CatNacionalidadRepository catNacionalidadRepository) {
        this.catNacionalidadRepository = catNacionalidadRepository;
    }

    /**
     * Return a {@link List} of {@link CatNacionalidad} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CatNacionalidad> findByCriteria(CatNacionalidadCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CatNacionalidad> specification = createSpecification(criteria);
        return catNacionalidadRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CatNacionalidad} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CatNacionalidad> findByCriteria(CatNacionalidadCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CatNacionalidad> specification = createSpecification(criteria);
        return catNacionalidadRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CatNacionalidadCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CatNacionalidad> specification = createSpecification(criteria);
        return catNacionalidadRepository.count(specification);
    }

    /**
     * Function to convert {@link CatNacionalidadCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CatNacionalidad> createSpecification(CatNacionalidadCriteria criteria) {
        Specification<CatNacionalidad> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CatNacionalidad_.id));
            }
            if (criteria.getNacionalidad() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNacionalidad(), CatNacionalidad_.nacionalidad));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioId(),
                    root -> root.join(CatNacionalidad_.usuario, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
