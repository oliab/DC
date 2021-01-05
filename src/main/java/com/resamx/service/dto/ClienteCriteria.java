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
 * Criteria class for the {@link com.resamx.domain.Cliente} entity. This class is used
 * in {@link com.resamx.web.rest.ClienteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /clientes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClienteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter noIdentificacion;

    private DoubleFilter ingresos;

    private DoubleFilter estimacionOperacion;

    private StringFilter telefono;

    private LocalDateFilter fechaAlta;

    private LocalDateFilter fechaAct;

    private LongFilter usuarioId;

    private LongFilter empresaId;

    private LongFilter tipoClienteId;

    private LongFilter tipoIdentificacionId;

    private StringFilter sectorId;

    private StringFilter monedaId;

    private LongFilter usuarioAltaId;

    private LongFilter usuarioActId;

    private LongFilter expedienteId;

    public ClienteCriteria() {
    }

    public ClienteCriteria(ClienteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.noIdentificacion = other.noIdentificacion == null ? null : other.noIdentificacion.copy();
        this.ingresos = other.ingresos == null ? null : other.ingresos.copy();
        this.estimacionOperacion = other.estimacionOperacion == null ? null : other.estimacionOperacion.copy();
        this.telefono = other.telefono == null ? null : other.telefono.copy();
        this.fechaAlta = other.fechaAlta == null ? null : other.fechaAlta.copy();
        this.fechaAct = other.fechaAct == null ? null : other.fechaAct.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.tipoClienteId = other.tipoClienteId == null ? null : other.tipoClienteId.copy();
        this.tipoIdentificacionId = other.tipoIdentificacionId == null ? null : other.tipoIdentificacionId.copy();
        this.sectorId = other.sectorId == null ? null : other.sectorId.copy();
        this.monedaId = other.monedaId == null ? null : other.monedaId.copy();
        this.usuarioAltaId = other.usuarioAltaId == null ? null : other.usuarioAltaId.copy();
        this.usuarioActId = other.usuarioActId == null ? null : other.usuarioActId.copy();
        this.expedienteId = other.expedienteId == null ? null : other.expedienteId.copy();
    }

    @Override
    public ClienteCriteria copy() {
        return new ClienteCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNoIdentificacion() {
        return noIdentificacion;
    }

    public void setNoIdentificacion(StringFilter noIdentificacion) {
        this.noIdentificacion = noIdentificacion;
    }

    public DoubleFilter getIngresos() {
        return ingresos;
    }

    public void setIngresos(DoubleFilter ingresos) {
        this.ingresos = ingresos;
    }

    public DoubleFilter getEstimacionOperacion() {
        return estimacionOperacion;
    }

    public void setEstimacionOperacion(DoubleFilter estimacionOperacion) {
        this.estimacionOperacion = estimacionOperacion;
    }

    public StringFilter getTelefono() {
        return telefono;
    }

    public void setTelefono(StringFilter telefono) {
        this.telefono = telefono;
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

    public LongFilter getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(LongFilter usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LongFilter getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(LongFilter empresaId) {
        this.empresaId = empresaId;
    }

    public LongFilter getTipoClienteId() {
        return tipoClienteId;
    }

    public void setTipoClienteId(LongFilter tipoClienteId) {
        this.tipoClienteId = tipoClienteId;
    }

    public LongFilter getTipoIdentificacionId() {
        return tipoIdentificacionId;
    }

    public void setTipoIdentificacionId(LongFilter tipoIdentificacionId) {
        this.tipoIdentificacionId = tipoIdentificacionId;
    }

    public StringFilter getSectorId() {
        return sectorId;
    }

    public void setSectorId(StringFilter sectorId) {
        this.sectorId = sectorId;
    }

    public StringFilter getMonedaId() {
        return monedaId;
    }

    public void setMonedaId(StringFilter monedaId) {
        this.monedaId = monedaId;
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

    public LongFilter getExpedienteId() {
        return expedienteId;
    }

    public void setExpedienteId(LongFilter expedienteId) {
        this.expedienteId = expedienteId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClienteCriteria that = (ClienteCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(noIdentificacion, that.noIdentificacion) &&
            Objects.equals(ingresos, that.ingresos) &&
            Objects.equals(estimacionOperacion, that.estimacionOperacion) &&
            Objects.equals(telefono, that.telefono) &&
            Objects.equals(fechaAlta, that.fechaAlta) &&
            Objects.equals(fechaAct, that.fechaAct) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(tipoClienteId, that.tipoClienteId) &&
            Objects.equals(tipoIdentificacionId, that.tipoIdentificacionId) &&
            Objects.equals(sectorId, that.sectorId) &&
            Objects.equals(monedaId, that.monedaId) &&
            Objects.equals(usuarioAltaId, that.usuarioAltaId) &&
            Objects.equals(usuarioActId, that.usuarioActId) &&
            Objects.equals(expedienteId, that.expedienteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        noIdentificacion,
        ingresos,
        estimacionOperacion,
        telefono,
        fechaAlta,
        fechaAct,
        usuarioId,
        empresaId,
        tipoClienteId,
        tipoIdentificacionId,
        sectorId,
        monedaId,
        usuarioAltaId,
        usuarioActId,
        expedienteId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClienteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (noIdentificacion != null ? "noIdentificacion=" + noIdentificacion + ", " : "") +
                (ingresos != null ? "ingresos=" + ingresos + ", " : "") +
                (estimacionOperacion != null ? "estimacionOperacion=" + estimacionOperacion + ", " : "") +
                (telefono != null ? "telefono=" + telefono + ", " : "") +
                (fechaAlta != null ? "fechaAlta=" + fechaAlta + ", " : "") +
                (fechaAct != null ? "fechaAct=" + fechaAct + ", " : "") +
                (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
                (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
                (tipoClienteId != null ? "tipoClienteId=" + tipoClienteId + ", " : "") +
                (tipoIdentificacionId != null ? "tipoIdentificacionId=" + tipoIdentificacionId + ", " : "") +
                (sectorId != null ? "sectorId=" + sectorId + ", " : "") +
                (monedaId != null ? "monedaId=" + monedaId + ", " : "") +
                (usuarioAltaId != null ? "usuarioAltaId=" + usuarioAltaId + ", " : "") +
                (usuarioActId != null ? "usuarioActId=" + usuarioActId + ", " : "") +
                (expedienteId != null ? "expedienteId=" + expedienteId + ", " : "") +
            "}";
    }

}
