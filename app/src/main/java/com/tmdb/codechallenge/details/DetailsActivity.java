package com.tmdb.codechallenge.details;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.tmdb.codechallenge.R;
import com.tmdb.codechallenge.api.model.Movie;
import com.tmdb.codechallenge.details.ui.DetailsFragment;

public class DetailsActivity extends AppCompatActivity {

    public static final String MOVIE = "movie";

    private Movie movie = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.details_activity);

        movie = getIntent().getParcelableExtra(MOVIE);

        setupToolbar();

        Fragment fragment = DetailsFragment.newInstance(movie);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detailsContainer, fragment)
                .commit();
    }

    private void setupToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.detailsToolbar);
        toolbar.setTitle(movie.getTitle());
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
