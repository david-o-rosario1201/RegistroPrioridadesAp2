package edu.ucne.composeregistroprioridadesap2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.composeregistroprioridadesap2.data.local.dao.PrioridadDao
import edu.ucne.composeregistroprioridadesap2.data.local.entities.PrioridadEntity

@Database(
    version = 1,
    exportSchema = false,
    entities = [PrioridadEntity::class]
)
abstract class PrioridadDb: RoomDatabase() {
    abstract fun prioridadDao(): PrioridadDao
}