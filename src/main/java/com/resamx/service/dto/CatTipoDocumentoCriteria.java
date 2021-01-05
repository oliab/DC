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
 * Criteria class for the {@link com.resamx.domain.CatTipoDocumento} entity. This class is used
 * in {@link com.resamx.web.rest.CatTipoDocumentoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cat-tipo-documentos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CatTipoDocumentoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tipo;

    private LongFilter usuarioId;

    public CatTipoDocumentoCriteria() {
    }

    public CatTipoDocumentoCriteria(CatTipoDocumentoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tipo = other.tipo == null ? null : other.tipo.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
    }

    @Override
    public CatTipoDocumentoCriteria copy() {
        return new CatTipoDocumentoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTipo() {
        return tipo;
    }

    public void setTipo(StringFilter tipo) {
        this.tipo = tipo;
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
        final CatTipoDocumentoCriteria that = (CatTipoDocumentoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(tipo, that.tipo) &&
            Objects.equals(usuarioId, that.usuarioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        tipo,
        usuarioId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatTipoDocumentoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tipo != null ? "tipo=" + tipo + ", " : "") +
                (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            "}";
    }

}
