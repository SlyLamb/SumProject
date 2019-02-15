package com.slylamb.pocketcuisine.Models;

public class Ingredient {
    private Integer ID; // ID of the ingredient item in the database table
    private String itemName; // the name of the ingredient item

    public Ingredient() {
    }

    //getters
    public Integer getID() {
        return ID;
    }
    public String getItemName() {
        return itemName;
    }


    //setters
    public void setID(Integer ID) {
        this.ID = ID;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
