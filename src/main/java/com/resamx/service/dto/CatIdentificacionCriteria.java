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

/**
 * Criteria class for the {@link com.resamx.domain.CatIdentificacion} entity. This class is used
 * in {@link com.resamx.web.rest.CatIdentificacionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cat-identificacions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CatIdentificacionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter identificacion;

    private LongFilter usuarioId;

    public CatIdentificacionCriteria() {
    }

    public CatIdentificacionCriteria(CatIdentificacionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.identificacion = other.identificacion == null ? null : other.identificacion.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
    }

    @Override
    public CatIdentificacionCriteria copy() {
        return new CatIdentificacionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(StringFilter identificacion) {
        this.identificacion = identificacion;
    }

    public LongFilter getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(LongFilter usuarioId) {
        this.usuarioId = usuarioId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CatIdentificacionCriteria that = (CatIdentificacionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(identificacion, that.identificacion) &&
            Objects.equals(usuarioId, that.usuarioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        identificacion,
        usuarioId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatIdentificacionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (identificacion != null ? "identificacion=" + identificacion + ", " : "") +
                (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            "}";
    }

}
