package edu.ucne.composeregistroprioridadesap2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.composeregistroprioridadesap2.presentation.navigation.RegistroPrioridadesAp2NavHost
import edu.ucne.composeregistroprioridadesap2.ui.theme.RegistroPrioridadesAp2Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroPrioridadesAp2Theme {
                val navController = rememberNavController()
                RegistroPrioridadesAp2NavHost(
                    navHostController = navController
                )
            }
        }
    }
}