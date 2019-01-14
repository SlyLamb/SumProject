package com.slylamb.pocketcuisine.Views;

import android.graphics.Bitmap;
import com.slylamb.pocketcuisine.Models.Ingredient;
import java.util.ArrayList;

public interface RecipeView {

    // Set recipe details in the view
    void setRecipeDetails(Bitmap image, String name, float duration, int servings, ArrayList<Ingredient> ingredients);
    // Set button look, different if already picked
    void setButton(boolean picked, String button);

}