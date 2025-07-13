package com.ionvaranita.belotenote.ui.table


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import belotenote.composeapp.generated.resources.Res
import belotenote.composeapp.generated.resources.confirm_delete
import belotenote.composeapp.generated.resources.dialog_fragment_insert_manually_winner_points
import belotenote.composeapp.generated.resources.dialog_fragment_insert_match
import belotenote.composeapp.generated.resources.ic_delete_white
import belotenote.composeapp.generated.resources.me
import belotenote.composeapp.generated.resources.no
import belotenote.composeapp.generated.resources.p
import belotenote.composeapp.generated.resources.sure_delete_row
import belotenote.composeapp.generated.resources.sure_delete_table
import belotenote.composeapp.generated.resources.we
import belotenote.composeapp.generated.resources.yes
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
import com.ionvaranita.belotenote.ui.LocalNavHostController
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
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

enum class WinningPointsEnum(val stringValue: String, val intValue: Int) {
    ONE_HUNDRED_ONE(stringValue = "101", intValue = 101), FIFTY_ONE(
        stringValue = "51", intValue = 51
    )
}

@Composable
internal fun TablesScreen2(
    viewModel: Game2PViewModel
) {
    val navController = LocalNavHostController.current
    val gamesUiState = viewModel.uiState.collectAsState()
    var shouDialog by remember { mutableStateOf(false) }
    val gameListState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    TablesBase(onInsertGameClick = {
        shouDialog = true
    }) { paddingValues ->
        if (shouDialog) {
            InsertGame2(onClick = {
                scope.launch {
                    val idGame = viewModel.insertGame(game = it)
                    val route = Match2Dest(idGame = idGame)
                    navController.navigate(route)
                }

            }, onDismissRequest = {
                shouDialog = false
            })
        }

        when (val state = gamesUiState.value) {
            is Games2PUiState.Success -> {
                scope.launch {
                    gameListState.animateScrollToItem(state.data.size)
                }
                LazyColumn(
                    contentPadding = paddingValues,
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    state = gameListState
                ) {
                    items(state.data) { game ->
                        GameCard(onDelete = {
                            viewModel.deleteGame(game.idGame)
                        }, onTap = {
                            val route = Match2Dest(idGame = game.idGame)
                            navController.navigate(route)
                        }, isTable = true) {
                            Column(modifier = Modifier.weight(1F)) {
                                TableTextAtom(game.name1)
                                TableTextAtom(game.name2)
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            StatusImage(
                                gameStatus = GameStatus.fromId(game.statusGame)
                            )
                        }
                    }
                }
            }

            is Games2PUiState.Error -> { // Handle error
            }
        }
    }
}

@Composable
internal fun TablesScreen3(
    viewModel: Game3PViewModel
) {
    val navController = LocalNavHostController.current
    val gamesUiState = viewModel.uiState.collectAsState()
    var shouDialog by remember { mutableStateOf(false) }
    val gameListState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    TablesBase(onInsertGameClick = {
        shouDialog = true
    }) { paddingValues ->
        if (shouDialog) {
            InsertGame3(onClick = {
                scope.launch {
                    val idGame = viewModel.insertGame(game = it)
                    val route = Match3Dest(idGame = idGame)
                    navController.navigate(route)
                }
            }, onDismissRequest = {
                shouDialog = false
            })
        }

        when (val state = gamesUiState.value) {
            is Games3PUiState.Success -> {
                scope.launch {
                    gameListState.animateScrollToItem(state.data.size)
                }
                LazyColumn(
                    contentPadding = paddingValues,
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    state = gameListState
                ) {
                    items(state.data) { game ->
                        GameCard(onDelete = {
                            viewModel.deleteGame(game.idGame)
                        }, onTap = {
                            val route = Match3Dest(idGame = game.idGame)
                            navController.navigate(route)
                        }, isTable = true) {
                            Column(modifier = Modifier.weight(1F)) {
                                TableTextAtom(game.name1)
                                TableTextAtom(game.name2)
                            }
                            TableTextAtom(game.name3, modifier = Modifier.weight(1F))
                            Spacer(modifier = Modifier.width(16.dp))
                            StatusImage(
                                gameStatus = GameStatus.fromId(game.statusGame)
                            )
                        }
                    }
                }
            }

            is Games3PUiState.Error -> { // Handle error
            }
        }
    }
}

@Composable
internal fun TablesScreen4(
    game4PViewModel: Game4PViewModel
) {
    val navController = LocalNavHostController.current
    val gamesUiState = game4PViewModel.uiState.collectAsState()
    var shouDialog by remember { mutableStateOf(false) }
    val gameListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    TablesBase(onInsertGameClick = {
        shouDialog = true
    }) { paddingValues ->
        if (shouDialog) {
            InsertGame4(onClick = {
                scope.launch {
                    val idGame = game4PViewModel.insertGame(game = it)
                    val route = Match4Dest(idGame = idGame)
                    navController.navigate(route)
                }
            }, onDismissRequest = {
                shouDialog = false
            })
        }

        when (val state = gamesUiState.value) {
            is Games4PUiState.Success -> {
                LazyColumn(
                    contentPadding = paddingValues,
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    state = gameListState
                ) {
                    scope.launch {
                        gameListState.animateScrollToItem(state.data.size)
                    }
                    items(state.data) { game ->
                        GameCard(onDelete = {
                            game4PViewModel.deleteGame(game.idGame)
                        }, onTap = {
                            val route = Match4Dest(idGame = game.idGame)
                            navController.navigate(route)
                        }, isTable = true) {
                            Column(modifier = Modifier.weight(1F)) {
                                TableTextAtom(game.name1)
                                TableTextAtom(game.name2)
                            }
                            Column(modifier = Modifier.weight(1F)) {
                                TableTextAtom(game.name3)
                                TableTextAtom(game.name4)
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            StatusImage(
                                gameStatus = GameStatus.fromId(game.statusGame)
                            )
                        }
                    }
                }
            }

            is Games4PUiState.Error -> { // Handle error
            }
        }
    }
}

@Composable
internal fun TablesScreenGroups(viewModel: Game2GroupsViewModel) {
    val navController = LocalNavHostController.current
    val gamesUiState = viewModel.uiState.collectAsState()
    var shouDialog by remember { mutableStateOf(false) }
    val gameListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    TablesBase(onInsertGameClick = {
        shouDialog = true
    }) { paddingValues ->
        if (shouDialog) {
            InsertGame2Groups(onClick = { game ->
                scope.launch {
                    val idGame = viewModel.insertGame(game = game)
                    val route = MatchGroupsDest(idGame = idGame)
                    navController.navigate(route)
                }

            }, onDismissRequest = {
                shouDialog = false
            })
        }

        when (val state = gamesUiState.value) {
            is Games2GroupsUiState.Success -> {
                scope.launch {
                    gameListState.animateScrollToItem(state.data.size)
                }
                LazyColumn(
                    contentPadding = paddingValues,
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    state = gameListState
                ) {
                    items(state.data) { game ->
                        GameCard(onDelete = {
                            viewModel.deleteGame(game.idGame)
                        }, onTap = {
                            val route = MatchGroupsDest(idGame = game.idGame)
                            navController.navigate(route)
                        }, isTable = true) {
                            Column(modifier = Modifier.weight(1f)) {
                                TableTextAtom(text = game.name1)
                                TableTextAtom(text = game.name2)
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            StatusImage(
                                gameStatus = GameStatus.fromId(game.statusGame)
                            )
                        }
                    }
                }
            }

            is Games2GroupsUiState.Error -> { // Handle error
            }
        }
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
        Button(onClick = onConfirm) {
            Text(stringResource(Res.string.yes))
        }
    }, dismissButton = {
        Button(onClick = onDismiss) {
            Text(stringResource(Res.string.no))
        }
    })
}

@Composable
fun GameCard(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    onTap: () -> Unit = {}, isTable: Boolean = false,
    content: @Composable RowScope.() -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val offsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    val swipeThreshold = -150f
    LaunchedEffect(Unit) {
        delay(1000L)
        offsetX.animateTo(-140f, tween(300))
        delay(500L)
        offsetX.animateTo(0f, tween(300))
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
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                content()
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
        modifier = modifier
    )
}

@Composable
private fun TablesBase(
    onInsertGameClick: () -> Unit, content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(floatingActionButton = {
        InsertFloatingActionButton(onClick = {
            onInsertGameClick()
        }, modifier = Modifier)
    }, containerColor = Color.Transparent) { paddingValues ->
        content(paddingValues)
    }

}

@Composable
fun InsertFloatingActionButton(onClick: () -> Unit, modifier: Modifier) {
    FloatingActionButton(
        modifier = modifier,
        onClick = { onClick() },
    ) {
        Icon(Icons.Filled.Add, "Floating action button.")
    }
}

@Composable
fun InsertGame2(
    onDismissRequest: () -> Unit, onClick: (Game2PEntity) -> Unit
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
    }) {
        Row {
            InsertNamesTextFieldAtom(
                value = p1,
                onValueChange = { p1 = it },
                shaker = shaker1,
                modifier = Modifier.weight(1f)
            )
            InsertNamesTextFieldAtom(
                value = p2,
                onValueChange = { p2 = it },
                shaker = shaker2,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun InsertGame3(
    onDismissRequest: () -> Unit, onClick: (Game3PEntity) -> Unit
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
    }) {
        Row {
            InsertNamesTextFieldAtom(value = p1, onValueChange = {
                p1 = it
            }, modifier = Modifier.weight(1F), shaker = shakerP1)
            InsertNamesTextFieldAtom(value = p2, onValueChange = {
                p2 = it
            }, modifier = Modifier.weight(1F), shaker = shakerP2)
            InsertNamesTextFieldAtom(value = p3, onValueChange = {
                p3 = it
            }, modifier = Modifier.weight(1F), shaker = shakerP3)
        }
    }
}

@Composable
fun InsertGame4(
    onDismissRequest: () -> Unit, onClick: (Game4PEntity) -> Unit
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
                    name4 = p4.capitalizeName(), winningPoints = winningPoints
                )
            )
            onDismissRequest()
        }
    }) {
        Row {
            InsertNamesTextFieldAtom(value = p1, onValueChange = {
                p1 = it
            }, modifier = Modifier.weight(1F), shaker = shakerP1)
            InsertNamesTextFieldAtom(value = p2, onValueChange = {
                p2 = it
            }, modifier = Modifier.weight(1F), shaker = shakerP2)
            InsertNamesTextFieldAtom(value = p3, onValueChange = {
                p3 = it
            }, modifier = Modifier.weight(1F), shaker = shakerP3)
            InsertNamesTextFieldAtom(value = p4, onValueChange = {
                p4 = it
            }, modifier = Modifier.weight(1F), shaker = shakerP4)
        }
    }
}

