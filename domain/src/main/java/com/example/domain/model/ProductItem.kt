package com.example.domain.model

data class ProductItem(
        val id: String,
        val title: String,
        val condition: String,
        val soldQuantity: Int,
        val images: List<String>,
        val variations: List<ProductVariation>?,
        val originalPrice: Int,
        val price: Int,
        val attributes: List<Attribute>
)

data class ProductVariation(
        val name: String,
        val value: String
)

data class Attribute(
        val name: String,
        val value: String
)
