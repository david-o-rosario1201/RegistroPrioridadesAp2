package edu.ucne.composeregistroprioridadesap2.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.composeregistroprioridadesap2.presentation.prioridad.PrioridadListScreen
import edu.ucne.composeregistroprioridadesap2.presentation.prioridad.PrioridadScreen

@Composable
fun RegistroPrioridadesAp2NavHost(
    navHostController: NavHostController
){
    NavHost(
        navController = navHostController,
        startDestination = Screen.PrioridadListScreen
    ) {
        composable<Screen.PrioridadListScreen> {
            PrioridadListScreen(
                onAddPrioridad = {
                    navHostController.navigate(Screen.PrioridadScreen(0))
                },
                onPrioridadClick = { prioridadId ->
                    navHostController.navigate(Screen.PrioridadScreen(prioridadId = prioridadId))
                }
            )
        }
        composable<Screen.PrioridadScreen> { argumento ->
            val id = argumento.toRoute<Screen.PrioridadScreen>().prioridadId

            PrioridadScreen(
                prioridadId = id,
                goPrioridadList = {
                    navHostController.navigate(
                        Screen.PrioridadListScreen
                    )
                }
            )
        }
    }
}