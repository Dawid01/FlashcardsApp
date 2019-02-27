package com.szczepaniak.dawid.flashcardsapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    NavigationView drawer;
    TextView nicknameText;
    TextView emailText;
    LinearLayout flashcardsLayout;
    private ArrayList<Flashcard> flashcardArrayList;
    retrofit2.Call<FlashcardsList> flashcardsListCall;

    retrofit2.Call<Flashcard> flashcardCall;

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton addFlashcardsButton = findViewById(R.id.addFlashcardsButton);

        addFlashcardsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createFlashcardsPopUp();
            }
        });

        drawer = findViewById(R.id.drawer);

        View headerLayout = drawer.getHeaderView(0);
        nicknameText = headerLayout.findViewById(R.id.nickname);
        emailText = headerLayout.findViewById(R.id.email);
        flashcardsLayout = findViewById(R.id.flashcardsLayout);
        ApiService api = RetroClient.getApiService();
        flashcardsListCall = api.getFlashcards();
        //flashcardCall = api.getFlashcard(0L);
        //loadUser();
        loadFlashcards();
    }

    private void loadUser(){


    }

    private void loadFlashcards() {

        flashcardsLayout.removeAllViews();

        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Loading Data.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

//        flashcardCall.enqueue(new Callback<Flashcard>() {
//            @Override
//            public void onResponse(retrofit2.Call<Flashcard> call, Response<Flashcard> response) {
//                Log.i("flashcard", response.body().getTitle());
//                Log.i("flashcard", response.body().getDescription());
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<Flashcard> call, Throwable t) {
//                Log.e("flash","something happend", t);
//            }
//        });

        flashcardsListCall.enqueue(new Callback<FlashcardsList>() {
            @Override
            public void onResponse(retrofit2.Call<FlashcardsList> call, Response<FlashcardsList> response) {
                pDialog.dismiss();
                if (response.isSuccessful()) {

                    List<Flashcard> flashcardList = response.body().getFlashcardList();

                        for (int i = 0; i < flashcardList.size(); i++) {

                            Flashcard flashcard = flashcardList.get(i);
                            String title = flashcard.getTitle();
                            String description = flashcard.getDescription();
                            int flashcardsCount = flashcard.getFlashcards();
                            int knowsFlashcards = flashcard.getKnowsFlashcards();

                            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                            ConstraintLayout flashcardItem = (ConstraintLayout) inflater.inflate(R.layout.flashcards_list_item, null, false);
                            TextView titleText = flashcardItem.findViewById(R.id.name);
                            titleText.setText(title);
                            TextView descriptionText = flashcardItem.findViewById(R.id.description);
                            descriptionText.setText(description);
                            TextView flashcardsText = flashcardItem.findViewById(R.id.flashcards);
                            flashcardsText.setText("" + flashcardsCount);
                            TextView knowsFlashcardsText = flashcardItem.findViewById(R.id.youKnow);
                            knowsFlashcardsText.setText("" + knowsFlashcards);
                            flashcardsLayout.addView(flashcardItem);


                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<FlashcardsList> call, Throwable t) {
                pDialog.dismiss();
                Log.e("flash","something happend", t);
            }
        });
    }


    private void createFlashcardsPopUp()
    {
        PopUpBlur createFlashcardsPopUp;
        View popUpView = getLayoutInflater().inflate(R.layout.create_flashcards_popup,
                null);
        createFlashcardsPopUp = new PopUpBlur(popUpView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, true, this);
        createFlashcardsPopUp.setAnimationStyle(android.R.style.Animation_Dialog);
        View window = findViewById(R.id.container);
        window.setDrawingCacheEnabled(true);
        Bitmap screenShot = createFlashcardsPopUp.blur(Bitmap.createBitmap(window.getDrawingCache()), 25);
        window.setDrawingCacheEnabled(false);
        Drawable blurBitmap = new BitmapDrawable(getResources(), screenShot);
        createFlashcardsPopUp.showAtLocation(popUpView, Gravity.CENTER, 0, 0);
        ConstraintLayout background = popUpView.findViewById(R.id.background);
        background.setBackground(blurBitmap);

        final TextInputEditText title = popUpView.findViewById(R.id.titleText);
        final TextInputEditText description = popUpView.findViewById(R.id.descriptionText);
        Button create = popUpView.findViewById(R.id.nextButton);

        final Context context = this;

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

}


