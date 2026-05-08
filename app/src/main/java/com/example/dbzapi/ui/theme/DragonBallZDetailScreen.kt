package com.example.dbzapi.ui.theme

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.dbzapi.model.Character

@Composable
fun DragonBallZDetailScreen(character: Character, onBack: () -> Unit) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFFF9800), Color(0xFFFF5722))
                    )
                )
        ) {
            IconButton(onClick = onBack, modifier = Modifier.padding(top = 40.dp, start = 8.dp)) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver", tint = Color.White)
            }

            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                contentScale = ContentScale.Fit
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = character.name,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "${character.race} - ${character.gender}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Base KI:", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
                        Text(
                            character.ki,
                            color = Color(0xFFFF5722),
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                        Text("Total KI:", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
                        Text(
                            character.maxKi,
                            color = Color(0xFFFF5722),
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Descripción", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Text(
                text = character.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )

            character.originPlanet?.let { planet ->
                Spacer(modifier = Modifier.height(24.dp))
                Text("Planeta de Origen", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Card(
                    modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = planet.image,
                            contentDescription = planet.name,
                            modifier = Modifier.size(100.dp).padding(8.dp),
                            contentScale = ContentScale.Crop
                        )
                        Text(planet.name, modifier = Modifier.padding(start = 8.dp), fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            if (!character.transformations.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(24.dp))
                Text("Transformaciones", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)

                LazyRow(modifier = Modifier.padding(top = 8.dp)) {
                    items(character.transformations) { trans ->
                        Column(
                            modifier = Modifier.padding(end = 16.dp).width(120.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AsyncImage(
                                model = trans.image,
                                contentDescription = trans.name,
                                modifier = Modifier.size(100.dp).clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Fit
                            )
                            Text(trans.name, maxLines = 1, style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
                        }
                    }
                }
            }
        }
    }
}