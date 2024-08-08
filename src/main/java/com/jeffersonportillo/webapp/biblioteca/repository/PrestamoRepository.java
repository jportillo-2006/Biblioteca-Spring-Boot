package com.jeffersonportillo.webapp.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeffersonportillo.webapp.biblioteca.model.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long>{
    
}
