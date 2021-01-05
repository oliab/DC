package com.resamx.service.dto;

public class ClienteDTO {
	
    private Long id;
    
    private DatosGeneralesCliente datosGeneralesCliente;
    private DatosDireccionCliente datosDireccionCliente;
    private DatosEmpresaCliente datosEmpresaCliente;
    private DatosDireccionCliente datosDireccionEmpresa;
   	
    
	public ClienteDTO() {
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public DatosGeneralesCliente getDatosGeneralesCliente() {
		return datosGeneralesCliente;
	}


	public void setDatosGeneralesCliente(DatosGeneralesCliente datosGeneralesCliente) {
		this.datosGeneralesCliente = datosGeneralesCliente;
	}


	public DatosDireccionCliente getDatosDireccionCliente() {
		return datosDireccionCliente;
	}


	public void setDatosDireccionCliente(DatosDireccionCliente datosDireccionCliente) {
		this.datosDireccionCliente = datosDireccionCliente;
	}


	public DatosEmpresaCliente getDatosEmpresaCliente() {
		return datosEmpresaCliente;
	}


	public void setDatosEmpresaCliente(DatosEmpresaCliente datosEmpresaCliente) {
		this.datosEmpresaCliente = datosEmpresaCliente;
	}


	public DatosDireccionCliente getDatosDireccionEmpresa() {
		return datosDireccionEmpresa;
	}


	public void setDatosDireccionEmpresa(DatosDireccionCliente datosDireccionEmpresa) {
		this.datosDireccionEmpresa = datosDireccionEmpresa;
	}
	
}
