package com.resamx.repository;

import com.resamx.domain.CatTipoEmpresa;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CatTipoEmpresa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatTipoEmpresaRepository extends JpaRepository<CatTipoEmpresa, Long>, JpaSpecificationExecutor<CatTipoEmpresa> {

    @Query("select catTipoEmpresa from CatTipoEmpresa catTipoEmpresa where catTipoEmpresa.usuario.login = ?#{principal.username}")
    List<CatTipoEmpresa> findByUsuarioIsCurrentUser();
}
