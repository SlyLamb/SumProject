package com.slylamb.pocketcuisine.Views;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.slylamb.pocketcuisine.Data.DataBaseHandler;
import com.slylamb.pocketcuisine.Models.Recipe;
import com.slylamb.pocketcuisine.Presenters.FavouritePageActivityPresenter;
import com.slylamb.pocketcuisine.R;

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

        presenter.getRecipesList();

        favouritePageRecyclerViewAdapter.notifyDataSetChanged();

    }


    @Override
    public void refreshRecipeList() {
        favouritePageRecyclerViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void setRecyclerViewAdapter() {
        recyclerView.setAdapter(favouritePageRecyclerViewAdapter);

    }
}
