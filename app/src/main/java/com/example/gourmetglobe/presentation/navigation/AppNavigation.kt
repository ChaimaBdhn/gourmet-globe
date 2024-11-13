package com.example.gourmetglobe.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gourmetglobe.presentation.ui.screens.*

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorites : Screen("favorites")
    object WorldMap : Screen("world_map")
    object Preferences : Screen("preferences")
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.Favorites.route) {
            FavoritesScreen(navController)
        }
        composable(Screen.WorldMap.route) {
            WorldMapScreen(navController)
        }
        composable(Screen.Preferences.route) {
            PreferencesScreen(navController)
        }
    }
}