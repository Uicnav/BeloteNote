package com.ionvaranita.belotenote

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import belotenote.composeapp.generated.resources.Res
import belotenote.composeapp.generated.resources.belote_backround
import com.ionvaranita.belotenote.datalayer.database.AppDatabase
import com.ionvaranita.belotenote.utils.BeloteTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(appDatabase: AppDatabase, navController: NavHostController = rememberNavController()) { // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState() // Get the name of the current screen
    val currentScreen = AppNavDest.valueOf(backStackEntry?.destination?.route ?: AppNavDest.HOME.name)
    BeloteTheme {
        Scaffold(topBar = {
            BeloteAppBar(currentScreen = currentScreen, canNavigateBack = navController.previousBackStackEntry != null, navigateUp = { navController.navigateUp() })
        }) { innerPadding ->
            Image(painter = painterResource(Res.drawable.belote_backround), contentDescription = "", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds)
            NavHost(navController = navController, startDestination = AppNavDest.HOME.name, modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                composable(route = AppNavDest.HOME.name) {
                    HomeScreen(onClick = {
                        navController.navigate(it.name)
                    })
                }
                composable(route = AppNavDest.TWO.name) {
                    TableScreen(appDatabase = appDatabase, gamePath = GamePath.TWO)
                }
                composable(route = AppNavDest.THREE.name) {
                    TableScreen(appDatabase = appDatabase, gamePath = GamePath.THREE)
                }
                composable(route = AppNavDest.FOUR.name) {
                    TableScreen(appDatabase = appDatabase, gamePath = GamePath.FOUR)
                }
                composable(route = AppNavDest.GROUP.name) {
                    TableScreen(appDatabase = appDatabase, gamePath = GamePath.GROUP)
                }
            }
        }
    }


}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeloteAppBar(currentScreen: AppNavDest, canNavigateBack: Boolean, navigateUp: () -> Unit, modifier: Modifier = Modifier) {
    TopAppBar(
        title = { Text(currentScreen.title) }, modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton({
                               navigateUp()
                           }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "menu items")
                }
            }
        },
             )
}

enum class AppNavDest(val title: String) {
    HOME("Belote Note"), TWO("2 Players"), THREE("3 Players"), FOUR("4 Players"), GROUP("2VS2")
}
