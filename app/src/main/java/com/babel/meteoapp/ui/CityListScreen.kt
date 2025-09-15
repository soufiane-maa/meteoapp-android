package com.babel.meteoapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import com.babel.meteoapp.R
import android.content.Context
import androidx.compose.ui.res.dimensionResource
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import com.babel.meteoapp.ui.theme.LocalWindowSizeClass
import com.babel.meteoapp.ui.theme.ResponsiveDesign
import com.babel.meteoapp.ui.CityCard

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
    val context = LocalContext.current
    val windowSizeClass = LocalWindowSizeClass.current
    var input by remember { mutableStateOf("") }
    
    // Responsive design values - adapt UI based on screen size
    val isTablet = ResponsiveDesign.isTablet(windowSizeClass)
    val isLandscape = ResponsiveDesign.isLandscape(windowSizeClass)
    val columnCount = ResponsiveDesign.getColumnCount(windowSizeClass)

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(context.getString(R.string.app_title)) },
            actions = {
                IconButton(onClick = onCurrentLocationClick) {
                    Icon(Icons.Filled.MyLocation, contentDescription = context.getString(R.string.my_location_desc))
                }
                if (isRefreshing) {
                    // Show loading indicator during refresh
                    CircularProgressIndicator(
                        modifier = Modifier.padding(16.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    IconButton(onClick = onRefresh) {
                        Icon(Icons.Filled.Refresh, contentDescription = context.getString(R.string.refresh_desc))
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, titleContentColor = Color.White, actionIconContentColor = Color.White)
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_lg)),
            value = input,
            onValueChange = {
                input = it
                onSearchChange(it)
            },
            placeholder = { Text(context.getString(R.string.search_city_hint)) }
        )

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_lg)), horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_sm))) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = input,
                onValueChange = { input = it },
                placeholder = { Text(context.getString(R.string.add_city_hint)) }
            )
            Button(onClick = {
                if (input.isNotBlank()) {
                    onAddCity(input)
                    input = ""
                }
            }) { Text(context.getString(R.string.add_button)) }
        }

        if (errorMessage != null) {
            Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(dimensionResource(R.dimen.padding_lg)))
        }

        if (isTablet) {
            // Grid layout for tablets - better use of screen space
            LazyVerticalGrid(
                columns = GridCells.Fixed(columnCount),
                modifier = Modifier.fillMaxSize().padding(dimensionResource(R.dimen.padding_lg)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_md)),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_md))
            ) {
                items(summaries, key = { it.name }) { item ->
                    CityCard(item, onRemove = { onRemoveCity(item.name) }, onClick = { onCityClick(item.name) }, context = context)
                }
            }
        } else {
            // List layout for phones - optimized for narrow screens
            LazyColumn(modifier = Modifier.fillMaxSize().padding(dimensionResource(R.dimen.padding_lg)), verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_sm))) {
                items(summaries, key = { it.name }) { item ->
                    CityRow(item, onRemove = { onRemoveCity(item.name) }, onClick = { onCityClick(item.name) }, context = context)
                }
            }
        }
    }
}

@Composable
private fun CityRow(item: CitySummary, onRemove: () -> Unit, onClick: () -> Unit, context: Context) {
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(dimensionResource(R.dimen.padding_lg)), horizontalArrangement = Arrangement.SpaceBetween) {
            Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_md))) {
                AsyncImage(
                    model = "${context.getString(R.string.weather_icon_base_url)}${item.icon}${context.getString(R.string.weather_icon_suffix)}",
                    contentDescription = null
                )
                Column {
                    Text(item.name, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    Text(item.weatherMain)
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_sm))) {
                Text(text = context.getString(R.string.temperature_format, item.temperatureCelsius), style = MaterialTheme.typography.titleMedium)
                IconButton(onClick = onRemove) {
                    Icon(androidx.compose.material.icons.Icons.Default.Delete, contentDescription = context.getString(R.string.remove_desc))
                }
            }
        }
    }
}


