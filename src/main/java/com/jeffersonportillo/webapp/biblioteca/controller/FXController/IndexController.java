package com.jeffersonportillo.webapp.biblioteca.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.jeffersonportillo.webapp.biblioteca.system.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import lombok.Setter;

@Component
public class IndexController implements Initializable{
    @Setter
    private Main stage;
    @FXML
    MenuItem btnCategoria,btnClientes,btnLibros,btnEmpleados,btnPrestamos;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        
    }
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCategoria){
            stage.categoriaView();
        }else if(event.getSource() == btnClientes){
            stage.clienteView();
        }else if(event.getSource() == btnEmpleados){
            stage.empleadoView();
        }else if(event.getSource() == btnLibros){
            stage.libroView();
        }else if(event.getSource() == btnPrestamos){
            stage.prestamoView();
        }
    }
}