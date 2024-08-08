package com.jeffersonportillo.webapp.biblioteca.service;

import java.util.List;

import com.jeffersonportillo.webapp.biblioteca.model.Cliente;

public interface IClienteService {
    
    public List<Cliente> listarClientes();

    public Cliente buscarClientePorId(Long dpi);

    public Cliente guardarCliente(Cliente cliente);

    public void eliminarCliente(Cliente cliente);
}
