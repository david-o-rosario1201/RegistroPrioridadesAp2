@file:OptIn(ExperimentalMaterial3Api::class)

package edu.ucne.composeregistroprioridadesap2.presentation.cliente

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
import edu.ucne.composeregistroprioridadesap2.data.remote.dto.ClienteDto
import edu.ucne.composeregistroprioridadesap2.ui.theme.RegistroPrioridadesAp2Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ClienteListScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    viewModel: ClienteViewModel = hiltViewModel(),
    onClickCliente: (Int) -> Unit,
    onAddCliente: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ClienteListBodyScreen(
        drawerState = drawerState,
        scope = scope,
        uiState = uiState,
        onClickCliente = onClickCliente,
        onAddCliente = onAddCliente
    )
}

@Composable
fun ClienteListBodyScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    uiState: ClienteUiState,
    onClickCliente: (Int) -> Unit,
    onAddCliente: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Lista de Clientes",
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
                onClick = onAddCliente
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Agregar nuevo Cliente"
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
                if(uiState.clientes.isEmpty()){
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
                    items(uiState.clientes){
                        ClienteRow(
                            it = it,
                            onClickCliente = onClickCliente
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ClienteRow(
    it: ClienteDto,
    onClickCliente: (Int) -> Unit
) {
    Card(
        onClick = {
            onClickCliente(it.clienteId ?: 0)
        },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFB0BEC5)
        ),
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .heightIn(min = 100.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.cliente_image),
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
                Text(
                    text = "Nombre: ${it.nombre}",
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "RNC: ${it.rnc}",
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "Email: ${it.email}",
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "Dirección: ${it.direccion}",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ClienteListScreenPreview() {
    RegistroPrioridadesAp2Theme {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        ClienteListScreen(
            drawerState = drawerState,
            scope = scope,
            onClickCliente = {},
            onAddCliente = {}
        )
    }
}