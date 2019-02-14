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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


import com.slylamb.pocketcuisine.Models.Recipe;
import com.slylamb.pocketcuisine.Presenters.RecipeSearchActivityPresenter;
import com.slylamb.pocketcuisine.R;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.net.URLConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;



public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.ViewHolder>{

    private final RecipeSearchActivityPresenter presenter;

    private Context context;

    public RecipeRecyclerViewAdapter(RecipeSearchActivityPresenter presenter,Context context){
        this.context = context;
        this.presenter = presenter;
        Log.d("RecipeRecyclerView","RecipeRecyclerViewAdapter constructor has been called");
    }

    @Override
    public RecipeRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_row,viewGroup,false);
        Log.d("onCreateViewHolder","onCreateViewHolder has been called");
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder( RecipeRecyclerViewAdapter.ViewHolder viewHolder, int position) {

        presenter.onBindRecipeRowViewAtPosition(position,viewHolder);
        Log.d("onBindViewHolder","onBindViewHolder has been called");

    }

    @Override
    public int getItemCount() {
        //return presenter.getRecipesRowsCount();
       // Log.d("getItemCount",String.valueOf(presenter.getRecipesRowsCount()));

        return 5;
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
                    Intent intent = new Intent(context, RecipeActivity.class);
                    intent.putExtra("recipeID",recipe.getID());
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


        @Override
        public void setRowViewImage(String imageLink) {

            Picasso.with(context)
                .load(imageLink)
                .error(R.drawable.common_full_open_on_phone)
                .fit()
                .into(recipeImage);

        }

        @Override
        public void setRowViewPublisher(String publisher) {
            txtRecipePublisher.setText(publisher);
        }

        @Override
        public void setRowViewTitle(String title) {
            txtRecipeTitle.setText(title);
        }
    }
}
