package com.resamx.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.resamx.web.rest.TestUtil;

public class CatNacionalidadTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CatNacionalidad.class);
        CatNacionalidad catNacionalidad1 = new CatNacionalidad();
        catNacionalidad1.setId(1L);
        CatNacionalidad catNacionalidad2 = new CatNacionalidad();
        catNacionalidad2.setId(catNacionalidad1.getId());
        assertThat(catNacionalidad1).isEqualTo(catNacionalidad2);
        catNacionalidad2.setId(2L);
        assertThat(catNacionalidad1).isNotEqualTo(catNacionalidad2);
        catNacionalidad1.setId(null);
        assertThat(catNacionalidad1).isNotEqualTo(catNacionalidad2);
    }
}
