package edu.ucne.composeregistroprioridadesap2.presentation.ticket

import edu.ucne.composeregistroprioridadesap2.data.local.entities.PrioridadEntity
import edu.ucne.composeregistroprioridadesap2.data.local.entities.TicketEntity
import java.util.Date

data class TicketUiState(
    val ticketId: Int? = null,
    val fecha: Date? = null,
    val prioriodadId: Int = 0,
    val cliente: String? = "",
    val asunto: String? = "",
    val descripcion: String? = "",
    val errorMessage: String? = null,
    val tickets: List<TicketEntity> = emptyList(),
    val prioridades: List<PrioridadEntity> = emptyList(),
    val success: Boolean = false
)
