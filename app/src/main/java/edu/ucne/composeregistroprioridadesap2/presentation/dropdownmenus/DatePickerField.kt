package edu.ucne.composeregistroprioridadesap2.presentation.dropdownmenus

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import edu.ucne.composeregistroprioridadesap2.presentation.ticket.TicketUiEvent
import edu.ucne.composeregistroprioridadesap2.presentation.ticket.TicketUiState
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
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
                    onEvent(TicketUiEvent.FechaChanged(newDate))
                    expanded = false
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
}