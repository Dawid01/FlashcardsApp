package com.szczepaniak.dawid.flashcardsapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogEngine;

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

        View window = findViewById(R.id.container);
        window.setDrawingCacheEnabled(true);
        Bitmap screenShot = blur(Bitmap.createBitmap(window.getDrawingCache()), 25);
        window.setDrawingCacheEnabled(false);
        Drawable blurBitmap = new BitmapDrawable(getResources(), screenShot);

        PopupWindow nicknamePopUp;
        View popUpView = getLayoutInflater().inflate(R.layout.nickname_layout,
                null);
        nicknamePopUp = new PopupWindow(popUpView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, true);
        nicknamePopUp.setAnimationStyle(android.R.style.Animation_Dialog);
        nicknamePopUp.showAtLocation(popUpView, Gravity.CENTER, 0, 0);

        ConstraintLayout background = popUpView.findViewById(R.id.background);
        background.setBackground(blurBitmap);

        BlurDialogEngine mBlurEngine;
        mBlurEngine = new BlurDialogEngine(SingUpActivity.this);
        mBlurEngine.setBlurRadius(8);
        mBlurEngine.setDownScaleFactor(8f);
        mBlurEngine.debug(true);
        mBlurEngine.setBlurActionBar(true);
        mBlurEngine.setUseRenderScript(true);

    }

    public Bitmap blur(Bitmap image, int radius) {
        if (null == image) return null;
        Bitmap outputBitmap = Bitmap.createBitmap(image);
        final RenderScript renderScript = RenderScript.create(this);
        Allocation tmpIn = Allocation.createFromBitmap(renderScript, image);
        Allocation tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        theIntrinsic.setRadius(radius);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }

}
