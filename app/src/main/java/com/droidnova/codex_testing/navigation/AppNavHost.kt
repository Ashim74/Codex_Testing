package com.droidnova.codex_testing.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.droidnova.codex_testing.ClipboardViewModel
import com.droidnova.codex_testing.ui.AboutScreen
import com.droidnova.codex_testing.ui.HomeScreen

@Composable
fun AppNavHost(
    viewModel: ClipboardViewModel,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToAbout = { navController.navigate("about") }
            )
        }
        composable("about") {
            AboutScreen(onBack = { navController.popBackStack() })
        }
    }
}
