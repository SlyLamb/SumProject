package com.slylamb.pocketcuisine.Views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.slylamb.pocketcuisine.Data.DataBaseHandler;
import com.slylamb.pocketcuisine.Models.Ingredient;
import com.slylamb.pocketcuisine.Models.Recipe;
import com.slylamb.pocketcuisine.R;
import java.util.ArrayList;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ShoppingListRecyclerViewAdapter recyclerViewAdapter;
    private DataBaseHandler db;
    private List<Ingredient> ingredientList;
    private List<Ingredient> ingredientItems;
    //private List<String> ingredientItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list);


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        db = new DataBaseHandler(this);
        recyclerView = (RecyclerView) findViewById(R.id.shoppingListRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ingredientList = new ArrayList<>();
        ingredientItems = new ArrayList<>();

        List<String> newingredientItems= new ArrayList<>();

//
//        ingredientItems.add("2 jalapeno peppers, cut in half lengthwise and seeded");
//        ingredientItems.add("2 slices sour dough bread");
//        ingredientItems.add("1 tablespoon butter, room temperature");
//        ingredientItems.add("2 tablespoons cream cheese, room temperature");
//        ingredientItems.add("1/2 cup jack and cheddar cheese, shredded");
//        ingredientItems.add("1 tablespoon tortilla chips, crumbled\n");

//        for(int i=0;i<ingredientItems.size();i++){
//            String[] splitted = ingredientItems.get(i).split(",");
//            newingredientItems.add(splitted[0]);
//        }


        Recipe recipe = new Recipe();
        db.addShoppingListFromRecipe( recipe);
        ingredientList = db.getALLStringItemsFromShoppingListTB();

        for(Ingredient i : ingredientList){

            Ingredient ingredient = new Ingredient();
            ingredient.setItemName(i.getItemName());
            ingredientItems.add(ingredient);

        }

        recyclerViewAdapter = new ShoppingListRecyclerViewAdapter(this,ingredientItems);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

    }

}
