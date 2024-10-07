package edu.ucne.composeregistroprioridadesap2.data.repository

import edu.ucne.composeregistroprioridadesap2.data.remote.api.PrioridadesApi
import edu.ucne.composeregistroprioridadesap2.data.remote.dto.PrioridadDto
import javax.inject.Inject

class PrioridadRepository @Inject constructor(
    private val prioridadApi: PrioridadesApi
) {

    suspend fun getPrioridadById(prioridadId: Int) = prioridadApi.getPrioridad(prioridadId)

    suspend fun save(prioridadDto: PrioridadDto?) = prioridadApi.save(prioridadDto)

    suspend fun getPrioridades() = prioridadApi.getAll()
}