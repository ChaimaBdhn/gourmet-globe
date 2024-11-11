package com.example.gourmetglobe
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gourmetglobe.presentation.ui.screen.RecipeScreen
import com.example.gourmetglobe.presentation.viewmodel.RecipeViewModel
import com.example.gourmetglobe.ui.theme.GourmetGlobeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GourmetGlobeTheme {
                Surface(modifier = androidx.compose.ui.Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    RecipeScreen()
                }
            }
        }
    }
}