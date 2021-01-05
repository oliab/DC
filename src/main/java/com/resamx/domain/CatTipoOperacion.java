package com.resamx.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A CatTipoOperacion.
 */
@Entity
@Table(name = "cat_tipo_operacion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CatTipoOperacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "operacion", length = 100, nullable = false)
    private String operacion;

    @Size(max = 150)
    @Column(name = "descripcion", length = 150)
    private String descripcion;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "catTipoOperacions", allowSetters = true)
    private User usuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperacion() {
        return operacion;
    }

    public CatTipoOperacion operacion(String operacion) {
        this.operacion = operacion;
        return this;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public CatTipoOperacion descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public User getUsuario() {
        return usuario;
    }

    public CatTipoOperacion usuario(User user) {
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
        if (!(o instanceof CatTipoOperacion)) {
            return false;
        }
        return id != null && id.equals(((CatTipoOperacion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatTipoOperacion{" +
            "id=" + getId() +
            ", operacion='" + getOperacion() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
