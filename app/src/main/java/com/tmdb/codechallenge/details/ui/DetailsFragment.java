package com.tmdb.codechallenge.details.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tmdb.codechallenge.R;
import com.tmdb.codechallenge.api.model.Genre;
import com.tmdb.codechallenge.api.model.Movie;
import com.tmdb.codechallenge.api.model.MovieImage;
import com.tmdb.codechallenge.details.presentation.DetailsViewModel;
import com.tmdb.codechallenge.details.presentation.DetailsViewModelFactory;
import com.tmdb.codechallenge.util.MovieImageUrlBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class DetailsFragment extends Fragment {
    private static final String MOVIE = "movie";

    private Movie movie;
    private DetailsViewModel detailsViewModel;

    private RecyclerView moviePostersCarousel;

    public static DetailsFragment newInstance(Movie movie) {
        DetailsFragment fragment = new DetailsFragment();

        Bundle fragArgs = new Bundle();
        fragArgs.putParcelable(MOVIE, movie);

        fragment.setArguments(fragArgs);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        assert args != null;
        movie = (Movie) args.get(MOVIE);
        detailsViewModel = new ViewModelProvider(requireActivity(), new DetailsViewModelFactory(movie)).get(DetailsViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        observeViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imageViewMovieBanner = view.findViewById(R.id.imageViewMovieBanner);

        Glide.with(this)
                .load(new MovieImageUrlBuilder().buildBackdropUrl(movie.getBackdropPath()))
                .into(imageViewMovieBanner);

        TextView textViewMovieSynopses = view.findViewById(R.id.textViewMovieSynopses);
        textViewMovieSynopses.setText(movie.getOverview());

        TextView textViewMovieGenres = view.findViewById(R.id.textViewMovieGenres);
        String genres = genresListToString(movie.getGenres());
        textViewMovieGenres.setText(genres);

        TextView textViewMovieReleaseDate = view.findViewById(R.id.textViewMovieReleaseDate);
        textViewMovieReleaseDate.setText(movie.getReleaseDate());

        moviePostersCarousel = view.findViewById(R.id.moviePostersCarousel);
    }

    private void observeViewModel() {
        detailsViewModel.getImages().observe(this, this::setupCarousel);
        detailsViewModel.retrieveImages();
    }

    private void setupCarousel(List<MovieImage> images) {
        DetailsAdapter detailsAdapter = new DetailsAdapter(images);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        moviePostersCarousel.setLayoutManager(layoutManager);
        moviePostersCarousel.setAdapter(detailsAdapter);
    }

    private String genresListToString(@Nullable List<Genre> genres) {
        assert genres != null;
        return genres.stream()
                .map(Genre::getName)
                .collect(Collectors.joining(", "));
    }
}
