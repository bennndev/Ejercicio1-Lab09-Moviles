package com.example.ejercicio1_lab09.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ejercicio1_lab09.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(navController: NavHostController, viewModel: RecipeViewModel, id: Int) {
    val selected by viewModel.selected.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(id) { viewModel.loadRecipe(id) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(selected?.name ?: "Detalle") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        selected?.let { r ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text("Ingredientes:", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                r.ingredients.forEach { Text("- $it") }

                Spacer(modifier = Modifier.height(8.dp))
                Text("Instrucciones:", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                r.instructions.forEach { Text("- $it") }

                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { navController.navigate("edit/${r.id}") }) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Editar")
                    }
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        onClick = {
                            scope.launch {
                                viewModel.deleteRecipe(r.id)
                                navController.popBackStack()
                            }
                        }
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Eliminar")
                    }
                }
            }
        } ?: run {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}
