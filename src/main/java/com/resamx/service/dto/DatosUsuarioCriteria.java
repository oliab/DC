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
 * Criteria class for the {@link com.resamx.domain.DatosUsuario} entity. This class is used
 * in {@link com.resamx.web.rest.DatosUsuarioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /datos-usuarios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DatosUsuarioCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter puesto;

    private LocalDateFilter fechaAct;

    private LongFilter sucursalId;

    private LongFilter userId;

    public DatosUsuarioCriteria() {
    }

    public DatosUsuarioCriteria(DatosUsuarioCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.puesto = other.puesto == null ? null : other.puesto.copy();
        this.fechaAct = other.fechaAct == null ? null : other.fechaAct.copy();
        this.sucursalId = other.sucursalId == null ? null : other.sucursalId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public DatosUsuarioCriteria copy() {
        return new DatosUsuarioCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPuesto() {
        return puesto;
    }

    public void setPuesto(StringFilter puesto) {
        this.puesto = puesto;
    }

    public LocalDateFilter getFechaAct() {
        return fechaAct;
    }

    public void setFechaAct(LocalDateFilter fechaAct) {
        this.fechaAct = fechaAct;
    }

    public LongFilter getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(LongFilter sucursalId) {
        this.sucursalId = sucursalId;
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
        final DatosUsuarioCriteria that = (DatosUsuarioCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(puesto, that.puesto) &&
            Objects.equals(fechaAct, that.fechaAct) &&
            Objects.equals(sucursalId, that.sucursalId) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        puesto,
        fechaAct,
        sucursalId,
        userId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DatosUsuarioCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (puesto != null ? "puesto=" + puesto + ", " : "") +
                (fechaAct != null ? "fechaAct=" + fechaAct + ", " : "") +
                (sucursalId != null ? "sucursalId=" + sucursalId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
