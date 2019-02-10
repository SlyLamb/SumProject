package com.slylamb.pocketcuisine.Models;

import android.provider.CalendarContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlannedMeal {

    private String ID;
    private Recipe recipe;
    private Date date;      // Date that the recipe is scheduled for

    public PlannedMeal() {
        recipe = new Recipe();
    }
    public PlannedMeal(Recipe recipe, String date) {
        this.recipe = recipe;
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            this.date = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    // Getters
    public Recipe getRecipe() {
        return recipe;
    }
    public Date getDate() {
        return date;
    }
}