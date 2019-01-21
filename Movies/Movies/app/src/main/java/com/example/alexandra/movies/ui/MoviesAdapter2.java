package com.example.alexandra.movies.ui;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alexandra.movies.R;
import com.example.alexandra.movies.DetailActivity;
import com.example.alexandra.movies.model.Movie;


/**
 * Adapter for the list of repositories.
 *
 *
 */
public class MoviesAdapter2 extends PagedListAdapter<Movie, MoviesAdapter2. MovieViewHolder> {
    private Context mContext;
    /**
     * DiffUtil to compare the Repo data (old and new)
     * for issuing notify commands suitably to update the list
     */
    private static DiffUtil.ItemCallback<Movie> REPO_COMPARATOR
            = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(Movie oldItem, Movie newItem) {
            return oldItem.title.equals(newItem.title);
        }

        @Override
        public boolean areContentsTheSame(Movie oldItem, Movie newItem) {
            return oldItem.equals(newItem);
        }
    };

    public void setContext(Context mContext){
        this.mContext=mContext;
    }

    MoviesAdapter2() {
        super(REPO_COMPARATOR);
    }

    @NonNull
    @Override
    public  MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Uses DataBinding to inflate the Item View
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.movie_layout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  MovieViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    /**
     * View Holder for a {@link Movie} RecyclerView list item.
     */
    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;

        MovieViewHolder(View itemView){
            super(itemView);
            textView=itemView.findViewById(R.id.text_view);
            textView.setOnClickListener(this);

        }

        void bind(Movie movie) {
//            if (movie == null) {
//                //Binding the elements in the code when the Repo is null
//                Resources resources = mDataBinding.getRoot().getContext().getResources();
//                mDataBinding.repoName.setText(resources.getString(R.string.loading));
//                mDataBinding.repoDescription.setVisibility(View.GONE);
//                mDataBinding.repoLanguage.setVisibility(View.GONE);
//                mDataBinding.repoStars.setText(resources.getString(R.string.unknown));
//                mDataBinding.repoForks.setText(resources.getString(R.string.unknown));
//            } else {
//                //When Repo is not null, data binding will be automatically done in the layout
//                mDataBinding.setRepo(repo);
//                //For Immediate Binding
//                mDataBinding.executePendingBindings();
//            }
            textView.setText("Title: "+movie.getTitle()+ "\nGenre:  "+ movie.getGenre()+"\nRating " + movie.getRating());
        }

        /**
         * Called when a view has been clicked.
         *
         * @param view The view that was clicked.
         */
        @Override
        public void onClick(View view) {
       //     if (getAdapterPosition() > RecyclerView.NO_POSITION) {
                Movie movie = getItem(getAdapterPosition());
                Class destinationClass = DetailActivity.class;
                Intent intentToStartDetailActivity = new Intent(mContext, destinationClass);
                intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, "Title: "+ movie.title+ "\nDescription: "+movie.description+"\nGenre: "+movie.genre+ "\nRating: "+movie.rating);
                mContext.startActivity(intentToStartDetailActivity);

        //    }
        }
    }
}
