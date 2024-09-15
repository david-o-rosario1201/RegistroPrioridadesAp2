package edu.ucne.composeregistroprioridadesap2.presentation.ticket

import java.util.Date

sealed interface TicketUiEvent {
    data class ticketIdChanged(val ticketId: Int) : TicketUiEvent
    data class fechaChanged(val fecha: Date?) : TicketUiEvent
    data class prioridadIdChanged(val prioridadId: String) : TicketUiEvent
    data class clienteChanged(val cliente: String) : TicketUiEvent
    data class asuntoChanged(val asunto: String) : TicketUiEvent
    data class descripcionChanged(val descripcion: String) : TicketUiEvent
    data class ticketSelected(val ticketId: Int): TicketUiEvent
    object Save : TicketUiEvent
    object Delete : TicketUiEvent
}