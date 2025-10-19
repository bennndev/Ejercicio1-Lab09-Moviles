package com.example.ejercicio1_lab09.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ejercicio1_lab09.data.model.RecipeModel
import com.example.ejercicio1_lab09.ui.components.RecipeCard
import com.example.ejercicio1_lab09.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: RecipeViewModel) {
    val recipes by viewModel.recipes.collectAsState()
    var query by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) { viewModel.loadRecipes() }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Recetas") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add") }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar receta")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(8.dp)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Buscar") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { scope.launch { viewModel.search(query) } }, modifier = Modifier.fillMaxWidth()) {
                Text("Buscar")
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(recipes) { r: RecipeModel ->
                    RecipeCard(recipe = r) { navController.navigate("detail/${r.id}") }
                }
            }
        }
    }
}