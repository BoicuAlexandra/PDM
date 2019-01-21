package com.example.alexandra.movies.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexandra.movies.R;
import com.example.alexandra.movies.DetailActivity;
import com.example.alexandra.movies.model.Movie;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MoviesViewHolder> {

    private Context mContext;
    private List<Movie> mMovieRespons;

    public MovieListAdapter(Context context,List<Movie> movieRespons){
        this.mContext=context;
        this.mMovieRespons = movieRespons;
    }
    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.movie_layout,viewGroup,false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder moviesViewHolder, int i) {
        Movie movie = mMovieRespons.get(i);
        moviesViewHolder.textView.setText(movie.getTitle()+ " "+ movie.getGenre()+" " + movie.getRating());
    }

    @Override
    public int getItemCount() {
        return mMovieRespons.size();
    }

     class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView;

        MoviesViewHolder(View itemView){
            super(itemView);
            textView=itemView.findViewById(R.id.text_view);
            textView.setOnClickListener(this);

        }

         @Override
         public void onClick(View v) {
             int adapterPosition = getAdapterPosition();
             Class destinationClass = DetailActivity.class;
             Intent intentToStartDetailActivity = new Intent(mContext, destinationClass);
             intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, mMovieRespons.get(adapterPosition).getDescription());
             mContext.startActivity(intentToStartDetailActivity);
         }
     }
}
