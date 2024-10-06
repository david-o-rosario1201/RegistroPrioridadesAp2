@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package edu.ucne.composeregistroprioridadesap2.presentation.prioridad

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
import edu.ucne.composeregistroprioridadesap2.data.remote.dto.PrioridadDto
import edu.ucne.composeregistroprioridadesap2.ui.theme.RegistroPrioridadesAp2Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PrioridadListScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    viewModel: PrioridadViewModel = hiltViewModel(),
    onPrioridadClick: (Int) -> Unit,
    onAddPrioridad: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PrioridadListBodyScreen(
        drawerState = drawerState,
        scope = scope,
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
    drawerState: DrawerState,
    scope: CoroutineScope,
    uiState: PrioridadUiState,
    onPrioridadClick: (Int) -> Unit,
    onAddPrioridad: () -> Unit,
    onDeletePrioridad: (Int) -> Unit
){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Lista de Prioridades",
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
    it: PrioridadDto,
    onPrioridadClick: (Int) -> Unit,
    onDeletePrioridad: (Int) -> Unit
) {
    Card(
        onClick = {
            onPrioridadClick(it.prioridadId ?: 0)
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
                painter = painterResource(R.drawable.priority_image),
                contentDescription = "Image Prioridad",
                modifier = Modifier
                    .padding(vertical = 20.dp)
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
                        text = "Id: ${it.prioridadId}",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Descripción: ${it.descripcion}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = "Días de compromiso: ${it.diasCompromiso}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            IconButton(
                onClick = {
                    onDeletePrioridad(it.prioridadId ?: 0)
                },
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar Prioridad"
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PrioridadListScreenPreview(){
    RegistroPrioridadesAp2Theme {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        PrioridadListScreen(
            drawerState = drawerState,
            scope = scope,
            onPrioridadClick = {},
            onAddPrioridad = {}
        )
    }
}