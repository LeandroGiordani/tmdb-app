package com.tmdb.codechallenge.home.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tmdb.codechallenge.R
import com.tmdb.codechallenge.api.model.Movie
import com.tmdb.codechallenge.details.DetailsActivity
import com.tmdb.codechallenge.home.presentation.HomeViewModel
import com.tmdb.codechallenge.home.presentation.HomeViewModelFactory
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), HomeViewModelFactory()).get(HomeViewModel::class.java)
        viewModel.onCreate()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.moviesList.observe(viewLifecycleOwner, Observer {
            val movies = it ?: return@Observer
            updateMovieList(movies)
        })

        viewModel.failure.observe(viewLifecycleOwner, Observer {errorMsg->
            Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
        })
    }

    private fun updateMovieList(movies: List<Movie>) {
        recyclerViewAction.adapter = HomeAdapter(filterMovies(movies, GenresEnum.ACTION.id), object : HomeAdapter.OnItemClickListener {
            override fun onItemClick(item: Movie) {
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra(DetailsActivity.MOVIE, item)
                startActivity(intent)
            }
        })
        recyclerViewAnimation.adapter = HomeAdapter(filterMovies(movies, GenresEnum.ANIMATION.id), object : HomeAdapter.OnItemClickListener {
            override fun onItemClick(item: Movie) {
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra(DetailsActivity.MOVIE, item)
                startActivity(intent)
            }
        })
        recyclerViewComedy.adapter = HomeAdapter(filterMovies(movies, GenresEnum.COMEDY.id), object : HomeAdapter.OnItemClickListener {
            override fun onItemClick(item: Movie) {
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra(DetailsActivity.MOVIE, item)
                startActivity(intent)
            }
        })
        recyclerViewDocumentary.adapter = HomeAdapter(filterMovies(movies, GenresEnum.DOCUMENTARY.id), object : HomeAdapter.OnItemClickListener {
            override fun onItemClick(item: Movie) {
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra(DetailsActivity.MOVIE, item)
                startActivity(intent)
            }
        })
        recyclerViewDrama.adapter = HomeAdapter(filterMovies(movies, GenresEnum.DRAMA.id), object : HomeAdapter.OnItemClickListener {
            override fun onItemClick(item: Movie) {
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra(DetailsActivity.MOVIE, item)
                startActivity(intent)
            }
        })
        recyclerViewHorror.adapter = HomeAdapter(filterMovies(movies, GenresEnum.HORROR.id), object : HomeAdapter.OnItemClickListener {
            override fun onItemClick(item: Movie) {
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra(DetailsActivity.MOVIE, item)
                startActivity(intent)
            }
        })
        recyclerViewRomance.adapter = HomeAdapter(filterMovies(movies, GenresEnum.ROMANCE.id), object : HomeAdapter.OnItemClickListener {
            override fun onItemClick(item: Movie) {
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra(DetailsActivity.MOVIE, item)
                startActivity(intent)
            }
        })
        recyclerViewThriller.adapter = HomeAdapter(filterMovies(movies, GenresEnum.THRILLER.id), object : HomeAdapter.OnItemClickListener {
            override fun onItemClick(item: Movie) {
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra(DetailsActivity.MOVIE, item)
                startActivity(intent)
            }
        })
        progressBar.visibility = View.GONE
    }

    private fun filterMovies(movies: List<Movie>, genreId: Int): MutableList<Movie> {
        return movies.filter {movie->
            movie.genreIds!!.contains(genreId)
        }.toMutableList()
    }
}