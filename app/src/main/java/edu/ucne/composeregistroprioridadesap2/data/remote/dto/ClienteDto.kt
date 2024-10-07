package edu.ucne.composeregistroprioridadesap2.data.remote.dto

data class ClienteDto(
    val clienteId: Int?,
    val nombre: String,
    val rnc: String,
    val email: String,
    val direccion: String,
    val clientesDetalleCelulares: List<ClientesDetalleCelulares>,
    val clientesDetalleTelefonos: List<ClientesDetalleTelefonos>
)
