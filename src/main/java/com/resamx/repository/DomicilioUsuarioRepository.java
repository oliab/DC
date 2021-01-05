package com.resamx.repository;

import com.resamx.domain.DomicilioUsuario;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DomicilioUsuario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DomicilioUsuarioRepository extends JpaRepository<DomicilioUsuario, Long>, JpaSpecificationExecutor<DomicilioUsuario> {
}
