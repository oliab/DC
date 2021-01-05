package com.resamx.repository;

import com.resamx.domain.CatNacionalidad;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CatNacionalidad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatNacionalidadRepository extends JpaRepository<CatNacionalidad, Long>, JpaSpecificationExecutor<CatNacionalidad> {

    @Query("select catNacionalidad from CatNacionalidad catNacionalidad where catNacionalidad.usuario.login = ?#{principal.username}")
    List<CatNacionalidad> findByUsuarioIsCurrentUser();
}
