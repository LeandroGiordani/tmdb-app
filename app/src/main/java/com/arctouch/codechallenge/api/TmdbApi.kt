package com.arctouch.codechallenge.api

import com.arctouch.codechallenge.api.model.GenreResponse
import com.arctouch.codechallenge.api.model.Movie
import com.arctouch.codechallenge.api.model.UpcomingMoviesResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    //Used this article as a reference to change RxJava to Coroutines: https://link.medium.com/92TkcLE1G4

    companion object {
        const val URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "1f54bd990f1cdfb230adb312546d765d"
        const val DEFAULT_LANGUAGE = "en-US"
        const val DEFAULT_REGION = "US"
    }

    @GET("genre/movie/list")
    fun getGenres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Deferred<Response<GenreResponse>>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Long,
        @Query("region") region: String
    ): Deferred<Response<UpcomingMoviesResponse>>

    @GET("movie/{id}")
    fun getMovie(
        @Path("id") id: Long,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Deferred<Response<Movie>>
}
