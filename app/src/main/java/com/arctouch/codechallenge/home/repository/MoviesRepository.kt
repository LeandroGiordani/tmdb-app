package com.arctouch.codechallenge.home.repository

import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.data.MovieResult

class MoviesRepository(private val apiService: TmdbApi) {

    suspend fun getUpcomingMovies(page: Long): MovieResult {
        try {
            val result = apiService.getUpcomingMoviesAsync(
                    TmdbApi.API_KEY,
                    TmdbApi.DEFAULT_LANGUAGE,
                    page,
                    TmdbApi.DEFAULT_REGION
            ).await()

            result.body()?.let {
                when {
                    result.isSuccessful -> {
                        if (it.results.isNotEmpty()) {
                            //Cache.cacheMovies(it.results)
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