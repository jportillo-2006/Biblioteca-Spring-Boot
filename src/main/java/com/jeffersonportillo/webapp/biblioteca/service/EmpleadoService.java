package com.jeffersonportillo.webapp.biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeffersonportillo.webapp.biblioteca.model.Empleado;
import com.jeffersonportillo.webapp.biblioteca.repository.EmpleadoRepository;

@Service
public class EmpleadoService implements IEmpleadoService{

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public List<Empleado> listarEmpleados() {
        return empleadoRepository.findAll();
    }

    @Override
    public Empleado buscarEmpleadoPorId(Long id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean guardarEmpleado(Empleado empleado) {
        if (!verificarDpiDuplicado(empleado)) {
            empleadoRepository.save(empleado);
            return true;
        } else{
            return false;
        }
    }

    @Override
    public void eliminarEmpleado(Empleado empleado) {
        empleadoRepository.delete(empleado); 
    }

    @Override
    public Boolean verificarDpiDuplicado(Empleado empleadoNuevo) {
        List<Empleado> empleados = listarEmpleados();
        Boolean flag = false;

        for (Empleado empleado : empleados){
            if (empleadoNuevo.getDpi().equals(empleado.getDpi()) && !empleado.getId().equals(empleadoNuevo.getId())) {
                flag = true;
            }
        }
        return flag;
    }
}
