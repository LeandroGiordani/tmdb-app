package com.arctouch.codechallenge.data

import com.arctouch.codechallenge.api.model.Movie

sealed class MovieResult {
    data class Success(val list: List<Movie>) : MovieResult()
    data class Failure(val errorMsg: String) : MovieResult()
}