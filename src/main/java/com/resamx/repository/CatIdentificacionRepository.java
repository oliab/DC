package com.resamx.repository;

import com.resamx.domain.CatIdentificacion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CatIdentificacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatIdentificacionRepository extends JpaRepository<CatIdentificacion, Long>, JpaSpecificationExecutor<CatIdentificacion> {

    @Query("select catIdentificacion from CatIdentificacion catIdentificacion where catIdentificacion.usuario.login = ?#{principal.username}")
    List<CatIdentificacion> findByUsuarioIsCurrentUser();
}
