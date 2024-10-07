package edu.ucne.composeregistroprioridadesap2.presentation.ticket

import java.util.Date

sealed interface TicketUiEvent {
    data class TicketIdChanged(val ticketId: Int) : TicketUiEvent
    data class FechaChanged(val fecha: Date?) : TicketUiEvent
    data class ClienteIdChanged(val clienteId: String) : TicketUiEvent
    data class SistemaIdChanged(val sistemaId: String): TicketUiEvent
    data class PrioridadIdChanged(val prioridadId: String) : TicketUiEvent
    data class SolicitadoPorChangend(val solicitadoPor: String): TicketUiEvent
    data class AsuntoChanged(val asunto: String) : TicketUiEvent
    data class DescripcionChanged(val descripcion: String) : TicketUiEvent
    data class SelectedTicket(val ticketId: Int): TicketUiEvent
    data object Save : TicketUiEvent
    data object Delete : TicketUiEvent
}