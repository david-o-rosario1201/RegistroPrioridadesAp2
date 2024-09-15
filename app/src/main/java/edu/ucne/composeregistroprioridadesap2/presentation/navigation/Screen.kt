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
}