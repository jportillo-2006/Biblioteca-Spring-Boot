package com.jeffersonportillo.webapp.biblioteca.service;

import java.util.List;

import com.jeffersonportillo.webapp.biblioteca.model.Cliente;
import com.jeffersonportillo.webapp.biblioteca.model.Empleado;

public interface IEmpleadoService {
    public List<Empleado> listarEmpleados();

    public Empleado buscarEmpleadoPorId(Long id);

    public Empleado guardarEmpleado(Empleado empleado);

    public void eliminarEmpleado(Empleado empleado);
}
