package com.example.gourmetglobe.presentation.ui.screen


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
import coil.compose.rememberImagePainter
import com.example.gourmetglobe.R
import com.example.gourmetglobe.data.local.entities.RecipeEntity
import com.example.gourmetglobe.data.model.Recipe

@Composable
fun RecipeCard(
    recipe: RecipeEntity,
    onHeartClick: (RecipeEntity) -> Unit, // Callback pour gérer les favoris
    isFavorite: Boolean // Détermine si cette recette est déjà en favoris
    ){

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )  {
            Box {
                Image(
                    painter = rememberImagePainter(data = recipe.image),
                    contentDescription = recipe.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                // Bouton cœur
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopEnd)
                        .size(40.dp)
                        .background(Color.White, CircleShape)
                        .clickable { onHeartClick(recipe) },
                    contentAlignment = Alignment.Center
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
        }

    }
}
