package com.slylamb.pocketcuisine.Models;

import java.util.ArrayList;

public class User {
    private ArrayList<ShoppingList> shoppingLists;
    private ArrayList<Recipe> ownRecipes;
    private ArrayList<Recipe> cookedRecipes;
    private ArrayList<Recipe> favorites;
    private int theme;  // which theme did the user choose? Might change type
    private MealPlanner planner;

    public User() {
        shoppingLists = new ArrayList<>();
        ownRecipes = new ArrayList<>();
        cookedRecipes = new ArrayList<>();
        favorites = new ArrayList<>();
        // theme = DEFAULT;
        planner = new MealPlanner();
    }
    // Check if recipe passed on is in this users favorites list
   public boolean hasFavorite(Recipe recipe) {
        int total = favorites.size();
        // Go thru all favorites
        for (int i = 0; i < total; i++) {
            // If any matches the recipe passed on, return true, otherwise, false
            if (recipe.getTitle().equals(favorites.get(i).getTitle())) {
                return true;
            }
        }
        return false;
   }
   // Check if recipe passed on is in the cooked list
   public boolean hasCooked(Recipe recipe) {
       int total = cookedRecipes.size();
       // Go thru all favorites
       for (int i = 0; i < total; i++) {
           // If any matches the recipe passed on, return true, otherwise, false
           if (recipe.getTitle().equals(cookedRecipes.get(i).getTitle())) {
               return true;
           }
       }
       return false;
   }
   // Add recipe to user's favorites list
    public void addFavorite(Recipe recipe) {
        favorites.add(recipe);
    }
    // Add recipe to user's cooked recipes list
    public void addCooked(Recipe recipe) {
        cookedRecipes.add(recipe);
    }
    // Delete recipe from users list of favorites
    public void deleteFavorite(Recipe recipe) {
        favorites.remove(recipe);
    }
    // Delete recipe from users list of cooked recipes
    public void deleteCooked(Recipe recipe) {
        cookedRecipes.remove(recipe);
    }
    // Add planned meal to users meal planner
    public void addMealToMealPlanner(PlannedMeal meal) {
        planner.addMeal(meal);
    }
    // Add shopping list to users shopping lists
    public void addShoppingList(ShoppingList list) {
        shoppingLists.add(list);
    }
    // Getters
    public MealPlanner getPlanner() {
        return planner;
    }
    public ShoppingList getShoppingListAt(int i) {
        return shoppingLists.get(i);
    }
}