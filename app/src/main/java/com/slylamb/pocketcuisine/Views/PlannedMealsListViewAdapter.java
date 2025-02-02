package com.slylamb.pocketcuisine.Views;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.slylamb.pocketcuisine.Models.MealPlanner;
import com.slylamb.pocketcuisine.Models.PlannedMeal;
import com.slylamb.pocketcuisine.Models.Recipe;
import com.slylamb.pocketcuisine.Presenters.PlannedMealsActivityPresenter;
import com.slylamb.pocketcuisine.R;

import java.util.ArrayList;

public class PlannedMealsListViewAdapter extends BaseAdapter {

    private PlannedMealsActivityPresenter presenter;
    private Context context;
    private ArrayList<String> plannedMeals;

    public PlannedMealsListViewAdapter(Context context, ArrayList<String> plannedMealsTitles, PlannedMealsActivityPresenter presenter) {
        this.context = context;
        plannedMeals = new ArrayList<>(plannedMealsTitles);
        this.presenter = presenter;
    }
    class ViewHolder {
        int position;
        Button btnMeal;
        Button btnDeleteMeal;
    }
    // How many items in ListView
    @Override
    public int getCount() {
        return plannedMeals.size();
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return i;
    }

// BELOW NOT DONE YET
    @Override
    public View getView(final int i, View convertView, final ViewGroup viewGroup) {
        ViewHolder vh;
        if (convertView == null) {
            // If it's not recycled, inflate it from xml
            convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.planned_meal, viewGroup, false);

            // Create a new ViewHolder
            vh = new ViewHolder();

            // Find view for buttons
            vh.btnMeal = convertView.findViewById(R.id.btn_meal);
            vh.btnDeleteMeal = convertView.findViewById(R.id.btn_delete_meal);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag(); // Otherwise get the ViewHolder
        }
        // Set its position
        vh.position = i;

        // Set text in meal button
        vh.btnMeal.setText(plannedMeals.get(vh.position));
        final int position = vh.position;

        // Set behavior in meal button
        vh.btnMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*

                LINK TO RECIPE ACTIVITY NOT WORKING

                Intent intent = new Intent(context, Recipe.class);
                intent.putExtra("recipeID", presenter.getPlannedMealId(position));
                intent.putExtra("activity", "FROM_PLANNED_MEAL");
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                try {
                    pendingIntent.send();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
                */
            }
        });
        // Set behavior in delete meal button
        vh.btnDeleteMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("plannedMealPosition", "" + position);
                presenter.deletePlannedMeal(plannedMeals.get(position));
                plannedMeals.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}