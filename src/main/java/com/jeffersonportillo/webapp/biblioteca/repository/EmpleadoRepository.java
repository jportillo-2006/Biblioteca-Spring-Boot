package com.jeffersonportillo.webapp.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeffersonportillo.webapp.biblioteca.model.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long>{

}
