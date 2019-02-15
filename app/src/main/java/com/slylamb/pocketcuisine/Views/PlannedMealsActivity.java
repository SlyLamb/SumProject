package com.slylamb.pocketcuisine.Views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.slylamb.pocketcuisine.Models.MealPlanner;
import com.slylamb.pocketcuisine.Models.PlannedMeal;
import com.slylamb.pocketcuisine.Presenters.PlannedMealsActivityPresenter;
import com.slylamb.pocketcuisine.R;

import java.util.ArrayList;

public class PlannedMealsActivity extends PocketCuisineActivity {

    private static final String TAG = "PlannedMealActivity";
    private PlannedMealsActivityPresenter presenter;
    private ListView lstvPlannedMeals;
    private PlannedMealsListViewAdapter adapter;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planned_meals);
        lstvPlannedMeals = findViewById(R.id.lstv_planned_meals);
        presenter = new PlannedMealsActivityPresenter(this);
        // Get intent with date from MealPlannerActivity
        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        // Get planned meals titles on date chosen
        ArrayList<String> plannedMealsTitles = new ArrayList<>(presenter.getPlannedMealsTitles(date));
        // Initialise list view adapter with planned meals
        adapter = new PlannedMealsListViewAdapter(this, plannedMealsTitles, presenter);
        lstvPlannedMeals.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(PlannedMealsActivity.this, MealPlannerActivity.class);
        startActivity(intent);
    }
}
