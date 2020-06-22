package com.tmdb.codechallenge.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tmdb.codechallenge.api.model.Movie
import com.tmdb.codechallenge.data.MovieResult
import com.tmdb.codechallenge.home.domain.HomeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val homeUseCase: HomeUseCase) : ViewModel() {

    private val _moviesList = MutableLiveData<List<Movie>>()
    val moviesList: LiveData<List<Movie>>
        get() = _moviesList

    private val _failure = MutableLiveData<String>()
    val failure: LiveData<String>
        get() = _failure

    fun onCreate() {
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            homeUseCase.getMovies().let {
                when (it) {
                    is MovieResult.Success -> _moviesList.postValue(it.list)
                    is MovieResult.Failure -> _failure.postValue(it.errorMsg)
                }
            }
        }
    }
}