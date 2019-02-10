package com.slylamb.pocketcuisine.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.slylamb.pocketcuisine.R;

public class PlannedMealsActivity extends AppCompatActivity {//implements PlannedMealsActivityPresenter.View {

    private ListView lstvPlannedMeals;
    private PlannedMealsListViewAdapter adapter;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planned_meals);
        lstvPlannedMeals = findViewById(R.id.lstv_planned_meals);
        // Get intent with date from MealPlannerActivity
        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        adapter = new PlannedMealsListViewAdapter(date);
        lstvPlannedMeals.setAdapter(adapter);
    }
}
