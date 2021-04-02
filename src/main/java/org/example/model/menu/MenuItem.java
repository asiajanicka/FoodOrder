package org.example.model.menu;

public abstract class MenuItem {

    private int id;
    private String name;
    private double price;
    private int kcal;
    private Category category;

    public MenuItem(int id, String name, Category category, double price, int kcal) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.kcal = kcal;
        this.category = category;
    }
    public MenuItem(String name, Category category, double price, int kcal) {
        this.id = -1;
        this.name = name;
        this.price = price;
        this.kcal = kcal;
        this.category = category;
    }

    public MenuItem(){
        this.name = "Owsianka";
        this.category = Category.BREAKFAST;
        this.price =34.0;
        this.kcal = 123;
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

    public  String fullDescription(){
        return null;
    }

    @Override
    public String toString() {
        return
                "name= " + name + ", category= " + category +
                ", price= " + price +
                ", kcal= " + kcal;
    }
}
