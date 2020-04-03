package com.arctouch.codechallenge.details.data.cache

import com.arctouch.codechallenge.api.model.MovieImage

object MovieImagesCache {

    private val images = hashMapOf<Long, List<MovieImage>>()

    fun getImages(movieId: Long): List<MovieImage>? {
        return images[movieId]
    }

    fun saveImages(movieId: Long, images: List<MovieImage>) {
        this.images[movieId] = images
    }
}