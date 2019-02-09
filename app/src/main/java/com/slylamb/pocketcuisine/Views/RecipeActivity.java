
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
    private WebView wvwPublisherSite;
    private Button btnAddFavorites;
    private Button btnAddMealPlanner;
    private Button btnAddShoppingList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);
        initializeView();

        // Get intent from previous activity
        Intent intent = getIntent();
        String recipeID;
        // If intent has recipeIDapi extras, user came from Recipe Search activity
        if (intent.hasExtra("recipeIDapi")) {
            recipeID = intent.getStringExtra("recipeIDapi");
            // Initialize presenter and pass on recipeID for recipe in API
            presenter = new RecipeActivityPresenter(this, recipeID, "API");
        // If intent has recipeIDdb extras, user came from Favorites or Planned Meals activity
        } else if (intent.hasExtra("recipeIDdb")) {
            recipeID = intent.getStringExtra("recipeIDdb");
            // Initialize presenter and pass on recipeID for recipe in Database
            presenter = new RecipeActivityPresenter(this, recipeID, "DB");
        }

        // Set images and texts for selected recipe
        presenter.setRecipeDetails();
        btnAddFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addFavorites();
                Toast.makeText(getBaseContext(), "Recipe successfully added to Favorites",
                        Toast.LENGTH_LONG).show();
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
                presenter.addMealToShoppingList();
                Toast.makeText(getBaseContext(), "Recipe ingredients successfully added to Shopping List",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initializeView() {
        imgRecipe = findViewById(R.id.img_recipe);
        txtRecipeName = findViewById(R.id.txt_recipe_name);
        wvwPublisherSite = findViewById(R.id.wvw_publisher_site);
        btnAddFavorites = findViewById(R.id.btn_add_favorites);
        btnAddMealPlanner = findViewById(R.id.btn_add_meal_planner);
        btnAddShoppingList = findViewById(R.id.btn_add_shopping_list);
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
    public void setFavoriteButton(boolean picked) {
        // Todo: find meaningful color value, or different way to differentiate buttons (different images?)

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
}