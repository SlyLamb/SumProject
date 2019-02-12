package com.slylamb.pocketcuisine.Views;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.slylamb.pocketcuisine.Presenters.MainPageActivityPresenter;
import com.slylamb.pocketcuisine.R;


public class HomePageActivity extends AppCompatActivity {
    private ImageButton btnSearch;
    private ImageButton btnShoppingList;
    private ImageButton btnPlanner;
    private ImageButton btnFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        btnSearch = findViewById(R.id.btnSearch);
        btnShoppingList =  findViewById(R.id.btnShoppingList);
        btnPlanner = findViewById(R.id.btnPlanner);
        btnFavourite = findViewById(R.id.btnFavourite);



        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomePageActivity.this, SearchRecipeActivity.class));

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

        btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomePageActivity.this, FavouritePageActivity.class));

            }
        });



    }
}
