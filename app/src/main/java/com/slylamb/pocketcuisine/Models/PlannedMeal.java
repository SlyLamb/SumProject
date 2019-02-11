package com.slylamb.pocketcuisine.Models;

import android.provider.CalendarContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlannedMeal {

    private String ID;
    private Recipe recipe;
    private Date date;      // Date that the recipe is scheduled for
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    public PlannedMeal() {
        recipe = new Recipe();
    }
    public PlannedMeal(Recipe recipe, String date) {
        this.recipe = recipe;
        try {
            this.date = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    // Getters
    public String getID() {
        return ID;
    }
    public Recipe getRecipe() {
        return recipe;
    }
    public Date getDate() {
        return date;
    }
    public String getDateString() {
        return formatter.format(this.date);
    }

    // Setters
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setDateFromString(String dateString) {
        try {
            this.date = formatter.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}