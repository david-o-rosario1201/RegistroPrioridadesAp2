@file:OptIn(ExperimentalMaterial3Api::class)

package edu.ucne.composeregistroprioridadesap2.presentation.ticket

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composeregistroprioridadesap2.ui.theme.RegistroPrioridadesAp2Theme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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
        onEvent(TicketUiEvent.ticketSelected(ticketId))

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
                        text = "Tickets",
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
                    OutlinedTextField(
                        label = { Text("Cliente") },
                        value = uiState.cliente ?: "",
                        onValueChange = {
                            onEvent(TicketUiEvent.clienteChanged(it))
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

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        label = { Text("Asunto") },
                        value = uiState.asunto ?: "",
                        onValueChange = {
                            onEvent(TicketUiEvent.asuntoChanged(it))
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

                    Spacer(modifier = Modifier.height(10.dp))

                    DatePickerField(
                        uiState = uiState,
                        onEvent = onEvent
                    )

                    DropDownMenu(
                        uiState = uiState,
                        onEvent = onEvent
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        label = { Text("Descripción") },
                        value = uiState.descripcion ?: "",
                        onValueChange = {
                            onEvent(TicketUiEvent.descripcionChanged(it))
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

                    Spacer(modifier = Modifier.height(10.dp))

                    uiState.errorMessage?.let {
                        Text(text = it, color = Color.Red)
                    }

                    OutlinedButton(
                        onClick = {
                            onEvent(TicketUiEvent.Save)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Guardar Ticket"
                        )
                        Text("Guardar")
                    }
                }
            }
        }
    }
}

@Composable
fun DatePickerField(
    uiState: TicketUiState,
    onEvent: (TicketUiEvent) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(uiState.fecha) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    val context = LocalContext.current

    LaunchedEffect(uiState.fecha) {
        selectedDate = uiState.fecha
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            label = { Text("Fecha") },
            value = selectedDate?.let { dateFormat.format(it) } ?: "Elija una fecha",
            onValueChange = {/* No se necesita aquí, ya que es solo lectura */},
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                }
                .clickable {
                    expanded = true
                },
            shape = RoundedCornerShape(10.dp),
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        expanded = !expanded
                    }
                )
            },
            readOnly = true
        )

        if (expanded) {
            val calendar = Calendar.getInstance()
            android.app.DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    val newDate = calendar.time
                    selectedDate = newDate
                    onEvent(TicketUiEvent.fechaChanged(newDate))
                    expanded = false
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
}


@Composable
fun DropDownMenu(
    uiState: TicketUiState,
    onEvent: (TicketUiEvent) -> Unit
){
    var expanded by remember { mutableStateOf(false) }
    var selectItem by remember { mutableStateOf(uiState.prioriodadId.toString()) }
    var textFielSize by remember { mutableStateOf(Size.Zero) }
    val priorities = uiState.prioridades

    val icon = if(expanded) {
        Icons.Filled.KeyboardArrowUp
    }else{
        Icons.Filled.KeyboardArrowDown
    }

    LaunchedEffect(uiState.prioriodadId) {
        selectItem = priorities.find { it.prioridadId == uiState.prioriodadId }?.descripcion ?: ""
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            label = {
                Text("Prioridad")
            },
            value = selectItem,
            onValueChange = { /* No se necesita aquí, ya que es solo lectura */ },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .onGloballyPositioned { coordinates ->
                    textFielSize = coordinates.size.toSize()
                }
                .clickable {
                    expanded = true
                },
            shape = RoundedCornerShape(10.dp),
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { expanded = !expanded }
                )
            },
            readOnly = true
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(
                with(LocalDensity.current) {
                    textFielSize.width.toDp()
                }
            )
        ) {
            priorities.forEach { priority ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        selectItem = priority.descripcion
                        onEvent(TicketUiEvent.prioridadIdChanged(priority.prioridadId.toString()))
                    },
                    text = {
                        Text(text = priority.descripcion)
                    }
                )
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