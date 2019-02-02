package com.slylamb.pocketcuisine.Views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.slylamb.pocketcuisine.Models.Ingredient;
import com.slylamb.pocketcuisine.R;

import org.w3c.dom.Text;

import java.util.List;

public class ShoppingListRecyclerViewAdapter extends RecyclerView.Adapter<ShoppingListRecyclerViewAdapter.ViewHolder> {

    private Context context;
    //private List<Ingredient>ingredientItems;
    private List<String>ingredientItems;

    private LayoutInflater inflater;

    public ShoppingListRecyclerViewAdapter(Context context, List<String>ingredientItems){
        this.context = context;
        this.ingredientItems = ingredientItems;

    }


    @Override
    public ShoppingListRecyclerViewAdapter.ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.shopping_list_row, viewGroup, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder( ShoppingListRecyclerViewAdapter.ViewHolder viewHolder, int i) {

        String ingredient = ingredientItems.get(i);

        viewHolder.itemName.setText(ingredient);

    }

    @Override
    public int getItemCount() {
        return ingredientItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView itemName;
        public Button btnDelete;


        public ViewHolder(View itemView, Context cxt) {
            super(itemView);
            context = cxt;
            itemName = (TextView) itemView.findViewById(R.id.itemName);
            btnDelete = (Button) itemView.findViewById(R.id.deleteButton);
        }


    }
}
