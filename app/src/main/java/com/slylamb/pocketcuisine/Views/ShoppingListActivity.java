package com.slylamb.pocketcuisine.Views;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import com.slylamb.pocketcuisine.Data.DataBaseHandler;
import com.slylamb.pocketcuisine.Models.Ingredient;
import com.slylamb.pocketcuisine.Models.Recipe;
import com.slylamb.pocketcuisine.R;
import java.util.ArrayList;
import java.util.List;



public class ShoppingListActivity extends PocketCuisineActivity {

    private RecyclerView recyclerView;
    private ShoppingListRecyclerViewAdapter recyclerViewAdapter;
    private DataBaseHandler db;
    private List<Ingredient> ingredientList;
    private List<Ingredient> ingredientItems;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText txtItem;
    private EditText txtQuantity;
    private Button btnSave;
    private Button btnAdd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list);

        btnAdd = findViewById(R.id.btnAdd);
        db = new DataBaseHandler(this);
        recyclerView = (RecyclerView) findViewById(R.id.shoppingListRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ingredientList = new ArrayList<>();
        ingredientItems = new ArrayList<>();

        Recipe recipe = new Recipe();

       // db.addShoppingListFromRecipe( recipe);

        // get all the existing ingredient items in the shopping list table
        ingredientList = db.getALLStringItemsFromShoppingListTB();

//        for(int i=0;i<ingredientList.size();i++){
//            Ingredient ingredient = new Ingredient();
//            ingredient.setItemName(ingredientList.get(i).getItemName());
//            ingredient.setID(ingredientList.get(i).getID());
//            ingredientItems.add(ingredient);
//        }


        //instantiate the recyclerviewadpater with the returned ingredientlist from database
        recyclerViewAdapter = new ShoppingListRecyclerViewAdapter(this,ingredientList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUpDialog();
            }
        });

    }


    // to ensure that when user clicks on back button the main page will be displayed
    // once a new item has been added successfully,
    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(ShoppingListActivity.this, HomePageActivity.class);
        startActivity(intent);
    }

    //pop up dialog for user to key in new item info
    public void showPopUpDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.shopping_list_pop_up, null);
        txtItem = (EditText) view.findViewById(R.id.txtItem);
        txtQuantity = (EditText) view.findViewById(R.id.txtQty);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItemToDataBase(v);
            }
        });

    }

    //save the user input into database
    public void saveItemToDataBase(View v){

        Ingredient ingredient = new Ingredient();
        String itemName = txtItem.getText().toString();
        String itemQty = txtQuantity.getText().toString();
        String item = itemQty + " "+itemName;
        ingredient.setItemName(item);
        db.addShoppingListFromUserInput(ingredient);
        //inform the user that the item has been saved
        Snackbar.make(v, "Item Saved!", Snackbar.LENGTH_LONG).show();

        new Thread() {
            @Override
            public void run() {
                try {
                    this.sleep(1200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
                startActivity(new Intent(ShoppingListActivity.this, ShoppingListActivity.class));
            }
        }.start();

    }

}
