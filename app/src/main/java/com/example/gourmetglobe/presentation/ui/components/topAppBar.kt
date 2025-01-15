package com.example.gourmetglobe.presentation.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.navigation.compose.rememberNavController

/**
 * Affiche la barre supérieure avec un titre et un bouton retour 
 * @param navController contrôleur de navigation 
 * @param title texte affiché dans la barre 
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController, title: String) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            // Si on n'est pas à la page d'accueil, on affiche le bouton de retour
            if (navController.previousBackStackEntry != null) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.onPrimary, // Couleur de l'arrière-plan
            navigationIconContentColor = MaterialTheme.colorScheme.secondary, // Couleur de l'icône de navigation
            titleContentColor = MaterialTheme.colorScheme.secondary // Couleur du titre
        )
    )
}
