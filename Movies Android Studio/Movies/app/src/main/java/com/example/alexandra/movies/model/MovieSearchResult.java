package com.example.alexandra.movies.model;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

/**
 * MovieSearchResult from a search, which contains LiveData<PagedList<Repo>> holding query data,
 * and a LiveData<String> of network error state.
 *
 *
 */
public class MovieSearchResult {
    //LiveData for Search Results
    private final LiveData<PagedList<Movie>> data;
    //LiveData for the Network Errors
    private final LiveData<String> networkErrors;

    public MovieSearchResult(LiveData<PagedList<Movie>> data, LiveData<String> networkErrors) {
        this.data = data;
        this.networkErrors = networkErrors;
    }

    public LiveData<PagedList<Movie>> getData() {
        return data;
    }

    public LiveData<String> getNetworkErrors() {
        return networkErrors;
    }
}
