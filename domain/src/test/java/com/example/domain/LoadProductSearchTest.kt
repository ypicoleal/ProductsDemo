package com.example.domain

import com.example.domain.actions.LoadProductSearch
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
class LoadProductSearchTest {

    private lateinit var loadProductSearch: LoadProductSearch

    @MockK
    internal lateinit var productRepository: ProductRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        loadProductSearch = LoadProductSearch(productRepository)
    }

    @Test
    fun `should returns ResultSuccess request when getSites returns Success`() = runBlockingTest {
        // given
        val search = "search"
        val site = "site"
        coEvery { productRepository.getProductSearch(search, site) } returns ResultWrapper.Success(listOf())

        // when
        val result = loadProductSearch(search, site)

        // then
        result shouldBeEqualTo LoadProductSearch.Result.Success(listOf())
    }

    @Test
    fun `should returns ResultError request when getSites returns Error`() = runBlockingTest {
        // given
        val search = "search"
        val site = "site"
        coEvery { productRepository.getProductSearch(search, site) } returns ResultWrapper.Error()

        // when
        val result = loadProductSearch(search, site)

        // then
        result shouldBeEqualTo LoadProductSearch.Result.Error
    }

    @Test
    fun `should returns NetworkError request when getSites returns NetworkError`() = runBlockingTest {
        // given
        val search = "search"
        val site = "site"
        coEvery { productRepository.getProductSearch(search, site) } returns ResultWrapper.NetworkError

        // when
        val result = loadProductSearch(search, site)

        // then
        result shouldBeEqualTo LoadProductSearch.Result.NetworkError
    }
}