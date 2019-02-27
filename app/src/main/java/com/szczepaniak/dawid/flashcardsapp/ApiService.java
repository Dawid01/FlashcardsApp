package com.szczepaniak.dawid.flashcardsapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("flashcards")
    Call<FlashcardsList> getFlashcards();

    @GET("flashcards/{id}")
    Call<Flashcard> getFlashcard(@Path("id") Long id);

}
