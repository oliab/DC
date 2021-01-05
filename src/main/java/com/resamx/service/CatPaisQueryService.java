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

import com.resamx.domain.CatPais;
import com.resamx.domain.*; // for static metamodels
import com.resamx.repository.CatPaisRepository;
import com.resamx.service.dto.CatPaisCriteria;

/**
 * Service for executing complex queries for {@link CatPais} entities in the database.
 * The main input is a {@link CatPaisCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CatPais} or a {@link Page} of {@link CatPais} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CatPaisQueryService extends QueryService<CatPais> {

    private final Logger log = LoggerFactory.getLogger(CatPaisQueryService.class);

    private final CatPaisRepository catPaisRepository;

    public CatPaisQueryService(CatPaisRepository catPaisRepository) {
        this.catPaisRepository = catPaisRepository;
    }

    /**
     * Return a {@link List} of {@link CatPais} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CatPais> findByCriteria(CatPaisCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CatPais> specification = createSpecification(criteria);
        return catPaisRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CatPais} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CatPais> findByCriteria(CatPaisCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CatPais> specification = createSpecification(criteria);
        return catPaisRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CatPaisCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CatPais> specification = createSpecification(criteria);
        return catPaisRepository.count(specification);
    }

    /**
     * Function to convert {@link CatPaisCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CatPais> createSpecification(CatPaisCriteria criteria) {
        Specification<CatPais> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getId(), CatPais_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), CatPais_.nombre));
            }
            if (criteria.getCodigoA2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodigoA2(), CatPais_.codigoA2));
            }
            if (criteria.getCodigoA3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodigoA3(), CatPais_.codigoA3));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioId(),
                    root -> root.join(CatPais_.usuario, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
