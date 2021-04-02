package org.example.model.jdbc;

public interface MealDao<Meal> extends Dao{
    void addEntryFromCSV(String name, String category, String price, String kcal);
    void addAll();
    int lastInsertRowId();
}
