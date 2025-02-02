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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.slylamb.pocketcuisine.Presenters.FavouritePageActivityPresenter;
import com.slylamb.pocketcuisine.R;
import com.squareup.picasso.Picasso;

public class FavouritePageRecyclerViewAdapter extends RecyclerView.Adapter<FavouritePageRecyclerViewAdapter.ViewHolder>{

    private final FavouritePageActivityPresenter presenter;
    private Context context;

    public FavouritePageRecyclerViewAdapter(FavouritePageActivityPresenter presenter,Context context){
        this.context = context;
        this.presenter = presenter;
    }

    // inflate the viewholder with the xml layout
    @Override
    public FavouritePageRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_favourite_page_row,viewGroup,false);
        return new ViewHolder(view,context);
    }

    // inflate the viewholder with the view and actual data in each row
    @Override
    public void onBindViewHolder( FavouritePageRecyclerViewAdapter.ViewHolder viewHolder, int position) {

        presenter.onBindRecipeRowViewAtPosition(position,viewHolder);
        Log.d("onBindViewHolder","onBindViewHolder has been called");

    }

    //get the number of items
    @Override
    public int getItemCount() {
        return presenter.getRecipesRowsCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements FavouritePageActivityPresenter.RecipeRowView {
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
                    /*

                    LINK TO RECIPE ACTIVITY NOT WORKING

                    Log.d("itemview","itemview has been clicked");
                    Recipe recipe = presenter.getRecipeList().get(getAdapterPosition());
                    Intent intent = new Intent(context, RecipeActivity.class);
                    intent.putExtra("recipeID",recipe.getID());
                    intent.putExtra("activity", "FROM_FAVORITES");
                    Log.i("recipeIDdb", recipe.getID());
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    try {
                        pendingIntent.send();
                    } catch (PendingIntent.CanceledException e) {
                        e.printStackTrace();
                    }
                    */

                }
            });
        }


        //set the image of each row in the view holder
        @Override
        public void setRowViewImage(String imageLink) {

            Picasso.with(context)
                    .load(imageLink)
                    .error(R.drawable.common_full_open_on_phone)
                    .fit()
                    .into(recipeImage);

        }

        //set the publisher of each row in the view holder
        @Override
        public void setRowViewPublisher(String publisher) {
            txtRecipePublisher.setText(publisher);
        }

        //set the view title of each row in the view holder
        @Override
        public void setRowViewTitle(String title) {
            txtRecipeTitle.setText(title);
        }
    }
}
