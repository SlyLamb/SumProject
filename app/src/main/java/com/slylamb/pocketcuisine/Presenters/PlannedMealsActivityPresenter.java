package com.slylamb.pocketcuisine.Presenters;

import android.content.Context;
import android.util.Log;

import com.slylamb.pocketcuisine.Data.DataBaseHandler;
import com.slylamb.pocketcuisine.Models.MealPlanner;
import com.slylamb.pocketcuisine.Models.PlannedMeal;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class PlannedMealsActivityPresenter {

    private MealPlanner planner;
    private ArrayList<PlannedMeal> plannedMealsOnDate;
    private DataBaseHandler db;

    public PlannedMealsActivityPresenter(Context context) {
        // Initialize database and get list of planned meals from it
        db = new DataBaseHandler(context);
        ArrayList<PlannedMeal> plannedMeals = db.getPlannedMeals();
        // Initialize planenr with planned meals from database
        planner = new MealPlanner();
        planner.setPlannedMeals(plannedMeals);
        plannedMealsOnDate = new ArrayList<>();
    }

    public ArrayList<String> getPlannedMealsTitles(String date) {
        Log.i(TAG, "DEBUGGING - planned meals presenter - get titles");
        // Save planned meals on date to presenter
        if (planner.hasMealOnDate(date)) {
            Log.i(TAG, "DEBUGGING - planned meals presenter - if succeded");
            plannedMealsOnDate = planner.getPlannedMealsOnDate(date);
        }
        // Go thru the saved meals and add their titles to string list
        ArrayList<String> mealsTitles = new ArrayList<>();
        for (int i = 0; i < plannedMealsOnDate.size(); i++) {
            mealsTitles.add(plannedMealsOnDate.get(i).getRecipe().getTitle());
            Log.i(TAG, "DEBUGGING - planned meals presenter - for - title[" + i + "] = " + plannedMealsOnDate.get(i).getRecipe().getTitle());
        }
        return mealsTitles;
    }

    public String getPlannedMealId(int i) {
        return plannedMealsOnDate.get(i).getID();
    }

    public void deletePlannedMeal(String title) {
        PlannedMeal meal;
        Log.i("plannedMealsTitle", title);
        for (int i = 0; i < plannedMealsOnDate.size(); i++) {
            Log.i("plannedMealLooking", "Title at i = " + i + " is " + plannedMealsOnDate.get(i).getRecipe().getTitle());
            if (plannedMealsOnDate.get(i).getRecipe().getTitle().equals(title)) {
                Log.i("plannedMealFound", "At i = " + i);
                // Delete meal from planner, plannedMealsOnDate and database
                meal = plannedMealsOnDate.get(i);
                planner.deletePlannedMeal(meal);
                plannedMealsOnDate.remove(i);
                db.deletePlannedMeal(meal.getRecipe().getTitle());
            }
        }
    }
}
