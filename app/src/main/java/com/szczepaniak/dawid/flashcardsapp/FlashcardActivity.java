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
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlashcardActivity extends AppCompatActivity {

    private List<FlashcardItem> flashcardItems;
    private List<FlashcardItem> currentFlashcardItems;

    private LinearLayout flashcardsItemLayout;
    private TabLayout tabLayout;
    private int flashcardIndex = 0;
    private ApiService api;
    private Long flashcardId;
    private int currentFlashcardIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);
        tabLayout = findViewById(R.id.tabLayout);
        flashcardsItemLayout = findViewById(R.id.flashcardLayout);
        flashcardItems = new ArrayList<>();
        api = RetroClient.getApiService();

        Bundle bundle = getIntent().getExtras();
        flashcardId = bundle.getLong("FlashcardIndex");

        Call<FlashcardItemsList> flashcardItemsListCall = api.getFlashcardItems(flashcardId);

        flashcardItemsListCall.enqueue(new Callback<FlashcardItemsList>() {
            @Override
            public void onResponse(Call<FlashcardItemsList> call, Response<FlashcardItemsList> response) {

                if(response.isSuccessful()){

                    flashcardItems = response.body().getFlashcardItemsList();
                    currentFlashcardItems = new ArrayList<>();
                    currentFlashcardItems = flashcardItems;
                    loadUISystem();
                }
            }

            @Override
            public void onFailure(Call<FlashcardItemsList> call, Throwable t) {
                Log.e("flashcardItem","ERROR", t);
            }
        });

    }


    void loadUISystem(){

        loadFlashcard(flashcardIndex);

        tabLayout.setOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                String tabName = tab.getText().toString();
                flashcardsItemLayout.removeAllViews();

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
                final View popUpView = getLayoutInflater().inflate(R.layout.add_flashcard_popup,
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
                Button nextButton = popUpView.findViewById(R.id.nextButton);


                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextInputEditText firstWord = popUpView.findViewById(R.id.firstWordText);
                        TextInputEditText secondWord = popUpView.findViewById(R.id.secondWordText);
                        TextInputEditText firstDescription = popUpView.findViewById(R.id.firstDescriptionText);
                        TextInputEditText secondDescription = popUpView.findViewById(R.id.secondDescriptionText);

                        FlashcardItem flashcardItem = new FlashcardItem();
                        flashcardItem.setFirstWord(firstWord.getText().toString());
                        flashcardItem.setSecondWord(secondWord.getText().toString());
                        flashcardItem.setFirstDescription(firstDescription.getText().toString());
                        flashcardItem.setSecondDescription(secondDescription.getText().toString());
                        flashcardItem.setKnow(false);

                        Call<FlashcardItem> flashcardItemCall = api.postFlashcardItem(flashcardItem, flashcardId);

                        flashcardItemCall.enqueue(new Callback<FlashcardItem>() {
                            @Override
                            public void onResponse(Call<FlashcardItem> call, Response<FlashcardItem> response) {

                                if(response.isSuccessful()){

                                    flashcardItems.add(response.body());
                                    loadFlashcard(currentFlashcardIndex);
                                    addFlashcardsPopUp.dismiss();

                                }
                            }

                            @Override
                            public void onFailure(Call<FlashcardItem> call, Throwable t) {
                                Log.e("flashcardItem","ERROR", t);
                            }
                        });

                    }
                });


            }
        });

    }

    void loadFlashcard(int i) {

        try {
                FlashcardItem flashcardItem = currentFlashcardItems.get(i);
                flashcardsItemLayout.removeAllViews();
                addFlashcardItem(flashcardItem);
                currentFlashcardIndex = i;

        }catch (IndexOutOfBoundsException e){}

    }

    void addFlashcardItem(FlashcardItem flashcardItem){

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
    }

}
