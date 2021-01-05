package com.resamx.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.resamx.web.rest.TestUtil;

public class CatMonedaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CatMoneda.class);
        CatMoneda catMoneda1 = new CatMoneda();
        catMoneda1.setId("1");
        CatMoneda catMoneda2 = new CatMoneda();
        catMoneda2.setId(catMoneda1.getId());
        assertThat(catMoneda1).isEqualTo(catMoneda2);
        catMoneda2.setId("2");
        assertThat(catMoneda1).isNotEqualTo(catMoneda2);
        catMoneda1.setId(null);
        assertThat(catMoneda1).isNotEqualTo(catMoneda2);
    }
}
