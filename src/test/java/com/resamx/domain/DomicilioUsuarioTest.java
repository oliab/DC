package com.resamx.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.resamx.web.rest.TestUtil;

public class DomicilioUsuarioTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DomicilioUsuario.class);
        DomicilioUsuario domicilioUsuario1 = new DomicilioUsuario();
        domicilioUsuario1.setId(1L);
        DomicilioUsuario domicilioUsuario2 = new DomicilioUsuario();
        domicilioUsuario2.setId(domicilioUsuario1.getId());
        assertThat(domicilioUsuario1).isEqualTo(domicilioUsuario2);
        domicilioUsuario2.setId(2L);
        assertThat(domicilioUsuario1).isNotEqualTo(domicilioUsuario2);
        domicilioUsuario1.setId(null);
        assertThat(domicilioUsuario1).isNotEqualTo(domicilioUsuario2);
    }
}
