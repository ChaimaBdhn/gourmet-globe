package com.example.gourmetglobe.presentation.ui.screens

import android.text.Spanned
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
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
            RecipeDetailsContentWithAnimatedImage(recipe = state.recipe, navController = navController)
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
fun RecipeDetailsContentWithAnimatedImage(
    recipe: RecipeEntity,
    navController: NavController,
    maxImageHeight: Dp = 300.dp,
    minImageHeight: Dp = 100.dp
) {
    // ScrollState pour détecter le défilement
    val scrollState = rememberScrollState()
    val image: Painter = rememberAsyncImagePainter(model = recipe.image)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        // Image en arrière-plan
        Box(
            modifier = Modifier
                .width(400.dp)
                .height(260.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp)) // Bord arrondi pour la Box
                .clip(RoundedCornerShape(16.dp))
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            // L'image en arrière-plan
            Image(
                painter = rememberAsyncImagePainter(model = recipe.image),
                contentDescription = recipe.title,
                modifier = Modifier
                    .width(400.dp)
                    .height(260.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
            )


        }

        Box (modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .offset { IntOffset(0, -scrollState.value / 2) } // Effet parallax pour le texte et la box
            .background(Color.White, shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
            .padding(20.dp))


        { // Titre de la recette
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.headlineSmall .copy(
                    fontWeight = FontWeight.Bold  // Applique le style gras
                ),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Box avec le texte qui défile
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp)
                .offset { IntOffset(0, -scrollState.value / 2) } // Effet parallax pour le texte et la box
                .background(Color.White, shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
                .padding(20.dp)
        ) {
            Column (
                modifier = Modifier.fillMaxWidth()
            ) {
                
                Spacer(modifier = Modifier.height(8.dp))

                // Description enrichie
                HtmlText(
                    html = recipe.summary ?: "Aucune description disponible",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            // Informations supplémentaires

                BulletListSection(
                    title = "Intolerances",
                    items = recipe.intolerances
                )
                BulletListSection(
                    title = "Ingredients",
                    items = recipe.ingredients
                )
                BulletListSection(
                    title = "Equipments",
                    items = recipe.equipment
                )
                DetailSection(title = "Preparation time", value = "${recipe.readyInMinutes} minutes")
                DetailSection(title = "Dish type(s)", value = recipe.dishTypes.joinToString(", ") ?: "Inconnu")
                DetailSection(title = "Diets", value = recipe.diets.joinToString(", ") ?: "Aucun")
            }

        }
//        // Bouton de retour
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(
//            onClick = { navController.popBackStack() },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp)
//        ) {
//            Text("Retour")
//        }
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
                text = "No information available",
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
    if(value.isBlank()){
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "No information available",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
    else{
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
}

// Composant pour interpréter et afficher du texte HTML
@Composable
fun HtmlText(html: String, modifier: Modifier = Modifier) {
    val parsedHtml = remember(html) { parseHtmlToAnnotatedString(html) }
    Text(
        text = parsedHtml,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Justify,
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
                Text("Back")
            }
        }
    }
}


