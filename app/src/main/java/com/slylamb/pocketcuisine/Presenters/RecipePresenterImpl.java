package com.slylamb.pocketcuisine.Presenters;

import com.slylamb.pocketcuisine.Models.Recipe;
import com.slylamb.pocketcuisine.Models.User;
import com.slylamb.pocketcuisine.Views.RecipeView;

public class RecipePresenterImpl implements RecipePresenter {

    private RecipeView view;
    private Recipe recipe;
    private User user;

    public RecipePresenterImpl(RecipeView view) {
        this.view = view;

        // Get recipe and user from database

    }

    @Override
    public void setRecipeDetails() {
        // Set fields image, name, duration, servings and ingredients
        view.setRecipeDetails(recipe.getImage(), recipe.getName(), recipe.getDuration(),
                recipe.getServings(), recipe.getIngredients());
        // Set buttons as they look different if already a favorite or cooked recipe
        view.setButton(user.hasFavorite(recipe), "addFavorites");
        view.setButton(user.hasCooked(recipe), "addCooked");
    }

    @Override
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

    @Override
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

    @Override
    public void addMealPlanner() {

        // Maybe use dialog to pick the date and choose if (b, l, d or s)
        // Doesn't require updating view as can add to meal planner more than once to different dates

        // Update user database

    }

    @Override
    public void addShoppingList() {

        // Maybe use dialog to choose a shopping list name or ask to add to existing list?
        // Doesn't require updating view also

        // Update user database

    }
}