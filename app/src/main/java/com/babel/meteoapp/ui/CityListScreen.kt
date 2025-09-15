package com.babel.meteoapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.babel.meteoapp.domain.CitySummary
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Refresh

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityListScreen(
    summaries: List<CitySummary>,
    isRefreshing: Boolean,
    errorMessage: String?,
    onSearchChange: (String) -> Unit,
    onAddCity: (String) -> Unit,
    onRemoveCity: (String) -> Unit,
    onRefresh: () -> Unit,
    onCityClick: (String) -> Unit,
    onCurrentLocationClick: () -> Unit = {}
) {
    var input by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Meteo") },
            actions = {
                IconButton(onClick = onCurrentLocationClick) {
                    Icon(Icons.Filled.MyLocation, contentDescription = "My location")
                }
                IconButton(onClick = onRefresh) {
                    Icon(androidx.compose.material.icons.Icons.Default.Refresh, contentDescription = "Refresh")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, titleContentColor = Color.White, actionIconContentColor = Color.White)
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = input,
            onValueChange = {
                input = it
                onSearchChange(it)
            },
            placeholder = { Text("Search city...") }
        )

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = input,
                onValueChange = { input = it },
                placeholder = { Text("Add city") }
            )
            Button(onClick = {
                if (input.isNotBlank()) {
                    onAddCity(input)
                    input = ""
                }
            }) { Text("Add") }
        }

        if (errorMessage != null) {
            Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(16.dp))
        }

        LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(summaries, key = { it.name }) { item ->
                CityRow(item, onRemove = { onRemoveCity(item.name) }, onClick = { onCityClick(item.name) })
            }
        }
    }
}

@Composable
private fun CityRow(item: CitySummary, onRemove: () -> Unit, onClick: () -> Unit) {
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                AsyncImage(
                    model = "https://openweathermap.org/img/wn/${item.icon}@2x.png",
                    contentDescription = null
                )
                Column {
                    Text(item.name, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    Text(item.weatherMain)
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "${item.temperatureCelsius}°C", style = MaterialTheme.typography.titleMedium)
                IconButton(onClick = onRemove) {
                    Icon(androidx.compose.material.icons.Icons.Default.Delete, contentDescription = "Remove")
                }
            }
        }
    }
}


