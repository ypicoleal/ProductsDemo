package com.example.infrastructure.entities

import com.example.domain.model.ProductSearchItem
import com.google.gson.annotations.SerializedName

data class ProductSearchEntity(
        @SerializedName("site_id") val siteId: String,
        @SerializedName("results") val results: List<ProductSearchItemEntity>,
)

data class ProductSearchItemEntity(
        @SerializedName("id") val id: String,
        @SerializedName("title") val title: String,
        @SerializedName("price") val price: Int,
        @SerializedName("thumbnail") val thumbnail: String,
)

fun ProductSearchItemEntity.toProductSearchItem() = ProductSearchItem(
        id = this.id,
        title = this.title,
        price = this.price,
        thumbnail = this.thumbnail
)
