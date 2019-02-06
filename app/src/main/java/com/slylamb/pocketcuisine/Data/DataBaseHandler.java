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

        List<String>ingredientItems = new ArrayList<>();

        List<String> newingredientItems= new ArrayList<>();

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
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        for(int i=0;i<ingredients.size();i++){
//
//            values.put(Constants.KEY_ITEM_STRING,ingredients.get(i));
//
//        }

        for(int i=0;i<newingredientItems.size();i++){

            values.put(Constants.KEY_ITEM_STRING,newingredientItems.get(i));
            Log.d("database ingredient",newingredientItems.get(i));
            db.insert(Constants.TABLE_NAME, null, values);


        }


       // Log.d("database","insert works");

    }



    public List<Ingredient> getALLStringItems() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Ingredient> ingredientList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[] {
                 Constants.KEY_ITEM_STRING}, null, null, null, null, null );

       // Constants.KEY_ID,

        if (cursor.moveToFirst()) {
            do {
                Ingredient ingredient = new Ingredient();
               // ingredient.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                ingredient.setItemName(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM_STRING)));
                Log.d("database",ingredient.getItemName());


                ingredientList.add(ingredient);


            }while (cursor.moveToNext());
        }

        return ingredientList;
    }




}
