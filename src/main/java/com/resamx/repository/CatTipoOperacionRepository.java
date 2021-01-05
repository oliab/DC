package com.resamx.repository;

import com.resamx.domain.CatTipoOperacion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CatTipoOperacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatTipoOperacionRepository extends JpaRepository<CatTipoOperacion, Long>, JpaSpecificationExecutor<CatTipoOperacion> {

    @Query("select catTipoOperacion from CatTipoOperacion catTipoOperacion where catTipoOperacion.usuario.login = ?#{principal.username}")
    List<CatTipoOperacion> findByUsuarioIsCurrentUser();
}
