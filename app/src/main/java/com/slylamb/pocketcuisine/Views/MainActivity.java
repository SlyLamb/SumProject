package com.slylamb.pocketcuisine.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.slylamb.pocketcuisine.Presenters.MainPageActivityPresenter;
import com.slylamb.pocketcuisine.R;

//import com.slylamb.pocketcuisine.RegisterPageActivityPresenter;

public class MainActivity extends AppCompatActivity implements MainPageActivityPresenter.View {

    private MainPageActivityPresenter presenter;
    private Button btnLogin;
    private TextView txtLoginSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       presenter = new MainPageActivityPresenter( this);
        EditText txtEmail = findViewById(R.id.txtEmail);
        EditText txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
         txtLoginSuccess = findViewById(R.id.txtloginStatus);
        //btnLogin.setOnClickListener(this);

        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                presenter.updateEmail(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.updatePassword(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.login();

            }
        });



    }

    @Override
    public void showLoginSuccessStatus() {
        txtLoginSuccess.setText("LoginSuccess!");
    }

}