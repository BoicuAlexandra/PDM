package com.example.alexandra.movies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.alexandra.movies.api.MovieApi;
import com.example.alexandra.movies.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesViewModel extends ViewModel {
    private static final String TAG = MoviesViewModel.class.getCanonicalName();
    private MutableLiveData<List<Movie>> movies;

    public LiveData<List<Movie>> getMovies() {
        if (movies == null) {
            movies = new MutableLiveData<>();
            loadMovies();
        }
        return movies;
    }

    private void loadMovies() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(MovieApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        MovieApi api= retrofit.create(MovieApi.class);

        Call<List<Movie>> call = api.getAllMovies();
        Log.d(TAG,"load movies");
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                Log.d(TAG, "load movies succeeded");
                if (response.body() != null) {
                    movies.setValue(response.body());
                } else {
                    movies = null;
                    Log.e(TAG, "body is null");
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.e(TAG, "load movies failed", t);
            }
        });

    }
}
