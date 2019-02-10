
package com.slylamb.pocketcuisine.Presenters;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.slylamb.pocketcuisine.Data.DataBaseHandler;
import com.slylamb.pocketcuisine.Models.Ingredient;
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
    private DataBaseHandler db;
    private final String baseUrl = "https://www.food2fork.com/api/get?key=";
    private final String key = "f5b73a553a6a92ccfabca695807bdaeb"; //50 calls limit per day
    private final String recipeSearch = "&rId=";

    public RecipeActivityPresenter(View view, String recipeID, String type) {
        this.view = view;
        db = new DataBaseHandler(view.getContext());
        // If type is API, must get recipe from API
        if (type.equals("API")) {
            // Get api url
            String url = baseUrl + key + recipeSearch + recipeID;
            getRecipeFromAPI(url);
        // If type is DB, must get recipe from Database
        } else if (type.equals("DB")) {
            recipe = db.getRecipe(recipeID);
        }

        // TEST DATA
        recipe = new Recipe();
        recipe.setImageLink("http://static.food2fork.com/iW8v49knM5faff.jpg");
        recipe.setTitle("Chicken with Spring Vegetables and Gnocchi");
        recipe.setPublisher("Framed Cooks");
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        Ingredient ingredient = new Ingredient();
        ingredient.setItemName("10 cups chicken broth");
        ingredients.add(ingredient);
        ingredient.setItemName("1/2 stick butter");
        ingredients.add(ingredient);
        recipe.setIngredients(ingredients);
        recipe.setSourceURL("http://www.framedcooks.com/2012/05/chicken-with-spring-vegetables-and-gnocchi.html");
    }

    // Set images and texts for current recipe in view
    public void setRecipeDetails() {
        // Set fields image, name, duration, servings and ingredients
        view.setRecipeDetails(recipe.getImageLink(), recipe.getTitle(), recipe.getSourceURL());
        // Set favorite button, different look if user has recipe or doesn't
        view.setFavoriteButton(db.hasRecipe(recipe.getTitle()));
    }

    // Handle add favorite button being pressed
    public void addFavorites() {
        // If user does not have current recipe in favorites yet, add it, otherwise, delete it
        if (!db.hasRecipe(recipe.getTitle())) {
            db.addRecipe(recipe);
        } else {
            db.deleteRecipe(recipe.getTitle());
        }
        // Then update the button in the view
        view.setFavoriteButton(db.hasRecipe(recipe.getTitle()));

    }

    // Handle addMealPlanner dialog "Add" pressed
    public void addToMealPlanner(String date) {
        // Create planned meal from current recipe and date given in button
        PlannedMeal meal = new PlannedMeal(recipe, date);
        db.addPlannedMeal(meal);
    }

    // Add meal's ingredients to shopping list
    public void addMealToShoppingList() {
        db.addShoppingListFromRecipe(recipe);
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
                    ArrayList<Ingredient> ingredients = new ArrayList<>();
                    for (int i = 0; i < ingredientsArray.length(); i++) {
                        Ingredient ingredient = new Ingredient();
                        ingredient.setItemName(ingredientsArray.getString(i));
                        ingredients.add(ingredient);
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
        void setFavoriteButton(boolean picked);
        // Open dialog for user to pick date before adding to meal planner
        void showMealPlannerDialog();
        // Get context from activity
        Context getContext();
    }
}