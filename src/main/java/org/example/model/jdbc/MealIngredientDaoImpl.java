package org.example.model.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MealIngredientDaoImpl implements MealIngredientDao{

    Connection conn = null;

    @Override
    public void add(int mealId, int ingredientId) {
        conn = DB.getInstance().getConn();
        String sql = "insert into meal_ingredient (mealId, ingredientId) values (?,?)";
        try (PreparedStatement insertStmt = conn.prepareStatement(sql)) {
            insertStmt.setInt(1, mealId);
            insertStmt.setInt(2, ingredientId);
            insertStmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
