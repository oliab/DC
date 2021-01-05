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
 * Criteria class for the {@link com.resamx.domain.Empresa} entity. This class is used
 * in {@link com.resamx.web.rest.EmpresaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /empresas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmpresaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter fideicomiso;

    private StringFilter rfc;

    private StringFilter razonSocial;

    private StringFilter noIdentificacion;

    private StringFilter telefono;

    private LocalDateFilter fechaAlta;

    private LocalDateFilter fechaAct;

    private LongFilter tipoIdentificacionId;

    private LongFilter usuarioAltaId;

    private LongFilter usuarioActId;

    private LongFilter domicilioId;

    public EmpresaCriteria() {
    }

    public EmpresaCriteria(EmpresaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fideicomiso = other.fideicomiso == null ? null : other.fideicomiso.copy();
        this.rfc = other.rfc == null ? null : other.rfc.copy();
        this.razonSocial = other.razonSocial == null ? null : other.razonSocial.copy();
        this.noIdentificacion = other.noIdentificacion == null ? null : other.noIdentificacion.copy();
        this.telefono = other.telefono == null ? null : other.telefono.copy();
        this.fechaAlta = other.fechaAlta == null ? null : other.fechaAlta.copy();
        this.fechaAct = other.fechaAct == null ? null : other.fechaAct.copy();
        this.tipoIdentificacionId = other.tipoIdentificacionId == null ? null : other.tipoIdentificacionId.copy();
        this.usuarioAltaId = other.usuarioAltaId == null ? null : other.usuarioAltaId.copy();
        this.usuarioActId = other.usuarioActId == null ? null : other.usuarioActId.copy();
        this.domicilioId = other.domicilioId == null ? null : other.domicilioId.copy();
    }

    @Override
    public EmpresaCriteria copy() {
        return new EmpresaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getFideicomiso() {
        return fideicomiso;
    }

    public void setFideicomiso(BooleanFilter fideicomiso) {
        this.fideicomiso = fideicomiso;
    }

    public StringFilter getRfc() {
        return rfc;
    }

    public void setRfc(StringFilter rfc) {
        this.rfc = rfc;
    }

    public StringFilter getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(StringFilter razonSocial) {
        this.razonSocial = razonSocial;
    }

    public StringFilter getNoIdentificacion() {
        return noIdentificacion;
    }

    public void setNoIdentificacion(StringFilter noIdentificacion) {
        this.noIdentificacion = noIdentificacion;
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

    public LongFilter getTipoIdentificacionId() {
        return tipoIdentificacionId;
    }

    public void setTipoIdentificacionId(LongFilter tipoIdentificacionId) {
        this.tipoIdentificacionId = tipoIdentificacionId;
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

    public LongFilter getDomicilioId() {
        return domicilioId;
    }

    public void setDomicilioId(LongFilter domicilioId) {
        this.domicilioId = domicilioId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmpresaCriteria that = (EmpresaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fideicomiso, that.fideicomiso) &&
            Objects.equals(rfc, that.rfc) &&
            Objects.equals(razonSocial, that.razonSocial) &&
            Objects.equals(noIdentificacion, that.noIdentificacion) &&
            Objects.equals(telefono, that.telefono) &&
            Objects.equals(fechaAlta, that.fechaAlta) &&
            Objects.equals(fechaAct, that.fechaAct) &&
            Objects.equals(tipoIdentificacionId, that.tipoIdentificacionId) &&
            Objects.equals(usuarioAltaId, that.usuarioAltaId) &&
            Objects.equals(usuarioActId, that.usuarioActId) &&
            Objects.equals(domicilioId, that.domicilioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fideicomiso,
        rfc,
        razonSocial,
        noIdentificacion,
        telefono,
        fechaAlta,
        fechaAct,
        tipoIdentificacionId,
        usuarioAltaId,
        usuarioActId,
        domicilioId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmpresaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fideicomiso != null ? "fideicomiso=" + fideicomiso + ", " : "") +
                (rfc != null ? "rfc=" + rfc + ", " : "") +
                (razonSocial != null ? "razonSocial=" + razonSocial + ", " : "") +
                (noIdentificacion != null ? "noIdentificacion=" + noIdentificacion + ", " : "") +
                (telefono != null ? "telefono=" + telefono + ", " : "") +
                (fechaAlta != null ? "fechaAlta=" + fechaAlta + ", " : "") +
                (fechaAct != null ? "fechaAct=" + fechaAct + ", " : "") +
                (tipoIdentificacionId != null ? "tipoIdentificacionId=" + tipoIdentificacionId + ", " : "") +
                (usuarioAltaId != null ? "usuarioAltaId=" + usuarioAltaId + ", " : "") +
                (usuarioActId != null ? "usuarioActId=" + usuarioActId + ", " : "") +
                (domicilioId != null ? "domicilioId=" + domicilioId + ", " : "") +
            "}";
    }

}
