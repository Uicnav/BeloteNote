package com.ionvaranita.belotenote.ui.table


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import belotenote.composeapp.generated.resources.Res
import belotenote.composeapp.generated.resources.confirm_delete
import belotenote.composeapp.generated.resources.dialog_fragment_insert_manually_winner_points
import belotenote.composeapp.generated.resources.dialog_fragment_insert_match
import belotenote.composeapp.generated.resources.dialog_fragment_insert_table
import belotenote.composeapp.generated.resources.game_deleted
import belotenote.composeapp.generated.resources.ic_delete_white
import belotenote.composeapp.generated.resources.me
import belotenote.composeapp.generated.resources.no
import belotenote.composeapp.generated.resources.p
import belotenote.composeapp.generated.resources.sure_delete_row
import belotenote.composeapp.generated.resources.sure_delete_table
import belotenote.composeapp.generated.resources.undo
import belotenote.composeapp.generated.resources.we
import belotenote.composeapp.generated.resources.you_p
import belotenote.composeapp.generated.resources.you_s
import com.ionvaranita.belotenote.Match2Dest
import com.ionvaranita.belotenote.Match3Dest
import com.ionvaranita.belotenote.Match4Dest
import com.ionvaranita.belotenote.MatchGroupsDest
import com.ionvaranita.belotenote.StatusImage
import com.ionvaranita.belotenote.constants.GameStatus
import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Game2GroupsEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players2.Game2PEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players3.Game3PEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players4.Game4PEntity
import com.ionvaranita.belotenote.domain.model.WinningPointsUi
import com.ionvaranita.belotenote.ui.LocalNavHostController
import com.ionvaranita.belotenote.ui.LocalSnackbarHostState
import com.ionvaranita.belotenote.ui.match.ConfirmYesButton
import com.ionvaranita.belotenote.ui.viewmodel.WinningPointsViewModel
import com.ionvaranita.belotenote.ui.viewmodel.game.Game2GroupsViewModel
import com.ionvaranita.belotenote.ui.viewmodel.game.Game2PViewModel
import com.ionvaranita.belotenote.ui.viewmodel.game.Game3PViewModel
import com.ionvaranita.belotenote.ui.viewmodel.game.Game4PViewModel
import com.ionvaranita.belotenote.ui.viewmodel.game.Games2GroupsUiState
import com.ionvaranita.belotenote.ui.viewmodel.game.Games2PUiState
import com.ionvaranita.belotenote.ui.viewmodel.game.Games3PUiState
import com.ionvaranita.belotenote.ui.viewmodel.game.Games4PUiState
import com.ionvaranita.belotenote.utils.capitalizeName
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
internal fun TablesScreen2(
    viewModel: Game2PViewModel, winningPointsViewModel: WinningPointsViewModel
) {
    val snackbarHostState: SnackbarHostState = LocalSnackbarHostState.current
    val navController = LocalNavHostController.current
    val gamesUiState by viewModel.uiState.collectAsState()
    val gameListState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }

    val isLoading = gamesUiState is Games2PUiState.Loading
    val isEmptyList = (gamesUiState as? Games2PUiState.Success)?.data?.none { it.isVisible } == true

    TablesBase(
        onInsertGameClick = { showDialog = true }, isEmptyList = isEmptyList, isLoading = isLoading
    ) { paddingValues ->

        if (showDialog) {
            InsertGame2(
                onClick = {
                scope.launch {
                    val idGame = viewModel.insertGame(it)
                    navController.navigate(Match2Dest(idGame))
                }
            }, onDismissRequest = { showDialog = false }, viewModel = winningPointsViewModel
            )
        }

        when (val state = gamesUiState) {
            is Games2PUiState.Success -> {
                val visibleGames = state.data.filter { it.isVisible }

                LaunchedEffect(state.data.size) {
                    if (state.data.isNotEmpty()) {
                        gameListState.animateScrollToItem(state.data.size - 1)
                    }
                }

                DisposableEffect(Unit) {
                    onDispose {
                        viewModel.deleteGameToDelete()
                    }
                }

                LazyColumn(
                    contentPadding = paddingValues,
                    modifier = Modifier.fillMaxSize(),
                    state = gameListState
                ) {
                    itemsIndexed(visibleGames) { index, game ->
                        GameCard(
                            onDelete = {
                            scope.launch {
                                snackbarHostState.currentSnackbarData?.dismiss()
                                viewModel.prepareDeleteGame(game)
                                snackbarHostState.UndoDeleteGameSnackbar(onActionPerformed = {
                                    viewModel.undoDeleteGame(game)
                                }, onDismissed = {
                                    viewModel.deleteGame(game.idGame)

                                })
                            }
                        }, onTap = {
                            navController.navigate(Match2Dest(game.idGame))
                        }, isTable = true, isSwipe = index == visibleGames.lastIndex
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                TableTextAtom(game.name1)
                                TableTextAtom(game.name2)
                            }
                            TableDateTextAtom(text = game.dateGame)
                            Spacer(modifier = Modifier.width(16.dp))
                            StatusImage(gameStatus = GameStatus.fromId(game.statusGame))
                        }
                    }
                }
            }

            is Games2PUiState.Error -> {
                // Show optional error message
            }

            Games2PUiState.Loading -> {
                CenteredCircularProgressIndicator()
            }
        }
    }
}

