package edu.ucne.composeregistroprioridadesap2.presentation.sistema

sealed interface SistemaUiEvent {
    data class SistemaIdChanged(val sistemaId: Int): SistemaUiEvent
    data class NombreChanged(val nombre: String): SistemaUiEvent
    data class SelectedSistema(val sistemaId: Int): SistemaUiEvent
    data object Save: SistemaUiEvent
    data object Delete: SistemaUiEvent
}