package com.arctouch.codechallenge.home.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.api.model.Movie
import com.arctouch.codechallenge.details.DetailsActivity
import com.arctouch.codechallenge.home.viewmodel.HomeViewModel
import com.arctouch.codechallenge.home.viewmodel.HomeViewModelFactory
import com.rockerhieu.rvadapter.endless.EndlessRecyclerViewAdapter
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private var homeAdapter: HomeAdapter? = null
    private var endlessRecyclerViewAdapter: EndlessRecyclerViewAdapter? = null
    private var currentPage = 1L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), HomeViewModelFactory()).get(HomeViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.getMovies(1L)
        viewModel.moviesList.observe(viewLifecycleOwner, Observer {
            val movies = it ?: return@Observer
            updateMovieList(movies)
        })
    }

    private fun updateMovieList(movies: List<Movie>) {
        if (homeAdapter == null){
            homeAdapter = HomeAdapter(movies.toMutableList(), object : HomeAdapter.OnItemClickListener {
                override fun onItemClick(item: Movie) {
                    val intent = Intent(context, DetailsActivity::class.java)
                    intent.putExtra(DetailsActivity.MOVIE, item)
                    startActivity(intent)
                }
            })
            endlessRecyclerViewAdapter = EndlessRecyclerViewAdapter(homeAdapter) {
                viewModel.getMovies(currentPage)
            }
            recyclerView.adapter = endlessRecyclerViewAdapter
            progressBar.visibility = View.GONE

        } else {
            homeAdapter?.insertItems(movies)
            endlessRecyclerViewAdapter?.onDataReady(true)
        }
        currentPage++
    }
}