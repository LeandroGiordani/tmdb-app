package com.arctouch.codechallenge.details.domain

import com.arctouch.codechallenge.api.model.MoviePoster

class DetailsUseCase {

    suspend fun getPosters() : List<MoviePoster> {
        return emptyList()
    }
}