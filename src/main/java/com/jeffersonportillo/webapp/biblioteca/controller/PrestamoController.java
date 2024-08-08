package com.jeffersonportillo.webapp.biblioteca.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeffersonportillo.webapp.biblioteca.model.Libro;
import com.jeffersonportillo.webapp.biblioteca.model.Prestamo;
import com.jeffersonportillo.webapp.biblioteca.service.PrestamoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RestController
@RequestMapping(value = "prestamo")
public class PrestamoController {

    @Autowired
    PrestamoService prestamoService;

    @GetMapping("/prestamos")
    public List<Prestamo> listarPrestamos() {
        return prestamoService.listarPrestamos();
    }
    
    @GetMapping("/prestamo")
    public ResponseEntity<Prestamo> buscarPrestamoPorId(@RequestParam Long id){
        try {
            return ResponseEntity.ok(prestamoService.buscarPrestamoPorId(id));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Map<String, String>> agregarPrestamo(@RequestBody Prestamo prestamo){
        Map<String, String> response = new HashMap<>();
        try{
            prestamoService.guardarPrestamo(prestamo);
            response.put("message", "Prestamo creado con exito");
            return ResponseEntity.ok(response);
        }catch(Exception e){
            response.put("message", "Error");
            response.put("err", "Hubo un error al crear el prestamo");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/libro")
    public ResponseEntity<Map<String, String>> editarPrestamo(@RequestParam Long id, @RequestBody Prestamo fechaDePrestamoNuevo, @RequestBody Prestamo fechaDeDevolucionNueva, @RequestBody Prestamo vigenciaNueva, @RequestBody Prestamo empleadoNueva, @RequestBody Prestamo clienteNuevo, @RequestBody Prestamo librosNuevos){
        Map<String, String> response = new HashMap<>();
        try {
            Prestamo prestamo = prestamoService.buscarPrestamoPorId(id);

            prestamo.setFechaDePrestamo(fechaDePrestamoNuevo.getFechaDePrestamo());
            prestamo.setFechaDeDevolucion(fechaDeDevolucionNueva.getFechaDeDevolucion());
            prestamo.setVigencia(vigenciaNueva.getVigencia());
            prestamo.setEmpleado(empleadoNueva.getEmpleado());
            prestamo.setCliente(clienteNuevo.getCliente());
            prestamo.setLibros(librosNuevos.getLibros());
            prestamoService.guardarPrestamo(prestamo);
            response.put("message", "El prestamo se ha editado");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "El prestamo no pudo editarse");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/prestamo")
    public ResponseEntity<Map<String, String>> eliminarPrestamo(@RequestParam Long id){
        Map<String, String> response = new HashMap<>();
        try {
            Prestamo prestamo = prestamoService.buscarPrestamoPorId(id);
            prestamoService.eliminarPrestamo(prestamo);
            response.put("message", "Prestamo eliminado con exito");
            return ResponseEntity.ok(response);
        } catch(Exception e) {
            response.put("message", "Prestamo eliminado con exito");
            return ResponseEntity.badRequest().body(response);
        }
    }
}