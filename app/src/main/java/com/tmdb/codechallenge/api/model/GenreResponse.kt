package com.tmdb.codechallenge.api.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenreResponse(val genres: List<Genre>) : Parcelable