suspend fun SnackbarHostState.UndoDeleteGameSnackbar(
    onActionPerformed: () -> Unit, onDismissed: () -> Unit
) {
    val result = this.showSnackbar(
        message = getString(Res.string.game_deleted),
        actionLabel = getString(Res.string.undo),
        withDismissAction = true
    )
    if (result == SnackbarResult.ActionPerformed) {
        onActionPerformed()
    } else {
        onDismissed()
    }
}

@Composable
internal fun TablesScreen3(
    viewModel: Game3PViewModel, winningPointsViewModel: WinningPointsViewModel
) {
    val snackbarHostState: SnackbarHostState = LocalSnackbarHostState.current
    val navController = LocalNavHostController.current
    val gamesUiState = viewModel.uiState.collectAsState()
    var shouDialog by remember { mutableStateOf(false) }
    val gameListState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var isEmptyList by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    TablesBase(onInsertGameClick = {
        shouDialog = true
    }, isEmptyList = isEmptyList, isLoading = isLoading) { paddingValues ->
        if (shouDialog) {
            InsertGame3(onClick = {
                scope.launch {
                    val idGame = viewModel.insertGame(game = it)
                    val route = Match3Dest(idGame = idGame)
                    navController.navigate(route)
                }
            }, onDismissRequest = {
                shouDialog = false
            }, viewModel = winningPointsViewModel)
        }

        when (val state = gamesUiState.value) {
            is Games3PUiState.Success -> {
                val visibleGames = state.data.filter { it.isVisible }
                DisposableEffect(Unit) {
                    onDispose {
                        viewModel.deleteGameToDelete()
                    }
                }
                isLoading = false
                isEmptyList = state.data.isEmpty()
                scope.launch {
                    gameListState.animateScrollToItem(state.data.size)
                }
                LazyColumn(
                    contentPadding = paddingValues,
                    modifier = Modifier.fillMaxSize(),
                    state = gameListState
                ) {
                    itemsIndexed(visibleGames) { index, game ->
                        GameCard(onDelete = {
                            snackbarHostState.currentSnackbarData?.dismiss()
                            viewModel.prepareDeleteGame(game)
                            scope.launch {
                                snackbarHostState.UndoDeleteGameSnackbar(onActionPerformed = {
                                    viewModel.undoDeleteGame(game)
                                }, onDismissed = {
                                    viewModel.deleteGame(game.idGame)
                                })
                            }
                        }, onTap = {
                            val route = Match3Dest(idGame = game.idGame)
                            navController.navigate(route)
                        }, isTable = true, isSwipe = index == visibleGames.lastIndex) {
                            Column(modifier = Modifier.weight(1F)) {
                                TableTextAtom(game.name1)
                                TableTextAtom(game.name2)
                            }
                            Column(modifier = Modifier.weight(1F)) {
                                TableTextAtom(game.name3)
                            }
                            TableDateTextAtom(text = game.dateGame)
                            Spacer(modifier = Modifier.width(16.dp))
                            StatusImage(
                                gameStatus = GameStatus.fromId(game.statusGame)
                            )
                        }
                    }
                }
            }

            is Games3PUiState.Error -> {
                isLoading = false
            }

            Games3PUiState.Loading -> {
                isLoading = true
                CenteredCircularProgressIndicator()
            }

        }
    }
}

