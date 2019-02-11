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

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_SHOPPINGLIST_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_FAVORITE_RECIPE);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_PLANNED_MEAL);
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
    // GABRIEL CODE START
    //----------------------------------------------------------------------------------------
    // Get recipe at keyId
    public Recipe getRecipe(String keyId) {
        // Get readable database
        SQLiteDatabase db = this.getReadableDatabase();
        // Get cursor for recipe at keyId
        Cursor cursor = db.query(Constants.TABLE_FAVORITE_RECIPE, new String[] {
                Constants.KEY_RECIPE_ID, Constants.KEY_RECIPE_TITLE, Constants.KEY_RECIPE_IMAGE,
                Constants.KEY_RECIPE_PUBLISHER, Constants.KEY_RECIPE_SOURCE}, Constants.KEY_RECIPE_ID + " = " + keyId,
                null, null, null, null );
        // Create recipe object from database and return it
        Recipe recipe = new Recipe();
        recipe.setTitle(cursor.getString(cursor.getColumnIndex(Constants.KEY_RECIPE_TITLE)));
        recipe.setImageLink(cursor.getString(cursor.getColumnIndex(Constants.KEY_RECIPE_IMAGE)));
        recipe.setPublisher(cursor.getString(cursor.getColumnIndex(Constants.KEY_RECIPE_PUBLISHER)));
        recipe.setSourceURL(cursor.getString(cursor.getColumnIndex(Constants.KEY_RECIPE_SOURCE)));
        cursor.close();
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
        // Interst recipe values to database
        db.insert(Constants.TABLE_FAVORITE_RECIPE, null, values);
    }
    // Delete recipe from database
    public void deleteRecipe(String title) {
        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete recipe which matches title
        db.delete(Constants.TABLE_FAVORITE_RECIPE, Constants.KEY_RECIPE_TITLE + " = " + title, null);
    }
    // Add planned meal to database
    public void addPlannedMeal(PlannedMeal meal) {
        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();
        // Set content values with recipe details
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_PLANNEDMEAL_TITLE, meal.getRecipe().getTitle());
        values.put(Constants.KEY_PLANNEDMEAL_IMAGE, meal.getRecipe().getImageLink());
        values.put(Constants.KEY_PLANNEDMEAL_PUBLISHER, meal.getRecipe().getPublisher());
        values.put(Constants.KEY_PLANNEDMEAL_SOURCE, meal.getRecipe().getSourceURL());
        values.put(Constants.KEY_PLANNEDMEAL_DATE, meal.getDateString());
        // Interst recipe values to database
        db.insert(Constants.TABLE_PLANNED_MEAL, null, values);
    }
    // Get all planned meals
    public ArrayList<PlannedMeal> getPlannedMeals() {
        // Todo: get planned meals from database
        //return new ArrayList<>();

        // DEBUGGING
        // TEST DATA
        // 1
        Recipe recipe = new Recipe();
        recipe.setImageLink("http://static.food2fork.com/iW8v49knM5faff.jpg");
        recipe.setTitle("Chicken with Spring Vegetables and Gnocchi");
        recipe.setPublisher("Framed Cooks");
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        Ingredient ingredient = new Ingredient();
        ingredient.setItemName("10 cups chicken broth");
        ingredients.add(ingredient);
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setItemName("1/2 stick butter");
        ingredients.add(ingredient1);
        recipe.setIngredients(ingredients);
        recipe.setSourceURL("http://www.framedcooks.com/2012/05/chicken-with-spring-vegetables-and-gnocchi.html");
        ArrayList<PlannedMeal> meals = new ArrayList<>();
        PlannedMeal meal = new PlannedMeal();
        meal.setRecipe(recipe);
        String date = "15-02-2019";
        final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date realDate = new Date();
        try {
            realDate = formatter.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        meal.setDate(realDate);
        meals.add(meal);
        // 2
        Recipe recipe1 = new Recipe();
        recipe1.setImageLink("http://static.food2fork.com/Jalapeno2BPopper2BGrilled2BCheese2BSandwich2B12B500fd186186.jpg");
        recipe1.setTitle("Jalapeno Popper Grilled Cheese Sandwich");
        recipe1.setPublisher("Closet Cooking");
        ArrayList<Ingredient> ingredients1 = new ArrayList<>();
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setItemName("2 jalapeno peppers, cut in half lengthwise and seeded");
        ingredients1.add(ingredient2);
        Ingredient ingredient3 = new Ingredient();
        ingredient3.setItemName("2 slices sour dough bread");
        ingredients1.add(ingredient3);
        recipe1.setIngredients(ingredients1);
        recipe1.setSourceURL("http://www.closetcooking.com/2011/04/jalapeno-popper-grilled-cheese-sandwich.html");
        ArrayList<PlannedMeal> meals1 = new ArrayList<>();
        PlannedMeal meal1 = new PlannedMeal();
        meal1.setRecipe(recipe1);
        String date1 = "19-02-2019";
        Date realDate1 = new Date();
        try {
            realDate1 = formatter.parse(date1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        meal1.setDate(realDate1);
        meals.add(meal1);
        return meals;
    }
    // Add ingredients to shopping list
    public void addShoppingListFromIngredients(ArrayList<Ingredient> ingredients) {
        // Todo: add ingredents to database
    }
    // Get planned meal with keyId
    public PlannedMeal getPlannedMeal(String keyId) {
        // Todo: get planned meal from database and return it
        return new PlannedMeal();



    }
    // Delete planned meal with keyId
    public void deletePlannedMeal(String keyId) {
        // Todo: delete planned meal at keyId from database
    }

}
