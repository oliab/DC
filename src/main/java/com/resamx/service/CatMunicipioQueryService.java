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

import com.resamx.domain.CatMunicipio;
import com.resamx.domain.*; // for static metamodels
import com.resamx.repository.CatMunicipioRepository;
import com.resamx.service.dto.CatMunicipioCriteria;

/**
 * Service for executing complex queries for {@link CatMunicipio} entities in the database.
 * The main input is a {@link CatMunicipioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CatMunicipio} or a {@link Page} of {@link CatMunicipio} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CatMunicipioQueryService extends QueryService<CatMunicipio> {

    private final Logger log = LoggerFactory.getLogger(CatMunicipioQueryService.class);

    private final CatMunicipioRepository catMunicipioRepository;

    public CatMunicipioQueryService(CatMunicipioRepository catMunicipioRepository) {
        this.catMunicipioRepository = catMunicipioRepository;
    }

    /**
     * Return a {@link List} of {@link CatMunicipio} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CatMunicipio> findByCriteria(CatMunicipioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CatMunicipio> specification = createSpecification(criteria);
        return catMunicipioRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CatMunicipio} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CatMunicipio> findByCriteria(CatMunicipioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CatMunicipio> specification = createSpecification(criteria);
        return catMunicipioRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CatMunicipioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CatMunicipio> specification = createSpecification(criteria);
        return catMunicipioRepository.count(specification);
    }

    /**
     * Function to convert {@link CatMunicipioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CatMunicipio> createSpecification(CatMunicipioCriteria criteria) {
        Specification<CatMunicipio> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getId(), CatMunicipio_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), CatMunicipio_.nombre));
            }
            if (criteria.getClave() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClave(), CatMunicipio_.clave));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioId(),
                    root -> root.join(CatMunicipio_.usuario, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getEstadoId() != null) {
                specification = specification.and(buildSpecification(criteria.getEstadoId(),
                    root -> root.join(CatMunicipio_.estado, JoinType.LEFT).get(CatEstado_.id)));
            }
        }
        return specification;
    }
}
