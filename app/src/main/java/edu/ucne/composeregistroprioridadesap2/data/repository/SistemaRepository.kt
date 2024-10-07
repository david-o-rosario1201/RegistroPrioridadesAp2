package edu.ucne.composeregistroprioridadesap2.data.repository

import edu.ucne.composeregistroprioridadesap2.data.remote.api.SistemasApi
import edu.ucne.composeregistroprioridadesap2.data.remote.dto.SistemaDto
import javax.inject.Inject

class SistemaRepository @Inject constructor(
    private val sistemaApi: SistemasApi
){
    suspend fun getSistemaById(sistemaId: Int) = sistemaApi.getSistema(sistemaId)

    suspend fun save(sistemaDto: SistemaDto) = sistemaApi.save(sistemaDto)

    suspend fun getAll() = sistemaApi.getAll()
}