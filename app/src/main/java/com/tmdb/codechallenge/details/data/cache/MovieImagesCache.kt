package com.tmdb.codechallenge.details.data.cache

import com.tmdb.codechallenge.api.model.MovieImage

object MovieImagesCache {

    private val images = hashMapOf<Long, List<MovieImage>>()

    fun getImages(movieId: Long): List<MovieImage>? {
        return images[movieId]
    }

    fun saveImages(movieId: Long, images: List<MovieImage>) {
        this.images[movieId] = images
    }
}