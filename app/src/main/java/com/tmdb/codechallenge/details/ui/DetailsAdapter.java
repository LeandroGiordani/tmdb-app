package com.tmdb.codechallenge.details.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tmdb.codechallenge.R;
import com.tmdb.codechallenge.api.model.MovieImage;
import com.tmdb.codechallenge.util.MovieImageUrlBuilder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {
    private List<MovieImage> images;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final MovieImageUrlBuilder movieImageUrlBuilder;

        public ViewHolder(@NotNull View itemView) {
            super(itemView);
            this.movieImageUrlBuilder = new MovieImageUrlBuilder();
        }

        public final void bind(@NotNull MovieImage image) {
            ImageView imageView = itemView.findViewById(R.id.posterImageView);
            Glide.with(itemView)
                    .load(movieImageUrlBuilder.buildDetailsPosterUrl(image.getFilePath()))
                    .apply((new RequestOptions()).placeholder(R.drawable.ic_image_placeholder))
                    .into(imageView);
        }

    }

    public DetailsAdapter(@NotNull List<MovieImage> images) {
        super();
        this.images = images;
    }

    @NotNull
    @Override
    public DetailsAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.poster_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        MovieImage image = images.get(position);
        holder.bind(image);
    }

    public int getItemCount() {
        return this.images.size();
    }

}
