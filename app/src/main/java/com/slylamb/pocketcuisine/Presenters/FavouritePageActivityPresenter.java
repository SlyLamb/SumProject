package com.slylamb.pocketcuisine.Presenters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.android.volley.RequestQueue;
import com.slylamb.pocketcuisine.Data.DataBaseHandler;
import com.slylamb.pocketcuisine.Models.Recipe;
import java.util.ArrayList;
import java.util.List;

public class FavouritePageActivityPresenter {

    private DataBaseHandler db;
    private List<Recipe> recipeList;
    private Context context;
    private View view;

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    //constructor
    public FavouritePageActivityPresenter(View view, Context context){

        this.view = view;
        this.context = context;
        recipeList = new ArrayList<>();

    }

    //implement onBindViewHolder for recipe row viewholder
    public void onBindRecipeRowViewAtPosition(int position, FavouritePageActivityPresenter.RecipeRowView rowView) {

        Recipe recipe = recipeList.get(position);
        rowView.setRowViewImage(recipe.getImageLink());
        Log.d("setRowViewImage",recipe.getImageLink());
        rowView.setRowViewPublisher(recipe.getPublisher());
        rowView.setRowViewTitle(recipe.getTitle());

    }

    //implement getItemCount
    public int getRecipesRowsCount(){
        Log.d("recipelist",String.valueOf(recipeList.size()));
        return recipeList.size();
    }

    //get the favourite recipes stored in the database and return a list of Recipe objects
    public List<Recipe> getRecipes(){

        List<Recipe>newRecipeList = new ArrayList<>();
        db = new DataBaseHandler(context);
        Log.i("favAct","about to call db method");
        newRecipeList = db.getALLFavouriteRecipesFromFavouriteRecipeTB();
        view.refreshRecipeList();
        view.setRecyclerViewAdapter();
        return newRecipeList;
    }

    // give recipeList filed the value returned from database
    public void getRecipesList(){
        this.recipeList = getRecipes();
        Log.d("recipeListSize",String.valueOf(recipeList.size()));
    }


    //interface for the recipe row view
    public interface RecipeRowView{
        void setRowViewImage(String imageLink);
        void setRowViewPublisher(String publisher);
        void setRowViewTitle(String title);
    }

    //interface for the view implemented by activity
    public interface View{
        void refreshRecipeList();
        void setRecyclerViewAdapter();
    }
}
