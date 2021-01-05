package com.resamx.config;

public enum TipoEmpresaEnum {
	FISICA(1), MORAL(2), FIDEICOMISO(3);
	
	
	TipoEmpresaEnum(int tipo) {
		this.tipo = tipo;
	}
	
	private int tipo;

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}	
}
