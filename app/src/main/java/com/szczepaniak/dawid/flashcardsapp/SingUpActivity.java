package com.szczepaniak.dawid.flashcardsapp;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SingUpActivity extends AppCompatActivity {

    private LinearLayout menuLayout;
    private TextInputEditText emailText;
    private TextInputLayout emailInputLayout;
    private TextInputEditText passText;
    private TextInputLayout passInputLayout;
    private TextInputEditText confPassText;
    private TextInputLayout confPassInputLayout;

    private Button singUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        menuLayout = findViewById(R.id.menuLayout);
        emailText = findViewById(R.id.emailText);
        emailInputLayout = findViewById(R.id.emailField);
        passText = findViewById(R.id.passwordText);
        passInputLayout = findViewById(R.id.passwordField);
        confPassText = findViewById(R.id.confirmPasswordText);
        confPassInputLayout = findViewById(R.id.confirmPasswordField);
        singUpButton = findViewById(R.id.singUpButton);

        TextView login = findViewById(R.id.loginText);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        singUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

    }


    void signUp(){

        String email = emailText.getText().toString();
        String password = passText.getText().toString();
        String confPassword = confPassText.getText().toString();

        emailInputLayout.setErrorEnabled(false);
        passInputLayout.setErrorEnabled(false);
        confPassInputLayout.setErrorEnabled(false);

        if(email.equals("")){
            emailInputLayout.setError("You need to enter a email");
        }
        else if(password.length() < 8){
            passInputLayout.setError("Min 8 chars");
        }
        else if(!password.equals(confPassword)){
            confPassInputLayout.setError("Wrong confirm password");
        }else{
            nicknamePopup();
        }
    }


    private void nicknamePopup()
    {

        PopUpBlur nicknamePopUp;
        View popUpView = getLayoutInflater().inflate(R.layout.nickname_layout,
                null);
        nicknamePopUp = new PopUpBlur(popUpView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, true, this);
        nicknamePopUp.setAnimationStyle(android.R.style.Animation_Dialog);
        View window = findViewById(R.id.container);
        window.setDrawingCacheEnabled(true);
        Bitmap screenShot = nicknamePopUp.blur(Bitmap.createBitmap(window.getDrawingCache()), 25);
        window.setDrawingCacheEnabled(false);
        Drawable blurBitmap = new BitmapDrawable(getResources(), screenShot);

        nicknamePopUp.showAtLocation(popUpView, Gravity.CENTER, 0, 0);

        ConstraintLayout background = popUpView.findViewById(R.id.background);
        background.setBackground(blurBitmap);

    }

}
