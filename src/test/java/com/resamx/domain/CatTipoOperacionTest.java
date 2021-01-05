package com.resamx.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.resamx.web.rest.TestUtil;

public class CatTipoOperacionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CatTipoOperacion.class);
        CatTipoOperacion catTipoOperacion1 = new CatTipoOperacion();
        catTipoOperacion1.setId(1L);
        CatTipoOperacion catTipoOperacion2 = new CatTipoOperacion();
        catTipoOperacion2.setId(catTipoOperacion1.getId());
        assertThat(catTipoOperacion1).isEqualTo(catTipoOperacion2);
        catTipoOperacion2.setId(2L);
        assertThat(catTipoOperacion1).isNotEqualTo(catTipoOperacion2);
        catTipoOperacion1.setId(null);
        assertThat(catTipoOperacion1).isNotEqualTo(catTipoOperacion2);
    }
}
