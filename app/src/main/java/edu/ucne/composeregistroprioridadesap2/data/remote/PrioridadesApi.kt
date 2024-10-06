package edu.ucne.composeregistroprioridadesap2.data.remote

import edu.ucne.composeregistroprioridadesap2.data.remote.dto.PrioridadDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PrioridadesApi {
    @GET("api/Prioridades/{prioridadId}")
    suspend fun getPrioridad(@Path("prioridadId") prioridadId: Int): PrioridadDto

    @GET("api/Prioridades")
    suspend fun getPrioridades(): List<PrioridadDto>

    @POST("api/Prioridades")
    suspend fun savePrioridad(@Body prioridadDto: PrioridadDto?): PrioridadDto
}