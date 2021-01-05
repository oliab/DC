package com.resamx.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A CatPais.
 */
@Entity
@Table(name = "cat_pais")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CatPais implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Size(max = 3)
    private String id;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @NotNull
    @Size(max = 2)
    @Column(name = "codigo_a_2", length = 2, nullable = false)
    private String codigoA2;

    @NotNull
    @Size(max = 3)
    @Column(name = "codigo_a_3", length = 3, nullable = false)
    private String codigoA3;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "catPais", allowSetters = true)
    private User usuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public CatPais nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoA2() {
        return codigoA2;
    }

    public CatPais codigoA2(String codigoA2) {
        this.codigoA2 = codigoA2;
        return this;
    }

    public void setCodigoA2(String codigoA2) {
        this.codigoA2 = codigoA2;
    }

    public String getCodigoA3() {
        return codigoA3;
    }

    public CatPais codigoA3(String codigoA3) {
        this.codigoA3 = codigoA3;
        return this;
    }

    public void setCodigoA3(String codigoA3) {
        this.codigoA3 = codigoA3;
    }

    public User getUsuario() {
        return usuario;
    }

    public CatPais usuario(User user) {
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
        if (!(o instanceof CatPais)) {
            return false;
        }
        return id != null && id.equals(((CatPais) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatPais{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", codigoA2='" + getCodigoA2() + "'" +
            ", codigoA3='" + getCodigoA3() + "'" +
            "}";
    }
}
