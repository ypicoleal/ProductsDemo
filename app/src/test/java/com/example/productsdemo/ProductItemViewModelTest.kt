package com.example.productsdemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.actions.LoadProductDescription
import com.example.domain.actions.LoadProductItem
import com.example.domain.models.ProductItem
import com.example.productsdemo.product.ProductItemViewModel
import com.example.productsdemo.utils.CoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProductItemViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutineRule()

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private lateinit var viewModel: ProductItemViewModel

    @MockK
    internal lateinit var loadProductItem: LoadProductItem

    @MockK
    internal lateinit var loadProductDescription: LoadProductDescription

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = ProductItemViewModel(loadProductItem, loadProductDescription)
    }

    @Test
    fun `verify data when loadData returns Success`() = runBlockingTest {
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
        coEvery { loadProductItem(productId) } returns LoadProductItem.Result.Success(productItem)

        // when
        viewModel.loadData(productId)

        // then
        viewModel.data.value shouldBeEqualTo productItem
    }

    @Test
    fun `verify data when loadData returns Error`() = runBlockingTest {
        // given
        val productId = "id"
        coEvery { loadProductItem(productId) } returns LoadProductItem.Result.Error

        // when
        viewModel.loadData(productId)

        // then
        viewModel.errorData.value shouldBeEqualTo "error"
    }

    @Test
    fun `verify data when loadData returns NetworkError`() = runBlockingTest {
        // given
        val productId = "id"
        coEvery { loadProductItem(productId) } returns LoadProductItem.Result.NetworkError

        // when
        viewModel.loadData(productId)

        // then
        viewModel.errorData.value shouldBeEqualTo "network error"
    }

    @Test
    fun `verify data when loadDescription returns Success`() = runBlockingTest {
        // given
        val productId = "id"
        val description = "description"
        coEvery { loadProductDescription(productId) } returns LoadProductDescription.Result.Success(description)

        // when
        viewModel.loadDescription(productId)

        // then
        viewModel.description.value shouldBeEqualTo description
    }

    @Test
    fun `verify data when getSites returns Error`() = runBlockingTest {
        // given
        val productId = "id"

        coEvery { loadProductDescription(productId) } returns LoadProductDescription.Result.Error
        // when
        viewModel.loadDescription(productId)

        // then
        viewModel.errorData.value shouldBeEqualTo "error"
    }

    @Test
    fun `verify data when getSites returns NetworkError`() = runBlockingTest {
        // given
        val productId = "id"

        coEvery { loadProductDescription(productId) } returns LoadProductDescription.Result.NetworkError
        // when
        viewModel.loadDescription(productId)

        // then
        viewModel.errorData.value shouldBeEqualTo "network error"
    }
}