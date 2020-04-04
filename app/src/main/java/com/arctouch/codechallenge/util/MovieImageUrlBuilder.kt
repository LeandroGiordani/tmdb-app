package com.arctouch.codechallenge.util

import com.arctouch.codechallenge.api.TmdbApi

private const val HOME_POSTER_URL = "https://image.tmdb.org/t/p/w154"
private const val BACKDROP_URL = "https://image.tmdb.org/t/p/w780"
private const val DETAILS_POSTER_CAROUSEL_URL = "https://image.tmdb.org/t/p/w500"

class MovieImageUrlBuilder {

    fun buildPosterUrl(posterPath: String): String {
        return HOME_POSTER_URL + posterPath + "?api_key=" + TmdbApi.API_KEY
    }

    fun buildBackdropUrl(backdropPath: String?): String {
        return BACKDROP_URL + backdropPath + "?api_key=" + TmdbApi.API_KEY
    }

    fun buildDetailsPosterUrl(posterPath: String): String {
        return DETAILS_POSTER_CAROUSEL_URL + posterPath + "?api_key=" + TmdbApi.API_KEY
    }
}
