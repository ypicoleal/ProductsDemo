package com.example.domain.actions

import com.example.domain.model.ProductSearchItem
import com.example.domain.model.ResultWrapper
import com.example.domain.repositories.ProductRepository

class LoadProductSearch(private val productRepository: ProductRepository) {
    sealed class Result {
        data class Success(val items: List<ProductSearchItem>) : Result()
        object NetworkError : Result()
        object Error : Result()
    }

    suspend operator fun invoke(query: String, site: String): Result {
        return when (val resultWrapper = productRepository.getProductSearch(query, site)) {
            is ResultWrapper.Success -> {
                Result.Success(resultWrapper.value)
            }
            is ResultWrapper.NetworkError -> Result.NetworkError
            else -> Result.Error
        }
    }
}
