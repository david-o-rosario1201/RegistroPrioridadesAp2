package edu.ucne.composeregistroprioridadesap2.data.remote.api

import edu.ucne.composeregistroprioridadesap2.data.remote.dto.SistemaDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SistemasApi {
    @GET("api/Sistemas/{sistemaId}")
    suspend fun getSistema(@Path("sistemaId") sistemaId: Int): SistemaDto

    @GET("api/Sistemas")
    suspend fun getAll(): List<SistemaDto>

    @POST("api/Sistemas")
    suspend fun save(@Body sistemaDto: SistemaDto?): SistemaDto
}