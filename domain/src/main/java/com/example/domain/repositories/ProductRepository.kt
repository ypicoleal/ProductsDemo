package com.example.domain.repositories

import com.example.domain.model.ProductItem
import com.example.domain.model.ProductSearchItem
import com.example.domain.model.ResultWrapper
import com.example.domain.model.Site

interface ProductRepository {
    suspend fun getProductItem(productId: String): ResultWrapper<ProductItem>

    suspend fun getProductDescription(productId: String): ResultWrapper<String>

    suspend fun getProductSearch(query: String, site: String): ResultWrapper<List<ProductSearchItem>>

    suspend fun getSites(): ResultWrapper<List<Site>>
}