package com.szczepaniak.dawid.flashcardsapp;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;



public class LoginActivity extends AppCompatActivity{

    LinearLayout menuLayout;
    private TextInputEditText emailText;
    private TextInputLayout emailInputLayout;
    private TextInputEditText passText;
    private TextInputLayout passInputLayout;
    private Button loginButton;

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

            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(mainIntent);
            //finish();
        }
    }

}
