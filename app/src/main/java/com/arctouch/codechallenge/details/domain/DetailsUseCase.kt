package com.arctouch.codechallenge.details.domain

import com.arctouch.codechallenge.api.model.Movie
import com.arctouch.codechallenge.api.model.MovieImage
import com.arctouch.codechallenge.details.data.repository.MovieImagesRepository

class DetailsUseCase(private val imagesRepository: MovieImagesRepository) {

    suspend fun getImages(movie: Movie) : List<MovieImage> {
        return imagesRepository.getImages(movie)
    }
}