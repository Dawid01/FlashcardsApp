package com.szczepaniak.dawid.flashcardsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity{

    LinearLayout menuLayout;
    private TextInputEditText emailText;
    private TextInputLayout emailInputLayout;
    private TextInputEditText passText;
    private TextInputLayout passInputLayout;
    private Button loginButton;
    ApiService apiService;
    private ProgressDialog pDialog;
    Singleton singleton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        menuLayout = findViewById(R.id.menuLayout);
        emailText = findViewById(R.id.emailText);
        emailInputLayout = findViewById(R.id.emailField);
        passText = findViewById(R.id.passwordText);
        passInputLayout = findViewById(R.id.passwordField);
        loginButton = findViewById(R.id.loginButton);
        apiService = RetroClient.getApiService();
        singleton = Singleton.getInstance();

        TextView singUp = findViewById(R.id.singUpText);
        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent singUpIntent = new Intent(LoginActivity.this, SingUpActivity.class);
                startActivity(singUpIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    void login(){

        String email = emailText.getText().toString();
        String password = passText.getText().toString();

        emailInputLayout.setErrorEnabled(false);
        passInputLayout.setErrorEnabled(false);


        if(email.equals("")){
            emailInputLayout.setError("You need to enter a email");
        }
        else if(password.equals("")){
            passInputLayout.setError("You need to enter a password");

        }else{

            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            String base = email + ":" + password;
            String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
            Call<User> userCall = apiService.loginUser(authHeader);

            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    pDialog.dismiss();
                    if(response.isSuccessful()) {
                        singleton.setCurrentUser(response.body());
                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    pDialog.dismiss();
                    Log.e("login","ERROR", t);
                }
            });

            //finish();
        }
    }

}
