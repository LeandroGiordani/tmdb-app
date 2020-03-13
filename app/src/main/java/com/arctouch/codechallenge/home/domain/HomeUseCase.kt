package com.arctouch.codechallenge.home.domain

import com.arctouch.codechallenge.api.model.Genre
import com.arctouch.codechallenge.api.model.Movie
import com.arctouch.codechallenge.data.MovieResult
import com.arctouch.codechallenge.home.repository.GenreRepository
import com.arctouch.codechallenge.home.repository.MoviesRepository
import kotlinx.coroutines.coroutineScope

class HomeUseCase(
        private val moviesRepository: MoviesRepository,
        private val genreRepository: GenreRepository
) {
    private var _page = 0L

    lateinit var movieResult: MovieResult

    suspend fun getMovies(page: Long, isCached: Boolean): MovieResult {
        val movies = mutableListOf<Movie>()
        var genres: List<Genre>

        if (isCached) {
            movies.addAll(moviesRepository.getCachedMovies())
        }

        if (page != _page) {
            _page = page
            coroutineScope {
                genres = if (genreRepository.getCachedGenres().isNotEmpty()) {
                    genreRepository.getCachedGenres()
                } else genreRepository.getGenres()
                movieResult = moviesRepository.getUpcomingMovies(page)
                movieResult = if (movieResult is MovieResult.Success) {
                    getMoviesWithGenres(movieResult, genres)
                } else
                    MovieResult.Failure(" error unknown")
            }
        }
        return movieResult
    }

    private fun getMoviesWithGenres(
            movieSuccess: MovieResult,
            genreList: List<Genre>
    ) : MovieResult {

        val movies = (movieSuccess as MovieResult.Success).list

        movies.forEach { movie ->
            movie.copy(genres = genreList.filter { movie.genreIds?.contains(it.id) == true })
        }
        return MovieResult.Success(movies)
    }
}