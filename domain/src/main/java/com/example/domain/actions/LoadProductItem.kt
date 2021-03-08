package com.example.domain.actions

import com.example.domain.model.ProductItem
import com.example.domain.model.ResultWrapper
import com.example.domain.repositories.ProductRepository

class LoadProductItem(private val productRepository: ProductRepository) {

    sealed class Result {
        data class Success(val productItem: ProductItem) : Result()
        object NetworkError : Result()
        object Error : Result()
    }

    suspend operator fun invoke(productId: String): Result {
        return when (val resultWrapper = productRepository.getHelpCenterData(productId)) {
            is ResultWrapper.Success -> {
                Result.Success(resultWrapper.value)
            }
            is ResultWrapper.NetworkError -> Result.NetworkError
            else -> Result.Error
        }
    }
}