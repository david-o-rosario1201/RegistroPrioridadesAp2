@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package edu.ucne.composeregistroprioridadesap2.presentation.ticket

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composeregistroprioridadesap2.presentation.dropdownmenus.DatePickerField
import edu.ucne.composeregistroprioridadesap2.presentation.dropdownmenus.DropDownMenuClientes
import edu.ucne.composeregistroprioridadesap2.presentation.dropdownmenus.DropDownMenuPrioridades
import edu.ucne.composeregistroprioridadesap2.presentation.dropdownmenus.DropDownMenuSistemas
import edu.ucne.composeregistroprioridadesap2.ui.theme.RegistroPrioridadesAp2Theme

@SuppressLint("NewApi")
@Composable
fun TicketScreen(
    viewModel: TicketViewModel = hiltViewModel(),
    ticketId: Int,
    goTicketList: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TicketBodyScreen(
        ticketId = ticketId,
        uiState = uiState,
        onEvent = viewModel::onEvent,
        goTicketList = goTicketList
    )
}

@Composable
fun TicketBodyScreen(
    ticketId: Int,
    uiState: TicketUiState,
    onEvent: (TicketUiEvent) -> Unit,
    goTicketList: () -> Unit
) {
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    LaunchedEffect(key1 = true, key2 = uiState.success) {
        onEvent(TicketUiEvent.SelectedTicket(ticketId))

        if (uiState.success)
            goTicketList()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if(ticketId == 0) "Crear Ticket" else "Modificar Ticket",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = goTicketList
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Ir hacia ticket list"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        DropDownMenuClientes(
                            uiState = uiState,
                            onEvent = onEvent
                        )
                        uiState.errorCliente?.let {
                            Text(
                                text = it,
                                color = Color.Red
                            )
                        }
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        DropDownMenuSistemas(
                            uiState = uiState,
                            onEvent = onEvent
                        )
                        uiState.errorSistema?.let {
                            Text(
                                text = it,
                                color = Color.Red
                            )
                        }
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        OutlinedTextField(
                            label = {
                                Text("Solicitado Por")
                            },
                            value = uiState.solicitadoPor ?: "",
                            onValueChange = {
                                onEvent(TicketUiEvent.SolicitadoPorChangend(it))
                            },
                            modifier = Modifier
                                .padding(15.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .onGloballyPositioned { coordinates ->
                                    textFieldSize = coordinates.size.toSize()
                                },
                            shape = RoundedCornerShape(10.dp)
                        )
                        uiState.errorSolicitadoPor?.let {
                            Text(
                                text = it,
                                color = Color.Red
                            )
                        }
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        OutlinedTextField(
                            label = { Text("Asunto") },
                            value = uiState.asunto ?: "",
                            onValueChange = {
                                onEvent(TicketUiEvent.AsuntoChanged(it))
                            },
                            modifier = Modifier
                                .padding(15.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .onGloballyPositioned { coordinates ->
                                    textFieldSize = coordinates.size.toSize()
                                },
                            shape = RoundedCornerShape(10.dp)
                        )
                        uiState.errorAsunto?.let {
                            Text(
                                text = it,
                                color = Color.Red
                            )
                        }
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        DatePickerField(
                            uiState = uiState,
                            onEvent = onEvent
                        )
                        uiState.errorFecha?.let {
                            Text(
                                text = it,
                                color = Color.Red
                            )
                        }
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        DropDownMenuPrioridades(
                            uiState = uiState,
                            onEvent = onEvent
                        )
                        uiState.errorPrioridad?.let {
                            Text(
                                text = it,
                                color = Color.Red
                            )
                        }
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        OutlinedTextField(
                            label = { Text("Descripción") },
                            value = uiState.descripcion ?: "",
                            onValueChange = {
                                onEvent(TicketUiEvent.DescripcionChanged(it))
                            },
                            modifier = Modifier
                                .padding(15.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .onGloballyPositioned { coordinates ->
                                    textFieldSize = coordinates.size.toSize()
                                },
                            shape = RoundedCornerShape(10.dp)
                        )
                        uiState.errorDescripcion?.let {
                            Text(
                                text = it,
                                color = Color.Red
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedButton(
                        onClick = {
                            onEvent(TicketUiEvent.Save)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Guardar Ticket"
                        )
                        Text(
                            text = if(ticketId == 0) "Crear Ticket" else "Modificar Ticket"
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TicketScreenPreview(){
    RegistroPrioridadesAp2Theme {
        TicketScreen(
            ticketId = 0,
            goTicketList = {}
        )
    }
}