package com.arctouch.codechallenge.home.repository

import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.api.model.Movie
import com.arctouch.codechallenge.data.MovieResult
import com.arctouch.codechallenge.data.cache.Cache

class MoviesRepository(private val apiService: TmdbApi) {

    suspend fun getUpcomingMovies(page: Long): MovieResult {
        try {
            val result = apiService.getUpcomingMovies(
                    TmdbApi.API_KEY,
                    TmdbApi.DEFAULT_LANGUAGE,
                    page,
                    TmdbApi.DEFAULT_REGION
            ).await()

            result.body()?.let {
                when {
                    result.isSuccessful -> {
                        when {
                            it.results.isNotEmpty() -> {
                                Cache.cacheMovies(it.results)
                                return MovieResult.Success(it.results)
                            }
                            Cache.movies.isNotEmpty() -> {
                                return MovieResult.Success(Cache.movies)
                            }
                            else -> MovieResult.Failure("No Movies")
                        }
                    }
                    else -> return MovieResult.Failure("No Movies")
                }
            }
        } catch (error: Exception) {
            return MovieResult.Failure("failure msg: ${error.message}")
        }
        return MovieResult.Failure("unknown failure")
    }

    fun getCachedMovies(): List<Movie> {
        return Cache.movies
    }

    fun saveMovies(movies: List<Movie>) {
        Cache.cacheMovies(Cache.movies.union(movies).toList())
    }
}