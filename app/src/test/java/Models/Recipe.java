package Models;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private ArrayList<Ingredient> ingredients;
    private int servings;   // how many people can eat from this recipe
    private float duration; // how long to cook meal
    private Bitmap image; // might change type
}


