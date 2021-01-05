package com.resamx.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A CatRiesgo.
 */
@Entity
@Table(name = "cat_riesgo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CatRiesgo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "riesgo", length = 50, nullable = false)
    private String riesgo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "catRiesgos", allowSetters = true)
    private User usuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRiesgo() {
        return riesgo;
    }

    public CatRiesgo riesgo(String riesgo) {
        this.riesgo = riesgo;
        return this;
    }

    public void setRiesgo(String riesgo) {
        this.riesgo = riesgo;
    }

    public User getUsuario() {
        return usuario;
    }

    public CatRiesgo usuario(User user) {
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
        if (!(o instanceof CatRiesgo)) {
            return false;
        }
        return id != null && id.equals(((CatRiesgo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatRiesgo{" +
            "id=" + getId() +
            ", riesgo='" + getRiesgo() + "'" +
            "}";
    }
}
