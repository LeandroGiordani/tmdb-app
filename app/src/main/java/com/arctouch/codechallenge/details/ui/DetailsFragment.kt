package com.arctouch.codechallenge.details.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.api.model.Movie
import com.arctouch.codechallenge.api.model.MovieImage
import com.arctouch.codechallenge.details.viewmodel.DetailsViewModel
import com.arctouch.codechallenge.details.viewmodel.DetailsViewModelFactory
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    private lateinit var movie: Movie

    private lateinit var detailsViewModel: DetailsViewModel

    private var detailsAdapter: DetailsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = arguments!!
        movie = args[MOVIE] as Movie
        detailsViewModel = ViewModelProvider(requireActivity(), DetailsViewModelFactory(movie)).get(DetailsViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this)
                .load(MovieImageUrlBuilder().buildBackdropUrl(movie.backdropPath))
                .into(imageViewMovieBanner)

        textViewMovieSynopses.text = movie.overview
        textViewMovieGenres.text = movie.genres?.joinToString(separator = ", ") { it.name }
        textViewMovieReleaseDate.text = movie.releaseDate
    }

    private fun observeViewModel() {
        detailsViewModel.images.observe(viewLifecycleOwner, Observer {
            val images = it ?: return@Observer
            setupCarousel(images)
        })

        detailsViewModel.getImages()
    }

    private fun setupCarousel(images: List<MovieImage>) {
        detailsAdapter = DetailsAdapter(images)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        moviePostersCarousel.layoutManager = layoutManager
        moviePostersCarousel.adapter = detailsAdapter
    }

    companion object {
        private const val MOVIE = "movie"

        fun newInstance(movie: Movie): DetailsFragment {
            val fragArgs = Bundle()
            fragArgs.putParcelable(MOVIE, movie)

            val fragment = DetailsFragment()
            fragment.arguments = fragArgs

            return fragment
        }
    }

}
