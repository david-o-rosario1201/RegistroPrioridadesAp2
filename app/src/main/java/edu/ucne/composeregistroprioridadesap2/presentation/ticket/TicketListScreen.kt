@file:OptIn(ExperimentalMaterial3Api::class)

package edu.ucne.composeregistroprioridadesap2.presentation.ticket

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composeregistroprioridadesap2.R
import edu.ucne.composeregistroprioridadesap2.data.local.entities.PrioridadEntity
import edu.ucne.composeregistroprioridadesap2.data.local.entities.TicketEntity
import edu.ucne.composeregistroprioridadesap2.ui.theme.RegistroPrioridadesAp2Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TicketListScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    viewModel: TicketViewModel = hiltViewModel(),
    onTicketClick: (Int) -> Unit,
    onAddTicket: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TicketListBodyScreen(
        drawerState = drawerState,
        scope = scope,
        uiState = uiState,
        onTicketClick = onTicketClick,
        onAddTicket = onAddTicket,
        onDeleteTicket = { ticketId ->
            viewModel.onEvent(
                TicketUiEvent.ticketIdChanged(ticketId)
            )
            viewModel.onEvent(TicketUiEvent.Delete)
        }
    )
}

@Composable
fun TicketListBodyScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    uiState: TicketUiState,
    onTicketClick: (Int) -> Unit,
    onAddTicket: () -> Unit,
    onDeleteTicket: (Int) -> Unit
){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Lista de Tickets",
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTicket
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Agregar nuevo ticket"
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(
                    start = 15.dp,
                    end = 15.dp
                )
        ){
            Spacer(modifier = Modifier.height(32.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ){
                if(uiState.tickets.isEmpty()){
                    item {
                        Column(
                            modifier = Modifier
                                .fillParentMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ){
                            Image(
                                painter = painterResource(R.drawable.empty_icon),
                                contentDescription = "Lista vacía"
                            )
                            Text(
                                text = "Lista vacía",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }else{
                    items(uiState.tickets){
                        TicketRow(
                            it = it,
                            prioridades = uiState.prioridades,
                            onTicketClick = onTicketClick,
                            onDeleteTicket = onDeleteTicket
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TicketRow(
    it: TicketEntity,
    prioridades: List<PrioridadEntity>,
    onTicketClick: (Int) -> Unit,
    onDeleteTicket: (Int) -> Unit
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    val descripcionPrioridad = prioridades.find { prioridad ->
        prioridad.prioridadId == it.prioridadId
    }?.descripcion ?: "Sin Prioridad"

    Card(
        onClick = {
            onTicketClick(it.ticketId ?: 0)
        },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFB0BEC5)
        ),
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .heightIn(min = 160.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ticket_image),
                contentDescription = "Image Ticket",
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Id: ${it.ticketId}",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Fecha: " + it.fecha?.let { dateFormat.format(it) }.toString(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Prioridad: ${descripcionPrioridad}",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Cliente: ${it.cliente}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Asunto: ${it.asunto}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Descripción: ${it.descripcion}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            IconButton(
                onClick = {
                    onDeleteTicket(it.ticketId ?: 0)
                },
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar ticket"
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TicketListScreenPreview(){
    RegistroPrioridadesAp2Theme {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        TicketListScreen(
            drawerState = drawerState,
            scope = scope,
            onTicketClick = {},
            onAddTicket = {}
        )
    }
}