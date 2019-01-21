package com.example.alexandra.movies;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.alexandra.movies.api.MovieServiceClient;
import com.example.alexandra.movies.data.MovieRepository;
import com.example.alexandra.movies.db.MovieDatabase;
import com.example.alexandra.movies.db.MoviesLocalCache;
import com.example.alexandra.movies.ui.ViewModelFactory;
import com.example.alexandra.movies.viewmodel.MoviesViewModel2;

import java.util.concurrent.Executors;

/**
 * Class that handles object creation.
 * <p>
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 *
 * @author Kaushik N Sanji
 */
public class Injection {

    @NonNull
    private static MoviesLocalCache provideCache(Context context) {
        MovieDatabase movieDatabase = MovieDatabase.getInstance(context);
        return new MoviesLocalCache(movieDatabase.moviesDao(), Executors.newSingleThreadExecutor());
    }


    @NonNull
    private static MovieRepository provideMovieRepository(Context context) {
        return new MovieRepository(MovieServiceClient.create(), provideCache(context));
    }


    @NonNull
    public static ViewModelFactory provideViewModelFactory(Context context) {
        return new ViewModelFactory(provideMovieRepository(context));
    }
}
