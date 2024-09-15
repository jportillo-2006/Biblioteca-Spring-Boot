package com.jeffersonportillo.webapp.biblioteca.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeffersonportillo.webapp.biblioteca.model.Cliente;
import com.jeffersonportillo.webapp.biblioteca.service.ClienteService;
import com.jeffersonportillo.webapp.biblioteca.system.Main;
import org.springframework.stereotype.Component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import lombok.Setter;

@Component
public class ClienteControllerFX implements Initializable {
    
    @FXML
    TextField tfDPI, tfNombre, tfApellido, tfTelefono, tfBuscar;
    @FXML
    Button btnGuardar, btnLimpiar, btnEliminar, btnBuscar, btnRegresar;
    @FXML
    TableView tblClientes;
    @FXML
    TableColumn colDPI, colNombre, colApellido, colTelefono;

    @Setter
    private Main stage;

    private Boolean edit = false;

    @Autowired
    ClienteService clienteService ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       cargarDatos();
    }

    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if (!edit) {
                agregarCliente();
            } else {
                editarCliente();
            }
        } else if (event.getSource() == btnLimpiar) {
            limpiarForm();
        } else if (event.getSource() == btnRegresar) {
            stage.indexView();
        } else if (event.getSource() == btnEliminar) {
            eliminarCliente();
        } else if (event.getSource() == btnBuscar) {
            tblClientes.getItems().clear();
            if (tfBuscar.getText().isBlank()) {
                cargarDatos();
            } else {
                tblClientes.getItems().add(buscarCliente());
                colDPI.setCellValueFactory(new PropertyValueFactory<Cliente, Long>("dpi"));
                colNombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));
                colApellido.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellido"));
                colTelefono.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefono"));
            }
        }
    }

    public void cargarDatos(){
        tblClientes.setItems(listarClientes());
        colDPI.setCellValueFactory(new PropertyValueFactory<Cliente, Long>("Dpi"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellido"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefono"));
    }

    public ObservableList<Cliente> listarClientes(){
        return FXCollections.observableList(clienteService.listarClientes());
    }

    public void limpiarForm(){
        tfDPI.clear();
        tfNombre.clear();
        tfApellido.clear();
        tfTelefono.clear();
        edit = false;
    }    

    public void agregarCliente(){
        Cliente cliente = new Cliente();
        cliente.setDpi(Long.parseLong(tfDPI.getText()));
        cliente.setNombre(tfNombre.getText());
        cliente.setApellido(tfApellido.getText());
        cliente.setTelefono(tfTelefono.getText());
        clienteService.guardarCliente(cliente);
        cargarDatos();
    }

    public void cargarFormEditar(){
        Cliente cliente = (Cliente)tblClientes.getSelectionModel().getSelectedItem();
        if(cliente != null){
            tfDPI.setText(Long.toString(cliente.getDpi()));
            tfNombre.setText(cliente.getNombre());
            tfApellido.setText(cliente.getApellido());
            tfTelefono.setText(cliente.getTelefono());
            edit = true;
        }
    }

    public void editarCliente(){
        Cliente cliente = clienteService.buscarClientePorId(Long.parseLong(tfDPI.getText()));
        cliente.setNombre(tfNombre.getText());
        cliente.setApellido(tfApellido.getText());
        cliente.setTelefono(tfTelefono.getText());
        clienteService.guardarCliente(cliente);
        cargarDatos();
    }

    public Cliente buscarCliente() {
        return clienteService.buscarClientePorId(Long.parseLong(tfBuscar.getText()));
    }

    public void eliminarCliente(){
        Cliente cliente = clienteService.buscarClientePorId(Long.parseLong(tfDPI.getText()));
        clienteService.eliminarCliente(cliente);
        cargarDatos();
    }

}
