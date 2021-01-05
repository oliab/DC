package com.resamx.repository;

import com.resamx.domain.ExpedienteCliente;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ExpedienteCliente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExpedienteClienteRepository extends JpaRepository<ExpedienteCliente, Long>, JpaSpecificationExecutor<ExpedienteCliente> {

    @Query("select expedienteCliente from ExpedienteCliente expedienteCliente where expedienteCliente.usuarioAlta.login = ?#{principal.username}")
    List<ExpedienteCliente> findByUsuarioAltaIsCurrentUser();

    @Query("select expedienteCliente from ExpedienteCliente expedienteCliente where expedienteCliente.usuarioAct.login = ?#{principal.username}")
    List<ExpedienteCliente> findByUsuarioActIsCurrentUser();
}
