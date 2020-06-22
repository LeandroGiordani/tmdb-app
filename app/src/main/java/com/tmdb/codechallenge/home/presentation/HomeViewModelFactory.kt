package com.tmdb.codechallenge.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tmdb.codechallenge.api.TmdbApi
import com.tmdb.codechallenge.api.TmdbApiFactory
import com.tmdb.codechallenge.home.domain.HomeUseCase
import com.tmdb.codechallenge.home.repository.GenreRepository
import com.tmdb.codechallenge.home.repository.MoviesRepository
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