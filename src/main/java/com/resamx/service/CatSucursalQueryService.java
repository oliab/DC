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

import com.resamx.domain.CatSucursal;
import com.resamx.domain.*; // for static metamodels
import com.resamx.repository.CatSucursalRepository;
import com.resamx.service.dto.CatSucursalCriteria;

/**
 * Service for executing complex queries for {@link CatSucursal} entities in the database.
 * The main input is a {@link CatSucursalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CatSucursal} or a {@link Page} of {@link CatSucursal} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CatSucursalQueryService extends QueryService<CatSucursal> {

    private final Logger log = LoggerFactory.getLogger(CatSucursalQueryService.class);

    private final CatSucursalRepository catSucursalRepository;

    public CatSucursalQueryService(CatSucursalRepository catSucursalRepository) {
        this.catSucursalRepository = catSucursalRepository;
    }

    /**
     * Return a {@link List} of {@link CatSucursal} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CatSucursal> findByCriteria(CatSucursalCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CatSucursal> specification = createSpecification(criteria);
        return catSucursalRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CatSucursal} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CatSucursal> findByCriteria(CatSucursalCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CatSucursal> specification = createSpecification(criteria);
        return catSucursalRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CatSucursalCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CatSucursal> specification = createSpecification(criteria);
        return catSucursalRepository.count(specification);
    }

    /**
     * Function to convert {@link CatSucursalCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CatSucursal> createSpecification(CatSucursalCriteria criteria) {
        Specification<CatSucursal> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CatSucursal_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), CatSucursal_.nombre));
            }
            if (criteria.getDireccion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDireccion(), CatSucursal_.direccion));
            }
            if (criteria.getTelefono() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefono(), CatSucursal_.telefono));
            }
            if (criteria.getFechaAct() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaAct(), CatSucursal_.fechaAct));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioId(),
                    root -> root.join(CatSucursal_.usuario, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
