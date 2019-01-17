package com.slylamb.pocketcuisine.Views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.slylamb.pocketcuisine.Models.Ingredient;
import com.slylamb.pocketcuisine.Presenters.RecipePresenter;
import com.slylamb.pocketcuisine.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


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

        // Todo: Test outcome, mainly for ingredients - test ingredients without specification and old versions too (16api)
        imgRecipe.setImageBitmap(image);
        txtRecipeName.setText(name);
        String durationText = String.valueOf(duration) + " minutes";
        txtRecipeDuration.setText(durationText);
        String servingsText = String.valueOf(servings) + " servings";
        txtRecipeServings.setText(servingsText);
        String ingredientsText = "";
        for (Ingredient ingredient : ingredients) {
            ingredientsText += "*" + ingredient.getQuantity() + " " + ingredient.getMeasurement() + " ";
            if (ingredient.hasSpecification()) {
                ingredientsText += ingredient.getSpecification() + " ";
            }
            ingredientsText += ingredient.getName();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                ingredientsText += System.lineSeparator();
            } else {
                ingredientsText += "\n";
            }
        }
        txtRecipeIngredients.setText(ingredientsText);

    }

    @Override
    public void setButton(boolean picked, String button) {

        // Todo: find meaningful color value, or different way to differentiate buttons (different images?)
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

    @Override
    public void showMealPlannerDialog() {

        LayoutInflater layoutInflater = null;
        final View view = layoutInflater.inflate(R.layout.add_meal_planner_dialog, null);

        final EditText etxtDatePicked = view.findViewById(R.id.etxt_date_picked);
        final Button btnPickDate = view.findViewById(R.id.btn_pick_date);

        final RadioGroup rgrpMealType = view.findViewById(R.id.rgrp_meal_type);
        final RadioButton rbtnSelected;

        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Add to Meal Planner:")
            .setMessage("Pick a date and meal type below").setCancelable(true).setView(view)
            .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Date today = new Date();
                    // check date selected is in the future
                    // check no meals for type and date
                    // call presenter method to add to mealplanner

                }
            })
            .setNegativeButton("Cancel", null)
            .create();

        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getBaseContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String datePicked = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                etxtDatePicked.setText(datePicked);

                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }

        });
    }

}
