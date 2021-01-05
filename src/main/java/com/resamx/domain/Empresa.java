package com.resamx.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Empresa.
 */
@Entity
@Table(name = "empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fideicomiso", nullable = false)
    private Boolean fideicomiso;

    @NotNull
    @Size(max = 15)
    @Column(name = "rfc", length = 15, nullable = false)
    private String rfc;

    @NotNull
    @Size(max = 250)
    @Column(name = "razon_social", length = 250, nullable = false)
    private String razonSocial;

    @NotNull
    @Size(max = 100)
    @Column(name = "no_identificacion", length = 100, nullable = false)
    private String noIdentificacion;

    @Size(max = 50)
    @Column(name = "telefono", length = 50)
    private String telefono;

    @NotNull
    @Column(name = "fecha_alta", nullable = false)
    private LocalDate fechaAlta;

    
    @Column(name = "fecha_act", nullable = false)
    private LocalDate fechaAct;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "empresas", allowSetters = true)
    private CatIdentificacion tipoIdentificacion;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "empresas", allowSetters = true)
    private User usuarioAlta;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "empresas", allowSetters = true)
    private User usuarioAct;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @NotNull
    @JoinColumn(unique = true)
    private DomicilioEmpresa domicilio;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isFideicomiso() {
        return fideicomiso;
    }

    public Empresa fideicomiso(Boolean fideicomiso) {
        this.fideicomiso = fideicomiso;
        return this;
    }

    public void setFideicomiso(Boolean fideicomiso) {
        this.fideicomiso = fideicomiso;
    }

    public String getRfc() {
        return rfc;
    }

    public Empresa rfc(String rfc) {
        this.rfc = rfc;
        return this;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public Empresa razonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
        return this;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNoIdentificacion() {
        return noIdentificacion;
    }

    public Empresa noIdentificacion(String noIdentificacion) {
        this.noIdentificacion = noIdentificacion;
        return this;
    }

    public void setNoIdentificacion(String noIdentificacion) {
        this.noIdentificacion = noIdentificacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public Empresa telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public Empresa fechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
        return this;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public LocalDate getFechaAct() {
        return fechaAct;
    }

    public Empresa fechaAct(LocalDate fechaAct) {
        this.fechaAct = fechaAct;
        return this;
    }

    public void setFechaAct(LocalDate fechaAct) {
        this.fechaAct = fechaAct;
    }

    public CatIdentificacion getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public Empresa tipoIdentificacion(CatIdentificacion catIdentificacion) {
        this.tipoIdentificacion = catIdentificacion;
        return this;
    }

    public void setTipoIdentificacion(CatIdentificacion catIdentificacion) {
        this.tipoIdentificacion = catIdentificacion;
    }

    public User getUsuarioAlta() {
        return usuarioAlta;
    }

    public Empresa usuarioAlta(User user) {
        this.usuarioAlta = user;
        return this;
    }

    public void setUsuarioAlta(User user) {
        this.usuarioAlta = user;
    }

    public User getUsuarioAct() {
        return usuarioAct;
    }

    public Empresa usuarioAct(User user) {
        this.usuarioAct = user;
        return this;
    }

    public void setUsuarioAct(User user) {
        this.usuarioAct = user;
    }

    public DomicilioEmpresa getDomicilio() {
        return domicilio;
    }

    public Empresa domicilio(DomicilioEmpresa domicilioUsuario) {
        this.domicilio = domicilioUsuario;
        return this;
    }

    public void setDomicilio(DomicilioEmpresa domicilioUsuario) {
        this.domicilio = domicilioUsuario;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Empresa)) {
            return false;
        }
        return id != null && id.equals(((Empresa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Empresa{" +
            "id=" + getId() +
            ", fideicomiso='" + isFideicomiso() + "'" +
            ", rfc='" + getRfc() + "'" +
            ", razonSocial='" + getRazonSocial() + "'" +
            ", noIdentificacion='" + getNoIdentificacion() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", fechaAlta='" + getFechaAlta() + "'" +
            ", fechaAct='" + getFechaAct() + "'" +
            "}";
    }
}
