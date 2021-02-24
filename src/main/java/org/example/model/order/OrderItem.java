package org.example.model.order;

import org.example.model.menu.MenuItem;

import java.util.Objects;

public class OrderItem {
    private MenuItem menuItem;
    private int count;

    public OrderItem(MenuItem menuItem) {
        this.menuItem = menuItem;
        this.count = 1;
    }

   public String toString(){
        return menuItem.getName() + " x " + this.count + "   " + this.count* menuItem.getPrice() + " PLN";
   }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(menuItem, orderItem.menuItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuItem);
    }


}
