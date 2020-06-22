package com.tmdb.codechallenge.details.domain

import com.tmdb.codechallenge.api.model.Movie
import com.tmdb.codechallenge.api.model.MovieImage
import com.tmdb.codechallenge.details.data.repository.MovieImagesRepository

class DetailsUseCase(private val imagesRepository: MovieImagesRepository) {

    suspend fun getImages(movie: Movie) : List<MovieImage> {
        return imagesRepository.getImages(movie)
    }
}