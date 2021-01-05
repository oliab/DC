package com.resamx.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.resamx.web.rest.TestUtil;

public class CatPaisTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CatPais.class);
        CatPais catPais1 = new CatPais();
        catPais1.setId("1");
        CatPais catPais2 = new CatPais();
        catPais2.setId(catPais1.getId());
        assertThat(catPais1).isEqualTo(catPais2);
        catPais2.setId("2");
        assertThat(catPais1).isNotEqualTo(catPais2);
        catPais1.setId(null);
        assertThat(catPais1).isNotEqualTo(catPais2);
    }
}
