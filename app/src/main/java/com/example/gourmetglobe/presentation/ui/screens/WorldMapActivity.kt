package com.example.gourmetglobe.presentation.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

// A voir si cette classe est utile ???? je crois que non ... on peut juste garder le WorldMapScreen.kt
class WorldMapActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorldMapScreen()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WorldMapScreen()
}