package com.slylamb.pocketcuisine.Models;

import android.text.format.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MealPlanner {
    private ArrayList<PlannedMeal> plannedMeals;
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    public MealPlanner() {
        plannedMeals = new ArrayList<>();
    }

    // Getters
    public ArrayList<PlannedMeal> getPlannedMeals() {
        return(plannedMeals);
    }
    public ArrayList<String> getIngredients(String dateFrom, String dateTo) {
        // Get Date object of dateFrom and dateTo strings
        Date from = null, to = null;
        try {
            from = formatter.parse(dateFrom);
            to = formatter.parse(dateTo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Initialise ingredients list
        ArrayList<String> ingredients = new ArrayList<>();
        // Go thru planned meals until you reach date from
        int i = 0;
        while (plannedMeals.get(i).getDate().before(from)) i++;
        // Then go thru all planned meals up to date to, saving their ingredients to list
        while (plannedMeals.get(i).getDate().before(to)) {
            ingredients.addAll(plannedMeals.get(i).getRecipe().getIngredients());
            i++;
        }
        return(ingredients);
    }

    // Add planned meal to list of planned meals
    public void addMeal(PlannedMeal meal) {
        // Delete planned meals which are from yesterday or older
        deleteGoneDates();
        // Find index to add meal to as must be in chronological order
        int i = 0;
        while (plannedMeals.get(i).getDate().before(meal.getDate())) i++;
        // After while loop, i has correct index, add meal to that index
        plannedMeals.add(i, meal);
    }

    // Returns true if there are any planned meals on date and false otherwise
    public boolean hasMealOnDate(String date) {
        for (int i = 0; i < plannedMeals.size(); i++) {
            // Extract date string from each planned meal
            String plannedDate = formatter.format(plannedMeals.get(i).getDate());
            // If current planned meal equals date, return true
            if (plannedDate.equals(date)) {
                return true;
            }
        }
        // If looked at all planned meals and haven't returned true, then return false
        return false;
    }

    // Delete all meals which have now been and gone
    private void deleteGoneDates() {
        // Get todays date
        Date today = new Date();
        // While the oldest planned meal is before today, keep deleting them
        while (plannedMeals.get(0).getDate().before(today)) {
            plannedMeals.remove(0);
        }
    }
}