package com.slylamb.pocketcuisine.Models;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class RegisteredUser  {
    private String email;
    private String password;
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private FirebaseUser user;
    private MealPlanner schedule;

    public RegisteredUser(){

    }

    public RegisteredUser(String email,String password){
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
}