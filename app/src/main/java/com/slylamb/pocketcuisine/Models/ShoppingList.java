package com.slylamb.pocketcuisine.Models;

import java.util.ArrayList;

public class ShoppingList {
    private String name;
    private ArrayList<String> items;

    public ShoppingList(ArrayList<String> items, String name) {
        this.items = items;
        this.name = name;
    }
    // Getters
    public String getName() {
        return name;
    }
}