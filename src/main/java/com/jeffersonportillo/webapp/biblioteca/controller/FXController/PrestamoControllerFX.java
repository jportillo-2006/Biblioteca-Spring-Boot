package com.jeffersonportillo.webapp.biblioteca.controller.FXController;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeffersonportillo.webapp.biblioteca.model.Cliente;
import com.jeffersonportillo.webapp.biblioteca.model.Empleado;
import com.jeffersonportillo.webapp.biblioteca.model.Libro;
import com.jeffersonportillo.webapp.biblioteca.model.Prestamo;
import com.jeffersonportillo.webapp.biblioteca.service.ClienteService;
import com.jeffersonportillo.webapp.biblioteca.service.EmpleadoService;
import com.jeffersonportillo.webapp.biblioteca.service.LibroService;
import com.jeffersonportillo.webapp.biblioteca.service.PrestamoService;
import com.jeffersonportillo.webapp.biblioteca.system.Main;
import com.jeffersonportillo.webapp.biblioteca.utils.MethodType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Setter;

@Component
public class PrestamoControllerFX implements Initializable {

    @FXML
    TextField tfId, tfPrestamo, tfDevolucion, tfVigencia, tfBuscar;
    @FXML
    Button btnGuardar, btnLimpiar, btnRegresar, btnEliminar, btnBuscar, btnPrestar;
    @FXML
    ComboBox cmbLibro, cmbLibroII, cmbLibroIII, cmbEmpleado, cmbCliente;
    @FXML
    TableView tblPrestamos;
    @FXML
    TableColumn colId, colPrestamo, colDevolucion, colVigencia, colEmpleado, colCliente, colLibro, colPres;

    private List<Libro> librosSeleccionados = new ArrayList<>();
    private int estado = 0;

    @Setter
    private Main stage;

    @Autowired
    PrestamoService prestamoService;

    @Autowired
    ClienteService clienteService;

    @Autowired
    EmpleadoService empleadoService;

