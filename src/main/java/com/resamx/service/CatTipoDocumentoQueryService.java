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

import com.resamx.domain.CatTipoDocumento;
import com.resamx.domain.*; // for static metamodels
import com.resamx.repository.CatTipoDocumentoRepository;
import com.resamx.service.dto.CatTipoDocumentoCriteria;

/**
 * Service for executing complex queries for {@link CatTipoDocumento} entities in the database.
 * The main input is a {@link CatTipoDocumentoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CatTipoDocumento} or a {@link Page} of {@link CatTipoDocumento} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CatTipoDocumentoQueryService extends QueryService<CatTipoDocumento> {

    private final Logger log = LoggerFactory.getLogger(CatTipoDocumentoQueryService.class);

    private final CatTipoDocumentoRepository catTipoDocumentoRepository;

    public CatTipoDocumentoQueryService(CatTipoDocumentoRepository catTipoDocumentoRepository) {
        this.catTipoDocumentoRepository = catTipoDocumentoRepository;
    }

    /**
     * Return a {@link List} of {@link CatTipoDocumento} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CatTipoDocumento> findByCriteria(CatTipoDocumentoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CatTipoDocumento> specification = createSpecification(criteria);
        return catTipoDocumentoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CatTipoDocumento} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CatTipoDocumento> findByCriteria(CatTipoDocumentoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CatTipoDocumento> specification = createSpecification(criteria);
        return catTipoDocumentoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CatTipoDocumentoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CatTipoDocumento> specification = createSpecification(criteria);
        return catTipoDocumentoRepository.count(specification);
    }

    /**
     * Function to convert {@link CatTipoDocumentoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CatTipoDocumento> createSpecification(CatTipoDocumentoCriteria criteria) {
        Specification<CatTipoDocumento> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CatTipoDocumento_.id));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipo(), CatTipoDocumento_.tipo));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsuarioId(),
                    root -> root.join(CatTipoDocumento_.usuario, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
