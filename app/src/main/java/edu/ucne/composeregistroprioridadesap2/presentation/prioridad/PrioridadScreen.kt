@file:OptIn(ExperimentalMaterial3Api::class)

package edu.ucne.composeregistroprioridadesap2.presentation.prioridad

import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PrioridadScreen(
    scope: CoroutineScope,
    goPrioridadList: () -> Unit
){
    var descripcion by remember { mutableStateOf("") }
    var diasCompromiso by remember { mutableStateOf("") }
    var errorMessage: String? by remember { mutableStateOf(null) }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Registro Prioridades")
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
                .padding(8.dp)
        ){
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        text = "Prioridades",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold
                    )
                    OutlinedTextField(
                        label = {
                            Text("Descripción")
                        },
                        value = descripcion,
                        onValueChange = {
                            descripcion = it
                            errorMessage = null
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        label = {
                            Text("Días de compromiso")
                        },
                        value = diasCompromiso,
                        onValueChange = {
                            diasCompromiso = it
                            errorMessage = null
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    errorMessage?.let {
                        Text(
                            text = it,
                            color = Color.Red
                        )
                    }

                    OutlinedButton(
                        onClick = {
                            if(descripcion.isBlank())
                                errorMessage = "La descripción no puede estar vacía"
                            else if(diasCompromiso.isBlank())
                                errorMessage = "Días de compromiso no puede ir vacío"
                            else if(diasCompromiso.toInt() <= 0 || diasCompromiso.toInt() > 30)
                                errorMessage = "Días de compromiso no puede ser menor a 1 o mayor a 30"

                            else{
                                scope.launch {
//                                    if(findPrioridad(descripcion) != null)
//                                    {
//                                        errorMessage = "Ya existe esta descripción"
//                                    }
//
//                                    else{
//                                        savePrioridad(
//                                            PrioridadEntity(
//                                                descripcion = descripcion,
//                                                diasCompromiso = diasCompromiso.toInt()
//                                            )
//                                        )
//                                        descripcion = ""
//                                        diasCompromiso = ""
//
//                                        goPrioridadList()
//                                    }
                                }
                            }
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
//                val lifecycleOwner = LocalLifecycleOwner.current
//                val prioridadList by prioridadDb.prioridadDao().getAll()
//                    .collectAsStateWithLifecycle(
//                        lifecycleOwner = lifecycleOwner,
//                        minActiveState = Lifecycle.State.STARTED,
//                        initialValue = emptyList()
//                    )
//                Spacer(modifier = Modifier.height(20.dp))
            //PrioridadListScreen(scope,prioridadList)
        }
    }
}