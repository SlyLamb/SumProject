package com.slylamb.pocketcuisine.Views;

import android.app.PendingIntent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.slylamb.pocketcuisine.Models.Recipe;
import com.slylamb.pocketcuisine.Presenters.RecipeSearchActivityPresenter;
import com.slylamb.pocketcuisine.R;
import com.squareup.picasso.Picasso;


public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.ViewHolder>{

    private final RecipeSearchActivityPresenter presenter;

    private Context context;

    public RecipeRecyclerViewAdapter(RecipeSearchActivityPresenter presenter,Context context){
        this.context = context;
        this.presenter = presenter;
        Log.d("RecipeRecyclerView","RecipeRecyclerViewAdapter constructor has been called");
    }

    //inflate the viewholder with xml layout
    @Override
    public RecipeRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_row,viewGroup,false);
        Log.d("onCreateViewHolder","onCreateViewHolder has been called");
        return new ViewHolder(view,context);
    }

    //bind the data with the viewholder in each row
    @Override
    public void onBindViewHolder( RecipeRecyclerViewAdapter.ViewHolder viewHolder, int position) {

        presenter.onBindRecipeRowViewAtPosition(position,viewHolder);
        Log.d("onBindViewHolder","onBindViewHolder has been called");

    }

    //return the number of items in the recycler view
    @Override
    public int getItemCount() {
        return presenter.getRecipesRowsCount();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements RecipeSearchActivityPresenter.RecipeRowView{
        ImageView recipeImage;
        TextView txtRecipePublisher;
        TextView txtRecipeTitle;


        public ViewHolder(View itemView, final Context cxt) {
            super(itemView);
            context = cxt;
            recipeImage = (ImageView) itemView.findViewById(R.id.recipeImage);
            txtRecipeTitle = (TextView) itemView.findViewById(R.id.txtRecipeTitle);
            txtRecipePublisher = (TextView) itemView.findViewById(R.id.txtRecipePublisher);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Recipe recipe = presenter.getRecipeList().get(getAdapterPosition());
                    //send the info about the specific view that the user has clicked on to the
                    //the next recipe page
                    Intent intent = new Intent(context, RecipeActivity.class);
                    //send the recipe id to the next page
                    intent.putExtra("recipeID",recipe.getID());
                    //send the source of the activity to the next page for certain action
                    intent.putExtra("activity", "FROM_RECIPE_SEARCH");
                    Log.i("recipeID", recipe.getID());
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    try {
                        pendingIntent.send();
                    } catch (PendingIntent.CanceledException e) {
                        e.printStackTrace();
                    }
                }
            });

        }


        //implement reciperowview interface to set the image in each row
        @Override
        public void setRowViewImage(String imageLink) {

            Picasso.with(context)
                .load(imageLink)
                .error(R.drawable.common_full_open_on_phone)
                .fit()
                .into(recipeImage);

        }

        //implement the reciperowview interface to set the publisher name in each row
        @Override
        public void setRowViewPublisher(String publisher) {
            txtRecipePublisher.setText(publisher);
        }

        //implement the reciperowview interface to set the title name in each row
        @Override
        public void setRowViewTitle(String title) {
            txtRecipeTitle.setText(title);
        }
    }
}
