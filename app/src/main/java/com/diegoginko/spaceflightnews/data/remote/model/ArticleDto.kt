package com.diegoginko.spaceflightnews.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ArticleDto(
    val id: Int,
    val title: String,
    val url: String,
    val imageUrl: String? = null,
    val newsSite: String,
    val summary: String? = null,
    val publishedAt: String,
    val updatedAt: String? = null,
    val featured: Boolean = false,
    val launches: List<LaunchDto> = emptyList(),
    val events: List<EventDto> = emptyList()
)

@Serializable
data class LaunchDto(
    val id: String,
    val provider: String
)

@Serializable
data class EventDto(
    val id: Int,
    val provider: String
)
