package com.slylamb.pocketcuisine.Presenters;

import android.graphics.Bitmap;

import com.slylamb.pocketcuisine.Models.Ingredient;
import com.slylamb.pocketcuisine.Models.Recipe;
import com.slylamb.pocketcuisine.Models.User;

import java.util.ArrayList;

public class RecipePresenter {

    private View view;
    private Recipe recipe;
    private User user;

    public RecipePresenter(View view) {
        this.view = view;

        // Get recipe and user from database

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

        // Update user database

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

        // Update user database

    }

    // Handle add meal planner button being pressed
    public void addMealPlanner() {

        // Maybe use dialog to pick the date and choose if (b, l, d or s)
        // Doesn't require updating view as can add to meal planner more than once to different dates

        // Update user database

    }

    // Handle add shopping list button being pressed
    public void addShoppingList() {

        // Maybe use dialog to choose a shopping list name or ask to add to existing list?
        // Doesn't require updating view also

        // Update user database

    }

    public interface View {
        // Set recipe details in the view
        void setRecipeDetails(Bitmap image, String name, float duration, int servings, ArrayList<Ingredient> ingredients);
        // Set button look, different if already picked
        void setButton(boolean picked, String button);
    }
}