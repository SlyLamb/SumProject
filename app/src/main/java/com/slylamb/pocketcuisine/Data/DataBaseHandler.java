package com.slylamb.pocketcuisine.Data;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

        Log.d("dbhandler","onCreate has been called");

        String CREATE_SHOPPINGLIST_TABLE ="CREATE TABLE " + Constants.TABLE_SHOPPINGLIST_NAME + "("
                + Constants.KEY_STRING_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Constants.KEY_ITEM_STRING + " TEXT"
                 + " );";

//        String CREATE_PLANNEDMEAL_TABLE="CREATE TABLE " + Constants.TABLE_PLANNED_MEAL + "("
//                + Constants.KEY_PLANNEDMEAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Constants.KEY_PLANNEDMEAL_NAME + " TEXT,"
//                + Constants.KEY_PLANNEDMEAL_URL + " TEXT," + Constants.KEY_PLANNEDMEAL_IMAGELINK + " TEXT"+ " );";

        db.execSQL(CREATE_SHOPPINGLIST_TABLE);
       // db.execSQL(CREATE_PLANNEDMEAL_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_SHOPPINGLIST_NAME);


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


        String CREATE_SHOPPINGLIST_TABLE ="CREATE TABLE " + Constants.TABLE_SHOPPINGLIST_NAME + "("
                + Constants.KEY_STRING_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Constants.KEY_ITEM_STRING + " TEXT"
                + " );";

        db.execSQL(CREATE_SHOPPINGLIST_TABLE);


        List<String>ingredientItems = new ArrayList<>();

        List<String> newingredientItems= new ArrayList<>();

        // hardcoded example, will remove later
        ingredientItems.add("2 jalapeno peppers, cut in half lengthwise and seeded");
        ingredientItems.add("2 slices sour dough bread");
        ingredientItems.add("1 tablespoon butter, room temperature");
        ingredientItems.add("2 tablespoons cream cheese, room temperature");
        ingredientItems.add("1/2 cup jack and cheddar cheese, shredded");
        ingredientItems.add("1 tablespoon tortilla chips, crumbled\n");

        for(int i=0;i<ingredientItems.size();i++){
            String[] splitted = ingredientItems.get(i).split(",");
            newingredientItems.add(splitted[0]);
        }

        //ArrayList<String> ingredients = new ArrayList<>(recipe.getIngredients());

        ContentValues values = new ContentValues();

        for(int i=0;i<newingredientItems.size();i++){

            values.put(Constants.KEY_ITEM_STRING,newingredientItems.get(i));
            Log.d("database ingredient",newingredientItems.get(i));
            db.insert(Constants.TABLE_SHOPPINGLIST_NAME, null, values);
            Log.d("dbhandler","insert has been called");

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



}
