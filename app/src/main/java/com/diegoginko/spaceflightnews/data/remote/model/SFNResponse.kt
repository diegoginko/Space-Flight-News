package com.diegoginko.spaceflightnews.data.remote.model

import kotlinx.serialization.Serializable

/**
 * Respuesta paginada genérica de la API v4
 */
@Serializable
data class PaginatedResponse<T>(
    val count: Int,
    val next: String? = null,
    val previous: String? = null,
    val results: List<T>
)

// Tipos específicos de respuestas
typealias ArticlesResponse = PaginatedResponse<ArticleDto>
typealias ArticleResponse = ArticleDto
typealias BlogsResponse = PaginatedResponse<BlogDto>
typealias BlogResponse = BlogDto
typealias ReportsResponse = PaginatedResponse<ReportDto>
typealias ReportResponse = ReportDto
