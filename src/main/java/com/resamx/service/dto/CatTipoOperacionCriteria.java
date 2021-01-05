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
 * Criteria class for the {@link com.resamx.domain.CatTipoOperacion} entity. This class is used
 * in {@link com.resamx.web.rest.CatTipoOperacionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cat-tipo-operacions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CatTipoOperacionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter operacion;

    private StringFilter descripcion;

    private LongFilter usuarioId;

    public CatTipoOperacionCriteria() {
    }

    public CatTipoOperacionCriteria(CatTipoOperacionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.operacion = other.operacion == null ? null : other.operacion.copy();
        this.descripcion = other.descripcion == null ? null : other.descripcion.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
    }

    @Override
    public CatTipoOperacionCriteria copy() {
        return new CatTipoOperacionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getOperacion() {
        return operacion;
    }

    public void setOperacion(StringFilter operacion) {
        this.operacion = operacion;
    }

    public StringFilter getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(StringFilter descripcion) {
        this.descripcion = descripcion;
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
        final CatTipoOperacionCriteria that = (CatTipoOperacionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(operacion, that.operacion) &&
            Objects.equals(descripcion, that.descripcion) &&
            Objects.equals(usuarioId, that.usuarioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        operacion,
        descripcion,
        usuarioId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatTipoOperacionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (operacion != null ? "operacion=" + operacion + ", " : "") +
                (descripcion != null ? "descripcion=" + descripcion + ", " : "") +
                (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            "}";
    }

}
