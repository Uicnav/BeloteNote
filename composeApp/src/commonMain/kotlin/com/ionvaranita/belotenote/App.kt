package com.ionvaranita.belotenote

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import belotenote.composeapp.generated.resources.Res
import belotenote.composeapp.generated.resources.app_name
import belotenote.composeapp.generated.resources.four_players
import belotenote.composeapp.generated.resources.game
import belotenote.composeapp.generated.resources.image_background_dark
import belotenote.composeapp.generated.resources.image_background_light
import belotenote.composeapp.generated.resources.reminder_body
import belotenote.composeapp.generated.resources.reminder_title
import belotenote.composeapp.generated.resources.tables_list
import belotenote.composeapp.generated.resources.three_players
import belotenote.composeapp.generated.resources.two_players
import belotenote.composeapp.generated.resources.two_vs_two
import com.ionvaranita.belotenote.alarmee.createAlarmeePlatformConfiguration
import com.ionvaranita.belotenote.datalayer.database.AppDatabase
import com.ionvaranita.belotenote.datalayer.datasource.game.Game2GroupsDataSourceImpl
import com.ionvaranita.belotenote.datalayer.datasource.game.Game2PDataSourceImpl
import com.ionvaranita.belotenote.datalayer.datasource.game.Game3PDataSourceImpl
import com.ionvaranita.belotenote.datalayer.datasource.game.Game4PDataSourceImpl
import com.ionvaranita.belotenote.datalayer.datasource.match.Points2GroupsDataSourceImpl
import com.ionvaranita.belotenote.datalayer.datasource.match.Points2PDataSourceImpl
import com.ionvaranita.belotenote.datalayer.datasource.match.Points3PDataSourceImpl
import com.ionvaranita.belotenote.datalayer.datasource.match.Points4PDataSourceImpl
import com.ionvaranita.belotenote.datalayer.datasource.winningpoints.WinningPointsDataSourceImpl
import com.ionvaranita.belotenote.datalayer.repo.game.Games2GroupsRepositoryImpl
import com.ionvaranita.belotenote.datalayer.repo.game.Games2PRepositoryImpl
import com.ionvaranita.belotenote.datalayer.repo.game.Games3PRepositoryImpl
import com.ionvaranita.belotenote.datalayer.repo.game.Games4PRepositoryImpl
import com.ionvaranita.belotenote.datalayer.repo.match.Points2GroupsRepositoryImpl
import com.ionvaranita.belotenote.datalayer.repo.match.Points2PRepositoryImpl
import com.ionvaranita.belotenote.datalayer.repo.match.Points3PRepositoryImpl
import com.ionvaranita.belotenote.datalayer.repo.match.Points4PRepositoryImpl
import com.ionvaranita.belotenote.datalayer.repo.winningpoints.WinningPointsRepositoryImpl
import com.ionvaranita.belotenote.domain.usecase.game.delete.DeleteGame2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.game.delete.DeleteGame2PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.delete.DeleteGame3PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.delete.DeleteGame4PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.get.GetGame2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.game.get.GetGame2PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.get.GetGame3PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.get.GetGame4PUseCase
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
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateOnlyStatusGame3PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateOnlyStatusGame4PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusScoreGame2GroupsName1UseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusScoreGame2GroupsName2UseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusScoreName1Game2PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusScoreName1Game3PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusScoreName1Game4PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusScoreName2Game2PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusScoreName2Game3PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusScoreName2Game4PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusScoreName3Game3PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusScoreName3Game4PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusScoreName4Game4PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusWinningPointsGame2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusWinningPointsGame2PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusWinningPointsGame3PUseCase
import com.ionvaranita.belotenote.domain.usecase.game.update.UpdateStatusWinningPointsGame4PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.delete.DeleteAllPoints2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.match.delete.DeleteAllPoints2PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.delete.DeleteAllPoints3PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.delete.DeleteAllPoints4PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.delete.DeleteLastRowPoints2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.match.delete.DeleteLastRowPoints2PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.delete.DeleteLastRowPoints3PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.delete.DeleteLastRowPoints4PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.get.GetPoints2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.match.get.GetPoints2PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.get.GetPoints3PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.get.GetPoints4PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.insert.InsertPoints2GroupsUseCase
import com.ionvaranita.belotenote.domain.usecase.match.insert.InsertPoints2PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.insert.InsertPoints3PUseCase
import com.ionvaranita.belotenote.domain.usecase.match.insert.InsertPoints4PUseCase
import com.ionvaranita.belotenote.domain.usecase.winningpoints.get.GetWinningPointsUseCase
import com.ionvaranita.belotenote.domain.usecase.winningpoints.insert.InsertWinningPointsUseCase
import com.ionvaranita.belotenote.review.rememberInAppReviewManager
import com.ionvaranita.belotenote.ui.HomeScreen
import com.ionvaranita.belotenote.ui.LocalNavHostController
import com.ionvaranita.belotenote.ui.LocalSnackbarHostState
import com.ionvaranita.belotenote.ui.match.MatchScreen2
import com.ionvaranita.belotenote.ui.match.MatchScreen2Groups
import com.ionvaranita.belotenote.ui.match.MatchScreen3
import com.ionvaranita.belotenote.ui.match.MatchScreen4
import com.ionvaranita.belotenote.ui.table.TablesScreen2
import com.ionvaranita.belotenote.ui.table.TablesScreen3
import com.ionvaranita.belotenote.ui.table.TablesScreen4
import com.ionvaranita.belotenote.ui.table.TablesScreenGroups
import com.ionvaranita.belotenote.ui.viewmodel.WinningPointsViewModel
import com.ionvaranita.belotenote.ui.viewmodel.game.Game2GroupsViewModel
import com.ionvaranita.belotenote.ui.viewmodel.game.Game2PViewModel
import com.ionvaranita.belotenote.ui.viewmodel.game.Game3PViewModel
import com.ionvaranita.belotenote.ui.viewmodel.game.Game4PViewModel
import com.ionvaranita.belotenote.ui.viewmodel.match.Match2GroupsViewModel
import com.ionvaranita.belotenote.ui.viewmodel.match.Match2PPViewModel
import com.ionvaranita.belotenote.ui.viewmodel.match.Match3PPViewModel
import com.ionvaranita.belotenote.ui.viewmodel.match.Match4PPViewModel
import com.ionvaranita.belotenote.utils.BeloteTheme
import com.tweener.alarmee.AlarmeeService
import com.tweener.alarmee.model.Alarmee
import com.tweener.alarmee.model.AndroidNotificationConfiguration
import com.tweener.alarmee.model.AndroidNotificationPriority
import com.tweener.alarmee.model.IosNotificationConfiguration
import com.tweener.alarmee.model.RepeatInterval
import com.tweener.alarmee.rememberAlarmeeService
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.time.ExperimentalTime

