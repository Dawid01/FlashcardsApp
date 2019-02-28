package com.szczepaniak.dawid.flashcardsapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class FlashcardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        final ConstraintLayout flashcard = findViewById(R.id.firstFlashcard);
        final ConstraintLayout flashcard2 = findViewById(R.id.secondFlashcard);

        flashcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcard.animate().rotationX(90).setDuration(200).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        flashcard.setVisibility(View.GONE);
                        flashcard2.setRotationX(-90);
                        flashcard2.setVisibility(View.VISIBLE);
                        flashcard2.animate().rotationX(0).setDuration(200).setListener(null);
                    }
                });
            }
        });

        flashcard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcard2.animate().rotationX(-90).setDuration(200).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        flashcard2.setVisibility(View.GONE);
                        flashcard.setRotationX(90);
                        flashcard.setVisibility(View.VISIBLE);
                        flashcard.animate().rotationX(0).setDuration(200).setListener(null);
                    }
                });
            }
        });


    }
}
