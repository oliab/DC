package com.resamx.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "no_identificacion", length = 100, nullable = false)
    private String noIdentificacion;

    @NotNull
    @Column(name = "ingresos", nullable = false)
    private Double ingresos;

    @NotNull
    @Column(name = "estimacion_operacion", nullable = false)
    private Double estimacionOperacion;

    @Size(max = 50)
    @Column(name = "telefono", length = 50)
    private String telefono;

    @NotNull
    @Column(name = "fecha_alta", nullable = false)
    private LocalDate fechaAlta;

    
    @Column(name = "fecha_act", nullable = false)
    private LocalDate fechaAct;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @NotNull
    @JoinColumn(unique = true)
    private User usuario;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private Empresa empresa;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "clientes", allowSetters = true)
    private CatTipoEmpresa tipoCliente;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "clientes", allowSetters = true)
    private CatIdentificacion tipoIdentificacion;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "clientes", allowSetters = true)
    private CatSector sector;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "clientes", allowSetters = true)
    private CatMoneda moneda;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "clientes", allowSetters = true)
    private User usuarioAlta;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "clientes", allowSetters = true)
    private User usuarioAct;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ExpedienteCliente> expedientes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoIdentificacion() {
        return noIdentificacion;
    }

    public Cliente noIdentificacion(String noIdentificacion) {
        this.noIdentificacion = noIdentificacion;
        return this;
    }

    public void setNoIdentificacion(String noIdentificacion) {
        this.noIdentificacion = noIdentificacion;
    }

    public Double getIngresos() {
        return ingresos;
    }

    public Cliente ingresos(Double ingresos) {
        this.ingresos = ingresos;
        return this;
    }

    public void setIngresos(Double ingresos) {
        this.ingresos = ingresos;
    }

    public Double getEstimacionOperacion() {
        return estimacionOperacion;
    }

    public Cliente estimacionOperacion(Double estimacionOperacion) {
        this.estimacionOperacion = estimacionOperacion;
        return this;
    }

    public void setEstimacionOperacion(Double estimacionOperacion) {
        this.estimacionOperacion = estimacionOperacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public Cliente telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public Cliente fechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
        return this;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public LocalDate getFechaAct() {
        return fechaAct;
    }

    public Cliente fechaAct(LocalDate fechaAct) {
        this.fechaAct = fechaAct;
        return this;
    }

    public void setFechaAct(LocalDate fechaAct) {
        this.fechaAct = fechaAct;
    }

    public User getUsuario() {
        return usuario;
    }

    public Cliente usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Cliente empresa(Empresa empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public CatTipoEmpresa getTipoCliente() {
        return tipoCliente;
    }

    public Cliente tipoCliente(CatTipoEmpresa catTipoEmpresa) {
        this.tipoCliente = catTipoEmpresa;
        return this;
    }

    public void setTipoCliente(CatTipoEmpresa catTipoEmpresa) {
        this.tipoCliente = catTipoEmpresa;
    }

    public CatIdentificacion getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public Cliente tipoIdentificacion(CatIdentificacion catIdentificacion) {
        this.tipoIdentificacion = catIdentificacion;
        return this;
    }

    public void setTipoIdentificacion(CatIdentificacion catIdentificacion) {
        this.tipoIdentificacion = catIdentificacion;
    }

    public CatSector getSector() {
        return sector;
    }

    public Cliente sector(CatSector catSector) {
        this.sector = catSector;
        return this;
    }

    public void setSector(CatSector catSector) {
        this.sector = catSector;
    }

    public CatMoneda getMoneda() {
        return moneda;
    }

    public Cliente moneda(CatMoneda catMoneda) {
        this.moneda = catMoneda;
        return this;
    }

    public void setMoneda(CatMoneda catMoneda) {
        this.moneda = catMoneda;
    }

    public User getUsuarioAlta() {
        return usuarioAlta;
    }

    public Cliente usuarioAlta(User user) {
        this.usuarioAlta = user;
        return this;
    }

    public void setUsuarioAlta(User user) {
        this.usuarioAlta = user;
    }

    public User getUsuarioAct() {
        return usuarioAct;
    }

    public Cliente usuarioAct(User user) {
        this.usuarioAct = user;
        return this;
    }

    public void setUsuarioAct(User user) {
        this.usuarioAct = user;
    }

    public Set<ExpedienteCliente> getExpedientes() {
        return expedientes;
    }

    public Cliente expedientes(Set<ExpedienteCliente> expedienteClientes) {
        this.expedientes = expedienteClientes;
        return this;
    }

    public Cliente addExpediente(ExpedienteCliente expedienteCliente) {
        this.expedientes.add(expedienteCliente);
        expedienteCliente.setCliente(this);
        return this;
    }

    public Cliente removeExpediente(ExpedienteCliente expedienteCliente) {
        this.expedientes.remove(expedienteCliente);
        expedienteCliente.setCliente(null);
        return this;
    }

    public void setExpedientes(Set<ExpedienteCliente> expedienteClientes) {
        this.expedientes = expedienteClientes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        return id != null && id.equals(((Cliente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", noIdentificacion='" + getNoIdentificacion() + "'" +
            ", ingresos=" + getIngresos() +
            ", estimacionOperacion=" + getEstimacionOperacion() +
            ", telefono='" + getTelefono() + "'" +
            ", fechaAlta='" + getFechaAlta() + "'" +
            ", fechaAct='" + getFechaAct() + "'" +
            "}";
    }
}
