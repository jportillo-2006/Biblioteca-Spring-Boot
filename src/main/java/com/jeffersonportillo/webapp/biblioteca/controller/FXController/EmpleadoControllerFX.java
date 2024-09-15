package com.jeffersonportillo.webapp.biblioteca.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeffersonportillo.webapp.biblioteca.model.Empleado;
import com.jeffersonportillo.webapp.biblioteca.service.EmpleadoService;
import com.jeffersonportillo.webapp.biblioteca.system.Main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Setter;

@Component
public class EmpleadoControllerFX implements Initializable {

   @FXML
    TextField tfId, tfNombre, tfApellido, tfTelefono, tfDpi, tfBuscar, tfDireccion;
    @FXML
    Button btnGuardar, btnLimpiar, btnRegresar, btnEliminar, btnBuscar;
    @FXML
    TableView tblEmpleados;
    @FXML
    TableColumn colId, colApellido, colTelefono, colNombre, colDireccion, colDpi;
    @Setter
    private Main stage;
    @Autowired
    EmpleadoService empleadoService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnGuardar) {
            if(tfId.getText().isBlank()){
                guardarEmpleado();
            }else{
                editarEmpleado();
            }
        } else if (event.getSource() == btnLimpiar) {
            limpiarForm();
        } else if (event.getSource() == btnRegresar) {
            stage.indexView();
        } else if (event.getSource() == btnEliminar) {
            eliminarEmpleado();
        } else if (event.getSource() == btnBuscar) {
            tblEmpleados.getItems().clear();
            if (tfBuscar.getText().isBlank()) {
                cargarDatos();
            } else {
                tblEmpleados.getItems().add(buscarEmpleado());
                colId.setCellValueFactory(new PropertyValueFactory<Empleado, Long>("id"));
                colNombre.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombre"));
                colApellido.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellido"));
                colTelefono.setCellValueFactory(new PropertyValueFactory<Empleado, String>("telefono"));
                colDireccion.setCellValueFactory(new PropertyValueFactory<Empleado, String>("direccion"));
                colDpi.setCellValueFactory(new PropertyValueFactory<Empleado, String>("dpi"));
            }

        }
    }

    public void cargarDatos() {
        tblEmpleados.setItems(listarEmpleados());
        colId.setCellValueFactory(new PropertyValueFactory<Empleado, Long>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellido"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Empleado, String>("telefono"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Empleado, String>("direccion"));
        colDpi.setCellValueFactory(new PropertyValueFactory<Empleado, String>("dpi"));

    }

    public void cargarForm() {
        Empleado empleado = (Empleado) tblEmpleados.getSelectionModel().getSelectedItem();
        if (empleado != null) {
            tfId.setText(empleado.getId().toString());
            tfNombre.setText(empleado.getNombre());
            tfApellido.setText(empleado.getApellido());
            tfTelefono.setText(empleado.getTelefono());
            tfDireccion.setText(empleado.getDireccion());
            tfDpi.setText(empleado.getDpi());
        }
    }

    public void limpiarForm() {
        tfId.clear();
        tfNombre.clear();
        tfApellido.clear();
        tfTelefono.clear();
        tfDireccion.clear();
        tfDpi.clear();

    }

    public ObservableList<Empleado> listarEmpleados() {
        return FXCollections.observableList(empleadoService.listarEmpleados());
    }

    public void guardarEmpleado() {
        Empleado empleado = new Empleado();
        empleado.setNombre(tfNombre.getText());
        empleado.setApellido(tfApellido.getText());
        empleado.setTelefono(tfTelefono.getText());
        empleado.setDireccion(tfDireccion.getText());
        empleado.setDpi(tfDpi.getText());
        empleadoService.guardarEmpleado(empleado);
        cargarDatos();
    }

    public void editarEmpleado() {
        Empleado empleado = empleadoService.buscarEmpleadoPorId(Long.parseLong(tfId.getText()));
        empleado.setNombre(tfNombre.getText());
        empleado.setApellido(tfApellido.getText());
        empleado.setTelefono(tfTelefono.getText());
        empleado.setDireccion(tfDireccion.getText());
        empleado.setDpi(tfDpi.getText());
        empleadoService.guardarEmpleado(empleado);
        cargarDatos();
    }

    public void eliminarEmpleado() {
        Empleado empleado = empleadoService.buscarEmpleadoPorId(Long.parseLong(tfId.getText()));
        empleadoService.eliminarEmpleado(empleado);
        cargarDatos();
    }

    public Empleado buscarEmpleado() {
        return empleadoService.buscarEmpleadoPorId(Long.parseLong(tfBuscar.getText()));
    }
}
