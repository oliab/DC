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

import com.resamx.domain.ExpedienteCliente;
import com.resamx.domain.*; // for static metamodels
import com.resamx.repository.ExpedienteClienteRepository;
import com.resamx.service.dto.ExpedienteClienteCriteria;

/**
 * Service for executing complex queries for {@link ExpedienteCliente} entities in the database.
 * The main input is a {@link ExpedienteClienteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ExpedienteCliente} or a {@link Page} of {@link ExpedienteCliente} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ExpedienteClienteQueryService extends QueryService<ExpedienteCliente> {

    private final Logger log = LoggerFactory.getLogger(ExpedienteClienteQueryService.class);

    private final ExpedienteClienteRepository expedienteClienteRepository;

    public ExpedienteClienteQueryService(ExpedienteClienteRepository expedienteClienteRepository) {
        this.expedienteClienteRepository = expedienteClienteRepository;
    }

    /**
     * Return a {@link List} of {@link ExpedienteCliente} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ExpedienteCliente> findByCriteria(ExpedienteClienteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ExpedienteCliente> specification = createSpecification(criteria);
        return expedienteClienteRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ExpedienteCliente} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ExpedienteCliente> findByCriteria(ExpedienteClienteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ExpedienteCliente> specification = createSpecification(criteria);
        return expedienteClienteRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ExpedienteClienteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ExpedienteCliente> specification = createSpecification(criteria);
        return expedienteClienteRepository.count(specification);
    }

    /**
     * Function to convert {@link ExpedienteClienteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ExpedienteCliente> createSpecification(ExpedienteClienteCriteria criteria) {
        Specification<ExpedienteCliente> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ExpedienteCliente_.id));
            }
            if (criteria.getEmpresarial() != null) {
                specification = specification.and(buildSpecification(criteria.getEmpresarial(), ExpedienteCliente_.empresarial));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), ExpedienteCliente_.descripcion));
            }
            if (criteria.getFechaAlta() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaAlta(), ExpedienteCliente_.fechaAlta));
            }
            if (criteria.getFechaAct() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaAct(), ExpedienteCliente_.fechaAct));
            }
            if (criteria.getClienteId() != null) {
                specification = specification.and(buildSpecification(criteria.getClienteId(),
                    root -> root.join(ExpedienteCliente_.cliente, JoinType.LEFT).get(Cliente_.id)));
            }
            if (criteria.getTipoDocumentoId() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoDocumentoId(),
                    root -> root.join(ExpedienteCliente_.tipoDocumento, JoinType.LEFT).get(CatTipoDocumento_.id)));
            }
            if (criteria.getUsuarioAltaId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioAltaId(),
                    root -> root.join(ExpedienteCliente_.usuarioAlta, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getUsuarioActId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioActId(),
                    root -> root.join(ExpedienteCliente_.usuarioAct, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