@Composable
fun InsertGame2Groups(
    onDismissRequest: () -> Unit, onClick: (Game2GroupsEntity) -> Unit
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
    }) {
        Row {
            InsertNamesTextFieldAtom(value = p1, onValueChange = {
                p1 = it
            }, modifier = Modifier.weight(1F), shaker = shakerP1)
            InsertNamesTextFieldAtom(value = p2, onValueChange = {
                p2 = it
            }, modifier = Modifier.weight(1F), shaker = shakerP2)
        }
    }
}

@Composable
internal fun InsertGameDialogBase(
    onDismissRequest: () -> Unit,
    onClick: (Short) -> Unit,
    content: (@Composable () -> Unit)? = null
) {
    var winningPoints by remember { mutableStateOf(WinningPointsEnum.ONE_HUNDRED_ONE.stringValue) }
    val shakerWinningPoints by remember { mutableStateOf(TextFieldShaker()) }
    Dialog(onDismissRequest = { onDismissRequest() }) { // Draw a rectangle shape with rounded corners inside the dialog

        Card(
            modifier = Modifier.fillMaxWidth().height(375.dp).padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
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
                        style = MaterialTheme.typography.labelLarge
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(checked = isChecked, onCheckedChange = { isChecked = it })
                }
                if (!isChecked) {
                    WinningPointsRadioButtons(
                        selectedValue = winningPoints.toInt(),
                        onValueChange = { winningPoints = it.toString() })
                }

                if (isChecked) {
                    InsertNamesTextFieldAtom(value = winningPoints, onValueChange = { newText ->
                        if (newText.isEmpty()) {
                            winningPoints = ""
                        } else if (newText.all { it.isDigit() } && !(newText.startsWith("0"))) {
                            winningPoints = newText
                        }
                    }, shaker = shakerWinningPoints, isOnlyDigit = true)
                }
                Button(onClick = {
                    if (winningPoints.isNotEmpty()) {
                        onClick(winningPoints.toShort())
                    } else {
                        shakerWinningPoints.shake()
                    }
                }) {
                    Text(stringResource(Res.string.dialog_fragment_insert_match))
                }
            }
        }
    }
}

