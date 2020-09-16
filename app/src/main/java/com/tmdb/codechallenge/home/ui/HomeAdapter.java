package com.tmdb.codechallenge.home.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tmdb.codechallenge.R;
import com.tmdb.codechallenge.api.model.Movie;
import com.tmdb.codechallenge.util.MovieImageUrlBuilder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private List<Movie> moviesList;
    private OnItemClickListener listener;

    public HomeAdapter(@NotNull List<Movie> images, OnItemClickListener listener) {
        super();
        this.moviesList = images;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final MovieImageUrlBuilder movieImageUrlBuilder;

        public ViewHolder(@NotNull View itemView) {
            super(itemView);
            movieImageUrlBuilder = new MovieImageUrlBuilder();
        }

        public final void bind(Movie movie) {
            if (movie.getPosterPath() != null) {
                ImageView imageView = itemView.findViewById(R.id.moviePosterImageView);
                Glide.with(itemView)
                        .load(movieImageUrlBuilder.buildPosterUrl(movie.getPosterPath()))
                        .apply(new RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                        .into(imageView);
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(moviesList.get(position));
        holder.itemView.setOnClickListener(v -> listener.onItemClick(moviesList.get(position)));
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    interface OnItemClickListener {
        void onItemClick(Movie item);
    }
}
