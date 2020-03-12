package com.arctouch.codechallenge.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.api.TmdbApiFactory
import com.arctouch.codechallenge.home.domain.HomeUseCase
import com.arctouch.codechallenge.home.repository.GenreRepository
import com.arctouch.codechallenge.home.repository.MoviesRepository
import okhttp3.OkHttpClient

class HomeViewModelFactory : ViewModelProvider.Factory {

    private val apiService: TmdbApi by lazy {
        TmdbApiFactory.buildApi(OkHttpClient().newBuilder().build())
    }

    private lateinit var homeUseCase: HomeUseCase
    private lateinit var moviesRepository: MoviesRepository
    private lateinit var genreRepository: GenreRepository

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            moviesRepository = MoviesRepository(apiService)
            genreRepository = GenreRepository(apiService)
            homeUseCase = HomeUseCase(moviesRepository, genreRepository)
            when {
                isAssignableFrom(HomeViewModel::class.java) ->
                    HomeViewModel(homeUseCase)
                else ->
                    throw IllegalArgumentException("Unknown Class")
            }
        } as T
}