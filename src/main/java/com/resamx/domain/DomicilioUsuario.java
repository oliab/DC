package com.resamx.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DomicilioUsuario.
 */
@Entity
@Table(name = "domicilio_usuario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DomicilioUsuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "colonia", length = 100)
    private String colonia;

    @Size(max = 100)
    @Column(name = "calle", length = 100)
    private String calle;

    @Size(max = 20)
    @Column(name = "no_ext", length = 20)
    private String noExt;

    @Size(max = 20)
    @Column(name = "no_int", length = 20)
    private String noInt;

    @Size(max = 512)
    @Column(name = "domicilio", length = 512)
    private String domicilio;

    @Column(name = "fecha_act")
    private LocalDate fechaAct;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "domicilioUsuarios", allowSetters = true)
    private CatNacionalidad nacionalidad;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "domicilioUsuarios", allowSetters = true)
    private CatPais paisOrigen;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "domicilioUsuarios", allowSetters = true)
    private CatPais pais;

    @ManyToOne
    @JsonIgnoreProperties(value = "domicilioUsuarios", allowSetters = true)
    private CatEstado estado;

    @ManyToOne
    @JsonIgnoreProperties(value = "domicilioUsuarios", allowSetters = true)
    private CatMunicipio municipio;

    @ManyToOne
    @JsonIgnoreProperties(value = "domicilioUsuarios", allowSetters = true)
    private CatLocalidad localidad;

    @ManyToOne
    @JsonIgnoreProperties(value = "domicilioUsuarios", allowSetters = true)
    private CatCP cp;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColonia() {
        return colonia;
    }

    public DomicilioUsuario colonia(String colonia) {
        this.colonia = colonia;
        return this;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCalle() {
        return calle;
    }

    public DomicilioUsuario calle(String calle) {
        this.calle = calle;
        return this;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNoExt() {
        return noExt;
    }

    public DomicilioUsuario noExt(String noExt) {
        this.noExt = noExt;
        return this;
    }

    public void setNoExt(String noExt) {
        this.noExt = noExt;
    }

    public String getNoInt() {
        return noInt;
    }

    public DomicilioUsuario noInt(String noInt) {
        this.noInt = noInt;
        return this;
    }

    public void setNoInt(String noInt) {
        this.noInt = noInt;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public DomicilioUsuario domicilio(String domicilio) {
        this.domicilio = domicilio;
        return this;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public LocalDate getFechaAct() {
        return fechaAct;
    }

    public DomicilioUsuario fechaAct(LocalDate fechaAct) {
        this.fechaAct = fechaAct;
        return this;
    }

    public void setFechaAct(LocalDate fechaAct) {
        this.fechaAct = fechaAct;
    }

    public CatNacionalidad getNacionalidad() {
        return nacionalidad;
    }

    public DomicilioUsuario nacionalidad(CatNacionalidad catNacionalidad) {
        this.nacionalidad = catNacionalidad;
        return this;
    }

    public void setNacionalidad(CatNacionalidad catNacionalidad) {
        this.nacionalidad = catNacionalidad;
    }

    public CatPais getPaisOrigen() {
        return paisOrigen;
    }

    public DomicilioUsuario paisOrigen(CatPais catPais) {
        this.paisOrigen = catPais;
        return this;
    }

    public void setPaisOrigen(CatPais catPais) {
        this.paisOrigen = catPais;
    }

    public CatPais getPais() {
        return pais;
    }

    public DomicilioUsuario pais(CatPais catPais) {
        this.pais = catPais;
        return this;
    }

    public void setPais(CatPais catPais) {
        this.pais = catPais;
    }

    public CatEstado getEstado() {
        return estado;
    }

    public DomicilioUsuario estado(CatEstado catEstado) {
        this.estado = catEstado;
        return this;
    }

    public void setEstado(CatEstado catEstado) {
        this.estado = catEstado;
    }

    public CatMunicipio getMunicipio() {
        return municipio;
    }

    public DomicilioUsuario municipio(CatMunicipio catMunicipio) {
        this.municipio = catMunicipio;
        return this;
    }

    public void setMunicipio(CatMunicipio catMunicipio) {
        this.municipio = catMunicipio;
    }

    public CatLocalidad getLocalidad() {
        return localidad;
    }

    public DomicilioUsuario localidad(CatLocalidad catLocalidad) {
        this.localidad = catLocalidad;
        return this;
    }

    public void setLocalidad(CatLocalidad catLocalidad) {
        this.localidad = catLocalidad;
    }

    public CatCP getCp() {
        return cp;
    }

    public DomicilioUsuario cp(CatCP catCP) {
        this.cp = catCP;
        return this;
    }

    public void setCp(CatCP catCP) {
        this.cp = catCP;
    }

    public User getUser() {
        return user;
    }

    public DomicilioUsuario user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DomicilioUsuario)) {
            return false;
        }
        return id != null && id.equals(((DomicilioUsuario) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DomicilioUsuario{" +
            "id=" + getId() +
            ", colonia='" + getColonia() + "'" +
            ", calle='" + getCalle() + "'" +
            ", noExt='" + getNoExt() + "'" +
            ", noInt='" + getNoInt() + "'" +
            ", domicilio='" + getDomicilio() + "'" +
            ", fechaAct='" + getFechaAct() + "'" +
            "}";
    }
}
