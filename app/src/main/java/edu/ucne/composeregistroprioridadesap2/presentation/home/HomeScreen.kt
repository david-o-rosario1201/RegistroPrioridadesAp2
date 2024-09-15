@file:OptIn(ExperimentalMaterial3Api::class)

package edu.ucne.composeregistroprioridadesap2.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import edu.ucne.composeregistroprioridadesap2.R
import edu.ucne.composeregistroprioridadesap2.Route
import edu.ucne.composeregistroprioridadesap2.presentation.navigation.Screen
import edu.ucne.composeregistroprioridadesap2.ui.theme.RegistroPrioridadesAp2Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    navHostController: NavHostController
){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Home",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }
                }
            )
        }
    ){ values ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(values)
            .background(Color.White))
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    CardHome(
                        painter = painterResource(id = R.drawable.priority_background),
                        contentDescription = "Prioridad List",
                        title = "Prioridad List",
                        route = Route.PRIORIDAD,
                        navController = navHostController
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    CardHome(
                        painter = painterResource(id = R.drawable.ticket_background),
                        contentDescription = "Ticket List",
                        title = "Ticket List",
                        route = Route.TICKET,
                        navController = navHostController
                    )
                }
            }
        }
    }
}

@Composable
fun CardHome(
    painter: Painter,
    contentDescription: String,
    title: String,
    modifier: Modifier = Modifier,
    route: Route,
    navController: NavController
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                if(route == Route.PRIORIDAD)
                    navController.navigate(Screen.PrioridadListScreen)
                if(route == Route.TICKET)
                    navController.navigate(Screen.TicketListScreen)
            },
        shape = RoundedCornerShape(15.dp)
    ){
        Box(
            modifier = Modifier
                .height(200.dp)
        ){
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 300f
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ){
                Text(
                    text = title,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview(){
    RegistroPrioridadesAp2Theme {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val navController = rememberNavController()

        HomeScreen(
            drawerState = drawerState,
            scope = scope,
            navHostController = navController
        )
    }
}