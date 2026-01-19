package com.diegoginko.spaceflightnews.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO para detalles completos de un lanzamiento desde Launch Library 2 API
 */
@Serializable
data class LaunchDetailDto(
    val id: String,
    val url: String? = null,
    @SerialName("launch_library_id")
    val launchLibraryId: Int? = null,
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
    val holdreason: String? = null,
    val failreason: String? = null,
    val hashtag: String? = null,
    @SerialName("launch_service_provider")
    val launchServiceProvider: LaunchServiceProviderDto? = null,
    val rocket: RocketDto? = null,
    val mission: MissionDto? = null,
    val pad: PadDto? = null,
    @SerialName("webcast_live")
    val webcastLive: Boolean? = null,
    val image: String? = null,
    val infographic: String? = null,
    val program: List<ProgramDto>? = null
)

@Serializable
data class LaunchStatusDto(
    val id: Int? = null,
    val name: String? = null,
    val abbrev: String? = null,
    val description: String? = null
)

@Serializable
data class LaunchServiceProviderDto(
    val id: Int? = null,
    val url: String? = null,
    val name: String? = null,
    val type: String? = null
)

@Serializable
data class RocketDto(
    val id: Int? = null,
    val configuration: RocketConfigurationDto? = null
)

@Serializable
data class RocketConfigurationDto(
    val id: Int? = null,
    val url: String? = null,
    val name: String? = null,
    val family: String? = null,
    @SerialName("full_name")
    val fullName: String? = null,
    val variant: String? = null
)

@Serializable
data class MissionDto(
    val id: Int? = null,
    val name: String? = null,
    val description: String? = null,
    @SerialName("launch_designator")
    val launchDesignator: String? = null,
    val type: String? = null,
    val orbit: OrbitDto? = null
)

@Serializable
data class OrbitDto(
    val id: Int? = null,
    val name: String? = null,
    val abbrev: String? = null
)

@Serializable
data class PadDto(
    val id: Int? = null,
    val url: String? = null,
    @SerialName("agency_id")
    val agencyId: Int? = null,
    val name: String? = null,
    val info: String? = null,
    val wiki_url: String? = null,
    @SerialName("map_url")
    val mapUrl: String? = null,
    @SerialName("latitude")
    val latitude: String? = null,
    @SerialName("longitude")
    val longitude: String? = null,
    val location: LocationDto? = null,
    @SerialName("country_code")
    val countryCode: String? = null,
    @SerialName("map_image")
    val mapImage: String? = null,
    @SerialName("total_launch_count")
    val totalLaunchCount: Int? = null
)

@Serializable
data class LocationDto(
    val id: Int? = null,
    val url: String? = null,
    val name: String? = null,
    @SerialName("country_code")
    val countryCode: String? = null,
    @SerialName("map_image")
    val mapImage: String? = null,
    @SerialName("timezone_name")
    val timezoneName: String? = null,
    @SerialName("total_launch_count")
    val totalLaunchCount: Int? = null,
    @SerialName("total_landing_count")
    val totalLandingCount: Int? = null
)

@Serializable
data class ProgramDto(
    val id: Int? = null,
    val url: String? = null,
    val name: String? = null,
    val description: String? = null,
    val agencies: List<AgencyDto>? = null,
    @SerialName("image_url")
    val imageUrl: String? = null,
    @SerialName("start_date")
    val startDate: String? = null,
    @SerialName("end_date")
    val endDate: String? = null,
    @SerialName("info_url")
    val infoUrl: String? = null,
    @SerialName("wiki_url")
    val wikiUrl: String? = null,
    @SerialName("mission_patches")
    val missionPatches: List<String>? = null
)

@Serializable
data class AgencyDto(
    val id: Int? = null,
    val url: String? = null,
    val name: String? = null,
    val featured: Boolean? = null,
    val type: String? = null,
    @SerialName("country_code")
    val countryCode: String? = null,
    @SerialName("abbrev")
    val abbrev: String? = null,
    val description: String? = null,
    @SerialName("administrator")
    val administrator: String? = null,
    @SerialName("founding_year")
    val foundingYear: String? = null,
    @SerialName("launcher_list")
    val launcherList: String? = null,
    @SerialName("spacecraft_list")
    val spacecraftList: String? = null,
    @SerialName("image_url")
    val imageUrl: String? = null,
    @SerialName("logo_url")
    val logoUrl: String? = null
)
