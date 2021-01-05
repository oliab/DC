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
 * Criteria class for the {@link com.resamx.domain.DomicilioEmpresa} entity. This class is used
 * in {@link com.resamx.web.rest.DomicilioEmpresaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /domicilio-empresas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DomicilioEmpresaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter colonia;

    private StringFilter calle;

    private StringFilter noExt;

    private StringFilter noInt;

    private StringFilter domicilio;

    private LocalDateFilter fechaAct;

    private LongFilter nacionalidadId;

    private StringFilter paisOrigenId;

    private StringFilter paisId;

    private StringFilter estadoId;

    private StringFilter municipioId;

    private StringFilter localidadId;

    private StringFilter cpId;

    private LongFilter userId;

    public DomicilioEmpresaCriteria() {
    }

    public DomicilioEmpresaCriteria(DomicilioEmpresaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.colonia = other.colonia == null ? null : other.colonia.copy();
        this.calle = other.calle == null ? null : other.calle.copy();
        this.noExt = other.noExt == null ? null : other.noExt.copy();
        this.noInt = other.noInt == null ? null : other.noInt.copy();
        this.domicilio = other.domicilio == null ? null : other.domicilio.copy();
        this.fechaAct = other.fechaAct == null ? null : other.fechaAct.copy();
        this.nacionalidadId = other.nacionalidadId == null ? null : other.nacionalidadId.copy();
        this.paisOrigenId = other.paisOrigenId == null ? null : other.paisOrigenId.copy();
        this.paisId = other.paisId == null ? null : other.paisId.copy();
        this.estadoId = other.estadoId == null ? null : other.estadoId.copy();
        this.municipioId = other.municipioId == null ? null : other.municipioId.copy();
        this.localidadId = other.localidadId == null ? null : other.localidadId.copy();
        this.cpId = other.cpId == null ? null : other.cpId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public DomicilioEmpresaCriteria copy() {
        return new DomicilioEmpresaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getColonia() {
        return colonia;
    }

    public void setColonia(StringFilter colonia) {
        this.colonia = colonia;
    }

    public StringFilter getCalle() {
        return calle;
    }

    public void setCalle(StringFilter calle) {
        this.calle = calle;
    }

    public StringFilter getNoExt() {
        return noExt;
    }

    public void setNoExt(StringFilter noExt) {
        this.noExt = noExt;
    }

    public StringFilter getNoInt() {
        return noInt;
    }

    public void setNoInt(StringFilter noInt) {
        this.noInt = noInt;
    }

    public StringFilter getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(StringFilter domicilio) {
        this.domicilio = domicilio;
    }

    public LocalDateFilter getFechaAct() {
        return fechaAct;
    }

    public void setFechaAct(LocalDateFilter fechaAct) {
        this.fechaAct = fechaAct;
    }

    public LongFilter getNacionalidadId() {
        return nacionalidadId;
    }

    public void setNacionalidadId(LongFilter nacionalidadId) {
        this.nacionalidadId = nacionalidadId;
    }

    public StringFilter getPaisOrigenId() {
        return paisOrigenId;
    }

    public void setPaisOrigenId(StringFilter paisOrigenId) {
        this.paisOrigenId = paisOrigenId;
    }

    public StringFilter getPaisId() {
        return paisId;
    }

    public void setPaisId(StringFilter paisId) {
        this.paisId = paisId;
    }

    public StringFilter getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(StringFilter estadoId) {
        this.estadoId = estadoId;
    }

    public StringFilter getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(StringFilter municipioId) {
        this.municipioId = municipioId;
    }

    public StringFilter getLocalidadId() {
        return localidadId;
    }

    public void setLocalidadId(StringFilter localidadId) {
        this.localidadId = localidadId;
    }

    public StringFilter getCpId() {
        return cpId;
    }

    public void setCpId(StringFilter cpId) {
        this.cpId = cpId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DomicilioEmpresaCriteria that = (DomicilioEmpresaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(colonia, that.colonia) &&
            Objects.equals(calle, that.calle) &&
            Objects.equals(noExt, that.noExt) &&
            Objects.equals(noInt, that.noInt) &&
            Objects.equals(domicilio, that.domicilio) &&
            Objects.equals(fechaAct, that.fechaAct) &&
            Objects.equals(nacionalidadId, that.nacionalidadId) &&
            Objects.equals(paisOrigenId, that.paisOrigenId) &&
            Objects.equals(paisId, that.paisId) &&
            Objects.equals(estadoId, that.estadoId) &&
            Objects.equals(municipioId, that.municipioId) &&
            Objects.equals(localidadId, that.localidadId) &&
            Objects.equals(cpId, that.cpId) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        colonia,
        calle,
        noExt,
        noInt,
        domicilio,
        fechaAct,
        nacionalidadId,
        paisOrigenId,
        paisId,
        estadoId,
        municipioId,
        localidadId,
        cpId,
        userId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DomicilioEmpresaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (colonia != null ? "colonia=" + colonia + ", " : "") +
                (calle != null ? "calle=" + calle + ", " : "") +
                (noExt != null ? "noExt=" + noExt + ", " : "") +
                (noInt != null ? "noInt=" + noInt + ", " : "") +
                (domicilio != null ? "domicilio=" + domicilio + ", " : "") +
                (fechaAct != null ? "fechaAct=" + fechaAct + ", " : "") +
                (nacionalidadId != null ? "nacionalidadId=" + nacionalidadId + ", " : "") +
                (paisOrigenId != null ? "paisOrigenId=" + paisOrigenId + ", " : "") +
                (paisId != null ? "paisId=" + paisId + ", " : "") +
                (estadoId != null ? "estadoId=" + estadoId + ", " : "") +
                (municipioId != null ? "municipioId=" + municipioId + ", " : "") +
                (localidadId != null ? "localidadId=" + localidadId + ", " : "") +
                (cpId != null ? "cpId=" + cpId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
