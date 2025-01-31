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
 * Criteria class for the {@link com.resamx.domain.CatNacionalidad} entity. This class is used
 * in {@link com.resamx.web.rest.CatNacionalidadResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cat-nacionalidads?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CatNacionalidadCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nacionalidad;

    private LongFilter usuarioId;

    public CatNacionalidadCriteria() {
    }

    public CatNacionalidadCriteria(CatNacionalidadCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nacionalidad = other.nacionalidad == null ? null : other.nacionalidad.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
    }

    @Override
    public CatNacionalidadCriteria copy() {
        return new CatNacionalidadCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(StringFilter nacionalidad) {
        this.nacionalidad = nacionalidad;
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
        final CatNacionalidadCriteria that = (CatNacionalidadCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nacionalidad, that.nacionalidad) &&
            Objects.equals(usuarioId, that.usuarioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nacionalidad,
        usuarioId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatNacionalidadCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nacionalidad != null ? "nacionalidad=" + nacionalidad + ", " : "") +
                (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            "}";
    }

}
