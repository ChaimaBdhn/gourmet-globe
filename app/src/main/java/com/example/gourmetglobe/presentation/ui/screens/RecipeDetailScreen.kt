package com.example.gourmetglobe.presentation.ui.screens

import android.content.res.Configuration
import android.text.Spanned
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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


/**
 * Fonction composable qui affiche l'écran pour le détails d'une recette
 * Elle affiche les inforamtions reçu par et stocké dans la base local (reçu par l'api)
 * @param recipeId l'identifiant unique de la recette
 * @param recipeRepository Le [RecipeRepository] contient les données de la recette.
 * @param navController Le [NavController] qui gère la navigation gère les transitions entre les différents écrans
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    recipeId: Int,
    recipeRepository: RecipeRepository,
    navController: NavController
) {
    val viewModel: RecipeDetailsViewModel = viewModel(factory = RecipeDetailsViewModelFactory(recipeRepository))
    val recipeDetailsState by viewModel.recipeDetailsState.collectAsState()
    val configuration = LocalConfiguration.current
    // Déterminer l'orientation actuelle
    val isLandscape = remember(configuration.orientation) {
        configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    LaunchedEffect(recipeId) {
        viewModel.fetchRecipeDetails(recipeId)
    }

    when (val state = recipeDetailsState) {
        is RecipeDetailsState.Success -> {
            Scaffold(
                topBar = {
                        TopAppBar(
                        title = { Text(text = "Details",color=MaterialTheme.colorScheme.secondary) },
                        navigationIcon = {
                                IconButton(onClick = { navController.popBackStack() }) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back",
                                        tint = MaterialTheme.colorScheme.secondary)
                                }
                        })
                         },
                content = { paddingValues ->
                RecipeDetailsContentWithAnimatedImage(recipe = state.recipe,paddingValues=paddingValues)
                }
            )
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

/**
 * Affiche les détails de la recette avec un effet parallaxe sur l'image
 * Elle affiche les inforamtions reçu par et stocké dans la base local (reçu par l'api).
 * @param recipe l'entité contenant les données de la recette
 * @param paddingValues le padding pour indiquer l'espacement à appliquer.
 */
@Composable
fun RecipeDetailsContentWithAnimatedImage(
    recipe: RecipeEntity,
    paddingValues: PaddingValues,
) {
    // ScrollState pour détecter le défilement
    val scrollState = rememberScrollState()


    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(paddingValues)
    ) {
        // Image en arrière-plan
        Box(
            modifier = Modifier
                .width(400.dp)
                .height(260.dp)
                .background(
                    Color.White,
                    shape = RoundedCornerShape(16.dp)
                ) // Bord arrondi pour la Box
                .clip(RoundedCornerShape(16.dp))
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        ) {

            // L'image en arrière-plan
            if (!recipe.image.isNullOrBlank()) {
                Image(
                    painter = rememberAsyncImagePainter(model = recipe.image),
                    contentDescription = recipe.title,
                    modifier = Modifier
                        .width(400.dp)
                        .height(260.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
            }else{
                Text(
                    text = "No image available",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }


        }

        Box (modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .offset {
                IntOffset(
                    0,
                    -scrollState.value / 2
                )
            } // Effet parallax pour le texte et la box
            .background(
                Color.White,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
            )
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
                .offset {
                    IntOffset(
                        0,
                        -scrollState.value / 2
                    )
                } // Effet parallax pour le texte et la box
                .background(
                    Color.White,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
                )
                .padding(20.dp)
        ) {
            Column (
                modifier = Modifier.fillMaxWidth()
            ) {

                Spacer(modifier = Modifier.height(8.dp))

                // Description enrichie
                Text(
                    text = "Summary",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold  // Applique le style gras
                    ),
                    color = MaterialTheme.colorScheme.primary
                )
                HtmlText(
                    html = recipe.summary ?: "No information available",
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
                DetailSection(title = "Preparation time (in minutes)", value = "${recipe.readyInMinutes}")
                DetailSection(title = "Dish type(s)", value = recipe.dishTypes.joinToString(", ") ?: "Inconnu")
                DetailSection(title = "Diets", value = recipe.diets.joinToString(", ") ?: "Aucun")
            }

        }
    }
}



/**
 * Affiche une liste de données sous forme de puces
 * @param recipe le titre de la section
 * @param items la liste des élements à afficher.
 */

// Composant pour afficher une liste à puces
@Composable
fun BulletListSection(title: String, items: List<String>) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold  // Applique le style gras
            ),
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


/**
 * Affiche un détail sous fome simple (titre + valeur) (pour le temps de préparations)
 * @param titre le titre du détail (temps de prépration)
 * @param value valeur associée (30 minutes)
 */
// Composant pour afficher un détail simple (titre + valeur)
@Composable
fun DetailSection(title: String, value: String) {
    if(value.isBlank()){
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold  // Applique le style gras
                ),
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
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold  // Applique le style gras
                ),color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

/**
 * Composant pour intergréter le html.
 * @param html le contenu html à afficher
 * @param modifier permet de personnalisé l'apparence et le comportement du composant
 */
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


/**
 * Affiche un message d'erreur
 * @param message le message d'erreur
 * @param navController pour revenir à la page précedente..
 */
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

