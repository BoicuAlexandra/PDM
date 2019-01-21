package com.example.alexandra.movies.db;

import android.util.Log;

import com.example.alexandra.movies.model.Movie;

import java.util.List;
import java.util.concurrent.Executor;

import android.arch.paging.DataSource;


public class MoviesLocalCache {
    private static final String LOG_TAG= MoviesLocalCache.class.getSimpleName();

    private MovieDao movieDao;

    private Executor ioExecutor;

    public MoviesLocalCache(MovieDao movieDao, Executor ioExecutor){
        this.movieDao = movieDao;
        this.ioExecutor=ioExecutor;
    }

    public void insert(List<Movie> movies, InsertCallback callback){
        ioExecutor.execute(()->{
            Log.d(LOG_TAG,"insert inserting " +movies.size()+ "movies" );
            movieDao.insert(movies);
            callback.insertFinished();

        });
    }

    public DataSource.Factory<Integer,Movie> getAllMovies(){
        return movieDao.getAll();
    }


    public interface InsertCallback {
        /**
         * Callback method invoked when the insert operation
         * completes.
         */
        void insertFinished();
    }
}
