package com.babel.meteoapp.navigation

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
import androidx.navigation.NavHostController
import com.babel.meteoapp.ui.CityListScreen
import com.babel.meteoapp.ui.DetailScreen
import com.babel.meteoapp.ui.LocationDetailScreen
import com.babel.meteoapp.viewmodel.CityListViewModel
import com.babel.meteoapp.viewmodel.DetailViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

sealed class Route(val path: String) {
    data object List : Route("list")
    data object Detail : Route("detail/{city}") {
        fun create(city: String) = "detail/${city}"
    }
    data object DetailByCoords : Route("detail_by_coords")
}

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Route.List.path) {
        composable(Route.List.path) {
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
            route = Route.Detail.path,
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
        composable(Route.DetailByCoords.path) {
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
