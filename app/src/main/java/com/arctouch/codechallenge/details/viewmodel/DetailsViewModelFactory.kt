package com.arctouch.codechallenge.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.api.TmdbApiFactory
import com.arctouch.codechallenge.api.model.Movie
import com.arctouch.codechallenge.details.data.repository.MovieImagesRepository
import com.arctouch.codechallenge.details.domain.DetailsUseCase
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