package com.example.ejercicio1_lab09.data.model

import com.google.gson.annotations.SerializedName

data class RecipeModel(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("ingredients")
    val ingredients: List<String> = emptyList(),

    @SerializedName("instructions")
    val instructions: String? = null,

    @SerializedName("image")
    val image: String? = null,

    @SerializedName("tags")
    val tags: List<String> = emptyList(),

    @SerializedName("mealType")
    val mealType: String? = null
)
