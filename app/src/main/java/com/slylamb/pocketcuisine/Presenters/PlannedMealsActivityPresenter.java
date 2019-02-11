package com.slylamb.pocketcuisine.Presenters;

import android.content.Context;

import com.slylamb.pocketcuisine.Data.DataBaseHandler;
import com.slylamb.pocketcuisine.Models.MealPlanner;
import com.slylamb.pocketcuisine.Models.PlannedMeal;

import java.util.ArrayList;

public class PlannedMealsActivityPresenter {

    private View view;
    private MealPlanner planner;
    private ArrayList<PlannedMeal> plannedMealsOnDate;
    private DataBaseHandler db;

    public PlannedMealsActivityPresenter() {
        // Initialize database and get list of planned meals from it
        db = new DataBaseHandler(view.getContext());
        ArrayList<PlannedMeal> plannedMeals = db.getPlannedMeals();
        // Initialize planenr with planned meals from database
        planner = new MealPlanner();
        planner.setPlannedMeals(plannedMeals);
        plannedMealsOnDate = new ArrayList<>();
    }

    public ArrayList<String> getPlannedMealsTitles(String date) {
        // Save planned meals on date to presenter
        if (planner.hasMealOnDate(date)) {
            plannedMealsOnDate = planner.getPlannedMealsOnDate(date);
        }
        // Go thru the saved meals and add their titles to string list
        ArrayList<String> mealsTitles = new ArrayList<>();
        for (int i = 0; i < plannedMealsOnDate.size(); i++) {
            mealsTitles.add(plannedMealsOnDate.get(i).getRecipe().getTitle());
        }
        return mealsTitles;
    }

    public String getPlannedMealId(int i) {
        return plannedMealsOnDate.get(i).getID();
    }

    public void deletePlannedMeal(int i) {
        // Delete meal from planner, plannedMealsOnDate and database
        PlannedMeal meal = plannedMealsOnDate.get(i);
        planner.deletePlannedMeal(meal);
        plannedMealsOnDate.remove(i);
        db.deletePlannedMeal(meal.getID());
    }

    public interface View {
        // Get activity context
        Context getContext();
    }
}
