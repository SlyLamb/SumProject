/*
package com.slylamb.pocketcuisine.Presenters;

import android.graphics.Bitmap;
import com.slylamb.pocketcuisine.Models.Ingredient;
import com.slylamb.pocketcuisine.Models.PlannedMeal;
import com.slylamb.pocketcuisine.Models.Recipe;
import com.slylamb.pocketcuisine.Models.ShoppingList;
import com.slylamb.pocketcuisine.Models.User;

import java.util.ArrayList;

public class RecipeActivityPresenter {

    private View view;
    private Recipe recipe;
    private User user;

    public RecipeActivityPresenter(View view) {
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
        // If user does not have current recipe in favorites yet, add it, otherwise, delete it
        if (!user.hasFavorite(recipe)) {
            user.addFavorite(recipe);
        } else {
            user.deleteFavorite(recipe);
        }
        // Then update the button in the view
        view.setButton(user.hasFavorite(recipe), "addFavorites");

        // Todo: Update user database

    }

    // Handle add cooked button being pressed
    public void addCooked() {
        // If user does not have current recipe in cooked recipes yet, add it, otherwise, delete it
        if (!user.hasCooked(recipe)) {
            user.addCooked(recipe);
        } else {
            user.deleteCooked(recipe);
        }
        // Then update the button in the view
        view.setButton(user.hasCooked(recipe), "addCooked");

        // Todo: Update user database

    }

    // Handle addMealPlanner dialog "Add" pressed
    public void addToMealPlanner(String date) {
        // Create planned meal from current recipe and date given in button
        PlannedMeal meal = new PlannedMeal(recipe, date);
        // Add meal to user's meal planner
        user.addMealToMealPlanner(meal);
        // Todo: Update user database
    }

    // Handle addShoppingList dialog "Add" pressed
    public void addMealToShoppingList(String name) {
        // Create Shopping List from recipe
        ShoppingList shoppingList = new ShoppingList(recipe, name);
        // Add list to user's shopping lists
        user.addShoppingList(shoppingList);
        // Todo: Update user database
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

*/