package com.slylamb.pocketcuisine.Presenters;

import android.content.Context;

import com.slylamb.pocketcuisine.Data.DataBaseHandler;
import com.slylamb.pocketcuisine.Models.MealPlanner;
import com.slylamb.pocketcuisine.Models.PlannedMeal;

import java.util.ArrayList;

public class PlannedMealsActivityPresenter {

    private View view;
    private MealPlanner planner;
    private DataBaseHandler db;

    public PlannedMealsActivityPresenter() {
        // Initialize database and get list of planned meals from it
        db = new DataBaseHandler(view.getContext());
        ArrayList<PlannedMeal> plannedMeals = db.getPlannedMeals();
        // Initialize planenr with planned meals from database
        planner = new MealPlanner();
        planner.setPlannedMeals(plannedMeals);
    }

    public interface View {
        // Get activity context
        Context getContext();
    }
}
