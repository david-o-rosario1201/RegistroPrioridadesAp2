@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package edu.ucne.composeregistroprioridadesap2.presentation.prioridad

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composeregistroprioridadesap2.R
import edu.ucne.composeregistroprioridadesap2.data.local.entities.PrioridadEntity

@Composable
fun PrioridadListScreen(
    viewModel: PrioridadViewModel = hiltViewModel(),
    onPrioridadClick: (Int) -> Unit,
    onAddPrioridad: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PrioridadListBodyScreen(
        uiState = uiState,
        onPrioridadClick = onPrioridadClick,
        onAddPrioridad = onAddPrioridad,
        onDeletePrioridad = { prioridadId ->
            viewModel.onEvent(
                PrioridadUiEvent.PrioridadIdChanged(prioridadId)
            )
            viewModel.onEvent(
                PrioridadUiEvent.Delete
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioridadListBodyScreen(
    uiState: PrioridadUiState,
    onPrioridadClick: (Int) -> Unit,
    onAddPrioridad: () -> Unit,
    onDeletePrioridad: (Int) -> Unit
){
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
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddPrioridad
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Agregar nueva prioridad"
                )
            }
        }
    ){
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
                if(uiState.prioridades.isEmpty()){
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

                    item{
                        HorizontalDivider()

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier
                        ){
                            Text(
                                text = "Id",
                                modifier = Modifier.weight(1f),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Descripción",
                                modifier = Modifier.weight(2f),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "     Días de Compromiso",
                                modifier = Modifier.weight(2f),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "",
                                modifier = Modifier.weight(0.3f),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    items(uiState.prioridades){
                        PrioridadRow(
                            it = it,
                            onPrioridadClick = onPrioridadClick,
                            onDeletePrioridad = onDeletePrioridad
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PrioridadRow(
    it: PrioridadEntity,
    onPrioridadClick: (Int) -> Unit,
    onDeletePrioridad: (Int) -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(
                onClick = {
                    onPrioridadClick(it.prioridadId ?: 0)
                }
            )
    ){
        Text(
            text = it.prioridadId.toString(),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = it.descripcion,
            modifier = Modifier.weight(2f)
        )
        Text(
            text = it.diasCompromiso.toString(),
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = {
                onDeletePrioridad(it.prioridadId ?: 0)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Eliminar prioridad"
            )
        }
    }
    HorizontalDivider()
}