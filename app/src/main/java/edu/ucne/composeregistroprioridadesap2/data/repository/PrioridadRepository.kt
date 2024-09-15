package edu.ucne.composeregistroprioridadesap2.data.repository

import edu.ucne.composeregistroprioridadesap2.data.local.dao.PrioridadDao
import edu.ucne.composeregistroprioridadesap2.data.local.entities.PrioridadEntity
import javax.inject.Inject

class PrioridadRepository @Inject constructor(
    private val prioridadDao: PrioridadDao
) {
    suspend fun save (prioridad: PrioridadEntity) = prioridadDao.save(prioridad)

    suspend fun getPrioridadById(prioridadId: Int) = prioridadDao.find(prioridadId)

    suspend fun delete(prioridad: PrioridadEntity) = prioridadDao.delete(prioridad)

    suspend fun findDescripcion(descripcion: String) = prioridadDao.findDescripcion(descripcion)

    fun getPrioridades() = prioridadDao.getAll()
}