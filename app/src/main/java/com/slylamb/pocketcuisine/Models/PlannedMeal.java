package com.slylamb.pocketcuisine.Models;

import android.provider.CalendarContract;
import java.util.Date;

public class PlannedMeal {
    private Recipe recipe;
    private Date date;      // Date that the recipe is scheduled for

    // GABRIEL METHODS
    public PlannedMeal(Recipe recipe, String date) {
        this.recipe = recipe;
        // Todo: convert string with format dd-mm-yyyy into a Date type and assign
    }
}