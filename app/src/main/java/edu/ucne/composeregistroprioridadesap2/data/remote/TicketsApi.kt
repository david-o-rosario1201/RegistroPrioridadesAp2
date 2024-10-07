package edu.ucne.composeregistroprioridadesap2.data.remote

import edu.ucne.composeregistroprioridadesap2.data.remote.dto.TicketDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TicketsApi {
    @GET("api/Tickets/{ticketId}")
    suspend fun getTicket(@Path("ticketId") ticketId: Int): TicketDto

    @GET("api/Tickets")
    suspend fun getAll(): List<TicketDto>

    @POST("api/Tickets")
    suspend fun save(@Body ticketDto: TicketDto?): TicketDto
}