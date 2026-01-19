package com.diegoginko.spaceflightnews.data.remote

import com.diegoginko.spaceflightnews.data.remote.model.EventDetailDto
import com.diegoginko.spaceflightnews.data.remote.model.LaunchDetailDto
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Servicio para interactuar con Launch Library 2 API
 * Documentación: https://thespacedevs.com/llapi
 */
interface LaunchLibraryApiService {

    /**
     * Obtiene detalles completos de un lanzamiento por ID
     * @param id ID del lanzamiento (UUID string)
     * @return Detalles del lanzamiento
     */
    @GET("launch/{id}/")
    suspend fun getLaunchById(
        @Path("id") id: String
    ): LaunchDetailDto

    /**
     * Obtiene detalles completos de un evento por ID
     * @param id ID del evento
     * @return Detalles del evento
     */
    @GET("event/{id}/")
    suspend fun getEventById(
        @Path("id") id: Int
    ): EventDetailDto
}
