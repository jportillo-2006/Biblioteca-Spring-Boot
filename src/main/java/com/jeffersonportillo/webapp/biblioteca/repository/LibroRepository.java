package com.jeffersonportillo.webapp.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeffersonportillo.webapp.biblioteca.model.Libro;

public interface LibroRepository extends JpaRepository <Libro, Long>{

}
