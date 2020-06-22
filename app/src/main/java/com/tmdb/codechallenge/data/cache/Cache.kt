package com.tmdb.codechallenge.data.cache

import com.tmdb.codechallenge.api.model.Genre
import com.tmdb.codechallenge.api.model.Movie

object Cache {

    var genres = listOf<Genre>()
    var movies = listOf<Movie>()

    fun cacheGenres(genres: List<Genre>) {
        Cache.genres = genres
    }

    fun cacheMovies(movies: List<Movie>) {
        Cache.movies = movies
    }
}
