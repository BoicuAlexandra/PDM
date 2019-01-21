package com.example.alexandra.movies.data;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.util.Log;

import com.example.alexandra.movies.api.MovieApi;
import com.example.alexandra.movies.db.MoviesLocalCache;
import com.example.alexandra.movies.model.Movie;
import com.example.alexandra.movies.model.MovieSearchResult;

public class MovieRepository {
    private static final String LOG_TAG = MovieRepository.class.getSimpleName();
    private static final int DATABASE_PAGE_SIZE = 20;
    private MovieApi movieApi;
    private MoviesLocalCache moviesLocalCache;

    public MovieRepository(MovieApi movieApi1, MoviesLocalCache moviesLocalCache){
        this.movieApi=movieApi1;
        this.moviesLocalCache=moviesLocalCache;
    }

    public MovieSearchResult search(){
        Log.d(LOG_TAG, "get movies");
        DataSource.Factory<Integer,Movie> allMovies = moviesLocalCache.getAllMovies();

        MovieBoundaryCallback boundaryCallback= new MovieBoundaryCallback(movieApi,moviesLocalCache);

        LiveData<String> networkErrors = boundaryCallback.getNetworkErrors();

        PagedList.Config pagedConfig = new PagedList.Config.Builder()
                .setPageSize(DATABASE_PAGE_SIZE)
                .setPrefetchDistance(0)
                .build();

        // Get the Live Paged list
        LiveData<PagedList<Movie>> data = new LivePagedListBuilder<>(allMovies , pagedConfig)
                .setBoundaryCallback(boundaryCallback)
                .build();

        return new MovieSearchResult(data,networkErrors);
    }

}
