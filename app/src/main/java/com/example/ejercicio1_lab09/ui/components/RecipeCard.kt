package com.example.ejercicio1_lab09.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ejercicio1_lab09.data.model.RecipeModel

@Composable
fun RecipeCard(recipe: RecipeModel, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column {
            recipe.image?.let {
                AsyncImage(model = it, contentDescription = recipe.name, modifier = Modifier.fillMaxWidth().height(160.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = recipe.name, modifier = Modifier.padding(8.dp))
        }
    }
}

