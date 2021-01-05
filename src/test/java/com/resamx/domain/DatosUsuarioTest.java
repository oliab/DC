package com.resamx.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.resamx.web.rest.TestUtil;

public class DatosUsuarioTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DatosUsuario.class);
        DatosUsuario datosUsuario1 = new DatosUsuario();
        datosUsuario1.setId(1L);
        DatosUsuario datosUsuario2 = new DatosUsuario();
        datosUsuario2.setId(datosUsuario1.getId());
        assertThat(datosUsuario1).isEqualTo(datosUsuario2);
        datosUsuario2.setId(2L);
        assertThat(datosUsuario1).isNotEqualTo(datosUsuario2);
        datosUsuario1.setId(null);
        assertThat(datosUsuario1).isNotEqualTo(datosUsuario2);
    }
}
