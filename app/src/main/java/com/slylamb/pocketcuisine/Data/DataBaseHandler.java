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

public class DataBaseHandler extends SQLiteOpenHelper {
    private Context context;
    public DataBaseHandler(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_PLANEDMEAL_TABLE ="CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY," + Constants.KEY_ITEM_STRING + " TEXT"
                + " );";

        db.execSQL(CREATE_PLANEDMEAL_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);


        onCreate(db);

    }

    public void addShoppingListFromRecipe(Recipe recipe) {


        ArrayList<String> ingredients = new ArrayList<>(recipe.getIngredients());
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        for(int i=0;i<ingredients.size();i++){

            values.put(Constants.KEY_ITEM_STRING,ingredients.get(i));

        }

        db.insert(Constants.TABLE_NAME, null, values);

    }



    public List<Ingredient> getALLStringItems() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Ingredient> ingredientList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[] {
                Constants.KEY_ID, Constants.KEY_ITEM_STRING,
                Constants.KEY_DATE_Time}, null, null, null, null, Constants.KEY_DATE_Time + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Ingredient ingredient = new Ingredient();
                ingredient.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                ingredient.setItemName(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM_STRING)));


                ingredientList.add(ingredient);

            }while (cursor.moveToNext());
        }

        return ingredientList;
    }

    // Get recipe at keyId
    public Recipe getRecipe(String keyId) {
        // Todo: get recipe from database using KEY_ID
        return new Recipe();
    }
    // True if recipe in database, false otherwise
    public boolean hasRecipe(String title) {
        // Todo: return true if any recipe in database match title, false otherwise
        return false;
    }
    // Add recipe to database
    public void addRecipe(Recipe recipe) {
        // Todo: add recipe to database
    }
    // Delete recipe from database
    public void deleteRecipe(String title) {
        // Todo: delete recipe from database
    }
    // Add planned meal to database
    public void addPlannedMeal(PlannedMeal meal) {
        // Todo: add planned meal to database
    }
    // Get all planned meals
    public ArrayList<PlannedMeal> getPlannedMeals() {
        // Todo: get planned meals from database
        return new ArrayList<>();
    }
    // Add ingredients to shopping list
    public void addShoppingListFromIngredients(ArrayList<Ingredient> ingredients) {
        // Todo: add ingredents to database
    }

}
