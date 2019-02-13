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

import com.slylamb.pocketcuisine.Models.Recipe;
import com.slylamb.pocketcuisine.R;

public class SearchRecipeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecipeRecyclerViewAdapter recipeRecyclerViewAdapter;
    private List<Recipe> recipeList;
    private RequestQueue queue;
    private ImageButton btnSearch;
    private TextView txtSearchField;
    private final String baseUrl = "https://www.food2fork.com/api/search?key=";
    //private final String key = "4d78d05d9f20215c272d04f6974c04db"; //50 calls limit per day
    //private final String key = "f5b73a553a6a92ccfabca695807bdaeb";//50 calls limit per day
    private final String key = "2066d15049b02e6f8ea0b11f77f9fd30";//50 calls limit per day
    //private final String key = "3092e7c11f93c302283e456ed92207e4"; //50 calls limit per day



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipesearch);

        queue = Volley.newRequestQueue(this);

        btnSearch = findViewById(R.id.btnSearch);
        txtSearchField = findViewById(R.id.txtSearchField);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recipeList = new ArrayList<>();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(txtSearchField.getWindowToken(), 0);

                String url = baseUrl+key+"&q="+txtSearchField.getText().toString();
                recipeList = getRecipes(url);
                Log.d("url",url);
                recipeRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        recipeRecyclerViewAdapter = new RecipeRecyclerViewAdapter(this, recipeList );
        recyclerView.setAdapter(recipeRecyclerViewAdapter);
        recipeRecyclerViewAdapter.notifyDataSetChanged();

    }

    public List<Recipe> getRecipes(String url) {
        Log.d("getrecipe","getrecipecalled");
        recipeList.clear();

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try{

                    Log.d("jsonrequest","jsonreuqestcalled");
                    JSONArray recipesArray = response.getJSONArray("recipes");

                    Log.d("recipesArray",recipesArray.toString());
                    for (int i = 0; i < recipesArray.length(); i++) {

                        JSONObject recipeObj = recipesArray.getJSONObject(i);

                        Recipe recipe = new Recipe();
                        recipe.setImageLink(recipeObj.getString("image_url"));
                        recipe.setTitle(recipeObj.getString("title"));
                        Log.d("imageLink",recipe.getImageLink());
                        recipe.setPublisher(recipeObj.getString("publisher"));
                        recipe.setID(recipeObj.getString("recipe_id"));
                        recipeList.add(recipe);

                    }
                    recipeRecyclerViewAdapter.notifyDataSetChanged();

                }catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub

            }
        });

        queue.add(request);

        return recipeList;

    }
}
