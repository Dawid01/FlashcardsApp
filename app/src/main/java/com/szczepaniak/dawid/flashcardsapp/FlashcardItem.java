package com.szczepaniak.dawid.flashcardsapp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FlashcardItem {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("firstWord")
    @Expose
    private String firstWord;
    @SerializedName("secondWord")
    @Expose
    private String secondWord;
    @SerializedName("firstDescription")
    @Expose
    private String firstDescription;
    @SerializedName("secondDescription")
    @Expose
    private String secondDescription;
    @SerializedName("know")
    @Expose
    private boolean know;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isKnow() {
        return know;
    }

    public void setKnow(boolean know) {
        this.know = know;
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
