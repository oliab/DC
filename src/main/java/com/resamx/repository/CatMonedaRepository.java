package com.resamx.repository;

import com.resamx.domain.CatMoneda;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CatMoneda entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatMonedaRepository extends JpaRepository<CatMoneda, String>, JpaSpecificationExecutor<CatMoneda> {

    @Query("select catMoneda from CatMoneda catMoneda where catMoneda.usuario.login = ?#{principal.username}")
    List<CatMoneda> findByUsuarioIsCurrentUser();
}
