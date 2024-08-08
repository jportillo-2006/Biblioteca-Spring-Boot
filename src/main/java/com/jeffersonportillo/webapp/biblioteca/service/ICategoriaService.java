package com.jeffersonportillo.webapp.biblioteca.service;

import java.util.List;

import com.jeffersonportillo.webapp.biblioteca.model.Categoria;

public interface ICategoriaService {
    
    public List<Categoria> listarCategorias();

    public Categoria buscarCategoriaPorId(Long id);

    public Categoria guardarCategoria(Categoria categoria);

    public void eliminarCategoria(Categoria categoria);
}
