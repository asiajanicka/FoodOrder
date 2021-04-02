package org.example.model.jdbc;

import org.example.model.menu.Category;
import org.example.model.menu.Ingredient;
import org.example.model.menu.Meal;
import org.example.model.menu.Menu;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MenuDaoImpl implements  MenuDao{
    Connection conn = null;
    @Override
    public Menu getMenu() throws IOException {
        Menu menu = new Menu();
        conn = DB.getInstance().getConn();
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
                Meal meal = new Meal.MealBuilder().id(rs.getInt("id")).name(rs.getString("name")).category(Category.valueOf(rs.getString("category"))).price(rs.getDouble("price")).kcal(rs.getInt("kcal")).ingredients(ingList).build();
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
}
