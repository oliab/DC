package com.resamx.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.resamx.domain.ExpedienteCliente} entity. This class is used
 * in {@link com.resamx.web.rest.ExpedienteClienteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /expediente-clientes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ExpedienteClienteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter empresarial;

    private StringFilter descripcion;

    private LocalDateFilter fechaAlta;

    private LocalDateFilter fechaAct;

    private LongFilter clienteId;

    private LongFilter tipoDocumentoId;

    private LongFilter usuarioAltaId;

    private LongFilter usuarioActId;

    public ExpedienteClienteCriteria() {
    }

    public ExpedienteClienteCriteria(ExpedienteClienteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.empresarial = other.empresarial == null ? null : other.empresarial.copy();
        this.descripcion = other.descripcion == null ? null : other.descripcion.copy();
        this.fechaAlta = other.fechaAlta == null ? null : other.fechaAlta.copy();
        this.fechaAct = other.fechaAct == null ? null : other.fechaAct.copy();
        this.clienteId = other.clienteId == null ? null : other.clienteId.copy();
        this.tipoDocumentoId = other.tipoDocumentoId == null ? null : other.tipoDocumentoId.copy();
        this.usuarioAltaId = other.usuarioAltaId == null ? null : other.usuarioAltaId.copy();
        this.usuarioActId = other.usuarioActId == null ? null : other.usuarioActId.copy();
    }

    @Override
    public ExpedienteClienteCriteria copy() {
        return new ExpedienteClienteCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getEmpresarial() {
        return empresarial;
    }

    public void setEmpresarial(BooleanFilter empresarial) {
        this.empresarial = empresarial;
    }

    public StringFilter getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(StringFilter descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateFilter getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDateFilter fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public LocalDateFilter getFechaAct() {
        return fechaAct;
    }

    public void setFechaAct(LocalDateFilter fechaAct) {
        this.fechaAct = fechaAct;
    }

    public LongFilter getClienteId() {
        return clienteId;
    }

    public void setClienteId(LongFilter clienteId) {
        this.clienteId = clienteId;
    }

    public LongFilter getTipoDocumentoId() {
        return tipoDocumentoId;
    }

    public void setTipoDocumentoId(LongFilter tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId;
    }

    public LongFilter getUsuarioAltaId() {
        return usuarioAltaId;
    }

    public void setUsuarioAltaId(LongFilter usuarioAltaId) {
        this.usuarioAltaId = usuarioAltaId;
    }

    public LongFilter getUsuarioActId() {
        return usuarioActId;
    }

    public void setUsuarioActId(LongFilter usuarioActId) {
        this.usuarioActId = usuarioActId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ExpedienteClienteCriteria that = (ExpedienteClienteCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(empresarial, that.empresarial) &&
            Objects.equals(descripcion, that.descripcion) &&
            Objects.equals(fechaAlta, that.fechaAlta) &&
            Objects.equals(fechaAct, that.fechaAct) &&
            Objects.equals(clienteId, that.clienteId) &&
            Objects.equals(tipoDocumentoId, that.tipoDocumentoId) &&
            Objects.equals(usuarioAltaId, that.usuarioAltaId) &&
            Objects.equals(usuarioActId, that.usuarioActId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        empresarial,
        descripcion,
        fechaAlta,
        fechaAct,
        clienteId,
        tipoDocumentoId,
        usuarioAltaId,
        usuarioActId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExpedienteClienteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (empresarial != null ? "empresarial=" + empresarial + ", " : "") +
                (descripcion != null ? "descripcion=" + descripcion + ", " : "") +
                (fechaAlta != null ? "fechaAlta=" + fechaAlta + ", " : "") +
                (fechaAct != null ? "fechaAct=" + fechaAct + ", " : "") +
                (clienteId != null ? "clienteId=" + clienteId + ", " : "") +
                (tipoDocumentoId != null ? "tipoDocumentoId=" + tipoDocumentoId + ", " : "") +
                (usuarioAltaId != null ? "usuarioAltaId=" + usuarioAltaId + ", " : "") +
                (usuarioActId != null ? "usuarioActId=" + usuarioActId + ", " : "") +
            "}";
    }

}
