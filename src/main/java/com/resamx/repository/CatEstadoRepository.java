package com.resamx.repository;

import com.resamx.domain.CatEstado;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CatEstado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatEstadoRepository extends JpaRepository<CatEstado, String>, JpaSpecificationExecutor<CatEstado> {

    @Query("select catEstado from CatEstado catEstado where catEstado.usuario.login = ?#{principal.username}")
    List<CatEstado> findByUsuarioIsCurrentUser();
}
