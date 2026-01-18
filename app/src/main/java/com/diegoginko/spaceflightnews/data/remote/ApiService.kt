package com.diegoginko.spaceflightnews.data.remote

import com.diegoginko.spaceflightnews.data.remote.model.ArticleResponse
import com.diegoginko.spaceflightnews.data.remote.model.ArticlesResponse
import com.diegoginko.spaceflightnews.data.remote.model.BlogResponse
import com.diegoginko.spaceflightnews.data.remote.model.BlogsResponse
import com.diegoginko.spaceflightnews.data.remote.model.ReportResponse
import com.diegoginko.spaceflightnews.data.remote.model.ReportsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    
    /**
     * Obtiene una lista de artículos
     * @param limit Número máximo de artículos a retornar
     * @param offset Número de artículos a saltar (para paginación)
     * @param search Término de búsqueda para filtrar artículos
     * @param newsSite Filtrar por sitio de noticias específico
     * @return Lista de artículos
     */
    @GET("articles")
    suspend fun getArticles(
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null,
        @Query("search") search: String? = null,
        @Query("news_site") newsSite: String? = null
    ): ArticlesResponse
    
    /**
     * Obtiene un artículo específico por ID
     * @param id ID del artículo
     * @return Artículo
     */
    @GET("articles/{id}")
    suspend fun getArticleById(
        @Path("id") id: Int
    ): ArticleResponse
    
    /**
     * Obtiene una lista de blogs
     * @param limit Número máximo de blogs a retornar
     * @param offset Número de blogs a saltar (para paginación)
     * @param search Término de búsqueda para filtrar blogs
     * @param newsSite Filtrar por sitio de noticias específico
     * @return Lista de blogs
     */
    @GET("blogs")
    suspend fun getBlogs(
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null,
        @Query("search") search: String? = null,
        @Query("news_site") newsSite: String? = null
    ): BlogsResponse
    
    /**
     * Obtiene un blog específico por ID
     * @param id ID del blog
     * @return Blog
     */
    @GET("blogs/{id}")
    suspend fun getBlogById(
        @Path("id") id: Int
    ): BlogResponse
    
    /**
     * Obtiene una lista de reportes
     * @param limit Número máximo de reportes a retornar
     * @param offset Número de reportes a saltar (para paginación)
     * @param search Término de búsqueda para filtrar reportes
     * @param newsSite Filtrar por sitio de noticias específico
     * @return Lista de reportes
     */
    @GET("reports")
    suspend fun getReports(
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null,
        @Query("search") search: String? = null,
        @Query("news_site") newsSite: String? = null
    ): ReportsResponse
    
    /**
     * Obtiene un reporte específico por ID
     * @param id ID del reporte
     * @return Reporte
     */
    @GET("reports/{id}")
    suspend fun getReportById(
        @Path("id") id: Int
    ): ReportResponse
}