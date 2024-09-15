package com.jeffersonportillo.webapp.biblioteca.system;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.jeffersonportillo.webapp.biblioteca.BibliotecaApplication;
import com.jeffersonportillo.webapp.biblioteca.controller.FXController.CategoriaControllerFX;
import com.jeffersonportillo.webapp.biblioteca.controller.FXController.ClienteControllerFX;
import com.jeffersonportillo.webapp.biblioteca.controller.FXController.EmpleadoControllerFX;
import com.jeffersonportillo.webapp.biblioteca.controller.FXController.IndexController;
import com.jeffersonportillo.webapp.biblioteca.controller.FXController.LibroControllerFX;
import com.jeffersonportillo.webapp.biblioteca.controller.FXController.PrestamoControllerFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application{
    private ConfigurableApplicationContext applicationContext;
    private Stage stage;
    private Scene scene;

    @Override
    public void init(){
        this.applicationContext = new SpringApplicationBuilder(BibliotecaApplication.class).run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        stage.setTitle("Biblioteca");
        indexView();
        stage.show();
    }

    public Initializable switchScene(String fxmlName, int width, int height)throws IOException{
        Initializable resultado = null;
        FXMLLoader loader = new FXMLLoader();

        loader.setControllerFactory(applicationContext::getBean);
        InputStream archivo = Main.class.getResourceAsStream("/templates/"+fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource("/templates/"+fxmlName));
        
        scene = new Scene((AnchorPane)loader.load(archivo),width,height);
        stage.setScene(scene);
        stage.sizeToScene();

        resultado = (Initializable)loader.getController();
        return resultado;
    }

    public void indexView(){
        try {
            IndexController indexView = (IndexController)switchScene("index.fxml", 1235, 720);
            indexView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void categoriaView(){
        try{
            CategoriaControllerFX categoriaView = (CategoriaControllerFX)switchScene("categoria.fxml", 1280, 720);
            categoriaView.setStage(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void clienteView(){
        try{
            ClienteControllerFX clienteView = (ClienteControllerFX)switchScene("cliente.fxml", 1280, 720);
            clienteView.setStage(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void empleadoView(){
        try{
            EmpleadoControllerFX empleadoView = (EmpleadoControllerFX)switchScene("empleado.fxml", 1280, 720);
            empleadoView.setStage(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void libroView(){
        try {
            LibroControllerFX libroView = (LibroControllerFX)switchScene("libro.fxml", 1280, 720);
            libroView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prestamoView(){
        try {
            PrestamoControllerFX prestamoView = (PrestamoControllerFX)switchScene("prestamo.fxml", 1280, 720);
            prestamoView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}