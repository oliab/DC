package com.resamx.repository;

import com.resamx.domain.CatSector;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CatSector entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatSectorRepository extends JpaRepository<CatSector, String>, JpaSpecificationExecutor<CatSector> {

    @Query("select catSector from CatSector catSector where catSector.usuario.login = ?#{principal.username}")
    List<CatSector> findByUsuarioIsCurrentUser();
}
