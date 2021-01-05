package com.resamx.repository;

import com.resamx.domain.CatMunicipio;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CatMunicipio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatMunicipioRepository extends JpaRepository<CatMunicipio, String>, JpaSpecificationExecutor<CatMunicipio> {

    @Query("select catMunicipio from CatMunicipio catMunicipio where catMunicipio.usuario.login = ?#{principal.username}")
    List<CatMunicipio> findByUsuarioIsCurrentUser();
}
