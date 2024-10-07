package edu.ucne.composeregistroprioridadesap2.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object PrioridadListScreen : Screen()

    @Serializable
    data class PrioridadScreen(val prioridadId: Int) : Screen()

    @Serializable
    data object TicketListScreen : Screen()

    @Serializable
    data class TicketScreen(val ticketId: Int) : Screen()

    @Serializable
    data object HomeScreen : Screen()

    @Serializable
    data object SistemaListScreen: Screen()

    @Serializable
    data class SistemaScreen(val sistemaId: Int): Screen()

    @Serializable
    data object ClienteListScreen: Screen()

    @Serializable
    data class ClienteScreen(val clienteId: Int): Screen()
}