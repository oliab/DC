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

import com.resamx.domain.DomicilioEmpresa;
import com.resamx.domain.*; // for static metamodels
import com.resamx.repository.DomicilioEmpresaRepository;
import com.resamx.service.dto.DomicilioEmpresaCriteria;

/**
 * Service for executing complex queries for {@link DomicilioEmpresa} entities in the database.
 * The main input is a {@link DomicilioEmpresaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DomicilioEmpresa} or a {@link Page} of {@link DomicilioEmpresa} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DomicilioEmpresaQueryService extends QueryService<DomicilioEmpresa> {

    private final Logger log = LoggerFactory.getLogger(DomicilioEmpresaQueryService.class);

    private final DomicilioEmpresaRepository domicilioEmpresaRepository;

    public DomicilioEmpresaQueryService(DomicilioEmpresaRepository domicilioEmpresaRepository) {
        this.domicilioEmpresaRepository = domicilioEmpresaRepository;
    }

    /**
     * Return a {@link List} of {@link DomicilioEmpresa} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DomicilioEmpresa> findByCriteria(DomicilioEmpresaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DomicilioEmpresa> specification = createSpecification(criteria);
        return domicilioEmpresaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DomicilioEmpresa} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DomicilioEmpresa> findByCriteria(DomicilioEmpresaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DomicilioEmpresa> specification = createSpecification(criteria);
        return domicilioEmpresaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DomicilioEmpresaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DomicilioEmpresa> specification = createSpecification(criteria);
        return domicilioEmpresaRepository.count(specification);
    }

    /**
     * Function to convert {@link DomicilioEmpresaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DomicilioEmpresa> createSpecification(DomicilioEmpresaCriteria criteria) {
        Specification<DomicilioEmpresa> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DomicilioEmpresa_.id));
            }
            if (criteria.getColonia() != null) {
                specification = specification.and(buildStringSpecification(criteria.getColonia(), DomicilioEmpresa_.colonia));
            }
            if (criteria.getCalle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCalle(), DomicilioEmpresa_.calle));
            }
            if (criteria.getNoExt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNoExt(), DomicilioEmpresa_.noExt));
            }
            if (criteria.getNoInt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNoInt(), DomicilioEmpresa_.noInt));
            }
            if (criteria.getDomicilio() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDomicilio(), DomicilioEmpresa_.domicilio));
            }
            if (criteria.getFechaAct() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaAct(), DomicilioEmpresa_.fechaAct));
            }
            if (criteria.getNacionalidadId() != null) {
                specification = specification.and(buildSpecification(criteria.getNacionalidadId(),
                    root -> root.join(DomicilioEmpresa_.nacionalidad, JoinType.LEFT).get(CatNacionalidad_.id)));
            }
            if (criteria.getPaisOrigenId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaisOrigenId(),
                    root -> root.join(DomicilioEmpresa_.paisOrigen, JoinType.LEFT).get(CatPais_.id)));
            }
            if (criteria.getPaisId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaisId(),
                    root -> root.join(DomicilioEmpresa_.pais, JoinType.LEFT).get(CatPais_.id)));
            }
            if (criteria.getEstadoId() != null) {
                specification = specification.and(buildSpecification(criteria.getEstadoId(),
                    root -> root.join(DomicilioEmpresa_.estado, JoinType.LEFT).get(CatEstado_.id)));
            }
            if (criteria.getMunicipioId() != null) {
                specification = specification.and(buildSpecification(criteria.getMunicipioId(),
                    root -> root.join(DomicilioEmpresa_.municipio, JoinType.LEFT).get(CatMunicipio_.id)));
            }
            if (criteria.getLocalidadId() != null) {
                specification = specification.and(buildSpecification(criteria.getLocalidadId(),
                    root -> root.join(DomicilioEmpresa_.localidad, JoinType.LEFT).get(CatLocalidad_.id)));
            }
            if (criteria.getCpId() != null) {
                specification = specification.and(buildSpecification(criteria.getCpId(),
                    root -> root.join(DomicilioEmpresa_.cp, JoinType.LEFT).get(CatCP_.id)));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(DomicilioEmpresa_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
