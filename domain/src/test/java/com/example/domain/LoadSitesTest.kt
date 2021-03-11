package com.example.domain

import com.example.domain.actions.LoadSites
import com.example.domain.models.ResultWrapper
import com.example.domain.repositories.ProductRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LoadSitesTest {

    private lateinit var loadSites: LoadSites

    @MockK
    internal lateinit var productRepository: ProductRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        loadSites = LoadSites(productRepository)
    }

    @Test
    fun `should returns ResultSuccess request when getSites returns Success`() = runBlockingTest {
        // given
        coEvery { productRepository.getSites() } returns ResultWrapper.Success(listOf())

        // when
        val result = loadSites()

        // then
        result shouldBeEqualTo LoadSites.Result.Success(listOf())
    }

    @Test
    fun `should returns ResultError request when getSites returns Error`() = runBlockingTest {
        // given
        coEvery { productRepository.getSites() } returns ResultWrapper.Error()

        // when
        val result = loadSites()

        // then
        result shouldBeEqualTo LoadSites.Result.Error
    }

    @Test
    fun `should returns NetworkError request when getSites returns NetworkError`() = runBlockingTest {
        // given
        coEvery { productRepository.getSites() } returns ResultWrapper.NetworkError

        // when
        val result = loadSites()

        // then
        result shouldBeEqualTo LoadSites.Result.NetworkError
    }
}