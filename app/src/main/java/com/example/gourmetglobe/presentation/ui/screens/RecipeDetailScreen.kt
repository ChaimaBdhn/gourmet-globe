package com.example.gourmetglobe.presentation.ui.screens

import android.text.Spanned
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.gourmetglobe.data.local.entities.RecipeEntity
import com.example.gourmetglobe.domain.repository.repository.RecipeRepository
import com.example.gourmetglobe.presentation.ui.state.RecipeDetailsState
import com.example.gourmetglobe.presentation.viewmodel.RecipeDetailsViewModel
import com.example.gourmetglobe.presentation.viewmodel.RecipeDetailsViewModelFactory

@Composable
fun RecipeDetailScreen(
    recipeId: Int,
    recipeRepository: RecipeRepository,
    navController: NavController
) {
    val viewModel: RecipeDetailsViewModel = viewModel(factory = RecipeDetailsViewModelFactory(recipeRepository))
    val recipeDetailsState by viewModel.recipeDetailsState.collectAsState()

    LaunchedEffect(recipeId) {
        viewModel.fetchRecipeDetails(recipeId)
    }

    when (val state = recipeDetailsState) {
        is RecipeDetailsState.Success -> {
            RecipeDetailsContent(recipe = state.recipe, navController = navController)
        }

        is RecipeDetailsState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is RecipeDetailsState.Error -> {
            ErrorContent(message = state.message, navController = navController)
        }
    }
}

@Composable
fun RecipeDetailsContent(recipe: RecipeEntity, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Affichage de l'image principale
        Image(
            painter = rememberAsyncImagePainter(model = recipe.image),
            contentDescription = recipe.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        // Titre de la recette
        Text(
            text = recipe.title,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            color = MaterialTheme.colorScheme.primary
        )

        // Description enrichie
        HtmlText(
            html = recipe.summary ?: "Aucune description disponible",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        // Informations supplémentaires
        Column(modifier = Modifier.padding(16.dp)) {
            BulletListSection(
                title = "Allergènes",
                items = recipe.intolerances
            )
            BulletListSection(
                title = "Ingrédients",
                items = recipe.ingredients
            )
            BulletListSection(
                title = "Ustensiles",
                items = recipe.equipment
            )
            DetailSection(title = "Temps de préparation", value = "${recipe.readyInMinutes} minutes")
            DetailSection(title = "Type de plat", value = recipe.dishTypes.joinToString(", ") ?: "Inconnu")
            DetailSection(title = "Régimes alimentaires", value = recipe.diets.joinToString(", ") ?: "Aucun")
        }

        // Bouton de retour
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text("Retour")
        }
    }
}

// Composant pour afficher une liste à puces
@Composable
fun BulletListSection(title: String, items: List<String>) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        // Vérification et affichage de la liste ou du message de fallback
        if (items.isEmpty()) {
            Text(
                text = "Aucune information disponible",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        } else {
            items.forEach { item ->
                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    Text(
                        text = "•",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(end = 8.dp),
                        color = Color.Gray
                    )
                    Text(
                        text = item,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

// Composant pour afficher un détail simple (titre + valeur)
@Composable
fun DetailSection(title: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

// Composant pour interpréter et afficher du texte HTML
@Composable
fun HtmlText(html: String, modifier: Modifier = Modifier) {
    val parsedHtml = remember(html) { parseHtmlToAnnotatedString(html) }
    Text(
        text = parsedHtml,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Start,
        modifier = modifier
    )
}

// Fonction pour convertir le HTML en AnnotatedString
fun parseHtmlToAnnotatedString(html: String): AnnotatedString {
    val spanned: Spanned = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
    return AnnotatedString.Builder().apply {
        append(spanned.toString())
    }.toAnnotatedString()
}

@Composable
fun ErrorContent(message: String, navController: NavController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(message, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.popBackStack() }) {
                Text("Retour")
            }
        }
    }
}
