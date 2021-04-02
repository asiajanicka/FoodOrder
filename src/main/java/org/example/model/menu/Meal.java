package org.example.model.menu;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class Meal extends MenuItem{

    private ArrayList<Ingredient>ingredients;
    private int id;

    public Meal(String name,Category category, ArrayList<Ingredient> ingredients, double price, int kcal) {
        super(name, category, price, kcal);
        this.ingredients = ingredients;
        this.id = -1;
    }

    public Meal(int id,String name,Category category, ArrayList<Ingredient> ingredients, double price, int kcal) {
        super(name, category, price, kcal);
        this.ingredients = ingredients;
        this.id = id;
    }


    public String getIngredients() {
        return ingredients.stream().map(b->b.toString()).collect(Collectors.joining(", "));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meal)) return false;
        Meal meal = (Meal) o;
        return Objects.equals(getIngredients(), meal.getIngredients()) && getCategory() == meal.getCategory();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIngredients(), getCategory());
    }

    @Override
    public String toString() {
        return  super.getName() + "     "+ super.getPrice();
    }

    public String fullDescription(){
        return super.getName() + "\n" + "price: " + super.getPrice() + " kcal: " + super.getKcal()+"\n"+ this.ingredients;
    }

        public static class MealBuilder {

        private int id;
        private String name;
        private double price;
        private int kcal;
        private ArrayList<Ingredient>ingredients;
        private Category category;

        public MealBuilder(){
        }

        public MealBuilder id(int id){
            this.id = id;
            return this;
        }

        public MealBuilder id(){
            this.id = -1;
            return this;
        }

        public MealBuilder name(String name){
            this.name = name;
            return this;
        }

        public MealBuilder price(double price){
            this.price = price;
            return this;
        }

        public MealBuilder kcal(int kcal){
            this.kcal =kcal ;
            return this;
        }

        public MealBuilder ingredients(ArrayList<Ingredient> ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public MealBuilder ingredients(String[] ingredients) {
            this.ingredients = new ArrayList<>();
            for(String ing: ingredients){
                this.ingredients.add(new Ingredient(ing));
            }
            return this;
        }

        public MealBuilder category(Category category) {
            this.category = category;
            return this;
        }

        public Meal build(){
            if(name.isEmpty())
                throw new IllegalArgumentException("Name must be non-empty value");
            if(price<=0)
                throw new IllegalArgumentException("Price must have positive value");
            if(kcal<0)
                throw new IllegalArgumentException("Kcal must be non-negative");
//            if(ingredients.size()<1)
//                throw new IllegalArgumentException("Meal should have at least one ingredient");
            if(this.id>0)
            return new Meal(this.id, this.name,this.category,this.ingredients,this.price,this.kcal);
            else return new Meal(this.name,this.category,this.ingredients,this.price,this.kcal);
        }
    }
}
