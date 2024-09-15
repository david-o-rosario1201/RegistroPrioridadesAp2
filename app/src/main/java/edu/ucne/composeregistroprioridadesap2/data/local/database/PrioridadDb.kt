package edu.ucne.composeregistroprioridadesap2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.ucne.composeregistroprioridadesap2.data.local.dao.PrioridadDao
import edu.ucne.composeregistroprioridadesap2.data.local.dao.TicketDao
import edu.ucne.composeregistroprioridadesap2.data.local.entities.PrioridadEntity
import edu.ucne.composeregistroprioridadesap2.data.local.entities.TicketEntity

@Database(
    version = 1,
    exportSchema = false,
    entities = [PrioridadEntity::class, TicketEntity::class]
)

@TypeConverters(Converters::class)
abstract class PrioridadDb: RoomDatabase() {
    abstract fun prioridadDao(): PrioridadDao
    abstract fun ticketDao(): TicketDao
}