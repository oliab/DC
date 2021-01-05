package com.resamx.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A CatCP.
 */
@Entity
@Table(name = "cat_cp")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CatCP implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Size(max = 5)
    private String id;

    @Column(name = "anio")
    private Integer anio;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "catCPS", allowSetters = true)
    private User usuario;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "catCPS", allowSetters = true)
    private CatEstado estado;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "catCPS", allowSetters = true)
    private CatMunicipio municipio;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "catCPS", allowSetters = true)
    private CatRiesgo riesgo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAnio() {
        return anio;
    }

    public CatCP anio(Integer anio) {
        this.anio = anio;
        return this;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public User getUsuario() {
        return usuario;
    }

    public CatCP usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public CatEstado getEstado() {
        return estado;
    }

    public CatCP estado(CatEstado catEstado) {
        this.estado = catEstado;
        return this;
    }

    public void setEstado(CatEstado catEstado) {
        this.estado = catEstado;
    }

    public CatMunicipio getMunicipio() {
        return municipio;
    }

    public CatCP municipio(CatMunicipio catMunicipio) {
        this.municipio = catMunicipio;
        return this;
    }

    public void setMunicipio(CatMunicipio catMunicipio) {
        this.municipio = catMunicipio;
    }

    public CatRiesgo getRiesgo() {
        return riesgo;
    }

    public CatCP riesgo(CatRiesgo catRiesgo) {
        this.riesgo = catRiesgo;
        return this;
    }

    public void setRiesgo(CatRiesgo catRiesgo) {
        this.riesgo = catRiesgo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CatCP)) {
            return false;
        }
        return id != null && id.equals(((CatCP) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatCP{" +
            "id=" + getId() +
            ", anio=" + getAnio() +
            "}";
    }
}
