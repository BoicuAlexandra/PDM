package com.example.alexandra.movies.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.alexandra.movies.api.MovieApi;
import com.example.alexandra.movies.api.MovieServiceClient;
import com.example.alexandra.movies.db.MoviesLocalCache;
import com.example.alexandra.movies.model.Movie;

import java.util.List;

public class MovieBoundaryCallback extends PagedList.BoundaryCallback<Movie> implements MovieServiceClient.ApiCallback {

    private static final String LOG_TAG = MovieBoundaryCallback.class.getSimpleName();

    private MovieApi movieApi;
    private MoviesLocalCache localCache;

    private boolean isRequesttInProgress = false;

    private MutableLiveData<String> networkErrors = new MutableLiveData<>();

    MovieBoundaryCallback(MovieApi movieApi, MoviesLocalCache localCache) {
        this.localCache = localCache;
        this.movieApi = movieApi;
    }

    LiveData<String> getNetworkErrors() {
        return networkErrors;
    }

    private void requestAndSaveData() {

        if (isRequesttInProgress) return;

        isRequesttInProgress = true;
        MovieServiceClient.getAllMovies(movieApi, this);

    }

    /**
     * Called when zero items are returned from an initial load of the PagedList's data source.
     */
    @Override
    public void onZeroItemsLoaded() {
        Log.d(LOG_TAG, "onZeroItemsLoaded: Started");
        requestAndSaveData();
    }

    /**
     * Called when the item at the end of the PagedList has been loaded, and access has
     * occurred within {@link PagedList.Config#prefetchDistance} of it.
     * <p>
     * No more data will be appended to the PagedList after this item.
     *
     * @param itemAtEnd The first item of PagedList
     */
    @Override
    public void onItemAtEndLoaded(@NonNull Movie itemAtEnd) {
        Log.d(LOG_TAG, "onItemAtEndLoaded: Started");
        requestAndSaveData();
    }

    /**
     * Callback invoked when the Search Repo API Call
     * completed successfully
     *
     * @param items The List of Repos retrieved for the Search done
     */
    @Override
    public void onSuccess(List<Movie> items) {
        //Inserting records in the database thread
        localCache.insert(items, () -> {
            //Updating the last requested page number when the request was successful
            //and the results were inserted successfully
            isRequesttInProgress = false;
        });
    }

    /**
     * Callback invoked when the Search Repo API Call failed
     *
     * @param errorMessage The Error message captured for the API Call failed
     */
    @Override
    public void onError(String errorMessage) {
        //Update the Network error to be shown
        networkErrors.postValue(errorMessage);
        //Mark the request progress as completed
        isRequesttInProgress = false;
    }

}
