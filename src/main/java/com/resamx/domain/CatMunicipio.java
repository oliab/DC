package com.resamx.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A CatMunicipio.
 */
@Entity
@Table(name = "cat_municipio")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CatMunicipio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Size(max = 5)
    private String id;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @NotNull
    @Size(max = 3)
    @Column(name = "clave", length = 3, nullable = false)
    private String clave;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "catMunicipios", allowSetters = true)
    private User usuario;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "catMunicipios", allowSetters = true)
    private CatEstado estado;

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

    public CatMunicipio nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public CatMunicipio clave(String clave) {
        this.clave = clave;
        return this;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public User getUsuario() {
        return usuario;
    }

    public CatMunicipio usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public CatEstado getEstado() {
        return estado;
    }

    public CatMunicipio estado(CatEstado catEstado) {
        this.estado = catEstado;
        return this;
    }

    public void setEstado(CatEstado catEstado) {
        this.estado = catEstado;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CatMunicipio)) {
            return false;
        }
        return id != null && id.equals(((CatMunicipio) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatMunicipio{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", clave='" + getClave() + "'" +
            "}";
    }
}
