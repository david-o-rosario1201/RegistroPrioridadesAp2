package edu.ucne.composeregistroprioridadesap2.data.repository

import edu.ucne.composeregistroprioridadesap2.data.remote.TicketsApi
import edu.ucne.composeregistroprioridadesap2.data.remote.dto.TicketDto
import javax.inject.Inject

class TicketRepository @Inject constructor(
    private val ticketApi: TicketsApi
) {
    suspend fun getTicketById(ticketId: Int) = ticketApi.getTicket(ticketId)

    suspend fun save(ticketDto: TicketDto) = ticketApi.save(ticketDto)

    suspend fun getAll() = ticketApi.getAll()
}