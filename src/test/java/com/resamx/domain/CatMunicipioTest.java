package com.resamx.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.resamx.web.rest.TestUtil;

public class CatMunicipioTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CatMunicipio.class);
        CatMunicipio catMunicipio1 = new CatMunicipio();
        catMunicipio1.setId("1");
        CatMunicipio catMunicipio2 = new CatMunicipio();
        catMunicipio2.setId(catMunicipio1.getId());
        assertThat(catMunicipio1).isEqualTo(catMunicipio2);
        catMunicipio2.setId("2");
        assertThat(catMunicipio1).isNotEqualTo(catMunicipio2);
        catMunicipio1.setId(null);
        assertThat(catMunicipio1).isNotEqualTo(catMunicipio2);
    }
}
