package org.example.model.order;

import org.example.model.menu.MenuItem;

import java.util.ArrayList;
import java.util.Iterator;

public class Order {
    private ArrayList<OrderItem> order;
    private double totalCost;
    private int totalKcal;

    public Order(){
        this.order = new ArrayList<>();
        this.totalCost = 0;
        this.totalKcal = 0;
    }

    public ArrayList<OrderItem> getOrder() {
        return this.order;
    }

    public double getTotalCost() {
        double val = Math.round(totalCost*100);
        return val/100;
    }

    public int getTotalKcal() {
        return totalKcal;
    }

    public void addItem(MenuItem item){
       if(order.contains(new OrderItem(item))){
           for(OrderItem orderItem: order){
               if(orderItem.getMenuItem().equals(item)){
                   orderItem.setCount(orderItem.getCount()+1);
                   break;
               }
           }
       } else order.add(new OrderItem(item));
        totalCost+= item.getPrice();
        totalKcal+= item.getKcal();
    }

    public  void removeItem(OrderItem item){
        if(order.contains(item)){
            Iterator<OrderItem> iterator = order.iterator();
            while(iterator.hasNext()){
                if (iterator.next().equals(item)){
                  totalCost-= item.getMenuItem().getPrice()*item.getCount();
                    totalKcal= totalKcal - (item.getMenuItem().getKcal() * item.getCount());
                    iterator.remove();
                    break;
                }
            }
        }
    }

    public void print(){
        for(OrderItem item: order){
            System.out.println(item);
        }
        System.out.println("Total cost: " + this.totalCost);
        System.out.print("Total Kcal: " + this.totalKcal);
        System.out.println();
    }



}
