package com.slylamb.pocketcuisine.Presenters;

import com.slylamb.pocketcuisine.Models.User;


public class RegisterPageActivityPresenter {

    private User user;
    private View view;

    public RegisterPageActivityPresenter(View view) {
        this.user = new User();
        this.view = view;
    }

    public interface View{

    }

}
