package com.babel.meteoapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.babel.meteoapp.R

/**
 * AppBar specifically for the main city list screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityListAppBar(
    isRefreshing: Boolean = false,
    onRefreshClick: () -> Unit,
    onLocationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    
    TopAppBar(
        title = { 
            Text(
                text = context.getString(R.string.app_title),
                style = MaterialTheme.typography.titleLarge
            ) 
        },
        actions = {
            // Location button
            IconButton(onClick = onLocationClick) {
                Icon(
                    imageVector = Icons.Filled.MyLocation,
                    contentDescription = context.getString(R.string.my_location_desc)
                )
            }
            
            // Refresh button or loading indicator
            if (isRefreshing) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                IconButton(onClick = onRefreshClick) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = context.getString(R.string.refresh_desc)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        modifier = modifier
    )
}

/**
 * AppBar for detail screens with back navigation
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailAppBar(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    
    TopAppBar(
        title = { 
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            ) 
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = context.getString(R.string.back_button_desc)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        ),
        modifier = modifier
    )
}
