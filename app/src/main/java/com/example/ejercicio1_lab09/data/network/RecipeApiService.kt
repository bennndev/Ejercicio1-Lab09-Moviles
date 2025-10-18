package com.example.ejercicio1_lab09.data.network

import com.example.ejercicio1_lab09.data.model.RecipeModel
import com.example.ejercicio1_lab09.data.model.RecipesResponse
import com.example.ejercicio1_lab09.data.model.TagsResponse
import retrofit2.http.*

interface RecipeApiService {

    @GET("recipes")
    suspend fun getRecipes(
        @Query("limit") limit: Int = 20,
        @Query("skip") skip: Int = 0
    ): RecipesResponse

    @GET("recipes/{id}")
    suspend fun getRecipeById(@Path("id") id: Int): RecipeModel

    @GET("recipes/search")
    suspend fun searchRecipes(@Query("q") query: String): RecipesResponse

    @GET("recipes/tags")
    suspend fun getAllTags(): TagsResponse

    @GET("recipes/tag/{tag}")
    suspend fun getRecipesByTag(@Path("tag") tag: String): RecipesResponse

    @GET("recipes/meal-type/{meal}")
    suspend fun getRecipesByMeal(@Path("meal") meal: String): RecipesResponse

    @POST("recipes/add")
    suspend fun addRecipe(@Body recipe: RecipeModel): RecipeModel

    @PUT("recipes/{id}")
    suspend fun updateRecipe(@Path("id") id: Int, @Body recipe: RecipeModel): RecipeModel

    @DELETE("recipes/{id}")
    suspend fun deleteRecipe(@Path("id") id: Int): RecipeModel
}

