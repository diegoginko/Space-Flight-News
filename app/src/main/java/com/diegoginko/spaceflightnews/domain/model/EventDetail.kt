package com.diegoginko.spaceflightnews.domain.model

data class EventDetail(
    val id: Int,
    val url: String?,
    val slug: String?,
    val name: String?,
    val description: String?,
    val location: String?,
    val newsUrl: String?,
    val videoUrl: String?,
    val featureImage: String?,
    val date: String?,
    val datePrecision: String?,
    val type: EventType?,
    val launches: List<LaunchReference>?
)

data class EventType(
    val id: Int?,
    val name: String?
)

data class LaunchReference(
    val id: String?,
    val launchLibraryId: Int?,
    val url: String?,
    val slug: String?,
    val name: String?,
    val status: LaunchStatus?,
    val netDate: String?,
    val windowStart: String?,
    val windowEnd: String?,
    val inHold: Boolean?,
    val tbdTime: Boolean?,
    val tbdDate: Boolean?,
    val probability: Int?,
    val hashtag: String?,
    val launchServiceProvider: LaunchServiceProvider?,
    val rocket: Rocket?,
    val mission: Mission?,
    val image: String?,
    val infographic: String?
)
