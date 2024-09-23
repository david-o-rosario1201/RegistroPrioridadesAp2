package edu.ucne.composeregistroprioridadesap2.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.composeregistroprioridadesap2.data.local.database.RegistroAplicada2Db
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun providePrioridadDb(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            RegistroAplicada2Db::class.java,
            "RegistroAplicada2.db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun providePrioridadDao(registroAplicada2Db: RegistroAplicada2Db) = registroAplicada2Db.prioridadDao()

    @Provides
    @Singleton
    fun provideTicketDao(cualquiervainaDb: RegistroAplicada2Db) = cualquiervainaDb.ticketDao()
}