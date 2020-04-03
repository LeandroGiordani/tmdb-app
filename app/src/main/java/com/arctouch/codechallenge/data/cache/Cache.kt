package com.arctouch.codechallenge.data.cache

import com.arctouch.codechallenge.api.model.Genre
import com.arctouch.codechallenge.api.model.Movie

object Cache {

    var genres = listOf<Genre>()

    fun cacheGenres(genres: List<Genre>) {
        Cache.genres = genres
    }
}
