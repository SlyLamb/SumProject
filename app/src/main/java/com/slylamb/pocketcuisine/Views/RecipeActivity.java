package com.slylamb.pocketcuisine.Views;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.slylamb.pocketcuisine.Models.Ingredient;
import com.slylamb.pocketcuisine.Presenters.RecipePresenter;
import com.slylamb.pocketcuisine.R;
import java.util.ArrayList;


public class RecipeActivity extends Activity implements RecipePresenter.View {

    private RecipePresenter presenter;
    private ImageView imgRecipe;
    private TextView txtRecipeName;
    private Button btnAddFavorites;
    private Button btnAddCooked;
    private Button btnAddMealPlanner;
    private TextView txtRecipeDuration;
    private TextView txtRecipeServings;
    private TextView txtRecipeIngredients;
    private Button btnAddShoppingList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);

        initializeView();
        presenter = new RecipePresenter(this);

        presenter.setRecipeDetails(); // sets images and texts for selected recipe

        btnAddFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addFavorites();
            }
        });

        btnAddCooked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addCooked();
            }
        });

        btnAddMealPlanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addMealPlanner();
            }
        });

        btnAddShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addShoppingList();
            }
        });

    }

    private void initializeView() {
        imgRecipe = findViewById(R.id.img_recipe);
        txtRecipeName = findViewById(R.id.txt_recipe_name);
        btnAddFavorites = findViewById(R.id.btn_add_favorites);
        btnAddCooked = findViewById(R.id.btn_add_cooked);
        btnAddMealPlanner = findViewById(R.id.btn_add_meal_planner);
        txtRecipeDuration = findViewById(R.id.txt_recipe_duration);
        txtRecipeServings = findViewById(R.id.txt_recipe_servings);
        txtRecipeIngredients = findViewById(R.id.txt_recipe_ingredients);
        btnAddShoppingList = findViewById(R.id.btn_add_shopping_list);
    }

    @Override
    public void setRecipeDetails(Bitmap image, String name, float duration, int servings, ArrayList<Ingredient> ingredients) {

        imgRecipe.setImageBitmap(image);
        txtRecipeName.setText(name);
        txtRecipeDuration.setText(String.valueOf(duration));
        txtRecipeServings.setText(String.valueOf(servings));
        //recipeIngredients requires some formatting

    }

    @Override
    public void setButton(boolean picked, String button) {

        // Example of difference between buttons
        int color;
        if (picked) {
            color = 1; //red
        } else {
            color = 2; //blue
        }
        switch (button) {
            case "addFavorites":
                btnAddFavorites.setBackgroundColor(color);
            case "addCooked":
                btnAddCooked.setBackgroundColor(color);
        }

    }

}
