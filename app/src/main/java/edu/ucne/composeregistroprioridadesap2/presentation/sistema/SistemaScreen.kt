@file:OptIn(ExperimentalMaterial3Api::class)

package edu.ucne.composeregistroprioridadesap2.presentation.sistema

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SistemaScreen(
    viewModel: SistemaViewModel = hiltViewModel(),
    sistemaId: Int,
    goSistemas: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SistemaBodyScreen(
        sistemaId = sistemaId,
        uiState = uiState,
        onEvent = viewModel::onEvent,
        goSistemas = goSistemas
    )
}

@Composable
fun SistemaBodyScreen(
    sistemaId: Int,
    uiState: SistemaUiState,
    onEvent: (SistemaUiEvent) -> Unit,
    goSistemas: () -> Unit
) {
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    LaunchedEffect(key1 = true, key2 = uiState.success) {
        onEvent(SistemaUiEvent.SelectedSistema(sistemaId))

        if(uiState.success)
            goSistemas()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if(sistemaId == 0) "Crear Sistema" else "Modificar Sistema",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = goSistemas
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Ir a la Lista de Sistemas"
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
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        OutlinedTextField(
                            label = {
                                Text("Nombre")
                            },
                            value = uiState.nombre ?: "",
                            onValueChange = {
                                onEvent(SistemaUiEvent.NombreChanged(it))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .focusRequester(focusRequester)
                                .onGloballyPositioned { coordiantes ->
                                    textFieldSize = coordiantes.size.toSize()
                                },
                            shape = RoundedCornerShape(10.dp),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                    onEvent(SistemaUiEvent.Save)
                                }
                            )
                        )
                        uiState.errorNombre?.let {
                            Text(
                                text = it,
                                color = Color.Red
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        OutlinedButton(
                            onClick = {
                                focusRequester.requestFocus()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Create,
                                contentDescription = "Empezar a Llenar"
                            )
                            Text("Empezar a Llenar")
                        }

                        Spacer(modifier = Modifier.width(20.dp))

                        OutlinedButton(
                            onClick = {
                                onEvent(SistemaUiEvent.Save)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Guardar Sistema"
                            )
                            Text(
                                text = if(sistemaId == 0) "Crear Sistema" else "Modificar Sistema"
                            )
                        }
                    }
                }
            }
        }
    }
}