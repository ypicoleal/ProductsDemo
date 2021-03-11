package com.example.productsdemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.actions.LoadProductSearch
import com.example.productsdemo.search.ProductSearchViewModel
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
class ProductSearchViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutineRule()

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private lateinit var viewModel: ProductSearchViewModel

    @MockK
    internal lateinit var loadProductSearch: LoadProductSearch

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = ProductSearchViewModel(loadProductSearch)
    }

    @Test
    fun `verify data when searchProducts returns Success`() = runBlockingTest {
        // given
        val query = "query"
        val site = "site"
        coEvery { loadProductSearch(query, site) } returns LoadProductSearch.Result.Success(emptyList())

        // when
        viewModel.searchProducts(query, site)

        // then
        viewModel.data.value shouldBeEqualTo emptyList()
    }

    @Test
    fun `verify data when searchProducts returns Error`() = runBlockingTest {
        // given
        val query = "query"
        val site = "site"
        coEvery { loadProductSearch(query, site) } returns LoadProductSearch.Result.Error

        // when
        viewModel.searchProducts(query, site)

        // then
        viewModel.errorData.value shouldBeEqualTo false
    }

    @Test
    fun `verify data when searchProducts returns NetworkError`() = runBlockingTest {
        // given
        val query = "query"
        val site = "site"
        coEvery { loadProductSearch(query, site) } returns LoadProductSearch.Result.NetworkError

        // when
        viewModel.searchProducts(query, site)

        // then
        viewModel.errorData.value shouldBeEqualTo true
    }
}