@Composable
internal fun TablesScreen4(
    viewModel: Game4PViewModel, winningPointsViewModel: WinningPointsViewModel
) {
    val snackbarHostState: SnackbarHostState = LocalSnackbarHostState.current
    val navController = LocalNavHostController.current
    val gamesUiState = viewModel.uiState.collectAsState()
    var shouDialog by remember { mutableStateOf(false) }
    val gameListState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var isEmptyList by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    TablesBase(onInsertGameClick = {
        shouDialog = true
    }, isEmptyList = isEmptyList, isLoading = isLoading) { paddingValues ->
        if (shouDialog) {
            InsertGame4(onClick = {
                scope.launch {
                    val idGame = viewModel.insertGame(game = it)
                    val route = Match4Dest(idGame = idGame)
                    navController.navigate(route)
                }
            }, onDismissRequest = {
                shouDialog = false
            }, winningPointsViewModel = winningPointsViewModel)
        }

        when (val state = gamesUiState.value) {
            is Games4PUiState.Success -> {
                val visibleGames = state.data.filter { it.isVisible }
                DisposableEffect(Unit) {
                    onDispose {
                        viewModel.deleteGameToDelete()
                    }
                }
                isLoading = false
                isEmptyList = state.data.isEmpty()
                LazyColumn(
                    contentPadding = paddingValues,
                    modifier = Modifier.fillMaxSize(),
                    state = gameListState
                ) {
                    scope.launch {
                        gameListState.animateScrollToItem(state.data.size)
                    }
                    itemsIndexed(visibleGames) { index, game ->
                        GameCard(onDelete = {
                            snackbarHostState.currentSnackbarData?.dismiss()
                            viewModel.prepareDeleteGame(game)
                            scope.launch {
                                snackbarHostState.UndoDeleteGameSnackbar(onActionPerformed = {
                                    viewModel.undoDeleteGame(game)

                                }, onDismissed = {
                                    viewModel.deleteGame(game.idGame)

                                })
                            }
                        }, onTap = {
                            val route = Match4Dest(idGame = game.idGame)
                            navController.navigate(route)
                        }, isTable = true, isSwipe = index == visibleGames.lastIndex) {
                            Column(modifier = Modifier.weight(1F)) {
                                TableTextAtom(game.name1)
                                TableTextAtom(game.name2)
                            }
                            Column(modifier = Modifier.weight(1F)) {
                                TableTextAtom(game.name3)
                                TableTextAtom(game.name4)
                            }
                            TableDateTextAtom(text = game.dateGame)
                            Spacer(modifier = Modifier.width(16.dp))
                            StatusImage(
                                gameStatus = GameStatus.fromId(game.statusGame)
                            )
                        }
                    }
                }
            }

            is Games4PUiState.Error -> {
                isLoading = false// Handle error
            }

            Games4PUiState.Loading -> {
                isLoading = true
                CenteredCircularProgressIndicator()
            }

        }
    }
}

