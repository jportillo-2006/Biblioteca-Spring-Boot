package com.jeffersonportillo.webapp.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeffersonportillo.webapp.biblioteca.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    
}
