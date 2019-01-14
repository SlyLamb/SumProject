package com.slylamb.pocketcuisine.Models;

import java.util.ArrayList;

public class User {
    private ArrayList<ShoppingList> shoppingLists;
    private ArrayList<Recipe> ownRecipes;
    private ArrayList<Recipe> cookedRecipes;
    private ArrayList<Recipe> favorites;
    private int theme;  // which theme did the user choose? Might change type
}