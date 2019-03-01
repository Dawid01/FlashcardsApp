package com.szczepaniak.dawid.flashcardsapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class FlashcardActivity extends AppCompatActivity {

    private ArrayList<FlashcardItem> flashcardItems;
    private LinearLayout flashcardsItemLayout;
    private int flashcardIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        flashcardItems = new ArrayList<>();
        flashcardsItemLayout = findViewById(R.id.flashcardLayout);
        flashcardItems.add(new FlashcardItem("Key", "Klucz", "Don't forgot to take the key!", "Nie zapomnij wziąć klucz!"));
        flashcardItems.add(new FlashcardItem("Car", "Samochód", "Yesterday I bought a new car.", "Wczoraj kupiłem nowy samochód."));
        flashcardItems.add(new FlashcardItem("Phone", "Telefon", "Yesterday I bought a new Phone.", "Wczoraj kupiłem nowy telefon."));
        flashcardItems.add(new FlashcardItem("Computer", "Komputer", "Yesterday I bought a new Computer.", "Wczoraj kupiłem nowy komputer."));
        flashcardItems.add(new FlashcardItem("Tree", "Drzewo", "The lumberjack cut the tree.", "Drwal ściął drzewo."));
        flashcardItems.add(new FlashcardItem("Chives", "Szczypiorek", "These chives are too dry.", "Ten szczypiorek jest zbyt suchy."));
//        flashcardItems.add(new FlashcardItem("To take a closer look", "Przyjrzeć się czemuś", "When you take a closer look it tums out he's not that happy", "Kiedy się bliżej przyjrzysz, okazuje się, że nie jest taki szczęśliwy"));

        loadFlashcard(flashcardIndex);

        Button know = findViewById(R.id.knowButton);
        Button dontKnow = findViewById(R.id.dontKnowButton);

        know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFlashcard(++flashcardIndex);
                if(flashcardIndex < 0){
                    flashcardIndex = 0;
                }else if(flashcardIndex > flashcardItems.size()-1){
                    flashcardIndex = flashcardItems.size()-1;
                }
            }
        });

        dontKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFlashcard(--flashcardIndex);
                if(flashcardIndex < 0){
                    flashcardIndex = 0;
                }else if(flashcardIndex > flashcardItems.size()-1){
                    flashcardIndex = flashcardItems.size()-1;
                }
            }
        });

        Button addFlashcardButton = findViewById(R.id.addFlashcardButton);

        addFlashcardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    void loadFlashcard(int i) {

        try {
            FlashcardItem flashcardItem = flashcardItems.get(i);
            flashcardsItemLayout.removeAllViews();
            LayoutInflater inflater = LayoutInflater.from(FlashcardActivity.this);
            ConstraintLayout flashcard = (ConstraintLayout) inflater.inflate(R.layout.flashcard, null, false);

            final ConstraintLayout firstFlashcard = flashcard.findViewById(R.id.firstFlashcard);
            final ConstraintLayout secondFlashcard = flashcard.findViewById(R.id.secondFlashcard);
            TextView firstDescription = flashcard.findViewById(R.id.description);
            TextView secondDescription = flashcard.findViewById(R.id.description2);
            TextView firstWord = flashcard.findViewById(R.id.firstWord);
            TextView secondWord = flashcard.findViewById(R.id.secondWord);
            TextView firstIndex = flashcard.findViewById(R.id.firstIndex);
            TextView secondIndex = flashcard.findViewById(R.id.secondIndex);

            String index = (flashcardItems.indexOf(flashcardItem) + 1) + "/" + flashcardItems.size();
            firstIndex.setText(index);
            secondIndex.setText(index);

            firstWord.setText(flashcardItem.getFirstWord());
            secondWord.setText(flashcardItem.getSecondWord());
            firstDescription.setText(flashcardItem.getPaintedText(flashcardItem.getFirstDescription(), flashcardItem.getFirstWord()));
            secondDescription.setText(flashcardItem.getPaintedText(flashcardItem.getSecondDescription(), flashcardItem.getSecondWord()));


            flashcard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firstFlashcard.animate().rotationX(90).setDuration(200).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            firstFlashcard.setVisibility(View.GONE);
                            secondFlashcard.setRotationX(-90);
                            secondFlashcard.setVisibility(View.VISIBLE);
                            secondFlashcard.animate().rotationX(0).setDuration(200).setListener(null);
                        }
                    });
                }
            });

            secondFlashcard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    secondFlashcard.animate().rotationX(-90).setDuration(200).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            secondFlashcard.setVisibility(View.GONE);
                            firstFlashcard.setRotationX(90);
                            firstFlashcard.setVisibility(View.VISIBLE);
                            firstFlashcard.animate().rotationX(0).setDuration(200).setListener(null);
                        }
                    });
                }
            });

            flashcardsItemLayout.addView(flashcard);

        }catch (IndexOutOfBoundsException e){}
    }

}
