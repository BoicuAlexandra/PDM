package com.example.alexandra.movies.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.alexandra.movies.data.MovieRepository;
import com.example.alexandra.movies.viewmodel.MoviesViewModel2;

import java.lang.reflect.InvocationTargetException;

/**
 * Factory for ViewModels
 *
 * @author Kaushik N Sanji
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private MovieRepository movieRepository;
    public ViewModelFactory(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (MoviesViewModel2.class.isAssignableFrom(modelClass)) {
            try {
                return modelClass.getConstructor(MovieRepository.class).newInstance(movieRepository);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            } catch (InstantiationException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            }
        }
        throw new IllegalArgumentException("Unknown ViewModel class " + modelClass);
    }
}
