package edu.ucne.composeregistroprioridadesap2

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object PrioridadList : Screen()

    @Serializable
    data class Prioridad(val prioridadId: Int) : Screen()
}