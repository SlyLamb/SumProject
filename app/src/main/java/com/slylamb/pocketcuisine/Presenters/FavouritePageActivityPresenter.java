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
    private String url;
    private Recipe recipe;
    private RequestQueue queue;
    private Context context;
    private RecyclerView recyclerView;
    private View view;

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public FavouritePageActivityPresenter(View view, Context context){

        this.view = view;
        this.context = context;
        recipeList = new ArrayList<>();

    }

    public void onBindRecipeRowViewAtPosition(int position, FavouritePageActivityPresenter.RecipeRowView rowView) {

        Recipe recipe = recipeList.get(position);
        rowView.setRowViewImage(recipe.getImageLink());
        Log.d("setRowViewImage",recipe.getImageLink());
        rowView.setRowViewPublisher(recipe.getPublisher());
        rowView.setRowViewTitle(recipe.getTitle());

    }

    public int getRecipesRowsCount(){
        Log.d("recipelist",String.valueOf(recipeList.size()));
        return recipeList.size();
    }

    public List<Recipe> getRecipes(){

        List<Recipe>newRecipeList = new ArrayList<>();
        db = new DataBaseHandler(context);
        Log.i("favAct","about to call db method");
        newRecipeList = db.getALLFavouriteRecipesFromFavouriteRecipeTB();
        view.refreshRecipeList();
        view.setRecyclerViewAdapter();
        return newRecipeList;
    }

    public void getRecipesList(){
        this.recipeList = getRecipes();
        Log.d("recipeListSize",String.valueOf(recipeList.size()));
    }


    public interface RecipeRowView{
        void setRowViewImage(String imageLink);
        void setRowViewPublisher(String publisher);
        void setRowViewTitle(String title);
    }

    public interface View{
        void refreshRecipeList();
        void setRecyclerViewAdapter();
    }
}
