package com.tmdb.codechallenge.home.repository

import com.tmdb.codechallenge.api.TmdbApi
import com.tmdb.codechallenge.data.MovieResult
import com.tmdb.codechallenge.data.cache.Cache

class MoviesRepository(private val apiService: TmdbApi) {

    suspend fun getUpcomingMovies(): MovieResult {
        try {
            val result = apiService.getUpcomingMoviesAsync(
                    TmdbApi.API_KEY,
                    TmdbApi.DEFAULT_LANGUAGE,
                    TmdbApi.DEFAULT_REGION
            ).await()

            result.body()?.let {
                when {
                    result.isSuccessful -> {
                        if (it.results.isNotEmpty()) {
                            Cache.movies = it.results
                            return MovieResult.Success(it.results)
                        }
                        else MovieResult.Failure("No Movies")
                    }
                    else -> return MovieResult.Failure("No Movies")
                }
            }
        } catch (error: Exception) {
            return MovieResult.Failure("failure msg: ${error.message}")
        }
        return MovieResult.Failure("unknown failure")
    }
}