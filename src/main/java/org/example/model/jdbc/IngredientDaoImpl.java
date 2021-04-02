package org.example.model.jdbc;

import org.example.model.menu.Ingredient;
import org.example.model.menu.MenuItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class IngredientDaoImpl implements IngredientDao{

    Connection conn = null;
    @Override
    public void save(Ingredient ingredient) {
    }

    @Override
    public Optional<Ingredient> findById(int id) {
        return Optional.empty();
    }

    @Override
    public void update(Ingredient ingredient) {
    }

    @Override
    public void delete(Ingredient ingredient) {
    }

    @Override
    public List<MenuItem> getAll() {
        return null;
    }

    public void add(String str){
        conn = DB.getInstance().getConn();
        String sql = "insert into ingredients (name) values (?)";
        try (PreparedStatement insertStmt = conn.prepareStatement(sql)) {
            insertStmt.setString(1, str);
            insertStmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int findByName(String name)  {
        conn = DB.getInstance().getConn();
        String sql2 = "select id from ingredients where name = ?";
        PreparedStatement stmtFindIng = null;
        int intFindIng = -1;
        try {
            stmtFindIng = conn.prepareStatement(sql2);
            stmtFindIng.setString(1, name);
            ResultSet rsFindIng = stmtFindIng.executeQuery();
            intFindIng = rsFindIng.getInt(1);
            rsFindIng.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return intFindIng;
    }
}
