package edu.ucne.composeregistroprioridadesap2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.composeregistroprioridadesap2.data.local.entities.TicketEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TicketDao {
    @Upsert
    suspend fun save(ticket: TicketEntity)

    @Query("""
        SELECT * FROM Tickets
        WHERE ticketId = :id
        LIMIT 1
    """)
    suspend fun find(id: Int): TicketEntity?

    @Query("""
        SELECT * FROM Tickets
        WHERE cliente = :cliente
        LIMIT 1
    """)
    suspend fun findCliente(cliente: String): TicketEntity?

    @Query("""
        SELECT * FROM Tickets
        WHERE asunto = :asunto
        LIMIT 1
    """)
    suspend fun findAsunto(asunto: String): TicketEntity?

    @Query("""
        SELECT * FROM Tickets
        WHERE descripcion = :descripcion
        LIMIT 1
    """)
    suspend fun findDescripcion(descripcion: String): TicketEntity?

    @Delete
    suspend fun delete(ticket: TicketEntity)

    @Query("SELECT * FROM Tickets")
    fun getAll(): Flow<List<TicketEntity>>
}