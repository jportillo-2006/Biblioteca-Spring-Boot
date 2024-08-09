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
 
import com.jeffersonportillo.webapp.biblioteca.model.Empleado;
import com.jeffersonportillo.webapp.biblioteca.service.EmpleadoService;
 
@Controller
@RestController
@RequestMapping(value = "empleado")
public class EmpleadoController {
 
  @Autowired
  EmpleadoService empleadoService;
 
  @GetMapping("/empleados")
  public ResponseEntity<List<Empleado>> listarEmpleados() {
    try {
      return ResponseEntity.ok(empleadoService.listarEmpleados());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(null);
    }
  }
 
  @GetMapping("/empleado")
  public ResponseEntity<Empleado> buscarEmpleadoPorId(@RequestParam Long id) {
    try {
      return ResponseEntity.ok(empleadoService.buscarEmpleadoPorId(id));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(null);
    }
  }
 
  @PostMapping("/empleado")
  public ResponseEntity<Map<String, String>> agregarEmpleado(@RequestBody Empleado empleado) {
    Map<String, String> response = new HashMap<>();
    try {
      if (empleadoService.guardarEmpleado(empleado)) {
        response.put("message", "Empleado creado con exito");
        return ResponseEntity.ok(response);
      }else{
        response.put("message", "Error");
        response.put("err", "El empleado no se registro, por DPI duplicado");
        return ResponseEntity.badRequest().body(response);
      }
    } catch (Exception e) {
      response.put("message", "Error");
      response.put("err", "Hubo un error al crear el cliente");
      return ResponseEntity.badRequest().body(response);
    }
  }

  @PutMapping("/empleado")
    public ResponseEntity<Map<String, String>>editarEmpleado(@RequestParam Long id, @RequestBody Empleado empleadoNuevo){
        Map<String,String> response = new HashMap<>();
        try {
            Empleado empleado = empleadoService.buscarEmpleadoPorId(id);
            empleado.setNombre(empleadoNuevo.getNombre());
            empleado.setApellido(empleadoNuevo.getApellido());
            empleado.setTelefono(empleadoNuevo.getTelefono());
            empleado.setDireccion(empleadoNuevo.getDireccion());
            empleado.setDpi(empleadoNuevo.getDpi());
            empleadoService.guardarEmpleado(empleado);
            if(empleadoService.guardarEmpleado(empleado)){
                response.put("message", "Empleado se ha editado");
                return ResponseEntity.ok(response);
            }else{
                response.put("message", "DPI Duplicado");
                return ResponseEntity.badRequest().body(response);
            }    
        } catch (Exception e) {
            response.put("message", "Error");
            response.put("message", "Hubo Un Error Al Editar El Empleado");
            return ResponseEntity.badRequest().body(response);
        }
    }

  @DeleteMapping("/empleado")
    public ResponseEntity<Map<String, String>>eliminarEmpleado(@RequestParam Long id){
        Map<String,String> response = new HashMap<>();
        try {
            Empleado empleado = empleadoService.buscarEmpleadoPorId(id);
            empleadoService.eliminarEmpleado(empleado); 
            response.put("message", "El Empleado Se Elimino Con Exito");
            return ResponseEntity.ok(response); 
        } catch (Exception e) {
            response.put("message", "Hubo Un Error Al Eliminar El Empleado");
            return ResponseEntity.badRequest().body(response);
        }
    }
}