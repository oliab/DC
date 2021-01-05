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
 * Criteria class for the {@link com.resamx.domain.CatCP} entity. This class is used
 * in {@link com.resamx.web.rest.CatCPResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cat-cps?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CatCPCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private StringFilter id;

    private IntegerFilter anio;

    private LongFilter usuarioId;

    private StringFilter estadoId;

    private StringFilter municipioId;

    private LongFilter riesgoId;

    public CatCPCriteria() {
    }

    public CatCPCriteria(CatCPCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.anio = other.anio == null ? null : other.anio.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.estadoId = other.estadoId == null ? null : other.estadoId.copy();
        this.municipioId = other.municipioId == null ? null : other.municipioId.copy();
        this.riesgoId = other.riesgoId == null ? null : other.riesgoId.copy();
    }

    @Override
    public CatCPCriteria copy() {
        return new CatCPCriteria(this);
    }

    public StringFilter getId() {
        return id;
    }

    public void setId(StringFilter id) {
        this.id = id;
    }

    public IntegerFilter getAnio() {
        return anio;
    }

    public void setAnio(IntegerFilter anio) {
        this.anio = anio;
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

    public LongFilter getRiesgoId() {
        return riesgoId;
    }

    public void setRiesgoId(LongFilter riesgoId) {
        this.riesgoId = riesgoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CatCPCriteria that = (CatCPCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(anio, that.anio) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(estadoId, that.estadoId) &&
            Objects.equals(municipioId, that.municipioId) &&
            Objects.equals(riesgoId, that.riesgoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        anio,
        usuarioId,
        estadoId,
        municipioId,
        riesgoId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatCPCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (anio != null ? "anio=" + anio + ", " : "") +
                (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
                (estadoId != null ? "estadoId=" + estadoId + ", " : "") +
                (municipioId != null ? "municipioId=" + municipioId + ", " : "") +
                (riesgoId != null ? "riesgoId=" + riesgoId + ", " : "") +
            "}";
    }

}
