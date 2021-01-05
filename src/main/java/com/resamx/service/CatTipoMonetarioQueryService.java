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

import com.resamx.domain.CatTipoMonetario;
import com.resamx.domain.*; // for static metamodels
import com.resamx.repository.CatTipoMonetarioRepository;
import com.resamx.service.dto.CatTipoMonetarioCriteria;

/**
 * Service for executing complex queries for {@link CatTipoMonetario} entities in the database.
 * The main input is a {@link CatTipoMonetarioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CatTipoMonetario} or a {@link Page} of {@link CatTipoMonetario} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CatTipoMonetarioQueryService extends QueryService<CatTipoMonetario> {

    private final Logger log = LoggerFactory.getLogger(CatTipoMonetarioQueryService.class);

    private final CatTipoMonetarioRepository catTipoMonetarioRepository;

    public CatTipoMonetarioQueryService(CatTipoMonetarioRepository catTipoMonetarioRepository) {
        this.catTipoMonetarioRepository = catTipoMonetarioRepository;
    }

    /**
     * Return a {@link List} of {@link CatTipoMonetario} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CatTipoMonetario> findByCriteria(CatTipoMonetarioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CatTipoMonetario> specification = createSpecification(criteria);
        return catTipoMonetarioRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CatTipoMonetario} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CatTipoMonetario> findByCriteria(CatTipoMonetarioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CatTipoMonetario> specification = createSpecification(criteria);
        return catTipoMonetarioRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CatTipoMonetarioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CatTipoMonetario> specification = createSpecification(criteria);
        return catTipoMonetarioRepository.count(specification);
    }

    /**
     * Function to convert {@link CatTipoMonetarioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CatTipoMonetario> createSpecification(CatTipoMonetarioCriteria criteria) {
        Specification<CatTipoMonetario> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getId(), CatTipoMonetario_.id));
            }
            if (criteria.getInstrumento() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInstrumento(), CatTipoMonetario_.instrumento));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), CatTipoMonetario_.descripcion));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioId(),
                    root -> root.join(CatTipoMonetario_.usuario, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
