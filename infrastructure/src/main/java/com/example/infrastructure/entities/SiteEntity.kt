package com.example.infrastructure.entities

import com.example.domain.model.Site
import com.google.gson.annotations.SerializedName

data class SiteEntity(
        @SerializedName("id") val id: String,
        @SerializedName("name") val name: String,
)

fun SiteEntity.toSite() = Site(id, name)
