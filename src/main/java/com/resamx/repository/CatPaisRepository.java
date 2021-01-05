package com.resamx.repository;

import com.resamx.domain.CatPais;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CatPais entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatPaisRepository extends JpaRepository<CatPais, String>, JpaSpecificationExecutor<CatPais> {

    @Query("select catPais from CatPais catPais where catPais.usuario.login = ?#{principal.username}")
    List<CatPais> findByUsuarioIsCurrentUser();
}
