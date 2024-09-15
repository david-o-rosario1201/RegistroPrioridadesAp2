package edu.ucne.composeregistroprioridadesap2.presentation.ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composeregistroprioridadesap2.data.local.entities.TicketEntity
import edu.ucne.composeregistroprioridadesap2.data.repository.PrioridadRepository
import edu.ucne.composeregistroprioridadesap2.data.repository.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val prioridadRepository: PrioridadRepository
) : ViewModel(){
    private val _uiState = MutableStateFlow(TicketUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTickets()
        getPrioridades()
    }

    private fun getTickets(){
        viewModelScope.launch {
            ticketRepository.getTickets().collect(){ tickets ->
                _uiState.update {
                    it.copy(tickets = tickets)
                }
            }
        }
    }

    private fun getPrioridades(){
        viewModelScope.launch {
            prioridadRepository.getPrioridades().collect(){ prioridades ->
                _uiState.update {
                    it.copy(prioridades = prioridades)
                }
            }
        }
    }

    fun onEvent(event: TicketUiEvent){
        when(event){
            is TicketUiEvent.ticketIdChanged -> {
                _uiState.update {
                    it.copy(ticketId = event.ticketId)
                }
            }
            is TicketUiEvent.fechaChanged -> {
                _uiState.update {
                    it.copy(fecha = event.fecha)
                }
            }
            is TicketUiEvent.prioridadIdChanged -> {
                _uiState.update {
                    it.copy(prioriodadId = event.prioridadId.toInt())
                }
            }
            is TicketUiEvent.clienteChanged -> {
                _uiState.update {
                    it.copy(cliente = event.cliente)
                }
            }
            is TicketUiEvent.asuntoChanged -> {
                _uiState.update {
                    it.copy(asunto = event.asunto)
                }
            }
            is TicketUiEvent.descripcionChanged -> {
                _uiState.update {
                    it.copy(descripcion = event.descripcion)
                }
            }
            is TicketUiEvent.ticketSelected -> {
                viewModelScope.launch {
                    if(event.ticketId > 0){
                        val ticket = ticketRepository.getTicketById(event.ticketId)
                        _uiState.update {
                            it.copy(
                                ticketId = ticket?.ticketId,
                                fecha = ticket?.fecha,
                                prioriodadId = ticket?.prioridadId ?: 0,
                                cliente = ticket?.cliente,
                                asunto = ticket?.asunto,
                                descripcion = ticket?.descripcion
                            )
                        }
                    }
                }
            }
            TicketUiEvent.Save -> {
                viewModelScope.launch {
                    if(_uiState.value.cliente.isNullOrBlank()){
                        _uiState.update {
                            it.copy(errorMessage = "El campo cliente no puede ir vacío")
                        }
                    }
                    else if(_uiState.value.asunto.isNullOrBlank()){
                        _uiState.update {
                            it.copy(errorMessage = "El campo asunto no puede ir vacío")
                        }
                    }
                    else if(_uiState.value.fecha == null){
                        _uiState.update {
                            it.copy(errorMessage = "El campo fecha no puede ir vacío")
                        }
                    }
                    else if(_uiState.value.prioriodadId <= 0){
                        _uiState.update {
                            it.copy(errorMessage = "El campo prioridad no puede ir vacío")
                        }
                    }
                    else if(_uiState.value.descripcion.isNullOrBlank()){
                        _uiState.update {
                            it.copy(errorMessage = "El campo descripción no puede ir vacío")
                        }
                    }
                    else{
                        ticketRepository.save(_uiState.value.toEntity())
                        _uiState.update {
                            it.copy(success = true)
                        }
                    }
                }
            }
            TicketUiEvent.Delete -> {
                viewModelScope.launch {
                    ticketRepository.delete(_uiState.value.toEntity())
                }
            }
        }
    }

    fun TicketUiState.toEntity() = TicketEntity(
        ticketId = ticketId,
        fecha = fecha,
        prioridadId = prioriodadId,
        cliente = cliente ?: "",
        asunto = asunto ?: "",
        descripcion = descripcion ?: ""
    )
}