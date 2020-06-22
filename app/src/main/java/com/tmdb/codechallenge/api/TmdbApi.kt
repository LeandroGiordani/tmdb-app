package com.tmdb.codechallenge.api

import com.tmdb.codechallenge.api.model.GenreResponse
import com.tmdb.codechallenge.api.model.ImagesResponse
import com.tmdb.codechallenge.api.model.UpcomingMoviesResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    //Used this article as a reference to Coroutines: https://link.medium.com/92TkcLE1G4

    companion object {
        const val URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "1f54bd990f1cdfb230adb312546d765d"
        const val DEFAULT_LANGUAGE = "en-US"
        const val DEFAULT_REGION = "US"
    }

    @GET("genre/movie/list")
    fun getGenresAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Deferred<Response<GenreResponse>>

    @GET("movie/upcoming")
    fun getUpcomingMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String?,
        @Query("region") region: String?
    ): Deferred<Response<UpcomingMoviesResponse>>

    @GET("movie/{id}/images")
    fun getMovieImagesAsync(
        @Path("id") id: Long,
        @Query("api_key") apiKey: String
    ): Deferred<Response<ImagesResponse>>
}
