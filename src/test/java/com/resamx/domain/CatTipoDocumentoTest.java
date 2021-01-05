package com.resamx.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.resamx.web.rest.TestUtil;

public class CatTipoDocumentoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CatTipoDocumento.class);
        CatTipoDocumento catTipoDocumento1 = new CatTipoDocumento();
        catTipoDocumento1.setId(1L);
        CatTipoDocumento catTipoDocumento2 = new CatTipoDocumento();
        catTipoDocumento2.setId(catTipoDocumento1.getId());
        assertThat(catTipoDocumento1).isEqualTo(catTipoDocumento2);
        catTipoDocumento2.setId(2L);
        assertThat(catTipoDocumento1).isNotEqualTo(catTipoDocumento2);
        catTipoDocumento1.setId(null);
        assertThat(catTipoDocumento1).isNotEqualTo(catTipoDocumento2);
    }
}
