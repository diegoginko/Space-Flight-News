package com.diegoginko.spaceflightnews.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ReportDto(
    val id: Int,
    val title: String,
    val url: String,
    val imageUrl: String? = null,
    val newsSite: String,
    val summary: String? = null,
    val publishedAt: String,
    val updatedAt: String? = null,
    val featured: Boolean = false
)
