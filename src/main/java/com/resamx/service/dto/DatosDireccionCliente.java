package com.resamx.service.dto;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

public class DatosDireccionCliente {
	
    private Long id;
       
    @NotNull    
    private DomicilioUsuarioDTO domicilioUsuario;

    @Lob
    @NotNull
    private byte[] comprobanteDomicilio;

    @NotNull
    private String domicilioContentType;
    
    private String descripcionDomicilio;
    
    public DatosDireccionCliente() {
    	
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DomicilioUsuarioDTO getDomicilioUsuario() {
		return domicilioUsuario;
	}

	public void setDomicilioUsuario(DomicilioUsuarioDTO domicilioUsuario) {
		this.domicilioUsuario = domicilioUsuario;
	}

	public byte[] getComprobanteDomicilio() {
		return comprobanteDomicilio;
	}

	public void setComprobanteDomicilio(byte[] comprobanteDomicilio) {
		this.comprobanteDomicilio = comprobanteDomicilio;
	}

	public String getDomicilioContentType() {
		return domicilioContentType;
	}

	public void setDomicilioContentType(String domicilioContentType) {
		this.domicilioContentType = domicilioContentType;
	}

	public String getDescripcionDomicilio() {
		return descripcionDomicilio;
	}

	public void setDescripcionDomicilio(String descripcionDomicilio) {
		this.descripcionDomicilio = descripcionDomicilio;
	}
}
