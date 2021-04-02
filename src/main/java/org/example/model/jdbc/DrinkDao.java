package org.example.model.jdbc;

import org.example.model.menu.Drink;

public interface DrinkDao extends Dao<Drink>{
    void addEntryFromCSV(String name, String category, String temp, String alcoholicType, String price, String kcal);
    void addAll();
}
