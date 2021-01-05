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
 * Criteria class for the {@link com.resamx.domain.CatEstado} entity. This class is used
 * in {@link com.resamx.web.rest.CatEstadoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cat-estados?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CatEstadoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private StringFilter id;

    private StringFilter nombre;

    private LongFilter usuarioId;

    private StringFilter paisId;

    public CatEstadoCriteria() {
    }

    public CatEstadoCriteria(CatEstadoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.paisId = other.paisId == null ? null : other.paisId.copy();
    }

    @Override
    public CatEstadoCriteria copy() {
        return new CatEstadoCriteria(this);
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

    public LongFilter getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(LongFilter usuarioId) {
        this.usuarioId = usuarioId;
    }

    public StringFilter getPaisId() {
        return paisId;
    }

    public void setPaisId(StringFilter paisId) {
        this.paisId = paisId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CatEstadoCriteria that = (CatEstadoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(paisId, that.paisId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombre,
        usuarioId,
        paisId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatEstadoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
                (paisId != null ? "paisId=" + paisId + ", " : "") +
            "}";
    }

}
