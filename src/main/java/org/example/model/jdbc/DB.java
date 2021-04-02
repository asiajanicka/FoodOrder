package org.example.model.jdbc;


import org.example.model.menu.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class DB {

    private static DB db;

    static {
        try {
            db = new DB();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final static String dbUrl = "jdbc:sqlite:food.db";
    private Connection conn;

    public static DB getInstance() {
        return db;
    }

    public Connection getConn() {
        return conn;
    }

    private DB() throws SQLException, IOException {
        connect();
        createTableForMeals();
        createTableForIngredients();
        createTableMealIngredient();
        createTableForDrinks();
//        populateTableMealsFromCsv();
//        populateTableDrinksFromCsv();
        //batchUpdateFromCSV();
    }

    public void connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        conn = DriverManager.getConnection(dbUrl);
    }

    private void createTableForMeals() {
        try (Statement stmt = conn.createStatement()) {
            String sql = "create table if not exists meals (id integer primary key, name text not null, category text not null, price real, kcal integer)";
            stmt.execute(sql);
        } catch (SQLException ex) {
            System.out.println("Cannot create a table meals in database");
            ex.printStackTrace();
        }
    }

    private void createTableForDrinks(){
        String sql = "create table if not exists drinks (id integer primary key, name text not null, category text not null, temp text not null, alcoholicType numeric, price real, kcal integer)";
        try(Statement stmt = conn.createStatement() ){
            stmt.execute(sql);
        } catch (SQLException throwables) {
            System.out.println("Cannot create a table drinks in database");
            throwables.printStackTrace();
        }
    }

    private void createTableForIngredients() {
        try (Statement stmt = conn.createStatement()) {
            String sql = "create table if not exists ingredients (id integer primary key, name text not null)";
            stmt.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void createTableMealIngredient() {
        try (Statement stmt = conn.createStatement()) {
            String sql = "create table if not exists meal_ingredient ( matchId integer primary key, mealId integer not null, ingredientId integer not null, foreign key (mealID) references meals (id), foreign key (ingredientID) references ingredients(id))";
            stmt.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void addIngredientToIngredientsTable(String str) {
        String sql = "insert into ingredients (name) values (?)";
        try (PreparedStatement insertStmt = conn.prepareStatement(sql)) {
            insertStmt.setString(1, str);
            insertStmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void addMatchToMealIngredientTable(int mealIdValue, int ingredientIdValue) {
        String sql = "insert into meal_ingredient (mealId, ingredientId) values (?,?)";
        try (PreparedStatement insertStmt = conn.prepareStatement(sql)) {
            insertStmt.setInt(1, mealIdValue);
            insertStmt.setInt(2, ingredientIdValue);
            insertStmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void populateTableMealsFromCsv() throws IOException {
        ArrayList<String> tempListOfIngredints = new ArrayList<>();
        String sql = " insert into meals (name, category, price, kcal) values (?, ?, ?, ?)";
        PreparedStatement insertStmt = null;
        try {
            insertStmt = conn.prepareStatement(sql);
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader("/Users/adam/Documents/asia/Workspace/FoodOrder/Breakfast.csv"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                for (int i = 0; i < values.length; i++) {
                    values[i] = values[i].trim();
                }
                insertStmt.setString(1, values[0]);//name
                insertStmt.setString(2, values[1]);//category
                insertStmt.setDouble(3, Double.parseDouble(values[2]));//price
                insertStmt.setInt(4, Integer.parseInt(values[3])); //kcal
                insertStmt.executeUpdate();

                String sql1 = "select last_insert_rowid()";
                PreparedStatement stmtFindMeal = conn.prepareStatement(sql1);
                ResultSet rsFindMeal = stmtFindMeal.executeQuery();
                int intFindMeal = rsFindMeal.getInt(1);
                rsFindMeal.close();

                String[] temp = values[4].split(";");
                for (int i = 0; i < temp.length; i++) {
                    temp[i] = temp[i].trim().toLowerCase();
                    if (!tempListOfIngredints.contains(temp[i])) {
                        tempListOfIngredints.add(temp[i]);
                        addIngredientToIngredientsTable(temp[i]);
                    }
                    String sql2 = "select id from ingredients where name = ?";
                    PreparedStatement stmtFindIng = conn.prepareStatement(sql2);
                    stmtFindIng.setString(1, temp[i]);
                    ResultSet rsFindIng = stmtFindIng.executeQuery();
                    int intFindIng = rsFindIng.getInt(1);
                    rsFindIng.close();
                    addMatchToMealIngredientTable(intFindMeal, intFindIng);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void populateTableDrinksFromCsv(){
        String sql = " insert into drinks (name, category, temp, alcoholicType, price, kcal) values (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement insertStmt = conn.prepareStatement(sql);
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader("/Users/adam/Documents/asia/Workspace/FoodOrder/Drinks.csv"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                for (int i = 0; i < values.length; i++) {
                    values[i] = values[i].trim();
                }
                insertStmt.setString(1,values[0]);
                insertStmt.setString(2,values[1]);
                insertStmt.setString(3,values[2]);
                insertStmt.setInt(4,Integer.parseInt(values[3]));
                insertStmt.setDouble(5, Double.parseDouble(values[4]));
                insertStmt.setInt(6,Integer.parseInt(values[5]));
                insertStmt.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<MenuItem> getDrinks(){
        ArrayList<MenuItem> drinkList = new ArrayList<>();
        String sql = "select * from drinks";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                boolean temp = true;
                if(rs.getInt(4)==0){
                    temp = false;
                }
                Drink drink = new Drink.DrinkBuilder().name(rs.getString("name")).category(Category.valueOf(rs.getString("category"))).
                        hotCold(TempDrink.valueOf(rs.getString("temp"))).alcoholic(temp).price(rs.getDouble("price")).kcal(rs.getInt("kcal")).build();
                drinkList.add(drink);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return drinkList;
    }

    public Menu getMeals() throws IOException {
        Menu menu = new Menu();
        try (Statement stmt = conn.createStatement()) {
            String sql = "select id, name, category, price, kcal from meals";
            String sql1 = "select meal_ingredient.mealId, ingredients.name \n" +
                    "from meal_ingredient \n" +
                    "join ingredients on (meal_ingredient.ingredientId=ingredients.id)";


            Statement stmt1 = conn.createStatement();
            var rs_ing = stmt1.executeQuery(sql1);
            ArrayList<String[]> temp = new ArrayList<>();

            while(rs_ing.next()){
                String[] temp_str = new String[2];
                temp_str[0]= String.valueOf(rs_ing.getInt(1));
                temp_str[1]=rs_ing.getString(2);
                temp.add(temp_str);
            }
            stmt1.close();
            var rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int mealId = rs.getInt(1);
                ArrayList<Ingredient> ingList = new ArrayList<>();

                for(int i=0; i<temp.size();i++){
                  if(Integer.parseInt(temp.get(i)[0])== mealId){
                      ingList.add(new Ingredient(temp.get(i)[1]));
                  }
                }
                Meal meal = new Meal.MealBuilder().name(rs.getString("name")).category(Category.valueOf(rs.getString("category"))).price(rs.getDouble("price")).kcal(rs.getInt("kcal")).ingredients(ingList).build();
                menu.getMenu().add(meal);

                for(int i=0; i<ingList.size();i++){
                    menu.addIngredientToIngredientList(ingList.get(i), meal);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return menu;
    }

    private void batchUpdateFromCSV() {
        ArrayList<String> tempListOfIngredints = new ArrayList<>();
        try {
            conn.setAutoCommit(false);
            try {
                PreparedStatement stmt = conn.prepareStatement("insert into meals (id, name, category, price, kcal, ingredients) values (?, ?, ?, ?, ?, ?)");
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader("/Users/adam/Documents/asia/Workspace/FoodOrder/Breakfast.csv"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                String line = "";
                while (true) {
                    try {
                        if (!((line = br.readLine()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String[] values = line.split(",");

                    for (int i = 0; i < values.length; i++) {
                        values[i] = values[i].trim();
                    }
                    stmt.setString(2, values[0]);//name
                    stmt.setString(3, values[1]);//category
                    stmt.setDouble(4, Double.parseDouble(values[2]));//price
                    stmt.setInt(5, Integer.parseInt(values[3])); //kcal
                    stmt.setString(6, values[4]);
                    stmt.addBatch();

                    String[] temp = values[4].split(";");
                    for (int i = 0; i < temp.length; i++) {
                        temp[i] = temp[i].trim().toLowerCase();
                        if (!tempListOfIngredints.contains(temp[i])) {
                            tempListOfIngredints.add(temp[i]);
                            addIngredientToIngredientsTable(temp[i]);
                        }
                    }
                }
                stmt.executeBatch();
                conn.commit();
                conn.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void close() {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("drop table meals");
            stmt.execute("drop table drinks");
            stmt.execute("drop table ingredients");
            stmt.execute("drop table meal_ingredient");
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
