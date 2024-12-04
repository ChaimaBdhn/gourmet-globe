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

@Composable
fun RecipeCard(
    recipe: RecipeEntity,
    onHeartClick: (RecipeEntity) -> Unit, // Callback pour gérer les favoris
    isFavorite: Boolean, // Détermine si cette recette est déjà en favoris
    onClick: ()-> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(){onClick()},
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column {
            // Image avec le bouton favori
            Box  {
                // Image principale
                Image(
                    painter = rememberAsyncImagePainter(model = recipe.image),
                    contentDescription = recipe.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
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
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = recipe.title ?: "Titre indisponible",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
//                Text(
//                    text = recipe.description ?: "Description indisponible",
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = MaterialTheme.colorScheme.onSurfaceVariant
//                )
            }
        }
    }
}