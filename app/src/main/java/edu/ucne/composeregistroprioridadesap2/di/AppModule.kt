package edu.ucne.composeregistroprioridadesap2.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.composeregistroprioridadesap2.data.local.database.DateAdapter
import edu.ucne.composeregistroprioridadesap2.data.remote.ClientesApi
import edu.ucne.composeregistroprioridadesap2.data.remote.PrioridadesApi
import edu.ucne.composeregistroprioridadesap2.data.remote.SistemasApi
import edu.ucne.composeregistroprioridadesap2.data.remote.TicketsApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    const val BASE_URL = "https://migrartareas-api.azurewebsites.net/"

    @Singleton
    @Provides
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(DateAdapter())
            .build()

    @Provides
    @Singleton
    fun providesPrioridadesApi(moshi: Moshi): PrioridadesApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(PrioridadesApi::class.java)
    }

    @Provides
    @Singleton
    fun providesSistemasApi(moshi: Moshi): SistemasApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(SistemasApi::class.java)
    }

    @Provides
    @Singleton
    fun providesClientesApi(moshi: Moshi): ClientesApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ClientesApi::class.java)
    }

    @Provides
    @Singleton
    fun providesTicketsApi(moshi: Moshi): TicketsApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TicketsApi::class.java)
    }
}