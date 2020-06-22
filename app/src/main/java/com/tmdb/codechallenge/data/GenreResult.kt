package com.tmdb.codechallenge.data

import com.tmdb.codechallenge.api.model.Genre

sealed class GenreResult {
    data class Success(val list: List<Genre>) : GenreResult()
    data class Failure(val errorMsg: String) : GenreResult()
}