package com.slylamb.pocketcuisine.Models;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private ArrayList<Ingredient> ingredients;
    private int servings;   // how many people can eat from this recipe
    private float duration; // how long to cook meal
    private String imageLink;
    private String publisher;
    private String title;

    public Recipe(){}


    public String getImageLink() {
        return imageLink;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setImageLink(String imageLink){
        this.imageLink = imageLink;

    }

    public void setPublisher(String publisher){
        this.publisher = publisher;
    }

    public void setTitle(String title){
        this.title = title;
    }


}
