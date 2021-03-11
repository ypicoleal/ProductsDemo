package com.example.domain.actions

import com.example.domain.models.ResultWrapper
import com.example.domain.models.Site
import com.example.domain.repositories.ProductRepository

class LoadSites(private val productRepository: ProductRepository) {
    sealed class Result {
        data class Success(val items: List<Site>) : Result()
        object NetworkError : Result()
        object Error : Result()
    }

    suspend operator fun invoke(): Result {
        return when (val resultWrapper = productRepository.getSites(
        )) {
            is ResultWrapper.Success -> {
                Result.Success(resultWrapper.value)
            }
            is ResultWrapper.NetworkError -> Result.NetworkError
            else -> Result.Error
        }
    }
}
