package com.babel.meteoapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.dimensionResource
import coil.compose.AsyncImage
import com.babel.meteoapp.R
import com.babel.meteoapp.domain.CitySummary
import com.babel.meteoapp.config.ApiConfig
import android.content.Context

/**
 * Card component for displaying city weather information
 * Used in tablet grid layout for better space utilization
 * 
 * Features:
 * - Clickable card with weather icon and temperature
 * - Remove button for city deletion
 * - Responsive sizing based on dimension resources
 */
@Composable
fun CityCard(item: CitySummary, onRemove: () -> Unit, onClick: () -> Unit, context: Context) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.list_item_height))
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_lg)),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_md))) {
                    AsyncImage(
                        model = "${ApiConfig.WeatherIcons.BASE_URL}${item.icon}${ApiConfig.WeatherIcons.SUFFIX}",
                        contentDescription = null,
                        modifier = Modifier.height(dimensionResource(R.dimen.weather_icon_size))
                    )
                    Column {
                        Text(
                            item.name, 
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(item.weatherMain)
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_sm))) {
                    Text(
                        text = context.getString(R.string.temperature_format, item.temperatureCelsius), 
                        style = MaterialTheme.typography.titleMedium
                    )
                    IconButton(onClick = onRemove) {
                        Icon(
                            Icons.Default.Delete, 
                            contentDescription = context.getString(R.string.remove_desc)
                        )
                    }
                }
            }
        }
    }
}
