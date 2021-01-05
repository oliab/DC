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
import com.resamx.domain.CatSucursal;
import com.resamx.domain.DatosUsuario;
import com.resamx.domain.User;

public class DatosUsuarioDTO {

    private Long id;

    @NotBlank
    @Size(max = 150)
    private String puesto;
    
    private LocalDate fechaAct;

    @NotNull
    @JsonIgnoreProperties(value = "datosUsuarios", allowSetters = true)
    private CatSucursal sucursal;

    @NotNull
    @JsonIgnoreProperties(value = "datosUsuarios", allowSetters = true)
    private User user;    
    
    
    public DatosUsuarioDTO() {
        // Empty constructor needed for Jackson.
    }
    
    public DatosUsuarioDTO(DatosUsuario datosUsuario) {
        this.id = datosUsuario.getId();
        this.puesto = datosUsuario.getPuesto();
    	this.sucursal = datosUsuario.getSucursal();
    	this.user = datosUsuario.getUser();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }    
    
    public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public CatSucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(CatSucursal sucursal) {
		this.sucursal = sucursal;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DatosUsuarioDTO)) {
            return false;
        }
        return id != null && id.equals(((DatosUsuarioDTO) o).id);
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
            ", Puesto='" + getPuesto() + "'" +
            ", Sucursal='" + getSucursal().getNombre() + "'" +
            "}";
    }
	
}
