package com.example.domain

import com.example.domain.actions.LoadProductDescription
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
class LoadProductDescriptionTest {

    private lateinit var loadProductDescription: LoadProductDescription

    @MockK
    internal lateinit var productRepository: ProductRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        loadProductDescription = LoadProductDescription(productRepository)
    }

    @Test
    fun `should returns ResultSuccess request when getSites returns Success`() = runBlockingTest {
        // given
        val productId = "id"
        val description = "description"
        coEvery { productRepository.getProductDescription(productId) } returns ResultWrapper.Success(description)

        // when
        val result = loadProductDescription(productId)

        // then
        result shouldBeEqualTo LoadProductDescription.Result.Success(description)
    }

    @Test
    fun `should returns ResultError request when getSites returns Error`() = runBlockingTest {
        // given
        val productId = "id"
        coEvery { productRepository.getProductDescription(productId) } returns ResultWrapper.Error()

        // when
        val result = loadProductDescription(productId)

        // then
        result shouldBeEqualTo LoadProductDescription.Result.Error
    }

    @Test
    fun `should returns NetworkError request when getSites returns NetworkError`() = runBlockingTest {
        // given
        val productId = "id"
        coEvery { productRepository.getProductDescription(productId) } returns ResultWrapper.NetworkError

        // when
        val result = loadProductDescription(productId)

        // then
        result shouldBeEqualTo LoadProductDescription.Result.NetworkError
    }
}