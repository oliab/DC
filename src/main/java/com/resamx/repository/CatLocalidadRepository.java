package com.resamx.repository;

import com.resamx.domain.CatLocalidad;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CatLocalidad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatLocalidadRepository extends JpaRepository<CatLocalidad, String>, JpaSpecificationExecutor<CatLocalidad> {

    @Query("select catLocalidad from CatLocalidad catLocalidad where catLocalidad.usuario.login = ?#{principal.username}")
    List<CatLocalidad> findByUsuarioIsCurrentUser();
}
