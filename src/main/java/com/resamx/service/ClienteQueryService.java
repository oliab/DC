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

import com.resamx.domain.Cliente;
import com.resamx.domain.*; // for static metamodels
import com.resamx.repository.ClienteRepository;
import com.resamx.service.dto.ClienteCriteria;

/**
 * Service for executing complex queries for {@link Cliente} entities in the database.
 * The main input is a {@link ClienteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Cliente} or a {@link Page} of {@link Cliente} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClienteQueryService extends QueryService<Cliente> {

    private final Logger log = LoggerFactory.getLogger(ClienteQueryService.class);

    private final ClienteRepository clienteRepository;

    public ClienteQueryService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /**
     * Return a {@link List} of {@link Cliente} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Cliente> findByCriteria(ClienteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Cliente> specification = createSpecification(criteria);
        return clienteRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Cliente} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Cliente> findByCriteria(ClienteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Cliente> specification = createSpecification(criteria);
        return clienteRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClienteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Cliente> specification = createSpecification(criteria);
        return clienteRepository.count(specification);
    }

    /**
     * Function to convert {@link ClienteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Cliente> createSpecification(ClienteCriteria criteria) {
        Specification<Cliente> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Cliente_.id));
            }
            if (criteria.getNoIdentificacion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNoIdentificacion(), Cliente_.noIdentificacion));
            }
            if (criteria.getIngresos() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIngresos(), Cliente_.ingresos));
            }
            if (criteria.getEstimacionOperacion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEstimacionOperacion(), Cliente_.estimacionOperacion));
            }
            if (criteria.getTelefono() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefono(), Cliente_.telefono));
            }
            if (criteria.getFechaAlta() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaAlta(), Cliente_.fechaAlta));
            }
            if (criteria.getFechaAct() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaAct(), Cliente_.fechaAct));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioId(),
                    root -> root.join(Cliente_.usuario, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmpresaId(),
                    root -> root.join(Cliente_.empresa, JoinType.LEFT).get(Empresa_.id)));
            }
            if (criteria.getTipoClienteId() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoClienteId(),
                    root -> root.join(Cliente_.tipoCliente, JoinType.LEFT).get(CatTipoEmpresa_.id)));
            }
            if (criteria.getTipoIdentificacionId() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoIdentificacionId(),
                    root -> root.join(Cliente_.tipoIdentificacion, JoinType.LEFT).get(CatIdentificacion_.id)));
            }
            if (criteria.getSectorId() != null) {
                specification = specification.and(buildSpecification(criteria.getSectorId(),
                    root -> root.join(Cliente_.sector, JoinType.LEFT).get(CatSector_.id)));
            }
            if (criteria.getMonedaId() != null) {
                specification = specification.and(buildSpecification(criteria.getMonedaId(),
                    root -> root.join(Cliente_.moneda, JoinType.LEFT).get(CatMoneda_.id)));
            }
            if (criteria.getUsuarioAltaId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioAltaId(),
                    root -> root.join(Cliente_.usuarioAlta, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getUsuarioActId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioActId(),
                    root -> root.join(Cliente_.usuarioAct, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getExpedienteId() != null) {
                specification = specification.and(buildSpecification(criteria.getExpedienteId(),
                    root -> root.join(Cliente_.expedientes, JoinType.LEFT).get(ExpedienteCliente_.id)));
            }
        }
        return specification;
    }
}
