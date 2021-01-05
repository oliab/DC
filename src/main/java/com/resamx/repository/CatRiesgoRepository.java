package com.resamx.repository;

import com.resamx.domain.CatRiesgo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CatRiesgo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatRiesgoRepository extends JpaRepository<CatRiesgo, Long>, JpaSpecificationExecutor<CatRiesgo> {

    @Query("select catRiesgo from CatRiesgo catRiesgo where catRiesgo.usuario.login = ?#{principal.username}")
    List<CatRiesgo> findByUsuarioIsCurrentUser();
}
