package com.example.gourmetglobe.presentation.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gourmetglobe.domain.repository.repository.RecipeRepository


/**
 * une factory pour créer une instance de [RecipeViewModel].
 *
 * @property repository Instance de [RecipeRepository] utilisée par le ViewModel.
 */
class RecipeViewModelFactory(
    private val repository: RecipeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecipeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
