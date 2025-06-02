package com.ionvaranita.belotenote

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import belotenote.composeapp.generated.resources.Res
import belotenote.composeapp.generated.resources.app_name
import belotenote.composeapp.generated.resources.belote_backround
import belotenote.composeapp.generated.resources.four_players
import belotenote.composeapp.generated.resources.three_players
import belotenote.composeapp.generated.resources.two_players
import belotenote.composeapp.generated.resources.two_vs_two
import com.ionvaranita.belotenote.constants.GLOBAL_ALPHA
import com.ionvaranita.belotenote.datalayer.database.AppDatabase
import com.ionvaranita.belotenote.ui.HomeScreen
import com.ionvaranita.belotenote.ui.LocalAppDatabase
import com.ionvaranita.belotenote.ui.LocalNavHostController
import com.ionvaranita.belotenote.ui.match.MatchScreen2
import com.ionvaranita.belotenote.ui.match.MatchScreen2Groups
import com.ionvaranita.belotenote.ui.match.MatchScreen3
import com.ionvaranita.belotenote.ui.match.MatchScreen4
import com.ionvaranita.belotenote.ui.table.TablesScreen2
import com.ionvaranita.belotenote.ui.table.TablesScreen3
import com.ionvaranita.belotenote.ui.table.TablesScreen4
import com.ionvaranita.belotenote.ui.table.TablesScreenGroups
import com.ionvaranita.belotenote.utils.BeloteTheme
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(appDatabase: AppDatabase) {
    BeloteTheme {
        CompositionLocalProvider(LocalAppDatabase provides appDatabase, LocalNavHostController provides rememberNavController()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(painter = painterResource(Res.drawable.belote_backround), contentDescription = "", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds)
                val navController = LocalNavHostController.current
                var canNavigateBack by remember {
                    mutableStateOf(navController.previousBackStackEntry != null)
                }
                var currentRoute by remember {
                    mutableStateOf(navController.currentDestination?.route)
                }
                LaunchedEffect(navController) {
                    navController.addOnDestinationChangedListener { _, _, backStackEntry ->
                        canNavigateBack = navController.previousBackStackEntry != null
                        currentRoute = navController.currentDestination?.route
                        println("Current route : =========== $currentRoute")
                    }
                }
                Scaffold(containerColor = Color.Transparent, modifier = Modifier.alpha(GLOBAL_ALPHA), topBar = {
                    BeloteAppBar(currentRoute = currentRoute, canNavigateBack = canNavigateBack, navigateUp = { navController.navigateUp() })
                }) { innerPadding ->
                    NavHost(navController = navController, startDestination = HomeDest, modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                        composable<HomeDest> {
                            HomeScreen(onClick = {
                                navController.navigate(it)
                            })
                        }
                        composable<Games2Dest> {
                            TablesScreen2()
                        }
                        composable<Games3Dest> {
                            TablesScreen3()
                        }
                        composable<Games4Dest> {
                            TablesScreen4()
                        }
                        composable<GamesGroupsDest> {
                            TablesScreenGroups()
                        }
                        composable<Match2Dest> {
                            val args = it.toRoute<Match2Dest>()
                            MatchScreen2(idGame = args.idGame)
                        }
                        composable<Match3Dest> {
                            val args = it.toRoute<Match3Dest>()

                            MatchScreen3(idGame = args.idGame)
                        }
                        composable<Match4Dest> {
                            val args = it.toRoute<Match4Dest>()
                            MatchScreen4(idGame = args.idGame)
                        }
                        composable<MatchGroupsDest> {
                            val args = it.toRoute<MatchGroupsDest>()
                            MatchScreen2Groups(idGame = args.idGame)
                        }
                    }
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
fun BeloteAppBar(currentRoute: String?, canNavigateBack: Boolean, navigateUp: () -> Unit, modifier: Modifier = Modifier) {
    TopAppBar(
        title = { ScreenTitle(currentRoute) }, modifier = modifier,
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

@Composable
private inline fun ScreenTitle(currentRoute: String?) {
    val text = when (currentRoute) {
        HomeDest::class.qualifiedName -> {
            stringResource(Res.string.app_name)
        }
        Match2Dest::class.qualifiedName + "/{idGame}", Games2Dest::class.qualifiedName -> {
            stringResource(Res.string.two_players)

        }
        Match3Dest::class.qualifiedName + "/{idGame}", Games3Dest::class.qualifiedName -> {
            stringResource(Res.string.three_players)

        }
        Match4Dest::class.qualifiedName + "/{idGame}", Games4Dest::class.qualifiedName -> {
            stringResource(Res.string.four_players)

        }
        MatchGroupsDest::class.qualifiedName + "/{idGame}", GamesGroupsDest::class.qualifiedName -> {
            stringResource(Res.string.two_vs_two)
        }
        else -> {
            stringResource(Res.string.app_name)
        }
    }
    Text(text)
}

@Serializable
object HomeDest

@Serializable
object Games2Dest

@Serializable
object Games3Dest

@Serializable
object Games4Dest

@Serializable
object GamesGroupsDest

@Serializable
data class Match2Dest(val idGame: Int)

@Serializable
data class Match3Dest(val idGame: Int)

@Serializable
data class Match4Dest(val idGame: Int)

@Serializable
data class MatchGroupsDest(val idGame: Int)

