package com.diegoginko.spaceflightnews.data.repository

import com.diegoginko.spaceflightnews.data.mapper.toDomain
import com.diegoginko.spaceflightnews.data.remote.SFNApiService
import com.diegoginko.spaceflightnews.data.remote.LaunchLibraryApiService
import com.diegoginko.spaceflightnews.data.util.NetworkErrorHandler
import com.diegoginko.spaceflightnews.domain.model.Article
import com.diegoginko.spaceflightnews.domain.model.Blog
import com.diegoginko.spaceflightnews.domain.model.EventDetail
import com.diegoginko.spaceflightnews.domain.model.LaunchDetail
import com.diegoginko.spaceflightnews.domain.model.PaginatedResult
import com.diegoginko.spaceflightnews.domain.model.Report
import com.diegoginko.spaceflightnews.domain.repository.SFNRepository
import timber.log.Timber
import javax.inject.Inject

class SFNRepositoryImpl @Inject constructor(
    private val SFNApiService: SFNApiService,
    private val launchLibraryApiService: LaunchLibraryApiService
) : SFNRepository {

    override suspend fun getArticles(
        limit: Int?,
        offset: Int?,
        search: String?,
        newsSite: String?
    ): Result<PaginatedResult<Article>> {
        return try {
            Timber.d("Obteniendo artículos - límite: $limit, offset: $offset, búsqueda: $search, sitio: $newsSite")
            val response = SFNApiService.getArticles(
                limit = limit,
                offset = offset,
                search = search,
                newsSite = newsSite
            )
            val articles = response.toDomain { it.toDomain() }
            Timber.d("Artículos obtenidos exitosamente: ${articles.results.size} (total: ${articles.count})")
            Result.success(articles)
        } catch (e: Exception) {
            val isNetworkError = NetworkErrorHandler.isNetworkError(e)
            if (isNetworkError) {
                Timber.w(e, "Error de red al obtener artículos")
            } else {
                Timber.e(e, "Error al obtener artículos")
            }
            Result.failure(e)
        }
    }

    override suspend fun getArticleById(id: Int): Result<Article> {
        return try {
            Timber.d("Obteniendo artículo por id: $id")
            val response = SFNApiService.getArticleById(id)
            val article = response.toDomain()
            Timber.d("Artículo obtenido exitosamente: ${article.title}")
            Result.success(article)
        } catch (e: Exception) {
            val isNetworkError = NetworkErrorHandler.isNetworkError(e)
            if (isNetworkError) {
                Timber.w(e, "Error de red al obtener artículo id: $id")
            } else {
                Timber.e(e, "Error al obtener artículo id: $id")
            }
            Result.failure(e)
        }
    }

    override suspend fun getBlogs(
        limit: Int?,
        offset: Int?,
        search: String?,
        newsSite: String?
    ): Result<PaginatedResult<Blog>> {
        return try {
            Timber.d("Obteniendo blogs - límite: $limit, offset: $offset, búsqueda: $search, sitio: $newsSite")
            val response = SFNApiService.getBlogs(
                limit = limit,
                offset = offset,
                search = search,
                newsSite = newsSite
            )
            val blogs = response.toDomain { it.toDomain() }
            Timber.d("Blogs obtenidos exitosamente: ${blogs.results.size} (total: ${blogs.count})")
            Result.success(blogs)
        } catch (e: Exception) {
            val isNetworkError = NetworkErrorHandler.isNetworkError(e)
            if (isNetworkError) {
                Timber.w(e, "Error de red al obtener blogs")
            } else {
                Timber.e(e, "Error al obtener blogs")
            }
            Result.failure(e)
        }
    }

    override suspend fun getBlogById(id: Int): Result<Blog> {
        return try {
            Timber.d("Obteniendo blog por id: $id")
            val response = SFNApiService.getBlogById(id)
            val blog = response.toDomain()
            Timber.d("Blog obtenido exitosamente: ${blog.title}")
            Result.success(blog)
        } catch (e: Exception) {
            val isNetworkError = NetworkErrorHandler.isNetworkError(e)
            if (isNetworkError) {
                Timber.w(e, "Error de red al obtener blog id: $id")
            } else {
                Timber.e(e, "Error al obtener blog id: $id")
            }
            Result.failure(e)
        }
    }

    override suspend fun getReports(
        limit: Int?,
        offset: Int?,
        search: String?,
        newsSite: String?
    ): Result<PaginatedResult<Report>> {
        return try {
            Timber.d("Obteniendo reportes - límite: $limit, offset: $offset, búsqueda: $search, sitio: $newsSite")
            val response = SFNApiService.getReports(
                limit = limit,
                offset = offset,
                search = search,
                newsSite = newsSite
            )
            val reports = response.toDomain { it.toDomain() }
            Timber.d("Reportes obtenidos exitosamente: ${reports.results.size} (total: ${reports.count})")
            Result.success(reports)
        } catch (e: Exception) {
            val isNetworkError = NetworkErrorHandler.isNetworkError(e)
            if (isNetworkError) {
                Timber.w(e, "Error de red al obtener reportes")
            } else {
                Timber.e(e, "Error al obtener reportes")
            }
            Result.failure(e)
        }
    }

    override suspend fun getReportById(id: Int): Result<Report> {
        return try {
            Timber.d("Obteniendo reporte por id: $id")
            val response = SFNApiService.getReportById(id)
            val report = response.toDomain()
            Timber.d("Reporte obtenido exitosamente: ${report.title}")
            Result.success(report)
        } catch (e: Exception) {
            val isNetworkError = NetworkErrorHandler.isNetworkError(e)
            if (isNetworkError) {
                Timber.w(e, "Error de red al obtener reporte id: $id")
            } else {
                Timber.e(e, "Error al obtener reporte id: $id")
            }
            Result.failure(e)
        }
    }

    override suspend fun getLaunchById(id: String): Result<LaunchDetail> {
        return try {
            Timber.d("Obteniendo detalles del lanzamiento id: $id")
            val response = launchLibraryApiService.getLaunchById(id)
            val launchDetail = response.toDomain()
            Timber.d("Detalles del lanzamiento obtenidos exitosamente: ${launchDetail.name ?: id}")
            Result.success(launchDetail)
        } catch (e: Exception) {
            val isNetworkError = NetworkErrorHandler.isNetworkError(e)
            if (isNetworkError) {
                Timber.w(e, "Error de red al obtener detalles del lanzamiento id: $id")
            } else {
                Timber.e(e, "Error al obtener detalles del lanzamiento id: $id")
            }
            Result.failure(e)
        }
    }

    override suspend fun getEventById(id: Int): Result<EventDetail> {
        return try {
            Timber.d("Obteniendo detalles del evento id: $id")
            val response = launchLibraryApiService.getEventById(id)
            val eventDetail = response.toDomain()
            Timber.d("Detalles del evento obtenidos exitosamente: ${eventDetail.name ?: id}")
            Result.success(eventDetail)
        } catch (e: Exception) {
            val isNetworkError = NetworkErrorHandler.isNetworkError(e)
            if (isNetworkError) {
                Timber.w(e, "Error de red al obtener detalles del evento id: $id")
            } else {
                Timber.e(e, "Error al obtener detalles del evento id: $id")
            }
            Result.failure(e)
        }
    }
}
