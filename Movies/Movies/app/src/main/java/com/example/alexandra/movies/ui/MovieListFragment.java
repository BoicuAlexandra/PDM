package com.example.alexandra.movies.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.alexandra.movies.Injection;
import com.example.alexandra.movies.R;
import com.example.alexandra.movies.viewmodel.MoviesViewModel2;

public class MovieListFragment extends Fragment {

    private MoviesViewModel2 mMoviewViewModel;
    private RecyclerView mMovieList;
    private ProgressBar pbLoadingIndicator;

    public static MovieListFragment newInstance() {
        return new MovieListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_list_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMovieList = view.findViewById(R.id.movie_list);
        pbLoadingIndicator = view.findViewById(R.id.pb_loading_indicator);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMovieList.setLayoutManager(new LinearLayoutManager(getActivity()));
        //    mMoviewViewModel = ViewModelProviders.of(this).get(MoviesViewModel2.class);
        mMoviewViewModel = ViewModelProviders.of(getActivity(), (ViewModelProvider.Factory) Injection.provideViewModelFactory(getActivity()))
                .get(MoviesViewModel2.class);
        pbLoadingIndicator.setVisibility(View.VISIBLE);
        mMoviewViewModel.getMovies("a");

//        mMoviewViewModel.getMovies().observe(this, new Observer<PagedList<Movie>>() {
//            @Override
//            public void onChanged(@Nullable PagedList<Movie> movies) {
//                MoviesAdapter2 moviesAdapter = new MoviesAdapter2();
//                moviesAdapter.submitList(movies);
//                //MovieListAdapter moviesAdapter = new MovieListAdapter(getActivity(), movies);
//                mMovieList.setAdapter(moviesAdapter);
//                pbLoadingIndicator.setVisibility(View.INVISIBLE);
//            }
//        });
        mMoviewViewModel.getMovies().observe(this, movies -> {
            if (movies != null) {
                MoviesAdapter2 moviesAdapter = new MoviesAdapter2();
                moviesAdapter.setContext(this.getContext());
                moviesAdapter.submitList(movies);
                mMovieList.setAdapter(moviesAdapter);
                pbLoadingIndicator.setVisibility(View.INVISIBLE);
            }
            if(movies == null){
                Toast.makeText(getActivity(), "\uD83D\uDE28 Wooops ", Toast.LENGTH_LONG).show();

            }

        });

    }
}
