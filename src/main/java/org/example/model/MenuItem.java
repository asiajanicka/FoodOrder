package org.example.model;

public abstract class MenuItem {

    private String name;
    private double price;
    private int kcal;
    private Category category;

    public MenuItem(String name, Category category, double price, int kcal) {
        this.name = name;
        this.price = price;
        this.kcal = kcal;
        this.category = category;

    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getKcal() {
        return kcal;
    }

    public Category getCategory(){
        return category;
    }



    @Override
    public String toString() {
        return
                "name= " + name + ", category= " + category +
                ", price= " + price +
                ", kcal= " + kcal;
    }
}
