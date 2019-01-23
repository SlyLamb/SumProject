package com.slylamb.pocketcuisine.Models;

public class Ingredient extends IngredientItem {
    private String specification;

    // GABRIEL METHODS
    public String getSpecification() {
        return specification;
    }
    public boolean hasSpecification() {
        // check if specification has been initialised and has a value
        if (specification != null || specification != "") {
            return true;
        }
        return false;
    }
}