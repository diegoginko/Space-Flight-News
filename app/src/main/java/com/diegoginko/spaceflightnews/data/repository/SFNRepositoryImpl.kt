package com.diegoginko.spaceflightnews.data.repository

import com.diegoginko.spaceflightnews.data.mapper.toDomain
import com.diegoginko.spaceflightnews.data.remote.ApiService
import com.diegoginko.spaceflightnews.domain.model.Article
import com.diegoginko.spaceflightnews.domain.model.Blog
import com.diegoginko.spaceflightnews.domain.model.PaginatedResult
import com.diegoginko.spaceflightnews.domain.model.Report
import com.diegoginko.spaceflightnews.domain.repository.SFNRepository
import javax.inject.Inject

class SFNRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : SFNRepository {

    override suspend fun getArticles(
        limit: Int?,
        offset: Int?,
        search: String?,
        newsSite: String?
    ): Result<PaginatedResult<Article>> {
        return try {
            val response = apiService.getArticles(
                limit = limit,
                offset = offset,
                search = search,
                newsSite = newsSite
            )
            Result.success(response.toDomain { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getArticleById(id: Int): Result<Article> {
        return try {
            val response = apiService.getArticleById(id)
            Result.success(response.toDomain())
        } catch (e: Exception) {
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
            val response = apiService.getBlogs(
                limit = limit,
                offset = offset,
                search = search,
                newsSite = newsSite
            )
            Result.success(response.toDomain { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getBlogById(id: Int): Result<Blog> {
        return try {
            val response = apiService.getBlogById(id)
            Result.success(response.toDomain())
        } catch (e: Exception) {
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
            val response = apiService.getReports(
                limit = limit,
                offset = offset,
                search = search,
                newsSite = newsSite
            )
            Result.success(response.toDomain { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getReportById(id: Int): Result<Report> {
        return try {
            val response = apiService.getReportById(id)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
