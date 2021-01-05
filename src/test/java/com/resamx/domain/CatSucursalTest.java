package com.resamx.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.resamx.web.rest.TestUtil;

public class CatSucursalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CatSucursal.class);
        CatSucursal catSucursal1 = new CatSucursal();
        catSucursal1.setId(1L);
        CatSucursal catSucursal2 = new CatSucursal();
        catSucursal2.setId(catSucursal1.getId());
        assertThat(catSucursal1).isEqualTo(catSucursal2);
        catSucursal2.setId(2L);
        assertThat(catSucursal1).isNotEqualTo(catSucursal2);
        catSucursal1.setId(null);
        assertThat(catSucursal1).isNotEqualTo(catSucursal2);
    }
}
