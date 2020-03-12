package com.arctouch.codechallenge.data.cache

import com.arctouch.codechallenge.api.model.Genre
import com.arctouch.codechallenge.api.model.Movie

object Cache {

    var movies = listOf<Movie>()
    var genres = listOf<Genre>()

    fun cacheMovies(movies: List<Movie>) {
        Cache.movies = movies
    }

    fun cacheGenres(genres: List<Genre>) {
        Cache.genres = genres
    }
}
