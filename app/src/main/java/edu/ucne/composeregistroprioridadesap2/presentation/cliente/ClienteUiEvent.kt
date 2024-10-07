package edu.ucne.composeregistroprioridadesap2.presentation.cliente

sealed interface ClienteUiEvent {
    data class ClienteIdChanged(val clienteId: Int): ClienteUiEvent
    data class NombreChanged(val nombre: String): ClienteUiEvent
    data class RncChanged(val rnc: String): ClienteUiEvent
    data class EmailChanged(val email: String): ClienteUiEvent
    data class DireccionChanged(val direccion: String): ClienteUiEvent
    data class SelectedCliente(val clienteId: Int): ClienteUiEvent
    data object Save: ClienteUiEvent
    data object Delete: ClienteUiEvent
}