package com.example.ejercicio1_lab09.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ejercicio1_lab09.viewmodel.RecipeViewModel

@Composable
fun TagsScreen(navController: NavHostController, viewModel: RecipeViewModel) {
    val tags by viewModel.tags.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadTags() }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        items(tags) { tag ->
            Text(
                text = tag,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .clickable { viewModel.loadByTag(tag); navController.navigate("home") }
            )
        }
    }
}