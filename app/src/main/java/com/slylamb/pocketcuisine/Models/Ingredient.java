package com.slylamb.pocketcuisine.Models;

public class Ingredient {
    private Integer ID;
    private String itemName;

    public Ingredient() {
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
