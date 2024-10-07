package edu.ucne.composeregistroprioridadesap2.presentation.dropdownmenus

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import edu.ucne.composeregistroprioridadesap2.presentation.ticket.TicketUiEvent
import edu.ucne.composeregistroprioridadesap2.presentation.ticket.TicketUiState

@Composable
fun DropDownMenuClientes(
    uiState: TicketUiState,
    onEvent: (TicketUiEvent) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectItem by remember { mutableStateOf(uiState.clienteId.toString()) }
    var textFielSize by remember { mutableStateOf(Size.Zero) }
    val clientes = uiState.clientes

    val icon = if(expanded) {
        Icons.Filled.KeyboardArrowUp
    }else{
        Icons.Filled.KeyboardArrowDown
    }

    LaunchedEffect(uiState.prioriodadId) {
        selectItem = clientes.find { it.clienteId == uiState.sistemaId }?.nombre ?: ""
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            label = {
                Text("Cliente")
            },
            value = selectItem,
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .onGloballyPositioned { coordinates ->
                    textFielSize = coordinates.size.toSize()
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
            clientes.forEach { cliente ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        selectItem = cliente.nombre
                        onEvent(TicketUiEvent.ClienteIdChanged(cliente.clienteId.toString()))
                    },
                    text = {
                        Text(text = cliente.nombre)
                    }
                )
            }
        }
    }
}