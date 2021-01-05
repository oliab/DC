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
 * Criteria class for the {@link com.resamx.domain.CatPais} entity. This class is used
 * in {@link com.resamx.web.rest.CatPaisResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cat-pais?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CatPaisCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private StringFilter id;

    private StringFilter nombre;

    private StringFilter codigoA2;

    private StringFilter codigoA3;

    private LongFilter usuarioId;

    public CatPaisCriteria() {
    }

    public CatPaisCriteria(CatPaisCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.codigoA2 = other.codigoA2 == null ? null : other.codigoA2.copy();
        this.codigoA3 = other.codigoA3 == null ? null : other.codigoA3.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
    }

    @Override
    public CatPaisCriteria copy() {
        return new CatPaisCriteria(this);
    }

    public StringFilter getId() {
        return id;
    }

    public void setId(StringFilter id) {
        this.id = id;
    }

    public StringFilter getNombre() {
        return nombre;
    }

    public void setNombre(StringFilter nombre) {
        this.nombre = nombre;
    }

    public StringFilter getCodigoA2() {
        return codigoA2;
    }

    public void setCodigoA2(StringFilter codigoA2) {
        this.codigoA2 = codigoA2;
    }

    public StringFilter getCodigoA3() {
        return codigoA3;
    }

    public void setCodigoA3(StringFilter codigoA3) {
        this.codigoA3 = codigoA3;
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
        final CatPaisCriteria that = (CatPaisCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(codigoA2, that.codigoA2) &&
            Objects.equals(codigoA3, that.codigoA3) &&
            Objects.equals(usuarioId, that.usuarioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombre,
        codigoA2,
        codigoA3,
        usuarioId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatPaisCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (codigoA2 != null ? "codigoA2=" + codigoA2 + ", " : "") +
                (codigoA3 != null ? "codigoA3=" + codigoA3 + ", " : "") +
                (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            "}";
    }

}
