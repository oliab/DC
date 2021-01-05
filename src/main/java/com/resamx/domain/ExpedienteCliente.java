package com.resamx.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ExpedienteCliente.
 */
@Entity
@Table(name = "expediente_cliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ExpedienteCliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "empresarial", nullable = false)
    private Boolean empresarial;

    @Size(max = 250)
    @Column(name = "descripcion", length = 250)
    private String descripcion;

    
    @Lob
    @Column(name = "documento", nullable = false)
    private byte[] documento;

    @Column(name = "documento_content_type", nullable = false)
    private String documentoContentType;

    @Column(name = "fecha_alta")
    private LocalDate fechaAlta;

    @Column(name = "fecha_act")
    private LocalDate fechaAct;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Cliente cliente;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "expedienteClientes", allowSetters = true)
    private CatTipoDocumento tipoDocumento;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "expedienteClientes", allowSetters = true)
    private User usuarioAlta;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "expedienteClientes", allowSetters = true)
    private User usuarioAct;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isEmpresarial() {
        return empresarial;
    }

    public ExpedienteCliente empresarial(Boolean empresarial) {
        this.empresarial = empresarial;
        return this;
    }

    public void setEmpresarial(Boolean empresarial) {
        this.empresarial = empresarial;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ExpedienteCliente descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getDocumento() {
        return documento;
    }

    public ExpedienteCliente documento(byte[] documento) {
        this.documento = documento;
        return this;
    }

    public void setDocumento(byte[] documento) {
        this.documento = documento;
    }

    public String getDocumentoContentType() {
        return documentoContentType;
    }

    public ExpedienteCliente documentoContentType(String documentoContentType) {
        this.documentoContentType = documentoContentType;
        return this;
    }

    public void setDocumentoContentType(String documentoContentType) {
        this.documentoContentType = documentoContentType;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public ExpedienteCliente fechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
        return this;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public LocalDate getFechaAct() {
        return fechaAct;
    }

    public ExpedienteCliente fechaAct(LocalDate fechaAct) {
        this.fechaAct = fechaAct;
        return this;
    }

    public void setFechaAct(LocalDate fechaAct) {
        this.fechaAct = fechaAct;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public ExpedienteCliente cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public CatTipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public ExpedienteCliente tipoDocumento(CatTipoDocumento catTipoDocumento) {
        this.tipoDocumento = catTipoDocumento;
        return this;
    }

    public void setTipoDocumento(CatTipoDocumento catTipoDocumento) {
        this.tipoDocumento = catTipoDocumento;
    }

    public User getUsuarioAlta() {
        return usuarioAlta;
    }

    public ExpedienteCliente usuarioAlta(User user) {
        this.usuarioAlta = user;
        return this;
    }

    public void setUsuarioAlta(User user) {
        this.usuarioAlta = user;
    }

    public User getUsuarioAct() {
        return usuarioAct;
    }

    public ExpedienteCliente usuarioAct(User user) {
        this.usuarioAct = user;
        return this;
    }

    public void setUsuarioAct(User user) {
        this.usuarioAct = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExpedienteCliente)) {
            return false;
        }
        return id != null && id.equals(((ExpedienteCliente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExpedienteCliente{" +
            "id=" + getId() +
            ", empresarial='" + isEmpresarial() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", documento='" + getDocumento() + "'" +
            ", documentoContentType='" + getDocumentoContentType() + "'" +
            ", fechaAlta='" + getFechaAlta() + "'" +
            ", fechaAct='" + getFechaAct() + "'" +
            "}";
    }
}
