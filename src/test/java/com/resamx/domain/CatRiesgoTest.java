package com.resamx.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.resamx.web.rest.TestUtil;

public class CatRiesgoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CatRiesgo.class);
        CatRiesgo catRiesgo1 = new CatRiesgo();
        catRiesgo1.setId(1L);
        CatRiesgo catRiesgo2 = new CatRiesgo();
        catRiesgo2.setId(catRiesgo1.getId());
        assertThat(catRiesgo1).isEqualTo(catRiesgo2);
        catRiesgo2.setId(2L);
        assertThat(catRiesgo1).isNotEqualTo(catRiesgo2);
        catRiesgo1.setId(null);
        assertThat(catRiesgo1).isNotEqualTo(catRiesgo2);
    }
}
