package com.slylamb.pocketcuisine.Presenters;

public interface RecipePresenter {

    // Set images and texts for current recipe in view
    void setRecipeDetails();
    // Handle add favorite button being pressed
    void addFavorites();
    // Handle add cooked button being pressed
    void addCooked();
    // Handle add meal planner button being pressed
    void addMealPlanner();
    // Handle add shopping list button being pressed
    void addShoppingList();

}