@Composable
internal fun TablesScreenGroups(
    viewModel: Game2GroupsViewModel, winningPointsViewModel: WinningPointsViewModel
) {
    val navController = LocalNavHostController.current
    val gamesUiState = viewModel.uiState.collectAsState()
    var shouDialog by remember { mutableStateOf(false) }
    val gameListState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var isEmptyList by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    val snackbarHostState: SnackbarHostState = LocalSnackbarHostState.current

    TablesBase(onInsertGameClick = {
        shouDialog = true
    }, isEmptyList = isEmptyList, isLoading = isLoading) { paddingValues ->
        if (shouDialog) {
            InsertGame2Groups(onClick = { game ->
                scope.launch {
                    val idGame = viewModel.insertGame(game = game)
                    val route = MatchGroupsDest(idGame = idGame)
                    navController.navigate(route)
                }

            }, onDismissRequest = {
                shouDialog = false
            }, winningPointsViewModel = winningPointsViewModel)
        }

        when (val state = gamesUiState.value) {
            is Games2GroupsUiState.Success -> {
                val visibleGames = state.data.filter { it.isVisible }
                DisposableEffect(Unit) {
                    onDispose {
                        viewModel.deleteGameToDelete()
                    }
                }
                isLoading = false
                isEmptyList = state.data.isEmpty()
                scope.launch {
                    gameListState.animateScrollToItem(state.data.size)
                }
                LazyColumn(
                    contentPadding = paddingValues,
                    modifier = Modifier.fillMaxSize(),
                    state = gameListState
                ) {
                    itemsIndexed(visibleGames) { index, game ->
                        GameCard(onDelete = {
                            snackbarHostState.currentSnackbarData?.dismiss()
                            viewModel.prepareDeleteGame(game)
                            scope.launch {
                                snackbarHostState.UndoDeleteGameSnackbar(onActionPerformed = {
                                    viewModel.undoDeleteGame(game)

                                }, onDismissed = {
                                    viewModel.deleteGame(game.idGame)
                                })
                            }
                        }, onTap = {
                            val route = MatchGroupsDest(idGame = game.idGame)
                            navController.navigate(route)
                        }, isTable = true, isSwipe = index == visibleGames.lastIndex) {
                            Column(modifier = Modifier.weight(1f)) {
                                TableTextAtom(text = game.name1)
                                TableTextAtom(text = game.name2)
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            TableDateTextAtom(text = game.dateGame)
                            StatusImage(
                                gameStatus = GameStatus.fromId(game.statusGame)
                            )
                        }
                    }
                }
            }

            is Games2GroupsUiState.Error -> { // Handle error
                isLoading = false
            }

            Games2GroupsUiState.Loading -> {
                isLoading = true
                CenteredCircularProgressIndicator()
            }
        }
    }
}

@Composable
fun CenteredCircularProgressIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.background(Color.Transparent).fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ConfirmDeleteDialog(onConfirm: () -> Unit, onDismiss: () -> Unit, isTable: Boolean) {
    AlertDialog(onDismissRequest = onDismiss, title = {
        Text(stringResource(Res.string.confirm_delete))
    }, text = {
        Text(
            text = if (isTable) stringResource(Res.string.sure_delete_table) else stringResource(
                Res.string.sure_delete_row
            )
        )
    }, confirmButton = {
        ConfirmYesButton(onConfirm = onConfirm)
    }, dismissButton = {
        Button(onClick = onDismiss) {
            Text(stringResource(Res.string.no))
        }
    })
}

@Composable
fun GameCard(
    onDelete: () -> Unit,
    onTap: () -> Unit = {},
    isTable: Boolean = false,
    isSwipe: Boolean = true,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    val offsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    val swipeThreshold = -150f
    LaunchedEffect(Unit) {
        if (isSwipe) {
            delay(1000L)
            offsetX.animateTo(-140f, tween(300))
            delay(500L)
            offsetX.animateTo(0f, tween(300))
        }
    }

    Box(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
        Box(
            modifier = Modifier.matchParentSize().clip(shape = CardDefaults.shape)
                .background(Color.Red).padding(end = 16.dp), contentAlignment = Alignment.CenterEnd
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_delete_white),
                contentDescription = null,
                modifier = modifier.width(44.dp)
            )
        }

        Card(modifier = modifier.offset { IntOffset(offsetX.value.toInt(), 0) }.pointerInput(Unit) {
            detectHorizontalDragGestures(onDragEnd = {
                if (offsetX.value <= swipeThreshold) {
                    showDialog = true
                } else {
                    scope.launch {
                        offsetX.animateTo(0f, animationSpec = tween(300))
                    }
                }
            }) { change, dragAmount ->
                change.consume()
                val newOffset = offsetX.value + dragAmount
                scope.launch {
                    offsetX.snapTo(newOffset.coerceIn(-300f, 0f))
                }
            }
        }.pointerInput(Unit) {
            detectTapGestures(onTap = {
                onTap()
            })
        }) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(if (isTable) 16.dp else 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                content()
                Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = null)
            }
        }
    }

    if (showDialog) {
        ConfirmDeleteDialog(onConfirm = {
            onDelete()
            showDialog = false
            scope.launch { offsetX.snapTo(0f) }
        }, onDismiss = {
            showDialog = false
            scope.launch { offsetX.snapTo(0f) }
        }, isTable = isTable)
    }

}

@Composable
private fun TableTextAtom(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.displayLarge,
        maxLines = 1,
        textAlign = TextAlign.Center,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
private fun TableDateTextAtom(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        maxLines = 1,
        textAlign = TextAlign.Center,
        modifier = modifier.wrapContentSize()
    )
}

