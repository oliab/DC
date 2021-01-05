package com.resamx.repository;

import com.resamx.domain.CatTipoDocumento;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CatTipoDocumento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatTipoDocumentoRepository extends JpaRepository<CatTipoDocumento, Long>, JpaSpecificationExecutor<CatTipoDocumento> {

    @Query("select catTipoDocumento from CatTipoDocumento catTipoDocumento where catTipoDocumento.usuario.login = ?#{principal.username}")
    List<CatTipoDocumento> findByUsuarioIsCurrentUser();
}
