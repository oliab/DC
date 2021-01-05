package com.resamx.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A CatNacionalidad.
 */
@Entity
@Table(name = "cat_nacionalidad")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CatNacionalidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "nacionalidad", length = 100, nullable = false)
    private String nacionalidad;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "catNacionalidads", allowSetters = true)
    private User usuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public CatNacionalidad nacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
        return this;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public User getUsuario() {
        return usuario;
    }

    public CatNacionalidad usuario(User user) {
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
        if (!(o instanceof CatNacionalidad)) {
            return false;
        }
        return id != null && id.equals(((CatNacionalidad) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatNacionalidad{" +
            "id=" + getId() +
            ", nacionalidad='" + getNacionalidad() + "'" +
            "}";
    }
}
