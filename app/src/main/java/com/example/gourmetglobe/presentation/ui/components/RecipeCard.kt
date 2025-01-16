package com.example.gourmetglobe.presentation.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.gourmetglobe.R
import com.example.gourmetglobe.data.local.entities.RecipeEntity


/**
 * Fonction composable qui représente une card de recette, utilisée pour afficher son image et son titre
 *
 * @param recipe l'objet [RecipeEntity] qui contient les informations sur la recette tels que l'image et le titre
 * @param onHeartClick Une fonction callback qui permet de réaliser une action quand on clique sur le coeur (ici sauvegarder en favori ou retirer des favoris d'une recette)
 * @param isFavorite Un boolean pour dire si la recette est en favori
 * @param onClick Une fonction callback qui permet de réaliser une action quand on clique sur la carte, nous affichons les détails de la recette
 * @param isLandscape Un boolean pour dire si le telephone est en format portrait ou paysage (pour modifier la taille des cards)
 */
@Composable
fun RecipeCard(
    recipe: RecipeEntity,
    onHeartClick: (RecipeEntity) -> Unit,
    isFavorite: Boolean,
    onClick: ()-> Unit,
    isLandscape: Boolean
) {


    // Définition de la taille de la Card en fonction de l'orientation
    val widthCard = if (isLandscape) 1000.dp else 450.dp // Plus large en mode paysage
    val heightCard = if (isLandscape) 300.dp else 270.dp

    Card(
        modifier = Modifier
            .width(widthCard)
            .height(heightCard)
            .padding(10.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White, // Couleur de fond de la Card
            contentColor = MaterialTheme.colorScheme.onSurface // Couleur du contenu (texte, icônes)
        )
    ) {
        // Image avec le bouton favori
        Box {

            // Image principale
            Image(
                painter = rememberAsyncImagePainter(model = recipe.image),
                contentDescription = recipe.title,
                modifier = Modifier
                    .width(400.dp) // Prend toute la largeur disponible
                    .height(200.dp) // Hauteur de l'image
                    .align(Alignment.Center) // Centrer l'image dans la Box
                    .padding(10.dp)
            )

            // Bouton favori
            IconButton(
                onClick = { onHeartClick(recipe) },
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopEnd)
                    .background(Color.White, CircleShape)
                    .size(36.dp)
            ) {
                Icon(
                    painter = painterResource(
                        id = if (isFavorite) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline
                    ),
                    contentDescription = "Toggle Favorite",
                    tint = if (isFavorite) Color.Red else Color.Gray
                )
            }

        }
        // Informations sur la recette
        Column(modifier = Modifier.padding(3.dp).align(Alignment.CenterHorizontally)) {
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

