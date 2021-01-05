package com.resamx.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A CatEstado.
 */
@Entity
@Table(name = "cat_estado")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CatEstado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Size(max = 3)
    private String id;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "catEstados", allowSetters = true)
    private User usuario;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "catEstados", allowSetters = true)
    private CatPais pais;

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

    public CatEstado nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public User getUsuario() {
        return usuario;
    }

    public CatEstado usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public CatPais getPais() {
        return pais;
    }

    public CatEstado pais(CatPais catPais) {
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
        if (!(o instanceof CatEstado)) {
            return false;
        }
        return id != null && id.equals(((CatEstado) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatEstado{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
