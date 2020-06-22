package com.tmdb.codechallenge.details.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tmdb.codechallenge.api.model.Movie
import com.tmdb.codechallenge.api.model.MovieImage
import com.tmdb.codechallenge.details.domain.DetailsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(
        private val movie: Movie,
        private val detailsUseCase: DetailsUseCase
) : ViewModel() {

    private val _images = MutableLiveData<List<MovieImage>>()
    val images: LiveData<List<MovieImage>>
        get() = _images

    fun getImages() {
        viewModelScope.launch(Dispatchers.IO) {
            _images.postValue(detailsUseCase.getImages(movie))
        }
    }
}