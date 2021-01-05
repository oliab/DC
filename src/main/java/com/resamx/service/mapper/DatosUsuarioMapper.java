package com.resamx.service.mapper;
import com.resamx.domain.DatosUsuario;
import com.resamx.service.dto.DatosUsuarioDTO;

public class DatosUsuarioMapper {
	
    public DatosUsuarioDTO DatosUsuarioToDatosUsuarioDTO(DatosUsuario datosUsuario) {
        return new DatosUsuarioDTO(datosUsuario);
    }

    public DatosUsuario datosUsuarioDTOToDatosUsuario(DatosUsuarioDTO datosUsuarioDTO) {
        if (datosUsuarioDTO == null) {
            return null;
        } else {
            DatosUsuario datosUsuario = new DatosUsuario();
            datosUsuario.setId(datosUsuarioDTO.getId());
            datosUsuario.setPuesto(datosUsuarioDTO.getPuesto());
            datosUsuario.setSucursal(datosUsuarioDTO.getSucursal());
            datosUsuario.setUser(datosUsuarioDTO.getUser());            
            return datosUsuario;
        }
    }
}
