package com.szczepaniak.dawid.flashcardsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    NavigationView drawer;
    TextView nicknameText;
    TextView emailText;
    LinearLayout flashcardsLayout;

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
        loadUser();
        loadFlashcards();
    }

    private void loadUser(){

        new NetworkManager(this, "/users", "content"){

            @Override
            void onDataGet(final JSONArray jsonArray, final Context context) {

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            JSONObject user = jsonArray.getJSONObject(0);
                            String name = user.getString("name");
                            String email = user.getString("email");
                            nicknameText.setText(name);
                            emailText.setText(email);
                        }catch (JSONException e){}
                    }
                });


            }
        }.execute();
    }

    private void loadFlashcards(){

        new NetworkManager(this, "/flashcards", "content"){

            @Override
            void onDataGet(final JSONArray jsonArray, final Context context) {

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            for(int i = 0; i < jsonArray.length(); i++) {
                                JSONObject flashcard = jsonArray.getJSONObject(i);
                                String title = flashcard.getString("title");
                                String description = flashcard.getString("description");
                                int flashcards = flashcard.getInt("flashcards");
                                int knowsFlashcards = flashcard.getInt("knowsFlashcards");

                                LayoutInflater inflater = LayoutInflater.from(context);
                                ConstraintLayout flashcardItem = (ConstraintLayout) inflater.inflate(R.layout.flashcards_list_item, null, false);
                                TextView titleText = flashcardItem.findViewById(R.id.name);
                                titleText.setText(title);
                                TextView descriptionText = flashcardItem.findViewById(R.id.description);
                                descriptionText.setText(description);
                                TextView flashcardsText = flashcardItem.findViewById(R.id.flashcards);
                                flashcardsText.setText("" + flashcards);
                                TextView knowsFlashcardsText = flashcardItem.findViewById(R.id.youKnow);
                                knowsFlashcardsText.setText("" + knowsFlashcards);
                                flashcardsLayout.addView(flashcardItem);

                            }
                        }catch (JSONException e){}
                    }
                });


            }
        }.execute();
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

    }

}


