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


}