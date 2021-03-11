package com.example.productsdemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.actions.LoadSites
import com.example.productsdemo.site.SiteViewModel
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
class SiteViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutineRule()

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private lateinit var viewModel: SiteViewModel

    @MockK
    internal lateinit var loadSites: LoadSites

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = SiteViewModel(loadSites)
    }

    @Test
    fun `verify data when getSites returns Success`() = runBlockingTest {
        // given
        coEvery { loadSites() } returns LoadSites.Result.Success(emptyList())

        // when
        viewModel.getSites()

        // then
        viewModel.data.value shouldBeEqualTo emptyList()
    }

    @Test
    fun `verify data when getSites returns Error`() = runBlockingTest {
        // given
        coEvery { loadSites() } returns LoadSites.Result.Error

        // when
        viewModel.getSites()

        // then
        viewModel.errorData.value shouldBeEqualTo false
    }

    @Test
    fun `verify data when getSites returns NetworkError`() = runBlockingTest {
        // given
        coEvery { loadSites() } returns LoadSites.Result.NetworkError

        // when
        viewModel.getSites()

        // then
        viewModel.errorData.value shouldBeEqualTo true
    }
}