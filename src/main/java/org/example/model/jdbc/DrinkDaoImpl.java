package org.example.model.jdbc;

import org.example.model.menu.Category;
import org.example.model.menu.Drink;
import org.example.model.menu.MenuItem;
import org.example.model.menu.TempDrink;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DrinkDaoImpl implements DrinkDao {

    Connection conn = null;

    @Override
    public void save(Drink drink) {
    }

    @Override
    public Optional<Drink> findById(int id) {
        return Optional.empty();
    }

    @Override
    public void update(Drink drink) {
    }

    @Override
    public void delete(Drink drink) {
    }

    @Override
    public List<MenuItem> getAll() {
        ArrayList<MenuItem> drinkList = new ArrayList<>();
        String sql = "select * from drinks";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                boolean temp = true;
                if (rs.getInt(4) == 0) {
                    temp = false;
                }
                Drink drink = new Drink.DrinkBuilder().id(rs.getInt("id")).name(rs.getString("name")).category(Category.valueOf(rs.getString("category"))).
                        hotCold(TempDrink.valueOf(rs.getString("temp"))).alcoholic(temp).price(rs.getDouble("price")).kcal(rs.getInt("kcal")).build();
                drinkList.add(drink);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return drinkList;
    }

    @Override
    public void addEntryFromCSV(String name, String category, String temp, String alcoholicType, String price, String kcal) {
        conn = DB.getInstance().getConn();
        String sql = " insert into drinks (name, category, temp, alcoholicType, price, kcal) values (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement insertStmt = conn.prepareStatement(sql);
            insertStmt.setString(1, name);
            insertStmt.setString(2, category);
            insertStmt.setString(3, temp);
            insertStmt.setInt(4, Integer.parseInt(alcoholicType));
            insertStmt.setDouble(5, Double.parseDouble(price));
            insertStmt.setInt(6, Integer.parseInt(kcal));
            insertStmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void addAll() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("/Users/adam/Documents/asia/Workspace/FoodOrder/Drinks.csv"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                for (int i = 0; i < values.length; i++) {
                    values[i] = values[i].trim();
                }
                this.addEntryFromCSV(values[0], values[1], values[2], values[3], values[4], values[5]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
