package com.babel.meteoapp.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavHostController
import com.babel.meteoapp.ui.CityListScreen
import com.babel.meteoapp.ui.DetailScreen
import com.babel.meteoapp.ui.LocationDetailScreen
import com.babel.meteoapp.viewmodel.CityListViewModel
import com.babel.meteoapp.viewmodel.DetailViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.platform.LocalContext
import com.babel.meteoapp.R

sealed class Route(val path: String) {
    data object List : Route("list")
    data object Detail : Route("detail/{city}") {
        fun create(city: String) = "detail/${city}"
    }
    data object DetailByCoords : Route("detail_by_coords")
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current
    NavHost(navController = navController, startDestination = context.getString(R.string.nav_list)) {
        composable(context.getString(R.string.nav_list)) {
            val vm: CityListViewModel = hiltViewModel()
            val summaries = vm.filteredSummaries.collectAsStateWithLifecycle(emptyList())
            val isRefreshing = vm.isRefreshing.collectAsStateWithLifecycle(false)
            val error = vm.errorMessage.collectAsStateWithLifecycle(null)
            Scaffold(modifier = Modifier.fillMaxSize()) {
                CityListScreen(
                    summaries = summaries.value,
                    isRefreshing = isRefreshing.value,
                    errorMessage = error.value,
                    onSearchChange = vm::setSearchQuery,
                    onAddCity = vm::addCity,
                    onRemoveCity = vm::removeCity,
                    onRefresh = vm::refreshAll,
                    onCityClick = { city -> navController.navigate(Route.Detail.create(city)) },
                    onCurrentLocationClick = { navController.navigate(Route.DetailByCoords.path) }
                )
            }
        }
        composable(
            route = context.getString(R.string.nav_detail),
            arguments = listOf(navArgument(context.getString(R.string.nav_city_arg)) { type = NavType.StringType })
        ) { backStackEntry ->
            val city = backStackEntry.arguments?.getString(context.getString(R.string.nav_city_arg)) ?: ""
            val vm: DetailViewModel = hiltViewModel()
            val details = vm.details.collectAsStateWithLifecycle(null)
            val isLoading = vm.isLoading.collectAsStateWithLifecycle(false)
            val error = vm.errorMessage.collectAsStateWithLifecycle(null)
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = { Text(city) },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                            titleContentColor = Color.White,
                            navigationIconContentColor = Color.White
                        )
                    )
                }
            ) { paddingValues ->
                DetailScreen(
                    city = city,
                    data = details.value,
                    isLoading = isLoading.value,
                    errorMessage = error.value,
                    onLoad = vm::loadByCity,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
        composable(context.getString(R.string.nav_detail_by_coords)) {
            val vm: DetailViewModel = hiltViewModel()
            val details = vm.details.collectAsStateWithLifecycle(null)
            val isLoading = vm.isLoading.collectAsStateWithLifecycle(false)
            val error = vm.errorMessage.collectAsStateWithLifecycle(null)
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = { Text(context.getString(R.string.current_location_forecast)) },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                            titleContentColor = Color.White,
                            navigationIconContentColor = Color.White
                        )
                    )
                }
            ) { paddingValues ->
                LocationDetailScreen(
                    data = details.value,
                    isLoading = isLoading.value,
                    errorMessage = error.value,
                    onResolveLocationAndLoad = { lat, lon -> vm.loadByCoords(lat, lon) },
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}
