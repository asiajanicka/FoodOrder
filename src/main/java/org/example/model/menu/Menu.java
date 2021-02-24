package org.example.model.menu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Menu {
    private ArrayList<MenuItem> menu;
    private IngredientList ingList;

    public Menu() throws IOException {
        this.menu = new ArrayList<>();
        this.ingList = new IngredientList();
        loadMeals();
        loadDrinks();
    }

    public void loadMeals() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("/Users/adam/Documents/asia/Workspace/FoodOrder/Breakfast.csv"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");

            for(int i=0; i<values.length;i++){
                values[i]=values[i].trim();
            }
            String[] ingredinets_arr = values[4].split(";");
            for(int i=0; i<ingredinets_arr.length;i++){
                ingredinets_arr[i]=ingredinets_arr[i].trim();
                ingList.addIngredient(new Ingredient(ingredinets_arr[i]),values[0]);
            }
            Meal meal = new Meal.MealBuilder().name(values[0]).category(Category.valueOf(values[1])).price(Double.parseDouble(values[2])).kcal(Integer.parseInt(values[3])).ingredients(ingredinets_arr).build();
            this.menu.add(meal);
        }
    }

    public void loadDrinks() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("/Users/adam/Documents/asia/Workspace/FoodOrder/Drinks.csv"));
        String line;
        while((line=br.readLine()) != null){
            String[]values = line.split(",");
            for(int i=0; i<values.length;i++){
                values[i]=values[i].trim();
            }
            Drink drink = new Drink.DrinkBuilder().name(values[0]).category(Category.valueOf(values[1])).hotCold(TempDrink.valueOf(values[2])).alcoholic(Boolean.valueOf(values[3])).price(Double.parseDouble(values[4])).kcal(Integer.parseInt(values[5])).build();
            menu.add(drink);
        }
    }

    public ArrayList<MenuItem> filterBasedOnCategory(Category category){
        return menu.stream().filter(e->e.getCategory().equals(category)).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<MenuItem> filterBasedOnIngredient(Ingredient ing){
        ArrayList<String> names = ingList.findMenuItemNames(ing);
        ArrayList<MenuItem> menuItemsFiltered = new ArrayList<>();
        for(String str: names){
                    menuItemsFiltered.addAll(menu.stream().filter(e->e.getName().equals(str)).collect(Collectors.toCollection(ArrayList::new)));
        }
        return menuItemsFiltered;
    }

    public ArrayList<MenuItem> getMenu() {
        return menu;
    }
}
