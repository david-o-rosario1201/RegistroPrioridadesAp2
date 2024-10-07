package edu.ucne.composeregistroprioridadesap2.presentation.ticket

import edu.ucne.composeregistroprioridadesap2.data.remote.dto.ClienteDto
import edu.ucne.composeregistroprioridadesap2.data.remote.dto.PrioridadDto
import edu.ucne.composeregistroprioridadesap2.data.remote.dto.SistemaDto
import edu.ucne.composeregistroprioridadesap2.data.remote.dto.TicketDto
import edu.ucne.composeregistroprioridadesap2.data.remote.dto.TicketsDetalle
import java.util.Date

data class TicketUiState(
    val ticketId: Int? = null,
    val fecha: Date? = null,
    val clienteId: Int = 0,
    val sistemaId: Int = 0,
    val prioriodadId: Int = 0,
    val solicitadoPor: String? = "",
    val asunto: String? = "",
    val descripcion: String? = "",
    val ticketsDetalle: List<TicketsDetalle> = emptyList(),
    val tickets: List<TicketDto> = emptyList(),
    val prioridades: List<PrioridadDto> = emptyList(),
    val sistemas: List<SistemaDto> = emptyList(),
    val clientes: List<ClienteDto> = emptyList(),
    val errorFecha: String? = "",
    val errorCliente: String? = "",
    val errorSistema: String? = "",
    val errorSolicitadoPor: String? = "",
    val errorAsunto: String? = "",
    val errorPrioridad: String? = "",
    val errorDescripcion: String? = "",
    val success: Boolean = false
)