const val dailyNewsChannelId = "dailyNewsChannelId"
const val BelotNoteAlarmId = "BelotNoteAlarmId"
const val breakingNewsChannelId = "breakingNewsChannelId"

@OptIn(ExperimentalTime::class)
@Composable
fun App(appDatabase: AppDatabase, prefs: DataStore<Preferences>) {
    val alarmService: AlarmeeService = rememberAlarmeeService(
        platformConfiguration = createAlarmeePlatformConfiguration()
    )

    LaunchedEffect(Unit) {
        // 1. Luăm referința spre localService
        val localService = alarmService.local

        // 2. Anulăm orice alarmă veche cu același ID
        localService.cancel(BelotNoteAlarmId)

        // 3. Luăm timpul curent (Instant)
        val nowInstant = kotlin.time.Clock.System.now()
        val timeZone = TimeZone.currentSystemDefault()

        // 4. Calculăm "mâine la aceeași oră" ca LocalDateTime
        val tomorrowSameTimeInstant = nowInstant.plus(1, DateTimeUnit.DAY, timeZone)
        val tomorrowSameTimeLocalDateTime = tomorrowSameTimeInstant.toLocalDateTime(timeZone)

        // 5. Programăm Alarmee: prima notificare mâine la aceeași oră,
        // apoi zilnic. De fiecare dată când userul intră în app,
        // acest cod rulează din nou, cancellează și resetează ora.
        localService.schedule(
            alarmee = Alarmee(
                uuid = BelotNoteAlarmId,
                notificationTitle = getString(Res.string.reminder_title),
                notificationBody = getString(Res.string.reminder_body),
                scheduledDateTime = tomorrowSameTimeLocalDateTime,
                repeatInterval = RepeatInterval.Daily,
                androidNotificationConfiguration = AndroidNotificationConfiguration(
                    priority = AndroidNotificationPriority.HIGH,
                    channelId = dailyNewsChannelId,
                ),
                iosNotificationConfiguration = IosNotificationConfiguration(
                    soundFilename = "notifications_sound.mp3"
                ),
            )
        )
    }/*LaunchedEffect(true) {
        val localService = alarmService.local*//*localService.schedule(
            alarmee = Alarmee(
                uuid = BelotNoteAlarmId,
                notificationTitle = getString(Res.string.reminder_title),
                notificationBody = getString(Res.string.reminder_body),
                repeatInterval = RepeatInterval.Custom(duration = 1.minutes),
                androidNotificationConfiguration = AndroidNotificationConfiguration(
                    // Required configuration for Android target only (this parameter is ignored on iOS)
                    priority = AndroidNotificationPriority.HIGH,
                    channelId = dailyNewsChannelId,
                ),
                iosNotificationConfiguration = IosNotificationConfiguration(soundFilename = "notifications_sound.mp3"),
            )

        )*//*
        localService.cancel(BelotNoteAlarmId)
        localService.schedule(
            alarmee = Alarmee(
                uuid = BelotNoteAlarmId,
                notificationTitle = getString(Res.string.reminder_title),
                notificationBody = getString(Res.string.reminder_body),
                scheduledDateTime = LocalDateTime(
                    year = 2025, month = Month.NOVEMBER, day = 16, hour = 6, minute = 46
                ),
                repeatInterval = RepeatInterval.Daily, // Will repeat every day
                androidNotificationConfiguration = AndroidNotificationConfiguration(
                    // Required configuration for Android target only (this parameter is ignored on iOS)
                    priority = AndroidNotificationPriority.HIGH,
                    channelId = dailyNewsChannelId,
                ),
                iosNotificationConfiguration = IosNotificationConfiguration(soundFilename = "notifications_sound.mp3"),
                )
        )
    }*/
    BeloteTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        val navController = rememberNavController()

        CompositionLocalProvider(
            LocalNavHostController provides navController,
            LocalSnackbarHostState provides snackbarHostState
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = if (isSystemInDarkTheme()) painterResource(Res.drawable.image_background_dark)
                    else painterResource(Res.drawable.image_background_light),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val winningPointsRepository = WinningPointsRepositoryImpl(
                        WinningPointsDataSourceImpl(appDatabase.winningPointsDao())
                    )
                    val getWinningPointsUseCase = GetWinningPointsUseCase(winningPointsRepository)
                    val insertWinningPointsUseCase =
                        InsertWinningPointsUseCase(winningPointsRepository)
                    val winningPointsViewModel = viewModel {
                        WinningPointsViewModel(
                            getWinningPointsUseCase = getWinningPointsUseCase,
                            insertWinningPointsUseCase = insertWinningPointsUseCase
                        )
                    }

                    var canNavigateBack by remember {
                        mutableStateOf(navController.previousBackStackEntry != null)
                    }
                    var currentRoute by remember {
                        mutableStateOf(navController.currentDestination?.route)
                    }

                    LaunchedEffect(navController) {
                        navController.addOnDestinationChangedListener { _, _, _ ->
                            canNavigateBack = navController.previousBackStackEntry != null
                            currentRoute = navController.currentDestination?.route
                        }
                    }

                    Scaffold(
                        snackbarHost = { SnackbarHost(snackbarHostState) },
                        containerColor = Color.Transparent,
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
                                        getGamesUseCase = getGamesUseCase,
                                        insertGameUseCase = insertGameUseCase,
                                        deleteGameUseCase = deleteGameUseCase,
                                        prefs = prefs
                                    )
                                }

                                TablesScreen2(
                                    viewModel = game2PViewModel,
                                    winningPointsViewModel = winningPointsViewModel,
                                )
                            }
                            composable<Games3Dest> {
                                val repository =
                                    Games3PRepositoryImpl(Game3PDataSourceImpl(appDatabase.game3PDao()))
                                val getGamesUseCase = GetGames3PUseCase(repository)
                                val insertGameUseCase = InsertGame3PUseCase(repository)
                                val deleteGameUseCase = DeleteGame3PUseCase(repository)
                                val game3PViewModel = viewModel {
                                    Game3PViewModel(
                                        getGamesUseCase = getGamesUseCase,
                                        insertGameUseCase = insertGameUseCase,
                                        deleteGameUseCase = deleteGameUseCase,
                                        prefs = prefs
                                    )
                                }
                                TablesScreen3(game3PViewModel, winningPointsViewModel)
                            }
                            composable<Games4Dest> {
                                val repository =
                                    Games4PRepositoryImpl(Game4PDataSourceImpl(appDatabase.game4PDao()))
                                val getGamesUseCase = GetGames4PUseCase(repository)
                                val insertGameUseCase = InsertGame4PUseCase(repository)
                                val deleteGameUseCase = DeleteGame4PUseCase(repository)
                                val game4PViewModel = viewModel {
                                    Game4PViewModel(
                                        getGamesUseCase = getGamesUseCase,
                                        insertGameUseCase = insertGameUseCase,
                                        deleteGameUseCase = deleteGameUseCase,
                                        prefs = prefs
                                    )
                                }
                                TablesScreen4(
                                    viewModel = game4PViewModel, winningPointsViewModel,
                                )
                            }
                            composable<GamesGroupsDest> {
                                val repository = Games2GroupsRepositoryImpl(
                                    Game2GroupsDataSourceImpl(appDatabase.game2GroupsDao())
                                )
                                val getGamesUseCase = GetGames2GroupsUseCase(repository)
                                val insertGameUseCase = InsertGame2GroupsUseCase(repository)
                                val deleteGameUseCase = DeleteGame2GroupsUseCase(repository)
                                val game2GroupsViewModel = viewModel {
                                    Game2GroupsViewModel(
                                        getGamesUseCase = getGamesUseCase,
                                        insertGameUseCase = insertGameUseCase,
                                        deleteGameUseCase = deleteGameUseCase,
                                        prefs = prefs
                                    )
                                }
                                TablesScreenGroups(game2GroupsViewModel, winningPointsViewModel)
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
                                val deleteLastPointsUseCase =
                                    DeleteLastRowPoints2PUseCase(repositoryPoints)

                                val updateStatusScoreName1UseCase =
                                    UpdateStatusScoreName1Game2PUseCase(repositoryGame)
                                val updateStatusScoreName2UseCase =
                                    UpdateStatusScoreName2Game2PUseCase(repositoryGame)

                                val updateStatusWinningPointsUseCase =
                                    UpdateStatusWinningPointsGame2PUseCase(repositoryGame)
                                val updateOnlyStatusUseCase =
                                    UpdateOnlyStatusGame2PUseCase(repositoryGame)
                                val deleteAllPointsUseCase =
                                    DeleteAllPoints2PUseCase(repositoryPoints)
                                val viewModel = viewModel {
                                    Match2PPViewModel(
                                        idGame = idGame,
                                        getGameUseCase = getGameUseCase,
                                        getPointsUseCase = getPointsUseCase,
                                        insertPointsUseCase = insertPointsUseCase,
                                        deleteLastPointsUseCase = deleteLastPointsUseCase,
                                        updateStatusScoreName1UseCase = updateStatusScoreName1UseCase,
                                        updateStatusScoreName2UseCase = updateStatusScoreName2UseCase,
                                        updateStatusWinningPointsUseCase = updateStatusWinningPointsUseCase,
                                        updateOnlyStatusUseCase = updateOnlyStatusUseCase,
                                        deleteAllPointsUseCase = deleteAllPointsUseCase,
                                        prefs = prefs
                                    )
                                }
                                MatchScreen2(viewModel, winningPointsViewModel)
                            }
                            composable<Match3Dest> {
                                val idGame = it.toRoute<Match3Dest>().idGame

                                val repositoryGame =
                                    Games3PRepositoryImpl(Game3PDataSourceImpl(appDatabase.game3PDao()))
                                val repositoryPoints =
                                    Points3PRepositoryImpl(Points3PDataSourceImpl(appDatabase.points3PDao()))
                                val getGameUseCase = GetGame3PUseCase(repositoryGame)
                                val getPointsUseCase = GetPoints3PUseCase(repositoryPoints)
                                val insertPointsUseCase = InsertPoints3PUseCase(repositoryPoints)
                                val deleteLastPointsUseCase =
                                    DeleteLastRowPoints3PUseCase(repositoryPoints)

                                val updateStatusScoreName1UseCase =
                                    UpdateStatusScoreName1Game3PUseCase(repositoryGame)
                                val updateStatusScoreName2UseCase =
                                    UpdateStatusScoreName2Game3PUseCase(repositoryGame)

                                val updateStatusScoreName3UseCase =
                                    UpdateStatusScoreName3Game3PUseCase(repositoryGame)

                                val updateStatusWinningPointsUseCase =
                                    UpdateStatusWinningPointsGame3PUseCase(repositoryGame)
                                val updateOnlyStatusUseCase =
                                    UpdateOnlyStatusGame3PUseCase(repositoryGame)
                                val deleteAllPointsUseCase =
                                    DeleteAllPoints3PUseCase(repositoryPoints)

                                val match3PPViewModel = viewModel {
                                    Match3PPViewModel(
                                        idGame = idGame,
                                        getGameUseCase = getGameUseCase,
                                        getPointsUseCase = getPointsUseCase,
                                        insertPointsUseCase = insertPointsUseCase,
                                        deleteLastPointsUseCase = deleteLastPointsUseCase,
                                        updateStatusScoreName1UseCase = updateStatusScoreName1UseCase,
                                        updateStatusScoreName2UseCase = updateStatusScoreName2UseCase,
                                        updateStatusScoreName3UseCase = updateStatusScoreName3UseCase,
                                        updateStatusWinningPointsUseCase = updateStatusWinningPointsUseCase,
                                        updateOnlyStatusUseCase = updateOnlyStatusUseCase,
                                        deleteAllPointsUseCase = deleteAllPointsUseCase,
                                        prefs = prefs
                                    )
                                }

                                MatchScreen3(
                                    viewModel = match3PPViewModel, winningPointsViewModel
                                )
                            }
                            composable<Match4Dest> {
                                val idGame = it.toRoute<Match3Dest>().idGame

                                val repositoryGame =
                                    Games4PRepositoryImpl(Game4PDataSourceImpl(appDatabase.game4PDao()))
                                val repositoryPoints =
                                    Points4PRepositoryImpl(Points4PDataSourceImpl(appDatabase.points4PDao()))
                                val getGameUseCase = GetGame4PUseCase(repositoryGame)
                                val getPointsUseCase = GetPoints4PUseCase(repositoryPoints)
                                val insertPointsUseCase = InsertPoints4PUseCase(repositoryPoints)
                                val deleteLastPointsUseCase =
                                    DeleteLastRowPoints4PUseCase(repositoryPoints)

                                val updateStatusScoreName1UseCase =
                                    UpdateStatusScoreName1Game4PUseCase(repositoryGame)
                                val updateStatusScoreName2UseCase =
                                    UpdateStatusScoreName2Game4PUseCase(repositoryGame)

                                val updateStatusScoreName3UseCase =
                                    UpdateStatusScoreName3Game4PUseCase(repositoryGame)

                                val updateStatusScoreName4UseCase =
                                    UpdateStatusScoreName4Game4PUseCase(repositoryGame)

                                val updateStatusWinningPointsUseCase =
                                    UpdateStatusWinningPointsGame4PUseCase(repositoryGame)
                                val updateOnlyStatusUseCase =
                                    UpdateOnlyStatusGame4PUseCase(repositoryGame)
                                val deleteAllPointsUseCase =
                                    DeleteAllPoints4PUseCase(repositoryPoints)

                                val match4PPViewModel = viewModel {
                                    Match4PPViewModel(
                                        idGame = idGame,
                                        getGameUseCase = getGameUseCase,
                                        getPointsUseCase = getPointsUseCase,
                                        insertPointsUseCase = insertPointsUseCase,
                                        deleteLastPointsUseCase = deleteLastPointsUseCase,
                                        updateStatusScoreName1UseCase = updateStatusScoreName1UseCase,
                                        updateStatusScoreName2UseCase = updateStatusScoreName2UseCase,
                                        updateStatusScoreName3UseCase = updateStatusScoreName3UseCase,
                                        updateStatusScoreName4UseCase = updateStatusScoreName4UseCase,
                                        updateStatusWinningPointsUseCase = updateStatusWinningPointsUseCase,
                                        updateOnlyStatusUseCase = updateOnlyStatusUseCase,
                                        deleteAllPointsUseCase = deleteAllPointsUseCase,
                                        prefs = prefs
                                    )
                                }
                                MatchScreen4(
                                    viewModel = match4PPViewModel,
                                    winningPointsViewModel = winningPointsViewModel
                                )
                            }
                            composable<MatchGroupsDest> {
                                val idGame = it.toRoute<MatchGroupsDest>().idGame
                                val repositoryGame = Games2GroupsRepositoryImpl(
                                    Game2GroupsDataSourceImpl(appDatabase.game2GroupsDao())
                                )
                                val repositoryPoints = Points2GroupsRepositoryImpl(
                                    Points2GroupsDataSourceImpl(appDatabase.points2GroupsDao())
                                )
                                val getGameUseCase = GetGame2GroupsUseCase(repositoryGame)
                                val getPointsUseCase = GetPoints2GroupsUseCase(repositoryPoints)
                                val insertPointsUseCase =
                                    InsertPoints2GroupsUseCase(repositoryPoints)
                                val deleteLastPointsUseCase =
                                    DeleteLastRowPoints2GroupsUseCase(repositoryPoints)
                                val updateStatusScoreName1UseCase =
                                    UpdateStatusScoreGame2GroupsName1UseCase(repositoryGame)
                                val updateStatusScoreName2UseCase =
                                    UpdateStatusScoreGame2GroupsName2UseCase(repositoryGame)

                                val updateStatusWinningPointsUseCase =
                                    UpdateStatusWinningPointsGame2GroupsUseCase(repositoryGame)
                                val updateOnlyStatusUseCase =
                                    UpdateOnlyStatusGame2GroupsUseCase(repositoryGame)
                                val deleteAllPointsUseCase =
                                    DeleteAllPoints2GroupsUseCase(repositoryPoints)

                                val match2GroupsViewModel = viewModel {
                                    Match2GroupsViewModel(
                                        idGame = idGame,
                                        getGameUseCase = getGameUseCase,
                                        getPointsUseCase = getPointsUseCase,
                                        insertPointsUseCase = insertPointsUseCase,
                                        deleteLastPointsUseCase = deleteLastPointsUseCase,
                                        updateStatusScoreName1UseCase = updateStatusScoreName1UseCase,
                                        updateStatusScoreName2UseCase = updateStatusScoreName2UseCase,
                                        updateStatusWinningPointsUseCase = updateStatusWinningPointsUseCase,
                                        updateOnlyStatusUseCase = updateOnlyStatusUseCase,
                                        deleteAllPointsUseCase = deleteAllPointsUseCase,
                                        prefs = prefs
                                    )
                                }
                                MatchScreen2Groups(
                                    viewModel = match2GroupsViewModel,
                                    winningPointsViewModel = winningPointsViewModel
                                )
                            }
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
        title = { ScreenTitle(currentRoute) }, navigationIcon = {
        if (canNavigateBack) {
            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "menu items"
                )
            }
        }
    }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent, scrolledContainerColor = Color.Transparent
    ), modifier = modifier
    )
}

