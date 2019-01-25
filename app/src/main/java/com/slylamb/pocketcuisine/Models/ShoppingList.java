package com.slylamb.pocketcuisine.Models;

import java.util.ArrayList;

public class ShoppingList {
    private String name;
    private ArrayList<IngredientItem> items;

    // GABRIEL METHODS
    public ShoppingList(Recipe recipe, String name) {
        this.name = name;
        // Todo: convert recipe object into ingredients list (Thinking about using only ingredient names in list, not amount)
    }
}