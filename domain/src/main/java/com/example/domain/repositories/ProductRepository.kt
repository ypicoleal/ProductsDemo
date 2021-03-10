package com.example.domain.repositories

import com.example.domain.model.ProductItem
import com.example.domain.model.ResultWrapper

interface ProductRepository {
    suspend fun getProductItem(productId: String): ResultWrapper<ProductItem>

    suspend fun getProductDescription(productId: String): ResultWrapper<String>
}