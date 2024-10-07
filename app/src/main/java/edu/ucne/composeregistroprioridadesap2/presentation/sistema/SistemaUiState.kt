package edu.ucne.composeregistroprioridadesap2.presentation.sistema

import edu.ucne.composeregistroprioridadesap2.data.remote.dto.SistemaDto

data class SistemaUiState(
    val sistemaId: Int? = null,
    val nombre: String? = "",
    val sistemas: List<SistemaDto> = emptyList(),
    val errorNombre: String? = "",
    val success: Boolean = false
)
