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

import com.resamx.domain.CatTipoEmpresa;
import com.resamx.domain.*; // for static metamodels
import com.resamx.repository.CatTipoEmpresaRepository;
import com.resamx.service.dto.CatTipoEmpresaCriteria;

/**
 * Service for executing complex queries for {@link CatTipoEmpresa} entities in the database.
 * The main input is a {@link CatTipoEmpresaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CatTipoEmpresa} or a {@link Page} of {@link CatTipoEmpresa} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CatTipoEmpresaQueryService extends QueryService<CatTipoEmpresa> {

    private final Logger log = LoggerFactory.getLogger(CatTipoEmpresaQueryService.class);

    private final CatTipoEmpresaRepository catTipoEmpresaRepository;

    public CatTipoEmpresaQueryService(CatTipoEmpresaRepository catTipoEmpresaRepository) {
        this.catTipoEmpresaRepository = catTipoEmpresaRepository;
    }

    /**
     * Return a {@link List} of {@link CatTipoEmpresa} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CatTipoEmpresa> findByCriteria(CatTipoEmpresaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CatTipoEmpresa> specification = createSpecification(criteria);
        return catTipoEmpresaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CatTipoEmpresa} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CatTipoEmpresa> findByCriteria(CatTipoEmpresaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CatTipoEmpresa> specification = createSpecification(criteria);
        return catTipoEmpresaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CatTipoEmpresaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CatTipoEmpresa> specification = createSpecification(criteria);
        return catTipoEmpresaRepository.count(specification);
    }

    /**
     * Function to convert {@link CatTipoEmpresaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CatTipoEmpresa> createSpecification(CatTipoEmpresaCriteria criteria) {
        Specification<CatTipoEmpresa> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CatTipoEmpresa_.id));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipo(), CatTipoEmpresa_.tipo));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioId(),
                    root -> root.join(CatTipoEmpresa_.usuario, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
