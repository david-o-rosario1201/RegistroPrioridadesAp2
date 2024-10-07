package edu.ucne.composeregistroprioridadesap2.presentation.cliente

import edu.ucne.composeregistroprioridadesap2.data.remote.dto.ClienteDto
import edu.ucne.composeregistroprioridadesap2.data.remote.dto.ClientesDetalleCelulares
import edu.ucne.composeregistroprioridadesap2.data.remote.dto.ClientesDetalleTelefonos

data class ClienteUiState(
    val clienteId: Int? = null,
    val nombre: String? = "",
    val rnc: String? = "",
    val email: String? = "",
    val direccion: String? = "",
    val clientesDetalleCelulares: List<ClientesDetalleCelulares> = emptyList(),
    val clientesDetalleTelefonos: List<ClientesDetalleTelefonos> = emptyList(),
    val clientes: List<ClienteDto> = emptyList(),
    val errorNombre: String? = "",
    val errorRNC: String? = "",
    val errorEmail: String? = "",
    val errorDireccion: String? = "",
    val success: Boolean = false
)
