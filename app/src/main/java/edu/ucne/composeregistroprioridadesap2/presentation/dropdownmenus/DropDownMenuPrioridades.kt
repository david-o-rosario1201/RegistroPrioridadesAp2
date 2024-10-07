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
fun DropDownMenuPrioridades(
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
            priorities.forEach { priority ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        selectItem = priority.descripcion
                        onEvent(TicketUiEvent.PrioridadIdChanged(priority.prioridadId.toString()))
                    },
                    text = {
                        Text(text = priority.descripcion)
                    }
                )
            }
        }
    }
}