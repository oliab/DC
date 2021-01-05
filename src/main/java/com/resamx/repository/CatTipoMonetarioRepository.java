package com.resamx.repository;

import com.resamx.domain.CatTipoMonetario;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CatTipoMonetario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatTipoMonetarioRepository extends JpaRepository<CatTipoMonetario, String>, JpaSpecificationExecutor<CatTipoMonetario> {

    @Query("select catTipoMonetario from CatTipoMonetario catTipoMonetario where catTipoMonetario.usuario.login = ?#{principal.username}")
    List<CatTipoMonetario> findByUsuarioIsCurrentUser();
}
