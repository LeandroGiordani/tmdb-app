package com.tmdb.codechallenge.api.model

data class ImagesResponse(
        val posters: List<MovieImage>,
        val backdrops: List<MovieImage>
)