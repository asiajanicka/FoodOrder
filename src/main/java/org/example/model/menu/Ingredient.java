package org.example.model.menu;

import java.util.ArrayList;
import java.util.Objects;

public class Ingredient {

    private final String ingredient;
    private int id;
    private ArrayList<MenuItem> listOfMeals;

    public Ingredient(String ingredient) {
        this.id =-1;
        this.ingredient = ingredient;
        listOfMeals = new ArrayList<>();
    }
    public Ingredient(int id, String ingredient) {
        this.id =id;
        this.ingredient = ingredient;
        listOfMeals = new ArrayList<>();
    }

    public String getIngredient() {
        return ingredient;
    }

    public void addMealToIngredient(Meal meal){
        if(!listOfMeals.contains(meal)){
            listOfMeals.add(meal);
        }
    }

    public ArrayList<MenuItem> getMealsForIngredient(){
        return listOfMeals;
    }

    @Override
    public String toString() {
        return this.ingredient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient)) return false;
        Ingredient that = (Ingredient) o;
        return getIngredient().equals(that.getIngredient());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIngredient(), listOfMeals);
    }
}
