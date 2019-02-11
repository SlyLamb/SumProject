package com.slylamb.pocketcuisine.Presenters;

import com.slylamb.pocketcuisine.Models.RegisteredUser;


public class RegisterPageActivityPresenter {

    private RegisteredUser user;
    private View view;

    public RegisterPageActivityPresenter(View view) {
        this.user = new RegisteredUser();
        this.view = view;
    }

    public interface View{

    }

}
