package com.example.infrastructure.clients

import com.example.infrastructure.entities.ProductDescriptionEntity
import com.example.infrastructure.entities.ProductItemEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductsClient {

    @GET("items/{id}")
    suspend fun getProductItem(@Path("id") id: String): ProductItemEntity

    @GET("items/{id}/descriptions")
    suspend fun getProductDescription(@Path("id") id: String): List<ProductDescriptionEntity>
}