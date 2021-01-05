package com.resamx.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.resamx.domain.CatCP;
import com.resamx.domain.CatEstado;
import com.resamx.domain.CatLocalidad;
import com.resamx.domain.CatMunicipio;
import com.resamx.domain.CatNacionalidad;
import com.resamx.domain.CatPais;
import com.resamx.domain.DomicilioEmpresa;
import com.resamx.domain.DomicilioUsuario;

public class DomicilioUsuarioDTO {

    private Long id;

    @NotBlank
    @Size(max = 100)
    private String colonia;

    @NotBlank
    @Size(max = 100)
    private String calle;

    @NotBlank
    @Size(max = 20)
    private String noExt;

    @NotBlank
    @Size(max = 20)
    private String noInt;

    @NotBlank
    @Size(max = 512)
    private String domicilio;

    private LocalDate fechaAct;

    @NotNull
    @JsonIgnoreProperties(value = "datosUsuarios", allowSetters = true)
    private CatNacionalidad nacionalidad;

    @NotNull
    @JsonIgnoreProperties(value = "datosUsuarios", allowSetters = true)
    private CatPais paisOrigen;

    @NotNull
    @JsonIgnoreProperties(value = "datosUsuarios", allowSetters = true)
    private CatPais pais;

    @NotNull
    @JsonIgnoreProperties(value = "datosUsuarios", allowSetters = true)
    private CatEstado estado;

    @NotNull
    @JsonIgnoreProperties(value = "datosUsuarios", allowSetters = true)
    private CatMunicipio municipio;

    @JsonIgnoreProperties(value = "datosUsuarios", allowSetters = true)
    private CatLocalidad localidad;

    @NotNull
    @JsonIgnoreProperties(value = "datosUsuarios", allowSetters = true)
    private CatCP cp;
    
    
    public DomicilioUsuarioDTO() {
        // Empty constructor needed for Jackson.
    }
    
    public DomicilioUsuarioDTO(DomicilioUsuario datosUsuario) {
        this.id = datosUsuario.getId();
        this.calle =datosUsuario.getCalle();
    	this.colonia = datosUsuario.getColonia();
    	this.cp = datosUsuario.getCp();
    	this.domicilio = datosUsuario.getDomicilio();
    	this.estado = datosUsuario.getEstado();
    	this.localidad = datosUsuario.getLocalidad();
    	this.municipio = datosUsuario.getMunicipio();
    	this.nacionalidad = datosUsuario.getNacionalidad();
    	this.noExt = datosUsuario.getNoExt();
    	this.noInt = datosUsuario.getNoInt();
    	this.pais = datosUsuario.getPais();
    	this.paisOrigen = datosUsuario.getPaisOrigen();  
    }
    
    
    public DomicilioUsuarioDTO(DomicilioEmpresa datosUsuario) {
        this.id = datosUsuario.getId();
        this.calle =datosUsuario.getCalle();
    	this.colonia = datosUsuario.getColonia();
    	this.cp = datosUsuario.getCp();
    	this.domicilio = datosUsuario.getDomicilio();
    	this.estado = datosUsuario.getEstado();
    	this.localidad = datosUsuario.getLocalidad();
    	this.municipio = datosUsuario.getMunicipio();
    	this.nacionalidad = datosUsuario.getNacionalidad();
    	this.noExt = datosUsuario.getNoExt();
    	this.noInt = datosUsuario.getNoInt();
    	this.pais = datosUsuario.getPais();
    	this.paisOrigen = datosUsuario.getPaisOrigen();  
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCalle() {
        return calle;
    }


    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNoExt() {
        return noExt;
    }


    public void setNoExt(String noExt) {
        this.noExt = noExt;
    }

    public String getNoInt() {
        return noInt;
    }

    public void setNoInt(String noInt) {
        this.noInt = noInt;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public LocalDate getFechaAct() {
        return fechaAct;
    }

    public void setFechaAct(LocalDate fechaAct) {
        this.fechaAct = fechaAct;
    }

    public CatNacionalidad getNacionalidad() {
        return nacionalidad;
    }


    public void setNacionalidad(CatNacionalidad catNacionalidad) {
        this.nacionalidad = catNacionalidad;
    }

    public CatPais getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(CatPais catPais) {
        this.paisOrigen = catPais;
    }

    public CatPais getPais() {
        return pais;
    }

    public void setPais(CatPais catPais) {
        this.pais = catPais;
    }

    public CatEstado getEstado() {
        return estado;
    }

    public void setEstado(CatEstado catEstado) {
        this.estado = catEstado;
    }

    public CatMunicipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(CatMunicipio catMunicipio) {
        this.municipio = catMunicipio;
    }

    public CatLocalidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(CatLocalidad catLocalidad) {
        this.localidad = catLocalidad;
    }

    public CatCP getCp() {
        return cp;
    }

    public void setCp(CatCP catCP) {
        this.cp = catCP;
    }

    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DatosUsuarioDTO)) {
            return false;
        }
        return id != null && id.equals(((DomicilioUsuarioDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DatosUsuarioDTO{" +
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
