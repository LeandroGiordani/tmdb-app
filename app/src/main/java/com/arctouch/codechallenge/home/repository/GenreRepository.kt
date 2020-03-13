package com.arctouch.codechallenge.home.repository

import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.api.model.Genre
import com.arctouch.codechallenge.data.cache.Cache

class GenreRepository(private val apiService: TmdbApi) {
    suspend fun getGenres(): List<Genre>{
        var genres = listOf<Genre>()
        try {
            val result = apiService.getGenres(
                    TmdbApi.API_KEY,
                    TmdbApi.DEFAULT_LANGUAGE
            ).await()

            result.body()?.let {
                if (result.isSuccessful) {
                    saveGenres(it.genres)
                    genres = it.genres
                }
            }
        } catch (error: Error) {
            error.printStackTrace()
        }
        return genres
    }

    fun getCachedGenres(): List<Genre> {
        return Cache.genres
    }

    private fun saveGenres(genres: List<Genre>) {
        Cache.cacheGenres(Cache.genres.union(genres).toList())
    }
}