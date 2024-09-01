package edu.ucne.composeregistroprioridadesap2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.composeregistroprioridadesap2.data.local.entities.PrioridadEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PrioridadDao {
    @Upsert()
    suspend fun save(prioridad: PrioridadEntity)

    @Query(
        """
            SELECT * FROM Prioridades
            WHERE prioridadId = :id
            LIMIT 1
        """
    )
    suspend fun find(id: Int): PrioridadEntity?

    @Query(
        """
            SELECT * FROM PRIORIDADES
            WHERE descripcion = :descripcion
            LIMIT 1
        """
    )
    suspend fun findDescripcion(descripcion: String): PrioridadEntity?

    @Delete
    suspend fun delete(prioridad: PrioridadEntity)

    @Query("SELECT * FROM PRIORIDADES")
    fun getAll(): Flow<List<PrioridadEntity>>
}