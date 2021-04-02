package org.example.model.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB2 {

    private static DB2 db2;

    static {
        try {
            db2 = new DB2();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final static String dbUrl = "jdbc:sqlite:test.db";
    private Connection conn;

    public static DB2 getInstance() {
        return db2;
    }


    private DB2() throws SQLException, IOException {
        connect();
        createTableForUsers();

    }

    public void connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        conn = DriverManager.getConnection(dbUrl);
    }

    private void createTableForUsers() {
        try (Statement stmt = conn.createStatement()) {
            String sql = "create table if not exists users (id integer primary key, name text not null, int age not null)";
            stmt.execute(sql);
        } catch (SQLException ex) {
            System.out.println("Cannot create a table meals in database");
            ex.printStackTrace();
        }
    }
}
