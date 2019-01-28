package com.slylamb.pocketcuisine.Models;

import java.util.ArrayList;

public class Recipe {

    private String title;
    private String imageLink;
    private String publisher;
    private ArrayList<String> ingredients;

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
    public ArrayList<String> getIngredients() {
        return(ingredients);
    }
    // Setters
    public void setTitle(String title){
        this.title = title;
    }
    public void setImageLink(String imageLink){
        this.imageLink = imageLink;

    }
    public void setPublisher(String publisher){
        this.publisher = publisher;
    }
    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = new ArrayList<>(ingredients);
    }

}