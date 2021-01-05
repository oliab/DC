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
 * Criteria class for the {@link com.resamx.domain.CatSucursal} entity. This class is used
 * in {@link com.resamx.web.rest.CatSucursalResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cat-sucursals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CatSucursalCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombre;

    private StringFilter direccion;

    private StringFilter telefono;

    private LocalDateFilter fechaAct;

    private LongFilter usuarioId;

    public CatSucursalCriteria() {
    }

    public CatSucursalCriteria(CatSucursalCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.direccion = other.direccion == null ? null : other.direccion.copy();
        this.telefono = other.telefono == null ? null : other.telefono.copy();
        this.fechaAct = other.fechaAct == null ? null : other.fechaAct.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
    }

    @Override
    public CatSucursalCriteria copy() {
        return new CatSucursalCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNombre() {
        return nombre;
    }

    public void setNombre(StringFilter nombre) {
        this.nombre = nombre;
    }

    public StringFilter getDireccion() {
        return direccion;
    }

    public void setDireccion(StringFilter direccion) {
        this.direccion = direccion;
    }

    public StringFilter getTelefono() {
        return telefono;
    }

    public void setTelefono(StringFilter telefono) {
        this.telefono = telefono;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CatSucursalCriteria that = (CatSucursalCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(direccion, that.direccion) &&
            Objects.equals(telefono, that.telefono) &&
            Objects.equals(fechaAct, that.fechaAct) &&
            Objects.equals(usuarioId, that.usuarioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombre,
        direccion,
        telefono,
        fechaAct,
        usuarioId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatSucursalCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (direccion != null ? "direccion=" + direccion + ", " : "") +
                (telefono != null ? "telefono=" + telefono + ", " : "") +
                (fechaAct != null ? "fechaAct=" + fechaAct + ", " : "") +
                (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            "}";
    }

}
