package com.tmdb.codechallenge.details.data.repository;

import com.tmdb.codechallenge.api.TmdbApi;
import com.tmdb.codechallenge.api.model.ImagesResponse;
import com.tmdb.codechallenge.api.model.MovieImage;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.internal.Intrinsics;

public class MovieImagesRepository2 {
    private TmdbApi tmdbApi;

    public MovieImagesRepository2(@NotNull TmdbApi tmdbApi) {
        Intrinsics.checkParameterIsNotNull(tmdbApi, "tmdbApi");
        this.tmdbApi = tmdbApi;
    }

//    @Nullable
//    public List<MovieImage> getImages(@NotNull Movie movie) {
//
//        MovieImagesCache cache = new MovieImagesCache();
//    }

    @Nullable
    private List<MovieImage> loadImages(long moveId) {
        return new ArrayList<>();
    }

    private List<MovieImage> saveImages(long id, ImagesResponse body) {
        return new ArrayList<>();
    }
}