@Composable
private inline fun ScreenTitle(currentRoute: String?) {
    val tablesList = stringResource(Res.string.tables_list)
    val game = stringResource(Res.string.game)
    val twoPlayers = stringResource(Res.string.two_players)
    val threePlayers = stringResource(Res.string.three_players)
    val fourPlayers = stringResource(Res.string.four_players)
    val twoVsTwo = stringResource(Res.string.two_vs_two)
    val text = when (currentRoute) {
        HomeDest::class.qualifiedName -> {
            stringResource(Res.string.app_name)
        }

        Games2Dest::class.qualifiedName -> {
            "$tablesList $twoPlayers"

        }

        Match2Dest::class.qualifiedName + "/{idGame}" -> {
            "$game $twoPlayers"

        }

        Games3Dest::class.qualifiedName -> {
            "$tablesList $threePlayers"


        }

        Match3Dest::class.qualifiedName + "/{idGame}" -> {
            "$game $threePlayers"
        }

        Games4Dest::class.qualifiedName -> {
            "$tablesList $fourPlayers"


        }

        Match4Dest::class.qualifiedName + "/{idGame}" -> {
            "$game $fourPlayers"
        }

        GamesGroupsDest::class.qualifiedName -> {
            "$tablesList $twoVsTwo"
        }

        MatchGroupsDest::class.qualifiedName + "/{idGame}" -> {
            "$game $twoVsTwo"
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

