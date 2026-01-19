package com.diegoginko.spaceflightnews.domain.model

data class LaunchDetail(
    val id: String,
    val url: String?,
    val launchLibraryId: Int?,
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
    val holdReason: String?,
    val failReason: String?,
    val hashtag: String?,
    val launchServiceProvider: LaunchServiceProvider?,
    val rocket: Rocket?,
    val mission: Mission?,
    val pad: Pad?,
    val webcastLive: Boolean?,
    val image: String?,
    val infographic: String?,
    val program: List<Program>?
)

data class LaunchStatus(
    val id: Int?,
    val name: String?,
    val abbrev: String?,
    val description: String?
)

data class LaunchServiceProvider(
    val id: Int?,
    val url: String?,
    val name: String?,
    val type: String?
)

data class Rocket(
    val id: Int?,
    val configuration: RocketConfiguration?
)

data class RocketConfiguration(
    val id: Int?,
    val url: String?,
    val name: String?,
    val family: String?,
    val fullName: String?,
    val variant: String?
)

data class Mission(
    val id: Int?,
    val name: String?,
    val description: String?,
    val launchDesignator: String?,
    val type: String?,
    val orbit: Orbit?
)

data class Orbit(
    val id: Int?,
    val name: String?,
    val abbrev: String?
)

data class Pad(
    val id: Int?,
    val url: String?,
    val agencyId: Int?,
    val name: String?,
    val info: String?,
    val wikiUrl: String?,
    val mapUrl: String?,
    val latitude: String?,
    val longitude: String?,
    val location: Location?,
    val countryCode: String?,
    val mapImage: String?,
    val totalLaunchCount: Int?
)

data class Location(
    val id: Int?,
    val url: String?,
    val name: String?,
    val countryCode: String?,
    val mapImage: String?,
    val timezoneName: String?,
    val totalLaunchCount: Int?,
    val totalLandingCount: Int?
)

data class Program(
    val id: Int?,
    val url: String?,
    val name: String?,
    val description: String?,
    val agencies: List<Agency>?,
    val imageUrl: String?,
    val startDate: String?,
    val endDate: String?,
    val infoUrl: String?,
    val wikiUrl: String?,
    val missionPatches: List<String>?
)

data class Agency(
    val id: Int?,
    val url: String?,
    val name: String?,
    val featured: Boolean?,
    val type: String?,
    val countryCode: String?,
    val abbrev: String?,
    val description: String?,
    val administrator: String?,
    val foundingYear: String?,
    val launcherList: String?,
    val spacecraftList: String?,
    val imageUrl: String?,
    val logoUrl: String?
)
