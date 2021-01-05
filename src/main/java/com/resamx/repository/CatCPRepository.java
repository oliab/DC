package com.resamx.repository;

import com.resamx.domain.CatCP;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CatCP entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatCPRepository extends JpaRepository<CatCP, String>, JpaSpecificationExecutor<CatCP> {

    @Query("select catCP from CatCP catCP where catCP.usuario.login = ?#{principal.username}")
    List<CatCP> findByUsuarioIsCurrentUser();
}
