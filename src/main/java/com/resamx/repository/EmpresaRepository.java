package com.resamx.repository;

import com.resamx.domain.Empresa;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Empresa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long>, JpaSpecificationExecutor<Empresa> {

    @Query("select empresa from Empresa empresa where empresa.usuarioAlta.login = ?#{principal.username}")
    List<Empresa> findByUsuarioAltaIsCurrentUser();

    @Query("select empresa from Empresa empresa where empresa.usuarioAct.login = ?#{principal.username}")
    List<Empresa> findByUsuarioActIsCurrentUser();
}
