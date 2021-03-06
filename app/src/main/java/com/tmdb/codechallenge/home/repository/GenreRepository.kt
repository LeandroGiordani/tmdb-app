package com.tmdb.codechallenge.home.repository

import com.tmdb.codechallenge.api.TmdbApi
import com.tmdb.codechallenge.api.model.Genre
import com.tmdb.codechallenge.data.GenreResult
import com.tmdb.codechallenge.data.cache.Cache

class GenreRepository(private val apiService: TmdbApi) {

    suspend fun getGenres(): GenreResult {
        return try {
            if (getCachedGenres().isNotEmpty()) {
                GenreResult.Success(getCachedGenres())
            } else {
                val result = apiService.getGenresAsync(
                        TmdbApi.API_KEY,
                        TmdbApi.DEFAULT_LANGUAGE
                ).await()

                val resultBody = result.body()
                if (result.isSuccessful && resultBody != null) {
                    saveGenres(resultBody.genres)
                    GenreResult.Success(resultBody.genres)
                } else {
                    GenreResult.Failure("empty list")
                }
            }
        } catch (error: Error) {
            GenreResult.Failure("Failure: ${error.message}")
        }
    }

    private fun getCachedGenres(): List<Genre> {
        return Cache.genres
    }

    private fun saveGenres(genres: List<Genre>) {
        Cache.cacheGenres((genres).toList())
    }
}