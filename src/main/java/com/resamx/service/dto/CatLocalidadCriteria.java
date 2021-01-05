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
 * Criteria class for the {@link com.resamx.domain.CatLocalidad} entity. This class is used
 * in {@link com.resamx.web.rest.CatLocalidadResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cat-localidads?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CatLocalidadCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private StringFilter id;

    private StringFilter nombre;

    private StringFilter clave;

    private LongFilter usuarioId;

    private StringFilter estadoId;

    private StringFilter municipioId;

    public CatLocalidadCriteria() {
    }

    public CatLocalidadCriteria(CatLocalidadCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.clave = other.clave == null ? null : other.clave.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.estadoId = other.estadoId == null ? null : other.estadoId.copy();
        this.municipioId = other.municipioId == null ? null : other.municipioId.copy();
    }

    @Override
    public CatLocalidadCriteria copy() {
        return new CatLocalidadCriteria(this);
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

    public StringFilter getClave() {
        return clave;
    }

    public void setClave(StringFilter clave) {
        this.clave = clave;
    }

    public LongFilter getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(LongFilter usuarioId) {
        this.usuarioId = usuarioId;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CatLocalidadCriteria that = (CatLocalidadCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(clave, that.clave) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(estadoId, that.estadoId) &&
            Objects.equals(municipioId, that.municipioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombre,
        clave,
        usuarioId,
        estadoId,
        municipioId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatLocalidadCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (clave != null ? "clave=" + clave + ", " : "") +
                (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
                (estadoId != null ? "estadoId=" + estadoId + ", " : "") +
                (municipioId != null ? "municipioId=" + municipioId + ", " : "") +
            "}";
    }

}
