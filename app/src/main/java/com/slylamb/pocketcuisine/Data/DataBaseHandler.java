package com.slylamb.pocketcuisine.Data;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.slylamb.pocketcuisine.Models.PlannedMeal;
import com.slylamb.pocketcuisine.Models.Recipe;
import com.slylamb.pocketcuisine.Models.Ingredient;


import com.slylamb.pocketcuisine.Util.Constants;


import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DataBaseHandler extends SQLiteOpenHelper {
    private Context context;
    public DataBaseHandler(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("dbhandler","onCreate has been called");

        String CREATE_SHOPPINGLIST_TABLE ="CREATE TABLE " + Constants.TABLE_SHOPPINGLIST_NAME + "("
                + Constants.KEY_STRING_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Constants.KEY_ITEM_STRING + " TEXT"
                 + " );";

//        String CREATE_PLANNEDMEAL_TABLE="CREATE TABLE " + Constants.TABLE_PLANNED_MEAL + "("
//                + Constants.KEY_PLANNEDMEAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Constants.KEY_PLANNEDMEAL_NAME + " TEXT,"
//                + Constants.KEY_PLANNEDMEAL_URL + " TEXT," + Constants.KEY_PLANNEDMEAL_IMAGELINK + " TEXT"+ " );";

        db.execSQL(CREATE_SHOPPINGLIST_TABLE);
       // db.execSQL(CREATE_PLANNEDMEAL_TABLE);

        //--------------------------------------------------------------------
        // Gabi code start
        //--------------------------------------------------------------------

        String CREATE_FAVORITE_RECIPE_TABLE = "CREATE TABLE " + Constants.TABLE_FAVORITE_RECIPE + "("
                + Constants.KEY_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constants.KEY_RECIPE_TITLE + " TEXT," + Constants.KEY_RECIPE_IMAGE + " TEXT,"
                + Constants.KEY_RECIPE_PUBLISHER + " TEXT," + Constants.KEY_RECIPE_SOURCE + " TEXT );";
        db.execSQL(CREATE_FAVORITE_RECIPE_TABLE);

        String CREATE_PLANNEDMEAL_TABLE = "CREATE TABLE " + Constants.TABLE_PLANNED_MEAL + "("
                + Constants.KEY_PLANNEDMEAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constants.KEY_PLANNEDMEAL_TITLE + " TEXT," + Constants.KEY_PLANNEDMEAL_IMAGE + " TEXT,"
                + Constants.KEY_PLANNEDMEAL_PUBLISHER + " TEXT," + Constants.KEY_PLANNEDMEAL_SOURCE + " TEXT,"
                + Constants.KEY_PLANNEDMEAL_DATE + " TEXT );";
        db.execSQL(CREATE_PLANNEDMEAL_TABLE);

        String CREATE_INGREDIENT_TABLE = "CREATE TABLE " + Constants.TABLE_INGREDIENT + "("
                + Constants.KEY_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constants.KEY_INGREDIENT_TEXT + " TEXT," + Constants.KEY_INGREDIENT_RECIPE + " TEXT,"
                + Constants.KEY_INGREDIENT_PLANNEDMEAL + " TEXT );";
        db.execSQL(CREATE_INGREDIENT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_SHOPPINGLIST_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_FAVORITE_RECIPE);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_PLANNED_MEAL);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_INGREDIENT);
        onCreate(db);
    }


    public void addShoppingListFromUserInput(Ingredient ingredient){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Constants.KEY_ITEM_STRING,ingredient.getItemName());
        Log.d("userinput",ingredient.getItemName());

        db.insert(Constants.TABLE_SHOPPINGLIST_NAME, null, values);

        Log.d("save","item saved into database");

    }

    public void addShoppingListFromRecipe(Recipe recipe) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_SHOPPINGLIST_NAME);


        String CREATE_SHOPPINGLIST_TABLE = "CREATE TABLE " + Constants.TABLE_SHOPPINGLIST_NAME + "("
                + Constants.KEY_STRING_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Constants.KEY_ITEM_STRING + " TEXT"
                + " );";

        db.execSQL(CREATE_SHOPPINGLIST_TABLE);


        List<String> ingredientItems = new ArrayList<>();

        List<String> newingredientItems = new ArrayList<>();

        // hardcoded example, will remove later
        ingredientItems.add("2 jalapeno peppers, cut in half lengthwise and seeded");
        ingredientItems.add("2 slices sour dough bread");
        ingredientItems.add("1 tablespoon butter, room temperature");
        ingredientItems.add("2 tablespoons cream cheese, room temperature");
        ingredientItems.add("1/2 cup jack and cheddar cheese, shredded");
        ingredientItems.add("1 tablespoon tortilla chips, crumbled\n");

        for (int i = 0; i < ingredientItems.size(); i++) {
            String[] splitted = ingredientItems.get(i).split(",");
            newingredientItems.add(splitted[0]);
        }

        //ArrayList<String> ingredients = new ArrayList<>(recipe.getIngredients());

        ContentValues values = new ContentValues();

        for (int i = 0; i < newingredientItems.size(); i++) {

            values.put(Constants.KEY_ITEM_STRING, newingredientItems.get(i));
            Log.d("database ingredient", newingredientItems.get(i));
            db.insert(Constants.TABLE_SHOPPINGLIST_NAME, null, values);
            Log.d("dbhandler", "insert has been called");

        }
    }

    public List<Ingredient> getALLStringItemsFromShoppingListTB() {
            SQLiteDatabase db = this.getReadableDatabase();

            List<Ingredient> ingredientList = new ArrayList<>();

            Cursor cursor = db.query(Constants.TABLE_SHOPPINGLIST_NAME, new String[] {
                    Constants.KEY_STRING_ITEM_ID,Constants.KEY_ITEM_STRING}, null, null, null, null, null );


            if (cursor.moveToFirst()) {
                do {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_STRING_ITEM_ID))));
                    Log.d("setID",cursor.getString(cursor.getColumnIndex(Constants.KEY_STRING_ITEM_ID)));
                    Log.d("getID",Integer.toString(ingredient.getID()));
                    ingredient.setItemName(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM_STRING)));
                    Log.d("database",ingredient.getItemName());

                    ingredientList.add(ingredient);

                }while (cursor.moveToNext());
            }

            return ingredientList;
    }

    public void deleteStringItemFromShoppingListTB(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_SHOPPINGLIST_NAME, Constants.KEY_STRING_ITEM_ID + " = ?",
                new String[] {String.valueOf(id)});

        db.close();
    }


    //----------------------------------------------------------------------------------------
    // GABRIEL CODE STARTS
    //----------------------------------------------------------------------------------------
    // Get recipe at keyId
    public Recipe getRecipe(String keyId) {
        // Get readable database
        SQLiteDatabase db = this.getReadableDatabase();
        // Get cursor for recipe at keyId
        Cursor cursor = db.query(Constants.TABLE_FAVORITE_RECIPE, new String[] {
                Constants.KEY_RECIPE_ID, Constants.KEY_RECIPE_TITLE, Constants.KEY_RECIPE_IMAGE,
                Constants.KEY_RECIPE_PUBLISHER, Constants.KEY_RECIPE_SOURCE}, Constants.KEY_RECIPE_ID + " = " + keyId,
                null, null, null, null);
        // Create recipe object from database and return it
        Recipe recipe = new Recipe();
        recipe.setTitle(cursor.getString(cursor.getColumnIndex(Constants.KEY_RECIPE_TITLE)));
        recipe.setImageLink(cursor.getString(cursor.getColumnIndex(Constants.KEY_RECIPE_IMAGE)));
        recipe.setPublisher(cursor.getString(cursor.getColumnIndex(Constants.KEY_RECIPE_PUBLISHER)));
        recipe.setSourceURL(cursor.getString(cursor.getColumnIndex(Constants.KEY_RECIPE_SOURCE)));
        // Get recipe id so ingredients can be found
        String recipeId = cursor.getString(cursor.getColumnIndex(Constants.KEY_RECIPE_ID));
        // New query for cursor, now getting ingredients that belong to this recipe
        cursor = db.query(Constants.TABLE_INGREDIENT, new String[] {Constants.KEY_INGREDIENT_TEXT,
                Constants.KEY_INGREDIENT_RECIPE}, Constants.KEY_INGREDIENT_RECIPE + " = " + recipeId,
                null, null, null, null);
        // Initialise list of ingredients and go thru all ingredients
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                // Create recipe from planned meal info
                Ingredient ingredient = new Ingredient();
                ingredient.setItemName(cursor.getString(cursor.getColumnIndex(Constants.KEY_INGREDIENT_TEXT)));
                ingredients.add(ingredient);
            } while (cursor.moveToNext());
        }
        cursor.close();
        recipe.setIngredients(ingredients);
        return recipe;
    }
    // True if recipe in database, false otherwise
    public boolean hasRecipe(String title) {
        // Get readable database
        SQLiteDatabase db = this.getReadableDatabase();
        // Get cursor for recipe with title that matches
        Cursor cursor = db.query(Constants.TABLE_FAVORITE_RECIPE, new String[] {Constants.KEY_RECIPE_TITLE},
                Constants.KEY_RECIPE_TITLE + " = " + title,null, null, null, null );
        // If cursor can't move to first and its count is 0, no recipe found that matches selection criteria
        if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
    // Add recipe to database
    public void addRecipe(Recipe recipe) {
        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();
        // Set content values with recipe details
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_RECIPE_TITLE, recipe.getTitle());
        values.put(Constants.KEY_RECIPE_IMAGE, recipe.getImageLink());
        values.put(Constants.KEY_RECIPE_PUBLISHER, recipe.getPublisher());
        values.put(Constants.KEY_RECIPE_SOURCE, recipe.getSourceURL());
        // Insert recipe values to database and get id
        long recipeID = db.insert(Constants.TABLE_FAVORITE_RECIPE, null, values);
        // Get ingredients from recipe
        ArrayList<Ingredient> ingredients = recipe.getIngredients();
        // Go thru all ingredients, add their text and recipe id
        for (int i = 0; i < ingredients.size(); i++) {
            ContentValues ingredientValues = new ContentValues();
            ingredientValues.put(Constants.KEY_INGREDIENT_TEXT, ingredients.get(i).getItemName());
            ingredientValues.put(Constants.KEY_INGREDIENT_RECIPE, Long.toString(recipeID));
            db.insert(Constants.TABLE_INGREDIENT, null, ingredientValues);
        }
    }
    // Delete recipe from database
    public void deleteRecipe(String title) {
        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete recipe which matches title and keep id
        long recipeID = db.delete(Constants.TABLE_FAVORITE_RECIPE, Constants.KEY_RECIPE_TITLE + " = " + title, null);
        // Delete ingredients from this recipe
        db.delete(Constants.TABLE_INGREDIENT, Constants.KEY_INGREDIENT_RECIPE + " = " + Long.toString(recipeID), null);
    }

    // Get all planned meals
    public ArrayList<PlannedMeal> getPlannedMeals() {
        // Get readable database
        SQLiteDatabase db = this.getReadableDatabase();
        // Get cursor for planned meals
        Cursor cursor = db.query(Constants.TABLE_PLANNED_MEAL, new String[]{
                 Constants.KEY_PLANNEDMEAL_ID, Constants.KEY_PLANNEDMEAL_TITLE, Constants.KEY_PLANNEDMEAL_IMAGE,
                 Constants.KEY_PLANNEDMEAL_PUBLISHER, Constants.KEY_PLANNEDMEAL_SOURCE, Constants.KEY_PLANNEDMEAL_DATE},
                null, null, null, null, null);
        // Initialize planned meals list
        ArrayList<PlannedMeal> meals = new ArrayList<>();
        // Go through all planned meals in cursor
        if (cursor.moveToFirst()) {
            do {
                // Create recipe from planned meal info
                Recipe recipe = new Recipe();
                recipe.setTitle(cursor.getString(cursor.getColumnIndex(Constants.KEY_PLANNEDMEAL_TITLE)));
                recipe.setImageLink(cursor.getString(cursor.getColumnIndex(Constants.KEY_PLANNEDMEAL_IMAGE)));
                recipe.setPublisher(cursor.getString(cursor.getColumnIndex(Constants.KEY_PLANNEDMEAL_PUBLISHER)));
                recipe.setSourceURL(cursor.getString(cursor.getColumnIndex(Constants.KEY_PLANNEDMEAL_SOURCE)));
                // Get cursor for ingredients from this planned meal
                Cursor ingredientCursor = db.query(Constants.TABLE_INGREDIENT, new String[] {
                        Constants.KEY_INGREDIENT_TEXT, Constants.KEY_INGREDIENT_PLANNEDMEAL},
                        Constants.KEY_INGREDIENT_PLANNEDMEAL + " = " + cursor.getString(cursor.getColumnIndex(Constants.KEY_PLANNEDMEAL_ID)),
                        null, null, null, null);
                // Initialise list of ingredients and go thru all ingredients
                ArrayList<Ingredient> ingredients = new ArrayList<>();
                if (ingredientCursor.moveToFirst()) {
                    do {
                        // Create ingredient from ingredient text in dtabase
                        Ingredient ingredient = new Ingredient();
                        ingredient.setItemName(ingredientCursor.getString(ingredientCursor.getColumnIndex(Constants.KEY_INGREDIENT_TEXT)));
                        ingredients.add(ingredient);
                    } while (ingredientCursor.moveToNext());
                }
                ingredientCursor.close();
                recipe.setIngredients(ingredients);
                // Then create planned meal with recipe and date and add it to list
                PlannedMeal meal = new PlannedMeal();
                meal.setRecipe(recipe);
                meal.setDateFromString(cursor.getString(cursor.getColumnIndex(Constants.KEY_PLANNEDMEAL_DATE)));
                meals.add(meal);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return meals;
    }
    // Get planned meal with keyId
    public PlannedMeal getPlannedMeal(String keyId) {
        // Get readable database
        SQLiteDatabase db = this.getReadableDatabase();
        // Get cursor for planned meal at keyId
        Cursor cursor = db.query(Constants.TABLE_PLANNED_MEAL, new String[] {
                Constants.KEY_PLANNEDMEAL_ID, Constants.KEY_PLANNEDMEAL_TITLE, Constants.KEY_PLANNEDMEAL_IMAGE,
                Constants.KEY_PLANNEDMEAL_PUBLISHER, Constants.KEY_PLANNEDMEAL_SOURCE, Constants.KEY_PLANNEDMEAL_DATE},
                Constants.KEY_PLANNEDMEAL_ID + " = " + keyId,
                null, null, null, null);
        // Create recipe object from planned meal info
        Recipe recipe = new Recipe();
        recipe.setTitle(cursor.getString(cursor.getColumnIndex(Constants.KEY_PLANNEDMEAL_TITLE)));
        recipe.setImageLink(cursor.getString(cursor.getColumnIndex(Constants.KEY_PLANNEDMEAL_IMAGE)));
        recipe.setPublisher(cursor.getString(cursor.getColumnIndex(Constants.KEY_PLANNEDMEAL_PUBLISHER)));
        recipe.setSourceURL(cursor.getString(cursor.getColumnIndex(Constants.KEY_PLANNEDMEAL_SOURCE)));
        // Get planned meal id and do new query to find ingredients that belong to this meal
        String plannedMealID = cursor.getString(cursor.getColumnIndex(Constants.KEY_PLANNEDMEAL_ID));
        cursor = db.query(Constants.TABLE_INGREDIENT, new String[] {
                Constants.KEY_INGREDIENT_TEXT, Constants.KEY_INGREDIENT_PLANNEDMEAL},
                Constants.KEY_INGREDIENT_PLANNEDMEAL + " = " + plannedMealID,
                null,null,null,null);
        // Initialize ingredients list
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                // Create ingredient from ingredient text in database
                Ingredient ingredient = new Ingredient();
                ingredient.setItemName(cursor.getString(cursor.getColumnIndex(Constants.KEY_INGREDIENT_TEXT)));
                ingredients.add(ingredient);
            } while (cursor.moveToNext());
        }
        recipe.setIngredients(ingredients);
        PlannedMeal meal = new PlannedMeal();
        meal.setRecipe(recipe);
        meal.setDateFromString(cursor.getString(cursor.getColumnIndex(Constants.KEY_PLANNEDMEAL_DATE)));
        cursor.close();
        return meal;
    }
    // Add planned meal to database
    public void addPlannedMeal(PlannedMeal meal) {
        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();
        // Set content values with planed meal details
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_PLANNEDMEAL_TITLE, meal.getRecipe().getTitle());
        values.put(Constants.KEY_PLANNEDMEAL_IMAGE, meal.getRecipe().getImageLink());
        values.put(Constants.KEY_PLANNEDMEAL_PUBLISHER, meal.getRecipe().getPublisher());
        values.put(Constants.KEY_PLANNEDMEAL_SOURCE, meal.getRecipe().getSourceURL());
        values.put(Constants.KEY_PLANNEDMEAL_DATE, meal.getDateString());
        // Insert planned meal values to database
        long mealID = db.insert(Constants.TABLE_PLANNED_MEAL, null, values);
        // Go thru all ingredients in meal
        for (int i = 0; i < meal.getRecipe().getIngredients().size(); i++) {
            ContentValues ingredientValue = new ContentValues();
            ingredientValue.put(Constants.KEY_INGREDIENT_TEXT, meal.getRecipe().getIngredients().get(i).getItemName());
            ingredientValue.put(Constants.KEY_INGREDIENT_PLANNEDMEAL, Long.toString(mealID));
            db.insert(Constants.TABLE_INGREDIENT, null, ingredientValue);
        }
    }
    // Delete planned meal with keyId
    public void deletePlannedMeal(String keyId) {
        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete planned meal which matches title
        db.delete(Constants.TABLE_PLANNED_MEAL, Constants.KEY_PLANNEDMEAL_ID + " = " + keyId, null);
        // Delete ingredients from this meal
        db.delete(Constants.TABLE_INGREDIENT, Constants.KEY_INGREDIENT_PLANNEDMEAL + " = " + keyId, null);
    }

    // Add ingredients to shopping list
    public void addShoppingListFromIngredients(ArrayList<Ingredient> ingredients) {
        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();
        // Go thru all ingredients, split their name and add it to list of names
        ArrayList<String> ingredientsNames = new ArrayList<>();
        for (int i = 0; i < ingredients.size(); i++) {
            String[] splitted = ingredients.get(i).getItemName().split(",");
            ingredientsNames.add(splitted[0]);
        }
        // Get each ingredient and insert to shopping list table
        ContentValues values = new ContentValues();
        for (int i = 0; i < ingredientsNames.size(); i++) {
            values.put(Constants.KEY_ITEM_STRING, ingredientsNames.get(i));
            db.insert(Constants.TABLE_SHOPPINGLIST_NAME, null, values);
        }
    }

}
