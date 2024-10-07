package edu.ucne.composeregistroprioridadesap2.presentation.sistema

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composeregistroprioridadesap2.data.remote.dto.SistemaDto
import edu.ucne.composeregistroprioridadesap2.data.repository.SistemaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SistemaViewModel @Inject constructor(
    private val sistemaRepository: SistemaRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(SistemaUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getSistemas()
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

    fun onEvent(event: SistemaUiEvent){
        when(event){
            is SistemaUiEvent.SistemaIdChanged -> {
                _uiState.update {
                    it.copy(sistemaId = event.sistemaId)
                }
            }
            is SistemaUiEvent.NombreChanged -> {
                _uiState.update {
                    it.copy(
                        nombre = event.nombre,
                        errorNombre = ""
                    )
                }
            }
            is SistemaUiEvent.SelectedSistema -> {
                viewModelScope.launch {
                    if(event.sistemaId > 0){
                        val sistema = sistemaRepository.getSistemaById(event.sistemaId)
                        _uiState.update {
                            it.copy(
                                sistemaId = sistema.sistemaId,
                                nombre = sistema.nombre
                            )
                        }
                    }
                }
            }
            SistemaUiEvent.Save -> {
                viewModelScope.launch {
                    val sistemaBuscado = sistemaRepository.getAll()
                        .find { sistema ->
                            sistema.nombre.lowercase() == _uiState.value.nombre?.lowercase()
                        }

                    if(_uiState.value.nombre.isNullOrEmpty()){
                        _uiState.update {
                            it.copy(errorNombre = "Este campo no puede estar vacío")
                        }
                    }
                    else if(sistemaBuscado != null && _uiState.value.sistemaId != sistemaBuscado.sistemaId){
                        _uiState.update {
                            it.copy(errorNombre = "Ya existe un sistema con este nombre")
                        }
                    }

                    if(_uiState.value.errorNombre == ""){
                        sistemaRepository.save(_uiState.value.toEntity())
                        _uiState.update {
                            it.copy(success = true)
                        }
                    }
                }
            }
            SistemaUiEvent.Delete -> TODO()
        }
    }

    fun SistemaUiState.toEntity() = SistemaDto(
        sistemaId = sistemaId,
        nombre = nombre ?: ""
    )
}