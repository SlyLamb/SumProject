package com.slylamb.pocketcuisine.Models;

import java.util.ArrayList;

public class MealPlanner {
    private ArrayList<PlannedMeal> plannedMeals;

    public MealPlanner() {
        plannedMeals = new ArrayList<>();
    }
    // Add planned meal to list of planned meals
    public void addMeal(PlannedMeal meal) {
        plannedMeals.add(meal);
    }
    // Getters
    public ArrayList<PlannedMeal> getPlannedMeals() {
        return(plannedMeals);
    }
}