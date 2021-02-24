package org.example.model.menu;

import java.util.Objects;

public class Ingredient {

    private final String ingredient;

    public Ingredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getIngredient() {
        return ingredient;
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
        return Objects.hash(getIngredient());
    }
}
