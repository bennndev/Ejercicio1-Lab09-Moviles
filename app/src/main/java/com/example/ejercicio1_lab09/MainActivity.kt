package com.example.ejercicio1_lab09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ejercicio1_lab09.navigation.RecipeNavGraph
import com.example.ejercicio1_lab09.ui.theme.Ejercicio1Lab09Theme
import com.example.ejercicio1_lab09.viewmodel.RecipeViewModel
import com.example.ejercicio1_lab09.data.repository.RecipeRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = RecipeRepository()
        val viewModel = RecipeViewModel(repository)

        setContent {
            Ejercicio1Lab09Theme {
                RecipeNavGraph(viewModel = viewModel)
            }
        }
    }
}
