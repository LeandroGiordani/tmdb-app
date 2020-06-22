package com.tmdb.codechallenge.home.domain

import com.tmdb.codechallenge.api.model.Genre
import com.tmdb.codechallenge.data.GenreResult
import com.tmdb.codechallenge.data.MovieResult
import com.tmdb.codechallenge.data.cache.Cache
import com.tmdb.codechallenge.home.repository.GenreRepository
import com.tmdb.codechallenge.home.repository.MoviesRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class HomeUseCase(
        private val moviesRepository: MoviesRepository,
        private val genreRepository: GenreRepository
) {

    suspend fun getMovies(): MovieResult {

        if (Cache.movies.isNotEmpty()) {
            return MovieResult.Success(Cache.movies)
        }
        lateinit var genreResult: GenreResult
        lateinit var movieResult: MovieResult

        coroutineScope {
            val defGenres = async { genreResult = genreRepository.getGenres() }
            val defMovies = async { movieResult = moviesRepository.getUpcomingMovies() }
            defGenres.await()
            defMovies.await()
        }

        return when {
            movieResult is MovieResult.Success && genreResult is GenreResult.Success -> getMoviesWithGenres(movieResult, genreResult)
            movieResult is MovieResult.Failure || genreResult is GenreResult.Failure -> movieResult
            else -> MovieResult.Failure("unknown failure")
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