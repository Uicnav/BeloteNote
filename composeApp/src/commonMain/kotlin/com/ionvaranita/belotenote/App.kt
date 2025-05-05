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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import belotenote.composeapp.generated.resources.Res
import belotenote.composeapp.generated.resources.app_name
import belotenote.composeapp.generated.resources.belote_backround
import com.ionvaranita.belotenote.datalayer.database.AppDatabase
import com.ionvaranita.belotenote.ui.GamePath
import com.ionvaranita.belotenote.ui.HomeScreen
import com.ionvaranita.belotenote.ui.match.MatchScreen
import com.ionvaranita.belotenote.ui.table.TableScreen
import com.ionvaranita.belotenote.utils.BeloteTheme
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

const val ID_GAME = "idGame"

@Composable
@Preview
fun App(appDatabase: AppDatabase, navController: NavHostController = rememberNavController()) { // Get current back stack entry
    BeloteTheme {
        Scaffold(topBar = {
            BeloteAppBar(canNavigateBack = navController.previousBackStackEntry != null, navigateUp = { navController.navigateUp() })
        }) { innerPadding ->
            Image(painter = painterResource(Res.drawable.belote_backround), contentDescription = "", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds)
            NavHost(navController = navController, startDestination = Home, modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                composable<Home> {
                    HomeScreen(onClick = {
                        navController.navigate(it)
                    })
                }
                composable<Games2> {
                    TableScreen(navController = navController, appDatabase = appDatabase, gamePath = GamePath.TWO)
                }
                composable<Games3> {
                    TableScreen(navController = navController, appDatabase = appDatabase, gamePath = GamePath.THREE)
                }
                composable<Games4> {
                    TableScreen(navController = navController, appDatabase = appDatabase, gamePath = GamePath.FOUR)
                }
                composable<GamesGroups> {
                    TableScreen(navController = navController, appDatabase = appDatabase, gamePath = GamePath.GROUP)
                }
                composable<Match2> {
                    val args = it.toRoute<Match2>()
                    MatchScreen(appDatabase = appDatabase, gamePath = GamePath.TWO, idGame = args.idGame.toShort())
                }
                composable<Match3> {
                    val args = it.toRoute<Match3>()

                    MatchScreen(appDatabase = appDatabase, gamePath = GamePath.THREE, idGame = args.idGame.toShort())
                }
                composable<Match4> {
                    val args = it.toRoute<Match4>()
                    MatchScreen(appDatabase = appDatabase, gamePath = GamePath.FOUR, idGame = args.idGame.toShort())
                }
                composable<MatchGroups> {
                    val args = it.toRoute<MatchGroups>()
                    MatchScreen(appDatabase = appDatabase, gamePath = GamePath.GROUP, idGame = args.idGame.toShort())
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
fun BeloteAppBar(canNavigateBack: Boolean, navigateUp: () -> Unit, modifier: Modifier = Modifier) {
    TopAppBar(
        title = { Text(stringResource(Res.string.app_name)) }, modifier = modifier,
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
}/*@Composable
private inline fun ScreenTitle(appNavDest: AppNavDest) {
    val text = when (appNavDest) {
        is Home -> {
            stringResource(Res.string.app_name)
        }
        is Match2, is Games2 -> {
            stringResource(Res.string.two_players)

        }
        is Match3, is Games3 -> {
            stringResource(Res.string.three_players)

        }
        is Match4, is Games4 -> {
            stringResource(Res.string.four_players)

        }
        is MatchGroups, is GamesGroups -> {
            stringResource(Res.string.two_vs_two)
        }
    }
    Text(text)
}*/

@Serializable
object Home

@Serializable
object Games2

@Serializable
object Games3

@Serializable
object Games4

@Serializable
object GamesGroups

@Serializable
data class Match2(val idGame: Int)

@Serializable
data class Match3(val idGame: Int)

@Serializable
data class Match4(val idGame: Int)

@Serializable
data class MatchGroups(val idGame: Int)

