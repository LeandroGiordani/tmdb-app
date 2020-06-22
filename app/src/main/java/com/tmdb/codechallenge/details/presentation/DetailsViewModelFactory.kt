package com.tmdb.codechallenge.details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tmdb.codechallenge.api.TmdbApi
import com.tmdb.codechallenge.api.TmdbApiFactory
import com.tmdb.codechallenge.api.model.Movie
import com.tmdb.codechallenge.details.data.repository.MovieImagesRepository
import com.tmdb.codechallenge.details.domain.DetailsUseCase
import okhttp3.OkHttpClient

class DetailsViewModelFactory(private val movie: Movie) : ViewModelProvider.Factory {

    private val apiService: TmdbApi by lazy {
        TmdbApiFactory.buildApi(OkHttpClient().newBuilder().build())
    }

    private lateinit var movieImagesRepository: MovieImagesRepository
    private lateinit var detailsUseCase: DetailsUseCase

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            movieImagesRepository = MovieImagesRepository(apiService)
            detailsUseCase = DetailsUseCase(movieImagesRepository)
            when {
                isAssignableFrom(DetailsViewModel::class.java) ->
                    DetailsViewModel(movie, detailsUseCase)
                else ->
                    throw IllegalArgumentException("Unknown Class")
            }
        } as T
}