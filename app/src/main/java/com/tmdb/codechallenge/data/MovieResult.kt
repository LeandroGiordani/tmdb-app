package com.tmdb.codechallenge.data

import com.tmdb.codechallenge.api.model.Movie

sealed class MovieResult {
    data class Success(val list: List<Movie>) : MovieResult()
    data class Failure(val errorMsg: String) : MovieResult()
}