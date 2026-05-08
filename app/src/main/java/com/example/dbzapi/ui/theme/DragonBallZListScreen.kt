package com.example.dbzapi.ui.theme

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.dbzapi.model.Character
import com.example.dbzapi.viewModel.DragonBallState
import com.example.dbzapi.viewModel.DragonBallZViewModel


@Composable
fun DragonBallZListScreen(
    viewModel: DragonBallZViewModel = viewModel(),
    onCharacterClick: (Character) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    var searchExpanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Personajes") }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF7F7F7))) {

        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("Personajes", "Planetas", "Poder").forEach { cat ->
                Button(
                    onClick = { selectedCategory = cat },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedCategory == cat) Color(0xFFFF9800) else Color.Gray
                    )
                ) { Text(cat) }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp).animateContentSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { searchExpanded = !searchExpanded }) {
                Icon(if (searchExpanded) Icons.Default.Close else Icons.Default.Search, "Busca")
            }
            if (searchExpanded) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Buscar...") },
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }

        when (val res = state) {
            is DragonBallState.Loading -> {
                Box(Modifier.fillMaxSize(), Alignment.Center) { CircularProgressIndicator() }
            }
            is DragonBallState.Error -> {
                Box(Modifier.fillMaxSize(), Alignment.Center) { Text(res.message) }
            }
            is DragonBallState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize().padding(8.dp),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    if (selectedCategory == "Personajes" || selectedCategory == "Poder") {
                        val chars = res.characters.filter { it.name.contains(searchQuery, true) }
                        items(chars) { character ->
                            DragonBallZItemCard(
                                name = character.name,
                                imageUrl = character.image,
                                info = if(selectedCategory == "Poder") "KI: ${character.ki}" else character.race,
                                onClick = { onCharacterClick(character) }
                            )
                        }
                    } else if (selectedCategory == "Planetas") {
                        val planets = res.planets.filter { it.name.contains(searchQuery, true) }
                        items(planets) { planet ->
                            DragonBallZItemCard(name = planet.name, imageUrl = planet.image, info = "Planeta", onClick = {})
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DragonBallZItemCard(name: String, imageUrl: String, info: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(220.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            AsyncImage(
                model = imageUrl,
                contentDescription = name,
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
            Column(Modifier.fillMaxWidth().padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(name, fontWeight = FontWeight.Bold, maxLines = 1)
                Text(info, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
        }
    }
}