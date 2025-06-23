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
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.ionvaranita.belotenote.datalayer.datasource.game.Game2GroupsDataSourceImpl
import com.ionvaranita.belotenote.datalayer.datasource.game.Game2PDataSourceImpl
import com.ionvaranita.belotenote.datalayer.datasource.game.Game3PDataSourceImpl
import com.ionvaranita.belotenote.datalayer.datasource.game.Game4PDataSourceImpl
import com.ionvaranita.belotenote.datalayer.datasource.match.Points2GroupsDataSourceImpl
import com.ionvaranita.belotenote.datalayer.datasource.match.Points2PDataSourceImpl
import com.ionvaranita.belotenote.datalayer.datasource.match.Points3PDataSourceImpl
import com.ionvaranita.belotenote.datalayer.repo.game.Games2GroupsRepositoryImpl
import com.ionvaranita.belotenote.datalayer.repo.game.Games2PRepositoryImpl
import com.ionvaranita.belotenote.datalayer.repo.game.Games3PRepositoryImpl
import com.ionvaranita.belotenote.datalayer.repo.game.Games4PRepositoryImpl
import com.ionvaranita.belotenote.datalayer.repo.match.Points2GroupsRepositoryImpl
import com.ionvaranita.belotenote.datalayer.repo.match.Points2PRepositoryImpl
import com.ionvaranita.belotenote.datalayer.repo.match.Points3PRepositoryImpl
import com.ionvaranita.belotenote.domain.usecase.game.delete.DeleteGame2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.game.delete.DeleteGame2PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.delete.DeleteGame3PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.delete.DeleteGame4PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.get.GetGame2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.game.get.GetGame2PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.get.GetGame3PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.get.GetGames2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.game.get.GetGames2PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.get.GetGames3PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.get.GetGames4PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.insert.InsertGame2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.game.insert.InsertGame2PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.insert.InsertGame3PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.insert.InsertGame4PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateOnlyStatusGame2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateOnlyStatusGame2PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusScoreGame2GroupsName1UseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusScoreGame2GroupsName2UseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusScoreName1Game2PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusScoreName2Game2PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusWinningPointsGame2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusWinningPointsGame2PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.delete.DeleteAllPoints2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.match.delete.DeleteAllPoints2PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.delete.DeleteLastRowPoints2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.match.delete.DeleteLastRowPoints2PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.delete.DeleteLastRowPoints3PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.get.GetLastPoints2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.match.get.GetLastPoints3PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.get.GetPoints2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.match.get.GetPoints2PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.get.GetPoints3PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.insert.InsertPoints2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.match.insert.InsertPoints2PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.insert.InsertPoints3PUseCase
import com.ionvaranita.belotenote.ui.HomeScreen
import com.ionvaranita.belotenote.ui.LocalNavHostController
import com.ionvaranita.belotenote.ui.match.MatchScreen2
import com.ionvaranita.belotenote.ui.match.MatchScreen2Groups
import com.ionvaranita.belotenote.ui.match.MatchScreen3
import com.ionvaranita.belotenote.ui.match.MatchScreen4
import com.ionvaranita.belotenote.ui.table.TablesScreen2
import com.ionvaranita.belotenote.ui.table.TablesScreen3
import com.ionvaranita.belotenote.ui.table.TablesScreen4
import com.ionvaranita.belotenote.ui.table.TablesScreenGroups
import com.ionvaranita.belotenote.ui.viewmodel.game.Game2GroupsViewModel
import com.ionvaranita.belotenote.ui.viewmodel.game.Game2PViewModel
import com.ionvaranita.belotenote.ui.viewmodel.game.Game3PViewModel
import com.ionvaranita.belotenote.ui.viewmodel.game.Game4PViewModel
import com.ionvaranita.belotenote.ui.viewmodel.match.Match2GroupsViewModel
import com.ionvaranita.belotenote.ui.viewmodel.match.Match2PPViewModel
import com.ionvaranita.belotenote.ui.viewmodel.match.Match3PPViewModel
import com.ionvaranita.belotenote.utils.BeloteTheme
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(appDatabase: AppDatabase) {

    BeloteTheme {
        CompositionLocalProvider(
            LocalNavHostController provides rememberNavController()
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(Res.drawable.belote_backround),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
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
                Scaffold(
                    containerColor = Color.Transparent,
                    modifier = Modifier.alpha(GLOBAL_ALPHA),
                    topBar = {
                        BeloteAppBar(
                            currentRoute = currentRoute,
                            canNavigateBack = canNavigateBack,
                            navigateUp = { navController.navigateUp() })
                    }) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = HomeDest,
                        modifier = Modifier.fillMaxSize().padding(innerPadding)
                    ) {
                        composable<HomeDest> {
                            HomeScreen(onClick = {
                                navController.navigate(it)
                            })
                        }
                        composable<Games2Dest> {
                            val repositoryGame =
                                Games2PRepositoryImpl(Game2PDataSourceImpl(appDatabase.game2PDao()))
                            val getGamesUseCase = GetGames2PUseCase(repositoryGame)
                            val insertGameUseCase = InsertGame2PUseCase(repositoryGame)
                            val deleteGameUseCase = DeleteGame2PUseCase(repositoryGame)
                            val game2PViewModel = viewModel {
                                Game2PViewModel(
                                    getGamesUseCase, insertGameUseCase, deleteGameUseCase
                                )
                            }
                            TablesScreen2(
                                viewModel = game2PViewModel,
                            )
                        }
                        composable<Games3Dest> {
                            val repository =
                                Games3PRepositoryImpl(Game3PDataSourceImpl(appDatabase.game3PDao()))
                            val getGamesUseCase: GetGames3PUseCase = GetGames3PUseCase(repository)
                            val insertGameUseCase: InsertGame3PUseCase =
                                InsertGame3PUseCase(repository)
                            val deleteGameUseCase: DeleteGame3PUseCase =
                                DeleteGame3PUseCase(repository)
                            val game3PViewModel = viewModel {
                                Game3PViewModel(
                                    getGamesUseCase, insertGameUseCase, deleteGameUseCase
                                )
                            }
                            TablesScreen3(game3PViewModel)
                        }
                        composable<Games4Dest> {
                            val repository =
                                Games4PRepositoryImpl(Game4PDataSourceImpl(appDatabase.game4PDao()))
                            val getGamesUseCase = GetGames4PUseCase(repository)
                            val insertGameUseCase = InsertGame4PUseCase(repository)
                            val deleteGameUseCase = DeleteGame4PUseCase(repository)
                            val game4PViewModel = viewModel {
                                Game4PViewModel(
                                    getGamesUseCase, insertGameUseCase, deleteGameUseCase
                                )
                            }
                            TablesScreen4(
                                game4PViewModel = game4PViewModel,
                            )
                        }
                        composable<GamesGroupsDest> {
                            val repository =
                                Games2GroupsRepositoryImpl(Game2GroupsDataSourceImpl(appDatabase.game2GroupsDao()))
                            val getGamesUseCase: GetGames2GroupsUseCase =
                                GetGames2GroupsUseCase(repository)
                            val insertGameUseCase: InsertGame2GroupsUseCase =
                                InsertGame2GroupsUseCase(repository)
                            val deleteGameUseCase: DeleteGame2GroupsUseCase =
                                DeleteGame2GroupsUseCase(repository)
                            val game2GroupsViewModel = viewModel {
                                Game2GroupsViewModel(
                                    getGamesUseCase, insertGameUseCase, deleteGameUseCase
                                )
                            }
                            TablesScreenGroups(game2GroupsViewModel)
                        }
                        composable<Match2Dest> {
                            val idGame = it.toRoute<Match2Dest>().idGame
                            val repositoryGame =
                                Games2PRepositoryImpl(Game2PDataSourceImpl(appDatabase.game2PDao()))
                            val getGameUseCase = GetGame2PUseCase(repositoryGame)
                            val repositoryPoints =
                                Points2PRepositoryImpl(Points2PDataSourceImpl(appDatabase.points2PDao()))

                            val getPointsUseCase = GetPoints2PUseCase(repositoryPoints)
                            val insertPointsUseCase = InsertPoints2PUseCase(repositoryPoints)
                            val deleteLastRowUseCase =
                                DeleteLastRowPoints2PUseCase(repositoryPoints)

                            val updateStatusScoreName1UseCase =
                                UpdateStatusScoreName1Game2PUseCase(repositoryGame)
                            val updateStatusScoreName2UseCase =
                                UpdateStatusScoreName2Game2PUseCase(repositoryGame)

                            val updateStatusWinningPointsUseCase =
                                UpdateStatusWinningPointsGame2PUseCase(repositoryGame)
                            val updateOnlyStatusUseCase =
                                UpdateOnlyStatusGame2PUseCase(repositoryGame)
                            val deleteAllPointsUseCase = DeleteAllPoints2PUseCase(repositoryPoints)
                            val viewModel = viewModel {
                                Match2PPViewModel(
                                    idGame,
                                    getGameUseCase,
                                    getPointsUseCase,
                                    insertPointsUseCase,
                                    deleteLastRowUseCase,
                                    updateStatusScoreName1UseCase,
                                    updateStatusScoreName2UseCase,
                                    updateStatusWinningPointsUseCase,
                                    updateOnlyStatusUseCase,
                                    deleteAllPointsUseCase
                                )
                            }
                            MatchScreen2(viewModel)
                        }
                        composable<Match3Dest> {
                            val idGame = it.toRoute<Match3Dest>().idGame

                            val repositoryGame =
                                Games3PRepositoryImpl(Game3PDataSourceImpl(appDatabase.game3PDao()))
                            val repositoryPoints =
                                Points3PRepositoryImpl(Points3PDataSourceImpl(appDatabase.points3PDao()))
                            val getGameUseCase = GetGame3PUseCase(repositoryGame)
                            val getPointsUseCase = GetPoints3PUseCase(repositoryPoints)
                            val getLastUseCase = GetLastPoints3PUseCase(repositoryPoints)
                            val insertPointsUseCase = InsertPoints3PUseCase(repositoryPoints)
                            val deleteLastRowUseCase =
                                DeleteLastRowPoints3PUseCase(repositoryPoints)

                            val match3PPViewModel = viewModel {
                                Match3PPViewModel(
                                    idGame,
                                    getGameUseCase,
                                    getPointsUseCase,
                                    getLastUseCase,
                                    insertPointsUseCase,
                                    deleteLastRowUseCase
                                )
                            }


                            MatchScreen3(viewModel = match3PPViewModel)
                        }
                        composable<Match4Dest> {
                            val args = it.toRoute<Match4Dest>()
                            MatchScreen4(idGame = args.idGame)
                        }
                        composable<MatchGroupsDest> {
                            val idGame = it.toRoute<MatchGroupsDest>().idGame
                            val repositoryGame =
                                Games2GroupsRepositoryImpl(Game2GroupsDataSourceImpl(appDatabase.game2GroupsDao()))
                            val repositoryPoints =
                                Points2GroupsRepositoryImpl(Points2GroupsDataSourceImpl(appDatabase.points2GroupsDao()))
                            val getGameUseCase = GetGame2GroupsUseCase(repositoryGame)
                            val getPointsUseCase = GetPoints2GroupsUseCase(repositoryPoints)
                            val getLastPointsUseCase = GetLastPoints2GroupsUseCase(repositoryPoints)
                            val insertPointsUseCase = InsertPoints2GroupsUseCase(repositoryPoints)
                            val deleteLastRowPointsUseCase =
                                DeleteLastRowPoints2GroupsUseCase(repositoryPoints)
                            val updateStatusScoreName1UseCase =
                                UpdateStatusScoreGame2GroupsName1UseCase(repositoryGame)
                            val updateStatusScoreName2UseCase =
                                UpdateStatusScoreGame2GroupsName2UseCase(repositoryGame)

                            val updateStatusWinningPointsUseCase =
                                UpdateStatusWinningPointsGame2GroupsUseCase(repositoryGame)
                            val updateOnlyStatusGameUseCase =
                                UpdateOnlyStatusGame2GroupsUseCase(repositoryGame)
                            val deleteAllPointsUseCase =
                                DeleteAllPoints2GroupsUseCase(repositoryPoints)

                            val match2GroupsViewModel = viewModel {
                                Match2GroupsViewModel(
                                    idGame,
                                    getGameUseCase,
                                    getPointsUseCase,
                                    getLastPointsUseCase,
                                    insertPointsUseCase,
                                    deleteLastRowPointsUseCase,
                                    updateStatusScoreName1UseCase,
                                    updateStatusScoreName2UseCase,
                                    updateStatusWinningPointsUseCase,
                                    updateOnlyStatusGameUseCase,
                                    deleteAllPointsUseCase
                                )
                            }

                            MatchScreen2Groups(viewModel = match2GroupsViewModel)
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
fun BeloteAppBar(
    currentRoute: String?,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { ScreenTitle(currentRoute) }, modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton({
                    navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "menu items"
                    )
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

