package com.resamx.repository;

import com.resamx.domain.Cliente;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Cliente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>, JpaSpecificationExecutor<Cliente> {

    @Query("select cliente from Cliente cliente where cliente.usuarioAlta.login = ?#{principal.username}")
    List<Cliente> findByUsuarioAltaIsCurrentUser();

    @Query("select cliente from Cliente cliente where cliente.usuarioAct.login = ?#{principal.username}")
    List<Cliente> findByUsuarioActIsCurrentUser();
}
