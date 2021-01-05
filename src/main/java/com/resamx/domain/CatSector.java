package com.resamx.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A CatSector.
 */
@Entity
@Table(name = "cat_sector")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CatSector implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Size(max = 15)
    private String id;

    @NotNull
    @Size(max = 100)
    @Column(name = "actividad_economica", length = 100, nullable = false)
    private String actividadEconomica;

    @NotNull
    @Column(name = "actividad_vulnerable", nullable = false)
    private Boolean actividadVulnerable;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "catSectors", allowSetters = true)
    private User usuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActividadEconomica() {
        return actividadEconomica;
    }

    public CatSector actividadEconomica(String actividadEconomica) {
        this.actividadEconomica = actividadEconomica;
        return this;
    }

    public void setActividadEconomica(String actividadEconomica) {
        this.actividadEconomica = actividadEconomica;
    }

    public Boolean isActividadVulnerable() {
        return actividadVulnerable;
    }

    public CatSector actividadVulnerable(Boolean actividadVulnerable) {
        this.actividadVulnerable = actividadVulnerable;
        return this;
    }

    public void setActividadVulnerable(Boolean actividadVulnerable) {
        this.actividadVulnerable = actividadVulnerable;
    }

    public User getUsuario() {
        return usuario;
    }

    public CatSector usuario(User user) {
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
        if (!(o instanceof CatSector)) {
            return false;
        }
        return id != null && id.equals(((CatSector) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatSector{" +
            "id=" + getId() +
            ", actividadEconomica='" + getActividadEconomica() + "'" +
            ", actividadVulnerable='" + isActividadVulnerable() + "'" +
            "}";
    }
}
