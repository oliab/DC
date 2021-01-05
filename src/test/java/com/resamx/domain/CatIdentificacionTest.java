package com.resamx.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.resamx.web.rest.TestUtil;

public class CatIdentificacionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CatIdentificacion.class);
        CatIdentificacion catIdentificacion1 = new CatIdentificacion();
        catIdentificacion1.setId(1L);
        CatIdentificacion catIdentificacion2 = new CatIdentificacion();
        catIdentificacion2.setId(catIdentificacion1.getId());
        assertThat(catIdentificacion1).isEqualTo(catIdentificacion2);
        catIdentificacion2.setId(2L);
        assertThat(catIdentificacion1).isNotEqualTo(catIdentificacion2);
        catIdentificacion1.setId(null);
        assertThat(catIdentificacion1).isNotEqualTo(catIdentificacion2);
    }
}
