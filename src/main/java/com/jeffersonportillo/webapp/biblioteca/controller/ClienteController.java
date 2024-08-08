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

import com.jeffersonportillo.webapp.biblioteca.model.Cliente;
import com.jeffersonportillo.webapp.biblioteca.service.ClienteService;

@Controller
@RestController
@RequestMapping(value = "cliente")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @GetMapping("/clientes")
    public List<Cliente> listaClientes(){
        return clienteService.listarClientes();
    }

    @GetMapping("/cliente")
    public ResponseEntity<Cliente> buscarClientePorId(@RequestParam Long dpi){
        try {
            return ResponseEntity.ok(clienteService.buscarClientePorId(dpi));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @PostMapping("/")
    public ResponseEntity<Map<String, Boolean>> agregarCliente(@RequestBody Cliente cliente){
        Map<String, Boolean> response = new HashMap<>();
        try{
            clienteService.guardarCliente(cliente);
            response.put("se agrego con exito", Boolean.TRUE);
            return ResponseEntity.ok(response);
        }catch(Exception e){
            response.put("se agrego con exito", Boolean.TRUE);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/cliente")
    public ResponseEntity<Map<String, String>> editarCliente(@RequestParam Long dpi, @RequestBody Cliente nombreNuevo, @RequestBody Cliente apellidoNuevo, @RequestBody Cliente telefonoNuevo) {
        Map<String, String> response = new HashMap<>();
        try {
            Cliente cliente = clienteService.buscarClientePorId(dpi);

            cliente.setNombre(nombreNuevo.getNombre());
            cliente.setApellido(apellidoNuevo.getApellido());
            cliente.setTelefono(telefonoNuevo.getTelefono());
            clienteService.guardarCliente(cliente);
            response.put("message", "El cliente se ha editado");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "El cliente no pudo editarse");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/cliente")
    public ResponseEntity<Map<String, String>> eliminarCliente(@RequestParam Long dpi){
        Map<String, String> response = new HashMap<>();
        try {
            Cliente cliente = clienteService.buscarClientePorId(dpi);
            clienteService.eliminarCliente(cliente);
            response.put("message", "Cliente eliminado con exito");
            return ResponseEntity.ok(response);
        } catch(Exception e) {
            response.put("message", "Cliente eliminado con exito");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
