package com.slylamb.pocketcuisine.Models;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Recipe {
    private String title;
    private String publisher;
    private ArrayList<String> ingredients;
    private String imageLink; // might change type

    // GABRIEL METHODS
    public String getTitle() {
        return title;
    }
    public ArrayList<String> getIngredients() {
        return(ingredients);
    }
    public String getImageLink() {
        return imageLink;
    }
}