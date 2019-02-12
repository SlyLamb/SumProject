package com.slylamb.pocketcuisine.Views;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


import com.slylamb.pocketcuisine.Models.Recipe;
import com.slylamb.pocketcuisine.R;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.net.URLConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;



public class FavouritePageRecyclerViewAdapter extends RecyclerView.Adapter<FavouritePageRecyclerViewAdapter.ViewHolder>{

    private Context context;
    private List<Recipe> recipeList;

    public FavouritePageRecyclerViewAdapter(Context context,List<Recipe>recipes){
        this.context = context;
        recipeList = recipes;
    }

    @Override
    public FavouritePageRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_favourite_page_row,viewGroup,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder( FavouritePageRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        Recipe recipe = recipeList.get(i);

        String imageLink = recipe.getImageLink();
        Log.d("downlandlink",imageLink);
        viewHolder.txtRecipeTitle.setText(recipe.getTitle());
        viewHolder.txtRecipePublisher.setText(recipe.getPublisher());

        Picasso.with(context)
                .load(imageLink)
                .error(R.drawable.common_full_open_on_phone)
                .fit()
                .into(viewHolder.recipeImage);

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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

                    Log.d("itemview","itemview has been clicked");
                    Recipe recipe = recipeList.get(getAdapterPosition());
                    Intent intent = new Intent(context, RecipeActivity.class);
                    intent.putExtra("recipeIDdb",recipe.getID());
                    context.startActivity(intent);

                }
            });

        }

        @Override
        public void onClick(View v) {

        }
    }
}
