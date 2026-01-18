package com.diegoginko.spaceflightnews.domain.model

data class Report(
    val id: Int,
    val title: String,
    val url: String,
    val imageUrl: String?,
    val newsSite: String,
    val summary: String?,
    val publishedAt: String,
    val updatedAt: String?,
    val featured: Boolean
)