    @Autowired
    LibroService libroService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbLibro.setItems(FXCollections.observableList(libroService.listarLibros()));
        cmbLibroII.setItems(FXCollections.observableList(libroService.listarLibros()));
        cmbLibroIII.setItems(FXCollections.observableList(libroService.listarLibros()));
        cmbEmpleado.setItems(FXCollections.observableList(empleadoService.listarEmpleados()));
        cmbCliente.setItems(FXCollections.observableList(clienteService.listarClientes()));
        cargarDatos();
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnGuardar) {
            if (tfId.getText().isBlank()) {
                agregarPrestamo();
            } else {
                eliminarPrestamo();
            }
        } else if (event.getSource() == btnLimpiar) {
            limpiarForm();
        } else if (event.getSource() == btnRegresar) {
            stage.indexView();
        } else if (event.getSource() == btnEliminar) {
            eliminarPrestamo();
        } else if (event.getSource() == btnBuscar) {
            tblPrestamos.getItems().clear();
            if (tfBuscar.getText().isBlank()) {
                cargarDatos();
            } else {
                tblPrestamos.getItems().add(buscarPrestamo());
                colId.setCellValueFactory(new PropertyValueFactory<Prestamo, Long>("id"));
                colPrestamo.setCellValueFactory(new PropertyValueFactory<Prestamo, Date>("fechaDePrestamo"));
                colDevolucion.setCellValueFactory(new PropertyValueFactory<Prestamo, Date>("fechaDeDevolucion"));
                colVigencia.setCellValueFactory(new PropertyValueFactory<Prestamo, Boolean>("vigencia"));
                colEmpleado.setCellValueFactory(new PropertyValueFactory<Prestamo, Empleado>("empleado"));
                colCliente.setCellValueFactory(new PropertyValueFactory<Prestamo, Cliente>("cliente"));
            }

        }else if (event.getSource() == btnPrestar) {
            if (estado == 0) {
                cmbLibroII.setDisable(false);
                estado = 1;
            } else if (estado == 1) {
                cmbLibroIII.setDisable(false);
                estado = 2;
            }
        }
    }

    public void cargarDatos() {
        tblPrestamos.setItems(listarPrestamos());
        colId.setCellValueFactory(new PropertyValueFactory<Prestamo, Long>("id"));
        colPrestamo.setCellValueFactory(new PropertyValueFactory<Prestamo, Date>("fechaDePrestamo"));
        colDevolucion.setCellValueFactory(new PropertyValueFactory<Prestamo, Date>("fechaDeDevolucion"));
        colVigencia.setCellValueFactory(new PropertyValueFactory<Prestamo, Boolean>("vigencia"));
        colEmpleado.setCellValueFactory(new PropertyValueFactory<Prestamo, Empleado>("empleado"));
        colCliente.setCellValueFactory(new PropertyValueFactory<Prestamo, Cliente>("cliente"));
    }

    public ObservableList<Prestamo> listarPrestamos() {
        return FXCollections.observableList(prestamoService.listarPrestamos());
    }

    public void cargarForm() {
        Prestamo prestamo = (Prestamo) tblPrestamos.getSelectionModel().getSelectedItem();
        if (prestamo != null) {
            tfId.setText(prestamo.getId().toString());
            tfPrestamo.setText(prestamo.getFechaDePrestamo().toString());
            tfDevolucion.setText(prestamo.getFechaDeDevolucion().toString());
            tfVigencia.setText(prestamo.getVigencia().toString());
            cmbEmpleado.getSelectionModel().select(obtenerIndexEmpleado());
            cmbCliente.getSelectionModel().select(obtenerIndexCliente());
            int libros = prestamo.getLibros().size();
            if (libros == 1) {
                cmbLibro.getSelectionModel().select(obtenerIndexLibro());
                cmbLibroII.setDisable(true);
                cmbLibroIII.setDisable(true);
            } else if (libros == 2) {
                cmbLibroII.setDisable(false);
                cmbLibroII.getSelectionModel().select(obtenerIndexLibro2());
                cmbLibroIII.setDisable(true);
            } else {
                cmbLibroIII.setDisable(false);
                cmbLibroIII.getSelectionModel().select(obtenerIndexLibro3());
            }
        }
    }

    public void limpiarForm() {
        tfId.clear();
        tfPrestamo.clear();
        tfDevolucion.clear();
        tfVigencia.clear();
        cmbCliente.getSelectionModel().clearSelection();
        cmbEmpleado.getSelectionModel().clearSelection();
        cmbLibro.getSelectionModel().clearSelection();
        cmbLibro.getSelectionModel().clearSelection();
        cmbLibroII.getSelectionModel().clearSelection();
        cmbLibroIII.getSelectionModel().clearSelection();
        cmbLibroII.setDisable(true);
        cmbLibroIII.setDisable(true);
    }

    public void agregarPrestamo() {
        try {
            Prestamo prestamo = new Prestamo();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date fechaPrestamo = formatter.parse(tfPrestamo.getText());
            java.util.Date fechaDevolucion = formatter.parse(tfDevolucion.getText());
            prestamo.setFechaDePrestamo(new java.sql.Date(fechaPrestamo.getTime()));
            prestamo.setFechaDeDevolucion(new java.sql.Date(fechaDevolucion.getTime()));
            prestamo.setVigencia(Boolean.valueOf(tfVigencia.getText()));
            prestamo.setCliente((Cliente)cmbCliente.getSelectionModel().getSelectedItem());
            prestamo.setEmpleado((Empleado)cmbEmpleado.getSelectionModel().getSelectedItem());
            prestamo.setLibros(librosSeleccionados);
            cmbLibroII.setDisable(true);
            cmbLibroIII.setDisable(true);
            prestamoService.guardarPrestamo(prestamo, MethodType.POST);
        cargarDatos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editarPrestamo() {
        try {
            Prestamo prestamo = prestamoService.buscarPrestamoPorId(Long.parseLong(tfId.getText()));
            createSelectBook();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date fechaPrestamo = formatter.parse(tfPrestamo.getText());
            java.util.Date fechaDevolucion = formatter.parse(tfDevolucion.getText());
            prestamo.setFechaDePrestamo(new java.sql.Date(fechaPrestamo.getTime()));
            prestamo.setFechaDeDevolucion(new java.sql.Date(fechaDevolucion.getTime()));
            prestamo.setVigencia(Boolean.valueOf(tfVigencia.getText()));
            prestamo.setCliente((Cliente)cmbCliente.getSelectionModel().getSelectedItem());
            prestamo.setEmpleado((Empleado)cmbEmpleado.getSelectionModel().getSelectedItem());
            prestamo.setLibros(librosSeleccionados);
            prestamoService.guardarPrestamo(prestamo, MethodType.PUT);
            cargarDatos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarPrestamo() {
        Prestamo prestamo = prestamoService.buscarPrestamoPorId(Long.parseLong(tfId.getText()));
        prestamoService.eliminarPrestamo(prestamo);
        cargarDatos();
    }

    public Prestamo buscarPrestamo() {
        return prestamoService.buscarPrestamoPorId(Long.parseLong(tfBuscar.getText()));
    }

    public void createSelectBook() {
        librosSeleccionados.clear();
        if (cmbLibro.getValue() != null) {
            librosSeleccionados.add((Libro) cmbLibro.getValue());
            System.out.println(librosSeleccionados);
        }
        if (cmbLibroII.getValue() != null) {
            librosSeleccionados.add((Libro) cmbLibroII.getValue());
            System.out.println(librosSeleccionados);
        }
        if (cmbLibroIII.getValue() != null) {
            librosSeleccionados.add((Libro) cmbLibroIII.getValue());
            System.out.println(librosSeleccionados);
        }
    }

    public int obtenerIndexEmpleado() {
        int index = 0;
        for (int i = 0; i < cmbEmpleado.getItems().size(); i++) {
            String empleadoCmb = cmbEmpleado.getItems().get(i).toString();
            String empleadoTbl = ((Prestamo) tblPrestamos.getSelectionModel().getSelectedItem()).getEmpleado().toString();
            System.out.println(empleadoCmb);
            System.out.println(empleadoTbl);
            if (empleadoCmb.equals(empleadoTbl)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public int obtenerIndexCliente() {
        int index = 0;
        for (int i = 0; i < cmbCliente.getItems().size(); i++) {
            String clienteCmb = cmbCliente.getItems().get(i).toString();
            String clienteTbl = ((Prestamo) tblPrestamos.getSelectionModel().getSelectedItem()).getCliente().toString();
            System.out.println(clienteCmb);
            System.out.println(clienteTbl);
            if (clienteCmb.equals(clienteTbl)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public int obtenerIndexLibro() {
        Prestamo prestamo = (Prestamo) tblPrestamos.getSelectionModel().getSelectedItem();
        if (prestamo == null) {
            return -1;
        }

        Libro libroSeleccionado = prestamo.getLibros().get(0);
        for (int i = 0; i < cmbLibro.getItems().size(); i++) {
            String libroCmb = cmbLibro.getItems().get(i).toString();
            String libroTbl = libroSeleccionado.toString();
            if (libroCmb.equals(libroTbl)) {
                return i;
            }
        }
        return -1;
    }

    public int obtenerIndexLibro2() {
        Prestamo prestamo = (Prestamo) tblPrestamos.getSelectionModel().getSelectedItem();
        if (prestamo == null || prestamo.getLibros().size() < 2) {
            return -1;
        }

        Libro libroSeleccionado = prestamo.getLibros().get(1);
        for (int i = 0; i < cmbLibroII.getItems().size(); i++) {
            String libroCmb = cmbLibroII.getItems().get(i).toString();
            String libroTbl = libroSeleccionado.toString();
            if (libroCmb.equals(libroTbl)) {
                return i;
            }
        }
        return -1;
    }

    public int obtenerIndexLibro3() {
        Prestamo prestamo = (Prestamo) tblPrestamos.getSelectionModel().getSelectedItem();
        if (prestamo == null || prestamo.getLibros().size() < 3) {
            return -1;
        }
        Libro libroSeleccionado = prestamo.getLibros().get(2);
        for (int i = 0; i < cmbLibroIII.getItems().size(); i++) {
            String libroCmb = cmbLibroIII.getItems().get(i).toString();
            String libroTbl = libroSeleccionado.toString();
            if (libroCmb.equals(libroTbl)) {
                return i;
            }
        }
        return -1;
    }
}