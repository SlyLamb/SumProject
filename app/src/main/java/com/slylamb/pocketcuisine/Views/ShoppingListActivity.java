package com.slylamb.pocketcuisine.Views;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import android.support.v7.widget.helper.ItemTouchHelper;


import com.google.android.gms.common.internal.ShowFirstParty;
import com.slylamb.pocketcuisine.Data.DataBaseHandler;
import com.slylamb.pocketcuisine.Models.Ingredient;
import com.slylamb.pocketcuisine.Models.Recipe;
import com.slylamb.pocketcuisine.R;
import java.util.ArrayList;
import java.util.List;

import com.slylamb.pocketcuisine.Util.SwipeController;
import com.slylamb.pocketcuisine.Util.SwipeControllerActions;


public class ShoppingListActivity extends AppCompatActivity {

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
    SwipeController swipeController = null;


    //private List<String> ingredientItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list);

        btnAdd = findViewById(R.id.btnAdd);
        db = new DataBaseHandler(this);
        recyclerView = (RecyclerView) findViewById(R.id.shoppingListRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



//        swipeController = new SwipeController(new SwipeControllerActions() {
//            @Override
//            public void onRightClicked(int position) {
////                recyclerViewAdapter.players.remove(position);
////                recyclerViewAdapter.notifyItemRemoved(position);
////                recyclerViewAdapter.notifyItemRangeChanged(position, recyclerViewAdapter.getItemCount());
//            }
//        });
//
//        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
//        itemTouchhelper.attachToRecyclerView(recyclerView);
//
//        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//                swipeController.onDraw(c);
//            }
//        });

        ingredientList = new ArrayList<>();
        ingredientItems = new ArrayList<>();

        List<String> newingredientItems= new ArrayList<>();


        Recipe recipe = new Recipe();
        db.addShoppingListFromRecipe( recipe);
        ingredientList = db.getALLStringItemsFromShoppingListTB();

        for(int i=0;i<ingredientList.size();i++){
            Ingredient ingredient = new Ingredient();
            ingredient.setItemName(ingredientList.get(i).getItemName());
            ingredient.setID(ingredientList.get(i).getID());
            ingredientItems.add(ingredient);
        }

        recyclerViewAdapter = new ShoppingListRecyclerViewAdapter(this,ingredientItems);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUpDialog();
            }
        });

    }

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

    public void saveItemToDataBase(View v){

        Ingredient ingredient = new Ingredient();
        String itemName = txtItem.getText().toString();
        String itemQty = txtQuantity.getText().toString();
        String item = itemQty + " "+itemName;
        ingredient.setItemName(item);
        db.addShoppingListFromUserInput(ingredient);
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
