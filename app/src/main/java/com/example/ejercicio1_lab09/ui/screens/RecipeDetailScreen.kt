package com.example.ejercicio1_lab09.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ejercicio1_lab09.viewmodel.RecipeViewModel

@Composable
fun RecipeDetailScreen(navController: NavHostController, viewModel: RecipeViewModel, id: Int) {
    val selected by viewModel.selected.collectAsState()

    LaunchedEffect(id) { viewModel.loadRecipe(id) }

    selected?.let { r ->
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(text = r.name)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Ingredientes:")
            r.ingredients.forEach { ing -> Text("- $ing") }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Instrucciones:")
            Text(text = r.instructions ?: "Sin instrucciones")
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = { /* editar o eliminar */ }) { Text("Acciones") }
        }
    } ?: run {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            Text("Cargando...")
        }
    }
}