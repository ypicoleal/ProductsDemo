package com.example.domain

import com.example.domain.actions.LoadProductItem
import com.example.domain.models.Attribute
import com.example.domain.models.ProductItem
import com.example.domain.models.ProductVariation
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
class LoadProductItemTest {

    private lateinit var loadProductItem: LoadProductItem

    @MockK
    internal lateinit var productRepository: ProductRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        loadProductItem = LoadProductItem(productRepository)
    }

    @Test
    fun `should returns ResultSuccess request when getSites returns Success`() = runBlockingTest {
        // given
        val productId = "id"
        val productItem = ProductItem(
                id = "id",
                title = "title",
                condition = "condition",
                soldQuantity = 0,
                images = emptyList(),
                variations = emptyList(),
                originalPrice = 0,
                price = 0,
                attributes = emptyList()
        )
        coEvery { productRepository.getProductItem(productId) } returns ResultWrapper.Success(productItem)

        // when
        val result = loadProductItem(productId)

        // then
        result shouldBeEqualTo LoadProductItem.Result.Success(productItem)
    }

    @Test
    fun `should returns ResultError request when getSites returns Error`() = runBlockingTest {
        // given
        val productId = "id"
        coEvery { productRepository.getProductItem(productId) } returns ResultWrapper.Error()

        // when
        val result = loadProductItem(productId)

        // then
        result shouldBeEqualTo LoadProductItem.Result.Error
    }

    @Test
    fun `should returns NetworkError request when getSites returns NetworkError`() = runBlockingTest {
        // given
        val productId = "id"
        coEvery { productRepository.getProductItem(productId) } returns ResultWrapper.NetworkError

        // when
        val result = loadProductItem(productId)

        // then
        result shouldBeEqualTo LoadProductItem.Result.NetworkError
    }
}