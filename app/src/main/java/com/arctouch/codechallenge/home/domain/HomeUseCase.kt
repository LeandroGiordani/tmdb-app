package com.arctouch.codechallenge.home.domain

import com.arctouch.codechallenge.home.repository.GenreRepository
import com.arctouch.codechallenge.home.repository.MoviesRepository

class HomeUseCase(
        private val moviesRepository: MoviesRepository,
        private val genreRepository: GenreRepository
) {
    private var _page = 0L

    suspend fun getMovies(page: Long, isCached: Boolean) {

    }
}