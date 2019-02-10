package com.slylamb.pocketcuisine.Presenters;

import android.content.Context;

import com.slylamb.pocketcuisine.Data.DataBaseHandler;
import com.slylamb.pocketcuisine.Models.Ingredient;
import com.slylamb.pocketcuisine.Models.MealPlanner;
import com.slylamb.pocketcuisine.Models.PlannedMeal;
import com.slylamb.pocketcuisine.Models.ShoppingList;
import com.slylamb.pocketcuisine.Models.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MealPlannerActivityPresenter {

    private View view;
    private MealPlanner mealPlanner;
    private DataBaseHandler db;

    public MealPlannerActivityPresenter(View view) {
        this.view = view;
        db = new DataBaseHandler(view.getContext());
        ArrayList<PlannedMeal> plannedMeals = db.getPlannedMeals();
        mealPlanner = new MealPlanner();
        mealPlanner.setPlannedMeals(plannedMeals);
    }

    // Returns true if meal planner has a meal on date and false otherwise
    public boolean hasMealOnDate(String date) {
        return mealPlanner.hasMealOnDate(date);
    }

    // Returns list of strings with all dates which have meals
    public ArrayList<String> getDatesWithMeals() {
        // Get meals from meal planner and initialise list of strings
        ArrayList<PlannedMeal> meals = mealPlanner.getPlannedMeals();
        ArrayList<String> mealsDates = new ArrayList<>();
        for (int i = 0; i < meals.size(); i++) {
            // Extract date string from each planned meal and add to list of strings
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            mealsDates.add(formatter.format(meals.get(i).getDate()));
        }
        // Return list of strings with dates in format dd-MM-yyyy
        return(mealsDates);
    }

    // Add new shopping list for all planned meals between dateFrom and dateTo
    public void addIngredientsToList(String dateFrom, String dateTo) {
        ArrayList<Ingredient> ingredients = mealPlanner.getIngredients(dateFrom, dateTo);
        // Add ingredients to database
        db.addShoppingListFromIngredients(ingredients);
    }

    public interface View {
        // Set the dates which already have planned meals so they're differentiated
        void setDatesWithMeals();
        // Open dialog for user to pick name and date range for shopping list
        void showShoppingListDialog();
        // Validate date string so it matches format
        boolean validDate(String date);
        // Get views activity context
        Context getContext();
    }
}
