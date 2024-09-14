package edu.ucne.composeregistroprioridadesap2.presentation.prioridad

import edu.ucne.composeregistroprioridadesap2.data.local.entities.PrioridadEntity

data class PrioridadUiState(
    val prioridadId: Int? = null,
    val descripcion: String? = "",
    val diasCompromiso: Int = 0,
    val errorMessge: String? = null,
    val prioridades: List<PrioridadEntity> = emptyList()
)
