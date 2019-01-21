package com.example.alexandra.movies.api;

import com.example.alexandra.movies.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieApi {
    String BASE_URL = "http://192.168.43.156:8080/app/";

    @GET("movies")
    Call<List<Movie>> getAllMovies();
}
