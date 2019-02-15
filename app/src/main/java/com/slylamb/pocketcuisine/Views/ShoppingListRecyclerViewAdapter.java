package com.slylamb.pocketcuisine.Views;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.slylamb.pocketcuisine.Data.DataBaseHandler;
import com.slylamb.pocketcuisine.Models.Ingredient;
import com.slylamb.pocketcuisine.R;

import java.util.List;

public class ShoppingListRecyclerViewAdapter extends RecyclerView.Adapter<ShoppingListRecyclerViewAdapter.ViewHolder> {


    private Context context;
    private List<Ingredient>ingredientItems;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    public ShoppingListRecyclerViewAdapter(Context context, List<Ingredient>ingredientItems){
        this.context = context;
        this.ingredientItems = ingredientItems;

    }


    //inflate the shopping list viewholder with xml layout
    @Override
    public ShoppingListRecyclerViewAdapter.ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.shopping_list_row, viewGroup, false);

        return new ViewHolder(view, context);
    }

    // bind the data with the layout in each row in the recycler view
    @Override
    public void onBindViewHolder( ShoppingListRecyclerViewAdapter.ViewHolder viewHolder, int i) {

        Ingredient ingredient = ingredientItems.get(i);

        viewHolder.itemName.setText(ingredient.getItemName());

    }

    //get the number of items
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
            btnDelete = (Button) itemView.findViewById(R.id.btnDeleteRow);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Ingredient ingredient = ingredientItems.get(getAdapterPosition());
                    Log.d("position",String.valueOf(getAdapterPosition()));
                    Log.d("position",String.valueOf(ingredient.getID()));
                    deleteItem(ingredient.getID());

                }
            });
        }


        //delete the shopping list item using the id stored in each ingredient object
        public void deleteItem(final int id){

            dialogBuilder = new AlertDialog.Builder(context);

            View view = LayoutInflater.from(context).inflate(R.layout.shopping_list_dialog_delete, null);

            Button btnNo = (Button) view.findViewById(R.id.noButton);
            Button btnYes = (Button) view.findViewById(R.id.yesButton);

            // dialog popped up to aler the user to confirm the deletion
            dialogBuilder.setView(view);
            dialogBuilder.setTitle("Delete Item");
            dialogBuilder.setMessage("Are you sure to delete this Item?");

            dialog = dialogBuilder.create();
            dialog.show();

            btnNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataBaseHandler db = new DataBaseHandler(context);
                    db.deleteStringItemFromShoppingListTB(id);
                    ingredientItems.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    dialog.dismiss();
                }
            });

        }

    }
}
