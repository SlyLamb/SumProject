package com.slylamb.pocketcuisine.Models;

import android.support.constraint.Constraints;
import android.text.format.DateUtils;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import static android.content.ContentValues.TAG;

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
    public ArrayList<Ingredient> getIngredients(String dateFrom, String dateTo) {
        // Get Date object of dateFrom and dateTo strings
        Date from = null, to = null;
        try {
            from = formatter.parse(dateFrom);
            to = formatter.parse(dateTo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Initialise ingredients list
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        // Go thru planned meals until you reach date from
        int count = plannedMeals.size();
        for (int i = 0; i < count; i++) {
            while (plannedMeals.get(i).getDate().before(from)) {
                if (i < count-1) i++;
                else break;
            }
            // Then go thru all planned meals up to date to, saving their ingredients to list
            if (i < count) {
                while (plannedMeals.get(i).getDate().before(to) || plannedMeals.get(i).getDate().equals(to)) {
                    ingredients.addAll(plannedMeals.get(i).getRecipe().getIngredients());
                    if (i < count-1) i++;
                    else break;
                }
            }

        }
        return(ingredients);
    }
    public ArrayList<PlannedMeal> getPlannedMealsOnDate(String dateString) {
        // Initialize list of meals and date
        ArrayList<PlannedMeal> meals = new ArrayList<>();
        Date date = new Date();
        // Get date from string with format
        try {
            date = formatter.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Go thru all planned meals before selected date
        int i = 0;
        while (plannedMeals.get(i).getDate().before(date)) {
            i++;
        }
        // Once we get to the current date, go thru all meals on this date
        while (i < plannedMeals.size()) {
            if (plannedMeals.get(i).getDateString().equals(dateString)) {
                meals.add(plannedMeals.get(i));
            }
            if (date.before(plannedMeals.get(i).getDate())) {
                break;
            }
            i++;
        }
        return meals;
    }

    // Setters
    public void setPlannedMeals(ArrayList<PlannedMeal> meals) {
        plannedMeals = new ArrayList<>(meals);
        // Sort planned meals by date and delete planned meals which are gone
        Collections.sort(plannedMeals, plannedMealDateComparator);
        deleteGoneDates();
    }


    // Add planned meal to list of planned meals
    public void addMeal(PlannedMeal meal) {
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

    //
    public void deletePlannedMeal(PlannedMeal meal) {
        plannedMeals.remove(meal);
    }

    // Delete all meals which have now been and gone
    private void deleteGoneDates() {
        // Get todays date
        Date today = Calendar.getInstance().getTime();
        // If there are planned meals
        if (plannedMeals.size() > 0) {
            // While the oldest planned meal is before today, keep deleting them
            while (plannedMeals.get(0).getDate().before(today)) {
                plannedMeals.remove(0);
            }
        }
    }

    public static Comparator<PlannedMeal> plannedMealDateComparator = new Comparator<PlannedMeal>() {

        @Override
        public int compare(PlannedMeal pm1, PlannedMeal pm2) {
            return (pm1.getDate().compareTo(pm2.getDate()));
            // Todo: test this
        }
    };
}