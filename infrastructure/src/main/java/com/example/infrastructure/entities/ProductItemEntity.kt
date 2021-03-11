package com.example.infrastructure.entities

import com.example.domain.models.Attribute
import com.example.domain.models.ProductItem
import com.example.domain.models.ProductVariation
import com.google.gson.annotations.SerializedName

data class ProductItemEntity(
        @SerializedName("id") val id: String,
        @SerializedName("title") val title: String,
        @SerializedName("condition") val condition: String,
        @SerializedName("sold_quantity") val soldQuantity: Int,
        @SerializedName("pictures") val pictures: List<ProductPictureEntity>,
        @SerializedName("variations") val variations: List<ProductVariationEntity>,
        @SerializedName("original_price") val originalPrice: Int,
        @SerializedName("price") val price: Int,
        @SerializedName("attributes") val attributes: List<AttributeEntity>
)

data class ProductPictureEntity(
        @SerializedName("id") val id: String,
        @SerializedName("url") val url: String,
        @SerializedName("secure_url") val secureUrl: String
)

data class ProductVariationEntity (
        @SerializedName("id") val id: String,
        @SerializedName("attribute_combinations") val combinations: List<VariationsCombination>
)

data class VariationsCombination(
        @SerializedName("id") val id: String,
        @SerializedName("name") val name: String,
        @SerializedName("value_name") val value: String,
)

data class AttributeEntity (
        @SerializedName("id") val id: String,
        @SerializedName("name") val name: String,
        @SerializedName("value_name") val value: String?,
)

fun ProductItemEntity.toProductItem() = ProductItem(
        id = this.id,
        title = this.title,
        condition = this.condition,
        soldQuantity = this.soldQuantity,
        images = this.pictures.map { it.secureUrl },
        variations = this.variations.firstOrNull()?.let { variation -> variation.combinations.map { ProductVariation(it.name, it.value) } },
        originalPrice = this.originalPrice,
        price = this.price,
        attributes = this.attributes.mapNotNull { it.value?.let { value -> Attribute(it.name, value) } }
)