@Composable
private fun TablesBase(
    onInsertGameClick: () -> Unit,
    isEmptyList: Boolean,
    isLoading: Boolean,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            InsertFloatingActionButton(
                onClick = { onInsertGameClick() },
                animate = isEmptyList,
                isLoading = isLoading,
                modifier = Modifier
            )
        },
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)  // Remove default window insets
    ) { paddingValues ->
        val customValues = PaddingValues(
            start = 8.dp, end = 8.dp, bottom = paddingValues.calculateBottomPadding() + 80.dp
        )
        content(customValues)
    }

}

@Composable
fun InsertFloatingActionButton(
    onClick: () -> Unit,
    isLoading: Boolean = false,
    animate: Boolean = false,
    modifier: Modifier = Modifier
) {
    val scale by if (animate) {
        rememberInfiniteTransition().animateFloat(
            initialValue = 1f, targetValue = 1.2f, animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 600, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
    } else {
        remember { mutableStateOf(1f) }
    }
    FloatingActionButton(
        onClick = {
            if (!isLoading) onClick()
        }, modifier = modifier.scale(scale)
    ) {
        Icon(
            imageVector = Icons.Filled.Add, contentDescription = "Floating action button"
        )
    }
}

@Composable
fun InsertGame2(
    viewModel: WinningPointsViewModel, onDismissRequest: () -> Unit, onClick: (Game2PEntity) -> Unit
) {
    val p1Hint = stringResource(Res.string.me)
    val p2Hint = stringResource(Res.string.you_s)
    var p1 by remember { mutableStateOf(p1Hint) }
    var p2 by remember { mutableStateOf(p2Hint) }
    val shaker1 = remember { TextFieldShaker() }
    val shaker2 = remember { TextFieldShaker() }

    InsertGameDialogBase(onDismissRequest = onDismissRequest, onClick = { winningPoints ->
        when {
            p1.isEmpty() -> shaker1.shake()
            p2.isEmpty() -> shaker2.shake()
            else -> {
                onClick(
                    Game2PEntity(
                        winningPoints = winningPoints,
                        name1 = p1.capitalizeName(),
                        name2 = p2.capitalizeName()
                    )
                )
                onDismissRequest()
            }
        }
    }, isNewGame = true, winningPointsViewModel = viewModel) {
        Row {
            ShakerTextFieldAtom(
                value = p1,
                onValueChange = { p1 = it },
                shaker = shaker1,
                modifier = Modifier.weight(1f),
                charLength = 4
            )
            ShakerTextFieldAtom(
                value = p2,
                onValueChange = { p2 = it },
                shaker = shaker2,
                modifier = Modifier.weight(1f),
                charLength = 4
            )
        }
    }
}

@Composable
fun InsertGame3(
    viewModel: WinningPointsViewModel, onDismissRequest: () -> Unit, onClick: (Game3PEntity) -> Unit
) {
    val p1Hint = stringResource(Res.string.p) + 1
    val p2Hint = stringResource(Res.string.p) + 2
    val p3Hint = stringResource(Res.string.p) + 3
    var p1 by remember { mutableStateOf(p1Hint) }
    var p2 by remember { mutableStateOf(p2Hint) }
    var p3 by remember { mutableStateOf(p3Hint) }
    val shakerP1 by remember { mutableStateOf(TextFieldShaker()) }
    val shakerP2 by remember { mutableStateOf(TextFieldShaker()) }
    val shakerP3 by remember { mutableStateOf(TextFieldShaker()) }


    InsertGameDialogBase(onDismissRequest = onDismissRequest, onClick = { winningPoints ->
        if (p1.isEmpty()) {
            shakerP1.shake()
        } else if (p2.isEmpty()) {
            shakerP2.shake()
        } else if (p3.isEmpty()) {
            shakerP3.shake()
        } else {
            onClick(
                Game3PEntity(
                    name1 = p1.capitalizeName(),
                    name2 = p2.capitalizeName(),
                    name3 = p3.capitalizeName(),
                    winningPoints = winningPoints
                )
            )
            onDismissRequest()
        }
    }, isNewGame = true, winningPointsViewModel = viewModel) {
        Row {
            ShakerTextFieldAtom(value = p1, onValueChange = {
                p1 = it
            }, modifier = Modifier.weight(1F), shaker = shakerP1)
            ShakerTextFieldAtom(value = p2, onValueChange = {
                p2 = it
            }, modifier = Modifier.weight(1F), shaker = shakerP2)
            ShakerTextFieldAtom(value = p3, onValueChange = {
                p3 = it
            }, modifier = Modifier.weight(1F), shaker = shakerP3)
        }
    }
}

@Composable
fun InsertGame4(
    winningPointsViewModel: WinningPointsViewModel,
    onDismissRequest: () -> Unit,
    onClick: (Game4PEntity) -> Unit
) {
    val p1Hint = stringResource(Res.string.p) + 1
    val p2Hint = stringResource(Res.string.p) + 2
    val p3Hint = stringResource(Res.string.p) + 3
    val p4Hint = stringResource(Res.string.p) + 4
    var p1 by remember { mutableStateOf(p1Hint) }
    var p2 by remember { mutableStateOf(p2Hint) }
    var p3 by remember { mutableStateOf(p3Hint) }
    var p4 by remember { mutableStateOf(p4Hint) }
    val shakerP1 by remember { mutableStateOf(TextFieldShaker()) }
    val shakerP2 by remember { mutableStateOf(TextFieldShaker()) }
    val shakerP3 by remember { mutableStateOf(TextFieldShaker()) }
    val shakerP4 by remember { mutableStateOf(TextFieldShaker()) }
    InsertGameDialogBase(onDismissRequest = onDismissRequest, onClick = { winningPoints ->
        if (p1.isEmpty()) {
            shakerP1.shake()
        } else if (p2.isEmpty()) {
            shakerP2.shake()
        } else if (p3.isEmpty()) {
            shakerP3.shake()
        } else if (p4.isEmpty()) {
            shakerP4.shake()
        } else {
            onClick(
                Game4PEntity(
                    name1 = p1.capitalizeName(),
                    name2 = p2.capitalizeName(),
                    name3 = p3.capitalizeName(),
                    name4 = p4.capitalizeName(),
                    winningPoints = winningPoints
                )
            )
            onDismissRequest()
        }
    }, isNewGame = true, winningPointsViewModel = winningPointsViewModel) {
        Row {
            ShakerTextFieldAtom(value = p1, onValueChange = {
                p1 = it
            }, modifier = Modifier.weight(1F), shaker = shakerP1)
            ShakerTextFieldAtom(value = p2, onValueChange = {
                p2 = it
            }, modifier = Modifier.weight(1F), shaker = shakerP2)
            ShakerTextFieldAtom(value = p3, onValueChange = {
                p3 = it
            }, modifier = Modifier.weight(1F), shaker = shakerP3)
            ShakerTextFieldAtom(value = p4, onValueChange = {
                p4 = it
            }, modifier = Modifier.weight(1F), shaker = shakerP4)
        }
    }
}

@Composable
fun InsertGame2Groups(
    winningPointsViewModel: WinningPointsViewModel,
    onDismissRequest: () -> Unit,
    onClick: (Game2GroupsEntity) -> Unit
) {
    val p1Hint = stringResource(Res.string.we)
    val p2Hint = stringResource(Res.string.you_p)
    var p1 by remember { mutableStateOf(p1Hint) }
    var p2 by remember { mutableStateOf(p2Hint) }
    val shakerP1 by remember { mutableStateOf(TextFieldShaker()) }
    val shakerP2 by remember { mutableStateOf(TextFieldShaker()) }
    InsertGameDialogBase(onDismissRequest = onDismissRequest, onClick = { winningPoints ->
        if (p1.isEmpty()) {
            shakerP1.shake()
        } else if (p2.isEmpty()) {
            shakerP2.shake()
        } else {
            onClick(
                Game2GroupsEntity(
                    name1 = p1.capitalizeName(),
                    name2 = p2.capitalizeName(),
                    winningPoints = winningPoints
                )
            )
            onDismissRequest()

        }
    }, isNewGame = true, winningPointsViewModel = winningPointsViewModel) {
        Row {
            ShakerTextFieldAtom(value = p1, onValueChange = {
                p1 = it
            }, modifier = Modifier.weight(1F), shaker = shakerP1, charLength = 4)
            ShakerTextFieldAtom(value = p2, onValueChange = {
                p2 = it
            }, modifier = Modifier.weight(1F), shaker = shakerP2, charLength = 4)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun InsertGameDialogBase(
    winningPointsViewModel: WinningPointsViewModel,
    onDismissRequest: () -> Unit,
    onClick: (Short) -> Unit,
    isNewGame: Boolean = false,
    content: (@Composable () -> Unit)? = null
) {
    val winningPointsDb by winningPointsViewModel.winningPoints.collectAsState()
    if (winningPointsDb.isNotEmpty()) {
        var winningPoints by remember { mutableStateOf(winningPointsDb[0].winningPoints.toString()) }
        val shakerWinningPoints by remember { mutableStateOf(TextFieldShaker()) }
        Dialog(onDismissRequest = { onDismissRequest() }) {
            Column(
                modifier = Modifier.wrapContentSize().background(
                    MaterialTheme.colorScheme.background, shape = RoundedCornerShape(16.dp)
                ).padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                content?.invoke()
                var isChecked by remember { mutableStateOf(false) }

                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(Res.string.dialog_fragment_insert_manually_winner_points),
                        style = MaterialTheme.typography.labelLarge,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                        modifier = Modifier.weight(1F)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(checked = isChecked, onCheckedChange = { isChecked = it })
                }
                if (!isChecked) {
                    var expanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                        TextField(
                            value = winningPoints,
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier.menuAnchor(
                                type = MenuAnchorType.PrimaryNotEditable, enabled = true
                            ).fillMaxWidth(),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded, onDismissRequest = { expanded = false }) {
                            winningPointsDb.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option.winningPoints.toString()) },
                                    onClick = {
                                        winningPoints = option.winningPoints.toString()
                                        expanded = false
                                    })
                            }
                        }
                    }
                }


                if (isChecked) {
                    ShakerTextFieldAtom(value = winningPoints, onValueChange = { newText ->
                        if (newText.isEmpty()) {
                            winningPoints = ""
                        } else if (newText.all { it.isDigit() } && !(newText.startsWith("0"))) {
                            winningPoints = newText
                        }
                    }, shaker = shakerWinningPoints, isOnlyDigit = true)
                }
                Button(onClick = {
                    if (winningPoints.isNotEmpty()) {
                        val result = winningPoints.toShort()
                        winningPointsViewModel.insertWinningPoints(WinningPointsUi(winningPoints = result))
                        onClick(result)
                    } else {
                        shakerWinningPoints.shake()
                    }
                }) {
                    Text(
                        text = if (isNewGame) {
                            stringResource(Res.string.dialog_fragment_insert_table)
                        } else {
                            stringResource(Res.string.dialog_fragment_insert_match)

                        }
                    )
                }
            }
        }
    }
}

