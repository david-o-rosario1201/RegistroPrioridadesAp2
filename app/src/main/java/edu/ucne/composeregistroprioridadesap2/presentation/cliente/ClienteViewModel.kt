package edu.ucne.composeregistroprioridadesap2.presentation.cliente

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composeregistroprioridadesap2.data.remote.dto.ClienteDto
import edu.ucne.composeregistroprioridadesap2.data.repository.ClienteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClienteViewModel @Inject constructor(
    private val clienteRepository: ClienteRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(ClienteUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getClientes()
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

    fun onEvent(event: ClienteUiEvent){
        when(event){
            is ClienteUiEvent.ClienteIdChanged -> {
                _uiState.update {
                    it.copy(clienteId = event.clienteId)
                }
            }
            is ClienteUiEvent.NombreChanged -> {
                _uiState.update {
                    it.copy(
                        nombre = event.nombre,
                        errorNombre = ""
                    )
                }
            }
            is ClienteUiEvent.RncChanged -> {
                _uiState.update {
                    it.copy(
                        rnc = event.rnc,
                        errorRNC = ""
                    )
                }
            }
            is ClienteUiEvent.EmailChanged -> {
                _uiState.update {
                    it.copy(
                        email = event.email,
                        errorEmail = ""
                    )
                }
            }
            is ClienteUiEvent.DireccionChanged -> {
                _uiState.update {
                    it.copy(
                        direccion = event.direccion,
                        errorDireccion = ""
                    )
                }
            }
            is ClienteUiEvent.SelectedCliente -> {
                viewModelScope.launch {
                    if(event.clienteId > 0){
                        val cliente = clienteRepository.getClienteById(event.clienteId)
                        _uiState.update {
                            it.copy(
                                clienteId = cliente.clienteId,
                                nombre = cliente.nombre,
                                rnc = cliente.rnc,
                                email = cliente.email,
                                direccion = cliente.direccion
                            )
                        }
                    }
                }
            }
            ClienteUiEvent.Save -> {
                viewModelScope.launch {
                    val nombreBuscado = clienteRepository.getAll()
                        .find { cliente ->
                            cliente.nombre.lowercase() == _uiState.value.nombre?.lowercase()
                        }

                    val rncBuscado = clienteRepository.getAll()
                        .find { cliente ->
                            cliente.rnc == _uiState.value.rnc
                        }

                    val emailBuscado = clienteRepository.getAll()
                        .find { cliente ->
                            cliente.email.lowercase() == _uiState.value.email?.lowercase()
                        }

                    if(_uiState.value.nombre.isNullOrEmpty()){
                        _uiState.update {
                            it.copy(errorNombre = "Este campo no puede ir vacío")
                        }
                    }
                    else if(nombreBuscado != null && _uiState.value.clienteId != nombreBuscado.clienteId){
                        _uiState.update {
                            it.copy(errorNombre = "Ya existe un cliente con este nombre")
                        }
                    }

                    if(_uiState.value.rnc.isNullOrEmpty()){
                        _uiState.update {
                            it.copy(errorRNC = "Este campo no puede ir vacío")
                        }
                    }
                    else if(_uiState.value.rnc!!.length != 11){
                        _uiState.update {
                            it.copy(errorRNC = "El RNC debe tener exactamente 11 caracteres. " +
                                    "\n                 Tienes '${_uiState.value.rnc!!.length}' caracteres")
                        }
                    }
                    else if(rncBuscado != null && _uiState.value.clienteId != rncBuscado.clienteId){
                        _uiState.update {
                            it.copy(errorRNC = "Ya existe un cliente con este número de RNC")
                        }
                    }

                    if(_uiState.value.email.isNullOrEmpty()){
                        _uiState.update {
                            it.copy(errorEmail = "Este campo no puede ir vacío")
                        }
                    }
                    else if(emailBuscado != null && _uiState.value.clienteId != emailBuscado.clienteId){
                        _uiState.update {
                            it.copy(errorEmail = "Ya existe un cliente con esta dirección de email")
                        }
                    }

                    if(_uiState.value.direccion.isNullOrEmpty()){
                        _uiState.update {
                            it.copy(errorDireccion = "Este campo no puede ir vacío")
                        }
                    }

                    if(_uiState.value.errorNombre == "" && _uiState.value.errorRNC == ""
                        && _uiState.value.errorEmail == "" && _uiState.value.errorDireccion == ""){

                        clienteRepository.save(_uiState.value.toEntity())
                        _uiState.update {
                            it.copy(success = true)
                        }
                    }
                }
            }
            ClienteUiEvent.Delete -> TODO()
        }
    }

    fun ClienteUiState.toEntity() = ClienteDto(
        clienteId = clienteId,
        nombre = nombre ?: "",
        rnc = rnc ?: "",
        email = email ?: "",
        direccion = direccion ?: "",
        clientesDetalleCelulares = clientesDetalleCelulares,
        clientesDetalleTelefonos = clientesDetalleTelefonos
    )
}