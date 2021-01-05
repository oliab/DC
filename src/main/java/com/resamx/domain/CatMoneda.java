package com.resamx.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A CatMoneda.
 */
@Entity
@Table(name = "cat_moneda")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CatMoneda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Size(max = 100)
    private String id;

    @NotNull
    @Size(max = 100)
    @Column(name = "moneda", length = 100, nullable = false)
    private String moneda;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "catMonedas", allowSetters = true)
    private User usuario;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "catMonedas", allowSetters = true)
    private CatPais pais;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMoneda() {
        return moneda;
    }

    public CatMoneda moneda(String moneda) {
        this.moneda = moneda;
        return this;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public User getUsuario() {
        return usuario;
    }

    public CatMoneda usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public CatPais getPais() {
        return pais;
    }

    public CatMoneda pais(CatPais catPais) {
        this.pais = catPais;
        return this;
    }

    public void setPais(CatPais catPais) {
        this.pais = catPais;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CatMoneda)) {
            return false;
        }
        return id != null && id.equals(((CatMoneda) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatMoneda{" +
            "id=" + getId() +
            ", moneda='" + getMoneda() + "'" +
            "}";
    }
}
