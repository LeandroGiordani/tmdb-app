package com.arctouch.codechallenge.home.domain

import com.arctouch.codechallenge.api.model.Genre
import com.arctouch.codechallenge.data.GenreResult
import com.arctouch.codechallenge.data.MovieResult
import com.arctouch.codechallenge.home.repository.GenreRepository
import com.arctouch.codechallenge.home.repository.MoviesRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class HomeUseCase(
        private val moviesRepository: MoviesRepository,
        private val genreRepository: GenreRepository
) {
    private var _page = 0L

    suspend fun getMovies(page: Long): MovieResult {
        lateinit var genreResult: GenreResult
        lateinit var movieResult: MovieResult

        if (page != _page) {
            _page = page
            coroutineScope {
                val defGenres = async { genreResult = genreRepository.getGenres() }
                val defMovies = async { movieResult = moviesRepository.getUpcomingMovies(page) }
                defGenres.await()
                defMovies.await()
            }

            return when {
                movieResult is MovieResult.Success && genreResult is GenreResult.Success -> getMoviesWithGenres(movieResult, genreResult)
                movieResult is MovieResult.Failure || genreResult is GenreResult.Failure -> movieResult
                else -> MovieResult.Failure("unknown failure")
            }
        } else {
            return MovieResult.Failure("Page already loaded")
        }
    }

    private fun getMoviesWithGenres(
            movieSuccess: MovieResult,
            genreSuccess: GenreResult
    ) : MovieResult {

        val movies = (movieSuccess as MovieResult.Success).list
        val genres = (genreSuccess as GenreResult.Success).list

        movies.forEach { movie ->
            val movieGenres = mutableListOf<Genre>()
            movie.genreIds?.forEach { genreId ->
                val genre = genres.find { it.id == genreId }
                if (genre != null) movieGenres.add(genre)
            }
            movie.genres = movieGenres
        }
        return MovieResult.Success(movies)
    }
}