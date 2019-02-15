package com.slylamb.pocketcuisine.Views;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.slylamb.pocketcuisine.Presenters.FavouritePageActivityPresenter;
import com.slylamb.pocketcuisine.R;


public class FavouritePageActivity extends PocketCuisineActivity implements FavouritePageActivityPresenter.View {

    private RecyclerView recyclerView;
    private FavouritePageActivityPresenter presenter;
    private FavouritePageRecyclerViewAdapter favouritePageRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_favourite_page);

        presenter = new FavouritePageActivityPresenter(this,this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        favouritePageRecyclerViewAdapter = new FavouritePageRecyclerViewAdapter(presenter,this);

        //to set the value of the recipeList field in presenter
        presenter.getRecipesList();

        //notify the adapter that the dataset has been changed
        favouritePageRecyclerViewAdapter.notifyDataSetChanged();

    }

    // implement the View interface in FavouritePageActivityPresenter so that the presenter has access
    //to recyclerview and view adapter
    @Override
    public void refreshRecipeList() {
        favouritePageRecyclerViewAdapter.notifyDataSetChanged();

    }

    // implement the View interface in FavouritePageActivityPresenter so that the presenter has access
    //to recyclerview and view adapter
    @Override
    public void setRecyclerViewAdapter() {
        recyclerView.setAdapter(favouritePageRecyclerViewAdapter);

    }
}
