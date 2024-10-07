package edu.ucne.composeregistroprioridadesap2.data.remote

import edu.ucne.composeregistroprioridadesap2.data.remote.dto.ClienteDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ClientesApi {
    @GET("api/Clientes/{clienteId}")
    suspend fun getCliente(@Path("clienteId") clienteId: Int): ClienteDto

    @GET("api/Clientes")
    suspend fun getAll(): List<ClienteDto>

    @POST("api/Clientes")
    suspend fun save(@Body clienteDto: ClienteDto?): ClienteDto
}