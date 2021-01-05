package com.resamx.config;

public enum TipoDocumentoEnum {

	IdentificacionOficial(1), 
	ComprobanteDomicilio(2),
	ComprobanteIngresos(3),
	DatosFiscales(4),
	FirmaElectronica(5),
	Anexo(6);
	
	TipoDocumentoEnum(long tipo) {
		this.tipo = tipo;
	}
	
	private long tipo;

	public long getTipo() {
		return tipo;
	}

	public void setTipo(long tipo) {
		this.tipo = tipo;
	}
}
