package edu.ucne.composeregistroprioridadesap2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import edu.ucne.composeregistroprioridadesap2.data.local.database.PrioridadDb
import edu.ucne.composeregistroprioridadesap2.data.local.entities.PrioridadEntity
import edu.ucne.composeregistroprioridadesap2.presentation.navigation.RegistroPrioridadesAp2
import edu.ucne.composeregistroprioridadesap2.presentation.prioridad.PrioridadListScreen
import edu.ucne.composeregistroprioridadesap2.presentation.prioridad.PrioridadScreen
import edu.ucne.composeregistroprioridadesap2.ui.theme.RegistroPrioridadesAp2Theme

class MainActivity : ComponentActivity() {
    private lateinit var prioridadDb: PrioridadDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        prioridadDb = Room.databaseBuilder(
            applicationContext,
            PrioridadDb::class.java,
            "Prioridad.db"
        ).fallbackToDestructiveMigration()
            .build()

        setContent {
            RegistroPrioridadesAp2Theme {
                val navController = rememberNavController()
                RegistroPrioridadesAp2(
                    prioridadDb = prioridadDb,
                    navHostController = navController
                )
            }
        }
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun PrioridadScreenPreview() {
        RegistroPrioridadesAp2Theme {
            val scope = rememberCoroutineScope()
            PrioridadScreen(
                id = 1,
                prioridadDb = prioridadDb,
                goPrioridadList = {},
                scope = scope
            )
        }
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun PrioridadListScreenPreview(){
        RegistroPrioridadesAp2Theme {
            val scope = rememberCoroutineScope()

            val prioridadList = listOf(
                PrioridadEntity(
                    prioridadId = 1,
                    descripcion = "Alta",
                    diasCompromiso = 8
                )
            )

            PrioridadListScreen(
                prioridadDb = prioridadDb,
                scope = scope,
                prioridadList = prioridadList,
                onAddPrioridad = {},
                onPrioridadClick = {}
            )
        }
    }
}