package com.slylamb.pocketcuisine.Presenters;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.slylamb.pocketcuisine.Models.PlannedMeal;
import com.slylamb.pocketcuisine.Models.Recipe;
import com.slylamb.pocketcuisine.Models.ShoppingList;
import com.slylamb.pocketcuisine.Models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipeActivityPresenter {

    private View view;
    private Recipe recipe;
    private User user;
    private final String baseUrl = "https://www.food2fork.com/api/search?key=";
    private final String key = "f5b73a553a6a92ccfabca695807bdaeb"; //50 calls limit per day
    private final String recipeSearch = "&rId=";

    public RecipeActivityPresenter(View view, String recipeID) {
        this.view = view;
        // Set recipe's properties from API url
        String url = baseUrl + key + recipeSearch + recipeID;
        getRecipeFromAPI(url);

        // Todo: Get recipe and user from api and/or database
    }

    // Set images and texts for current recipe in view
    public void setRecipeDetails() {
        // Set fields image, name, duration, servings and ingredients
        view.setRecipeDetails(recipe.getImageLink(), recipe.getTitle(), recipe.getIngredients());
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

    //
    public void getRecipeFromAPI(String url) {
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject recipeObj = response.getJSONObject("recipe");
                    // Initialize recipe and set its variables
                    recipe = new Recipe();
                    recipe.setImageLink(recipeObj.getString("image_url"));
                    recipe.setTitle(recipeObj.getString("title"));
                    recipe.setPublisher(recipeObj.getString("publisher"));
                    // For recipes, get JSONArray and convert into List of Strings
                    JSONArray ingredientsArray = recipeObj.getJSONArray("ingredients");
                    ArrayList<String> ingredients = new ArrayList<>();
                    for (int i = 0; i < ingredientsArray.length(); i++) {
                        ingredients.add(ingredientsArray.getString(i));
                    }
                    recipe.setIngredients(ingredients);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    public interface View {
        // Set recipe details in the view
        void setRecipeDetails(String imageLink, String name, ArrayList<String> ingredients);
        // Set button look, different if already picked
        void setButton(boolean picked, String button);
        // Open dialog for user to pick date before adding to meal planner
        void showMealPlannerDialog();
        // Open dialog for user to pick name before adding to shopping lists
        void showShoppingListDialog();
    }
}