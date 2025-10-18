package com.example.ejercicio1_lab09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.example.ejercicio1_lab09.data.network.RetrofitInstance
import com.example.ejercicio1_lab09.data.repository.RecipeRepository
import com.example.ejercicio1_lab09.ui.screens.*
import com.example.ejercicio1_lab09.viewmodel.RecipeViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {

    // Factory to create ViewModel with repo
    private val vmFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val repo = RecipeRepository(RetrofitInstance.api)
            @Suppress("UNCHECKED_CAST")
            return RecipeViewModel(repo) as T
        }
    }

    private val viewModel: RecipeViewModel by viewModels { vmFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApp(viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(viewModel: RecipeViewModel) {
    val navController = rememberNavController()

    Scaffold(topBar = { TopAppBar(title = { Text("Recipes App") }) }) { padding ->
        NavHost(navController = navController, startDestination = "home", modifier = Modifier.padding(padding)) {
            composable("home") { HomeScreen(navController, viewModel) }
            composable("detail/{id}", arguments = listOf(navArgument("id") { type = NavType.IntType })) {
                val id = it.arguments?.getInt("id") ?: 0
                RecipeDetailScreen(navController, viewModel, id)
            }
            composable("tags") { TagsScreen(navController, viewModel) }
            composable("add") { AddEditRecipeScreen(viewModel = viewModel, onDone = { navController.navigateUp() }) }
        }
    }
}
