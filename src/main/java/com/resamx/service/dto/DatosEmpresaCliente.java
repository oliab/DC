package com.resamx.service.dto;

import java.time.LocalDate;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.resamx.domain.CatIdentificacion;

public class DatosEmpresaCliente {

    private Long id;

    @NotNull
    private Boolean fideicomiso;

    @NotNull
    @Size(max = 15)
    private String rfc;

    @NotNull
    @Size(max = 250)
    private String razonSocial;

    @NotNull
    @Size(max = 100)
    private String noIdentificacion;

    @Size(max = 50)
    private String telefono;

    private LocalDate fechaAlta;

    private LocalDate fechaAct;

    @NotNull
    private CatIdentificacion tipoIdentificacion;
    
    @Lob
    @NotNull
    private byte[] comprobanteIdentificacion;

    @NotNull
    private String identificacionContentType;
    
    private String descripcionIdentificacion;
    
    @Lob
    @NotNull
    private byte[] comprobanteFirma;

    @NotNull
    private String firmaContentType;
    
    private String descripcionFirma;
    
    public DatosEmpresaCliente() {
    	
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getFideicomiso() {
		return fideicomiso;
	}

	public void setFideicomiso(Boolean fideicomiso) {
		this.fideicomiso = fideicomiso;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNoIdentificacion() {
		return noIdentificacion;
	}

	public void setNoIdentificacion(String noIdentificacion) {
		this.noIdentificacion = noIdentificacion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public LocalDate getFechaAct() {
		return fechaAct;
	}

	public void setFechaAct(LocalDate fechaAct) {
		this.fechaAct = fechaAct;
	}

	public CatIdentificacion getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(CatIdentificacion tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public byte[] getComprobanteIdentificacion() {
		return comprobanteIdentificacion;
	}

	public void setComprobanteIdentificacion(byte[] comprobanteIdentificacion) {
		this.comprobanteIdentificacion = comprobanteIdentificacion;
	}

	public String getIdentificacionContentType() {
		return identificacionContentType;
	}

	public void setIdentificacionContentType(String identificacionContentType) {
		this.identificacionContentType = identificacionContentType;
	}

	public String getDescripcionIdentificacion() {
		return descripcionIdentificacion;
	}

	public void setDescripcionIdentificacion(String descripcionIdentificacion) {
		this.descripcionIdentificacion = descripcionIdentificacion;
	}

	public byte[] getComprobanteFirma() {
		return comprobanteFirma;
	}

	public void setComprobanteFirma(byte[] comprobanteFirma) {
		this.comprobanteFirma = comprobanteFirma;
	}

	public String getFirmaContentType() {
		return firmaContentType;
	}

	public void setFirmaContentType(String firmaContentType) {
		this.firmaContentType = firmaContentType;
	}

	public String getDescripcionFirma() {
		return descripcionFirma;
	}

	public void setDescripcionFirma(String descripcionFirma) {
		this.descripcionFirma = descripcionFirma;
	}
}
