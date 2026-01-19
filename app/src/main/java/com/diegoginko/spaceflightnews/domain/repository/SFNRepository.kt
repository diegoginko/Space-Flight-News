package com.diegoginko.spaceflightnews.domain.repository

import com.diegoginko.spaceflightnews.domain.model.Article
import com.diegoginko.spaceflightnews.domain.model.Blog
import com.diegoginko.spaceflightnews.domain.model.EventDetail
import com.diegoginko.spaceflightnews.domain.model.LaunchDetail
import com.diegoginko.spaceflightnews.domain.model.PaginatedResult
import com.diegoginko.spaceflightnews.domain.model.Report

interface SFNRepository {
    
    /**
     * Obtiene una lista paginada de artículos
     * @param limit Número máximo de artículos a retornar
     * @param offset Número de artículos a saltar (para paginación)
     * @param search Término de búsqueda para filtrar artículos
     * @param newsSite Filtrar por sitio de noticias específico
     * @return Resultado paginado con lista de artículos
     */
    suspend fun getArticles(
        limit: Int? = null,
        offset: Int? = null,
        search: String? = null,
        newsSite: String? = null
    ): Result<PaginatedResult<Article>>
    
    /**
     * Obtiene un artículo específico por ID
     * @param id ID del artículo
     * @return Artículo
     */
    suspend fun getArticleById(id: Int): Result<Article>
    
    /**
     * Obtiene una lista paginada de blogs
     * @param limit Número máximo de blogs a retornar
     * @param offset Número de blogs a saltar (para paginación)
     * @param search Término de búsqueda para filtrar blogs
     * @param newsSite Filtrar por sitio de noticias específico
     * @return Resultado paginado con lista de blogs
     */
    suspend fun getBlogs(
        limit: Int? = null,
        offset: Int? = null,
        search: String? = null,
        newsSite: String? = null
    ): Result<PaginatedResult<Blog>>
    
    /**
     * Obtiene un blog específico por ID
     * @param id ID del blog
     * @return Blog
     */
    suspend fun getBlogById(id: Int): Result<Blog>
    
    /**
     * Obtiene una lista paginada de reportes
     * @param limit Número máximo de reportes a retornar
     * @param offset Número de reportes a saltar (para paginación)
     * @param search Término de búsqueda para filtrar reportes
     * @param newsSite Filtrar por sitio de noticias específico
     * @return Resultado paginado con lista de reportes
     */
    suspend fun getReports(
        limit: Int? = null,
        offset: Int? = null,
        search: String? = null,
        newsSite: String? = null
    ): Result<PaginatedResult<Report>>
    
    /**
     * Obtiene un reporte específico por ID
     * @param id ID del reporte
     * @return Reporte
     */
    suspend fun getReportById(id: Int): Result<Report>
    
    /**
     * Obtiene detalles completos de un lanzamiento por ID
     * @param id ID del lanzamiento (UUID string)
     * @return Detalles del lanzamiento
     */
    suspend fun getLaunchById(id: String): Result<LaunchDetail>
    
    /**
     * Obtiene detalles completos de un evento por ID
     * @param id ID del evento
     * @return Detalles del evento
     */
    suspend fun getEventById(id: Int): Result<EventDetail>
}