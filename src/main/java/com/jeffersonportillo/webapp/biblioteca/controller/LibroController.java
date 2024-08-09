package com.jeffersonportillo.webapp.biblioteca.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jeffersonportillo.webapp.biblioteca.model.Libro;
import com.jeffersonportillo.webapp.biblioteca.service.LibroService;

@Controller
@RestController
@RequestMapping(value = "libro")
public class LibroController {
    
    @Autowired
    LibroService libroService;

    @GetMapping("/libros")
    public List<Libro> listarLibros(){
        return libroService.listarLibros();
    }

    @GetMapping("/libro")
    public ResponseEntity<Libro> buscarLibroPorId(@RequestParam Long id){
        try {
            return ResponseEntity.ok(libroService.buscarLibroPorId(id));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @PostMapping("/")
    public ResponseEntity<Map<String, String>> agregarLibro(@RequestBody Libro libro){
        Map<String, String> response = new HashMap<>();
        try{
            libroService.guardarLibro(libro);
            response.put("message", "Libro creado con exito");
            return ResponseEntity.ok(response);
        }catch(Exception e){
            response.put("message", "Error");
            response.put("err", "Hubo un error al crear el libro");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/libro")
    public ResponseEntity<Map<String, String>> editarLibro(@RequestParam Long id, @RequestBody Libro isbnNuevo, @RequestBody Libro nombreNuevo, @RequestBody Libro sinopsisNueva, @RequestBody Libro autorNuevo, @RequestBody Libro editorialNueva, @RequestBody Libro disponibilidadNueva, @RequestBody Libro numeroEstanteriaNueva, @RequestBody Libro clusterNuevo, @RequestBody Libro categoriaNueva) {
        Map<String, String> response = new HashMap<>();
        try {
            Libro libro = libroService.buscarLibroPorId(id);

            libro.setIsbn(isbnNuevo.getIsbn());
            libro.setNombre(nombreNuevo.getNombre());
            libro.setSinopsis(sinopsisNueva.getSinopsis());
            libro.setAutor(autorNuevo.getAutor());
            libro.setEditorial(editorialNueva.getEditorial());
            libro.setDisponibilidad(disponibilidadNueva.getDisponibilidad());
            libro.setNumeroEstanteria(numeroEstanteriaNueva.getNumeroEstanteria());
            libro.setCluster(clusterNuevo.getCluster());
            libro.setCategoria(categoriaNueva.getCategoria());
            libroService.guardarLibro(libro);
            response.put("message", "El libro se ha editado");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "El libro no pudo editarse");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/libro")
    public ResponseEntity<Map<String, String>> eliminarLibro(@RequestParam Long id){
        Map<String, String> response = new HashMap<>();
        try {
            Libro libro = libroService.buscarLibroPorId(id);
            libroService.eliminarLibro(libro);
            response.put("message", "Libro eliminado con exito");
            return ResponseEntity.ok(response);
        } catch(Exception e) {
            response.put("message", "Libro eliminado con exito");
            return ResponseEntity.badRequest().body(response);
        }
    }
}