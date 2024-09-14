@file:OptIn(ExperimentalMaterial3Api::class)

package edu.ucne.composeregistroprioridadesap2.presentation.prioridad

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun PrioridadScreen(
    viewModel: PrioridadViewModel = hiltViewModel(),
    prioridadId: Int,
    goPrioridadList: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PrioridadBodyScreen(
        prioridadId = prioridadId,
        uiState = uiState,
        onEvent = viewModel::onEvent,
        goPrioridadList = goPrioridadList
    )
}

@Composable
fun PrioridadBodyScreen(
    prioridadId: Int,
    uiState: PrioridadUiState,
    onEvent: (PrioridadUiEvent) -> Unit,
    goPrioridadList: () -> Unit
){
    LaunchedEffect(key1 = true, key2 = uiState.success) {
        onEvent(PrioridadUiEvent.SelectedPrioridad(prioridadId))

        if(uiState.success)
            goPrioridadList()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(
                            text = "Prioridades",
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(
                                    end = 50.dp
                                )
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = goPrioridadList
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Lista"
                        )
                    }
                }
            )
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(15.dp)
        ){
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    OutlinedTextField(
                        label = {
                            Text("Descripción")
                        },
                        value = uiState.descripcion ?: "",
                        onValueChange = {
                            onEvent(PrioridadUiEvent.DescripcionChanged(it))
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        label = {
                            Text("Días de compromiso")
                        },
                        value = if(uiState.diasCompromiso == 0) "" else uiState.diasCompromiso.toString(),
                        onValueChange = {
                            val diasCompromiso = it.toIntOrNull() ?: 0
                            onEvent(PrioridadUiEvent.DiasCompromisoChanged(diasCompromiso.toString()))
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    uiState.errorMessge?.let {
                        Text(
                            text = it,
                            color = Color.Red
                        )
                    }

                    OutlinedButton(
                        onClick = {
                            onEvent(PrioridadUiEvent.Save)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Guardar Prioridad"
                        )
                        Text("Guardar")
                    }
                }
            }
        }
    }
}