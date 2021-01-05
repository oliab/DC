package com.resamx.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DatosUsuario.
 */
@Entity
@Table(name = "datos_usuario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DatosUsuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 150)
    @Column(name = "puesto", length = 150, nullable = false)
    private String puesto;

    @Column(name = "fecha_act")
    private LocalDate fechaAct;

    @ManyToOne
    @JsonIgnoreProperties(value = "datosUsuarios", allowSetters = true)
    private CatSucursal sucursal;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPuesto() {
        return puesto;
    }

    public DatosUsuario puesto(String puesto) {
        this.puesto = puesto;
        return this;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public LocalDate getFechaAct() {
        return fechaAct;
    }

    public DatosUsuario fechaAct(LocalDate fechaAct) {
        this.fechaAct = fechaAct;
        return this;
    }

    public void setFechaAct(LocalDate fechaAct) {
        this.fechaAct = fechaAct;
    }

    public CatSucursal getSucursal() {
        return sucursal;
    }

    public DatosUsuario sucursal(CatSucursal catSucursal) {
        this.sucursal = catSucursal;
        return this;
    }

    public void setSucursal(CatSucursal catSucursal) {
        this.sucursal = catSucursal;
    }

    public User getUser() {
        return user;
    }

    public DatosUsuario user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DatosUsuario)) {
            return false;
        }
        return id != null && id.equals(((DatosUsuario) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DatosUsuario{" +
            "id=" + getId() +
            ", puesto='" + getPuesto() + "'" +
            ", fechaAct='" + getFechaAct() + "'" +
            "}";
    }
}
