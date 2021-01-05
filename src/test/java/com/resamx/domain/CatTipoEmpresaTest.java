package com.resamx.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.resamx.web.rest.TestUtil;

public class CatTipoEmpresaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CatTipoEmpresa.class);
        CatTipoEmpresa catTipoEmpresa1 = new CatTipoEmpresa();
        catTipoEmpresa1.setId(1L);
        CatTipoEmpresa catTipoEmpresa2 = new CatTipoEmpresa();
        catTipoEmpresa2.setId(catTipoEmpresa1.getId());
        assertThat(catTipoEmpresa1).isEqualTo(catTipoEmpresa2);
        catTipoEmpresa2.setId(2L);
        assertThat(catTipoEmpresa1).isNotEqualTo(catTipoEmpresa2);
        catTipoEmpresa1.setId(null);
        assertThat(catTipoEmpresa1).isNotEqualTo(catTipoEmpresa2);
    }
}
