package com.arctouch.codechallenge

import com.arctouch.codechallenge.data.MovieResult
import com.arctouch.codechallenge.home.domain.HomeUseCase
import com.arctouch.codechallenge.home.repository.GenreRepository
import com.arctouch.codechallenge.home.repository.MoviesRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class HomeUseCaseTest {

    private lateinit var homeUseCase: HomeUseCase

    private val moviesRepository: MoviesRepository = mock()
    private val genreRepository: GenreRepository = mock()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        homeUseCase = HomeUseCase(moviesRepository, genreRepository)
    }

    @Test
    fun `check if UseCase return failure when try to upload same page`() = runBlocking {
        whenever(moviesRepository.getUpcomingMovies(1)).thenReturn(MovieResult.Failure("Page already loaded"))
        assertEquals(MovieResult.Failure("Page already loaded"), homeUseCase.getMovies(1))
    }
}