package org.example.model.menu;

import java.util.ArrayList;
import java.util.HashMap;

public class IngredientList {
    private HashMap<Ingredient,ArrayList<String>>list;

    public IngredientList() {
        this.list = new HashMap<>();
    }

    public ArrayList<String>findMenuItemNames(Ingredient ing){
        return list.getOrDefault(ing,null);
    }

    public void addIngredient(Ingredient ing, String name){
        if(list.containsKey(ing)){
            if(!list.get(ing).contains(name))
                list.get(ing).add(name);
        }else {
            list.putIfAbsent(ing, new ArrayList<>());
            list.get(ing).add(name);
        }

    }

    public void print(){
        for(Ingredient ing: list.keySet()){
            System.out.println(ing + " " + list.get(ing));
        }
    }
}
