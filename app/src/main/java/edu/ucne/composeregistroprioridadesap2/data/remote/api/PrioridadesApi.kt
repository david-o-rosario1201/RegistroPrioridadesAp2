package edu.ucne.composeregistroprioridadesap2.data.remote.api

import edu.ucne.composeregistroprioridadesap2.data.remote.dto.PrioridadDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PrioridadesApi {
    @GET("api/Prioridades/{prioridadId}")
    suspend fun getPrioridad(@Path("prioridadId") prioridadId: Int): PrioridadDto

    @GET("api/Prioridades")
    suspend fun getAll(): List<PrioridadDto>

    @POST("api/Prioridades")
    suspend fun save(@Body prioridadDto: PrioridadDto?): PrioridadDto
}