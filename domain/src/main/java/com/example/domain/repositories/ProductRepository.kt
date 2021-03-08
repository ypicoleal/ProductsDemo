package com.example.domain.repositories

import com.example.domain.model.ProductItem
import com.example.domain.model.ResultWrapper

interface ProductRepository {
    suspend fun getHelpCenterData(productId: String): ResultWrapper<ProductItem>
}