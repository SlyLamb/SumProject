package com.slylamb.pocketcuisine.Views;

import android.content.Context;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;

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

                // get the search keyword from user input
                String keyword = txtSearchField.getText().toString();
                Log.d("keyword",keyword);

                //set the full url based on the keyword
                presenter.setUrl(keyword);

                //instantiate the adapter with the presenter
                recipeRecyclerViewAdapter = new RecipeRecyclerViewAdapter(presenter, v.getContext());

                //get a list of recipe list from the server through api call based on the keyword
                presenter.getRecipesList();

                //inform the adapter that the data has been changed
                recipeRecyclerViewAdapter.notifyDataSetChanged();


            }
        });


    }


    //implement the presenter view interface so that the presenter can gain access to
    //the adapter
    @Override
    public void refreshRecipeList() {
        recipeRecyclerViewAdapter.notifyDataSetChanged();
        Log.d("refreshRecipeList","refreshRecipeList has been called");

    }

    //implement the presenter view interface so that the presenter can gain access to
    //the recycler view
    @Override
    public void setRecyclerViewAdapter() {
        recyclerView.setAdapter(recipeRecyclerViewAdapter);

    }
}