@Composable
fun WinningPointsRadioButtons(
    selectedValue: Int, onValueChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onValueChange(WinningPointsEnum.ONE_HUNDRED_ONE.intValue) }) {
            RadioButton(
                selected = selectedValue == WinningPointsEnum.ONE_HUNDRED_ONE.intValue,
                onClick = { onValueChange(WinningPointsEnum.ONE_HUNDRED_ONE.intValue) })
            Text(text = WinningPointsEnum.ONE_HUNDRED_ONE.stringValue)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onValueChange(WinningPointsEnum.FIFTY_ONE.intValue) }) {
            RadioButton(
                selected = selectedValue == WinningPointsEnum.FIFTY_ONE.intValue,
                onClick = { onValueChange(WinningPointsEnum.FIFTY_ONE.intValue) })
            Text(text = WinningPointsEnum.FIFTY_ONE.stringValue)
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
private fun InsertNamesTextFieldAtom(
    value: String,
    onValueChange: (String) -> Unit,
    shaker: TextFieldShaker,
    isOnlyDigit: Boolean = false,
    modifier: Modifier = Modifier
) {
    val vibrationOffset = remember { Animatable(0f) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(shaker.shouldShake.value) {
        if (shaker.shouldShake.value) {
            if (value.isEmpty()) {
                focusRequester.requestFocus()
                repeat(6) {
                    vibrationOffset.snapTo(if (it % 2 == 0) 6f else -6f)
                    delay(30)
                }
                vibrationOffset.snapTo(0f)
            }
            shaker.reset()
        }
    }

    TextField(
        value = value,
        onValueChange = {
            if (it.length <= 3) {
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
        )
    )
}


