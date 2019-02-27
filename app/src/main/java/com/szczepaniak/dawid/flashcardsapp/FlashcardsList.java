package com.szczepaniak.dawid.flashcardsapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FlashcardsList {

    @SerializedName("content")
    @Expose
    List<Flashcard> flashcardList;

    public List<Flashcard> getFlashcardList() {
        return flashcardList;
    }

    public void setFlashcardList(List<Flashcard> flashcardList) {
        this.flashcardList = flashcardList;
    }
}
