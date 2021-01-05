package com.resamx.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.resamx.web.rest.TestUtil;

public class ExpedienteClienteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExpedienteCliente.class);
        ExpedienteCliente expedienteCliente1 = new ExpedienteCliente();
        expedienteCliente1.setId(1L);
        ExpedienteCliente expedienteCliente2 = new ExpedienteCliente();
        expedienteCliente2.setId(expedienteCliente1.getId());
        assertThat(expedienteCliente1).isEqualTo(expedienteCliente2);
        expedienteCliente2.setId(2L);
        assertThat(expedienteCliente1).isNotEqualTo(expedienteCliente2);
        expedienteCliente1.setId(null);
        assertThat(expedienteCliente1).isNotEqualTo(expedienteCliente2);
    }
}
