package com.jeffersonportillo.webapp.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeffersonportillo.webapp.biblioteca.model.Cliente;

public interface ClienteRepository extends JpaRepository <Cliente, Long>{

}