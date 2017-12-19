package com.carefree.findmovie.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.carefree.findmovie.R;
import com.carefree.findmovie.repo.model.Movie;
import com.carefree.findmovie.repo.remote.ApiClient;
import com.carefree.findmovie.viewmodel.MovieViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MovieViewModel mViewModel;
    private List<Movie> mData;
    private MoviesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        final RecyclerView recyclerView = findViewById(R.id.movies_recycler_view);
        final Observer<List<Movie>> observer = new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable final List<Movie> movies) {
                if (mData == null) {
                    mData = movies;
                    mAdapter = new MoviesAdapter();
                    recyclerView.setAdapter(mAdapter);
                } else {
                    DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {

                        @Override
                        public int getOldListSize() {
                            return mData.size();
                        }

                        @Override
                        public int getNewListSize() {
                            return movies.size();
                        }

                        @Override
                        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                            return (mData.get(oldItemPosition)).getId()
                                    == movies.get(newItemPosition).getId();
                        }

                        @Override
                        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                            Movie oldPw = mData.get(oldItemPosition);
                            Movie newPw = movies.get(newItemPosition);
                            return oldPw.equals(newPw);
                        }
                    });
                    result.dispatchUpdatesTo(mAdapter);
                    mData = movies;
                }
            }
        };

        mViewModel.getMovies().observe(this, observer);
    }

    private class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieHolder> {

        @Override
        public MoviesAdapter.MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie, parent, false);
            return new MovieHolder(view);
        }

        @Override
        public void onBindViewHolder(MoviesAdapter.MovieHolder holder, int position) {
            holder.movieTitle.setText(mData.get(position).getTitle());
            holder.data.setText(mData.get(position).getReleaseDate());
            holder.movieDescription.setText(mData.get(position).getOverview());
            holder.rating.setText(mData.get(position).getVoteAverage().toString());
            Glide.with(getApplicationContext())
                    .load(ApiClient.IMAGE + mData.get(position).getBackdropPath())
                    .into(holder.posterPath);
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class MovieHolder extends RecyclerView.ViewHolder {
            LinearLayout moviesLayout;
            TextView movieTitle;
            TextView data;
            TextView movieDescription;
            TextView rating;
            ImageView posterPath;

            MovieHolder(View v) {
                super(v);
                moviesLayout = v.findViewById(R.id.movies_layout);
                movieTitle = v.findViewById(R.id.title);
                data = v.findViewById(R.id.subtitle);
                movieDescription = v.findViewById(R.id.description);
                rating = v.findViewById(R.id.rating);
                posterPath = v.findViewById(R.id.backdrop_path);
            }
        }
    }
}
