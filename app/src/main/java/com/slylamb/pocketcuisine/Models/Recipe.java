package com.slylamb.pocketcuisine.Models;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Recipe {
    private String title;
    private String publisher;
    private ArrayList<String> ingredients;
    private String imageLink; // might change type

    // GABRIEL METHODS
    // Getters
    public String getTitle() {
        return title;
    }
    public ArrayList<String> getIngredients() {
        return(ingredients);
    }
    public String getImageLink() {
        return imageLink;
    }
    // Setters
    public void setTitle(String title) {
        this.title = title;
    }
    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = new ArrayList<>(ingredients);
    }
    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}