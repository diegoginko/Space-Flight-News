package com.diegoginko.spaceflightnews.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportDto(
    val id: Int,
    val title: String,
    val url: String,
    @SerialName("image_url")
    val imageUrl: String? = null,
    @SerialName("news_site")
    val newsSite: String,
    val summary: String? = null,
    @SerialName("published_at")
    val publishedAt: String,
    @SerialName("updated_at")
    val updatedAt: String? = null,
    val featured: Boolean = false
)
