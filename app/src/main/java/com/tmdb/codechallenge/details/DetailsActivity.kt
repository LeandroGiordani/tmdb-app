package com.tmdb.codechallenge.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tmdb.codechallenge.R
import com.tmdb.codechallenge.api.model.Movie
import com.tmdb.codechallenge.details.ui.DetailsFragment
import kotlinx.android.synthetic.main.details_activity.*

class DetailsActivity : AppCompatActivity() {

    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_activity)

        movie = intent.getParcelableExtra(MOVIE)

        setupToolbar()

        val fragment = DetailsFragment.newInstance(movie as Movie)

        supportFragmentManager.beginTransaction()
                .replace(R.id.detailsContainer, fragment)
                .commit()
    }

    private fun setupToolbar() {
        setSupportActionBar(detailsToolbar)
        supportActionBar?.title = movie?.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        const val MOVIE = "movie"
    }
}
