package com.szczepaniak.dawid.flashcardsapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Flashcard {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("flashcards")
    @Expose
    private int flashcards;

    @SerializedName("knowsFlashcards")
    @Expose
    private int knowsFlashcards;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(int flashcards) {
        this.flashcards = flashcards;
    }

    public int getKnowsFlashcards() {
        return knowsFlashcards;
    }

    public void setKnowsFlashcards(int knowsFlashcards) {
        this.knowsFlashcards = knowsFlashcards;
    }
}
