package com.example.ejercicio1_lab09.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ejercicio1_lab09.data.model.RecipeModel
import com.example.ejercicio1_lab09.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch

@Composable
fun AddEditRecipeScreen(viewModel: RecipeViewModel, onDone: () -> Unit, editing: RecipeModel? = null) {
    var name by remember { mutableStateOf(editing?.name ?: "") }
    var image by remember { mutableStateOf(editing?.image ?: "") }
    var ingredients by remember { mutableStateOf(editing?.ingredients?.joinToString(", ") ?: "") }
    var instructions by remember { mutableStateOf(editing?.instructions ?: "") }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = image, onValueChange = { image = it }, label = { Text("URL Imagen") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = ingredients, onValueChange = { ingredients = it }, label = { Text("Ingredientes (coma separado)") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = instructions, onValueChange = { instructions = it }, label = { Text("Instrucciones") })

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {
            scope.launch {
                val model = RecipeModel(
                    id = editing?.id ?: 0,
                    name = name,
                    image = image.ifBlank { null },
                    ingredients = ingredients.split(",").map { it.trim() },
                    instructions = instructions
                )
                if (editing == null) viewModel.addRecipe(model) else viewModel.updateRecipe(editing.id, model)
                onDone()
            }
        }) { Text(if (editing == null) "Agregar" else "Guardar") }
    }
}