package com.example.domain.actions

import com.example.domain.models.ResultWrapper
import com.example.domain.repositories.ProductRepository

class LoadProductDescription(private val productRepository: ProductRepository) {

    sealed class Result {
        data class Success(val description: String) : Result()
        object NetworkError : Result()
        object Error : Result()
    }

    suspend operator fun invoke(productId: String): Result {
        return when (val resultWrapper = productRepository.getProductDescription(productId)) {
            is ResultWrapper.Success -> {
                Result.Success(resultWrapper.value)
            }
            is ResultWrapper.NetworkError -> Result.NetworkError
            else -> Result.Error
        }
    }
}