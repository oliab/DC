package com.resamx.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.resamx.web.rest.TestUtil;

public class CatTipoMonetarioTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CatTipoMonetario.class);
        CatTipoMonetario catTipoMonetario1 = new CatTipoMonetario();
        catTipoMonetario1.setId("1");
        CatTipoMonetario catTipoMonetario2 = new CatTipoMonetario();
        catTipoMonetario2.setId(catTipoMonetario1.getId());
        assertThat(catTipoMonetario1).isEqualTo(catTipoMonetario2);
        catTipoMonetario2.setId("2");
        assertThat(catTipoMonetario1).isNotEqualTo(catTipoMonetario2);
        catTipoMonetario1.setId(null);
        assertThat(catTipoMonetario1).isNotEqualTo(catTipoMonetario2);
    }
}
