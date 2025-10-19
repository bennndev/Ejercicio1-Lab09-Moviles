package com.example.ejercicio1_lab09.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
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
    val tags by viewModel.tags.collectAsState()

    var query by remember { mutableStateOf("") }
    var searched by remember { mutableStateOf(false) }
    var selectedTag by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

    // 🔄 Cargar recetas y etiquetas al iniciar
    LaunchedEffect(Unit) {
        viewModel.loadRecipes()
        viewModel.loadTags()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Recetas") }) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add") },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar receta")
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            // 🔍 Campo de búsqueda
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Buscar recetas") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 🔘 Botón buscar
            Button(
                onClick = {
                    scope.launch {
                        searched = true
                        selectedTag = null // Limpiar selección de tag al buscar
                        if (query.isBlank()) viewModel.loadRecipes()
                        else viewModel.search(query)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Buscar")
            }

            // 🏷️ Filtro por etiquetas (chips)
            if (tags.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text("Filtrar por etiqueta:", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(tags) { tag ->
                        FilterChip(
                            selected = selectedTag == tag,
                            onClick = {
                                scope.launch {
                                    searched = true
                                    if (selectedTag == tag) {
                                        // Si se vuelve a tocar, se quita el filtro
                                        selectedTag = null
                                        viewModel.loadRecipes()
                                    } else {
                                        // Filtrar por tag
                                        selectedTag = tag
                                        query = ""
                                        viewModel.loadByTag(tag)
                                    }
                                }
                            },
                            label = { Text(tag) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 📜 Lista de recetas o mensajes de estado
            when {
                recipes.isEmpty() && searched -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No se encontraron resultados.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                recipes.isEmpty() && !searched -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Cargando recetas...",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(recipes) { recipe: RecipeModel ->
                            RecipeCard(recipe = recipe) {
                                navController.navigate("detail/${recipe.id}")
                            }
                        }
                    }
                }
            }
        }
    }
}
