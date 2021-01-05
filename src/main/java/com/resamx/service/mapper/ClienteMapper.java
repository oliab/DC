package com.resamx.service.mapper;

import com.resamx.domain.Cliente;
import com.resamx.service.dto.ClienteDTO;

public class ClienteMapper {
	
	
	public ClienteDTO ClienteToClienteDTO(Cliente cliente) {
        return new ClienteDTO();
    }

    public Cliente clienteDTOToCliente(ClienteDTO clienteDTO) {
        if (clienteDTO == null) {
            return null;
        } else {
            Cliente cliente = new Cliente();
            
            return cliente;
        }
    }

}
