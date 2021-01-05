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

import com.resamx.domain.CatLocalidad;
import com.resamx.domain.*; // for static metamodels
import com.resamx.repository.CatLocalidadRepository;
import com.resamx.service.dto.CatLocalidadCriteria;

/**
 * Service for executing complex queries for {@link CatLocalidad} entities in the database.
 * The main input is a {@link CatLocalidadCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CatLocalidad} or a {@link Page} of {@link CatLocalidad} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CatLocalidadQueryService extends QueryService<CatLocalidad> {

    private final Logger log = LoggerFactory.getLogger(CatLocalidadQueryService.class);

    private final CatLocalidadRepository catLocalidadRepository;

    public CatLocalidadQueryService(CatLocalidadRepository catLocalidadRepository) {
        this.catLocalidadRepository = catLocalidadRepository;
    }

    /**
     * Return a {@link List} of {@link CatLocalidad} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CatLocalidad> findByCriteria(CatLocalidadCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CatLocalidad> specification = createSpecification(criteria);
        return catLocalidadRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CatLocalidad} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CatLocalidad> findByCriteria(CatLocalidadCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CatLocalidad> specification = createSpecification(criteria);
        return catLocalidadRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CatLocalidadCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CatLocalidad> specification = createSpecification(criteria);
        return catLocalidadRepository.count(specification);
    }

    /**
     * Function to convert {@link CatLocalidadCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CatLocalidad> createSpecification(CatLocalidadCriteria criteria) {
        Specification<CatLocalidad> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getId(), CatLocalidad_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), CatLocalidad_.nombre));
            }
            if (criteria.getClave() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClave(), CatLocalidad_.clave));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioId(),
                    root -> root.join(CatLocalidad_.usuario, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getEstadoId() != null) {
                specification = specification.and(buildSpecification(criteria.getEstadoId(),
                    root -> root.join(CatLocalidad_.estado, JoinType.LEFT).get(CatEstado_.id)));
            }
            if (criteria.getMunicipioId() != null) {
                specification = specification.and(buildSpecification(criteria.getMunicipioId(),
                    root -> root.join(CatLocalidad_.municipio, JoinType.LEFT).get(CatMunicipio_.id)));
            }
        }
        return specification;
    }
}
