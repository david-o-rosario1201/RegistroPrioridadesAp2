package edu.ucne.composeregistroprioridadesap2.presentation.prioridad

sealed interface PrioridadUiEvent {
    data class PrioridadIdChanged(val prioridadId: Int) : PrioridadUiEvent
    data class DescripcionChanged(val descripcion: String) : PrioridadUiEvent
    data class DiasCompromisoChanged(val diasCompromiso: String) : PrioridadUiEvent
    data class SelectedPrioridad(val prioridadId: Int) : PrioridadUiEvent
    object Save : PrioridadUiEvent
    object Delete : PrioridadUiEvent
}