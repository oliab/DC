package com.resamx.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DomicilioEmpresa.
 */
@Entity
@Table(name = "domicilio_empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DomicilioEmpresa implements Serializable {

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
    @JsonIgnoreProperties(value = "domicilioEmpresas", allowSetters = true)
    private CatNacionalidad nacionalidad;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "domicilioEmpresas", allowSetters = true)
    private CatPais paisOrigen;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "domicilioEmpresas", allowSetters = true)
    private CatPais pais;

    @ManyToOne
    @JsonIgnoreProperties(value = "domicilioEmpresas", allowSetters = true)
    private CatEstado estado;

    @ManyToOne
    @JsonIgnoreProperties(value = "domicilioEmpresas", allowSetters = true)
    private CatMunicipio municipio;

    @ManyToOne
    @JsonIgnoreProperties(value = "domicilioEmpresas", allowSetters = true)
    private CatLocalidad localidad;

    @ManyToOne
    @JsonIgnoreProperties(value = "domicilioEmpresas", allowSetters = true)
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

    public DomicilioEmpresa colonia(String colonia) {
        this.colonia = colonia;
        return this;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCalle() {
        return calle;
    }

    public DomicilioEmpresa calle(String calle) {
        this.calle = calle;
        return this;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNoExt() {
        return noExt;
    }

    public DomicilioEmpresa noExt(String noExt) {
        this.noExt = noExt;
        return this;
    }

    public void setNoExt(String noExt) {
        this.noExt = noExt;
    }

    public String getNoInt() {
        return noInt;
    }

    public DomicilioEmpresa noInt(String noInt) {
        this.noInt = noInt;
        return this;
    }

    public void setNoInt(String noInt) {
        this.noInt = noInt;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public DomicilioEmpresa domicilio(String domicilio) {
        this.domicilio = domicilio;
        return this;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public LocalDate getFechaAct() {
        return fechaAct;
    }

    public DomicilioEmpresa fechaAct(LocalDate fechaAct) {
        this.fechaAct = fechaAct;
        return this;
    }

    public void setFechaAct(LocalDate fechaAct) {
        this.fechaAct = fechaAct;
    }

    public CatNacionalidad getNacionalidad() {
        return nacionalidad;
    }

    public DomicilioEmpresa nacionalidad(CatNacionalidad catNacionalidad) {
        this.nacionalidad = catNacionalidad;
        return this;
    }

    public void setNacionalidad(CatNacionalidad catNacionalidad) {
        this.nacionalidad = catNacionalidad;
    }

    public CatPais getPaisOrigen() {
        return paisOrigen;
    }

    public DomicilioEmpresa paisOrigen(CatPais catPais) {
        this.paisOrigen = catPais;
        return this;
    }

    public void setPaisOrigen(CatPais catPais) {
        this.paisOrigen = catPais;
    }

    public CatPais getPais() {
        return pais;
    }

    public DomicilioEmpresa pais(CatPais catPais) {
        this.pais = catPais;
        return this;
    }

    public void setPais(CatPais catPais) {
        this.pais = catPais;
    }

    public CatEstado getEstado() {
        return estado;
    }

    public DomicilioEmpresa estado(CatEstado catEstado) {
        this.estado = catEstado;
        return this;
    }

    public void setEstado(CatEstado catEstado) {
        this.estado = catEstado;
    }

    public CatMunicipio getMunicipio() {
        return municipio;
    }

    public DomicilioEmpresa municipio(CatMunicipio catMunicipio) {
        this.municipio = catMunicipio;
        return this;
    }

    public void setMunicipio(CatMunicipio catMunicipio) {
        this.municipio = catMunicipio;
    }

    public CatLocalidad getLocalidad() {
        return localidad;
    }

    public DomicilioEmpresa localidad(CatLocalidad catLocalidad) {
        this.localidad = catLocalidad;
        return this;
    }

    public void setLocalidad(CatLocalidad catLocalidad) {
        this.localidad = catLocalidad;
    }

    public CatCP getCp() {
        return cp;
    }

    public DomicilioEmpresa cp(CatCP catCP) {
        this.cp = catCP;
        return this;
    }

    public void setCp(CatCP catCP) {
        this.cp = catCP;
    }

    public User getUser() {
        return user;
    }

    public DomicilioEmpresa user(User user) {
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
        if (!(o instanceof DomicilioEmpresa)) {
            return false;
        }
        return id != null && id.equals(((DomicilioEmpresa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DomicilioEmpresa{" +
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
