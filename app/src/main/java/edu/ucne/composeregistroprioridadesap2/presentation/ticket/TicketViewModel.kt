package edu.ucne.composeregistroprioridadesap2.presentation.ticket

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composeregistroprioridadesap2.data.remote.dto.TicketDto
import edu.ucne.composeregistroprioridadesap2.data.repository.ClienteRepository
import edu.ucne.composeregistroprioridadesap2.data.repository.PrioridadRepository
import edu.ucne.composeregistroprioridadesap2.data.repository.SistemaRepository
import edu.ucne.composeregistroprioridadesap2.data.repository.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val prioridadRepository: PrioridadRepository,
    private val sistemaRepository: SistemaRepository,
    private val clienteRepository: ClienteRepository
) : ViewModel(){
    private val _uiState = MutableStateFlow(TicketUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTickets()
        getPrioridades()
        getSistemas()
        getClientes()
    }

    private fun getTickets(){
        viewModelScope.launch {
            try {
                val tickets = ticketRepository.getAll()
                _uiState.update {
                    it.copy(tickets = tickets)
                }
            }catch (e: Exception){
                Log.e("ViewModel", "Error obteniendo tickets: ${e.message}", e)
                e.printStackTrace()
            }
        }
    }

    private fun getPrioridades(){
        viewModelScope.launch {
            try {
                val prioridades = prioridadRepository.getPrioridades()
                _uiState.update {
                    it.copy(prioridades = prioridades)
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "Error obteniendo prioridades: ${e.message}", e)
                e.printStackTrace()
            }
        }
    }

    private fun getSistemas(){
        viewModelScope.launch {
            try {
                val sistemas = sistemaRepository.getAll()
                _uiState.update {
                    it.copy(sistemas = sistemas)
                }
            }catch (e: Exception){
                Log.e("ViewModel", "Error obteniendo sistemas: ${e.message}", e)
                e.printStackTrace()
            }
        }
    }

    private fun getClientes(){
        viewModelScope.launch {
            try {
                val clientes = clienteRepository.getAll()
                _uiState.update {
                    it.copy(clientes = clientes)
                }
            }catch (e: Exception){
                Log.e("ViewModel", "Error obteniendo cliente: ${e.message}", e)
                e.printStackTrace()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: TicketUiEvent){
        when(event){
            is TicketUiEvent.TicketIdChanged -> {
                _uiState.update {
                    it.copy(ticketId = event.ticketId)
                }
            }
            is TicketUiEvent.FechaChanged -> {
                _uiState.update {
                    it.copy(
                        fecha = event.fecha,
                        errorFecha = ""
                    )
                }
            }
            is TicketUiEvent.ClienteIdChanged -> {
                _uiState.update {
                    it.copy(
                        clienteId = event.clienteId.toInt(),
                        errorCliente = ""
                    )
                }
            }

            is TicketUiEvent.SistemaIdChanged -> {
                _uiState.update {
                    it.copy(
                        sistemaId = event.sistemaId.toInt(),
                        errorSistema = ""
                    )
                }
            }

            is TicketUiEvent.PrioridadIdChanged -> {
                _uiState.update {
                    it.copy(
                        prioriodadId = event.prioridadId.toInt(),
                        errorPrioridad = ""
                    )
                }
            }

            is TicketUiEvent.SolicitadoPorChangend -> {
                _uiState.update {
                    it.copy(
                        solicitadoPor = event.solicitadoPor,
                        errorSolicitadoPor = ""
                    )
                }
            }

            is TicketUiEvent.AsuntoChanged -> {
                _uiState.update {
                    it.copy(
                        asunto = event.asunto,
                        errorAsunto = ""
                    )
                }
            }

            is TicketUiEvent.DescripcionChanged -> {
                _uiState.update {
                    it.copy(
                        descripcion = event.descripcion,
                        errorDescripcion = ""
                    )
                }
            }

            is TicketUiEvent.SelectedTicket -> {
                viewModelScope.launch {
                    if(event.ticketId > 0){
                        val ticket = ticketRepository.getTicketById(event.ticketId)
                        _uiState.update {
                            it.copy(
                                ticketId = ticket.ticketId,
                                fecha = ticket.fecha,
                                clienteId = ticket.clienteId,
                                sistemaId = ticket.sistemaId,
                                prioriodadId = ticket.prioridadId,
                                solicitadoPor = ticket.solicitadoPor,
                                asunto = ticket.asunto,
                                descripcion = ticket.descripcion
                            )
                        }
                    }
                }
            }
            TicketUiEvent.Save -> {
                viewModelScope.launch {
                    val fechaHoy = Date.from(Instant.now())
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

                    val solicitadoPorBuscado = ticketRepository.getAll()
                        .find { ticket ->
                            ticket.solicitadoPor.lowercase() == _uiState.value.solicitadoPor?.lowercase()
                        }

                    val asuntoBuscado = ticketRepository.getAll()
                        .find { ticket ->
                            ticket.asunto.lowercase() == _uiState.value.asunto?.lowercase()
                        }

                    val descripcionBuscada = ticketRepository.getAll()
                        .find { ticket ->
                            ticket.descripcion.lowercase() == _uiState.value.descripcion?.lowercase()
                        }

                    if(_uiState.value.clienteId == 0){
                        _uiState.update {
                            it.copy(errorCliente = "Este campo no puede estar vacío")
                        }
                    }

                    if(_uiState.value.sistemaId == 0){
                        _uiState.update {
                            it.copy(errorSistema = "Este campo no puede estar vacío")
                        }
                    }

                    if(_uiState.value.solicitadoPor.isNullOrEmpty()){
                        _uiState.update {
                            it.copy(errorSolicitadoPor = "Este campo no puede estar vacío")
                        }
                    }
                    else if(solicitadoPorBuscado != null && _uiState.value.ticketId != solicitadoPorBuscado.ticketId){
                        _uiState.update {
                            it.copy(errorSolicitadoPor = "Ya existe un ticket con esta solicitud")
                        }
                    }

                    if(_uiState.value.asunto.isNullOrEmpty()){
                        _uiState.update {
                            it.copy(errorAsunto = "Este campo no puede estar vacío")
                        }
                    }
                    else if(asuntoBuscado != null && _uiState.value.ticketId != asuntoBuscado.ticketId){
                        _uiState.update {
                            it.copy(errorAsunto = "Ya existe un ticket con este asunto")
                        }
                    }

                    if(_uiState.value.fecha == null){
                        _uiState.update {
                            it.copy(errorFecha = "Este campo no puede estar vacío")
                        }
                    }
                    else if(_uiState.value.fecha!!.before(fechaHoy)){
                        _uiState.update {
                            it.copy(errorFecha = "La fecha no puede ser anterior a ${dateFormat.format(fechaHoy)}")
                        }
                    }

                    if(_uiState.value.prioriodadId == 0){
                        _uiState.update {
                            it.copy(errorPrioridad = "Este campo no puede estar vacío")
                        }
                    }

                    if(_uiState.value.descripcion.isNullOrEmpty()){
                        _uiState.update {
                            it.copy(errorDescripcion = "Este campo no puede estar vacío")
                        }
                    }
                    else if(descripcionBuscada != null && _uiState.value.ticketId != descripcionBuscada.ticketId){
                        _uiState.update {
                            it.copy(errorDescripcion = "Ya existe un ticket con esta descripción")
                        }
                    }

                    if(_uiState.value.errorCliente == "" && _uiState.value.errorSistema == "" && _uiState.value.errorSolicitadoPor == ""
                        && _uiState.value.errorAsunto == "" && _uiState.value.errorFecha == "" && _uiState.value.errorPrioridad == ""
                        && _uiState.value.errorDescripcion == ""){

                        ticketRepository.save(_uiState.value.toEntity())
                        _uiState.update {
                            it.copy(success = true)
                        }
                    }
                }
            }
            TicketUiEvent.Delete -> {
//                viewModelScope.launch {
//                    ticketRepository.delete(_uiState.value.toEntity())
//                }
            }
            TicketUiEvent.Save -> TODO()
            else -> {}
        }
    }

    fun TicketUiState.toEntity() = TicketDto(
        ticketId = ticketId,
        fecha = fecha ?: Date(),
        clienteId = clienteId,
        sistemaId = sistemaId,
        prioridadId = prioriodadId,
        solicitadoPor = solicitadoPor ?: "",
        asunto = asunto ?: "",
        descripcion = descripcion ?: "",
        ticketsDetalle = ticketsDetalle
    )
}