package edu.ucne.composeregistroprioridadesap2

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.Room
import edu.ucne.composeregistroprioridadesap2.data.local.database.PrioridadDb
import edu.ucne.composeregistroprioridadesap2.data.local.entities.PrioridadEntity
import edu.ucne.composeregistroprioridadesap2.ui.theme.RegistroPrioridadesAp2Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var prioridadDb: PrioridadDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        prioridadDb = Room.databaseBuilder(
            applicationContext,
            PrioridadDb::class.java,
            "Prioridad.db"
        ).fallbackToDestructiveMigration()
            .build()

        setContent {
            RegistroPrioridadesAp2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ){
                        PrioridadScreen()
                    }
                }
            }
        }
    }

    @Composable
    fun PrioridadScreen(){
        var descripcion by remember { mutableStateOf("") }
        var diasCompromiso by remember { mutableStateOf("") }
        var errorMessage: String? by remember { mutableStateOf(null) }
        val scope = rememberCoroutineScope()

        Scaffold { innerPadding ->
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
                                        if(findPrioridad(descripcion) != null)
                                        {
                                            errorMessage = "Ya existe esta descripción"
                                        }

                                        else{
                                            savePrioridad(
                                                PrioridadEntity(
                                                    descripcion = descripcion,
                                                    diasCompromiso = diasCompromiso.toInt()
                                                )
                                            )
                                            descripcion = ""
                                            diasCompromiso = ""
                                        }
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
                val lifecycleOwner = LocalLifecycleOwner.current
                val prioridadList by prioridadDb.prioridadDao().getAll()
                    .collectAsStateWithLifecycle(
                        lifecycleOwner = lifecycleOwner,
                        minActiveState = Lifecycle.State.STARTED,
                        initialValue = emptyList()
                    )
                Spacer(modifier = Modifier.height(20.dp))
                PrioridadListScreen(scope,prioridadList)
            }
        }
    }

    @Composable
    fun PrioridadListScreen(
        scope: CoroutineScope,
        prioridadList: List<PrioridadEntity>
    ){
        Column(
            modifier = Modifier.fillMaxWidth()
        ){
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Lista de Prioridades",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(
                    alignment = Alignment.CenterHorizontally
                )
            )
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


            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ){
                items(prioridadList){
                    PrioridadRow(
                        scope = scope,
                        it = it
                    )
                }
            }
        }
    }

    @Composable
    fun PrioridadRow(
        scope: CoroutineScope,
        it: PrioridadEntity
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically
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

    private suspend fun savePrioridad(prioridad: PrioridadEntity){
        prioridadDb.prioridadDao().save(prioridad)
    }

    private suspend fun findPrioridad(descripcion: String): PrioridadEntity? {
        return prioridadDb.prioridadDao().findDescripcion(descripcion)
    }

    private suspend fun deletePrioridad(prioridad: PrioridadEntity){
        prioridadDb.prioridadDao().delete(prioridad)
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun GreetingPreview() {
        RegistroPrioridadesAp2Theme {
           PrioridadScreen()
        }
    }
}