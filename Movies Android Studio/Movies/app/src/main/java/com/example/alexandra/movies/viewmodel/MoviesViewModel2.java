package com.example.alexandra.movies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;
import android.support.annotation.Nullable;

import com.example.alexandra.movies.data.MovieRepository;
import com.example.alexandra.movies.model.Movie;
import com.example.alexandra.movies.model.MovieSearchResult;

public class MoviesViewModel2 extends ViewModel {
    private MovieRepository movieRepository;
    private MutableLiveData<String> liveData = new MutableLiveData<>();


    private LiveData<MovieSearchResult> movieResult = Transformations.map(liveData, (x) -> movieRepository.search());

    private LiveData<PagedList<Movie>> movies=Transformations.switchMap(movieResult,MovieSearchResult::getData);

    private LiveData<String> networkError = Transformations.switchMap(movieResult,MovieSearchResult::getNetworkErrors);

    public MoviesViewModel2(MovieRepository movieRepository){
        this.movieRepository=movieRepository;
    }

   public  LiveData<PagedList<Movie>> getMovies(){return movies;
    }

   public  LiveData<String> getNetworkError(){return networkError;}


    /**
     * Search a repository based on a query string.
     */

  public void getMovies(String x) {
      liveData.postValue(x);
    }


    @Nullable
    String lastQueryValue() {
        return liveData.getValue();
    }
}
