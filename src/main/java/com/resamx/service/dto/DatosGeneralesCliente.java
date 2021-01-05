package com.resamx.service.dto;

import java.time.LocalDate;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.resamx.domain.CatIdentificacion;
import com.resamx.domain.CatMoneda;
import com.resamx.domain.CatSector;
import com.resamx.domain.CatTipoEmpresa;
import com.resamx.domain.User;

public class DatosGeneralesCliente {
	
	private Long id;

    @NotBlank
    @Size(max = 100)
    private String noIdentificacion;

    @NotBlank
    private Double ingresos;

    @NotBlank
    private Double estimacionOperacion;

    @Size(max = 50)
    private String telefono;

    private LocalDate fechaAlta;

    private LocalDate fechaAct;
	
    @NotNull
	private UserDTO usuario;        
	
    @NotNull
    private CatTipoEmpresa tipoCliente;

    @NotNull
    private CatIdentificacion tipoIdentificacion;

    @NotNull
    private CatSector sector;

    @NotNull
    private CatMoneda moneda;
    
    private User usuarioAct;
    
    @Lob
    @NotNull
    private byte[] comprobanteIdentificacion;

    @NotNull
    private String identificacionContentType;
    
    private String descripcionIdentificacion;
    
    @Lob
    @NotNull
    private byte[] comprobanteIngresos;

    @NotNull
    private String ingresosContentType;
    
    private String descripcionIngresos;
	
	public DatosGeneralesCliente() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNoIdentificacion() {
		return noIdentificacion;
	}

	public void setNoIdentificacion(String noIdentificacion) {
		this.noIdentificacion = noIdentificacion;
	}

	public Double getIngresos() {
		return ingresos;
	}

	public void setIngresos(Double ingresos) {
		this.ingresos = ingresos;
	}

	public Double getEstimacionOperacion() {
		return estimacionOperacion;
	}

	public void setEstimacionOperacion(Double estimacionOperacion) {
		this.estimacionOperacion = estimacionOperacion;
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

	public UserDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UserDTO usuario) {
		this.usuario = usuario;
	}

	public CatTipoEmpresa getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(CatTipoEmpresa tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public CatIdentificacion getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(CatIdentificacion tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public CatSector getSector() {
		return sector;
	}

	public void setSector(CatSector sector) {
		this.sector = sector;
	}

	public CatMoneda getMoneda() {
		return moneda;
	}

	public void setMoneda(CatMoneda moneda) {
		this.moneda = moneda;
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

	public byte[] getComprobanteIngresos() {
		return comprobanteIngresos;
	}

	public void setComprobanteIngresos(byte[] comprobanteIngresos) {
		this.comprobanteIngresos = comprobanteIngresos;
	}

	public String getIngresosContentType() {
		return ingresosContentType;
	}

	public void setIngresosContentType(String ingresosContentType) {
		this.ingresosContentType = ingresosContentType;
	}

	public String getDescripcionIngresos() {
		return descripcionIngresos;
	}

	public void setDescripcionIngresos(String descripcionIngresos) {
		this.descripcionIngresos = descripcionIngresos;
	}

	public User getUsuarioAct() {
		return usuarioAct;
	}

	public void setUsuarioAct(User usuarioAct) {
		this.usuarioAct = usuarioAct;
	}	

}
