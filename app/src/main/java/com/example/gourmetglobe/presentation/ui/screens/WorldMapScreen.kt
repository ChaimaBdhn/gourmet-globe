package com.example.gourmetglobe.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.gourmetglobe.R


@Composable
fun WorldMapScreen() {

    val mapImage: Painter = painterResource(id = R.drawable.world_map)

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = mapImage,
            contentDescription = "World Map",
            modifier = Modifier.fillMaxSize()
        )


        Box(
            modifier = Modifier
                .clickable { openRegion("Europe") }
                .align(Alignment.TopStart)
                .then(Modifier.size(200.dp))
        )

        Box(
            modifier = Modifier
                .clickable { openRegion("Asia") }
                .align(Alignment.Center)
                .then(Modifier.size(250.dp))
        )

        Box(
            modifier = Modifier
                .clickable { openRegion("Africa") }
                .align(Alignment.BottomEnd)
                .then(Modifier.size(200.dp))
        )

        Button(onClick = { openRegion("World") }, modifier = Modifier.align(Alignment.BottomCenter)) {
            Text(text = "Explore the World")
        }
    }
}




fun openRegion(region: String) {
    // afficher la lite des recettes
    println("Region clicked: $region")
}