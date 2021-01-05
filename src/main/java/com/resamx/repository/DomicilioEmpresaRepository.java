package com.resamx.repository;

import com.resamx.domain.DomicilioEmpresa;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DomicilioEmpresa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DomicilioEmpresaRepository extends JpaRepository<DomicilioEmpresa, Long>, JpaSpecificationExecutor<DomicilioEmpresa> {
}
