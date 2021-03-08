package org.example.model.menu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Menu {
    private ArrayList<MenuItem> menu;
    private ArrayList<Ingredient> ing_list;

    public Menu() throws IOException {
        this.menu = new ArrayList<>();
        this.ing_list = new ArrayList<>();
    }

    public void init() throws IOException {
        loadDrinks();
    }

    public void addIngredientToIngredientList(Ingredient ing, Meal meal) {
        if (this.ing_list.contains(ing)) {
            for (Ingredient ing_temp : this.ing_list) {
                if (ing_temp.equals(ing)) {
                    ing_temp.addMealToIngredient(meal);
                }
            }
        } else {
            this.ing_list.add(ing);
            for (Ingredient ing_temp : this.ing_list) {
                if (ing_temp.equals(ing)) {
                    ing_temp.addMealToIngredient(meal);
                }
            }
        }
    }

    public void loadMealsFromCSV() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("/Users/adam/Documents/asia/Workspace/FoodOrder/Breakfast.csv"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");

            for (int i = 0; i < values.length; i++) {
                values[i] = values[i].trim();
            }
            String[] ingredinets_arr = values[4].split(";");
            for (int i = 0; i < ingredinets_arr.length; i++) {
                ingredinets_arr[i] = ingredinets_arr[i].trim();
            }
            Meal meal = new Meal.MealBuilder().name(values[0]).category(Category.valueOf(values[1])).price(Double.parseDouble(values[2])).kcal(Integer.parseInt(values[3])).ingredients(ingredinets_arr).build();
            this.menu.add(meal);
        }
    }

    public void loadDrinks() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("/Users/adam/Documents/asia/Workspace/FoodOrder/Drinks.csv"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            for (int i = 0; i < values.length; i++) {
                values[i] = values[i].trim();
            }
            Drink drink = new Drink.DrinkBuilder().name(values[0]).category(Category.valueOf(values[1])).hotCold(TempDrink.valueOf(values[2])).alcoholic(Boolean.valueOf(values[3])).price(Double.parseDouble(values[4])).kcal(Integer.parseInt(values[5])).build();
            menu.add(drink);
        }
    }

    public ArrayList<MenuItem> filterBasedOnCategory(Category category) {
        return menu.stream().filter(e -> e.getCategory().equals(category)).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<MenuItem> filterBasedOnIngredient(Ingredient ing) {
        ArrayList<MenuItem> arr = new ArrayList<>();
        for (Ingredient ing_temp : this.ing_list) {
            if (ing_temp.equals(ing))
                return ing_temp.getMealsForIngredient();
        }
        return arr;
    }

    public ArrayList<MenuItem> getMenu() {
        return menu;
    }
}
