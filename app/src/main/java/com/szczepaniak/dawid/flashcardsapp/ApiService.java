package com.szczepaniak.dawid.flashcardsapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @GET("flashcards")
    Call<FlashcardsList> getFlashcards();

    @GET("flashcards/{id}")
    Call<Flashcard> getFlashcard(@Path("id") Long id);

    @POST("flashcards")
    Call<Flashcard> postFlashcard(@Body Flashcard flashcard);

    @DELETE("flashcards/{id}")
    Call<Flashcard> deleteFlashcard(@Path("id") Long id);

    @GET("users/{id}")
    Call<User> getUser(@Path("id") Long id);

    @PUT("users/{id}")
    Call<User> putUser(@Path("id") Long id, @Body User user);

    @GET("users")
    Call<User> loginUser(@Header("Authorization") String authHeader);
}
