package com.arctouch.codechallenge.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arctouch.codechallenge.api.model.Movie
import com.arctouch.codechallenge.data.MovieResult
import com.arctouch.codechallenge.home.domain.HomeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val homeUseCase: HomeUseCase) : ViewModel() {

    private val _moviesList = MutableLiveData<List<Movie>>()
    val moviesList: LiveData<List<Movie>>
        get() = _moviesList

//    private val _failure = MutableLiveData<MovieResult.Failure>()
//    val failure: LiveData<MovieResult.Failure>
//        get() = _failure

    fun getMovies(page: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = homeUseCase.getMovies(page)) {
                is MovieResult.Success -> _moviesList.postValue(result.list)
//                is MovieResult.Failure -> _failure.postValue(result)
            }
        }
    }
}