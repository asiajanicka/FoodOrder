package org.example.model.jdbc;

import org.example.model.menu.Ingredient;

public interface IngredientDao extends  Dao<Ingredient>{
     void add(String str);
     int findByName(String name);
}
