package com.tmdb.codechallenge.details.data.repository

import com.tmdb.codechallenge.api.TmdbApi
import com.tmdb.codechallenge.api.model.ImagesResponse
import com.tmdb.codechallenge.api.model.Movie
import com.tmdb.codechallenge.api.model.MovieImage
import com.tmdb.codechallenge.details.data.cache.MovieImagesCache

class MovieImagesRepository(private val tmdbApi: TmdbApi) {

    suspend fun getImages(movie: Movie): List<MovieImage> {

        val images = mutableListOf<MovieImage>()
        MovieImagesCache.getImages(movie.id)?.let {
            images.addAll(it)
        }
        if (images.isEmpty()) {
            loadImages(movie.id).let {
                images.addAll(it)
            }
        }
        return images
    }

    private suspend fun loadImages(moveId: Long): List<MovieImage> {

        return try {
            val result = tmdbApi.getMovieImagesAsync(moveId, TmdbApi.API_KEY).await()
            val body = result.body()
            if (result.isSuccessful && body != null) {
                saveImages(moveId, body)
            } else {
                emptyList()
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            emptyList()
        }
    }

    private fun saveImages(id: Long, body: ImagesResponse): List<MovieImage> {
        val images = (body.posters.union(body.backdrops)).toMutableList().apply { shuffle() }
        MovieImagesCache.saveImages(id, images)
        return images
    }
}