package edu.ucne.composeregistroprioridadesap2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Warning
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.composeregistroprioridadesap2.presentation.navigation.RegistroPrioridadesAp2NavHost
import edu.ucne.composeregistroprioridadesap2.ui.theme.RegistroPrioridadesAp2Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroPrioridadesAp2Theme {
                val navController = rememberNavController()
                val items = BuildNavigationItems(0)

                RegistroPrioridadesAp2NavHost(
                    navHostController = navController,
                    items = items
                )
            }
        }
    }
}

fun BuildNavigationItems(taskCount: Int): List<NavigationItem>{
    return listOf(
        NavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = Route.HOME
        ),
        NavigationItem(
            title = "Prioridades",
            selectedIcon = Icons.Filled.Warning,
            unselectedIcon = Icons.Outlined.Warning,
            route = Route.PRIORIDAD
        ),
        NavigationItem(
            title = "Tickets",
            selectedIcon = Icons.Filled.Star,
            unselectedIcon = Icons.Outlined.Star,
            route = Route.TICKET
        )
    )
}

enum class Route{
    HOME,
    PRIORIDAD,
    TICKET
}