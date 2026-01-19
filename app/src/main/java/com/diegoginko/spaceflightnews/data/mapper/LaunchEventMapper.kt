package com.diegoginko.spaceflightnews.data.mapper

import com.diegoginko.spaceflightnews.data.remote.model.*
import com.diegoginko.spaceflightnews.domain.model.*

fun LaunchDetailDto.toDomain(): LaunchDetail {
    return LaunchDetail(
        id = id,
        url = url,
        launchLibraryId = launchLibraryId,
        slug = slug,
        name = name,
        status = status?.toDomain(),
        netDate = netDate,
        windowStart = windowStart,
        windowEnd = windowEnd,
        inHold = inHold,
        tbdTime = tbdTime,
        tbdDate = tbdDate,
        probability = probability,
        holdReason = holdreason,
        failReason = failreason,
        hashtag = hashtag,
        launchServiceProvider = launchServiceProvider?.toDomain(),
        rocket = rocket?.toDomain(),
        mission = mission?.toDomain(),
        pad = pad?.toDomain(),
        webcastLive = webcastLive,
        image = image,
        infographic = infographic,
        program = program?.map { it.toDomain() }
    )
}

fun LaunchStatusDto.toDomain(): LaunchStatus {
    return LaunchStatus(
        id = id,
        name = name,
        abbrev = abbrev,
        description = description
    )
}

fun LaunchServiceProviderDto.toDomain(): LaunchServiceProvider {
    return LaunchServiceProvider(
        id = id,
        url = url,
        name = name,
        type = type
    )
}

fun RocketDto.toDomain(): Rocket {
    return Rocket(
        id = id,
        configuration = configuration?.toDomain()
    )
}

fun RocketConfigurationDto.toDomain(): RocketConfiguration {
    return RocketConfiguration(
        id = id,
        url = url,
        name = name,
        family = family,
        fullName = fullName,
        variant = variant
    )
}

fun MissionDto.toDomain(): Mission {
    return Mission(
        id = id,
        name = name,
        description = description,
        launchDesignator = launchDesignator,
        type = type,
        orbit = orbit?.toDomain()
    )
}

fun OrbitDto.toDomain(): Orbit {
    return Orbit(
        id = id,
        name = name,
        abbrev = abbrev
    )
}

fun PadDto.toDomain(): Pad {
    return Pad(
        id = id,
        url = url,
        agencyId = agencyId,
        name = name,
        info = info,
        wikiUrl = wiki_url,
        mapUrl = mapUrl,
        latitude = latitude,
        longitude = longitude,
        location = location?.toDomain(),
        countryCode = countryCode,
        mapImage = mapImage,
        totalLaunchCount = totalLaunchCount
    )
}

fun LocationDto.toDomain(): Location {
    return Location(
        id = id,
        url = url,
        name = name,
        countryCode = countryCode,
        mapImage = mapImage,
        timezoneName = timezoneName,
        totalLaunchCount = totalLaunchCount,
        totalLandingCount = totalLandingCount
    )
}

fun ProgramDto.toDomain(): Program {
    return Program(
        id = id,
        url = url,
        name = name,
        description = description,
        agencies = agencies?.map { it.toDomain() },
        imageUrl = imageUrl,
        startDate = startDate,
        endDate = endDate,
        infoUrl = infoUrl,
        wikiUrl = wikiUrl,
        missionPatches = missionPatches
    )
}

fun AgencyDto.toDomain(): Agency {
    return Agency(
        id = id,
        url = url,
        name = name,
        featured = featured,
        type = type,
        countryCode = countryCode,
        abbrev = abbrev,
        description = description,
        administrator = administrator,
        foundingYear = foundingYear,
        launcherList = launcherList,
        spacecraftList = spacecraftList,
        imageUrl = imageUrl,
        logoUrl = logoUrl
    )
}

fun EventDetailDto.toDomain(): EventDetail {
    return EventDetail(
        id = id,
        url = url,
        slug = slug,
        name = name,
        description = description,
        location = location,
        newsUrl = newsUrl,
        videoUrl = videoUrl,
        featureImage = featureImage,
        date = date,
        datePrecision = datePrecision,
        type = type?.toDomain(),
        launches = launches?.map { it.toDomain() }
    )
}

fun EventTypeDto.toDomain(): EventType {
    return EventType(
        id = id,
        name = name
    )
}

fun LaunchReferenceDto.toDomain(): LaunchReference {
    return LaunchReference(
        id = id,
        launchLibraryId = launchLibraryId,
        url = url,
        slug = slug,
        name = name,
        status = status?.toDomain(),
        netDate = netDate,
        windowStart = windowStart,
        windowEnd = windowEnd,
        inHold = inHold,
        tbdTime = tbdTime,
        tbdDate = tbdDate,
        probability = probability,
        hashtag = hashtag,
        launchServiceProvider = launchServiceProvider?.toDomain(),
        rocket = rocket?.toDomain(),
        mission = mission?.toDomain(),
        image = image,
        infographic = infographic
    )
}
