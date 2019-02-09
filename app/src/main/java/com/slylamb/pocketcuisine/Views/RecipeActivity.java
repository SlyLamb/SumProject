
package com.slylamb.pocketcuisine.Views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.slylamb.pocketcuisine.Presenters.RecipeActivityPresenter;
import com.slylamb.pocketcuisine.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.ContentValues.TAG;


public class RecipeActivity extends Activity implements RecipeActivityPresenter.View {

    private RecipeActivityPresenter presenter;
    private ImageView imgRecipe;
    private TextView txtRecipeName;
    private Button btnAddFavorites;
    private Button btnAddCooked;
    private Button btnAddMealPlanner;
    private Button btnAddShoppingList;
    private WebView wvwPublisherSite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);
        // Initialize view and presenter
        initializeView();
        // Get recipe id from RecipeSearchActivity
        Intent intent = getIntent();
        String recipeID = intent.getStringExtra("recipeID");
        // Initialize presenter and pass on recipeID for recipe in API
        presenter = new RecipeActivityPresenter(this, recipeID);
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
                showMealPlannerDialog();
            }
        });
        btnAddShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShoppingListDialog();
            }
        });
    }

    private void initializeView() {
        imgRecipe = findViewById(R.id.img_recipe);
        txtRecipeName = findViewById(R.id.txt_recipe_name);
        btnAddFavorites = findViewById(R.id.btn_add_favorites);
        btnAddCooked = findViewById(R.id.btn_add_cooked);
        btnAddMealPlanner = findViewById(R.id.btn_add_meal_planner);
        btnAddShoppingList = findViewById(R.id.btn_add_shopping_list);
        wvwPublisherSite = findViewById(R.id.wvw_publisher_site);
    }

    @Override
    public void setRecipeDetails(String imageLink, String name, String sourceURL) {
        // Todo: Image not showing
        // Set image and texts with recipe information
        Picasso.with(this)
                .load(imageLink)
                .error(R.drawable.common_full_open_on_phone)
                .fit()
                .into(imgRecipe);
        txtRecipeName.setText(name);
        wvwPublisherSite.loadUrl(sourceURL);
    }

    @Override
    public void setButton(boolean picked, String button) {
        // Todo: find meaningful color value, or different way to differentiate buttons (different images?)
        /*int color;
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
        }*/
    }

    @Override
    public void showMealPlannerDialog() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(RecipeActivity.this,
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Create string with date format wanted and add it to EditText
                String datePicked = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                // Add meal to meal planner at date picked
                presenter.addToMealPlanner(datePicked);
                // Let user know it's been successfully added
                Toast.makeText(getBaseContext(), "Recipe successfully added to Meal Planner",
                        Toast.LENGTH_LONG).show();
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void showShoppingListDialog() {
        // Inflate dialog layout
        LayoutInflater myLayout = LayoutInflater.from(RecipeActivity.this);
        final View view = myLayout.inflate(R.layout.add_shopping_list_dialog, null);
        // Dialog has an edit text with the shopping list name
        final EditText etxtShoppingListName = view.findViewById(R.id.etxt_shopping_list_name);
        // Create dialog with title, message, and positive and negative behaviours
        new AlertDialog.Builder(RecipeActivity.this).setTitle("Add to Shopping Lists:")
                .setMessage("Pick a name").setCancelable(true).setView(view)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Todo: checks, if and elses with Toasts
                        // Add meal to meal planner
                        presenter.addMealToShoppingList(etxtShoppingListName.getText().toString());
                        // Let user know it's been successfully added
                        Toast.makeText(getBaseContext(), "Shopping List successfully created",
                                Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create().show();
    }
}