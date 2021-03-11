package com.example.infrastructure.clients

import com.example.infrastructure.entities.ProductDescriptionEntity
import com.example.infrastructure.entities.ProductItemEntity
import com.example.infrastructure.entities.ProductSearchEntity
import com.example.infrastructure.entities.SiteEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductsClient {

    @GET("items/{id}")
    suspend fun getProductItem(@Path("id") id: String): ProductItemEntity

    @GET("items/{id}/descriptions")
    suspend fun getProductDescription(@Path("id") id: String): List<ProductDescriptionEntity>

    @GET("sites/{site}/search")
    suspend fun getProductSearch(@Path("site") site: String, @Query("q") query: String): ProductSearchEntity

    @GET("sites")
    suspend fun getSites(): List<SiteEntity>
}