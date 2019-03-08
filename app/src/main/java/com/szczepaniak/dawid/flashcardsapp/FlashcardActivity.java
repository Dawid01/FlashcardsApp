package com.szczepaniak.dawid.flashcardsapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class FlashcardActivity extends AppCompatActivity {

    private ArrayList<FlashcardItem> flashcardItems;
    private ArrayList<FlashcardItem> currentFlashcardItems;

    private LinearLayout flashcardsItemLayout;
    private TabLayout tabLayout;
    private int flashcardIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);
        tabLayout = findViewById(R.id.tabLayout);
        flashcardItems = new ArrayList<>();
        flashcardsItemLayout = findViewById(R.id.flashcardLayout);
        flashcardItems.add(new FlashcardItem("Key", "Klucz", "Don't forgot to take the key!", "Nie zapomnij wziąć klucz!", true));
        flashcardItems.add(new FlashcardItem("Car", "Samochód", "Yesterday I bought a new car.", "Wczoraj kupiłem nowy samochód.", false));
        flashcardItems.add(new FlashcardItem("Phone", "Telefon", "Yesterday I bought a new Phone.", "Wczoraj kupiłem nowy telefon.", true));
        flashcardItems.add(new FlashcardItem("Computer", "Komputer", "Yesterday I bought a new Computer.", "Wczoraj kupiłem nowy komputer.", false));
        flashcardItems.add(new FlashcardItem("Tree", "Drzewo", "The lumberjack cut the tree.", "Drwal ściął drzewo.", true));
        flashcardItems.add(new FlashcardItem("Chives", "Szczypiorek", "These chives are too dry.", "Ten szczypiorek jest zbyt suchy.", false));
//        flashcardItems.add(new FlashcardItem("To take a closer look", "Przyjrzeć się czemuś", "When you take a closer look it tums out he's not that happy", "Kiedy się bliżej przyjrzysz, okazuje się, że nie jest taki szczęśliwy"));
        currentFlashcardItems = new ArrayList<>();
        currentFlashcardItems = flashcardItems;
        loadFlashcard(flashcardIndex);

        tabLayout.setOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                String tabName = tab.getText().toString();

                switch (tabName){

                    case "all":
                        currentFlashcardItems = flashcardItems;
                        break;
                    case "lerned":
                        currentFlashcardItems = new ArrayList<>();
                        for(FlashcardItem flashcard : flashcardItems){
                            if(flashcard.isKnow()){
                                currentFlashcardItems.add(flashcard);
                            }
                        }

                        break;
                    case "unlerned":
                        currentFlashcardItems = new ArrayList<>();
                        for(FlashcardItem flashcard : flashcardItems){
                            if(!flashcard.isKnow()){
                                currentFlashcardItems.add(flashcard);
                            }
                        }
                        break;
                }

                loadFlashcard(0);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Button know = findViewById(R.id.knowButton);
        Button dontKnow = findViewById(R.id.dontKnowButton);

        know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFlashcard(++flashcardIndex);
                if(flashcardIndex < 0){
                    flashcardIndex = 0;
                }else if(flashcardIndex > currentFlashcardItems.size()-1){
                    flashcardIndex = currentFlashcardItems.size()-1;
                }
            }
        });

        dontKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFlashcard(--flashcardIndex);
                if(flashcardIndex < 0){
                    flashcardIndex = 0;
                }else if(flashcardIndex > currentFlashcardItems.size()-1){
                    flashcardIndex = currentFlashcardItems.size()-1;
                }
            }
        });

        Button addFlashcardButton = findViewById(R.id.addFlashcardButton);

        addFlashcardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final PopUpBlur addFlashcardsPopUp;
                View popUpView = getLayoutInflater().inflate(R.layout.add_flashcard_popup,
                        null);
                addFlashcardsPopUp = new PopUpBlur(popUpView, LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT, true, FlashcardActivity.this);
                addFlashcardsPopUp.setAnimationStyle(android.R.style.Animation_Dialog);
                View window = findViewById(R.id.container);
                window.setDrawingCacheEnabled(true);
                Bitmap screenShot = addFlashcardsPopUp.blur(Bitmap.createBitmap(window.getDrawingCache()), 25);
                window.setDrawingCacheEnabled(false);
                Drawable blurBitmap = new BitmapDrawable(getResources(), screenShot);
                addFlashcardsPopUp.showAtLocation(popUpView, Gravity.CENTER, 0, 0);
                ConstraintLayout background = popUpView.findViewById(R.id.background);
                background.setBackground(blurBitmap);
            }
        });

    }


    void loadFlashcard(int i) {

        try {
            FlashcardItem flashcardItem = currentFlashcardItems.get(i);
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

            String index = (currentFlashcardItems.indexOf(flashcardItem) + 1) + "/" + currentFlashcardItems.size();
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
