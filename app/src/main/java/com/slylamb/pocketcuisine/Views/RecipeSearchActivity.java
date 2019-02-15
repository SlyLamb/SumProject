package com.slylamb.pocketcuisine.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
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

import com.slylamb.pocketcuisine.Models.Recipe;
import com.slylamb.pocketcuisine.Presenters.RecipeSearchActivityPresenter;
import com.slylamb.pocketcuisine.R;

public class RecipeSearchActivity extends PocketCuisineActivity implements RecipeSearchActivityPresenter.View{

    private RecyclerView recyclerView;
    private RecipeRecyclerViewAdapter recipeRecyclerViewAdapter;
    private RecipeSearchActivityPresenter presenter;
    private ImageButton btnSearch;
    private TextView txtSearchField;
    private final String baseUrl = "https://www.food2fork.com/api/search?key=";
    //private final String key = "4d78d05d9f20215c272d04f6974c04db"; //50 calls limit per day
    private final String key = "f5b73a553a6a92ccfabca695807bdaeb";//50 calls limit per day
   // private final String key = "bba82bc3b0c0d5036c7d521014b02b62";//50 calls limit per day
    //private final String key = "2066d15049b02e6f8ea0b11f77f9fd30";//50 calls limit per day
    //private final String key = "3092e7c11f93c302283e456ed92207e4"; //50 calls limit per day
   // private final String key = "47dfa0226334502d4bce0a4cb3ba8863";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipesearch);


        btnSearch = findViewById(R.id.btnSearch);
        txtSearchField = findViewById(R.id.txtSearchField);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        presenter = new RecipeSearchActivityPresenter(this,this);



        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(txtSearchField.getWindowToken(), 0);

                String keyword = txtSearchField.getText().toString();
                Log.d("keyword",keyword);

                presenter.setUrl(keyword);

                recipeRecyclerViewAdapter = new RecipeRecyclerViewAdapter(presenter, v.getContext());

                presenter.getRecipesList();

                recipeRecyclerViewAdapter.notifyDataSetChanged();


            }
        });


    }


    @Override
    public void refreshRecipeList() {

        recipeRecyclerViewAdapter.notifyDataSetChanged();
        Log.d("refreshRecipeList","refreshRecipeList has been called");

    }

    @Override
    public void setRecyclerViewAdapter() {
        recyclerView.setAdapter(recipeRecyclerViewAdapter);

    }
}
