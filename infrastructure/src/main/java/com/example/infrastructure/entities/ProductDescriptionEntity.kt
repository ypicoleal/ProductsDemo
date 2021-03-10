package com.example.infrastructure.entities

import com.google.gson.annotations.SerializedName

data class ProductDescriptionEntity(
        @SerializedName("id") val id: String,
        @SerializedName("plain_text") val text: String,
)
