package com.szczepaniak.dawid.flashcardsapp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

public class FlashcardItem {

    String firstWord;
    String secondWord;

    String firstDescription;
    String secondDescription;

    public FlashcardItem(String firstWord, String secondWord, String firstDescription, String secondDescription) {
        this.firstWord = firstWord;
        this.secondWord = secondWord;
        this.firstDescription = firstDescription;
        this.secondDescription = secondDescription;
    }


    public String getFirstWord() {
        return firstWord;
    }

    public void setFirstWord(String firstWord) {
        this.firstWord = firstWord;
    }

    public String getSecondWord() {
        return secondWord;
    }

    public void setSecondWord(String secondWord) {
        this.secondWord = secondWord;
    }

    public String getFirstDescription() {
        return firstDescription;
    }

    public void setFirstDescription(String firstDescription) {
        this.firstDescription = firstDescription;
    }

    public String getSecondDescription() {
        return secondDescription;
    }

    public void setSecondDescription(String secondDescription) {
        this.secondDescription = secondDescription;
    }



    public SpannableString getPaintedText(String text, String keyWord){

        SpannableString spannableString = new SpannableString(text);

        for (int i = -1; (i = text.toLowerCase().indexOf(keyWord.toLowerCase(), i + 1)) != -1; i++) {
            try {
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.rgb(95, 95, 195));
                spannableString.setSpan(colorSpan, i, i + keyWord.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
                spannableString.setSpan(boldSpan, i, i + keyWord.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            }catch (IndexOutOfBoundsException e){}
        }

        return spannableString;
    }
}
