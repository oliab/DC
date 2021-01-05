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

import com.resamx.domain.CatCP;
import com.resamx.domain.*; // for static metamodels
import com.resamx.repository.CatCPRepository;
import com.resamx.service.dto.CatCPCriteria;

/**
 * Service for executing complex queries for {@link CatCP} entities in the database.
 * The main input is a {@link CatCPCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CatCP} or a {@link Page} of {@link CatCP} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CatCPQueryService extends QueryService<CatCP> {

    private final Logger log = LoggerFactory.getLogger(CatCPQueryService.class);

    private final CatCPRepository catCPRepository;

    public CatCPQueryService(CatCPRepository catCPRepository) {
        this.catCPRepository = catCPRepository;
    }

    /**
     * Return a {@link List} of {@link CatCP} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CatCP> findByCriteria(CatCPCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CatCP> specification = createSpecification(criteria);
        return catCPRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CatCP} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CatCP> findByCriteria(CatCPCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CatCP> specification = createSpecification(criteria);
        return catCPRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CatCPCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CatCP> specification = createSpecification(criteria);
        return catCPRepository.count(specification);
    }

    /**
     * Function to convert {@link CatCPCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CatCP> createSpecification(CatCPCriteria criteria) {
        Specification<CatCP> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getId(), CatCP_.id));
            }
            if (criteria.getAnio() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAnio(), CatCP_.anio));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioId(),
                    root -> root.join(CatCP_.usuario, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getEstadoId() != null) {
                specification = specification.and(buildSpecification(criteria.getEstadoId(),
                    root -> root.join(CatCP_.estado, JoinType.LEFT).get(CatEstado_.id)));
            }
            if (criteria.getMunicipioId() != null) {
                specification = specification.and(buildSpecification(criteria.getMunicipioId(),
                    root -> root.join(CatCP_.municipio, JoinType.LEFT).get(CatMunicipio_.id)));
            }
            if (criteria.getRiesgoId() != null) {
                specification = specification.and(buildSpecification(criteria.getRiesgoId(),
                    root -> root.join(CatCP_.riesgo, JoinType.LEFT).get(CatRiesgo_.id)));
            }
        }
        return specification;
    }
}
