package edu.ucne.composeregistroprioridadesap2.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.composeregistroprioridadesap2.data.local.database.PrioridadDb
import edu.ucne.composeregistroprioridadesap2.presentation.prioridad.PrioridadListScreen
import edu.ucne.composeregistroprioridadesap2.presentation.prioridad.PrioridadScreen

@Composable
fun RegistroPrioridadesAp2(
    prioridadDb: PrioridadDb,
    navHostController: NavHostController
){
    val scope = rememberCoroutineScope()

    val lifecycleOwner = LocalLifecycleOwner.current
    val prioridadList by prioridadDb.prioridadDao().getAll()
        .collectAsStateWithLifecycle(
            initialValue = emptyList(),
            lifecycleOwner = lifecycleOwner,
            minActiveState = Lifecycle.State.STARTED
        )

    NavHost(
        navController = navHostController,
        startDestination = Screen.PrioridadListScreen
    ) {
        composable<Screen.PrioridadListScreen> {
            PrioridadListScreen(
                prioridadDb = prioridadDb,
                prioridadList = prioridadList,
                onAddPrioridad = {
                    navHostController.navigate(Screen.PrioridadScreen(0))
                },
                scope = scope,
                onPrioridadClick = { prioridad ->
                    navHostController.navigate(Screen.PrioridadScreen(prioridad.prioridadId ?: 0))
                }
            )
        }
        composable<Screen.PrioridadScreen> { argumento ->
            val id = argumento.toRoute<Screen.PrioridadScreen>().prioridadId

            PrioridadScreen(
                id = id,
                prioridadDb = prioridadDb,
                scope = scope,
                goPrioridadList = {
                    navHostController.navigate(
                        Screen.PrioridadListScreen
                    )
                }
            )
        }
    }
}