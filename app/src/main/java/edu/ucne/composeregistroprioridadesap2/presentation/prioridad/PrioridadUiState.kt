package edu.ucne.composeregistroprioridadesap2.presentation.prioridad

import edu.ucne.composeregistroprioridadesap2.data.remote.dto.PrioridadDto

data class PrioridadUiState(
    val prioridadId: Int? = null,
    val descripcion: String? = "",
    val diasCompromiso: Int = 0,
    val prioridades: List<PrioridadDto> = emptyList(),
    val errorDescripcion: String? = "",
    val errorDiasCompromiso: String? = "",
    val success: Boolean = false
)
