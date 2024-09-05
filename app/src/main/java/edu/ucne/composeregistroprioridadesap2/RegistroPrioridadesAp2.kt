package edu.ucne.composeregistroprioridadesap2

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.composeregistroprioridadesap2.data.local.entities.PrioridadEntity
import edu.ucne.composeregistroprioridadesap2.presentation.prioridad.PrioridadListScreen
import edu.ucne.composeregistroprioridadesap2.presentation.prioridad.PrioridadScreen

@Composable
fun RegistroPrioridadesAp2(
    navHostController: NavHostController
){
    val scope = rememberCoroutineScope()

//    val lifecycleOwner = LocalLifecycleOwner.current
//    val prioridadList by prioridadDb.prioridadDao().getAll()
//        .collectAsStateWithLifecycle(
//            initialValue = emptyList(),
//            lifecycleOwner = lifecycleOwner,
//            minActiveState = Lifecycle.State.STARTED
//        )

    val prioridadList = listOf(
        PrioridadEntity(
            1,
            "",
            1
        )
    )

    NavHost(
        navController = navHostController,
        startDestination = Screen.PrioridadList
    ) {
        composable<Screen.PrioridadList> {
            PrioridadListScreen(
                prioridadList = prioridadList,
                onAddPrioridad = {
                    navHostController.navigate(Screen.Prioridad(0))
                },
                scope = scope
            )
        }
        composable<Screen.Prioridad> {
            PrioridadScreen(
                scope = scope,
                goPrioridadList = {
                    navHostController.navigate(
                        Screen.PrioridadList
                    )
                }
            )
        }
    }
}