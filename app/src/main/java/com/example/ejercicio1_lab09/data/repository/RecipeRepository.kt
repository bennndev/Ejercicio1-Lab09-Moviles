package com.example.ejercicio1_lab09.data.repository

import com.example.ejercicio1_lab09.data.model.RecipeModel
import com.example.ejercicio1_lab09.data.network.RecipeApiService
import com.example.ejercicio1_lab09.data.network.RetrofitInstance

class RecipeRepository(
    private val api: RecipeApiService = RetrofitInstance.api
    ) {

    suspend fun getRecipes(limit: Int = 20, skip: Int = 0) =
        api.getRecipes(limit, skip)

    suspend fun getRecipeById(id: Int) = api.getRecipeById(id)

    suspend fun searchRecipes(q: String) = api.searchRecipes(q)

    suspend fun getTags(): List<String> = api.getAllTags()

    suspend fun getByTag(tag: String) = api.getRecipesByTag(tag)

    suspend fun getByMeal(meal: String) = api.getRecipesByMeal(meal)

    suspend fun addRecipe(recipe: RecipeModel) = api.addRecipe(recipe)

    suspend fun updateRecipe(id: Int, recipe: RecipeModel) = api.updateRecipe(id, recipe)

    suspend fun deleteRecipe(id: Int) = api.deleteRecipe(id)
}