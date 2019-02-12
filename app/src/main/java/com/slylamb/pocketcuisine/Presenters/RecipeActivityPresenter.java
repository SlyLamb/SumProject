
package com.slylamb.pocketcuisine.Presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class RecipeActivityPresenter {

    private View view;
    private Recipe recipe;
    private DataBaseHandler db;
    private RequestQueue queue;
    Context context;
    private final String baseUrl = "https://www.food2fork.com/api/get?key=";
    //private final String key = "f5b73a553a6a92ccfabca695807bdaeb"; //50 calls limit per day
    //private final String key = "f5b73a553a6a92ccfabca695807bdaeb";
    private final String key = "3092e7c11f93c302283e456ed92207e4";
    private final String recipeSearch = "&rId=";

    public RecipeActivityPresenter(View view, Context context, String recipeID, String type) {
        this.view = view;
        this.context = context;
        db = new DataBaseHandler(context);
        recipe = new Recipe();

        // DOWN THE BOTTOM
        //queue = Volley.newRequestQueue(context);

/*
        String url = baseUrl + key + recipeSearch + recipeID;
        Log.d("url",url);
        try {
            getRecipeFromAPI(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
*/

        // If type is API, must get recipe from API
        switch (type) {
            case "API":
                // Get api url
                Log.i("recipeIDapiPRE", recipeID);
                String url = baseUrl + key + recipeSearch + recipeID;
                try {
                    queue = Volley.newRequestQueue(context);
                    getRecipeFromAPI(url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // If type is DB, must get recipe from Database
                break;
            case "DB":
                Log.i("recipeIDdbPRE", recipeID);
                recipe = db.getRecipe(recipeID);
                // If type is PM, must get recipe from Database as a planned meal
                break;
            case "PM":
                PlannedMeal plannedMeal = db.getPlannedMeal(recipeID);
                recipe = new Recipe();
                recipe.setTitle(plannedMeal.getRecipe().getTitle());
                recipe.setImageLink(plannedMeal.getRecipe().getImageLink());
                recipe.setSourceURL(plannedMeal.getRecipe().getSourceURL());
                break;
        }
/*
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
        recipe.setSourceURL("http://www.framedcooks.com/2012/05/chicken-with-spring-vegetables-and-gnocchi.html");*/
    }

    // Set images and texts for current recipe in view
    public void setRecipeDetails() {
        // Set fields image, name, duration, servings and ingredients
        view.setRecipeDetails(recipe.getImageLink(), recipe.getTitle(), recipe.getSourceURL());
        // Set favorite button, different look if user has recipe or doesn't

        //THIS WILL CAUSE CRASH FOR NOW!!!!!
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
    private void getRecipeFromAPI(String url) throws IOException {

        Log.d("getRecipeFromAPI",url);
    // Todo: not working
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.i(TAG, "DEBUGGING - getRecipeFromAPI - inside Json request");
                    JSONObject recipeObj = response.getJSONObject("recipe");
                    // Initialize recipe and set its variables
                    recipe.setImageLink(recipeObj.getString("image_url"));
                    Log.d("recipeAPI",recipe.getImageLink());

                    recipe.setTitle(recipeObj.getString("title"));
                    Log.d("recipeAPI",recipe.getTitle());

                    recipe.setPublisher(recipeObj.getString("publisher"));
                    Log.d("recipeAPI",recipe.getPublisher());

                    recipe.setSourceURL(recipeObj.getString("source_url"));
                    Log.d("recipeAPI",recipe.getSourceURL());

                    // For recipes, get JSONArray and convert into List of Strings
                    JSONArray ingredientsArray = recipeObj.getJSONArray("ingredients");
                    ArrayList<Ingredient> ingredients = new ArrayList<>();
                    for (int i = 0; i < ingredientsArray.length(); i++) {
                        Ingredient ingredient = new Ingredient();
                        ingredient.setItemName(ingredientsArray.getString(i));
                        ingredients.add(ingredient);
                    }
                    recipe.setIngredients(ingredients);

                    setRecipeDetails();

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
        queue.add(request);



        /*
        // Connect to the URL using java's native library
        URL url = new URL(sURL);
        URLConnection request = url.openConnection();
        request.connect();

        // Convert to a JSON object to print data
        JsonParser jp = new JsonParser(); //from gson
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
        JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
        String zipcode = rootobj.get("zip_code").getAsString(); //just grab the zipcode





        Log.i(TAG, "DEBUGGING - API - inside getRecipeFromAPI, url = " + url);
        JSONObject recipeObj = new JSONObject();
        try {
            recipeObj = JsonReader.readJsonFromUrl(url);
            Log.i(TAG, "DEBUGGING - API - recipeObj = " + recipeObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        recipe = new Recipe();
        String imgLink = null, title = null, publisher = null, sourceURL = null;
        try {
            imgLink = recipeObj.getString("image_url");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            title = recipeObj.getString("title");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            publisher = recipeObj.getString("publisher");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            sourceURL = recipeObj.getString("source_url");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(TAG, "DEBUGGING - API - imgLink = " + imgLink + ", title = " + title + ", publisher = " + publisher + ", source = " + sourceURL);
        recipe.setImageLink(imgLink);
        recipe.setTitle(title);
        recipe.setPublisher(publisher);
        recipe.setSourceURL(sourceURL);
        // For recipes, get JSONArray and convert into List of Strings
        JSONArray ingredientsArray = new JSONArray();
        try {
            ingredientsArray = recipeObj.getJSONArray("ingredients");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientsArray.length(); i++) {
            String ingredientString = "";
            try {
                ingredientString = ingredientsArray.getString(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Ingredient ingredient = new Ingredient();
            ingredient.setItemName(ingredientString);
            ingredients.add(ingredient);
        }
        recipe.setIngredients(ingredients);*/
    }

    public interface View {
        // Set recipe details in the view
        void setRecipeDetails(String imageLink, String name, String sourceURL);
        // Set button look, different if already picked
        void setFavoriteButton(boolean picked);
        // Open dialog for user to pick date before adding to meal planner
        void showMealPlannerDialog();
    }
}