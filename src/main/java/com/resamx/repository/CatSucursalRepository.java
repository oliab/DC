package com.resamx.repository;

import com.resamx.domain.CatSucursal;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CatSucursal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatSucursalRepository extends JpaRepository<CatSucursal, Long>, JpaSpecificationExecutor<CatSucursal> {

    @Query("select catSucursal from CatSucursal catSucursal where catSucursal.usuario.login = ?#{principal.username}")
    List<CatSucursal> findByUsuarioIsCurrentUser();
}
