package com.resamx.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.resamx.web.rest.TestUtil;

public class CatCPTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CatCP.class);
        CatCP catCP1 = new CatCP();
        catCP1.setId("1");
        CatCP catCP2 = new CatCP();
        catCP2.setId(catCP1.getId());
        assertThat(catCP1).isEqualTo(catCP2);
        catCP2.setId("2");
        assertThat(catCP1).isNotEqualTo(catCP2);
        catCP1.setId(null);
        assertThat(catCP1).isNotEqualTo(catCP2);
    }
}
