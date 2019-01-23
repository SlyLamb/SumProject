package com.slylamb.pocketcuisine.Models;

public class IngredientItem {
    private String name;
    private String measurement;
    private float quantity;
    private boolean selected;

    // GABRIEL METHODS
    public String getName() {
        return name;
    }
    public String getMeasurement() {
        return measurement;
    }
    public float getQuantity() {
        return quantity;
    }
    public boolean isSelected() {
        return selected;
    }
}