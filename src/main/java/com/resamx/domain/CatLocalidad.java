package com.resamx.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A CatLocalidad.
 */
@Entity
@Table(name = "cat_localidad")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CatLocalidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Size(max = 9)
    private String id;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @NotNull
    @Size(max = 4)
    @Column(name = "clave", length = 4, nullable = false)
    private String clave;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "catLocalidads", allowSetters = true)
    private User usuario;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "catLocalidads", allowSetters = true)
    private CatEstado estado;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "catLocalidads", allowSetters = true)
    private CatMunicipio municipio;

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

    public CatLocalidad nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public CatLocalidad clave(String clave) {
        this.clave = clave;
        return this;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public User getUsuario() {
        return usuario;
    }

    public CatLocalidad usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public CatEstado getEstado() {
        return estado;
    }

    public CatLocalidad estado(CatEstado catEstado) {
        this.estado = catEstado;
        return this;
    }

    public void setEstado(CatEstado catEstado) {
        this.estado = catEstado;
    }

    public CatMunicipio getMunicipio() {
        return municipio;
    }

    public CatLocalidad municipio(CatMunicipio catMunicipio) {
        this.municipio = catMunicipio;
        return this;
    }

    public void setMunicipio(CatMunicipio catMunicipio) {
        this.municipio = catMunicipio;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CatLocalidad)) {
            return false;
        }
        return id != null && id.equals(((CatLocalidad) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatLocalidad{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", clave='" + getClave() + "'" +
            "}";
    }
}
