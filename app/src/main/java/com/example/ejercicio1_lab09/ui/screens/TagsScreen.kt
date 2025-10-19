package com.example.ejercicio1_lab09.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ejercicio1_lab09.viewmodel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagsScreen(navController: NavHostController, viewModel: RecipeViewModel) {
    val tags by viewModel.tags.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadTags() }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Etiquetas") }) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(8.dp)
        ) {
            items(tags) { tag ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            viewModel.loadByTag(tag)
                            navController.navigate("home")
                        },
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Text(text = tag, modifier = Modifier.padding(12.dp))
                }
            }
        }
    }
}