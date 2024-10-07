package edu.ucne.composeregistroprioridadesap2.presentation.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.composeregistroprioridadesap2.NavigationItem
import edu.ucne.composeregistroprioridadesap2.Route
import edu.ucne.composeregistroprioridadesap2.presentation.home.HomeScreen
import edu.ucne.composeregistroprioridadesap2.presentation.prioridad.PrioridadListScreen
import edu.ucne.composeregistroprioridadesap2.presentation.prioridad.PrioridadScreen
import edu.ucne.composeregistroprioridadesap2.presentation.sistema.SistemaListScreen
import edu.ucne.composeregistroprioridadesap2.presentation.sistema.SistemaScreen
import edu.ucne.composeregistroprioridadesap2.presentation.ticket.TicketListScreen
import edu.ucne.composeregistroprioridadesap2.presentation.ticket.TicketScreen
import kotlinx.coroutines.launch


@Composable
fun RegistroPrioridadesAp2NavHost(
    navHostController: NavHostController,
    items: List<NavigationItem>
){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "  Menu",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))
                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = {
                            Text(item.title)
                        },
                        selected = index == selectedItemIndex,
                        onClick = {
                            if(item.route == Route.HOME)
                                navHostController.navigate(Screen.HomeScreen)
                            if(item.route == Route.PRIORIDAD)
                                navHostController.navigate(Screen.PrioridadListScreen)
                            if(item.route == Route.TICKET)
                                navHostController.navigate(Screen.TicketListScreen)
                            if(item.route == Route.SISTEMA)
                                navHostController.navigate(Screen.SistemaListScreen)
                            selectedItemIndex = index
                            scope.launch {drawerState.close()}
                        },
                        icon = {
                            Icon(
                                imageVector = if(index == selectedItemIndex){
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        drawerState = drawerState
    ) {
        NavHost(
            navController = navHostController,
            startDestination = Screen.HomeScreen
        ) {

            composable<Screen.HomeScreen>{
                HomeScreen(
                    drawerState = drawerState,
                    scope = scope,
                    navHostController = navHostController
                )
            }

            composable<Screen.PrioridadListScreen> {
                PrioridadListScreen(
                    drawerState = drawerState,
                    scope = scope,
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
            composable<Screen.TicketListScreen> {
                TicketListScreen(
                    drawerState = drawerState,
                    scope = scope,
                    onAddTicket = {
                        navHostController.navigate(Screen.TicketScreen( 0))
                    },
                    onTicketClick = { ticketId ->
                        navHostController.navigate(Screen.TicketScreen(ticketId = ticketId))
                    }
                )
            }
            composable<Screen.TicketScreen> {argumento ->
                val ticketId = argumento.toRoute<Screen.TicketScreen>().ticketId

                TicketScreen(
                    ticketId = ticketId,
                    goTicketList = {
                        navHostController.navigate(
                            Screen.TicketListScreen
                        )
                    }
                )
            }
            composable<Screen.SistemaListScreen> {
                SistemaListScreen(
                    drawerState = drawerState,
                    scope = scope,
                    onClickSistema = { sistemaId ->
                        navHostController.navigate(Screen.SistemaScreen(sistemaId))
                    },
                    onAddSistema = {
                        navHostController.navigate(Screen.SistemaScreen(0))
                    }
                )
            }
            composable<Screen.SistemaScreen> { argumento ->
                val sistemaId = argumento.toRoute<Screen.SistemaScreen>().sistemaId
                SistemaScreen(
                    sistemaId = sistemaId,
                    goSistemas = {
                        navHostController.navigate(Screen.SistemaListScreen)
                    }
                )
            }
        }
    }
}