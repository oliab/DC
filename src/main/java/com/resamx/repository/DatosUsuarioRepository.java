package com.resamx.repository;

import com.resamx.domain.DatosUsuario;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DatosUsuario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DatosUsuarioRepository extends JpaRepository<DatosUsuario, Long>, JpaSpecificationExecutor<DatosUsuario> {
}
