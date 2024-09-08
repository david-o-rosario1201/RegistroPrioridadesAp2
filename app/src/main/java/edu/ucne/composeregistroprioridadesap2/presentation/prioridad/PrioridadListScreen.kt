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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import edu.ucne.composeregistroprioridadesap2.R
import edu.ucne.composeregistroprioridadesap2.data.local.database.PrioridadDb
import edu.ucne.composeregistroprioridadesap2.data.local.entities.PrioridadEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PrioridadListScreen(
    prioridadDb: PrioridadDb,
    scope: CoroutineScope,
    prioridadList: List<PrioridadEntity>,
    onAddPrioridad: () -> Unit,
    onPrioridadClick: (PrioridadEntity) -> Unit
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
                if(prioridadList.isEmpty()){
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
                    items(prioridadList){
                        PrioridadRow(
                            prioridadDb = prioridadDb,
                            it = it,
                            scope = scope,
                            onPrioridadClick = onPrioridadClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PrioridadRow(
    prioridadDb: PrioridadDb,
    scope: CoroutineScope,
    it: PrioridadEntity,
    onPrioridadClick: (PrioridadEntity) -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(
                onClick = {
                    onPrioridadClick(it)
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
                scope.launch {
                    deletePrioridad(
                        prioridadDb = prioridadDb,
                        PrioridadEntity(
                            prioridadId = it.prioridadId,
                            descripcion = it.descripcion,
                            diasCompromiso = it.diasCompromiso
                        )
                    )
                }
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

private suspend fun deletePrioridad(
    prioridadDb: PrioridadDb,
    prioridad: PrioridadEntity
){
    prioridadDb.prioridadDao().delete(prioridad)
}