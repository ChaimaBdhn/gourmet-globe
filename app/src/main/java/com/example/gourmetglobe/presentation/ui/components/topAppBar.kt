package com.example.gourmetglobe.presentation.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.navigation.compose.rememberNavController

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
        }
    )
}

@Preview
@Composable
fun TopBarPreview() {
    // Ajouter un exemple d'usage dans une vue d'aperçu
    TopBar(navController = rememberNavController(), title = "My Screen")
}
