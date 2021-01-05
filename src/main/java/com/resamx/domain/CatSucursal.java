package com.resamx.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A CatSucursal.
 */
@Entity
@Table(name = "cat_sucursal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CatSucursal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    @Size(max = 150)
    @Column(name = "nombre", length = 150, nullable = false)
    private String nombre;

    @Size(max = 512)
    @Column(name = "direccion", length = 512)
    private String direccion;

    @Size(max = 50)
    @Column(name = "telefono", length = 50)
    private String telefono;

    @Column(name = "fecha_act")
    private LocalDate fechaAct;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "catSucursals", allowSetters = true)
    private User usuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public CatSucursal nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public CatSucursal direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public CatSucursal telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaAct() {
        return fechaAct;
    }

    public CatSucursal fechaAct(LocalDate fechaAct) {
        this.fechaAct = fechaAct;
        return this;
    }

    public void setFechaAct(LocalDate fechaAct) {
        this.fechaAct = fechaAct;
    }

    public User getUsuario() {
        return usuario;
    }

    public CatSucursal usuario(User user) {
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
        if (!(o instanceof CatSucursal)) {
            return false;
        }
        return id != null && id.equals(((CatSucursal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatSucursal{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", fechaAct='" + getFechaAct() + "'" +
            "}";
    }
}
