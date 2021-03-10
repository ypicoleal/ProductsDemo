package com.example.infrastructure.repositories

import com.example.domain.model.ProductItem
import com.example.domain.model.ResultWrapper
import com.example.domain.repositories.ProductRepository
import com.example.infrastructure.clients.ProductsClient
import com.example.infrastructure.entities.toProductItem
import com.example.infrastructure.network.ResponseHandler

class ProductRepositoryImpl(
        private val client: ProductsClient,
        private val responseHandler: ResponseHandler
): ProductRepository {

    override suspend fun getProductItem(productId: String): ResultWrapper<ProductItem> {
        return responseHandler {
            client.getProductItem(productId).toProductItem()
        }
    }

    override suspend fun getProductDescription(productId: String): ResultWrapper<String> {
        return responseHandler {
            client.getProductDescription(productId).first().text
        }
    }
}