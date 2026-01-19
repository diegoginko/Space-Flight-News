package com.diegoginko.spaceflightnews.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDto(
    val id: Int,
    val title: String,
    val authors: List<AuthorDto> = emptyList(),
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
    val featured: Boolean = false,
    val launches: List<LaunchDto> = emptyList(),
    val events: List<EventDto> = emptyList()
)

@Serializable
data class AuthorDto(
    val name: String,
    val socials: SocialsDto? = null
)

@Serializable
data class SocialsDto(
    val x: String? = null,
    val youtube: String? = null,
    val instagram: String? = null,
    val linkedin: String? = null,
    val mastodon: String? = null,
    val bluesky: String? = null
)

@Serializable
data class LaunchDto(
    @SerialName("launch_id")
    val launchId: String,
    val provider: String
)

@Serializable
data class EventDto(
    @SerialName("event_id")
    val eventId: Int,
    val provider: String
)
