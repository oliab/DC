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
 * Criteria class for the {@link com.resamx.domain.CatSector} entity. This class is used
 * in {@link com.resamx.web.rest.CatSectorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cat-sectors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CatSectorCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private StringFilter id;

    private StringFilter actividadEconomica;

    private BooleanFilter actividadVulnerable;

    private LongFilter usuarioId;

    public CatSectorCriteria() {
    }

    public CatSectorCriteria(CatSectorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.actividadEconomica = other.actividadEconomica == null ? null : other.actividadEconomica.copy();
        this.actividadVulnerable = other.actividadVulnerable == null ? null : other.actividadVulnerable.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
    }

    @Override
    public CatSectorCriteria copy() {
        return new CatSectorCriteria(this);
    }

    public StringFilter getId() {
        return id;
    }

    public void setId(StringFilter id) {
        this.id = id;
    }

    public StringFilter getActividadEconomica() {
        return actividadEconomica;
    }

    public void setActividadEconomica(StringFilter actividadEconomica) {
        this.actividadEconomica = actividadEconomica;
    }

    public BooleanFilter getActividadVulnerable() {
        return actividadVulnerable;
    }

    public void setActividadVulnerable(BooleanFilter actividadVulnerable) {
        this.actividadVulnerable = actividadVulnerable;
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
        final CatSectorCriteria that = (CatSectorCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(actividadEconomica, that.actividadEconomica) &&
            Objects.equals(actividadVulnerable, that.actividadVulnerable) &&
            Objects.equals(usuarioId, that.usuarioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        actividadEconomica,
        actividadVulnerable,
        usuarioId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatSectorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (actividadEconomica != null ? "actividadEconomica=" + actividadEconomica + ", " : "") +
                (actividadVulnerable != null ? "actividadVulnerable=" + actividadVulnerable + ", " : "") +
                (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            "}";
    }

}
