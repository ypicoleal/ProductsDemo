package com.example.infrastructure.entities

import com.example.domain.model.ProductItem
import com.google.gson.annotations.SerializedName

data class ProductItemEntity(
        @SerializedName("title") val id: String
)

fun ProductItemEntity.toProductItem() = ProductItem(id = this.id)