package com.tmdb.codechallenge.api.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UpcomingMoviesResponse(
        val results: List<Movie>,
        @Json(name = "total_results") val totalResults: Int
) : Parcelable