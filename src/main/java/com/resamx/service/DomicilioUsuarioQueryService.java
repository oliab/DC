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

import com.resamx.domain.DomicilioUsuario;
import com.resamx.domain.*; // for static metamodels
import com.resamx.repository.DomicilioUsuarioRepository;
import com.resamx.service.dto.DomicilioUsuarioCriteria;

/**
 * Service for executing complex queries for {@link DomicilioUsuario} entities in the database.
 * The main input is a {@link DomicilioUsuarioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DomicilioUsuario} or a {@link Page} of {@link DomicilioUsuario} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DomicilioUsuarioQueryService extends QueryService<DomicilioUsuario> {

    private final Logger log = LoggerFactory.getLogger(DomicilioUsuarioQueryService.class);

    private final DomicilioUsuarioRepository domicilioUsuarioRepository;

    public DomicilioUsuarioQueryService(DomicilioUsuarioRepository domicilioUsuarioRepository) {
        this.domicilioUsuarioRepository = domicilioUsuarioRepository;
    }

    /**
     * Return a {@link List} of {@link DomicilioUsuario} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DomicilioUsuario> findByCriteria(DomicilioUsuarioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DomicilioUsuario> specification = createSpecification(criteria);
        return domicilioUsuarioRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DomicilioUsuario} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DomicilioUsuario> findByCriteria(DomicilioUsuarioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DomicilioUsuario> specification = createSpecification(criteria);
        return domicilioUsuarioRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DomicilioUsuarioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DomicilioUsuario> specification = createSpecification(criteria);
        return domicilioUsuarioRepository.count(specification);
    }

    /**
     * Function to convert {@link DomicilioUsuarioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DomicilioUsuario> createSpecification(DomicilioUsuarioCriteria criteria) {
        Specification<DomicilioUsuario> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DomicilioUsuario_.id));
            }
            if (criteria.getColonia() != null) {
                specification = specification.and(buildStringSpecification(criteria.getColonia(), DomicilioUsuario_.colonia));
            }
            if (criteria.getCalle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCalle(), DomicilioUsuario_.calle));
            }
            if (criteria.getNoExt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNoExt(), DomicilioUsuario_.noExt));
            }
            if (criteria.getNoInt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNoInt(), DomicilioUsuario_.noInt));
            }
            if (criteria.getDomicilio() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDomicilio(), DomicilioUsuario_.domicilio));
            }
            if (criteria.getFechaAct() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaAct(), DomicilioUsuario_.fechaAct));
            }
            if (criteria.getNacionalidadId() != null) {
                specification = specification.and(buildSpecification(criteria.getNacionalidadId(),
                    root -> root.join(DomicilioUsuario_.nacionalidad, JoinType.LEFT).get(CatNacionalidad_.id)));
            }
            if (criteria.getPaisOrigenId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaisOrigenId(),
                    root -> root.join(DomicilioUsuario_.paisOrigen, JoinType.LEFT).get(CatPais_.id)));
            }
            if (criteria.getPaisId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaisId(),
                    root -> root.join(DomicilioUsuario_.pais, JoinType.LEFT).get(CatPais_.id)));
            }
            if (criteria.getEstadoId() != null) {
                specification = specification.and(buildSpecification(criteria.getEstadoId(),
                    root -> root.join(DomicilioUsuario_.estado, JoinType.LEFT).get(CatEstado_.id)));
            }
            if (criteria.getMunicipioId() != null) {
                specification = specification.and(buildSpecification(criteria.getMunicipioId(),
                    root -> root.join(DomicilioUsuario_.municipio, JoinType.LEFT).get(CatMunicipio_.id)));
            }
            if (criteria.getLocalidadId() != null) {
                specification = specification.and(buildSpecification(criteria.getLocalidadId(),
                    root -> root.join(DomicilioUsuario_.localidad, JoinType.LEFT).get(CatLocalidad_.id)));
            }
            if (criteria.getCpId() != null) {
                specification = specification.and(buildSpecification(criteria.getCpId(),
                    root -> root.join(DomicilioUsuario_.cp, JoinType.LEFT).get(CatCP_.id)));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(DomicilioUsuario_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
