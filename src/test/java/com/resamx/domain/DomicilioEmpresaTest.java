package com.resamx.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.resamx.web.rest.TestUtil;

public class DomicilioEmpresaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DomicilioEmpresa.class);
        DomicilioEmpresa domicilioEmpresa1 = new DomicilioEmpresa();
        domicilioEmpresa1.setId(1L);
        DomicilioEmpresa domicilioEmpresa2 = new DomicilioEmpresa();
        domicilioEmpresa2.setId(domicilioEmpresa1.getId());
        assertThat(domicilioEmpresa1).isEqualTo(domicilioEmpresa2);
        domicilioEmpresa2.setId(2L);
        assertThat(domicilioEmpresa1).isNotEqualTo(domicilioEmpresa2);
        domicilioEmpresa1.setId(null);
        assertThat(domicilioEmpresa1).isNotEqualTo(domicilioEmpresa2);
    }
}
