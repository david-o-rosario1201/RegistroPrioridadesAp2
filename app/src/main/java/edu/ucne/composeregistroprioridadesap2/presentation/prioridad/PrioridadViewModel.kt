package edu.ucne.composeregistroprioridadesap2.presentation.prioridad

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composeregistroprioridadesap2.data.remote.dto.PrioridadDto
import edu.ucne.composeregistroprioridadesap2.data.repository.PrioridadRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrioridadViewModel @Inject constructor(
    private val prioridadRepository: PrioridadRepository
) : ViewModel(){
    private val _uiState = MutableStateFlow(PrioridadUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getPrioridades()
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
                e.printStackTrace()  // Esto mostrará el stack trace completo en el log
            }
        }
    }

    fun onEvent(event: PrioridadUiEvent){
        when(event){
            is PrioridadUiEvent.PrioridadIdChanged -> {
                _uiState.update {
                    it.copy(prioridadId = event.prioridadId)
                }
            }
            is PrioridadUiEvent.DescripcionChanged -> {
                _uiState.update {
                    it.copy(
                        descripcion = event.descripcion,
                        errorDescripcion = ""
                    )
                }
            }
            is PrioridadUiEvent.DiasCompromisoChanged -> {
                _uiState.update {
                    it.copy(
                        diasCompromiso = event.diasCompromiso.toInt(),
                        errorDiasCompromiso = ""
                    )
                }
            }
            is PrioridadUiEvent.SelectedPrioridad -> {
                viewModelScope.launch {
                    if(event.prioridadId > 0){
                        val prioridad = prioridadRepository.getPrioridadById(event.prioridadId)
                        _uiState.update {
                            it.copy(
                                prioridadId = prioridad.prioridadId,
                                descripcion = prioridad.descripcion,
                                diasCompromiso = prioridad.diasCompromiso
                            )
                        }
                    }
                }
            }
            PrioridadUiEvent.Save -> {
                viewModelScope.launch {
                    val prioridadBuscada = prioridadRepository.getPrioridades()
                        .filter { prioridad ->
                            prioridad.descripcion.contains(_uiState.value.descripcion.toString())
                        }

                    if(_uiState.value.descripcion.isNullOrEmpty()){
                        _uiState.update {
                            it.copy(errorDescripcion = "Este campo no puede estar vacío")
                        }
                    }
                    else if(prioridadBuscada.isNotEmpty()){
                        _uiState.update {
                            it.copy(errorDescripcion = "Ya existe una prioridad con esta descripción")
                        }
                    }

                    if(_uiState.value.diasCompromiso == 0){
                        _uiState.update {
                            it.copy(errorDiasCompromiso = "Este campo no puede estar vacío")
                        }
                    }
                    else if(_uiState.value.diasCompromiso < 1 || _uiState.value.diasCompromiso > 30){
                        _uiState.update {
                            it.copy(errorDiasCompromiso = "El mínimo de días es 1 y el máximo es 30")
                        }
                    }

                    if(_uiState.value.errorDescripcion == "" && _uiState.value.errorDiasCompromiso == ""){
                        prioridadRepository.save(_uiState.value.toEntity())
                        _uiState.update { it.copy(success = true) }
                    }
                }
            }
            PrioridadUiEvent.Delete -> {
//                viewModelScope.launch {
//                    prioridadRepository.delete(_uiState.value.toEntity())
//                }
            }
        }
    }

    fun PrioridadUiState.toEntity() = PrioridadDto(
        prioridadId = prioridadId,
        descripcion = descripcion ?: "",
        diasCompromiso = diasCompromiso
    )
}