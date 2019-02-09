
package com.slylamb.pocketcuisine.Presenters;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.slylamb.pocketcuisine.Models.PlannedMeal;
import com.slylamb.pocketcuisine.Models.Recipe;
import com.slylamb.pocketcuisine.Models.ShoppingList;
import com.slylamb.pocketcuisine.Models.User;
import com.slylamb.pocketcuisine.Views.RecipeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class RecipeActivityPresenter {

    private View view;
    private Recipe recipe;
    private User user;
    private final String baseUrl = "https://www.food2fork.com/api/get?key=";
    private final String key = "f5b73a553a6a92ccfabca695807bdaeb"; //50 calls limit per day
    private final String recipeSearch = "&rId=";

    public RecipeActivityPresenter(View view, String recipeID) {
        this.view = view;
        // Set recipe's properties from API url
        //String url = baseUrl + key + recipeSearch + recipeID;
        //getRecipeFromAPI(url);

        recipe = new Recipe();
        recipe.setImageLink("http://static.food2fork.com/iW8v49knM5faff.jpg");
        recipe.setTitle("Chicken with Spring Vegetables and Gnocchi");
        recipe.setPublisher("Framed Cooks");
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("10 cups chicken broth");
        ingredients.add("1/2 stick butter");
        ingredients.add("6 tablespoons flour");
        ingredients.add("I large bulb of fennel, trim and sliced");
        ingredients.add("4 carrots, peeled and sliced");
        ingredients.add("1 leek, cut in half lengthwise and thinly sliced (white and light green part only)");
        ingredients.add("12 ounces fresh potato or ricotta gnocchi");
        ingredients.add("1/2 cup chopped fresh parsley, plus extra for garnish");
        ingredients.add("Coarse salt and fresh ground pepper");
        ingredients.add("Shaved parmesan for garnish");
        recipe.setIngredients(ingredients);
        recipe.setSourceURL("http://www.framedcooks.com/2012/05/chicken-with-spring-vegetables-and-gnocchi.html");

        user = new User();




        // Todo: Get recipe and user from api and/or database
    }

    // Set images and texts for current recipe in view
    public void setRecipeDetails() {
        // Set fields image, name, duration, servings and ingredients
        view.setRecipeDetails(recipe.getImageLink(), recipe.getTitle(), recipe.getSourceURL());
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
        ShoppingList shoppingList = new ShoppingList(recipe.getIngredients(), name);
        // Add list to user's shopping lists
        user.addShoppingList(shoppingList);
        // Todo: Update user database
    }

    // Initialise recipe and sets its values from API url
    public void getRecipeFromAPI(String url) {
        // Todo: not working
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
                    recipe.setSourceURL(recipeObj.getString("source_url"));
                    // For recipes, get JSONArray and convert into List of Strings
                    JSONArray ingredientsArray = recipeObj.getJSONArray("ingredients");
                    ArrayList<String> ingredients = new ArrayList<>();
                    for (int i = 0; i < ingredientsArray.length(); i++) {
                        ingredients.add(ingredientsArray.getString(i));
                    }
                    recipe.setIngredients(ingredients);
                } catch (JSONException e) {
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
        void setRecipeDetails(String imageLink, String name, String sourceURL);
        // Set button look, different if already picked
        void setButton(boolean picked, String button);
        // Open dialog for user to pick date before adding to meal planner
        void showMealPlannerDialog();
        // Open dialog for user to pick name before adding to shopping lists
        void showShoppingListDialog();
    }
}