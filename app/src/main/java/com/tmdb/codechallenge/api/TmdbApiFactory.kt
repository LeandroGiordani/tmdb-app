package com.tmdb.codechallenge.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object TmdbApiFactory {

    fun buildApi(okHttpClient: OkHttpClient): TmdbApi {

        return Retrofit.Builder()
                .baseUrl(TmdbApi.URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(TmdbApi::class.java)

    }

}