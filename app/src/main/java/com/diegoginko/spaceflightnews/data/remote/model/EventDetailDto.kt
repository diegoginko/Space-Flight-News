package com.diegoginko.spaceflightnews.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO para detalles completos de un evento desde Launch Library 2 API
 */
@Serializable
data class EventDetailDto(
    val id: Int,
    val url: String? = null,
    val slug: String? = null,
    val name: String? = null,
    val description: String? = null,
    val location: String? = null,
    @SerialName("news_url")
    val newsUrl: String? = null,
    @SerialName("video_url")
    val videoUrl: String? = null,
    @SerialName("feature_image")
    val featureImage: String? = null,
    @SerialName("date")
    val date: String? = null,
    @SerialName("date_precision")
    val datePrecision: String? = null,
    @SerialName("type")
    val type: EventTypeDto? = null,
    @SerialName("launches")
    val launches: List<LaunchReferenceDto>? = null
)

@Serializable
data class EventTypeDto(
    val id: Int? = null,
    val name: String? = null
)

@Serializable
data class LaunchReferenceDto(
    val id: String? = null,
    @SerialName("launch_library_id")
    val launchLibraryId: Int? = null,
    val url: String? = null,
    val slug: String? = null,
    val name: String? = null,
    val status: LaunchStatusDto? = null,
    @SerialName("net")
    val netDate: String? = null,
    @SerialName("window_start")
    val windowStart: String? = null,
    @SerialName("window_end")
    val windowEnd: String? = null,
    @SerialName("inhold")
    val inHold: Boolean? = null,
    @SerialName("tbdtime")
    val tbdTime: Boolean? = null,
    @SerialName("tbddate")
    val tbdDate: Boolean? = null,
    @SerialName("probability")
    val probability: Int? = null,
    val hashtag: String? = null,
    @SerialName("launch_service_provider")
    val launchServiceProvider: LaunchServiceProviderDto? = null,
    val rocket: RocketDto? = null,
    val mission: MissionDto? = null,
    val image: String? = null,
    val infographic: String? = null
)
