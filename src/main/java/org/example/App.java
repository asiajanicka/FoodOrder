package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import org.example.model.jdbc.DB;
import org.example.model.menu.Category;
import org.example.model.menu.Ingredient;
import org.example.model.menu.Menu;
import org.example.model.menu.MenuItem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;

import java.io.IOException;
import java.sql.SQLException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("primary.fxml"));
        stage = fxmlLoader.load();
        stage.show();
    }

    public static void main(String[] args)  {

       launch();
    }

}