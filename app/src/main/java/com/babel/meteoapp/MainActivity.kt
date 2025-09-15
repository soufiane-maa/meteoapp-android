package com.babel.meteoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import com.babel.meteoapp.ui.CityListScreen
import com.babel.meteoapp.ui.DetailScreen
import com.babel.meteoapp.ui.LocationDetailScreen
import com.babel.meteoapp.ui.theme.MeteoAppTheme
import com.babel.meteoapp.viewmodel.CityListViewModel
import com.babel.meteoapp.viewmodel.DetailViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MeteoAppTheme {
                App()
            }
        }
    }
}

@Composable
private fun App() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "list") {
        composable("list") {
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
                    onCityClick = { city -> nav.navigate("detail/${city}") },
                    onCurrentLocationClick = {
                        nav.navigate("detail_by_coords")
                    }
                )
            }
        }
        composable(
            route = "detail/{city}",
            arguments = listOf(navArgument("city") { type = NavType.StringType })
        ) { backStackEntry ->
            val city = backStackEntry.arguments?.getString("city") ?: ""
            val vm: DetailViewModel = hiltViewModel()
            val details = vm.details.collectAsStateWithLifecycle(null)
            val isLoading = vm.isLoading.collectAsStateWithLifecycle(false)
            val error = vm.errorMessage.collectAsStateWithLifecycle(null)
            DetailScreen(
                city = city,
                data = details.value,
                isLoading = isLoading.value,
                errorMessage = error.value,
                onLoad = vm::loadByCity
            )
        }
        composable("detail_by_coords") {
            val vm: DetailViewModel = hiltViewModel()
            val details = vm.details.collectAsStateWithLifecycle(null)
            val isLoading = vm.isLoading.collectAsStateWithLifecycle(false)
            val error = vm.errorMessage.collectAsStateWithLifecycle(null)
            LocationDetailScreen(
                data = details.value,
                isLoading = isLoading.value,
                errorMessage = error.value,
                onResolveLocationAndLoad = { lat, lon -> vm.loadByCoords(lat, lon) }
            )
        }
    }
}