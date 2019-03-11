package com.szczepaniak.dawid.flashcardsapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FlashcardItemsList {

    @SerializedName("content")
    @Expose
    List<FlashcardItem> flashcardItemsList;

    public List<FlashcardItem> getFlashcardItemsList() {
        return flashcardItemsList;
    }

    public void setFlashcardItemsList(List<FlashcardItem> flashcardItemsList) {
        this.flashcardItemsList = flashcardItemsList;
    }
}
