package com.slylamb.pocketcuisine.Models;

import java.util.ArrayList;

public class Recipe {

    private String ID;
    private String title;
    private String imageLink;
    private String publisher;
    private String sourceURL;
    private ArrayList<Ingredient> ingredients;

    public Recipe(){}

    // Getters
    public String getTitle() {
        return title;
    }
    public String getImageLink() {
        return imageLink;
    }
    public String getPublisher() {
        return publisher;
    }
    public String getSourceURL() {
        return sourceURL;
    }
    public ArrayList<Ingredient> getIngredients() {
        return(ingredients);
    }
    // Setters
    public void setTitle(String title){
        this.title = title;
    }
    public void setImageLink(String imageLink){
        // Swapping http with https so image is found and displayed
        this.imageLink = imageLink.replace("http","https");

    }
    public void setPublisher(String publisher){
        this.publisher = publisher;
    }
    public void setSourceURL(String sourceURL) {
        // Swapping http with https so web page is found and displayed
         this.sourceURL = sourceURL.replace("http","https");
    }
    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = new ArrayList<>(ingredients);
    }

}