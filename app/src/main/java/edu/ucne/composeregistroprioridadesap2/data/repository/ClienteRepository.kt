package edu.ucne.composeregistroprioridadesap2.data.repository

import edu.ucne.composeregistroprioridadesap2.data.remote.api.ClientesApi
import edu.ucne.composeregistroprioridadesap2.data.remote.dto.ClienteDto
import javax.inject.Inject

class ClienteRepository @Inject constructor(
    private val clienteApi: ClientesApi
){
    suspend fun getClienteById(clienteId: Int) = clienteApi.getCliente(clienteId)

    suspend fun save(clienteDto: ClienteDto) = clienteApi.save(clienteDto)

    suspend fun getAll() = clienteApi.getAll()
}