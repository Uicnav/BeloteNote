package com.ionvaranita.belotenote

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ionvaranita.belotenote.datalayer.database.AppDatabase
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(appDatabase: AppDatabase,navController: NavHostController = rememberNavController()) { // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState() // Get the name of the current screen
    val currentScreen = AppNavDest.valueOf(backStackEntry?.destination?.route ?: AppNavDest.HOME.name)
    Scaffold(topBar = {
        BeloteAppBar(currentScreen = currentScreen, canNavigateBack = navController.previousBackStackEntry != null, navigateUp = { navController.navigateUp() })
    }) { innerPadding ->
        NavHost(navController = navController, startDestination = AppNavDest.HOME.name, modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            composable(route = AppNavDest.HOME.name) {
                HomeScreen(onClick = {
                    navController.navigate(it.name)
                })
            }
            composable(route = AppNavDest.TWO.name) {
                TableScreen(appDatabase = appDatabase,gamePath = GamePath.TWO)
            }
            composable(route = AppNavDest.THREE.name) {
                TableScreen(appDatabase = appDatabase,gamePath = GamePath.THREE)
            }
            composable(route = AppNavDest.FOUR.name) {
                TableScreen(appDatabase = appDatabase,gamePath = GamePath.FOUR)
            }
            composable(route = AppNavDest.GROUP.name) {
                TableScreen(appDatabase = appDatabase,gamePath = GamePath.GROUP)
            }
        }
    }

}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@Composable
fun BeloteAppBar(currentScreen: AppNavDest, canNavigateBack: Boolean, navigateUp: () -> Unit, modifier: Modifier = Modifier) {
    TopAppBar(title = { Text(currentScreen.title) }, modifier = modifier)
}

enum class AppNavDest(val title: String) {
    HOME("Belote Note"), TWO("2 Players"), THREE("3 Players"), FOUR("4 Players"), GROUP("2VS2")
}
