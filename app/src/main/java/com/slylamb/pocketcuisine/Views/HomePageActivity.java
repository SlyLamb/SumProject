package com.slylamb.pocketcuisine.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageButton;
import android.content.Intent;

import com.slylamb.pocketcuisine.R;


public class HomePageActivity extends AppCompatActivity {
    private ImageButton btnSearch;
    private ImageButton btnFavourite;
    private ImageButton btnShoppingList;
    private ImageButton btnPlanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        btnSearch = findViewById(R.id.btnSearch);
        btnFavourite = findViewById(R.id.btnFavourite);
        btnShoppingList =  findViewById(R.id.btnShoppingList);
        btnPlanner = findViewById(R.id.btnPlanner);



        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomePageActivity.this, RecipeSearchActivity.class));

            }
        });

        btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomePageActivity.this, FavouritePageActivity.class));

            }
        });

        btnShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomePageActivity.this, ShoppingListActivity.class));

            }
        });

        btnPlanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomePageActivity.this, MealPlannerActivity.class));

            }
        });
    }
}
