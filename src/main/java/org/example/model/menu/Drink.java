package org.example.model.menu;

import java.util.Objects;

public class Drink extends MenuItem {
    private final TempDrink hotCold;
    private final boolean alcoholic;

    public Drink(String name, Category category, TempDrink hotColdType, boolean alcoholicType, double price, int kcal) {
        super(name, category, price, kcal);
        this.hotCold = hotColdType;
        this.alcoholic = alcoholicType;
    }

    public TempDrink isHotCold() {
        return hotCold;
    }

    public boolean isAlcoholic() {
        return alcoholic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Drink)) return false;
        Drink drink = (Drink) o;
        return hotCold == drink.hotCold && alcoholic == drink.alcoholic;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hotCold, alcoholic);
    }

    @Override
    public String toString() {
        return super.getName()+"        "+super.getPrice();
    }

    @Override
    public String fullDescription() {
        return super.getName() + "\n" + "price: " + super.getPrice() + " kcal: " + super.getKcal();
    }

    public static class DrinkBuilder {
        private String name;
        private Category category;
        private TempDrink hotCold;
        private boolean alcoholic;
        private double price;
        private int kcal;

        DrinkBuilder() {

        }

        public DrinkBuilder name(String name) {
            this.name = name;
            return this;
        }

        public DrinkBuilder category(Category category) {
            this.category = category;
            return this;
        }

        public DrinkBuilder hotCold(TempDrink value) {
            this.hotCold = value;
            return this;
        }

        public DrinkBuilder alcoholic(Boolean value) {
            this.alcoholic = value;
            return this;
        }

        public DrinkBuilder price(double price) {
            this.price = price;
            return this;
        }

        public DrinkBuilder kcal(int kcal) {
            this.kcal = kcal;
            return this;
        }

        public Drink build() {
            if (name.isEmpty())
                throw new IllegalArgumentException("Name must be non-empty value");
            if (price <= 0)
                throw new IllegalArgumentException("Price must have positive value");
            if (kcal < 0)
                throw new IllegalArgumentException("Kcal must be non-negative");
            return new Drink(this.name, this.category, this.hotCold, this.alcoholic, this.price, this.kcal);
        }
    }
}
