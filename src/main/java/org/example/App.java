package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.model.Category;
import org.example.model.Ingredient;
import org.example.model.Menu;
import org.example.model.MenuItem;

import java.io.IOException;
import java.util.ArrayList;

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

    public static void main(String[] args) throws IOException {
        Menu menu = new Menu();
        for(MenuItem item: menu.getMenu()){
            System.out.println(item);
        }
        System.out.println("  ");
        for(MenuItem item: menu.filterBasedOnCategory(Category.BREAKFAST)){
            System.out.println(item);
        }
        for(MenuItem item: menu.filterBasedOnIngredient(new Ingredient("flour"))){
            System.out.println(item);
        }
        //menu.filterBasedOnIngredient(new Ingredient("flour"));
        //listBasedOnIngredient = menu.filterBasedOnIngredient(new Ingredient("flour"));
        System.out.println();

        launch();
    }

}