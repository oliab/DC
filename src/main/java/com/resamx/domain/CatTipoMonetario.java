package com.resamx.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A CatTipoMonetario.
 */
@Entity
@Table(name = "cat_tipo_monetario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CatTipoMonetario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Size(max = 3)
    private String id;

    @NotNull
    @Size(max = 100)
    @Column(name = "instrumento", length = 100, nullable = false)
    private String instrumento;

    @Size(max = 150)
    @Column(name = "descripcion", length = 150)
    private String descripcion;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "catTipoMonetarios", allowSetters = true)
    private User usuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstrumento() {
        return instrumento;
    }

    public CatTipoMonetario instrumento(String instrumento) {
        this.instrumento = instrumento;
        return this;
    }

    public void setInstrumento(String instrumento) {
        this.instrumento = instrumento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public CatTipoMonetario descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public User getUsuario() {
        return usuario;
    }

    public CatTipoMonetario usuario(User user) {
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
        if (!(o instanceof CatTipoMonetario)) {
            return false;
        }
        return id != null && id.equals(((CatTipoMonetario) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatTipoMonetario{" +
            "id=" + getId() +
            ", instrumento='" + getInstrumento() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