class TextFieldShaker {
    val shouldShake = mutableStateOf(false)
    fun shake() {
        shouldShake.value = true
    }

    fun reset() {
        shouldShake.value = false
    }
}

@Composable
fun ShakerTextFieldAtom(
    value: String,
    onValueChange: (String) -> Unit,
    shaker: TextFieldShaker,
    isOnlyDigit: Boolean = false,
    placeholder: String = "",
    charLength: Int = 3,
    modifier: Modifier = Modifier
) {
    val vibrationOffset = remember { Animatable(0f) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(shaker.shouldShake.value) {
        if (shaker.shouldShake.value) {
            focusRequester.requestFocus()
            repeat(6) {
                vibrationOffset.snapTo(if (it % 2 == 0) 6f else -6f)
                delay(30)
            }
            vibrationOffset.snapTo(0f)
        }
        shaker.reset()
    }

    TextField(
        value = value,
        onValueChange = {
            if (it.length <= charLength) {
                val filtered =
                    it.filter { char -> if (isOnlyDigit) char.isDigit() else char.isLetterOrDigit() }
                onValueChange(filtered)
            }
        },
        modifier = modifier.focusRequester(focusRequester).offset(x = vibrationOffset.value.dp)
            .padding(4.dp),
        textStyle = MaterialTheme.typography.bodySmall.copy(textAlign = TextAlign.Center),
        singleLine = true,
        maxLines = 1,
        keyboardOptions = if (isOnlyDigit) KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number) else KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text
        ),
        placeholder = {
            Text(
                text = placeholder, fontStyle = FontStyle.Italic
            )
        },
    )
}
