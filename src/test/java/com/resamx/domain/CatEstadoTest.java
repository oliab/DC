package com.resamx.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.resamx.web.rest.TestUtil;

public class CatEstadoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CatEstado.class);
        CatEstado catEstado1 = new CatEstado();
        catEstado1.setId("1");
        CatEstado catEstado2 = new CatEstado();
        catEstado2.setId(catEstado1.getId());
        assertThat(catEstado1).isEqualTo(catEstado2);
        catEstado2.setId("2");
        assertThat(catEstado1).isNotEqualTo(catEstado2);
        catEstado1.setId(null);
        assertThat(catEstado1).isNotEqualTo(catEstado2);
    }
}
