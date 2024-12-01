package com.example.gourmetglobe.presentation.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

@Composable
fun BottomNavigationBar(navController: NavController) {
    // Liste des éléments de navigation
    val items = listOf(
        NavigationItem("home", "Home", Icons.Filled.Home),
        NavigationItem("search", "Search", Icons.Filled.Search),
        NavigationItem("favorites", "Favorites", Icons.Filled.Favorite)
    )

    // Récupérer l'état de la route actuelle
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    // Affichage de la barre de navigation avec les éléments
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    // Vérifie si l'élément de navigation cliqué est différent de la route actuelle
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            // On empêche la pile de navigation de s'empiler en boucle
                            popUpTo(item.route) { saveState = true }
                            launchSingleTop = true // Pour éviter plusieurs instances du même écran
                        }
                    }
                }
            )
        }
    }
}

// Data class représentant chaque élément de navigation
data class NavigationItem(
    val route: String,  // La route à naviguer
    val label: String,  // Le label de l'élément (nom affiché)
    val icon: ImageVector // L'icône associée à l'élément
)
