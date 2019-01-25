package com.slylamb.pocketcuisine.Models;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.slylamb.pocketcuisine.R;

public class User {
        private ArrayList<ShoppingList> shoppingLists;
    private ArrayList<Recipe> ownRecipes;
    private ArrayList<Recipe> cookedRecipes;
    private ArrayList<Recipe> favorites;
    private int theme;  // which theme did the user choose? Might change type
    private MealPlanner planner;
    private String email;
    private String password;
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private FirebaseUser user;


    public User(){

    }

    public User(String email,String password){
        this.email = email;
        this.password = password;
    }

    public FirebaseUser getFirebaseUser(){return this.user;}

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void validateEmail(String email){

        if (TextUtils.isEmpty(email)) {

        } else {
            //mEmailField.setError(null);
            this.email = email;
        }


    }

    public void validatePassword(String password){
        if (TextUtils.isEmpty(password)) {

        } else {
            //mEmailField.setError(null);
            this.password = password;
        }


    }

    private void createAccount(String email, String password) {

        // What is Executor this??
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
//        if (!validateForm()) {
//            return;
//        }

        //showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
//                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            Log.d(TAG, "task is not successful");
//                            mStatusTextView.setText(R.string.auth_failed);
                        }


                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

//        private void signOut() {
//        mAuth.signOut();
//        //updateUI(null);
//    }


    // GABRIEL METHODS
    // Check if recipe passed on is in this users favorites list
   public boolean hasFavorite(Recipe recipe) {
        int total = favorites.size();
        // Go thru all favorites
        for (int i = 0; i < total; i++) {
            // If any matches the recipe passed on, return true, otherwise, false
            if (recipe.getName().equals(favorites.get(i).getName())) {
                return true;
            }
        }
        return false;
   }
   // Check if recipe passed on is in the cooked list
   public boolean hasCooked(Recipe recipe) {
       int total = cookedRecipes.size();
       // Go thru all favorites
       for (int i = 0; i < total; i++) {
           // If any matches the recipe passed on, return true, otherwise, false
           if (recipe.getName().equals(cookedRecipes.get(i).getName())) {
               return true;
           }
       }
       return false;
   }
   // Add recipe to user's favorites list
    public void addFavorite(Recipe recipe) {
        favorites.add(recipe);
    }
    // Add recipe to user's cooked recipes list
    public void addCooked(Recipe recipe) {
        cookedRecipes.add(recipe);
    }
    // Delete recipe from users list of favorites
    public void deleteFavorite(Recipe recipe) {
        // Todo: add try and catch as there might be no recipe in favorites to remove
        favorites.remove(recipe);
    }
    // Delete recipe from users list of cooked recipes
    public void deleteCooked(Recipe recipe) {
        // Todo: add try and catch as there might be no recipe in cooked to remove
        cookedRecipes.remove(recipe);
    }
    // Add planned meal to users meal planner
    public void addMealToMealPlanner(PlannedMeal meal) {
        planner.addMeal(meal);
    }
    // Add shopping list to users shopping lists
    public void addShoppingList(ShoppingList list) {
        shoppingLists.add(list);
    }
}