package org.example.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Order {
    private LinkedHashMap<MenuItem,Integer>order;
    private double totalCost;
    private int totalKcal;

    public Order(){
        this.order = new LinkedHashMap<>();
        this.totalCost = 0;
        this.totalKcal = 0;
    }

    public LinkedHashMap<MenuItem,Integer> getOrder() {
        return order;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public int getTotalKcal() {
        return totalKcal;
    }

    public void addItem(MenuItem item){
        if(order.containsKey(item)) {
            order.put(item,order.get(item)+1);
        }else order.put(item,1);
        totalCost+= item.getPrice();
        totalKcal+= item.getKcal();
    }

    public  void removeItem(MenuItem item){
        if(order.containsKey(item)) {
            int amount = order.get(item);
            order.remove(item);
            totalCost-=item.getPrice()*amount;
            totalKcal-=item.getKcal()*amount;
        }
    }

}
