package com.example.ejercicio1_lab09.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ejercicio1_lab09.data.model.RecipeModel
import com.example.ejercicio1_lab09.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditRecipeScreen(
    navController: NavHostController,
    viewModel: RecipeViewModel,
    editing: RecipeModel? = null
) {
    var name by remember { mutableStateOf(editing?.name ?: "") }
    var image by remember { mutableStateOf(editing?.image ?: "") }
    var ingredients by remember { mutableStateOf(editing?.ingredients?.joinToString(", ") ?: "") }
    var instructions by remember { mutableStateOf(editing?.instructions?.joinToString("\n") ?: "") }

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (editing == null) "Agregar receta" else "Editar receta") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = image, onValueChange = { image = it }, label = { Text("URL Imagen") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = ingredients,
                onValueChange = { ingredients = it },
                label = { Text("Ingredientes (separados por coma)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = instructions,
                onValueChange = { instructions = it },
                label = { Text("Instrucciones (una por línea)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    scope.launch {
                        val model = RecipeModel(
                            id = editing?.id ?: 0,
                            name = name,
                            image = image.ifBlank { null },
                            ingredients = ingredients.split(",").map { it.trim() }.filter { it.isNotEmpty() },
                            instructions = instructions.split("\n").map { it.trim() }.filter { it.isNotEmpty() }
                        )

                        if (editing == null) viewModel.addRecipe(model)
                        else viewModel.updateRecipe(editing.id, model)

                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (editing == null) "Agregar" else "Guardar")
            }
        }
    }
}