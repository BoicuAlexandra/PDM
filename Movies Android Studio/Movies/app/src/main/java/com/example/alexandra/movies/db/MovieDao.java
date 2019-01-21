package com.example.alexandra.movies.db;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.example.alexandra.movies.model.Movie;

import java.util.List;


@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Movie>posts);

    // Do a similar query as the search API:
    // Look for repos that contain the query string in the name or in the description
    // and order those results descending, by the number of stars and then by name ascending
    @Query("SELECT * FROM movies ")
    DataSource.Factory<Integer, Movie> getAll();
}
