package com.example.alexandra.movies.api;

import android.util.Log;

import com.example.alexandra.movies.model.Movie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieServiceClient {
    private static final String LOG_TAG = MovieServiceClient.class.getSimpleName();

    public static MovieApi create(){
        return new Retrofit.Builder().baseUrl(MovieApi.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(MovieApi.class);
    }

    public static void getAllMovies(MovieApi movieApi,ApiCallback apiCallback){
        Log.d(LOG_TAG, String.format("Getting all movies"));
        movieApi.getAllMovies().enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {

                if (response.isSuccessful()) {
                    Log.d(LOG_TAG, "movies loaded succesfully");
                    //Retrieving the Repo Items when the response is successful
                    List<Movie> items;
                    if (response.body() != null) {
                        items = response.body();
                    } else {
                        Log.d(LOG_TAG, "the body is null");
                        items = new ArrayList<>();
                    }
                    //Pass the result to the callback
                    apiCallback.onSuccess(items);
                } else {
                    //When the response is unsuccessful
                    try {
                        //Pass the error to the callback
                        apiCallback.onError(response.errorBody() != null ?
                                response.errorBody().string() : "Unknown error");
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "onResponse: Failed while reading errorBody: ", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.d(LOG_TAG, "onFailure: Failed to get data");
                //Pass the error to the callback
                apiCallback.onError(t.getMessage() != null ?
                        t.getMessage() : "Unknown error");
            }
        });

    }


    public interface ApiCallback {
        /**
         * Callback invoked when the Search Repo API Call
         * completed successfully
         *
         * @param items The List of Repos retrieved for the Search done
         */
        void onSuccess(List<Movie> items);

        /**
         * Callback invoked when the Search Repo API Call failed
         *
         * @param errorMessage The Error message captured for the API Call failed
         */
        void onError(String errorMessage);
    }
}
