package com.arctouch.codechallenge.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arctouch.codechallenge.api.model.Movie
import com.arctouch.codechallenge.home.domain.HomeUseCase

class HomeViewModel(private val homeUseCase: HomeUseCase) : ViewModel() {

    private var currentPage = 1L

    private val _moviesList = MutableLiveData<List<Movie>>()
    val moviesList: LiveData<List<Movie>>
        get() = _moviesList


}