package edu.ucne.composeregistroprioridadesap2.presentation.prioridad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composeregistroprioridadesap2.data.local.entities.PrioridadEntity
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
            prioridadRepository.getPrioridades().collect(){ prioridades ->
                _uiState.update {
                    it.copy(prioridades = prioridades)
                }
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
                    it.copy(descripcion = event.descripcion)
                }
            }
            is PrioridadUiEvent.DiasCompromisoChanged -> {
                _uiState.update {
                    it.copy(diasCompromiso = event.diasCompromiso.toInt())
                }
            }
            is PrioridadUiEvent.SelectedPrioridad -> {
                viewModelScope.launch {
                    if(event.prioridadId > 0){
                        val prioridad = prioridadRepository.getPrioridadById(event.prioridadId)
                        _uiState.update {
                            it.copy(
                                prioridadId = prioridad?.prioridadId,
                                descripcion = prioridad?.descripcion,
                                diasCompromiso = prioridad?.diasCompromiso ?: 0
                            )
                        }
                    }
                }
            }
            PrioridadUiEvent.Save -> {
                viewModelScope.launch {
                    val prioridadId = _uiState.value.prioridadId ?: 0

                    val prioridadBuscada = prioridadRepository.findDescripcion(_uiState.value.descripcion ?: "")

                    if (_uiState.value.descripcion.isNullOrBlank()) {
                        _uiState.update {
                            it.copy(errorMessge = "El campo descripción no puede ir vacío")
                        }
                    }
                    else if (_uiState.value.diasCompromiso <= 0 || _uiState.value.diasCompromiso > 30) {
                        _uiState.update {
                            it.copy(errorMessge = "El campo días de compromiso no puede ser menor a 1 o mayor a 30")
                        }
                    }
                    else if (prioridadId == 0 && prioridadBuscada != null && prioridadBuscada.prioridadId != 0) {
                        _uiState.update {
                            it.copy(errorMessge = "Ya existe una prioridad con esta descripción")
                        }
                    }
                    else {
                        prioridadRepository.save(_uiState.value.toEntity())
                        _uiState.update { it.copy(success = true) }
                    }
                }
            }
            PrioridadUiEvent.Delete -> {
                viewModelScope.launch {
                    prioridadRepository.delete(_uiState.value.toEntity())
                }
            }
        }
    }

    fun PrioridadUiState.toEntity() = PrioridadEntity(
        prioridadId = prioridadId,
        descripcion = descripcion ?: "",
        diasCompromiso = diasCompromiso
    )
}