package com.diegoginko.spaceflightnews.data.mapper

import com.diegoginko.spaceflightnews.data.remote.model.ArticleDto
import com.diegoginko.spaceflightnews.data.remote.model.BlogDto
import com.diegoginko.spaceflightnews.data.remote.model.PaginatedResponse
import com.diegoginko.spaceflightnews.data.remote.model.ReportDto
import com.diegoginko.spaceflightnews.domain.model.Article
import com.diegoginko.spaceflightnews.domain.model.Author
import com.diegoginko.spaceflightnews.domain.model.Blog
import com.diegoginko.spaceflightnews.domain.model.Event
import com.diegoginko.spaceflightnews.domain.model.Launch
import com.diegoginko.spaceflightnews.domain.model.PaginatedResult
import com.diegoginko.spaceflightnews.domain.model.Report
import com.diegoginko.spaceflightnews.domain.model.Socials

fun ArticleDto.toDomain(): Article {
    return Article(
        id = id,
        title = title,
        authors = authors.map { it.toDomain() },
        url = url,
        imageUrl = imageUrl,
        newsSite = newsSite,
        summary = summary,
        publishedAt = publishedAt,
        updatedAt = updatedAt,
        featured = featured,
        launches = launches.map { it.toDomain() },
        events = events.map { it.toDomain() }
    )
}

fun com.diegoginko.spaceflightnews.data.remote.model.AuthorDto.toDomain(): Author {
    return Author(
        name = name,
        socials = socials?.toDomain()
    )
}

fun com.diegoginko.spaceflightnews.data.remote.model.SocialsDto.toDomain(): Socials {
    return Socials(
        x = x,
        youtube = youtube,
        instagram = instagram,
        linkedin = linkedin,
        mastodon = mastodon,
        bluesky = bluesky
    )
}

fun com.diegoginko.spaceflightnews.data.remote.model.LaunchDto.toDomain(): Launch {
    return Launch(
        id = launchId,
        provider = provider
    )
}

fun com.diegoginko.spaceflightnews.data.remote.model.EventDto.toDomain(): Event {
    return Event(
        id = eventId,
        provider = provider
    )
}

fun BlogDto.toDomain(): Blog {
    return Blog(
        id = id,
        title = title,
        url = url,
        imageUrl = imageUrl,
        newsSite = newsSite,
        summary = summary,
        publishedAt = publishedAt,
        updatedAt = updatedAt,
        featured = featured
    )
}

fun ReportDto.toDomain(): Report {
    return Report(
        id = id,
        title = title,
        url = url,
        imageUrl = imageUrl,
        newsSite = newsSite,
        summary = summary,
        publishedAt = publishedAt,
        updatedAt = updatedAt,
        featured = featured
    )
}

fun <T, R> PaginatedResponse<T>.toDomain(mapper: (T) -> R): PaginatedResult<R> {
    return PaginatedResult(
        count = count,
        next = next,
        previous = previous,
        results = results.map(mapper)
    )
}
