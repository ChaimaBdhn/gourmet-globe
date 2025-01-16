package com.example.gourmetglobe.presentation.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.example.gourmetglobe.data.local.entities.RecipeEntity


/**
 * Fonction composable qui permet de gérer la navigation
 * Elle affiche plusieurs élements de navigations pour les différentes sections de l'app (Accueil, Recherche, Favoris)
 * @param navController Le [NavController] utilisé pour gérer la navigation entre les différentes pages de l'application
 */
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
    NavigationBar (
        containerColor = MaterialTheme.colorScheme.onPrimary, // Couleur d'arrière-plan de la barre
        contentColor = MaterialTheme.colorScheme.secondary
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.label, tint=MaterialTheme.colorScheme.secondary) },
                label = { Text(item.label, color=MaterialTheme.colorScheme.onSecondary) },
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

/**
 * Data class représentant chaque élément de navigation dans la barre de navigation.
 * Contient une route, un label et une icône associés à un élément.
 *
 * @param route La route à naviguer lorsqu'on clique sur cet élément.
 * @param label Le label affiché pour cet élément dans la barre de navigation.
 * @param icon L'icône associée à cet élément.
 */

data class NavigationItem(
    val route: String,  // La route à naviguer
    val label: String,  // Le label de l'élément (nom affiché)
    val icon: ImageVector // L'icône associée à l'élément
)

