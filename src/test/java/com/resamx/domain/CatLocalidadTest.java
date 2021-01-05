package com.resamx.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.resamx.web.rest.TestUtil;

public class CatLocalidadTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CatLocalidad.class);
        CatLocalidad catLocalidad1 = new CatLocalidad();
        catLocalidad1.setId("1");
        CatLocalidad catLocalidad2 = new CatLocalidad();
        catLocalidad2.setId(catLocalidad1.getId());
        assertThat(catLocalidad1).isEqualTo(catLocalidad2);
        catLocalidad2.setId("2");
        assertThat(catLocalidad1).isNotEqualTo(catLocalidad2);
        catLocalidad1.setId(null);
        assertThat(catLocalidad1).isNotEqualTo(catLocalidad2);
    }
}
