package org.example.model.menu;

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
        return "bla";
    }


    @Override
    public String toString() {
        return
                "name= " + name + ", category= " + category +
                ", price= " + price +
                ", kcal= " + kcal;
    }
}
