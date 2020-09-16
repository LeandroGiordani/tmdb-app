package com.tmdb.codechallenge.home.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.tmdb.codechallenge.R;
import com.tmdb.codechallenge.api.model.Movie;
import com.tmdb.codechallenge.details.DetailsActivity;
import com.tmdb.codechallenge.home.presentation.HomeViewModel;
import com.tmdb.codechallenge.home.presentation.HomeViewModelFactory;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerViewAction;
    private RecyclerView recyclerViewAnimation;
    private RecyclerView recyclerViewComedy;
    private RecyclerView recyclerViewDocumentary;
    private RecyclerView recyclerViewDrama;
    private RecyclerView recyclerViewHorror;
    private RecyclerView recyclerViewRomance;
    private RecyclerView recyclerViewThriller;

    private ProgressBar progressBar;

    private HomeViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity(), new HomeViewModelFactory()).get(HomeViewModel.class);
        viewModel.onCreate();
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewAction = view.findViewById(R.id.recyclerViewAction);
        recyclerViewAnimation = view.findViewById(R.id.recyclerViewAnimation);
        recyclerViewComedy = view.findViewById(R.id.recyclerViewComedy);
        recyclerViewDocumentary = view.findViewById(R.id.recyclerViewDocumentary);
        recyclerViewDrama = view.findViewById(R.id.recyclerViewDrama);
        recyclerViewHorror = view.findViewById(R.id.recyclerViewHorror);
        recyclerViewRomance = view.findViewById(R.id.recyclerViewRomance);
        recyclerViewThriller = view.findViewById(R.id.recyclerViewThriller);

        progressBar = view.findViewById(R.id.progressBar);

        viewModel.getMoviesList().observe(this, this::updateMovieList);

        viewModel.getFailure().observe(this, errorMsg->
                Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show());
    }

    private void updateMovieList(List<Movie> movies) {
        recyclerViewAction.setAdapter(new HomeAdapter(movies, this::goToDetails));

        recyclerViewAnimation.setAdapter(new HomeAdapter(filterMovies(movies, GenresEnum.ANIMATION.id), this::goToDetails));

        recyclerViewComedy.setAdapter(new HomeAdapter(filterMovies(movies, GenresEnum.COMEDY.id), this::goToDetails));

        recyclerViewDocumentary.setAdapter(new HomeAdapter(filterMovies(movies, GenresEnum.DOCUMENTARY.id), this::goToDetails));

        recyclerViewDrama.setAdapter(new HomeAdapter(filterMovies(movies, GenresEnum.DRAMA.id), this::goToDetails));

        recyclerViewHorror.setAdapter(new HomeAdapter(filterMovies(movies, GenresEnum.HORROR.id), this::goToDetails));

        recyclerViewRomance.setAdapter(new HomeAdapter(filterMovies(movies, GenresEnum.ROMANCE.id), this::goToDetails));

        recyclerViewThriller.setAdapter(new HomeAdapter(filterMovies(movies, GenresEnum.THRILLER.id), this::goToDetails));

        progressBar.setVisibility(View.GONE);
    }

    private List<Movie> filterMovies(List<Movie> movies, int genreId) {
        Predicate<Movie> byGenre = movie -> Objects.requireNonNull(movie.getGenreIds()).contains(genreId);
        return movies.stream()
                .filter(byGenre)
                .collect(Collectors.toList());
    }

    private void goToDetails(Movie movie) {
        Intent intent = new Intent(getContext(), DetailsActivity.class);
        intent.putExtra(DetailsActivity.MOVIE, movie);
        startActivity(intent);
    }
}
