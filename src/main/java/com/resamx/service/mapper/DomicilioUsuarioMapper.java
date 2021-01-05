package com.resamx.service.mapper;
import com.resamx.domain.DomicilioEmpresa;
import com.resamx.domain.DomicilioUsuario;
import com.resamx.service.dto.DomicilioUsuarioDTO;

public class DomicilioUsuarioMapper {
	
    public DomicilioUsuarioDTO DomicilioUsuarioToDomicilioUsuarioDTO(DomicilioUsuario datosUsuario) {
        return new DomicilioUsuarioDTO(datosUsuario);
    }
    
    public DomicilioUsuarioDTO DomicilioEmpresaToDomicilioUsuarioDTO(DomicilioEmpresa datosUsuario) {
        return new DomicilioUsuarioDTO(datosUsuario);
    }

    public DomicilioUsuario domicilioUsuarioDTOToDomicilioUsuario(DomicilioUsuarioDTO datosUsuarioDTO) {
        if (datosUsuarioDTO == null) {
            return null;
        } else {
            DomicilioUsuario datosUsuario = new DomicilioUsuario();
            datosUsuario.setId(datosUsuarioDTO.getId());
            datosUsuario.setCalle(datosUsuarioDTO.getCalle());
            datosUsuario.setColonia(datosUsuarioDTO.getColonia());
            datosUsuario.setCp(datosUsuarioDTO.getCp());
            datosUsuario.setDomicilio(datosUsuarioDTO.getDomicilio());
            datosUsuario.setEstado(datosUsuarioDTO.getEstado());            
            datosUsuario.setLocalidad(datosUsuarioDTO.getLocalidad());
            datosUsuario.setMunicipio(datosUsuarioDTO.getMunicipio());
            datosUsuario.setNacionalidad(datosUsuarioDTO.getNacionalidad());
            datosUsuario.setNoExt(datosUsuarioDTO.getNoExt());
            datosUsuario.setNoInt(datosUsuarioDTO.getNoInt());
            datosUsuario.setPais(datosUsuarioDTO.getPais());
            datosUsuario.setPaisOrigen(datosUsuarioDTO.getPaisOrigen()); 
            return datosUsuario;
        }
    }
    
    
    public DomicilioEmpresa domicilioUsuarioDTOToDomicilioEmpresa(DomicilioUsuarioDTO datosUsuarioDTO) {
        if (datosUsuarioDTO == null) {
            return null;
        } else {
        	DomicilioEmpresa datosUsuario = new DomicilioEmpresa();
            datosUsuario.setId(datosUsuarioDTO.getId());
            datosUsuario.setCalle(datosUsuarioDTO.getCalle());
            datosUsuario.setColonia(datosUsuarioDTO.getColonia());
            datosUsuario.setCp(datosUsuarioDTO.getCp());
            datosUsuario.setDomicilio(datosUsuarioDTO.getDomicilio());
            datosUsuario.setEstado(datosUsuarioDTO.getEstado());            
            datosUsuario.setLocalidad(datosUsuarioDTO.getLocalidad());
            datosUsuario.setMunicipio(datosUsuarioDTO.getMunicipio());
            datosUsuario.setNacionalidad(datosUsuarioDTO.getNacionalidad());
            datosUsuario.setNoExt(datosUsuarioDTO.getNoExt());
            datosUsuario.setNoInt(datosUsuarioDTO.getNoInt());
            datosUsuario.setPais(datosUsuarioDTO.getPais());
            datosUsuario.setPaisOrigen(datosUsuarioDTO.getPaisOrigen()); 
            return datosUsuario;
        }
    }

}
