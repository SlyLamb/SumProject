package com.slylamb.pocketcuisine.Presenters;

import android.graphics.Bitmap;
import com.slylamb.pocketcuisine.Models.Ingredient;
import com.slylamb.pocketcuisine.Models.MealPlanner;
import com.slylamb.pocketcuisine.Models.PlannedMeal;
import com.slylamb.pocketcuisine.Models.Recipe;
import com.slylamb.pocketcuisine.Models.User;

import java.util.ArrayList;

public class RecipePresenter {

    private View view;
    private Recipe recipe;
    private User user;

    public RecipePresenter(View view) {
        this.view = view;

        // Todo: Get recipe and user from api and/or database

    }

    // Set images and texts for current recipe in view
    public void setRecipeDetails() {
        // Set fields image, name, duration, servings and ingredients
        view.setRecipeDetails(recipe.getImage(), recipe.getName(), recipe.getDuration(),
                recipe.getServings(), recipe.getIngredients());
        // Set buttons as they look different if already a favorite or cooked recipe
        view.setButton(user.hasFavorite(recipe), "addFavorites");
        view.setButton(user.hasCooked(recipe), "addCooked");
    }

    // Handle add favorite button being pressed
    public void addFavorites() {

        if (!user.hasFavorite(recipe)) {
            user.addFavorite(recipe);
            view.setButton(true, "addFavorites");
        } else {
            user.deleteFavorite(recipe);
            view.setButton(false, "addFavorites");
        }

        // Todo: Update user database

    }

    // Handle add cooked button being pressed
    public void addCooked() {

        if (!user.hasCooked(recipe)) {
            user.addCooked(recipe);
            view.setButton(true, "addCooked");
        } else {
            user.deleteCooked(recipe);
            view.setButton(false, "addCooked");
        }

        // Todo: Update user database

    }

    // Handle add meal planner button being pressed
    public void addMealPlanner() {
        // Show meal planner dialog so user can pick date and confirm
        view.showMealPlannerDialog();
    }

    // Handle addMealPlanner dialog "Add" pressed
    public void addMealToMealPlanner(String date) {
        // Create planned meal from current recipe and date given in button
        PlannedMeal meal = new PlannedMeal(recipe, date);
        // Add meal to user's meal planner
        user.addMealToMealPlanner(meal);
        // Todo: Update user database
    }

    // Handle add shopping list button being pressed
    public void addShoppingList() {
        // Show shopping list dialog so user can set name
        view.showShoppingListDialog();
    }

    public interface View {
        // Set recipe details in the view
        void setRecipeDetails(Bitmap image, String name, float duration, int servings, ArrayList<Ingredient> ingredients);
        // Set button look, different if already picked
        void setButton(boolean picked, String button);
        // Open dialog for user to pick date before adding to meal planner
        void showMealPlannerDialog();
        // Open dialog for user to pick name before adding to shopping lists
        void showShoppingListDialog();
    }
}