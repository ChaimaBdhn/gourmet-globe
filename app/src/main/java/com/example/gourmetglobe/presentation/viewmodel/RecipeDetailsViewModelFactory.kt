package com.example.gourmetglobe.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gourmetglobe.domain.repository.repository.RecipeRepository

class RecipeDetailsViewModelFactory(
    private val repository: RecipeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecipeDetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}