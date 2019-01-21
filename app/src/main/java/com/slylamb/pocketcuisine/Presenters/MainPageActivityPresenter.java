package com.slylamb.pocketcuisine.Presenters;

import com.slylamb.pocketcuisine.Models.User;

public class MainPageActivityPresenter {
    private User user;
    private View view;

    public MainPageActivityPresenter(View view){
        this.user = new User();
        this.view = view;
    }

    public void updateEmail(String email){
        user.validateEmail(email);

    }

    public void updatePassword(String password){
        user.validatePassword(password);
    }

    public void login(){
        user.signIn(user.getEmail(),user.getPassword());
        if(user.getFirebaseUser()!=null) {
            view.showLoginSuccessStatus();

        }
    }


    public interface View{
        void showLoginSuccessStatus();

    }

}