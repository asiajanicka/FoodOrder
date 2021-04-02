package org.example.model.jdbc;

import org.example.model.menu.Category;
import org.example.model.menu.Ingredient;
import org.example.model.menu.Meal;
import org.example.model.menu.MenuItem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MealDaoImpl implements MealDao {

    Connection conn = null;

    public MealDaoImpl() {
    }

    @Override
    public void save(Object o) {
    }

    @Override
    public Optional findById(int id) {
        return Optional.empty();
    }

    @Override
    public void update(Object o) {
    }

    @Override
    public void delete(Object o) {
    }

    @Override
    public List getAll() {
        ArrayList<MenuItem> list = new ArrayList<>();
        conn = DB.getInstance().getConn();
        try (Statement stmt = conn.createStatement()) {
            String sql = "select id, name, category, price, kcal from meals";
            var rs = stmt.executeQuery(sql);
            while(rs.next()){
                Meal meal = new Meal.MealBuilder().id(rs.getInt("id")).name(rs.getString("name")).category(Category.valueOf(rs.getString("category"))).price(rs.getDouble("price")).kcal(rs.getInt("kcal")).ingredients(new ArrayList<Ingredient>()).build();
                list.add(meal);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    @Override
    public void addEntryFromCSV(String name, String category, String price, String kcal) {
        conn = DB.getInstance().getConn();
        String sql = " insert into meals (name, category, price, kcal) values (?, ?, ?, ?)";
        // PreparedStatement insertStmt = null;
        try {
            PreparedStatement insertStmt = conn.prepareStatement(sql);
            insertStmt.setString(1, name);//name
            insertStmt.setString(2, category);//category
            insertStmt.setDouble(3, Double.parseDouble(price));//price
            insertStmt.setInt(4, Integer.parseInt(kcal)); //kcal
            insertStmt.executeUpdate();
            // insertStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int lastInsertRowId() {
        String sql1 = "select last_insert_rowid()";
        PreparedStatement stmtFindMeal = null;
        try {
            conn = DB.getInstance().getConn();
            stmtFindMeal = conn.prepareStatement(sql1);
            ResultSet rsFindMeal = stmtFindMeal.executeQuery();
            int temp = rsFindMeal.getInt(1);
            rsFindMeal.close();
            return temp;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    @Override
    public void addAll() {
        IngredientDao ingredientDao = new IngredientDaoImpl();
        MealIngredientDao mealIngredientDao = new MealIngredientDaoImpl();
        conn = DB.getInstance().getConn();
        ArrayList<String> tempListOfIngredints = new ArrayList<>();
        try {
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
                this.addEntryFromCSV(values[0], values[1], values[2], values[3]);

                int intFindMeal = lastInsertRowId();

                String[] temp = values[4].split(";");
                for (int i = 0; i < temp.length; i++) {
                    temp[i] = temp[i].trim().toLowerCase();
                    if (!tempListOfIngredints.contains(temp[i])) {
                        tempListOfIngredints.add(temp[i]);
                        ingredientDao.add(temp[i]);
                    }
                     int intFindIng = ingredientDao.findByName(temp[i]);
                             mealIngredientDao.add(intFindMeal, intFindIng);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}