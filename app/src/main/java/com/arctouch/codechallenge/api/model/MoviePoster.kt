package com.arctouch.codechallenge.api.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoviePoster (
        val width: Int,
        val height: Int,
        @field:Json(name = "file_path") val filePath: String
) : Parcelable