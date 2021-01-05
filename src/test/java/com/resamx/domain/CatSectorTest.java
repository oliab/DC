package com.resamx.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.resamx.web.rest.TestUtil;

public class CatSectorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CatSector.class);
        CatSector catSector1 = new CatSector();
        catSector1.setId("1");
        CatSector catSector2 = new CatSector();
        catSector2.setId(catSector1.getId());
        assertThat(catSector1).isEqualTo(catSector2);
        catSector2.setId("2");
        assertThat(catSector1).isNotEqualTo(catSector2);
        catSector1.setId(null);
        assertThat(catSector1).isNotEqualTo(catSector2);
    }
}
