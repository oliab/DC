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
 * Criteria class for the {@link com.resamx.domain.CatTipoMonetario} entity. This class is used
 * in {@link com.resamx.web.rest.CatTipoMonetarioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cat-tipo-monetarios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CatTipoMonetarioCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private StringFilter id;

    private StringFilter instrumento;

    private StringFilter descripcion;

    private LongFilter usuarioId;

    public CatTipoMonetarioCriteria() {
    }

    public CatTipoMonetarioCriteria(CatTipoMonetarioCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.instrumento = other.instrumento == null ? null : other.instrumento.copy();
        this.descripcion = other.descripcion == null ? null : other.descripcion.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
    }

    @Override
    public CatTipoMonetarioCriteria copy() {
        return new CatTipoMonetarioCriteria(this);
    }

    public StringFilter getId() {
        return id;
    }

    public void setId(StringFilter id) {
        this.id = id;
    }

    public StringFilter getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(StringFilter instrumento) {
        this.instrumento = instrumento;
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
        final CatTipoMonetarioCriteria that = (CatTipoMonetarioCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(instrumento, that.instrumento) &&
            Objects.equals(descripcion, that.descripcion) &&
            Objects.equals(usuarioId, that.usuarioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        instrumento,
        descripcion,
        usuarioId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatTipoMonetarioCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (instrumento != null ? "instrumento=" + instrumento + ", " : "") +
                (descripcion != null ? "descripcion=" + descripcion + ", " : "") +
                (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            "}";
    }

}
