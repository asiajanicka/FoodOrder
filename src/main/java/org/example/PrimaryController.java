package org.example;

/* What was missing
 * -> variables declared in Controller for fxid must have annotation @FXML
 * -> In addition in method initialize() menuViewList was initialized to new empty ListView, which was breaking the
 *    connection to menuViewList to ListView already initialized by loading fxml and binding to Controller in app.java:20
 * -> Apologies I had to close your browser tabs, but mac was about to commence a vertical launch.
 *    No worries it is saved as a session :)
 *
 * Happy coding Joanna ☆ :) :*
 */


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.example.model.jdbc.DB;
import org.example.model.menu.Category;
import org.example.model.menu.Ingredient;
import org.example.model.menu.Menu;
import org.example.model.menu.MenuItem;
import org.example.model.order.Order;
import org.example.model.order.OrderItem;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {
    @FXML
    public Label menuMealsLabel;
    @FXML
    public Label mealPreviewMenu;
    @FXML
    public TextField filterTextField;
    @FXML
    public ListView orderListView;
    @FXML
    public Button addMeal;
    public Label totalCost;
    public Label totalKcal;
    public Label orderMealPrview;
    @FXML
    private Button breakfastButton;
    @FXML
    private Button lunchButton;
    @FXML
    private Button supperButton;
    @FXML
    private Button drinksButton;
    @FXML
    private ListView<MenuItem> menuViewList;
    private Menu menu;
    private Order order;


    public PrimaryController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DB db = DB.getInstance();
            menu = new Menu();
            menu = db.getMeals();
            menu.getMenu().addAll(db.getDrinks());
           // menu.init();
            db.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        menuViewList.setItems(FXCollections.observableArrayList(menu.filterBasedOnCategory(Category.BREAKFAST)));
        if (!menuViewList.getItems().isEmpty()) {
            menuViewList.getSelectionModel().select(0);
            displaySelectedMenuItem();
        }
        order = new Order();
        orderListView.setItems(FXCollections.observableArrayList(order.getOrder()));
    }

    @FXML
    private void clickBreakfastButtonAction() {
        menuMealsLabel.setText("Breakfast:");
        menuViewList.setItems(FXCollections.observableArrayList(menu.filterBasedOnCategory(Category.BREAKFAST)));
        if (!menuViewList.getItems().isEmpty()) {
            menuViewList.getSelectionModel().select(0);
            displaySelectedMenuItem();
        }
    }
    @FXML
    private void clickLunchButtonAction() {
        menuMealsLabel.setText("Lunch:");
        menuViewList.setItems(FXCollections.observableArrayList(menu.filterBasedOnCategory(Category.LUNCH)));
        if (!menuViewList.getItems().isEmpty()) {
            menuViewList.getSelectionModel().select(0);
            displaySelectedMenuItem();
        }
    }
    @FXML
    private void clickSupperButtonAction() {
        menuMealsLabel.setText("Supper:");
        menuViewList.setItems(FXCollections.observableArrayList(menu.filterBasedOnCategory(Category.SUPPER)));
        if (!menuViewList.getItems().isEmpty()) {
            menuViewList.getSelectionModel().select(0);
            displaySelectedMenuItem();
        }
    }
    @FXML
    private void clickBDrinksButtonAction() {
        menuMealsLabel.setText("Drinks:");
        menuViewList.setItems(FXCollections.observableArrayList(menu.filterBasedOnCategory(Category.DRINK)));
        if (!menuViewList.getItems().isEmpty()) {
            menuViewList.getSelectionModel().select(0);
            displaySelectedMenuItem();
        }
    }

    @FXML
    private void clickFilterByIngredient(KeyEvent e){
        if(e.getCode().equals(KeyCode.ENTER)) {
            String str = "Filtered by \"" + filterTextField.getText() + "\"";
            menuMealsLabel.setText(str);
            menuViewList.setItems(FXCollections.observableArrayList(menu.filterBasedOnIngredient(new Ingredient(filterTextField.getText()))));
            if (!menuViewList.getItems().isEmpty()) {
                menuViewList.getSelectionModel().select(0);
                displaySelectedMenuItem();
            }
        }
    }

    @FXML
    private void displaySelectedMenuItem(){
        MenuItem item =  menuViewList.getSelectionModel().getSelectedItem();
       mealPreviewMenu.setText(item.fullDescription());
    }

    @FXML
    private void displaySelectedOrderItem(){
        OrderItem item = (OrderItem) orderListView.getSelectionModel().getSelectedItem();
        orderMealPrview.setText(item.getMenuItem().fullDescription());
    }

    @FXML
    private void addMealItemToOrder(){
        MenuItem item = menuViewList.getSelectionModel().getSelectedItem();
        order.addItem(item);
        orderListView.setItems(FXCollections.observableArrayList(order.getOrder()));
        totalKcal.setText("Total kcal: " + order.getTotalKcal());
        totalCost.setText("Total cost: " + order.getTotalCost());
        if (!orderListView.getItems().isEmpty()) {
            orderListView.getSelectionModel().select(0);
            displaySelectedOrderItem();
        }
    }

    @FXML
    private void removeMealFromOrder(){
        OrderItem item = (OrderItem) orderListView.getSelectionModel().selectedItemProperty().get();
        order.removeItem(item);
        orderListView.setItems(FXCollections.observableArrayList(order.getOrder()));
        totalKcal.setText("Total kcal: " + order.getTotalKcal());
        totalCost.setText("Total cost: " + order.getTotalCost());
        if (!orderListView.getItems().isEmpty()) {
            orderListView.getSelectionModel().select(0);
            displaySelectedOrderItem();
        } else orderMealPrview.setText("Meal preview");

    }
}
