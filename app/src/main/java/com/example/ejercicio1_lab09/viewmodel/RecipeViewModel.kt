package com.example.ejercicio1_lab09.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejercicio1_lab09.data.model.RecipeModel
import com.example.ejercicio1_lab09.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeViewModel(private val repo: RecipeRepository) : ViewModel() {

    private val _recipes = MutableStateFlow<List<RecipeModel>>(emptyList())
    val recipes: StateFlow<List<RecipeModel>> = _recipes

    private val _selected = MutableStateFlow<RecipeModel?>(null)
    val selected: StateFlow<RecipeModel?> = _selected

    private val _tags = MutableStateFlow<List<String>>(emptyList())
    val tags: StateFlow<List<String>> = _tags

    fun loadRecipes(limit: Int = 20, skip: Int = 0) {
        viewModelScope.launch {
            try {
                val res = repo.getRecipes(limit, skip)
                _recipes.value = res.recipes
            } catch (e: Exception) {

            }
        }
    }

    fun loadRecipe(id: Int) {
        viewModelScope.launch {
            _selected.value = try { repo.getRecipeById(id) } catch (e: Exception) { null }
        }
    }

    fun search(q: String) {
        viewModelScope.launch {
            try {
                val res = repo.searchRecipes(q)
                _recipes.value = res.recipes
            } catch (e: Exception) { }
        }
    }

    fun loadTags() {
        viewModelScope.launch {
            try {
                val res = repo.getTags()
                _tags.value = res.tags
            } catch (e: Exception) { }
        }
    }

    fun loadByTag(tag: String) {
        viewModelScope.launch {
            try {
                val res = repo.getByTag(tag)
                _recipes.value = res.recipes
            } catch (e: Exception) {}
        }
    }

    suspend fun addRecipe(recipe: RecipeModel) = repo.addRecipe(recipe)
    suspend fun updateRecipe(id: Int, recipe: RecipeModel) = repo.updateRecipe(id, recipe)
    suspend fun deleteRecipe(id: Int) = repo.deleteRecipe(id)
}
