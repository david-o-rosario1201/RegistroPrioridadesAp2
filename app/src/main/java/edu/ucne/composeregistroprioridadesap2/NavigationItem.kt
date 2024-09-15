package edu.ucne.composeregistroprioridadesap2

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: Route,
    val badgeCount: Int? = null
)
