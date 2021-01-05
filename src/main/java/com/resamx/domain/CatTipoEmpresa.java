package com.resamx.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A CatTipoEmpresa.
 */
@Entity
@Table(name = "cat_tipo_empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CatTipoEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "tipo", length = 100, nullable = false)
    private String tipo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "catTipoEmpresas", allowSetters = true)
    private User usuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public CatTipoEmpresa tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public User getUsuario() {
        return usuario;
    }

    public CatTipoEmpresa usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CatTipoEmpresa)) {
            return false;
        }
        return id != null && id.equals(((CatTipoEmpresa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatTipoEmpresa{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            "}";
    }
}
