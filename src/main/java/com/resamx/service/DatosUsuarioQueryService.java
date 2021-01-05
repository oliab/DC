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

import com.resamx.domain.DatosUsuario;
import com.resamx.domain.*; // for static metamodels
import com.resamx.repository.DatosUsuarioRepository;
import com.resamx.service.dto.DatosUsuarioCriteria;

/**
 * Service for executing complex queries for {@link DatosUsuario} entities in the database.
 * The main input is a {@link DatosUsuarioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DatosUsuario} or a {@link Page} of {@link DatosUsuario} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DatosUsuarioQueryService extends QueryService<DatosUsuario> {

    private final Logger log = LoggerFactory.getLogger(DatosUsuarioQueryService.class);

    private final DatosUsuarioRepository datosUsuarioRepository;

    public DatosUsuarioQueryService(DatosUsuarioRepository datosUsuarioRepository) {
        this.datosUsuarioRepository = datosUsuarioRepository;
    }

    /**
     * Return a {@link List} of {@link DatosUsuario} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DatosUsuario> findByCriteria(DatosUsuarioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DatosUsuario> specification = createSpecification(criteria);
        return datosUsuarioRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DatosUsuario} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DatosUsuario> findByCriteria(DatosUsuarioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DatosUsuario> specification = createSpecification(criteria);
        return datosUsuarioRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DatosUsuarioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DatosUsuario> specification = createSpecification(criteria);
        return datosUsuarioRepository.count(specification);
    }

    /**
     * Function to convert {@link DatosUsuarioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DatosUsuario> createSpecification(DatosUsuarioCriteria criteria) {
        Specification<DatosUsuario> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DatosUsuario_.id));
            }
            if (criteria.getPuesto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPuesto(), DatosUsuario_.puesto));
            }
            if (criteria.getFechaAct() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaAct(), DatosUsuario_.fechaAct));
            }
            if (criteria.getSucursalId() != null) {
                specification = specification.and(buildSpecification(criteria.getSucursalId(),
                    root -> root.join(DatosUsuario_.sucursal, JoinType.LEFT).get(CatSucursal_.id)));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(DatosUsuario_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
