package com.tmdb.codechallenge

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.tmdb.codechallenge.data.MovieResult
import com.tmdb.codechallenge.home.domain.HomeUseCase
import com.tmdb.codechallenge.home.repository.GenreRepository
import com.tmdb.codechallenge.home.repository.MoviesRepository
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
    fun `check if UseCase return failure when try to upload list`() = runBlocking {
        whenever(moviesRepository.getUpcomingMovies()).thenReturn(MovieResult.Failure("Page already loaded"))
        assertEquals(MovieResult.Failure("Page already loaded"), homeUseCase.getMovies())
    }
}