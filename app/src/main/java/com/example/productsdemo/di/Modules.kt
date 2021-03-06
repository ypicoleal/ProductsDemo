package com.example.productsdemo.di

import com.example.domain.actions.LoadProductDescription
import com.example.domain.actions.LoadProductItem
import com.example.domain.actions.LoadProductSearch
import com.example.domain.actions.LoadSites
import com.example.domain.repositories.ProductRepository
import com.example.infrastructure.clients.ProductsClient
import com.example.infrastructure.network.ResponseHandler
import com.example.infrastructure.network.RetrofitBuilder
import com.example.infrastructure.repositories.ProductRepositoryImpl
import com.example.productsdemo.BuildConfig
import com.example.productsdemo.product.ProductItemViewModel
import com.example.productsdemo.search.ProductSearchViewModel
import com.example.productsdemo.site.SiteViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val viewModelsModule = module {
    viewModel { ProductItemViewModel(get(), get()) }
    viewModel { ProductSearchViewModel(get()) }
    viewModel { SiteViewModel(get()) }
}

val repositoriesModule = module {
    single<ProductRepository> { ProductRepositoryImpl(get(), get()) }
}

val actionsModule = module {
    single { LoadProductItem(get()) }
    single { LoadProductDescription(get()) }
    single { LoadProductSearch(get()) }
    single { LoadSites(get()) }
}

val networkModule = module {
    single { provideRetrofit() }
    single { provideProductsClientClient(get()) }
    factory { ResponseHandler(Dispatchers.IO) }
}

private fun provideProductsClientClient(retrofit: Retrofit): ProductsClient = retrofit.create(ProductsClient::class.java)

private fun provideRetrofit() =
    RetrofitBuilder(BuildConfig.BASE_URL).retrofit()
