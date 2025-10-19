package com.example.ejercicio1_lab09.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ejercicio1_lab09.ui.screens.*
import androidx.compose.runtime.collectAsState

@Composable
fun RecipeNavGraph(viewModel: com.example.ejercicio1_lab09.viewmodel.RecipeViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController, viewModel) }
        composable("add") { AddEditRecipeScreen(navController, viewModel) }
        composable(
            "edit/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            val recipe = viewModel.recipes.collectAsState().value.find { it.id == id }
            AddEditRecipeScreen(navController, viewModel, editing = recipe)
        }
        composable(
            "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            RecipeDetailScreen(navController, viewModel, id)
        }
    }
}